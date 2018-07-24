/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class DeleteWindowController implements Initializable {

    /**
     * @FXML private DeleteWindow = new javax.swing.JDialog;
     *
     */
    @FXML
    private ComboBox speciesDeleteDropdown;
    @FXML
    private ComboBox cultureDeleteDropdown;
    @FXML
    private ComboBox classDeleteDropdown;
    @FXML
    private ComboBox heroDeleteDropdown;
    @FXML
    private ComboBox progressDeleteDropdown;
    @FXML
    private ComboBox rosterDeleteDropdown;
    @FXML
    private Label speciesDeleteLabel;
    @FXML
    private Label cultureDeleteLabel;
    @FXML
    private Label classDeleteLabel;
    @FXML
    private Label heroDeleteLabel;
    @FXML
    private Label rrogressDeleteLabel;
    @FXML
    private Label rosterDeleteLabel;
    @FXML
    private Button deleteFinishButton;
    @FXML
    private Button deleteCancelButton;

    /**
     * deleteAreYouSureStage
     */
    public Stage deleteAreYouSureStage = new Stage();
    private DeleteAreYouSureController deleteAreYouSureController;

    private ListView speciesList;
    private ListView cultureList;
    private ListView rosterList;
    private Label toDelete;

    /**
     *
     * @
     */
    @FXML
    public void speciesDeleteDropdownItemStateChangedActions() {
        cultureDeleteDropdown.setItems(DatabaseReader.populateDropdownsCultures(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        cultureDeleteDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @
     */
    @FXML
    public void cultureDeleteDropdownItemStateChangedActions() {
        if (cultureDeleteDropdown.getSelectionModel().isEmpty()) {
            cultureDeleteDropdown.getSelectionModel().select(0);
        }
        classDeleteDropdown.setItems(DatabaseReader.populateDropdownsClasses(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(), cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        classDeleteDropdown.getSelectionModel().select(0);
        heroDeleteDropdown.setItems(DatabaseReader.populateDropdownsHeroes(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(), cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString(), classDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDeleteDropdown.getSelectionModel().select(0);
        progressDeleteDropdown.setItems(DatabaseReader.populateDropdownsProgress(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(), cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        progressDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.setItems(DatabaseReader.populateDropdownsRosters(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(), cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        rosterDeleteDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @
     */
    @FXML
    public void classDeleteDropdownItemStateChangedActions() {
        if (classDeleteDropdown.getSelectionModel().isEmpty()) {
            classDeleteDropdown.getSelectionModel().select(0);
        }
        Object sel = classDeleteDropdown.getSelectionModel().getSelectedItem();
        heroDeleteDropdown.setItems(DatabaseReader.populateDropdownsHeroes(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(), cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString(), classDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDeleteDropdown.getSelectionModel().select(0);
        progressDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(0);
        classDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * heroDeleteDropdownItemStateChangedActions
     */
    @FXML
    public void heroDeleteDropdownItemStateChangedActions() {
        Object sel = heroDeleteDropdown.getSelectionModel().getSelectedItem();
        progressDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(0);
        heroDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * progressDeleteDropdownItemStateChangedActions
     */
    @FXML
    public void progressDeleteDropdownItemStateChangedActions() {
        Object sel = progressDeleteDropdown.getSelectionModel().getSelectedItem();
        heroDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(0);
        classDeleteDropdown.getSelectionModel().select(0);
        progressDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * rosterDeleteDropdownItemStateChangedActions
     */
    @FXML
    public void rosterDeleteDropdownItemStateChangedActions() {
        Object sel = rosterDeleteDropdown.getSelectionModel().getSelectedItem();
        heroDeleteDropdown.getSelectionModel().select(0);
        progressDeleteDropdown.getSelectionModel().select(0);
        classDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * deleteFinishButtonActions
     *
     */
    @FXML
    public void deleteFinishButtonActions() {
        prepareDeleteScript();
        if (deleteAreYouSureStage.isShowing()) {
            deleteAreYouSureStage.requestFocus();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DeleteAreYouSureFXML.fxml"));
                Parent root = loader.load();
                deleteAreYouSureController = loader.getController();
                deleteAreYouSureController.setSpeciesList(speciesList);                
                deleteAreYouSureController.setDeleteWindow(deleteFinishButton.getScene().getWindow());
                Scene scene = new Scene(root);
                deleteAreYouSureStage.setScene(scene);
                deleteAreYouSureStage.setTitle("Confirm Deletion");
                deleteAreYouSureStage.show();
                toDelete = deleteAreYouSureController.getToDelete();
            } catch (IOException ex) {
                ErrorController.ErrorController(ex);
                Logger.getLogger(DeleteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        showWhatToDelete();
    }

    /**
     * deleteCancelButtonActions
     */
    @FXML
    public void deleteCancelButtonActions() {
        Stage stage = (Stage) deleteCancelButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * prepareDeleteScript
     */
    public void prepareDeleteScript() {
        String temp = "";
        String w = " WHERE (";
        String a = "') AND (";
        String[] sql1 = {"DELETE FROM CreatedSpecies", "DELETE FROM CreatedCultures", "DELETE FROM CreatedClasses", "DELETE FROM CreatedHeroes", "DELETE FROM CreatedProgress", "DELETE FROM CreatedRosters"};
        String[] sql2 = {"SpeciesName = '", "CultureName = '", "ClassName = '", "HeroName = '", "ProgressName = '", "RosterName = '"};
        if (speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[0] + ";" + sql1[1] + ";" + sql1[2] + ";" + sql1[3] + ";" + sql1[4] + ";" + sql1[5] + ";";
        }
        if (!speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[0] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[1] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[2] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[3] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[4] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[5] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');";
        }
        if (!cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[1] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[2] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[3] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[4] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[5] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');";
        }
        if (!classDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[2] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[2] + classDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[3] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[2] + classDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[4] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');"
                    + sql1[5] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');";
        }
        if (!heroDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[3] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[3] + heroDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');";
        }
        if (!progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[4] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[4] + progressDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');";
        }
        if (!rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = sql1[5] + w + sql2[0] + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[1] + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + a + sql2[5] + rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString() + "');";
        }
        DatabaseWriter.tempDeletescript = temp;
    }

    /**
     * showWhatToDelete
     */
    public void showWhatToDelete() {
        String temp = "";
        if (speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  All Species";
        }
        if (!speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Species: " + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString();
        }
        if (!cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Culture: " + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + " (in species: " + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!classDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Class: " + classDeleteDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!heroDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Hero: " + heroDeleteDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Progress: " + progressDeleteDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Roster: " + rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        toDelete.setText(temp);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speciesDeleteDropdown.setItems(DatabaseReader.populateDropdownsSpecies());
        speciesDeleteDropdown.getSelectionModel().select(0);
        speciesDeleteDropdownItemStateChangedActions();
        cultureDeleteDropdownItemStateChangedActions();
    }

    void setSpeciesAndCultureAndRosterList(ListView speciesList, ListView cultureList, ListView rosterList) {
        this.speciesList = speciesList;
        this.cultureList = cultureList;
        this.rosterList = rosterList;
    }

    void setSelected() {
        if (!speciesList.getSelectionModel().isEmpty()) {
            speciesDeleteDropdown.getSelectionModel().select(speciesList.getSelectionModel().getSelectedItem());
            speciesDeleteDropdownItemStateChangedActions();
            if (!cultureList.getSelectionModel().isEmpty() && !speciesList.isFocused()) {
                cultureDeleteDropdown.getSelectionModel().select(cultureList.getSelectionModel().getSelectedItem());
                cultureDeleteDropdownItemStateChangedActions();
                if (!rosterList.getSelectionModel().isEmpty() && !cultureList.isFocused()) {
                    rosterDeleteDropdown.getSelectionModel().select(rosterList.getSelectionModel().getSelectedItem());
                    rosterDeleteDropdownItemStateChangedActions();
                }
            }
        }
    }

}
