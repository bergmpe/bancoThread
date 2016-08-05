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

/**
 *
 * @author padrao
 */
public class Caixa implements Runnable{

    private final int id;
    private Semaphore clientesSem;
    private Semaphore caixasSem;
    private Semaphore mutexSem;
    private List<Cliente> clientes;
    
    private AnchorPane rootPane;
    private final VBox vbox;
    private final Label label;
    private final Label lbStatus;
    private final ImageView imgView;
    private final Image[] images;
    private Image imgDormindo;
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
        images = new Image[]{ new Image(this.getClass().getResource("caxSemz0.png").toString()),
                new Image(this.getClass().getResource("caxSemz1.png").toString()),
                    new Image(this.getClass().getResource("caxSemz2.png").toString())};
        imgDormindo = new Image(this.getClass().getResource("caxz0z.png").toString());
        imgView.setImage( imgDormindo );
        final int COLUMNS  =   3;
        final int COUNT    =  3;
        final int OFFSET_X =  0;
        final int OFFSET_Y =  0;
        final int WIDTH    = 60;
        final int HEIGHT   = 70;          
        imgView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

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
    
    public void durma(){
        try {
            mutexSem.acquire();
            imgView.setImage( imgDormindo );
            mutexSem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void run() {
        FXMLMainController.console.append("caixa criado" + this.id + "\n");
        while(true){
            try {
                clientesSem.acquire(); 
                
                mutexSem.acquire();
                //libera o primeiro cliente da fila.
                Cliente cliente = clientes.get(0);
                clientes.remove(0);
                
                mutexSem.release();
                
                //move o cliente para o caixa
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        cliente.getLbStatus().setText("");
                    }
                });
                int i = 0;
                long inicio = System.currentTimeMillis();
                while( cliente.getVbox().getLayoutX() >  vbox.getLayoutX() ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 140){
                       // mutexSem.acquire();
                        cliente.getVbox().setLayoutX( cliente.getVbox().getLayoutX() -4);
                        cliente.getImgView().setImage(cliente.getImagensLeft()[i++]);
                       // mutexSem.release();
                        if( 2 < i)
                            i = 0;
                        inicio = ini;
                    } 			
                }
                inicio = System.currentTimeMillis();
                while( cliente.getVbox().getLayoutY() >  vbox.getLayoutY() + 100 ){
                    long ini = System.currentTimeMillis();
                    if (ini - inicio > 140){
                        mutexSem.acquire();
                        cliente.getVbox().setLayoutY( cliente.getVbox().getLayoutY() -4);
                        cliente.getImgView().setImage(cliente.getImagensUp()[i++]);
                        mutexSem.release();
                        if( 2 < i )
                            i = 0;
                        inicio = ini;
                    } 
                }
                cliente.getLocalSem().release();
                
                //atendendo o cliente.
                FXMLMainController.console.append("caixa " + id + " atendendo o cliente " + cliente.getId() + "\n");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lbStatus.setText("At o cli " + cliente.getId());
                    }
                });
                long tempoInicio = System.currentTimeMillis();
                inicio = System.currentTimeMillis();
                i = 0;
                while( System.currentTimeMillis() - tempoInicio < cliente.getTempoAtendimento()){
                    // System.out.println("caixa");
                    long ini = System.currentTimeMillis();
                    if( ini - inicio > 250){
                        mutexSem.acquire();
                        imgView.setImage( images[i++] );
                        mutexSem.release();
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
                caixasSem.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Caixa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
