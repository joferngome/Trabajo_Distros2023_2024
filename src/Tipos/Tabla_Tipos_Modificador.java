

public class Tabla_Tipos_Modificador{

    public double[][] getTablaTipos(){

        double[][] tablaTipos = new double[18][18];

        //Tipo ataque y tipo del oponente. En ese orden.
    //Tabla de tipos. Primera dimensión: tipo del ataque. Segunda dimensión: tipo del oponente.
    tablaTipos[Tipo.ACERO.getNumVal()] = new double[]{0.5, 0.5, 1, 1, 0.5, 1, 0.5, 2, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1};
    tablaTipos[Tipo.AGUA.getNumVal()] = new double[]{1, 0.5, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 0.5, 1, 2, 1, 2, 1, 1};
    tablaTipos[Tipo.BICHO.getNumVal()] = new double[]{0.5, 1, 1, 1, 1, 0.5, 0.5, 0.5, 1, 0.5, 1, 2, 2, 1, 2, 1, 0.5, 0.5};
    tablaTipos[Tipo.DRAGÓN.getNumVal()] = new double[]{0.5, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    tablaTipos[Tipo.ELÉCTRICO.getNumVal()] = new double[]{1, 2, 1, 0.5, 0.5, 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 0, 1, 2};
    tablaTipos[Tipo.FANTASMA.getNumVal()] = new double[]{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 1, 2, 1, 0.5, 1, 1, 1};
    tablaTipos[Tipo.FUEGO.getNumVal()] = new double[]{2, 0.5, 2, 0.5, 1, 1, 0.5, 1, 2, 1, 1, 2, 1, 0.5, 1, 1, 1, 1};
    tablaTipos[Tipo.HADA.getNumVal()] = new double[]{0.5, 1, 1, 2, 1, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 2, 1, 0.5, 1};
    tablaTipos[Tipo.HIELO.getNumVal()] = new double[]{0.5, 0.5, 1, 2, 1, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 1, 1, 2, 1, 2};
    tablaTipos[Tipo.LUCHA.getNumVal()] = new double[]{2, 1, 0.5, 1, 1, 0, 1, 0.5, 2, 1, 2, 1, 0.5, 2, 2, 1, 0.5, 0.5};
    tablaTipos[Tipo.NORMAL.getNumVal()] = new double[]{0.5, 1, 1, 1, 1, 0, 1, 1, 1, 2, 1, 1, 1, 0.5, 1, 1, 1, 1};
    tablaTipos[Tipo.PLANTA.getNumVal()] = new double[]{0.5, 2, 0.5, 0.5, 1, 1, 0.5, 1, 1, 1, 1, 0.5, 1, 2, 1, 2, 0.5, 0.5};
    tablaTipos[Tipo.PSÍQUICO.getNumVal()] = new double[]{0.5, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0.5, 1, 0, 1, 2, 1};
    tablaTipos[Tipo.ROCA.getNumVal()] = new double[]{0.5, 1, 2, 1, 1, 1, 2, 1, 2, 0.5, 1, 1, 1, 1, 1, 0.5, 1, 2};
    tablaTipos[Tipo.SINIESTRO.getNumVal()] = new double[]{1, 1, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 0.5, 1, 1, 1};
    tablaTipos[Tipo.TIERRA.getNumVal()] = new double[]{2, 1, 0.5, 1, 2, 1, 2, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 2, 0};
    tablaTipos[Tipo.VENENO.getNumVal()] = new double[]{0, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 0.5, 2};
    tablaTipos[Tipo.VOLADOR.getNumVal()] = new double[]{0.5, 1, 2, 1, 0.5, 1, 1, 1, 1, 2, 1, 2, 1, 0.5, 1, 1, 1, 1};

    return tablaTipos;

        
    }
    



        



    
}