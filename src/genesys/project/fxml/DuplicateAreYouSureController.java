/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import java.net.URL;
import java.sql.SQLException;
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
public class DuplicateAreYouSureController implements Initializable {

    /**
     * @FXML private DuplicateAreYouSure = new javax.swing.JDialog;
 *
     */
    @FXML
    private Label areYouSureMessage1;
    @FXML
    private Label toDuplicate;
    @FXML
    private Button duplicateYesButton;
    @FXML
    private Button duplicateNoButton;

    private ListView speciesList;
    Window duplicateWindow;

    /**
     *duplicateWindowController
     */
    public DuplicateWindowController duplicateWindowController;

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void duplicateYesButtonActions() throws SQLException {
        duplicateWindowController.commenceDuplicating();
        speciesList.setItems(BuilderCORE.getSpeciesList());
        speciesList.getSelectionModel().clearSelection();

        Stage stage = (Stage) duplicateYesButton.getScene().getWindow();
        stage.hide();
        duplicateWindow.hide();
        //java.awt.EventQueue.invokeLater(() -> {
        //    BuilderCORE.listeners();
        //});
    }

    /**
     *duplicateNoButtonActions
     */
    @FXML
    public void duplicateNoButtonActions() {
        Stage stage = (Stage) duplicateNoButton.getScene().getWindow();
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

    void getDeleteWindow(Window duplicateWindow) {
        this.duplicateWindow = duplicateWindow;
    }

    Label getToDuplicate() {
        return this.toDuplicate;
    }

    void setDuplicateWindowController(DuplicateWindowController duplicateWindowController) {
        this.duplicateWindowController = duplicateWindowController;
    }

}
