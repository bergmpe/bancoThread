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
    List<Cliente> clientes;
    List<Caixa>   caixas;
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        console.append("clicou vei");
        console.writeln();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        console  = new ConsoleWindow();
        clientes = new LinkedList<>();
        caixas   = new LinkedList<>();
        console.showConsoleWindow();
    }    
    
}
