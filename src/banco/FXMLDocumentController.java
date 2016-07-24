/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author padrao
 */
public class FXMLDocumentController implements Initializable {

    ConsoleWindow console = new ConsoleWindow();
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
         console.showConsoleWindow();
    }    
    
}
