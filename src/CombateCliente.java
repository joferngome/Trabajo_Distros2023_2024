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
            Pokemon pok1 = Pokemon.generar_pokemon("https://pokeapi.co/api/v2/pokemon/quagsire");
            ArrayList<Pokemon> equipArray = new ArrayList<>();
            equipArray.add(pok1);

            Equipo_Pokemon eq1 = new Equipo_Pokemon(equipArray);

            oos.writeObject(eq1);

            //Falta cuando me envian los pokemon y tengo que elegir un numero.


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
