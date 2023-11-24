import Tipos.Tipo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class CombateCliente {

    public static void main(String args[]){
        System.out.println("cliente main");
        try(Socket s = new Socket("localhost",6666);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream()))
        {

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


            System.out.println("genera equipo cliente " + equipArray.size());

            Equipo_Pokemon eq1 = new Equipo_Pokemon(equipArray);

            oos.writeObject(eq1);
            System.out.println("ha mandado equipo");
            //Recibe mensaje elige un pokemon para luchar:
            System.out.println("espera elegir poke");
            System.out.println((String) ois.readObject());
            System.out.println("recibe elegir poke");

            for(int i = 0; i < 6; i++){ //lo he cambiado a 1 por simplificar
                //Mostrar los pokemon disponibles
                System.out.println((String) ois.readObject());
                System.out.println("elegir poke");
            }

            //Elegir numero de Pokemon:

            System.out.println("Introduce el numero del pokemon a elegir: ");
            Scanner s1 = new Scanner(System.in);

            oos.writeObject(s1.nextInt());

            //Falta cuando me envian los pokemon y tengo que elegir un numero.


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
