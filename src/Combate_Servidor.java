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

                    ArrayList<Tipo> tipos = new ArrayList<Tipo>();
                    tipos.add(Tipo.LUCHA);

                    Pokemon Hitmonchan = new Pokemon("Hitmonchan",600,50,20,10000,
                            tipos,)

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

