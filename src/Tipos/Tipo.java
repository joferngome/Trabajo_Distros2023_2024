package Tipos;

import java.util.ArrayList;

public enum Tipo {

   

    steel(1), water(2), bug(3), dragon(4), electric(5), ghost(6), fire(7), fairy(8), ice(9),
     fighting(10), normal(11), grass(12), psychic(13), rock(14), dark(15), ground(16), poison(17), flying(18);

    private int numVal;

    Tipo(int numVal) {
        this.numVal = numVal;
    } 
    
    public int getNumVal() {
        return numVal;
    }

   
    public static ArrayList<Tipo> getTipos() {
        ArrayList<Tipo> tipos = new ArrayList<>();
        tipos.add(steel);
        tipos.add(water);
        tipos.add(bug);
        tipos.add(dragon);
        tipos.add(electric);
        tipos.add(ghost);
        tipos.add(fire);
        tipos.add(fairy);
        tipos.add(ice);
        tipos.add(fighting);
        tipos.add(normal);
        tipos.add(grass);
        tipos.add(psychic);
        tipos.add(rock);
        tipos.add(dark);
        tipos.add(ground);
        tipos.add(poison);
        tipos.add(flying);
        return tipos;
    }





    

   

    
    
}
