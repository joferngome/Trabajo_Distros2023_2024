import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

import Interfaz_grafica.InterfazGraficaInicio;
import Pokemon.*;

import javax.swing.*;

public class Combate_Servidor {
    private static String mensaje;

    public static void main(String args[]) {
        try (ServerSocket ss = new ServerSocket(6666)) {

            while (true) {

                try (Socket rival = ss.accept();
                        ObjectOutputStream oos = new ObjectOutputStream(rival.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(rival.getInputStream());) {

                    //Generar equipo
                    Equipo_Pokemon eq1 = Equipo_Pokemon.generar_equipo();
                    System.out.println("genera equipo servidor " + eq1.getEquipo().size());


                    // Pokemon con el que abrir la batalla
                    eq1.elegir_pokemon();

                    //mandar equipo
                    oos.writeObject(eq1);

                    //Equipo rival
                    Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();
                    System.out.println("El rival ha elegido a "+eqEnemigo.getPokemonActivo());

                    int[] opciones = eq1.elegir_accion();

                    while (!eq1.AllDead() && !eqEnemigo.AllDead() && opciones[0] != 3) {
                        oos.reset();

                        oos.writeObject(opciones);
                        //Obtiene eleccion y equipo del rival
                        int[] opciones_rival = (int[]) ois.readObject();

                        if(opciones_rival[0] == 3){
                            System.out.println("El rival se ha rendido");
                            break;
                        }

                        eqEnemigo = (Equipo_Pokemon)  ois.readObject();

                        //Ejecuta el turno
                        ArrayList<Equipo_Pokemon> equipos_actualizados = ejecutar_turno(eq1,opciones,eqEnemigo,opciones_rival);

                        //Actualiza los equipos despues del turno
                        eq1 = equipos_actualizados.get(0);
                        eqEnemigo = equipos_actualizados.get(1);

                        //Manda el equipo suyo al cliente
                        oos.writeObject(eqEnemigo);

                        //Comprueba si ha muerto nuestro pokemon activo
                        if(!eq1.getPokemonActivo().isAlive()){
                            System.out.println(eq1.getPokemonActivo().getName() + " ha muerto");
                            Pokemon elegido = eq1.elegir_pokemon();
                            if(elegido == null){
                                oos.writeObject(eq1);
                                break;
                            }
                        }

                        //Manda nuestro equipo al cliente
                        oos.writeObject(eq1);

                        //Si se ha muerto el pokemon del rival, nos tiene que mandar de nuevo su equipo con el nuevo pokemon activo
                        if(!eqEnemigo.getPokemonActivo().isAlive()){
                            System.out.println("El "+ eqEnemigo.getPokemonActivo().getName() + " rival ha muerto");
                            if(eqEnemigo.AllDead()){
                                break;
                            }
                            eqEnemigo = (Equipo_Pokemon)  ois.readObject();

                        }
                        System.out.println("El rival esta usando a " + eqEnemigo.getPokemonActivo());

                        //repetir turno
                        opciones = eq1.elegir_accion();
                    }
                    if(opciones[0] == 3){
                        System.out.println("Te has rendido");
                        //Esperar a que el rival elija para que no explote.
                        oos.writeObject(opciones);
                        int[] opciones_rival = (int[]) ois.readObject();
                    }else if(eq1.AllDead()){
                        System.out.println("Has perdido");
                    }else if(eqEnemigo.AllDead()){
                        System.out.println("Has ganado");
                    }

                    System.out.println("Final del combate");

                    System.out.println("Esperando un nuevo combate...");
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<Equipo_Pokemon> ejecutar_turno( Equipo_Pokemon equipo1, int [] opciones1, Equipo_Pokemon equipo2, int [] opciones2){

        if(opciones1[0] == 2 && opciones2[0] == 2 ){
            System.out.println("Cambian los dos");
            //Cambian los dos de pokemon activo
            //equipo1.setPokemonActivo(equipo1.getEquipo().get(opciones1[1]-1));
            //equipo2.setPokemonActivo(equipo2.getEquipo().get(opciones2[1]-1));
        }
        else if(opciones1[0] == 1 && opciones2[0] == 2 ){
            System.out.println("Cambian 2 ataca 1");
            //Cambia el 2 de pokemon activo, y luego el 1 ataca
            //equipo2.setPokemonActivo(equipo2.getEquipo().get(opciones2[1]-1));
            //Ataca equipo 1
            int porVida = equipo2.getPokemonActivo().getHealthPercentage();

            int dano_hecho = equipo1.getPokemonActivo().atacar(equipo2.getPokemonActivo(), equipo1.getPokemonActivo().getAttacks().get(opciones1[1]));

            int Porcevidaquitada =  porVida - equipo2.getPokemonActivo().getHealthPercentage();
            mensaje = "El ataque "+ equipo1.getPokemonActivo().getAttacks().get(opciones1[1]).getName()+ " de "+equipo1.getPokemonActivo().getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+equipo2.getPokemonActivo().getName()+"\n";
        }else if (opciones1[0] == 2 && opciones2[0] == 1 ) {
            System.out.println("Cambian 1 ataca 2");
            //Cambia el 1 de pokemon activo y luego el 2 ataca
            //equipo1.setPokemonActivo(equipo1.getEquipo().get(opciones1[1]-1));
            //Ataca equipo 2

            int porVida = equipo1.getPokemonActivo().getHealthPercentage();

            int dano_hecho = equipo2.getPokemonActivo().atacar(equipo1.getPokemonActivo(), equipo2.getPokemonActivo().getAttacks().get(opciones2[1]));

            int Porcevidaquitada =  porVida - equipo1.getPokemonActivo().getHealthPercentage();
            mensaje = "El ataque "+ equipo2.getPokemonActivo().getAttacks().get(opciones2[1]).getName()+ " de "+equipo2.getPokemonActivo().getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+equipo1.getPokemonActivo().getName()+"\n";
        }
        else if(opciones1[0] == 1 && opciones2[0] == 1 ){
            System.out.println("Atacan los 2");
            //Atacan los dos, pero primero el más rápido
            if(equipo1.getPokemonActivo().getSpeed() > equipo2.getPokemonActivo().getSpeed()) {
                //Ataca equipo 1
                int porVida = equipo2.getPokemonActivo().getHealthPercentage();

                int dano_hecho = equipo1.getPokemonActivo().atacar(equipo2.getPokemonActivo(), equipo1.getPokemonActivo().getAttacks().get(opciones1[1]));

                int Porcevidaquitada =  porVida - equipo2.getPokemonActivo().getHealthPercentage();
                mensaje = "El ataque "+ equipo1.getPokemonActivo().getAttacks().get(opciones1[1]).getName()+ " de "+equipo1.getPokemonActivo().getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+equipo2.getPokemonActivo().getName()+"\n";
                if(equipo2.getPokemonActivo().isAlive()){
                    porVida = equipo1.getPokemonActivo().getHealthPercentage();
                    //si no se ha muerto el pokemon activo del 2 ataca
                    dano_hecho = equipo2.getPokemonActivo().atacar(equipo1.getPokemonActivo(), equipo2.getPokemonActivo().getAttacks().get(opciones2[1]));
                    Porcevidaquitada =  porVida - equipo1.getPokemonActivo().getHealthPercentage();
                    mensaje += "El ataque "+ equipo2.getPokemonActivo().getAttacks().get(opciones2[1]).getName()+ " de "+equipo2.getPokemonActivo().getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+equipo1.getPokemonActivo().getName()+"\n";

                }
            }else{
                //Ataca equipo 2
                int porVida = equipo1.getPokemonActivo().getHealthPercentage();
                int dano_hecho = equipo2.getPokemonActivo().atacar(equipo1.getPokemonActivo(), equipo2.getPokemonActivo().getAttacks().get(opciones2[1]));
                int Porcevidaquitada =  porVida - equipo2.getPokemonActivo().getHealthPercentage();
                mensaje = "El ataque "+ equipo1.getPokemonActivo().getAttacks().get(opciones1[1]).getName()+ " de "+equipo1.getPokemonActivo().getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+equipo2.getPokemonActivo().getName()+"\n";

                if(equipo1.getPokemonActivo().isAlive()){
                    //si no se ha muerto el pokemon activo del 1 ataca
                    porVida = equipo2.getPokemonActivo().getHealthPercentage();
                    dano_hecho = equipo1.getPokemonActivo().atacar(equipo2.getPokemonActivo(), equipo1.getPokemonActivo().getAttacks().get(opciones1[1]));
                    Porcevidaquitada =  porVida - equipo1.getPokemonActivo().getHealthPercentage();
                    mensaje += "El ataque "+ equipo2.getPokemonActivo().getAttacks().get(opciones2[1]).getName()+ " de "+equipo2.getPokemonActivo().getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+equipo1.getPokemonActivo().getName()+"\n";
                }
            }
        }

        //Devuelve los dos equipos actualizados despues del turno
        ArrayList<Equipo_Pokemon> equipos = new ArrayList<Equipo_Pokemon>();
        equipos.add(equipo1);
        equipos.add(equipo2);

        return equipos;

    }
}
