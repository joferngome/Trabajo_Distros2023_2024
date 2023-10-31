import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Combate {


    public static void main(String args[]){

        try(ServerSocket ss = new ServerSocket(6666)){

            while(true){

                try(Socket rival = ss.accept();
                    ObjectOutputStream oos = new ObjectOutputStream(rival.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(rival.getInputStream())){

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
