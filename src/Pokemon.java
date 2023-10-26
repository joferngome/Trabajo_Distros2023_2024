//Class Pokemon

import java.util.ArrayList;

public class Pokemon {
    private String name;
    
    private int health;
    private int attack;
    private int defense;
    private int speed;
    
    int level;
    int maxHealth;
    private ArrayList<String> types;
    private static int count = 0;
    
    private Attack attack1;
    private Attack attack2;
    private Attack attack3;
    private Attack attack4;


    public Pokemon(String name, int health,int attack ,int defense,int speed, ArrayList<String> types, Attack attack1, Attack attack2, Attack attack3, Attack attack4 ){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.types = types; 
        this.setAttack(attack);
        this.setDefense(defense);
        this.setSpeed(speed);
        
        this.attack1 = attack1;
        this.attack2 = attack2;
        this.attack3 = attack3;
        this.attack4 = attack4;
        
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

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public Attack getAttack1() {
		return attack1;
	}

	public void setAttack1(Attack attack1) {
		this.attack1 = attack1;
	}

	public Attack getAttack3() {
		return attack3;
	}

	public void setAttack3(Attack attack3) {
		this.attack3 = attack3;
	}

	public Attack getAttack2() {
		return attack2;
	}

	public void setAttack2(Attack attack2) {
		this.attack2 = attack2;
	}

	/**
	 * @return the attack4
	 */
	public Attack getAttack4() {
		return attack4;
	}

	/**
	 * @param attack4 the attack4 to set
	 */
	public void setAttack4(Attack attack4) {
		this.attack4 = attack4;
	}
}
