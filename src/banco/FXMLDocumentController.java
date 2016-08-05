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
            mainController.criarCaixas ( numCaixasTextField.getText() );
            
            scene = new Scene(root);
            newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Banco MMC");
            
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
    }    
    
}
