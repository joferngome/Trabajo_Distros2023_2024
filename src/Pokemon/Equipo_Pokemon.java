package Pokemon;

import Pokemon.Pokemon;

import java.io.Serializable;
import java.util.*;

public class Equipo_Pokemon implements Serializable {

    private ArrayList<Pokemon> equipo = new ArrayList<>();
    private Pokemon pokemonActivo;

    //PRE: Recibe un arraylist de pokemons no nulo
    //POST: Crea un equipo de pokemon con los pokemons recibidos

    public Equipo_Pokemon(ArrayList<Pokemon> equipo) {
        this.equipo = equipo;
    }

    //PRE:
    //POST: Devuelve true si todos los pokemons del equipo estan muertos

    public boolean AllDead(){
        for (Pokemon pokemon : equipo) {
            if(pokemon.getHealth() > 0){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    //PRE: 
    //POST: Devuelve un arraylist con los pokemons del equipo que estan vivos y no son el pokemon activo

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

    public void setPokemonActivo(Pokemon pokemonActivo) {
        this.pokemonActivo = pokemonActivo;
    }

    public Pokemon getPokemonActivo() {
        return pokemonActivo;
    }

    //PRE
    //POST: Devuelve un equipo de pokemon aleatorio

    public static Equipo_Pokemon generar_equipo(){
        Random r = new Random();
        ArrayList<Pokemon> equipArray = new ArrayList<>();
        List<Integer> baneados = Arrays.asList(new Integer[]{10,11,13,14,129,132,201,202,235,265,266,268,292,360, 664,665,771,789,790,824,825});
        List<Integer> num_usados = new ArrayList<>();

        for(int i = 0; i < 6; i++ ) {
            int numpoke = r.nextInt(1,1017);
            while (num_usados.contains(numpoke) || baneados.contains(numpoke)) {
                numpoke = r.nextInt(1,1017);
            }

            Pokemon pok1 = Pokemon.generar_pokemon("https://pokeapi.co/api/v2/pokemon/" + numpoke);
            equipArray.add(pok1);
            num_usados.add(numpoke);
            System.out.println(i+1 + ". " +pok1.getName());
        }
        return new Equipo_Pokemon(equipArray);
    }

    //PRE:
    //POST: Elige el pokemon del equipo con el que se va a abrir el combate

    public Pokemon elegir_pokemon(){
        //Elegir el pokemon con el que abrir el combate

        ArrayList<Pokemon> equipoDisponible = this.getEquipoDisponible();
        if(equipoDisponible.size() == 0){
            return null;
        }
        System.out.println("Elige un pokemon aliado para luchar: ");

        for (int i = 0; i < equipoDisponible.size(); i++) {
            System.out.println((i + 1) + " " + equipoDisponible.get(i));
        }

        Scanner s1 = new Scanner(System.in);

        this.setPokemonActivo(this.getEquipoDisponible().get(s1.nextInt()-1));

        System.out.println("Has elegido a " + this.getPokemonActivo().getName());

        return this.getPokemonActivo();
    }

    //PRE:
    //POST: Elige la accion que se va a realizar en el turno

    public int[] elegir_accion(){
        //Elegir la accion en los turnos

        ArrayList<Pokemon> equipoDisponible = this.getEquipoDisponible();
        System.out.println("Pokemon activo: " + this.getPokemonActivo());
        System.out.println("Pokemon disponibles");
        for (int i = 0; i < equipoDisponible.size(); i++) {
            if(i == equipoDisponible.size() - 1){
                System.out.print(equipoDisponible.get(i));
            }
            else{
                System.out.print(equipoDisponible.get(i) +  " | ");
            }
        }
        System.out.println("\n ¿Qué quieres hacer?");
        System.out.println("1.Atacar");
        System.out.println("2.Cambiar");
        System.out.println("3.Rendirse");

        int opcion_turno = 0;
        int eleccion = 0;

        Scanner s1 = new Scanner(System.in);

        opcion_turno  = s1.nextInt();

        while(opcion_turno < 1 || opcion_turno > 3){
            System.out.println("Elige una opcion válida");
            System.out.println("1.Atacar");
            System.out.println("2.Cambiar");
            System.out.println("3.Rendirse");
            opcion_turno  = s1.nextInt();
        }

        if(opcion_turno == 1){
            System.out.println("Elige el movimiento");
            for (int i = 0; i < pokemonActivo.getAttacks().size(); i++) {
                System.out.println((i + 1) + " " + pokemonActivo.getAttacks().get(i).toString());
            }
            eleccion  = s1.nextInt() - 1;
        }else if(opcion_turno == 2){
            eleccion = this.equipo.indexOf(this.elegir_pokemon());
        }

        int [] opciones = {opcion_turno,eleccion};

        return opciones;
    }
}
