package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(5500)){
            while(true){

                try(Socket s = serverSocket.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));){

                    String line = br.readLine();
                    System.out.println(line);
                    


                    
                }
                
            }

      
        

        
    }catch(IOException e){
        e.printStackTrace();

    }
    
    
    
}
}

