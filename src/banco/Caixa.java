/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author padrao
 */
public class Caixa implements Runnable{

    private int id;
    private Semaphore clientesSem;
    private Semaphore caixasSem;
    private Semaphore mutexSem;
    private List<Cliente> clientes;
    
    private AnchorPane rootPane;
    private final VBox vbox;
    private final Label label;
    private final Label lbStatus;
    private final ImageView imgView;
    private Image[] images;
    private Animation animation;

    public Caixa(int id, Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem, AnchorPane rootPane) {
        this.id = id;
        this.clientesSem = clientesSem;
        this.caixasSem   = caixasSem;
        this.mutexSem    = mutexSem;
        
        vbox = new VBox();
        vbox.setLayoutX( (id * 70) + 10);
        vbox.setLayoutY(10);
        label = new Label("caixa  " + this.id);
        label.setTextFill(Color.web("#FFFFFF"));
        lbStatus = new Label("Dormindo");
        lbStatus.setTextFill(Color.web("#FFFFFF"));

        
        imgView = new ImageView();
        images = new Image[]{ new Image(this.getClass().getResource("caixa0.png").toString()),
                new Image(this.getClass().getResource("caixa1.png").toString()),
                    new Image(this.getClass().getResource("caixa2.png").toString())};
        imgView.setImage(images[2]);
        final int COLUMNS  =   3;
        final int COUNT    =  3;
        final int OFFSET_X =  0;
        final int OFFSET_Y =  0;
        final int WIDTH    = 60;
        final int HEIGHT   = 70;     
     
        imgView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        /*animation = new SpriteAnimation(
                imgView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );*/
        //this.durma();
        vbox.getChildren().add(label);
        vbox.getChildren().add(lbStatus);
        vbox.getChildren().add(imgView);
        rootPane.getChildren().add(vbox);
    }

    public void setClientes(List clientes) {
        this.clientes = clientes;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setSemaphore(Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem){
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }
    
    /*public void durma(){
        animation.stop();
        animation.setCycleCount(1);
        animation.jumpTo(Duration.millis(3000));
        animation.play();
    }
    
    public void acorde(){
        animation.stop();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }*/
    
    public void durma(){
        imgView.setImage(images[2]);
    }
    

    @Override
    public void run() {
        System.out.println("caixa criado" + this.id);
        while(true){
            try {
                clientesSem.acquire();
                System.out.println("caixa" + this.id + " iniciou atendimento");
                //this.acorde();original
                
                mutexSem.acquire();
                //libera o primeiro cliente da fila.
                Cliente cliente = clientes.get(0);
                clientes.remove(0);
                mutexSem.release();
                //move o cliente para o caixa
                //cliente.vaParaoCaixa(this);
                int i = 0;
                long inicio = System.currentTimeMillis();
                while( cliente.getVbox().getLayoutX() >  vbox.getLayoutX() ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 40){
                        cliente.getVbox().setLayoutX( cliente.getVbox().getLayoutX() -2);
                        cliente.getImgView().setImage(cliente.getImagensLeft()[i++]);
                        if( 2 < i)
                            i = 0;
                        inicio = ini;
                    } 			
                }
                inicio = System.currentTimeMillis();
                while( cliente.getVbox().getLayoutY() >  vbox.getLayoutY() + 100 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 80){
                        cliente.getVbox().setLayoutY( cliente.getVbox().getLayoutY() -2);
                        cliente.getImgView().setImage(cliente.getImagensLeft()[i++]);
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 
                }
                
                //atendendo o cliente.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbStatus.setText("At o cli " + cliente.getId());
                    }
                });
                long tempoInicio = System.currentTimeMillis();
                inicio = System.currentTimeMillis();
                i = 0;
                while(System.currentTimeMillis() - tempoInicio < cliente.getTempoAtendimento()){                    
                    long ini = System.currentTimeMillis();
                    if(ini - inicio > 250){
                        imgView.setImage(images[i++]);
                        if( i > 2 )
                            i = 0;
                        inicio = ini;
                    }
                }
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbStatus.setText("Dormindo");
                    }
                });
                
                
                this.durma();
                //cliente va embora
                //cliente.vaEmbora();
                inicio = System.currentTimeMillis();
                while( cliente.getVbox().getLayoutY() <  200 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 40){
                        cliente.getVbox().setLayoutY( cliente.getVbox().getLayoutY() +2);
                        cliente.getImgView().setImage(cliente.getImagensLeft()[i++]);
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 
                }
                inicio = System.currentTimeMillis();
                while( cliente.getVbox().getLayoutX() >  -50 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 40){
                        cliente.getVbox().setLayoutX( cliente.getVbox().getLayoutX() -2);
                        cliente.getImgView().setImage(cliente.getImagensLeft()[i++]);
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 			
                }
                
                caixasSem.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
