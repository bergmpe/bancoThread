/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author padrao
 */
public class FXMLMainController implements Initializable {

    private Integer numCaixas;
    static ConsoleWindow console = new ConsoleWindow();
    static List<Cliente> clientes;
    static List<Caixa>   caixas;
    private Semaphore clienteSem;
    private Semaphore caixaSem;
    private Semaphore mutex;
    
    
    private int caixaId;
    private int clienteId;
    
    @FXML
    private Label label;
    @FXML
    private ImageView imgView;
    @FXML
    private AnchorPane rootPane;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientes = new LinkedList<>();
        caixas   = new LinkedList<>();
        clienteSem = new Semaphore(0, true);
        mutex    = new Semaphore(1);
        
        caixaId = 0;
        clienteId = 0;
        //System.out.println("Iniciando...." + caixaSem.availablePermits());
        System.out.println("Iniciando.." + rootPane.getId());
        //imgView.setImage(new Image(this.getClass().getResource("background2.png").toString()));
        console.showConsoleWindow();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Caixa> getCaixas() {
        return caixas;
    }

    public Semaphore getClienteSem() {
        return clienteSem;
    }

    public Semaphore getCaixaSem() {
        return caixaSem;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public int getCaixaId() {
        return caixaId;
    }

    public int getClienteId() {
        return clienteId;
    }
    
    public void setNumCaixas(String numCaixas){
        this.numCaixas = Integer.getInteger(numCaixas);
        label.setText( numCaixas );
    }
    
    public void criarCaixas(String numCaixas){
    /*    Image IMAGE = new Image(this.getClass().getResource("slinkEdit3.png").toString());

     int COLUMNS  =   3;
     int COUNT    =  3;
     int OFFSET_X =  0;
     int OFFSET_Y =  0;
     int WIDTH    = 60;
     int HEIGHT   = 70;
     final ImageView imageView = new ImageView(IMAGE);
     
     
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        //animation.setCycleCount(Animation.INDEFINITE);
        //animation.play();
        //animation.stop();
        animation.jumpTo(Duration.millis(3000));
        animation.play();
        rootPane.getChildren().add(imageView);
        
        /*animation.stop();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();*/
        
        System.out.println("criando caixas");
        int numeroCaixas = Integer.parseInt(numCaixas);
        caixaSem = new Semaphore(numeroCaixas, true);
        System.out.println("numero do semaphoro " + numeroCaixas);
        for (int i = 0; i < numeroCaixas; i++) {
             
            Caixa caixa = new Caixa(caixaId++, clienteSem, caixaSem, mutex, rootPane);
            caixa.setClientes(clientes);
            caixas.add(caixa);
            Thread t = new Thread(caixa);
            t.start();
        }
    }
    
    @FXML
    private void criarClienteBtnClick(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Banco MMC");
        dialog.setHeaderText("Criar Cliente");
        dialog.setContentText("Informe o tempo de Atendimento:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        //if (result.isPresent()){
         //   System.out.println("Your name: " + result.get());
        //}

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(tempoAtendimento -> { int tempo = Integer.parseInt(tempoAtendimento)*1000;
        Cliente cliente = new Cliente(clienteId++, tempo, 1, clientes.size(),rootPane);
        cliente.setSemaphore(clienteSem, caixaSem, mutex);
        clientes.add(cliente);
        Thread thread = new Thread(cliente);
        thread.start();
        } );
        
        
    }
    
    /*public void acorda(){
    animation.stop();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }*/
    
    /*public void criarCaixarEdorme(){
    Image IMAGE = new Image(this.getClass().getResource("slinkEdit3.png").toString());

     int COLUMNS  =   3;
     int COUNT    =  3;
     int OFFSET_X =  0;
     int OFFSET_Y =  0;
     int WIDTH    = 60;
     int HEIGHT   = 70;
     final ImageView imageView = new ImageView(IMAGE);
     
     
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        //animation.setCycleCount(Animation.INDEFINITE);
        //animation.play();
        //animation.stop();
        animation.jumpTo(Duration.millis(3000));
        animation.play();
    }*/
    
    /*private void criarCliente(String tempo){
        int tempoAtendimento = Integer.parseInt(tempo)*1000;
        Cliente cliente = new Cliente(clienteId++, tempoAtendimento, 1);
        cliente.setSemaphore(clienteSem, caixaSem, mutex);
        clientes.add(cliente);
        Thread thread = new Thread(cliente);
        thread.start();
    }*/
}
