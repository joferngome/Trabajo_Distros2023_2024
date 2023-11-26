
package Tipos;

public class Tabla_Tipos_Modificador{

    
    private double[][] tablaTipos = new double[18][18];

    public Tabla_Tipos_Modificador(){
    //Tipo ataque y tipo del oponente. En ese orden.
    //Tabla de tipos. Primera dimensión: tipo del ataque. Segunda dimensión: tipo del oponente.
    tablaTipos[Tipo.steel.getNumVal()-1] = new double[]{0.5, 0.5, 1, 1, 0.5, 1, 0.5, 2, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1};
    tablaTipos[Tipo.water.getNumVal()-1] = new double[]{1, 0.5, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 0.5, 1, 2, 1, 2, 1, 1};
    tablaTipos[Tipo.bug.getNumVal()-1] = new double[]{0.5, 1, 1, 1, 1, 0.5, 0.5, 0.5, 1, 0.5, 1, 2, 2, 1, 2, 1, 0.5, 0.5};
    tablaTipos[Tipo.dragon.getNumVal()-1] = new double[]{0.5, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    tablaTipos[Tipo.electric.getNumVal()-1] = new double[]{1, 2, 1, 0.5, 0.5, 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 0, 1, 2};
    tablaTipos[Tipo.ghost.getNumVal()-1] = new double[]{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 1, 2, 1, 0.5, 1, 1, 1};
    tablaTipos[Tipo.fire.getNumVal()-1] = new double[]{2, 0.5, 2, 0.5, 1, 1, 0.5, 1, 2, 1, 1, 2, 1, 0.5, 1, 1, 1, 1};
    tablaTipos[Tipo.fairy.getNumVal()-1] = new double[]{0.5, 1, 1, 2, 1, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 2, 1, 0.5, 1};
    tablaTipos[Tipo.ice.getNumVal()-1] = new double[]{0.5, 0.5, 1, 2, 1, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 1, 1, 2, 1, 2};
    tablaTipos[Tipo.fighting.getNumVal()-1] = new double[]{2, 1, 0.5, 1, 1, 0, 1, 0.5, 2, 1, 2, 1, 0.5, 2, 2, 1, 0.5, 0.5};
    tablaTipos[Tipo.normal.getNumVal()-1] = new double[]{0.5, 1, 1, 1, 1, 0, 1, 1, 1, 2, 1, 1, 1, 0.5, 1, 1, 1, 1};
    tablaTipos[Tipo.grass.getNumVal()-1] = new double[]{0.5, 2, 0.5, 0.5, 1, 1, 0.5, 1, 1, 1, 1, 0.5, 1, 2, 1, 2, 0.5, 0.5};
    tablaTipos[Tipo.psychic.getNumVal()-1] = new double[]{0.5, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0.5, 1, 0, 1, 2, 1};
    tablaTipos[Tipo.rock.getNumVal()-1] = new double[]{0.5, 1, 2, 1, 1, 1, 2, 1, 2, 0.5, 1, 1, 1, 1, 1, 0.5, 1, 2};
    tablaTipos[Tipo.dark.getNumVal()-1] = new double[]{1, 1, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 0.5, 1, 1, 1};
    tablaTipos[Tipo.ground.getNumVal()-1] = new double[]{2, 1, 0.5, 1, 2, 1, 2, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 2, 0};
    tablaTipos[Tipo.poison.getNumVal()-1] = new double[]{0, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 0.5, 2};
    tablaTipos[Tipo.flying.getNumVal()-1] = new double[]{0.5, 1, 2, 1, 0.5, 1, 1, 1, 1, 2, 1, 2, 1, 0.5, 1, 1, 1, 1};

    }

    public double getModificador(int tipoAtacante, int tipoOponente) {

        return tablaTipos[tipoAtacante][tipoOponente];
    }

    //  Si un pokemon tiene dos tipos que devuelva el modificador más alto de los dos tipos.

    public double getModificador(int tipoAtacante, int tipoOponente1, int tipoOponente2) {

        double modificador1 = tablaTipos[tipoAtacante][tipoOponente1];
        double modificador2 = tablaTipos[tipoAtacante][tipoOponente2];

        if(modificador1>modificador2){
            return modificador1;
        }
        else{
            return modificador2;
        }

    }


    



        



    
}