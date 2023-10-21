//Class Pokemon

import java.util.ArrayList;

public class Pokemon {
    private String name;
    private int health;
    int level;
    int maxHealth;
    private ArrayList<String> types;
    private static int count = 0;
    private ArrayList<Attack> attacks;


    public Pokemon(String name, int health, ArrayList<String> types){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.types = types;
        count++;
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

    public ArrayList<String> getTypes(){
        return this.types;
    }

    public static int getCount(){
        return count;
    }
}