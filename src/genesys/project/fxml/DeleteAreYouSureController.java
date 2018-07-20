/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class DeleteAreYouSureController implements Initializable {

    /**
     * @FXML private DeleteAreYouSure = new javax.swing.JDialog;
 *
     */
    @FXML
    private Label areYouSureMessage;
    @FXML
    private Label toDelete;
    @FXML
    private Button deleteYesButton;
    @FXML
    private Button deleteNoButton;

    private ListView speciesList;
    Window deleteWindow;

    /**
     *
     *
     */
    @FXML
    public void deleteYesButtonActions()  {
        DatabaseWriter.commenceDeleting();
        speciesList.setItems(DatabaseReader.getSpeciesList());
        speciesList.getSelectionModel().clearSelection();

        Stage stage = (Stage) deleteYesButton.getScene().getWindow();
        stage.hide();
        deleteWindow.hide();
        //java.awt.EventQueue.invokeLater(() -> {
        //    BuilderCORE.listeners();
        //});
    }

    /**
     *deleteNoButtonActions
     */
    @FXML
    public void deleteNoButtonActions() {
        Stage stage = (Stage) deleteNoButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

    void getDeleteWindow(Window deleteWindow) {
        this.deleteWindow = deleteWindow;
    }

    Label getToDelete() {
        return this.toDelete;
    }

}
