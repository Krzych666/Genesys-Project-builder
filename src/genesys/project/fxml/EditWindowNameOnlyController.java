/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class EditWindowNameOnlyController implements Initializable {

    /**
     * @FXML private EditWindowNameOnly = new javax.swing.JDialog;
     *
     */
    @FXML
    private Label editWindowNameOnlyText1;
    @FXML
    private Label editWindowNameOld;
    @FXML
    private Label editWindowNameOnlyText2;
    @FXML
    private TextField editWindowNameNew;
    @FXML
    private Button editWindowNameButtonOk;
    @FXML
    private Button editWindowNameButtonCancel;

    private ListView speciesList;

    /**
     * editWindowNameButtonOkAction
     *
     */
    @FXML
    public void editWindowNameButtonOkAction() {
        Stage stage = (Stage) editWindowNameButtonOk.getScene().getWindow();
        stage.hide();
        switch (DatabaseHolder.currentTable) {
            case CreatedSpecies:
                DatabaseWriter.modifySpeciesName(editWindowNameNew.getText());
                break;
            case CreatedCultures:
                DatabaseWriter.modifyCultureName(editWindowNameNew.getText());
                break;
            case CreatedClasses:
                DatabaseWriter.modifyClassName(editWindowNameNew.getText(), 0);
                break;
            case CreatedHeroes:
                DatabaseWriter.modifyHeroName(editWindowNameNew.getText());
                break;
            case CreatedProgress:
                DatabaseWriter.modifyProgressName(editWindowNameNew.getText());
                break;
            case CreatedRosters:
                DatabaseWriter.modifyRosterName(editWindowNameNew.getText());
                break;
        }
        editCloseAfterSaveActions();
    }

    /**
     * editCloseAfterSaveActions
     *
     */
    public void editCloseAfterSaveActions() {
        speciesList.setItems(DatabaseReader.getSpeciesList());
        speciesList.getSelectionModel().clearSelection();
    }

    /**
     * editWindowNameButtonCancelAction
     */
    @FXML
    public void editWindowNameButtonCancelAction() {
        Stage stage = (Stage) editWindowNameButtonCancel.getScene().getWindow();
        stage.hide();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editWindowNameNew.setText("");
    }

    Label getEditWindowNameOnlyText1() {
        return editWindowNameOnlyText1;
    }

    Label getEditWindowNameOld() {
        return editWindowNameOld;
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
