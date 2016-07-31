/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
    
    //private AnchorPane rootPane;
    private VBox vbox;
    private Label label;
    private ImageView imgView;
    

    public Cliente(int id, long tempoAtendimento, int senha, AnchorPane rootPane) {
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
        
        rootPane.getChildren().add(vbox);
        //rootPane.getChildren().add(rectBasicTimeline);
        //timeline.play();
        this.irParaFila();
    }

    public int getId() {
        return id;
    }

    public long getTempoAtendimento() {
        return tempoAtendimento;
    }
    
    public void setSemaphore(Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem){
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }
    
    public void irParaFila(){
        final Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.setAutoReverse(true);
        //final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(), 300);
        final KeyValue kv = new KeyValue(vbox.translateXProperty(), -300);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
   
    @Override
    public void run() {
        try {
            //localSem.acquire();
            System.out.println("iniciou cliente");
            caixasSem.acquire();
            clientesSem.release();
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
