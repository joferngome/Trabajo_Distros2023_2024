import Tipos.Tipo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CombateCliente {

    public static void main(String args[]){

        try(Socket s = new Socket("localhost",6666);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream())){

            

                
                    ArrayList<Tipo> tipos = new ArrayList<Tipo>();

                    tipos.add(Tipo.AGUA);
                    tipos.add(Tipo.TIERRA);

                    ArrayList<Attack> ataques = new ArrayList<Attack>();

                    ataques.add(new Attack("Roca afilada", 100, 80, 5));
                    ataques.add(new Attack("Terremoto", 100, 100, 15));
                    ataques.add(new Attack("Acua cola", 90, 95, 15));
                    ataques.add(new Attack("Puño hielo", 75, 100, 15));

                    Pokemon pokemon = new Pokemon("Quagsire",95,85,85,35, tipos, ataques);

                    ArrayList<Pokemon> p = new ArrayList<>();
                    p.add(pokemon);
                    Equipo_Pokemon equipo = new Equipo_Pokemon(p);


                    oos.writeObject(equipo);


                
            


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
