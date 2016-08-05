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
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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
    private final Label lbStatus;
    private final ImageView imgView;
    private Image[] imagensLeft;
    private Image[] imagensUp;
    private Image[] imagensDown;
    
    private Boolean chegouNaFila;
    private Boolean chegouNoCaixa;
    private List<Cliente> clientes;
    private int xInicioFila;
    

    public Cliente(int id, long tempoAtendimento, int senha, List<Cliente> clientes,AnchorPane rootPane) {
        this.id = id;
        this.tempoAtendimento = tempoAtendimento;
        this.senha = senha;
        vbox = new VBox();
        vbox.setLayoutX(800);
        vbox.setLayoutY(200);
        
        label = new Label("" + id + " T: " + this.tempoAtendimento/1000);
        label.textAlignmentProperty().set(TextAlignment.CENTER);
        label.setTextFill(Color.web("#FFFFFF"));
        lbStatus = new Label("Status");
        lbStatus.setTextFill(Color.web("#FFFFFF"));
        
        imgView = new ImageView();
        imgView.setScaleX(0.8);
        imgView.setScaleY(0.8);
        imagensLeft = new Image[]{new Image(this.getClass().getResource("mariolleft0.png").toString()),
            new Image(this.getClass().getResource("mariolleft1.png").toString()),
                new Image(this.getClass().getResource("mariolleft2.png").toString())};
        imagensUp = new Image[]{new Image(this.getClass().getResource("marioUp0.png").toString()),
            new Image(this.getClass().getResource("marioUp1.png").toString()),
                new Image(this.getClass().getResource("marioUp2.png").toString())};
        imagensDown = new Image[]{new Image(this.getClass().getResource("mariodown0.png").toString()),
            new Image(this.getClass().getResource("mariodown1.png").toString()),
                new Image(this.getClass().getResource("mariodown2.png").toString())};
        vbox.getChildren().add(label);
        vbox.getChildren().add(lbStatus);
        vbox.getChildren().add(imgView);
        this.rootPane = rootPane;
//        this.rootPane.getChildren().add(vbox);
        
        this.clientes = clientes;
        
        chegouNaFila = false;
        chegouNoCaixa = false;    
        localSem = new Semaphore(0);
    }
    
    public void adicionarVbox(){
        try {
            mutexSem.acquire();
            this.rootPane.getChildren().add(vbox);
            mutexSem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

    public Semaphore getLocalSem() {
        return localSem;
    }

    public Label getLbStatus() {
        return lbStatus;
    }

    public void setxInicioFila(int xInicioFila) {
        this.xInicioFila = xInicioFila;
    }
    
    public void setSemaphore(Semaphore clientesSem, Semaphore caixasSem, Semaphore mutexSem){
        this.clientesSem = clientesSem;
        this.caixasSem = caixasSem;
        this.mutexSem = mutexSem;
    }
    
    private void vaParaFila(){
        long inicio = System.currentTimeMillis();
            int i = 0;
            while( vbox.getLayoutX() >  (xInicioFila + clientes.size()*60) ){
                long ini = System.currentTimeMillis();
                if (ini - inicio > 280){
                    try {
                        mutexSem.acquire();
                        vbox.setLayoutX( vbox.getLayoutX() -8);
                        imgView.setImage(imagensLeft[i++]);
                        mutexSem.release();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if( 2 < i )
                        i = 0;
                    inicio = ini;
                } 			
            }
    }
    
    private void vaEmbora(){
        int i=0;
        long inicio = System.currentTimeMillis();
        while( vbox.getLayoutY() <  200 ){
            long ini = System.currentTimeMillis();
            if (ini - inicio > 280){
                try {
                    mutexSem.acquire();
                    vbox.setLayoutY( vbox.getLayoutY() +8 );
                    imgView.setImage( imagensDown[i++] );
                    mutexSem.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                if( 2 < i )
                    i = 0;
                inicio = ini;
                } 
                }
                inicio = System.currentTimeMillis();
                while( vbox.getLayoutX() >  -50 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 280){
                        try {
                            mutexSem.acquire();
                            vbox.setLayoutX( vbox.getLayoutX() -8);
                            imgView.setImage( imagensLeft[i++]);
                            mutexSem.release();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 			
                }
            
    }
    
    public void avance(){
        int i = 0;
        long ini;
        double posInicial = vbox.getLayoutX();
        long inicio = System.currentTimeMillis();
        System.out.println("" + clientes.indexOf(this));
        while( vbox.getLayoutX() > (xInicioFila + clientes.indexOf(this)*60) ){
            ini = System.currentTimeMillis();
            if (ini - inicio > 140){
                vbox.setLayoutX( vbox.getLayoutX() -4);
                imgView.setImage(imagensLeft[i++]);
                if( 2 < i )
                    i = 0;
                inicio = ini;
            } 			
        }
    }
    
    @Override
    public void run() {
        try {
            //move o cliente para a fila          
            /*long inicio = System.currentTimeMillis();
            int i = 0;
            while( vbox.getLayoutX() >  (xInicioFila + tamanhoFila*60) ){
                long ini = System.currentTimeMillis();
                if (ini - inicio > 40){
                    //localSem.acquire();
                    mutexSem.acquire();
                    vbox.setLayoutX( vbox.getLayoutX() -2);
                    imgView.setImage(imagensLeft[i++]);
                    mutexSem.release();
                    //localSem.release();
                    if( 2 < i)
                        i = 0;
                    inicio = ini;
                } 			
            }*/
            
            vaParaFila();
            Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbStatus.setText("Dormindo");
                    }
             });

            //isso bloqueia o cliente na fila
            //while ( !chegouNaFila ) {
                //System.out.println("");
            //}
            //System.out.println("iniciou cliente");
            
            caixasSem.acquire();
            clientesSem.release();//original
            localSem.acquire();
            
            
            //Em atendimento
            long tempoInicio = System.currentTimeMillis();
            while(System.currentTimeMillis() - tempoInicio < tempoAtendimento){
                System.out.println("");
            }
            
            /*int i=0;
            long inicio = System.currentTimeMillis();
                while( vbox.getLayoutY() <  200 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 40){
                        mutexSem.acquire();
                        vbox.setLayoutY( vbox.getLayoutY() +2);
                        imgView.setImage( imagensLeft[i++]);
                        mutexSem.release();
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 
                }
                inicio = System.currentTimeMillis();
                while( vbox.getLayoutX() >  -50 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 40){
                        mutexSem.acquire();
                        vbox.setLayoutX( vbox.getLayoutX() -2);
                        imgView.setImage( imagensLeft[i++]);
                        mutexSem.release();
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 			
                }*/
            
             Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbStatus.setText("Ja foi Ate");
                    }
             });
            vaEmbora();
            FXMLMainController.console.append("cliente " + id + " foi embora\n");
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
