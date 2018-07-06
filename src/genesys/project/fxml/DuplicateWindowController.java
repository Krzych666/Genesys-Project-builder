/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import genesys.project.builder.DatabaseWriter;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class DuplicateWindowController implements Initializable {

    /**
     * @FXML private DuplicateWindow = new javax.swing.JDialog;
     *
     */
    @FXML
    private ComboBox speciesDuplicateDropdown;
    @FXML
    private ComboBox cultureDuplicateDropdown;
    @FXML
    private ComboBox classDuplicateDropdown;
    @FXML
    private ComboBox heroDuplicateDropdown;
    @FXML
    private ComboBox progressDuplicateDropdown;
    @FXML
    private ComboBox rosterDuplicateDropdown;
    @FXML
    private Label speciesDuplicateLabel;
    @FXML
    private Label cultureDuplicateLabel;
    @FXML
    private Label classDuplicateLabel;
    @FXML
    private Label heroDuplicateLabel;
    @FXML
    private Label progressDuplicateLabel;
    @FXML
    private Label rosterDuplicateLabel;
    @FXML
    private Button duplicateFinishButton;
    @FXML
    private Button duplicateCancelButton;
    @FXML
    private TextField duplicateNewNameValue;
    @FXML
    private Label duplicateNewNameLabel;

    /**
     * duplicateAreYouSureStage
     */
    public Stage duplicateAreYouSureStage = new Stage();
    private DuplicateAreYouSureController duplicateAreYouSureController;

    private ListView speciesList;
    private ListView cultureList;
    private ListView rosterList;
    private Label toDuplicate;

    /**
     * duplicateWindowController
     */
    public DuplicateWindowController duplicateWindowController;

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void speciesDuplicateDropdownItemStateChangedActions() throws SQLException {
        cultureDuplicateDropdown.setItems(DatabaseReader.populateDropdownsCultures(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        cultureDuplicateDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void cultureDuplicateDropdownItemStateChangedActions() throws SQLException {
        if (cultureDuplicateDropdown.getSelectionModel().isEmpty()) {
            cultureDuplicateDropdown.getSelectionModel().select(0);
        }
        classDuplicateDropdown.setItems(DatabaseReader.populateDropdownsClasses(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        classDuplicateDropdown.getSelectionModel().select(0);
        heroDuplicateDropdown.setItems(DatabaseReader.populateDropdownsHeroes(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDuplicateDropdown.getSelectionModel().select(0);
        progressDuplicateDropdown.setItems(DatabaseReader.populateDropdownsProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        progressDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.setItems(DatabaseReader.populateDropdownsRosters(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        rosterDuplicateDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void classDuplicateDropdownItemStateChangedActions() throws SQLException {
        if (classDuplicateDropdown.getSelectionModel().isEmpty()) {
            classDuplicateDropdown.getSelectionModel().select(0);
        }
        Object sel = classDuplicateDropdown.getSelectionModel().getSelectedItem();
        heroDuplicateDropdown.setItems(DatabaseReader.populateDropdownsHeroes(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDuplicateDropdown.getSelectionModel().select(0);
        progressDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.getSelectionModel().select(0);
        classDuplicateDropdown.getSelectionModel().select(sel);
    }

    /**
     * heroDuplicateDropdownItemStateChangedActions
     */
    @FXML
    public void heroDuplicateDropdownItemStateChangedActions() {
        Object sel = heroDuplicateDropdown.getSelectionModel().getSelectedItem();
        progressDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.getSelectionModel().select(0);
        heroDuplicateDropdown.getSelectionModel().select(sel);
    }

    /**
     * progressDuplicateDropdownItemStateChangedActions
     */
    @FXML
    public void progressDuplicateDropdownItemStateChangedActions() {
        Object sel = progressDuplicateDropdown.getSelectionModel().getSelectedItem();
        heroDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.getSelectionModel().select(0);
        classDuplicateDropdown.getSelectionModel().select(0);
        progressDuplicateDropdown.getSelectionModel().select(sel);
    }

    /**
     * rosterDuplicateDropdownItemStateChangedActions
     */
    @FXML
    public void rosterDuplicateDropdownItemStateChangedActions() {
        Object sel = rosterDuplicateDropdown.getSelectionModel().getSelectedItem();
        heroDuplicateDropdown.getSelectionModel().select(0);
        progressDuplicateDropdown.getSelectionModel().select(0);
        classDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.getSelectionModel().select(sel);
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void duplicateFinishButtonActions() throws IOException {

        if (duplicateAreYouSureStage.isShowing()) {
            duplicateAreYouSureStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DuplicateAreYouSureFXML.fxml"));
            Parent root = loader.load();
            duplicateAreYouSureController = loader.getController();
            duplicateAreYouSureController.setSpeciesList(speciesList);
            duplicateAreYouSureController.getDeleteWindow(duplicateFinishButton.getScene().getWindow());
            toDuplicate = duplicateAreYouSureController.getToDuplicate();
            duplicateAreYouSureController.setDuplicateWindowController(duplicateWindowController);
            Scene scene = new Scene(root);
            duplicateAreYouSureStage.setScene(scene);
            duplicateAreYouSureStage.setTitle("Confirm Duplication");
            duplicateAreYouSureStage.show();
        }
        showWhatToDuplicate();
    }

    /**
     * duplicateCancelButtonActions
     */
    @FXML
    public void duplicateCancelButtonActions() {
        Stage stage = (Stage) duplicateCancelButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * showWhatToDuplicate
     */
    public void showWhatToDuplicate() {
        String temp = "";
        if (!speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString();
        }
        if (!cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Class: " + classDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Hero: " + heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Progress: " + progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Roster: " + rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        temp += "    with new name: " + duplicateNewNameValue.getText();
        if (speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp = "  Nothing choosen";
        }
        toDuplicate.setText(temp);
    }

    /**
     *
     * @throws SQLException
     */
    public void commenceDuplicating() throws SQLException {
        if (!speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateSpecies(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),duplicateNewNameValue.getText());
            DatabaseWriter.duplicateCulture(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateClass(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateHero(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateRoster(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--",duplicateNewNameValue.getText());
        }
        if (!cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateCulture(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),duplicateNewNameValue.getText());
            DatabaseWriter.duplicateClass(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateHero(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--",duplicateNewNameValue.getText());
            DatabaseWriter.duplicateRoster(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--",duplicateNewNameValue.getText());
        }
        if (!classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateClass(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),duplicateNewNameValue.getText());
        }
        if (!heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateHero(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),duplicateNewNameValue.getText());
        }
        if (!progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),duplicateNewNameValue.getText());
        }
        if (!rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateRoster(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),duplicateNewNameValue.getText());
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            speciesDuplicateDropdown.setItems(DatabaseReader.populateDropdownsSpecies());
        } catch (SQLException ex) {
            Logger.getLogger(DuplicateWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        speciesDuplicateDropdown.getSelectionModel().select(0);
        try {
            speciesDuplicateDropdownItemStateChangedActions();
            cultureDuplicateDropdownItemStateChangedActions();
        } catch (SQLException ex) {
            Logger.getLogger(DuplicateWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setSpeciesAndCultureAndRosterList(ListView speciesList, ListView cultureList, ListView rosterList) {
        this.speciesList = speciesList;
        this.cultureList = cultureList;
        this.rosterList = rosterList;
    }

    void setDuplicateWindowController(DuplicateWindowController duplicateWindowController) {
        this.duplicateWindowController = duplicateWindowController;
    }

    void setSelected() throws SQLException {
        if (!speciesList.getSelectionModel().isEmpty()) {
            speciesDuplicateDropdown.getSelectionModel().select(speciesList.getSelectionModel().getSelectedItem());
            speciesDuplicateDropdownItemStateChangedActions();
            if (!cultureList.getSelectionModel().isEmpty() && !speciesList.isFocused()) {
                cultureDuplicateDropdown.getSelectionModel().select(cultureList.getSelectionModel().getSelectedItem());
                cultureDuplicateDropdownItemStateChangedActions();
                if (!rosterList.getSelectionModel().isEmpty() && !cultureList.isFocused()) {
                    rosterDuplicateDropdown.getSelectionModel().select(rosterList.getSelectionModel().getSelectedItem());
                    rosterDuplicateDropdownItemStateChangedActions();
                }
            }
        }
    }

}
