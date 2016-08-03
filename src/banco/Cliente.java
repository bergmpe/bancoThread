/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.beans.EventHandler;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author padrao
 */
public class Cliente implements Runnable{

    private int id;
    private long tempoAtendimento;
    private int senha;
    private Semaphore clientesSem;
    private Semaphore caixasSem;
    private Semaphore mutexSem;
    private Semaphore localSem;
    
    private final AnchorPane rootPane;
    private final VBox vbox;
    private final Label label;
    private final ImageView imgView;
    private Image[] imagensLeft;
    private Image[] imagensUp;
    
    private Boolean chegouNaFila;
    private Boolean chegouNoCaixa;
    private final int xInicioFila = 300;
    private int tamanhoFila;
    

    public Cliente(int id, long tempoAtendimento, int senha, int tamanhoFila,AnchorPane rootPane) {
        this.id = id;
        this.tempoAtendimento = tempoAtendimento;
        this.senha = senha;
        vbox = new VBox();
        vbox.setLayoutX(650);
        vbox.setLayoutY(200);
        
        
        label = new Label(""+id);
        label.textAlignmentProperty().set(TextAlignment.CENTER);
        
        imgView = new ImageView();
        imgView.setScaleX(1.5);
        imgView.setScaleY(1.5);
        imagensLeft = new Image[]{new Image(this.getClass().getResource("marioleft0.png").toString()),
            new Image(this.getClass().getResource("marioleft1.png").toString()),
                new Image(this.getClass().getResource("marioleft2.png").toString())};
        vbox.getChildren().add(label);
        vbox.getChildren().add(imgView);
        this.rootPane = rootPane;
        rootPane.getChildren().add(vbox);
        
        this.tamanhoFila = tamanhoFila;
        
        chegouNaFila = false;
        chegouNoCaixa = false;    
        localSem = new Semaphore(1);
    }

    public int getId() {
        return id;
    }

    public long getTempoAtendimento() {
        return tempoAtendimento;
    }

    public VBox getVbox() {
        return vbox;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public Image[] getImagensLeft() {
        return imagensLeft;
    }

    public Image[] getImagensUp() {
        return imagensUp;
    }
    
    public void setSemaphore(Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem){
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }
    
    /*public void irParaFila(int tamanhoFila){
        final Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        //final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(), 300);
        System.out.println("tamanhofila " + tamanhoFila);
        double dist = (-rootPane.widthProperty().doubleValue()/2) + (tamanhoFila*60);
        System.out.println("valor: " + dist);
        final KeyValue kv = new KeyValue(vbox.translateXProperty(), dist);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        
        timeline.setOnFinished(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("terminou xegou na fila");
                chegouNaFila = true;
                //localSem.release();
            }
        });
    }*/
    
    @Override
    public void run() {
        try {
            //move o cliente para a fila          
            long inicio = System.currentTimeMillis();
            int i = 0;
            while( vbox.getLayoutX() >  (xInicioFila + tamanhoFila*60) ){
                long ini = System.currentTimeMillis();
                if (ini - inicio > 40){
                    localSem.acquire();
                    vbox.setLayoutX( vbox.getLayoutX() -2);
                    imgView.setImage(imagensLeft[i++]);
                    localSem.release();
                    if( 2 < i)
                        i = 0;
                    inicio = ini;
                } 			
            }
            
            
            //isso bloqueia o cliente na fila
            //while ( !chegouNaFila ) {
                //System.out.println("");
            //}
            //System.out.println("iniciou cliente");
            
            caixasSem.acquire();
            clientesSem.release();//original
            //localSem.acquire();
            
            
            //Em atendimento
            long tempoInicio = System.currentTimeMillis();
            while(System.currentTimeMillis() - tempoInicio < tempoAtendimento){
                
            }            
            FXMLMainController.console.append("terminou de atender cliente " + id + "\n");
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
