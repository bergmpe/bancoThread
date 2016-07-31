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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author padrao
 */
public class FXMLCriarClienteController implements Initializable{

    @FXML
    private Button okBtn;
    @FXML
    private TextField tempoAtendimentoTxtField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To Do
    }
    
    @FXML
    private void okButtonClick(ActionEvent event){
        // ( tempoAtendimentoTxtField.getText() );
        Stage stagePopup = (Stage)okBtn.getScene().getWindow();
        stagePopup.close();
        
        
    }
    
}
