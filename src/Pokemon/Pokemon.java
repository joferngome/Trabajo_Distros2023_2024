package Pokemon;//Class Pokemon.Pokemon

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import Tipos.Tabla_Tipos_Modificador;
import Tipos.Tipo;

public class Pokemon implements Serializable {
    private String name;
    
    private int health;
    private int attack;
    private int defense;
    private int speed;

    private String sprite_front;
    private String sprite_back;

    int level;
    int maxHealth;
    private ArrayList<Tipo> types;
    private static int count = 0;
    
    private ArrayList<Attack> attacks = new ArrayList<Attack>();




    public Pokemon(String name, int health, int attack , int defense, int speed, ArrayList<Tipo> types, ArrayList<Attack> attacks, int level, String sprite_front, String sprite_back){
        this.name = name;

        this.maxHealth = health;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;

        this.types = types; 
        this.level = level;

        this.attacks = attacks;

        this.sprite_front = sprite_front;
        this.sprite_back = sprite_back;

        count++;
    }

    public static Pokemon generar_pokemon(String url){
        try{
            URL url_poke = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url_poke.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String linea = br.readLine();

            br.close();
            connection.disconnect();

            JSONObject pokemon_json = new JSONObject(linea);

            JSONArray tipos_json = pokemon_json.getJSONArray("types");
            ArrayList<Tipo> tipos = new ArrayList<Tipo>();

            String nombre_poke = pokemon_json.getString("name");
            System.out.println(nombre_poke);
            for(int i = 0; i < tipos_json.length(); i++) {
                JSONObject tipo = tipos_json.getJSONObject(i).getJSONObject("type");
                tipos.add(Tipo.valueOf(tipo.getString("name")));
            }

            System.out.println(tipos.toString());

            //Obtención de Stats

            JSONArray stats_json = pokemon_json.getJSONArray("stats");
            int health = stats_json.getJSONObject(0).getInt("base_stat");
            int attack = stats_json.getJSONObject(1).getInt("base_stat");
            int defense = stats_json.getJSONObject(2).getInt("base_stat");
            int spe_attack = stats_json.getJSONObject(3).getInt("base_stat");
            int spe_defense = stats_json.getJSONObject(4).getInt("base_stat");
            int speed = stats_json.getJSONObject(5).getInt("base_stat");

            int level =  100;

            attack = Math.max(attack, spe_attack);
            defense = Math.max(defense, spe_defense);

            //Recálculo de stats para adecuarlas al nivel 100.

            health = 10 + (health * 2) + level;
            attack = 5 + (attack * 2);
            defense = 5 + (defense * 2);
            speed = 5 + (speed * 2);

            //Obtención de Sprites
            JSONObject sprites_json = pokemon_json.getJSONObject("sprites");

            String back = sprites_json.getString("back_default");
            String front = sprites_json.getString("front_default");


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


                Object poder = move_json.get("power") ;
                Object precision = move_json.get("accuracy") ;

                if(!clase_move.equals("status") && poder != JSONObject.NULL && precision != JSONObject.NULL){
                    ataques.add(new Attack(ataque.getString("name"),
                            move_json.getInt("power"),move_json.getInt("accuracy"),
                            move_json.getInt("pp"),Tipo.valueOf(move_json.getJSONObject("type").getString("name"))));
                    j++;
                }

                br_move.close();
                connection_move.disconnect();
            }

            Pokemon pokemon = new Pokemon(nombre_poke,health,attack,defense,speed, tipos, ataques, level, front, back);

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

    public void setHealth(int health){
        this.health = health;
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

    //Esta vivo

    public boolean isAlive(){

        if(this.health>0){
            return true;
        }

        return false;

    }

    //Atacar

    public int atacar(Pokemon pokemonEnemigo, Attack ataque){

        //Elige un ataque del pokemon activo
        int damage = 0;

        if(ataque.getPower_points() > 0){

            //Comprobamos si el ataque acierta
            
            //sout precision del ataque
            System.out.println("La precision del ataque es: "+ataque.getPrecision());

            if(ataque.getPrecision() >= (int) (Math.random()*100)){

                Tabla_Tipos_Modificador tabla = new Tabla_Tipos_Modificador();

                //Comprobamos efectividad del ataque con tabla tipos

                //Comprueba cuantos tipos tiene el pokemon

                double modificadorTipo = 1;

                for(int i = 0; i < pokemonEnemigo.getTypes().size();i++){
                    modificadorTipo *= tabla.getModificador(ataque.getType().getNumVal()-1, pokemonEnemigo.getTypes().get(i).getNumVal()-1);
                }


                //Calcula el daño con el modificador de tipo

                int dano = (int) ((((((2.0*this.level)/5)+2)*ataque.getPower()*(this.attack/(double)pokemonEnemigo.getDefense()))/50)*modificadorTipo);

                ataque.setPower_points(ataque.getPower_points()-1);

                pokemonEnemigo.setHealth(pokemonEnemigo.getHealth()-dano);
                damage = dano;
            }

            //Ataca al pokemon enemigo
            else{
                System.out.println("El ataque falló");
                ataque.setPower_points(ataque.getPower_points()-1);
            }

        }
        System.out.println("El ataque "+ataque.getName()+ " de "+this.getName()+" ha hecho "+damage+" de daño contra "+pokemonEnemigo.getName()+"\n");
        return damage;
    }

    @Override
    public boolean equals(Object pokemon) {
        if(this.name.equals(((Pokemon) pokemon).name)){
            return true;
        }else{
            return false;
        }
    }
}
