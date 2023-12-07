import Pokemon.Pokemon;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Pokemon.*;

public class CombateCliente {

    public static void main(String args[]){
        boolean salir = false;
        System.out.println("cliente main");
        try(Socket s = new Socket("localhost",6666);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream()))
        {

            //Genera equipo
            Equipo_Pokemon eq1 = Equipo_Pokemon.generar_equipo();
            System.out.println("genera equipo cliente " + eq1.getEquipo().size());

            // Pokemon con el que abrir la batalla
            eq1.elegir_pokemon();

            //Manda equipo
            oos.writeObject(eq1);

            //recibe equipo
            Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();
            System.out.println("El rival ha elegido: "+eqEnemigo.getPokemonActivo().getName());

            int[] opciones = eq1.elegir_accion();

            oos.reset();

            while(!eq1.AllDead() && !eqEnemigo.AllDead() && opciones[0] != 3){
                //Manda al servidor la accion a realizar y su equipo


                oos.writeObject(opciones);
                oos.writeObject(eq1);

                //Lee eleccion rival(por si se ha rendido)
                int[] opciones_rival = (int[]) ois.readObject();
                if(opciones_rival[0] == 3){
                    System.out.println("El rival se ha rendido");
                    //Significa que el rival se ha rendido
                    break;
                }

                //Lee los dos equipos actualizados
                eq1 = (Equipo_Pokemon) ois.readObject();
                eqEnemigo = (Equipo_Pokemon) ois.readObject();

                oos.reset();

                //Si se ha muerto nuestro pokemon, elegimos otro y mandamos actualización al servidor
                if(!eq1.getPokemonActivo().isAlive()){
                    System.out.println(eq1.getPokemonActivo().getName() + " ha muerto");
                    Pokemon elegido = eq1.elegir_pokemon();
                    if(elegido == null){
                        oos.writeObject(eq1);
                        break;
                    }
                    oos.writeObject(eq1);
                }

                if(!eqEnemigo.getPokemonActivo().isAlive()){
                    System.out.println("El "+ eqEnemigo.getPokemonActivo().getName() + " rival ha muerto");
                    if(eqEnemigo.AllDead()){
                        break;
                    }
                }

                System.out.println("El rival esta usando a " + eqEnemigo.getPokemonActivo());

                opciones = eq1.elegir_accion();
                oos.reset();
            }
            if(opciones[0] == 3) {
                System.out.println("Te has rendido");
                oos.writeObject(opciones);
                //Esperar a que el rival elija para que no explote.
                int[] opciones_rival = (int[]) ois.readObject();
            }else if(eq1.AllDead()){
                System.out.println("Has perdido");
            }else if(eqEnemigo.AllDead()){
                System.out.println("Has ganado");
            }

            System.out.println("Final del combate");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    }

