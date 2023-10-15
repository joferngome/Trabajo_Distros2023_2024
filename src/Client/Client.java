package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public static void main (String[] args) {
        // TODO implement here
        try(Socket s = new Socket("localhost", 5500);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));){

            bw.write("Hello from client");
            bw.flush();

            

            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
}
