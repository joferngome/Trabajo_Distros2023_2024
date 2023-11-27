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
            InterfazGraficaInicio in = new InterfazGraficaInicio(s);
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


            System.out.println("genera equipo cliente " + equipArray.size());

            Equipo_Pokemon eq1 = new Equipo_Pokemon(equipArray);

            oos.writeObject(eq1);
            //Recibe mensaje elige un pokemon para luchar:
            System.out.println((String) ois.readObject());

            for(int i = 0; i < 6; i++){
                //Mostrar los pokemon disponibles
                System.out.print((String) ois.readObject());
            }

            //Elegir numero de Pokemon.Pokemon:

            System.out.println("Introduce el numero del pokemon a elegir: ");
            Scanner s1 = new Scanner(System.in);

            oos.writeObject(s1.nextInt());

            //Falta cuando me envian los pokemon y tengo que elegir un numero.

            while(!salir){

                if(((String)ois.readObject()).equals("Atacas")){

                    //Recibir ataques por ois
                    System.out.println("Ataques: ");
                    
                    for(int i=0;i<4;i++){
                        System.out.println((String)ois.readObject());

                    }
                    System.out.println("Elige un ataque: ");
                    Scanner s2 = new Scanner(System.in);
                    oos.writeObject(s2.nextInt());
                    System.out.println("He atacado");
                }


            }*/


        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    }

