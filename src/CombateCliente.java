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
            URL url_poke = new URL("https://pokeapi.co/api/v2/pokemon/quagsire");
            HttpURLConnection connection = (HttpURLConnection) url_poke.openConnection();
            connection.setRequestMethod("GET");
        			
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
            String linea = br.readLine();
        			
            JSONObject pokemon_json = new JSONObject(linea);
        			
            JSONArray tipos_json = pokemon_json.getJSONArray("types");
            ArrayList<Tipo> tipos = new ArrayList<Tipo>();
                    
            for(int i = 0; i < tipos_json.length(); i++) {
                JSONObject tipo = tipos_json.getJSONObject(i).getJSONObject("type");
                tipos.add(Tipo.valueOf(tipo.getString("name")));
            }

            System.out.println(tipos.toString());

            JSONArray ataques_json = pokemon_json.getJSONArray("moves");
            System.out.println(ataques_json.length());
            Random r = new Random();
            List<Integer> num_usados = new ArrayList<>();

            for(int j = 0; j < 4; j++){
                int numattack = r.nextInt(ataques_json.length());
                while(num_usados.contains(numattack)){
                    numattack = r.nextInt(ataques_json.length());
                }
                JSONObject ataque = ataques_json.getJSONObject(numattack).getJSONObject("move");
                System.out.println(ataque.getString("name"));
                num_usados.add(numattack);
            }
                    
            ArrayList<Attack> ataques = new ArrayList<Attack>();

            ataques.add(new Attack("Roca afilada", 100, 80, 5));
            ataques.add(new Attack("Terremoto", 100, 100, 15));
            ataques.add(new Attack("Acua cola", 90, 95, 15));
            ataques.add(new Attack("Puño hielo", 75, 100, 15));

            Pokemon pokemon = new Pokemon("Quagsire",95,85,85,35, tipos, ataques);

            ArrayList<Pokemon> p = new ArrayList<>();
            p.add(pokemon);
            Equipo_Pokemon equipo = new Equipo_Pokemon(p);


            oos.writeObject(equipo);


                
            


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
