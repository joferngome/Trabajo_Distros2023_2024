//Class Pokemon

import Tipos.Tipo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pokemon implements Serializable {
    private String name;
    
    private int health;
    private int attack;
    private int defense;
    private int speed;
    
    int level;
    int maxHealth;
    private ArrayList<Tipo> types;
    private static int count = 0;
    
    private ArrayList<Attack> attacks = new ArrayList<Attack>();




    public Pokemon(String name, int health, int attack , int defense, int speed, ArrayList<Tipo> types, ArrayList<Attack> attacks ){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.types = types; 

        this.setDefense(defense);
        this.setSpeed(speed);
        this.attacks = attacks;


        
        count++;
    }

    public static Pokemon generar_pokemon(String url){
        try{
            URL url_poke = new URL("https://pokeapi.co/api/v2/pokemon/quagsire");
            HttpURLConnection connection = (HttpURLConnection) url_poke.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String linea = br.readLine();

            br.close();
            connection.disconnect();

            JSONObject pokemon_json = new JSONObject(linea);

            JSONArray tipos_json = pokemon_json.getJSONArray("types");
            ArrayList<Tipo> tipos = new ArrayList<Tipo>();

            for(int i = 0; i < tipos_json.length(); i++) {
                JSONObject tipo = tipos_json.getJSONObject(i).getJSONObject("type");
                tipos.add(Tipo.valueOf(tipo.getString("name")));
            }

            System.out.println(tipos.toString());

            JSONArray stats_json = pokemon_json.getJSONArray("stats");
            int health = stats_json.getJSONObject(0).getInt("base_stat");
            int attack = stats_json.getJSONObject(1).getInt("base_stat");
            int defense = stats_json.getJSONObject(2).getInt("base_stat");
            int spe_attack = stats_json.getJSONObject(3).getInt("base_stat");
            int spe_defense = stats_json.getJSONObject(4).getInt("base_stat");
            int speed = stats_json.getJSONObject(5).getInt("base_stat");

            attack = Math.max(attack, spe_attack);
            defense = Math.max(defense, spe_defense);


            JSONArray ataques_json = pokemon_json.getJSONArray("moves");
            Random r = new Random();
            List<Integer> num_usados = new ArrayList<>();
            ArrayList<Attack> ataques = new ArrayList<Attack>();

            ArrayList<String> urls_ataques = new ArrayList<String>();

            int j = 0;
            while(j < 4){
                int numattack = r.nextInt(ataques_json.length());
                while(num_usados.contains(numattack)){
                    numattack = r.nextInt(ataques_json.length());
                }
                JSONObject ataque = ataques_json.getJSONObject(numattack).getJSONObject("move");

                num_usados.add(numattack);

                URL url_move = new URL(ataque.getString("url"));
                HttpURLConnection connection_move = (HttpURLConnection) url_move.openConnection();
                connection_move.setRequestMethod("GET");

                BufferedReader br_move = new BufferedReader(new InputStreamReader(connection_move.getInputStream()));

                String linea_move = br_move.readLine();

                JSONObject move_json = new JSONObject(linea_move);

                JSONObject move_class_json = move_json.getJSONObject("damage_class");

                String clase_move = move_class_json.getString("name");



                if(!clase_move.equals("status")){
                    ataques.add(new Attack(ataque.getString("name"),
                            move_json.getInt("power"),move_json.getInt("accuracy"),
                            move_json.getInt("pp"),Tipo.valueOf(move_json.getJSONObject("type").getString("name"))));
                    j++;
                }

                br_move.close();
                connection_move.disconnect();
            }
            System.out.println(ataques.toString());

            Pokemon pokemon = new Pokemon("Quagsire",health,attack,defense,speed, tipos, ataques);

            return pokemon;



        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void attackPokemon(Pokemon pokemon){
        pokemon.health -= 10;
    }

    public String getName(){
        return this.name;
    }

    public int getHealth(){
        return this.health;
    }

    public ArrayList<Tipo> getTypes(){
        return this.types;
    }

    public static int getCount(){
        return count;
    }

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setAttacks(ArrayList<Attack> attacks){
		this.attacks = attacks;
	}

	public ArrayList<Attack> getAttacks(){
		return this.attacks;

	}


}
