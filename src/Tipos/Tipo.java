package Tipos;

import java.util.ArrayList;

public enum Tipo {

   

    ACERO(1), AGUA(2), BICHO(3), DRAGÓN(4), ELÉCTRICO(5), FANTASMA(6), FUEGO(7), HADA(8), HIELO(9),
     LUCHA(10), NORMAL(11), PLANTA(12), PSÍQUICO(13), ROCA(14), SINIESTRO(15), TIERRA(16), VENENO(17), VOLADOR(18);

    private int numVal;

    Tipo(int numVal) {
        this.numVal = numVal;
    } 
    
    public int getNumVal() {
        return numVal;
    }

   
    public static ArrayList<Tipo> getTipos() {
        ArrayList<Tipo> tipos = new ArrayList<>();
        tipos.add(ACERO);
        tipos.add(AGUA);
        tipos.add(BICHO);
        tipos.add(DRAGÓN);
        tipos.add(ELÉCTRICO);
        tipos.add(FANTASMA);
        tipos.add(FUEGO);
        tipos.add(HADA);
        tipos.add(HIELO);
        tipos.add(LUCHA);
        tipos.add(NORMAL);
        tipos.add(PLANTA);
        tipos.add(PSÍQUICO);
        tipos.add(ROCA);
        tipos.add(SINIESTRO);
        tipos.add(TIERRA);
        tipos.add(VENENO);
        tipos.add(VOLADOR);
        return tipos;
    }





    

   

    
    
}
