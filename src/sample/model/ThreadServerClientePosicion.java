package sample.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class ThreadServerClientePosicion extends Observable implements Runnable{

    ServerSocket  serverSocket;

    public ThreadServerClientePosicion(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()){
            try {
                Socket socket = serverSocket.accept();
                setChanged();
                notifyObservers(socket);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
