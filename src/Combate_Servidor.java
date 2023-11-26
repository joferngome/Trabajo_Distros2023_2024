import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Tipos.Tabla_Tipos_Modificador;
import Tipos.Tipo;

public class Combate_Servidor {

    public static void main(String args[]) {

        try (ServerSocket ss = new ServerSocket(6666)) {

            while (true) {

                try (Socket rival = ss.accept();
                        ObjectOutputStream oos = new ObjectOutputStream(rival.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(rival.getInputStream());) {
                    Random r = new Random();
                    ArrayList<Pokemon> equipArray = new ArrayList<>();
                    List<Integer> num_usados = new ArrayList<>();
                    for(int i = 0; i < 6; i++ ) {//lo he cambiado a 1 por simplificar
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

                    while (!eq1.AllDead() && !eqEnemigo.AllDead()) {

                        System.out.println("Elige un pokemon aliado para luchar: ");
                        for (int i = 0; i < eq1.getEquipo().size(); i++) {

                            System.out.println((i + 1) + " " + eq1.getEquipo().get(i).getName());
                        }

                        // Pokemon Activo

                        Scanner s1 = new Scanner(System.in);

                        eq1.setPokemonActivo(eq1.getEquipo().get(Integer.parseInt(s1.nextLine())-1));

                        Pokemon pokemonActivo = eq1.getPokemonActivo();

                        System.out.println("Entrenador 1 eligió a: " + eq1.getPokemonActivo().getName());


                        //Equipo enemigo

                        oos.writeObject("Elige un pokemon para luchar: \n");

                        System.out.println("manda elegir");
                        for (int i = 0; i < eqEnemigo.getEquipo().size(); i++) {
                            oos.writeObject((i + 1) + " " + eqEnemigo.getEquipo().get(i).getName()+"\n");
                        }

                        Pokemon pEnemigo = eqEnemigo.getEquipo().get((int)ois.readObject()-1);
                        eqEnemigo.setPokemonActivo(pEnemigo);

                        System.out.println("Se ha elegido: "+pEnemigo.getName());




                        // Pokemon Activo
/*
eq1.setPokemonActivo(eq1.getEquipo().get(dis.readInt() - 1));
                        Pokemon pokemonActivo = eq1.getPokemonActivo();

                        System.out.println("Entrenador 1 eligió a: " + eq1.getPokemonActivo().getName());
 */





                        Pokemon pokemonEnemigo = eqEnemigo.getPokemonActivo();
                        int velocidadEnemigo = pokemonEnemigo.getSpeed();
                        System.out.println("Llego aqui antes del combate");

                        while (pokemonActivo.isAlive() && pokemonEnemigo.isAlive()) {
                            System.out.println("Los dos vivos");

                            

                            // Comprobamos si el pokemon enemigo es mas rapido que el pokemon activo del
                            // entrenador 1

                            if (velocidadEnemigo > eq1.getPokemonActivo().getSpeed()) {
                                System.out.println("aTACA EL ENEMIGO");
                                //Primero me atacan luego ataco.

                                //Nos mandan el numero del ataque que va a hacer.

                                oos.writeObject("Atacas");

                                //Enviar ataques por oos
                                

                                for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
                                    oos.writeObject((i + 1) + " " + pokemonActivo.getAttacks().get(i).getName()+"\n");
                                }

                                
                                Attack ataqueEnemigo = pokemonEnemigo.getAttacks().get((Integer )ois.readObject()-1);
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


                                Attack ataque = pokemonActivo.getAttacks().get(s2.nextInt() - 1);

                                //Ataca

                                pokemonActivo.atacar(pokemonEnemigo, ataque);
                                

                            }

                        }
                        
                        //Aqui habra q cambiar de pokemon activo xq significa que alguno de los 2 ha muerto.

                    }

                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
