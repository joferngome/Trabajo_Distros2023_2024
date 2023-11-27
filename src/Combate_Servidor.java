import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Interfaz_grafica.InterfazGraficaInicio;
import Pokemon.*;

public class Combate_Servidor {

    public static void main(String args[]) {

        try (ServerSocket ss = new ServerSocket(6666)) {

            while (true) {

                try (Socket rival = ss.accept();
                        ObjectOutputStream oos = new ObjectOutputStream(rival.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(rival.getInputStream());) {

                    //Lo he comentado para probar la interfaz grafica, si quieres probarlo por consola descomentalo y comenta esta línea. Descomenta el catch también
                    InterfazGraficaInicio in = new InterfazGraficaInicio(rival);

                    //Generar equipo
                    /*Random r = new Random();
                    ArrayList<Pokemon> equipArray = new ArrayList<>();
                    List<Integer> num_usados = new ArrayList<>();
                    for(int i = 0; i < 6; i++ ) {
                        int numpoke = r.nextInt(1017);
                        while (num_usados.contains(numpoke)) {
                            numpoke = r.nextInt(1017);
                        }
                        System.out.println(numpoke);
                        Pokemon pok1 = Pokemon.generar_pokemon("https://pokeapi.co/api/v2/pokemon/" + numpoke);
                        equipArray.add(pok1);
                        num_usados.add(numpoke);
                    }

                    System.out.println("genera equipo servidor " + equipArray.size());

                    Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();

                    Equipo_Pokemon eq1 = new Equipo_Pokemon(equipArray);

                    // Pokemon.Pokemon Activo
                    Pokemon pokemonActivo = elegir_pokemon(eq1);

                    //Equipo enemigo*/

                    /* oos.writeObject("Elige un pokemon para luchar: \n");

                    for (int i = 0; i < eqEnemigo.getEquipo().size(); i++) {
                        oos.writeObject((i + 1) + " " + eqEnemigo.getEquipo().get(i).getName()+"\n");
                    }

                    Pokemon pEnemigo = eqEnemigo.getEquipo().get((int)ois.readObject()-1);
                    eqEnemigo.setPokemonActivo(pEnemigo);

                    System.out.println("Se ha elegido: "+pEnemigo.getName());
                    int opcion_turno = 0;

                    while (!eq1.AllDead() && !eqEnemigo.AllDead() && opcion_turno != 3) {

                        Pokemon pokemonEnemigo = eqEnemigo.getPokemonActivo();

                        int velocidadEnemigo = pokemonEnemigo.getSpeed();

                        //Estrucura de cada turno

                        //1. Elegir opción (cambiar pokemon, usar ataque)
                        //2. Mandar opción elegida
                        //3. Si la opción es cambiar, se ejecta lo primero, sino se comprueba velocidad y se ataca.



                        //Si atacan los 2
                        //Comprobar velocidades
                        //Ejecutar ataque del más rápido
                        //Si no mata, ejecutar ataque del más lento
                        //comprobar si mata


                        //Si cambian pokemon
                        //mostrar menu elegir pokemon
                        //hacer el cambio de pokemon activo
                        //comprobar si el otro ataca o cambia tambien
                        //si ataca ejecutar ataque
                        //si cambia cambiar pokemon activo enemigo.

                        //repetir turno


                        //comento esto porque mejor hacerlo ya directamente con interfaz gráfica y botones.

                        /*
                        System.out.println("1. Atacar");
                        System.out.println("2. Cambiar de pokemon");
                        System.out.println("3. Rendirse");

                        Scanner s1 = new Scanner(System.in);
                        opcion_turno = s1.nextInt();

                        if(opcion_turno == 1){
                            elegir_pokemon(eq1);
                        }else if(opcion_turno == 2){




                            //Mandar opciones
                            oos.writeObject("Atacas");

                            for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
                                oos.writeObject((i + 1) + " " + pokemonActivo.getAttacks().get(i).getName()+"\n");
                            }

                            Pokemon.Attack ataqueEnemigo = pokemonEnemigo.getAttacks().get((Integer )ois.readObject()-1);
                            pokemonEnemigo.atacar(pokemonActivo, ataqueEnemigo);

                            Pokemon.Attack ataque = elegir_ataque(pokemonActivo);

                            pokemonActivo.atacar(pokemonEnemigo, ataque);
                        }




                        while (pokemonActivo.isAlive() && pokemonEnemigo.isAlive()) {
                            // Comprobamos si el pokemon enemigo es mas rapido que el pokemon activo del
                            // entrenador 1
                            if (velocidadEnemigo > eq1.getPokemonActivo().getSpeed()) {
                                System.out.println("ATACA EL ENEMIGO");
                                //Primero me atacan luego ataco.

                                //Nos mandan el numero del ataque que va a hacer.

                                oos.writeObject("Atacas");

                                //Enviar ataques por oos
                                

                                for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
                                    oos.writeObject((i + 1) + " " + pokemonActivo.getAttacks().get(i).getName()+"\n");
                                }

                                
                                Pokemon.Attack ataqueEnemigo = pokemonEnemigo.getAttacks().get((Integer )ois.readObject()-1);
                                System.out.println("El pokemon enemigo ha usado: "+ataqueEnemigo.getName());

                                pokemonEnemigo.atacar(pokemonActivo, ataqueEnemigo);



                            }

                            else {
                                System.out.println("Ataca el jugador");

                                //Mostrar ataques entrenador 1

                                System.out.println("Elige un ataque: ");
                                for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
                                    System.out.println((i + 1) + " " + pokemonActivo.getAttacks().get(i).getName());
                                }

                                //Elige el ataque
                                
                                Scanner s2 = new Scanner(System.in);


                                Pokemon.Attack ataque = pokemonActivo.getAttacks().get(s2.nextInt() - 1);

                                //Ataca

                                pokemonActivo.atacar(pokemonEnemigo, ataque);
                                

                            }

                        }

                    }*/

                } /*catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Pokemon elegir_pokemon(Equipo_Pokemon equipo){
        //Elegir el pokemon con el que abrir el combate

        ArrayList<Pokemon> equipoDisponible = equipo.getEquipoDisponible();
        System.out.println("Elige un pokemon aliado para luchar: ");

        for (int i = 0; i < equipoDisponible.size(); i++) {

            System.out.println((i + 1) + " " + equipoDisponible.get(i).getName());
        }

        Scanner s1 = new Scanner(System.in);

        equipo.setPokemonActivo(equipo.getEquipo().get(s1.nextInt()-1));

        System.out.println("Entrenador 1 eligió a: " + equipo.getPokemonActivo().getName());

        return equipo.getPokemonActivo();
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
}
