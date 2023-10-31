import java.io.Serializable;
import java.util.ArrayList;

public class Equipo_Pokemon implements Serializable {

    private ArrayList<Pokemon> equipo = new ArrayList<>();
    private final int numPokemon = 1;

    public Equipo_Pokemon(ArrayList<Pokemon> equipo) {
        this.equipo = equipo;


    }


    
}
