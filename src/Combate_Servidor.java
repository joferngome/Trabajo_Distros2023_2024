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

    public static void main(String args[]) {
        try (ServerSocket ss = new ServerSocket(6666)) {

            while (true) {

                try (Socket rival = ss.accept();
                        ObjectOutputStream oos = new ObjectOutputStream(rival.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(rival.getInputStream());) {

                    //Lo he comentado para probar la interfaz grafica, si quieres probarlo por consola descomentalo y comenta esta línea. Descomenta el catch también
                    //InterfazGraficaInicio in = new InterfazGraficaInicio(rival);

                    //Generar equipo
                    Equipo_Pokemon eq1 = Equipo_Pokemon.generar_equipo();
                    System.out.println("genera equipo servidor " + eq1.getEquipo().size());


                    // Pokemon Activo
                    Pokemon pokemonActivo = eq1.elegir_pokemon();
                    eq1.setPokemonActivo(pokemonActivo);

                    //mandar equipo al rival
                    oos.writeObject(eq1);
                    //Equipo rival
                    Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();
                    System.out.println("El rival ha elegido a "+eqEnemigo.getPokemonActivo().getName());

                    int[] opciones = eq1.elegir_accion();

                    while (!eq1.AllDead() && !eqEnemigo.AllDead() && opciones[0] != 3) {
                        //Obtiene eleccion del rival
                        int[] opciones_rival = (int[]) ois.readObject();

                        //Ejecuta el turno
                        ArrayList<Equipo_Pokemon> equipos_actualizados = ejecutar_turno(eq1,opciones,eqEnemigo,opciones_rival);

                        //Actualiza los equipos despues del turno
                        eq1 = equipos_actualizados.get(0);
                        eqEnemigo = equipos_actualizados.get(1);

                        if(!eq1.getPokemonActivo().isAlive()){
                            System.out.println(eq1.getPokemonActivo().getName() + " ha muerto");
                            pokemonActivo = eq1.elegir_pokemon();
                            eq1.setPokemonActivo(pokemonActivo);
                        }

                        //Manda los equipos al cliente, primero el suyo y luego el nuestro
                        oos.writeObject(eqEnemigo);
                        oos.writeObject(eq1);

                        //repetir turno
                        opciones = eq1.elegir_accion();
                    }

                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Attack elegir_ataque(Pokemon pokemonActivo){
        System.out.println("Ataca el jugador");

        //Mostrar ataques entrenador 1

        System.out.println("Elige un ataque: ");
        for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
            System.out.println((i + 1) + " " + pokemonActivo.getAttacks().get(i).getName());
        }

        //Elige el ataque
        Scanner s = new Scanner(System.in);

        return pokemonActivo.getAttacks().get(s.nextInt() - 1);
    }
    private static ArrayList<Equipo_Pokemon> ejecutar_turno( Equipo_Pokemon equipo1, int [] opciones1, Equipo_Pokemon equipo2, int [] opciones2){

        if(opciones1[0] == 2 && opciones2[0] == 2 ){
            //Cambian los dos de pokemon activo
            equipo1.setPokemonActivo(equipo1.getEquipo().get(opciones1[1]));
            equipo2.setPokemonActivo(equipo2.getEquipo().get(opciones2[1]));
        }
        else if(opciones1[0] == 1 && opciones2[0] == 2 ){
            //Cambia el 2 de pokemon activo, y luego el 1 ataca
            equipo2.setPokemonActivo(equipo2.getEquipo().get(opciones2[1]));
            //Ataca equipo 1
            int dano_hecho = equipo1.getPokemonActivo().atacar(equipo2.getPokemonActivo(), equipo1.getPokemonActivo().getAttacks().get(opciones1[1]));
            equipo2.getPokemonActivo().setHealth(equipo2.getPokemonActivo().getHealth() - dano_hecho);
        }else if (opciones1[0] == 2 && opciones2[0] == 1 ) {
            //Cambia el 1 de pokemon activo y luego el 2 ataca
            equipo1.setPokemonActivo(equipo1.getEquipo().get(opciones1[1]));
            //Ataca equipo 2
            int dano_hecho = equipo2.getPokemonActivo().atacar(equipo1.getPokemonActivo(), equipo2.getPokemonActivo().getAttacks().get(opciones2[1]));
            equipo1.getPokemonActivo().setHealth(equipo1.getPokemonActivo().getHealth() - dano_hecho);
        }
        else if(opciones1[0] == 1 && opciones2[0] == 1 ){
            //Atacan los dos, pero primero el más rápido
            if(equipo1.getPokemonActivo().getSpeed() > equipo2.getPokemonActivo().getSpeed()) {
                //Ataca equipo 1
                int dano_hecho = equipo1.getPokemonActivo().atacar(equipo2.getPokemonActivo(), equipo1.getPokemonActivo().getAttacks().get(opciones1[1]));
                equipo2.getPokemonActivo().setHealth(equipo2.getPokemonActivo().getHealth() - dano_hecho);
                if(equipo2.getPokemonActivo().isAlive()){
                    //si no se ha muerto el pokemon activo del 2 ataca
                    dano_hecho = equipo2.getPokemonActivo().atacar(equipo1.getPokemonActivo(), equipo2.getPokemonActivo().getAttacks().get(opciones2[1]));
                    equipo1.getPokemonActivo().setHealth(equipo1.getPokemonActivo().getHealth() - dano_hecho);
                }
            }else{
                //Ataca equipo 2
                int dano_hecho = equipo2.getPokemonActivo().atacar(equipo1.getPokemonActivo(), equipo2.getPokemonActivo().getAttacks().get(opciones2[1]));
                equipo1.getPokemonActivo().setHealth(equipo1.getPokemonActivo().getHealth() - dano_hecho);
                if(equipo2.getPokemonActivo().isAlive()){
                    //si no se ha muerto el pokemon activo del 1 ataca
                    dano_hecho = equipo1.getPokemonActivo().atacar(equipo2.getPokemonActivo(), equipo1.getPokemonActivo().getAttacks().get(opciones1[1]));
                    equipo2.getPokemonActivo().setHealth(equipo2.getPokemonActivo().getHealth() - dano_hecho);

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
