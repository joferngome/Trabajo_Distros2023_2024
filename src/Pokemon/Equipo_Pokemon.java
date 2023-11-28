package Pokemon;

import Pokemon.Pokemon;

import java.io.Serializable;
import java.util.*;

public class Equipo_Pokemon implements Serializable {

    private ArrayList<Pokemon> equipo = new ArrayList<>();
    private Pokemon pokemonActivo;
    private final int numPokemon = 1;

    public Equipo_Pokemon(ArrayList<Pokemon> equipo) {
        this.equipo = equipo;


    }

    public boolean AllDead(){

        for (Pokemon pokemon : equipo) {

            if(pokemon.getHealth()>0){
                return false;

            }
        }
        return true;
    }

    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    public ArrayList<Pokemon> getEquipoDisponible() {
        ArrayList<Pokemon> equipo_disponible = new ArrayList<>();

        for (Pokemon pokemon : equipo) {
            if(pokemonActivo != null){
                if(pokemon.getHealth() > 0 && !pokemonActivo.equals(pokemon)){
                    equipo_disponible.add(pokemon);
                }
            }else{
                if(pokemon.getHealth() > 0){
                    equipo_disponible.add(pokemon);
                }
            }

        }
        return equipo_disponible;
    }

    //Set Pokemon.Pokemon activo

    public void setPokemonActivo(Pokemon pokemonActivo) {
        this.pokemonActivo = pokemonActivo;
    }

    public Pokemon getPokemonActivo() {
        return pokemonActivo;
    }

    public static Equipo_Pokemon generar_equipo(){
        Random r = new Random();
        ArrayList<Pokemon> equipArray = new ArrayList<>();
        List<Integer> baneados = Arrays.asList(new Integer[]{10,11,13,14,129,132,201,202,235,265,266,268,292,360, 664,665,771,789,790,824,825});
        List<Integer> num_usados = new ArrayList<>();

        for(int i = 0; i < 6; i++ ) {
            int numpoke = r.nextInt(1017);
            while (num_usados.contains(numpoke) || baneados.contains(numpoke)) {
                numpoke = r.nextInt(1017);
            }
            System.out.println(numpoke);
            Pokemon pok1 = Pokemon.generar_pokemon("https://pokeapi.co/api/v2/pokemon/" + numpoke);
            equipArray.add(pok1);
            num_usados.add(numpoke);
        }
        return new Equipo_Pokemon(equipArray);
    }

    public Pokemon elegir_pokemon(){
        //Elegir el pokemon con el que abrir el combate

        ArrayList<Pokemon> equipoDisponible = this.getEquipoDisponible();
        System.out.println("Elige un pokemon aliado para luchar: ");

        for (int i = 0; i < equipoDisponible.size(); i++) {

            System.out.println((i + 1) + " " + equipoDisponible.get(i).getName());
        }

        Scanner s1 = new Scanner(System.in);

        this.setPokemonActivo(this.getEquipoDisponible().get(s1.nextInt()-1));

        System.out.println("Has elegido a " + this.getPokemonActivo().getName());

        return this.getPokemonActivo();
    }

    public int[] elegir_accion(){
        //Elegir el pokemon con el que abrir el combate

        ArrayList<Pokemon> equipoDisponible = this.getEquipoDisponible();
        System.out.println("Pokemon disponibles");
        for (int i = 0; i < equipoDisponible.size(); i++) {
            System.out.print(equipoDisponible.get(i).getName() + ",");
        }
        System.out.println("\n ¿Qué quieres hacer?");
        System.out.println("1.Atacar");
        System.out.println("2.Cambiar");
        System.out.println("3.Rendirse");
        int opcion_turno = 0;
        int eleccion = 0;
        Scanner s1 = new Scanner(System.in);

        opcion_turno  = s1.nextInt();

        if(opcion_turno == 1){
            System.out.println("Elige el movimiento");
            System.out.println("0. " + pokemonActivo.getAttacks().get(0).getName());
            System.out.println("1. " + pokemonActivo.getAttacks().get(1).getName());
            System.out.println("2. " + pokemonActivo.getAttacks().get(2).getName());
            System.out.println("3. " + pokemonActivo.getAttacks().get(3).getName());
            eleccion  = s1.nextInt();
        }else if(opcion_turno == 2){
            eleccion = this.equipo.indexOf(this.elegir_pokemon());
        }

        int [] opciones = {opcion_turno,eleccion};

        return opciones;
    }


    
}
