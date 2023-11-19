import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Tipos.Tabla_Tipos_Modificador;
import Tipos.Tipo;

public class Combate_Servidor {


    public static void main(String args[]){

        try(ServerSocket ss = new ServerSocket(6666)){


            while(true){

                try(Socket rival = ss.accept();
                    DataOutputStream dos = new DataOutputStream(rival.getOutputStream());
                    DataInputStream dis = new DataInputStream(rival.getInputStream());
                    ObjectInputStream ois = new ObjectInputStream(rival.getInputStream());){

                    ArrayList<Pokemon> pok1 = new ArrayList<>();




                    ArrayList<Tipo> tipos = new ArrayList<Tipo>();

                    tipos.add(Tipo.fighting);
                    
                    


                    ArrayList<Attack> ataquesHitmonchan = new ArrayList<>();
                    Attack a1 = new Attack("Patada giro",100,100,5, Tipo.fighting);
                    Attack a2 = new Attack("Roca afilada",80,100,5, Tipo.fighting);
                    Attack a3 = new Attack("Puño hielo",75,100,5, Tipo.fighting);
                    Attack a4 = new Attack("Puño trueno",75,100,5, Tipo.fighting);
                    ataquesHitmonchan.add(a1);
                    ataquesHitmonchan.add(a2);
                    ataquesHitmonchan.add(a3);
                    ataquesHitmonchan.add(a4);


                    Pokemon Hitmonchan = new Pokemon("Hitmonchan",600,50,20,10000,
                            tipos, ataquesHitmonchan);

                    pok1.add(Hitmonchan);

                    Equipo_Pokemon eq1 = new Equipo_Pokemon(pok1);


                    

                    while(!eq1.AllDead()){

                        System.out.println("Elige un pokemon para luchar: ");
                        for(int i = 0; i < eq1.getEquipo().size(); i++){
                            System.out.println((i+1) + " " + eq1.getEquipo().get(i).getName());
                        }

                        //Pokemon Activo

                        eq1.setPokemonActivo(eq1.getEquipo().get(dis.readInt()-1));
                        Pokemon pokemonActivo = eq1.getPokemonActivo();

                        System.out.println("Entrenador 1 eligió a: " + eq1.getPokemonActivo().getName());

                        Pokemon pokemonEnemigo = (Pokemon) ois.readObject();
                        //La linea tendra la pinta: NombrePokemon Velocidad. Ejemplo: Hitmonchan 100

                        
                        int velocidadEnemigo = pokemonEnemigo.getSpeed();


                        //Comprobamos si el pokemon enemigo es mas rapido que el pokemon activo del entrenador 1

                        if(velocidadEnemigo>eq1.getPokemonActivo().getSpeed())
                        {
                            dos.writeBytes("Atacas\n");
                            dos.flush();


                        }

                        else{

                            

                            




                        }



                        




                        

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

