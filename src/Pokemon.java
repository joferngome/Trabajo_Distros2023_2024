//Class Pokemon

import Tipos.Tipo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
