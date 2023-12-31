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

    private int level;
    private int maxHealth;
    private ArrayList<Tipo> types;
    private ArrayList<Attack> attacks = new ArrayList<Attack>();

    //PRE: Recibe el nombre del pokemon no nulo , su vida >0 , su ataque >0, su defensa >0, su velocidad>0, sus tipos no nulos, sus ataques no nulos y su nivel >=1.
    //POST: Crea un pokemon con los datos recibidos.

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
    }

    //PRE: Recibe un string con la url valida de un pokemon
    //POST: Devuelve un pokemon con los datos obtenidos de la url

    public static Pokemon generar_pokemon(String url){
        try{
            URL url_poke = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url_poke.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //Leemos una línea porque el resultado del json está en una sola línea
            String linea = br.readLine();

            br.close();
            connection.disconnect();

            //Obtención del objeto JSON a partir de la linea leida.
            JSONObject pokemon_json = new JSONObject(linea);
            String nombre_poke = pokemon_json.getString("name");

            //Obtención de Tipos
            JSONArray tipos_json = pokemon_json.getJSONArray("types");
            ArrayList<Tipo> tipos = new ArrayList<Tipo>();
            for(int i = 0; i < tipos_json.length(); i++) {
                JSONObject tipo = tipos_json.getJSONObject(i).getJSONObject("type");
                tipos.add(Tipo.valueOf(tipo.getString("name")));
            }

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

            //Recálculo de stats para adecuarlas al nivel 100
            health = 10 + (health * 2) + level;
            attack = 5 + (attack * 2);
            defense = 5 + (defense * 2);
            speed = 5 + (speed * 2);

            //Obtención de Sprites, por si hacemos interfaz gráfica
            JSONObject sprites_json = pokemon_json.getJSONObject("sprites");
            Object back_o = sprites_json.get("back_default") ;
            Object front_o = sprites_json.get("front_default") ;
            String back = "", front = "";
            if(back_o != JSONObject.NULL){
                back = (String) back_o;
            }
            if(front_o != JSONObject.NULL){
                front = (String) front_o;
            }

            //Obtención de movimientos
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

                //Obtener los datos de cada movimiento, desde el JSON, igual que antes
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
                    //Solo aumentamos el nª de ataques si el ataque no es de estado, hace daño fijo, y tiene precision fija.
                    //Si no se genera otro ataque distinto
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

    public String getName(){
        return this.name;
    }

    public int getHealth(){
        return this.health;
    }

    public ArrayList<Tipo> getTypes(){
        return this.types;
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

    public int getMaxHealth(){
        return this.maxHealth;
    }
    public int getLevel(){
        return this.level;
    }
    public String getSprite_front() {
        return sprite_front;
    }

    public void setSprite_front(String sprite_front) {
        this.sprite_front = sprite_front;
    }

    public String getSprite_back() {
        return sprite_back;
    }

    public void setSprite_back(String sprite_back) {
        this.sprite_back = sprite_back;
    }

    //PRE
    //POST: Devuelve el porcentaje de vida del pokemon

    public int getHealthPercentage(){
        if(this.isAlive()){
            return Math.round(this.health * 100/this.maxHealth) ;
        }else{
            return 0;
        }

    }

	public ArrayList<Attack> getAttacks(){
		return this.attacks;

	}

    //Esta vivo
    //PRE
    //POST: Devuelve true si el pokemon esta vivo

    public boolean isAlive(){

        if(this.health>0){
            return true;
        }

        return false;

    }

    //PRE: Recibe un pokemon no nulo y un ataque no nulo
    //POST: Ataca al pokemon recibido con el ataque recibido y devuelve el daño hecho


    //Atacar
    public int atacar(Pokemon pokemonEnemigo, Attack ataque){

        int damage = 0;
        int porVida = pokemonEnemigo.getHealthPercentage();

        //Comprueba si quedan puntos de poder para usar el ataque
        if(ataque.getPower_points() > 0){

            //Genera un numero aleatorio para decidir si el ataque falla o no
            if(ataque.getPrecision() >= (int) (Math.random()*100)){

                Tabla_Tipos_Modificador tabla = new Tabla_Tipos_Modificador();

                //Comprobamos efectividad del ataque con tabla tipos

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
            else{
                System.out.println("El ataque falló");
                ataque.setPower_points(ataque.getPower_points()-1);
            }

        }
        int Porcevidaquitada =  porVida - pokemonEnemigo.getHealthPercentage();
        System.out.println("El ataque "+ataque.getName()+ " de "+this.getName()+" ha hecho "+Porcevidaquitada+" % de daño contra "+pokemonEnemigo.getName()+"\n");

        return Porcevidaquitada;
    }

    //PRE: Recibe un pokemon no nulo
    //POST: Devuelve true si el pokemon recibido es igual a este pokemon

    @Override
    public boolean equals(Object pokemon) {
        if(this.name.equals(((Pokemon) pokemon).name)){
            return true;
        }else{
            return false;
        }
    }

    //PRE
    //POST: Devuelve un string con los datos del pokemon

    @Override
    public String toString() {
        String tipos = "[";
        for(int i = 0; i < this.types.size(); i++){
            if(i == this.types.size() - 1){
                tipos += this.types.get(i).name();
            }else{
                tipos += this.types.get(i).name() + " | ";
            }
        }
        tipos += "]";
        return this.name +  " (" + this.getHealthPercentage() + "%) " + tipos;
    }
}
