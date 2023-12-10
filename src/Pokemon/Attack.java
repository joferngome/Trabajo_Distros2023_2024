package Pokemon;

import Tipos.Tipo;

import java.io.Serializable;

public class Attack implements Serializable {
    private String name;
	 private int power;
	 private int precision;
	 private int power_points;

	private Tipo type;

	public Attack(String name, int power, int precision, int power_points, Tipo type){
		this.name = name;
		this.power = power;
		this.precision = precision;
		this.power_points = power_points;
		this.type = type;
	}
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getPower_points() {
		return power_points;
	}
	public void setPower_points(int power_points) {
		this.power_points = power_points;
	}

	public Tipo getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return this.name +  " pw: " + this.power + " pp: " + this.power_points + " prec: " + this.precision + " ("+ this.type.name() +")";
	}
}
