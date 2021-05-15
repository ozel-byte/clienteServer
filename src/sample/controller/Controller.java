package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.model.Cliente;
import sample.model.ThreadServerCliente;
import sample.model.ThreadServerClientePosicion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML
    private Pane areajuego;

    @FXML
    private Text ganadosUser;

    @FXML
    private Button botonFinalizarCurso;

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

    @FXML
    private Pane fondomsjganador;


    Integer puntos =0;

    boolean bandera = false;

    Integer auxTurnos = 0;

    ArrayList<Integer> casillas = new ArrayList<Integer>();


    @FXML
    private void initialize(){
        listenerPane();
        listeningMessagePosicion();
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

    public void listeningMessagePosicion(){
        try {
            ServerSocket serverSocket = new ServerSocket(3004);
            ThreadServerClientePosicion threadServerClientePosicion = new ThreadServerClientePosicion(serverSocket);
            threadServerClientePosicion.addObserver(this);
            new Thread(threadServerClientePosicion).start();
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


    public void checkPaneRemote(String valor){
        msjTurno.setText("Tu turno");
        msjTurno.setStyle("-fx-background-color: green");
        Integer contador=0;
        for (int i=0; i<areajuego.getChildren().size(); i++){
            if (areajuego.getChildren().get(i) instanceof Text){
                if (valor.equals(areajuego.getChildren().get(i).getId())){
                    String nombreaux = "cuadricula"+valor.charAt(valor.length()-1);
                    System.out.println(nombreaux);
                    while (contador < areajuego.getChildren().size()){
                        if (areajuego.getChildren().get(contador) instanceof Pane){
                            if (areajuego.getChildren().get(contador).getId().equals(nombreaux)){
                                areajuego.getChildren().get(contador).setVisible(false);
                                areajuego.getChildren().get(i).setVisible(true);
                                msjTurno.setVisible(true);
                                if (bandera){
                                    checkx.setDisable(false);
                                }else{
                                    checkO.setDisable(false);
                                }
                            }
                        }
                        contador++;
                    }

                }
            }
        }
    }


    public void checkBoxListening(Text valorO, Text valorX,Pane cuadricula){
        if (auxTurnos<5){
            if (checkO.isSelected() && !checkx.isSelected()){
                valorO.setVisible(true);
                valorX.setVisible(false);
                cuadricula.setVisible(false);
                bandera=false;
                try {
                    Socket socket = new Socket("ip",3003);
                    DataOutputStream dataPosicion = new DataOutputStream(socket.getOutputStream());
                    dataPosicion.writeUTF(valorO.getId());
                    Integer idvalor = Integer.parseInt(String.valueOf(valorX.getId().charAt(valorX.getId().length()-1)));
                    casillas.add(idvalor);
                    dataPosicion.close();
                    msjTurno.setText("Tu Turno acabo");
                    botonFinalizarCurso.setDisable(true);
                    msjTurno.setStyle("-fx-background-color:  #C70039 ");
                    auxTurnos++;
                    bloquearcheckBox();
                    verificarCasillas();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if (checkx.isSelected() && !checkO.isSelected()){
                System.out.println("segun if x");
                valorO.setVisible(false);
                valorX.setVisible(true);
                cuadricula.setVisible(false);
                bandera=true;
                try {
                    Socket socket = new Socket("192.168.0.11",3003);
                    DataOutputStream dataPosicion = new DataOutputStream(socket.getOutputStream());
                    dataPosicion.writeUTF(valorX.getId());
                    Integer idvalor = Integer.parseInt(String.valueOf(valorX.getId().charAt(valorX.getId().length()-1)));
                    casillas.add(idvalor);
                    dataPosicion.close();
                    botonFinalizarCurso.setDisable(true);
                    bloquearcheckBox();
                    msjTurno.setText("Tu Turno acabo");
                    msjTurno.setStyle("-fx-background-color:  #C70039 ");
                    auxTurnos++;
                    verificarCasillas();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("error");
            }
        }else{

        }
    }


    public void verificarCasillas(){
        if (auxTurnos==3){
            casillas.sort((t1, t2) -> t1.compareTo(t2));
            for (int i=0; i<casillas.size(); i++){
                System.out.println(casillas.get(i));
                if (casillas.get(i) == 1 && casillas.get(i+1) == 2 && casillas.get(casillas.size()-1) ==3){
                    mostrarGanador();
                   break;
                }else if(casillas.get(i) == 4 && casillas.get(i+1) == 5 && casillas.get(casillas.size()-1)==6){
                    mostrarGanador();
                    break;
                }else if(casillas.get(i) == 7 && casillas.get(i+1) == 8 && casillas.get(casillas.size()-1)==9){
                    mostrarGanador();
                    break;
                }else if(casillas.get(i) == 2 && casillas.get(i+1) == 5 && casillas.get(casillas.size()-1)==8){
                    mostrarGanador();
                    break;
                }else if(casillas.get(i) == 3 && casillas.get(i+1) == 6 && casillas.get(casillas.size()-1)==9){
                    mostrarGanador();
                    break;
                }else if(casillas.get(i) == 1 && casillas.get(i+1) == 5 && casillas.get(casillas.size()-1)==9){
                    mostrarGanador();
                    break;
                }else if(casillas.get(i) == 3 && casillas.get(i+1) == 5 && casillas.get(casillas.size()-1)==7){
                    mostrarGanador();
                    break;
                }else if(casillas.get(i) == 1 && casillas.get(i+1) == 4 && casillas.get(casillas.size()-1)==7){
                    mostrarGanador();
                    break;
                }
            }
        }
    }

    public void mostrarGanador(){
        ganadosUser.setText(puntos.toString());
        fondomsjganador.setVisible(true);
    }

    public void bloquearcheckBox(){
        checkx.setSelected(false);
        checkO.setSelected(false);
        checkO.setDisable(true);
        checkx.setDisable(true);
    }


    @Override
    public void update(Observable observable, Object o) {
        System.out.println("lelgio hasta qui update");
        String objectId= "0";
        try {
            Socket data = (Socket) o;
            DataInputStream inputStream = new DataInputStream(data.getInputStream());
            System.out.println(objectId);
            objectId = inputStream.readUTF();
            System.out.println(objectId);
            inputStream.close();

            String finalObjectId = objectId;
            Platform.runLater(() -> checkPaneRemote(finalObjectId));
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}

