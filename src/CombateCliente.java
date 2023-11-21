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

import org.json.JSONArray;
import org.json.JSONObject;

public class CombateCliente {

    public static void main(String args[]){

        try(Socket s = new Socket("localhost",6666);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream()))
        {
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

            System.out.println("genera equipo cliente" + equipArray.size());

            Equipo_Pokemon eq1 = new Equipo_Pokemon(equipArray);

            oos.writeObject(eq1);

            //Falta cuando me envian los pokemon y tengo que elegir un numero.


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
