/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 *
 * @author padrao
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button btnOk;
    @FXML
    private TextField numCaixasTextField;
    
    @FXML
    private void btnOkClick(ActionEvent event){
        Parent root;
        Stage newStage;
        Scene scene;
        try {
            //
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMain.fxml"));
            root = (Parent)fxmlLoader.load();
            FXMLMainController mainController = fxmlLoader.getController();
            mainController.setNumCaixas( numCaixasTextField.getText() );
            mainController.criarCaixas ( numCaixasTextField.getText() );
            
            scene = new Scene(root);
            newStage = new Stage();
            newStage.setScene(scene);
            
            
            newStage.show();
            
            //close this window
            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /*@FXML
    private void handleButtonAction(ActionEvent event) {
        
        Cliente cliente = new Cliente(1, 1000, 1);
        cliente.setSemaphore(clienteSem, caixaSem, mutex);
        clientes.add(cliente);
        Thread thread;
        thread = new Thread(cliente);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cliente cliente1 = new Cliente(2, 1000, 1);
        cliente1.setSemaphore(clienteSem, caixaSem, mutex);
        clientes.add(cliente1);
        Thread thread1;
        thread1 = new Thread(cliente1);
        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cliente cliente2 = new Cliente(3, 1000, 1);
        cliente2.setSemaphore(clienteSem, caixaSem, mutex);
        clientes.add(cliente2);
        Thread thread2;
        thread2 = new Thread(cliente2);
        thread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cliente cliente3 = new Cliente(4, 1000, 1);
        cliente3.setSemaphore(clienteSem, caixaSem, mutex);
        clientes.add(cliente3);
        Thread thread3;
        thread3 = new Thread(cliente3);
        thread3.start();
        //Caixa caixa1 = new Caixa(1, clientesSem, caixasSem, mutexSem)
        FXMLMainController.console.append("clicou vei\n");
        //console.writeln();
    }
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
    }    
    
}
