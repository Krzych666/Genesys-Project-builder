/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseHolder.IDDataSet;
import genesys.project.builder.DatabaseHolder.mainWindowData;
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
import genesys.project.builder.DatabaseWriter;
import javafx.collections.FXCollections;

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

    private Label toDuplicate;
    private mainWindowData mainWindowDataPropagator;
    private IDDataSet iDDataPropagator;

    /**
     * duplicateWindowController
     */
    public DuplicateWindowController duplicateWindowController;

    /**
     *
     * @
     */
    @FXML
    public void speciesDuplicateDropdownItemStateChangedActions() {
        iDDataPropagator.setSpeciesID(Integer.parseInt(mainWindowDataPropagator.getSpeciesIdMap().get(mainWindowDataPropagator.getSpeciesList().getSelectionModel().getSelectedIndex()).toString()));
        cultureDuplicateDropdown.setItems(DatabaseReader.populateDropdownsCultures(iDDataPropagator.getSpeciesID()));
        cultureDuplicateDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @
     */
    @FXML
    public void cultureDuplicateDropdownItemStateChangedActions() {
        if (cultureDuplicateDropdown.getSelectionModel().isEmpty()) {
            cultureDuplicateDropdown.getSelectionModel().select(0);
        }
        iDDataPropagator.setCultureID(DatabaseReader.getCultureID(iDDataPropagator.getSpeciesID(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        classDuplicateDropdown.setItems(DatabaseReader.populateDropdownsClasses(iDDataPropagator.getCultureID()));
        classDuplicateDropdown.getSelectionModel().select(0);

        heroDuplicateDropdown.setItems(DatabaseReader.populateDropdownsHeroes(iDDataPropagator.getCultureID(), iDDataPropagator.getClassID()));
        heroDuplicateDropdown.getSelectionModel().select(0);

        progressDuplicateDropdown.setItems(FXCollections.observableArrayList(DatabaseHolder.TOPDROP));
        progressDuplicateDropdown.getItems().addAll(
                BuilderCORE.generateProgressAndBattlesSortedList(
                        speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(),
                        "both"));
        progressDuplicateDropdown.getSelectionModel().select(0);

        rosterDuplicateDropdown.setItems(DatabaseReader.populateDropdownsRosters(iDDataPropagator.getCultureID()));
        rosterDuplicateDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @
     */
    @FXML
    public void classDuplicateDropdownItemStateChangedActions() {
        if (classDuplicateDropdown.getSelectionModel().isEmpty()) {
            classDuplicateDropdown.getSelectionModel().select(0);
        }
        iDDataPropagator.setClassID(DatabaseReader.getClassID(iDDataPropagator.getCultureID(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        Object sel = classDuplicateDropdown.getSelectionModel().getSelectedItem();
        heroDuplicateDropdown.setItems(DatabaseReader.populateDropdownsHeroes(iDDataPropagator.getCultureID(), iDDataPropagator.getClassID()));
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
        iDDataPropagator.setHeroID(DatabaseReader.getHeroID(iDDataPropagator.getCultureID(), heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
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
        iDDataPropagator.setRosterID(DatabaseReader.getHeroID(iDDataPropagator.getCultureID(), rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        Object sel = rosterDuplicateDropdown.getSelectionModel().getSelectedItem();
        heroDuplicateDropdown.getSelectionModel().select(0);
        progressDuplicateDropdown.getSelectionModel().select(0);
        classDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.getSelectionModel().select(sel);
    }

    /**
     * duplicateFinishButtonActions
     *
     */
    @FXML
    public void duplicateFinishButtonActions() {

        if (duplicateAreYouSureStage.isShowing()) {
            duplicateAreYouSureStage.requestFocus();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DuplicateAreYouSureFXML.fxml"));
                Parent root = loader.load();
                duplicateAreYouSureController = loader.getController();
                duplicateAreYouSureController.setDataLists(mainWindowDataPropagator);
                duplicateAreYouSureController.setIDData(iDDataPropagator);
                duplicateAreYouSureController.getDeleteWindow(duplicateFinishButton.getScene().getWindow());
                toDuplicate = duplicateAreYouSureController.getToDuplicate();
                duplicateAreYouSureController.setDuplicateWindowController(duplicateWindowController);
                Scene scene = new Scene(root);
                duplicateAreYouSureStage.setScene(scene);
                duplicateAreYouSureStage.setTitle("Confirm Duplication");
                duplicateAreYouSureStage.show();
            } catch (IOException ex) {
                ErrorController.ErrorControllerMethod(ex);
                Logger.getLogger(DuplicateWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            temp = "  " + progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
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
     * @
     */
    public void commenceDuplicating() {
        if (!speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateSpecies(iDDataPropagator.getSpeciesID(), duplicateNewNameValue.getText());
            int newSpeciesID = DatabaseReader.getSpeciesID(duplicateNewNameValue.getText());
            DatabaseWriter.duplicateAllCulturesAndRelatedDataFromSpecies(iDDataPropagator.getSpeciesID(), newSpeciesID);
        }
        if (!cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateOneCulture(iDDataPropagator.getCultureID(), duplicateNewNameValue.getText());
            int newCultureID = DatabaseReader.getCultureID(iDDataPropagator.getSpeciesID(), duplicateNewNameValue.getText());
            DatabaseWriter.duplicateAllClassesFromCulture(iDDataPropagator.getCultureID(), newCultureID);
            DatabaseWriter.duplicateAllHeroesFromCulture(iDDataPropagator.getCultureID(), newCultureID);
            DatabaseWriter.duplicateAllProgressFromCulture(iDDataPropagator.getCultureID(), newCultureID);
            DatabaseWriter.duplicateAllRostersFromCulture(iDDataPropagator.getCultureID(), newCultureID);
            DatabaseWriter.duplicateAllBattlesFromCulture(iDDataPropagator.getCultureID(), newCultureID);
        }
        if (!classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateOneClass(iDDataPropagator.getClassID(), duplicateNewNameValue.getText());
        }
        if (!heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateOneHero(iDDataPropagator.getHeroID(), duplicateNewNameValue.getText());
        }
        if (!progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            if (progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Progress: ")) {
                DatabaseWriter.duplicateOneProgress(DatabaseReader.getProgressID(iDDataPropagator.getCultureID(), progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Progress: ", "")), duplicateNewNameValue.getText());
            }
            if (progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Battle: ")) {
                DatabaseWriter.duplicateOneBattle(DatabaseReader.getBattleID(iDDataPropagator.getCultureID(), progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Battle: ", "")), duplicateNewNameValue.getText());
            }
        }
        if (!rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseWriter.duplicateOneRoster(iDDataPropagator.getRosterID(), duplicateNewNameValue.getText());
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
        speciesDuplicateDropdown.setItems(DatabaseReader.populateDropdownsSpecies());
        speciesDuplicateDropdown.getSelectionModel().select(0);
        speciesDuplicateDropdownItemStateChangedActions();
        cultureDuplicateDropdownItemStateChangedActions();
    }

    void setDataLists(DatabaseHolder.mainWindowData mainWindowDataPropagator) {
        this.mainWindowDataPropagator = mainWindowDataPropagator;
    }

    void setDuplicateWindowController(DuplicateWindowController duplicateWindowController) {
        this.duplicateWindowController = duplicateWindowController;
    }

    void setSelected() {
        if (!mainWindowDataPropagator.getSpeciesList().getSelectionModel().isEmpty()) {
            speciesDuplicateDropdown.getSelectionModel().select(mainWindowDataPropagator.getSpeciesList().getSelectionModel().getSelectedItem());
            speciesDuplicateDropdownItemStateChangedActions();
            if (!mainWindowDataPropagator.getCulturesList().getSelectionModel().isEmpty() && !mainWindowDataPropagator.getSpeciesList().isFocused()) {
                cultureDuplicateDropdown.getSelectionModel().select(mainWindowDataPropagator.getCulturesList().getSelectionModel().getSelectedItem());
                cultureDuplicateDropdownItemStateChangedActions();
                if (!mainWindowDataPropagator.getRostersList().getSelectionModel().isEmpty() && !mainWindowDataPropagator.getCulturesList().isFocused()) {
                    rosterDuplicateDropdown.getSelectionModel().select(mainWindowDataPropagator.getRostersList().getSelectionModel().getSelectedItem());
                    rosterDuplicateDropdownItemStateChangedActions();
                }
                if (!mainWindowDataPropagator.getCulturesProgressList().getSelectionModel().isEmpty() && !mainWindowDataPropagator.getCulturesList().isFocused()) {
                    progressDuplicateDropdown.getSelectionModel().select(mainWindowDataPropagator.getCulturesProgressList().getSelectionModel().getSelectedItem());
                    rosterDuplicateDropdownItemStateChangedActions();
                }
            }
        }
    }

}
