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

import Tipos.Tabla_Tipos_Modificador;
import Tipos.Tipo;

public class Combate_Servidor {

    public static void main(String args[]) {

        try (ServerSocket ss = new ServerSocket(6666)) {

            while (true) {

                try (Socket rival = ss.accept();
                        DataOutputStream dos = new DataOutputStream(rival.getOutputStream());
                        DataInputStream dis = new DataInputStream(rival.getInputStream());
                        ObjectInputStream ois = new ObjectInputStream(rival.getInputStream());) {

                    Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();

                    Random r = new Random();
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

                    //Equipo_Pokemon eqEnemigo = (Equipo_Pokemon) ois.readObject();

                    Equipo_Pokemon eq1 = new Equipo_Pokemon(equipArray);

                    while (!eq1.AllDead() && !eqEnemigo.AllDead()) {

                        System.out.println("Elige un pokemon aliado para luchar: ");
                        for (int i = 0; i < eq1.getEquipo().size(); i++) {
                            System.out.println((i + 1) + " " + eq1.getEquipo().get(i).getName());
                        }

                        // Pokemon Activo

                        eq1.setPokemonActivo(eq1.getEquipo().get(dis.readInt() - 1));
                        Pokemon pokemonActivo = eq1.getPokemonActivo();

                        System.out.println("Entrenador 1 eligió a: " + eq1.getPokemonActivo().getName());


                        //Equipo enemigo

                        dos.writeBytes("Elige un pokemon para luchar: \n");
                        for (int i = 0; i < eqEnemigo.getEquipo().size(); i++) {
                            dos.writeBytes((i + 1) + " " + eqEnemigo.getEquipo().get(i).getName()+"\n");
                        }

                        Pokemon pEnemigo = eqEnemigo.getEquipo().get(Integer.parseInt(ois.readLine())-1);




                        // Pokemon Activo
/*
eq1.setPokemonActivo(eq1.getEquipo().get(dis.readInt() - 1));
                        Pokemon pokemonActivo = eq1.getPokemonActivo();

                        System.out.println("Entrenador 1 eligió a: " + eq1.getPokemonActivo().getName());
 */





                        Pokemon pokemonEnemigo = ((Equipo_Pokemon) ois.readObject()).getPokemonActivo();
                        int velocidadEnemigo = pokemonEnemigo.getSpeed();

                        while (pokemonActivo.isAlive() && pokemonEnemigo.isAlive()) {

                            

                            // Comprobamos si el pokemon enemigo es mas rapido que el pokemon activo del
                            // entrenador 1

                            if (velocidadEnemigo > eq1.getPokemonActivo().getSpeed()) {
                                //Primero me atacan luego ataco.

                                //Nos mandan el numero del ataque que va a hacer.

                                
                                Attack ataqueEnemigo = pokemonEnemigo.getAttacks().get(Integer.parseInt(dis.readLine()));

                                pokemonEnemigo.atacar(pokemonActivo, ataqueEnemigo);



                            }

                            else {

                                //Mostrar ataques entrenador 1

                                System.out.println("Elige un ataque: ");
                                for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
                                    System.out.println((i + 1) + " " + pokemonActivo.getAttacks().get(i).getName());
                                }

                                //Elige el ataque

                                Attack ataque = pokemonActivo.getAttacks().get(dis.readInt() - 1);

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
