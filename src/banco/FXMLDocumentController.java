/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
/**
 *
 * @author padrao
 */
public class FXMLDocumentController implements Initializable {

    ConsoleWindow console;
    private List<Cliente> clientes;
    private List<Caixa>   caixas;
    private Semaphore clienteSem;
    private Semaphore caixaSem;
    private Semaphore mutex;
    
    
    @FXML
    private Label label;
    
    @FXML
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
        console.append("clicou vei\n");
        //console.writeln();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Popup pop = new Popup();
        
        console  = new ConsoleWindow();
        clientes = new LinkedList<>();
        caixas   = new LinkedList<>();
        clienteSem = new Semaphore(0, true);
        caixaSem = new Semaphore(1, true);
        mutex    = new Semaphore(1);
        Caixa caixa1 = new Caixa(1, clienteSem, caixaSem, mutex);
        Caixa caixa2 = new Caixa(2, clienteSem, caixaSem, mutex);
        caixa1.setSemaphore(clienteSem, caixaSem, mutex);
        caixa2.setSemaphore(clienteSem, caixaSem, mutex);
        Thread t1 = new Thread(caixa1);
        t1.start();
        //Thread t2 = new Thread(caixa2);
        //t2.start();
        console.showConsoleWindow();
        label.setText("Hello World!");
    }    
    
}
