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
        imgView = new ImageView(new Image(this.getClass().getResource("black.png").toString()));
        vbox.getChildren().add(label);
        vbox.getChildren().add(imgView);
        
        //final Rectangle rectBasicTimeline = new Rectangle(100, 50, 100, 50);
        //rectBasicTimeline.setFill(Color.RED);

        /*final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        //final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(), 300);
        final KeyValue kv = new KeyValue(vbox.translateXProperty(), -300);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();*/
        this.rootPane = rootPane;
        rootPane.getChildren().add(vbox);
        //rootPane.getChildren().add(rectBasicTimeline);
        //timeline.play();
        this.tamanhoFila = tamanhoFila;
        
        chegouNaFila = false;
        chegouNoCaixa = false;    
        localSem = new Semaphore(0);
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
    
    public void irParaFila(int tamanhoFila) throws InterruptedException{
        /*double dist = (-rootPane.widthProperty().doubleValue()/2) + (tamanhoFila*60);
        for(int i = 0; i < dist; i++){
            long inicio = System.currentTimeMillis();
            while( (System.currentTimeMillis() - inicio) < 5){
                System.out.println("banco.Cliente.irParaFila()");}
            vbox.translateXProperty().set( vbox.translateXProperty().getValue() + 1);
        }*/
        
        //vbox.translateXProperty().set( vbox.translateXProperty().getValue() + 300);
        //for(int i = 0; i < 100; i++){
        
        //long inicio = System.currentTimeMillis();
            //while( (System.currentTimeMillis() - inicio) < 50){
               // System.out.println("banco.Cliente.irParaFila()");
            //}
        //vbox.setLayoutX(i);
        //}
        
        //chegouNaFila = true;
        
        
        System.out.print("foi para fila");
    }
   
    public void vaParaoCaixa(Caixa caixa){
        final Timeline timeline = new Timeline();
        System.out.println("cVbox " + caixa.getVbox().layoutXProperty().getValue());
        final KeyValue kvx = new KeyValue(vbox.translateXProperty(),
                -rootPane.widthProperty().doubleValue()-vbox.widthProperty().getValue()+
                    caixa.getVbox().layoutXProperty().getValue());
        final KeyValue kvy = new KeyValue(vbox.translateYProperty(),
                -rootPane.heightProperty().doubleValue()+vbox.heightProperty().getValue()+
                    caixa.getVbox().layoutYProperty().getValue()+200);
        final KeyFrame kfx = new KeyFrame(Duration.millis(1000), kvx);
        final KeyFrame kfy = new KeyFrame(Duration.millis(1000), kvy);
        timeline.getKeyFrames().add(kfx);
        timeline.getKeyFrames().add(kfy);
        timeline.play();
        timeline.setOnFinished(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chegouNoCaixa = true;  
                //localSem.release();
            }
        });
    }
    
    public void vaEmbora(){
        final Timeline timeline = new Timeline();
        final KeyValue kvx = new KeyValue(vbox.layoutXProperty(), -10);
        final KeyValue kvy = new KeyValue(vbox.layoutYProperty(), 320);
        final KeyFrame kfx = new KeyFrame(Duration.millis(1500), kvx);
        final KeyFrame kfy = new KeyFrame(Duration.millis(500), kvy);
        timeline.getKeyFrames().add(kfy);
        timeline.play();
        timeline.setOnFinished(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.getKeyFrames().add(kfx);
                timeline.play();
            }
        });
    }
    
    @Override
    public void run() {
        try {
            //move o cliente para a fila          
            long inicio = System.currentTimeMillis();
            while( vbox.getLayoutX() >  (xInicioFila + tamanhoFila*60) ){
                long ini = System.currentTimeMillis();
                if (ini - inicio > 20){
                    vbox.setLayoutX( vbox.getLayoutX() -2);
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
            
            System.out.println("atendendo " + this.id);
            //
            long tempoInicio = System.currentTimeMillis();
            while(System.currentTimeMillis() - tempoInicio < tempoAtendimento){
                
            }            
            System.out.println("terminou cliente");
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
