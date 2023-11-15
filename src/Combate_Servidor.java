import Tipos.Tipo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Combate_Servidor {


    public static void main(String args[]){

        try(ServerSocket ss = new ServerSocket(6666)){


            while(true){

                try(Socket rival = ss.accept();
                    ObjectOutputStream oos = new ObjectOutputStream(rival.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(rival.getInputStream())){

                    ArrayList<Pokemon> pok1 = new ArrayList<>();




                    ArrayList<Tipo> tipos = new ArrayList<Tipo>();

                    tipos.add(Tipo.fighting);
                    
                    


                    ArrayList<Attack> ataquesHitmonchan = new ArrayList<>();
                    Attack a1 = new Attack("Patada giro",100,100,5);
                    Attack a2 = new Attack("Roca afilada",80,100,5);
                    Attack a3 = new Attack("Puño hielo",75,100,5);
                    Attack a4 = new Attack("Puño trueno",75,100,5);
                    ataquesHitmonchan.add(a1);
                    ataquesHitmonchan.add(a2);
                    ataquesHitmonchan.add(a3);
                    ataquesHitmonchan.add(a4);


                    Pokemon Hitmonchan = new Pokemon("Hitmonchan",600,50,20,10000,
                            tipos, ataquesHitmonchan);

                    pok1.add(Hitmonchan);

                    Equipo_Pokemon eq1 = new Equipo_Pokemon(pok1);


                    Equipo_Pokemon Eqrival = (Equipo_Pokemon) ois.readObject();

                    while(!eq1.AllDead() && !Eqrival.AllDead()){

                        

                    }





                }

                catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

