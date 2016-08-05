/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private int senha;
    private int clienteId;
    private final int xInicioFila = 350;
    
    @FXML
    private Label label;
    @FXML
    private TextField tempoAtendimentoTxtField;
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
        senha = 0;
        clienteId = 0;
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
    
    public void criarCaixas(String numCaixas){
        int numeroCaixas = Integer.parseInt(numCaixas);
        caixaSem = new Semaphore(numeroCaixas, true);
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
        int tempo = 0;
        
        tempo = Integer.parseInt(tempoAtendimentoTxtField.getText()) * 1000;
        Cliente cliente = new Cliente(clienteId++, tempo, senha++, clientes,this.rootPane);
        cliente.setSemaphore(clienteSem, caixaSem, mutex);
        cliente.setxInicioFila(xInicioFila);
        try {
            mutex.acquire();
            rootPane.getChildren().add(cliente.getVbox());
            clientes.add(cliente);
            mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread thread = new Thread(cliente);
        thread.start();
    }
}