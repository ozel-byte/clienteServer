package sample.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServerCliente implements Runnable{
    ServerSocket server;

    public ThreadServerCliente (ServerSocket srver){
        this.server =srver;
    }


    @Override
    public void run() {

        try {
            while (!server.isClosed()){
                Socket socket = server.accept();
                System.out.println("llego aqui1");
                try{
                    ObjectInputStream dataObject = new ObjectInputStream(socket.getInputStream());
                    dataObject.readObject();
                    dataObject.close();
                }catch (Exception e){
                    dataString(socket);
                }

            }
        } catch (IOException  e) {
            System.out.println(e);
        }

    }


    public void dataString(Socket socket){
        DataInputStream data  = null;
        try {
            data = new DataInputStream(socket.getInputStream());
            System.out.println(data.readUTF());
            System.out.println("llego aqui 2");
            data.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
