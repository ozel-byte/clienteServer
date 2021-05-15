package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.model.Cliente;
import sample.model.ThreadServerCliente;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {

    @FXML
    private Pane areajuego;

    @FXML
    private ListView<?> listmsj;

    @FXML
    private TextField campomsj;

    @FXML
    private Text nameUser;


    @FXML
    private TextField camponombreuser;
    @FXML
    private Pane fondo;

    @FXML
    private Text valorO1;

    @FXML
    private Text valorX1;

    @FXML
    private Text valorO2;

    @FXML
    private Text valorO5;

    @FXML
    private Text valorO3;

    @FXML
    private Text valorO4;

    @FXML
    private Text valorO8;

    @FXML
    private Text valorO6;

    @FXML
    private Text valorO7;

    @FXML
    private Text valorO9;

    @FXML
    private Text valorX2;

    @FXML
    private Text valorX3;

    @FXML
    private Text valorX6;

    @FXML
    private Text valorX9;

    @FXML
    private Text valorX8;

    @FXML
    private Text valorX7;

    @FXML
    private Text valorX4;

    @FXML
    private Text valorX5;

    @FXML
    private CheckBox checkO;

    @FXML
    private CheckBox checkx;

    @FXML
    private Pane cuadricula1;

    @FXML
    private Pane cuadricula2;

    @FXML
    private Pane cuadricula3;

    @FXML
    private Pane cuadricula5;

    @FXML
    private Pane cuadricula4;

    @FXML
    private Pane cuadricula6;

    @FXML
    private Pane cuadricula7;

    @FXML
    private Pane cuadricula8;

    @FXML
    private Pane cuadricula9;

    @FXML
    private Label msjTurno;


    Integer puntos =0;

    boolean bandera = false;


    @FXML
    private void initialize(){
        listenerPane();
    }

    public void listeningServerMessage(){
        try {
            System.out.println("se inicio para escuchar");
            ServerSocket serverSocket = new ServerSocket(3002);
            new Thread(new ThreadServerCliente(serverSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void iniciarJuego(){
        fondo.setVisible(false);
        nameUser.setText(camponombreuser.getText());

        try {
            Socket socket = new Socket("ip",3001);
            try {
                ObjectOutputStream data = new ObjectOutputStream(socket.getOutputStream());
                Cliente cliente = new Cliente(camponombreuser.getText(),puntos,0.0,0.0,0.0,0.0);
                data.writeObject(cliente);
                data.close();
            }catch (Exception e){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        valorX1.setVisible(true);

        listeningServerMessage();

    }



    public void listenerPane(){
        cuadricula1.setOnMouseClicked(mouseEvent -> {
            System.out.println("llego a esta cuadri1");
            checkBoxListening(valorO1,valorX1,cuadricula1);
        });
        cuadricula2.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO2,valorX2,cuadricula2);
        });
        cuadricula3.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO3,valorX3,cuadricula3);
        });
        cuadricula4.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO4,valorX4,cuadricula4);
        });
        cuadricula5.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO5,valorX5,cuadricula5);
        });
        cuadricula6.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO6,valorX6,cuadricula6);
        });
        cuadricula7.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO7,valorX7,cuadricula7);
        });
        cuadricula8.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO8,valorX8,cuadricula8);
        });
        cuadricula9.setOnMouseClicked(mouseEvent -> {
            checkBoxListening(valorO9,valorX9,cuadricula9);
        });
    }


    public void checkBoxListening(Text valorO, Text valorX,Pane cuadricula){
        if (checkO.isSelected() && !checkx.isSelected()){
            valorO.setVisible(true);
            valorX.setVisible(false);
            cuadricula.setVisible(false);
            try {
                Socket socket = new Socket("ip",3003);
                DataOutputStream dataPosicion = new DataOutputStream(socket.getOutputStream());
                dataPosicion.writeUTF(valorO.getId());
                dataPosicion.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (checkx.isSelected() && !checkO.isSelected()){
            System.out.println("segun if x");
            valorO.setVisible(false);
            valorX.setVisible(true);
            cuadricula.setVisible(false);
            try {
                Socket socket = new Socket("ip",3003);
                DataOutputStream dataPosicion = new DataOutputStream(socket.getOutputStream());
                dataPosicion.writeUTF(valorX.getId());
                dataPosicion.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("error");
        }
    }


}

