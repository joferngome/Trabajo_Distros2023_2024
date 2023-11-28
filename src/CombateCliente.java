import Interfaz_grafica.InterfazGraficaInicio;
import Pokemon.Pokemon;

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
            //Lo he comentado para probar la interfaz grafica, si quieres probarlo por consola descomentalo y comenta esta línea. Descomenta el catch también
            //InterfazGraficaInicio in = new InterfazGraficaInicio(s);

            //Genera equipo
            Equipo_Pokemon eq1 = Equipo_Pokemon.generar_equipo();
            System.out.println("genera equipo cliente " + eq1.getEquipo().size());

            Pokemon pokemonActivo = eq1.elegir_pokemon();
            eq1.setPokemonActivo(pokemonActivo);

            //Manda equipo
            oos.writeObject(eq1);

            //recibe equipo
            Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();
            System.out.println("El rival ha elegido: "+eqEnemigo.getPokemonActivo().getName());

            int[] opciones = eq1.elegir_accion();

            while(opciones[0] != 3){
                //Manda al servidor la accion a realizar
                oos.writeObject(opciones);

                //Lee los dos equipos actualizados
                eq1 = (Equipo_Pokemon) ois.readObject();
                eqEnemigo = (Equipo_Pokemon) ois.readObject();

                opciones = eq1.elegir_accion();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    }

