/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author krzysztofg
 */
public class CreateRosterController implements Initializable {

    @FXML
    private Label speciesChooseLabel;
    @FXML
    private ComboBox speciesChooseDropdown;
    @FXML
    private Label cultureChooseLabel;
    @FXML
    private ComboBox cultureChooseDropdown;
    @FXML
    private Label rosterNameLabel;
    @FXML
    private TextField rosterNameTextField;
    @FXML
    private Label pointsLabel;
    @FXML
    private TextField pointsTextField;
    @FXML
    private Label gameTypeLabel;
    @FXML
    private ComboBox gameTypeDropdown;
    @FXML
    private Button selectDataForRoster;
    @FXML
    private Button cancelSelectDataForRoster;

    /**
     *
     */
    public Stage rosterCreatorWindowStage = new Stage();
    private RosterCreatorWindowController rosterCreatorWindowController;
    private ListView speciesList;

    /**
     * speciesChooseDropdownItemStateChangedActions
     */
    @FXML
    public void speciesChooseDropdownItemStateChangedActions() {
        if (!speciesChooseDropdown.getSelectionModel().getSelectedItem().equals("--CHOOSE--")) {
            cultureChooseDropdown.setItems(DatabaseReader.populateDropdownsCultures(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString()));
            cultureChooseDropdown.getSelectionModel().select(0);
        } else {
            cultureChooseDropdown.getItems().clear();
        }
    }

    /**
     * selectDataForRosterActions
     */
    @FXML
    public void selectDataForRosterActions() {
        if (pointsTextField.getText().isEmpty()) {
            pointsTextField.setText("0");
        }
        if (!speciesChooseDropdown.getSelectionModel().getSelectedItem().equals("--CHOOSE--") && !cultureChooseDropdown.getSelectionModel().getSelectedItem().equals("--CHOOSE--")) {
            loadSpecies();
            DatabaseHolder.holdRoster = new DatabaseHolder.ARoster();
            DatabaseHolder.holdRoster.setRosterName(rosterNameTextField.getText());
            DatabaseHolder.holdRoster.setSpeciesName(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString());
            DatabaseHolder.holdRoster.setCultureName(cultureChooseDropdown.getSelectionModel().getSelectedItem().toString());
            Stage stage = (Stage) selectDataForRoster.getScene().getWindow();
            stage.hide();
            if (rosterCreatorWindowStage.isShowing()) {
                rosterCreatorWindowStage.requestFocus();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/RosterCreatorWindowFXML.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    rosterCreatorWindowController = loader.getController();
                    rosterCreatorWindowController.setMaxPoints(pointsTextField.getText());
                    rosterCreatorWindowStage.setScene(scene);
                    rosterCreatorWindowStage.setTitle("Create Roster " + DatabaseHolder.holdRoster.getRosterName() + " for " + DatabaseHolder.holdRoster.getSpeciesName() + " - " + DatabaseHolder.holdRoster.getCultureName());
                    rosterCreatorWindowStage.show();
                } catch (IOException ex) {
                    ErrorController.ErrorController(ex);
                    Logger.getLogger(CreateRosterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void loadSpecies() {
        DatabaseHolder.loadSpeciesToHold(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString());
        DatabaseHolder.holdCulture = new DatabaseHolder.ACulture();
        DatabaseHolder.loadCultureToHold(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString(), cultureChooseDropdown.getSelectionModel().getSelectedItem().toString());
        ObservableList classesList = DatabaseReader.populateDropdownsClasses(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString(), cultureChooseDropdown.getSelectionModel().getSelectedItem().toString());
        classesList.remove(DatabaseHolder.TOPDROP);
        DatabaseHolder.holdClass = new DatabaseHolder.AClass[classesList.size()];
        DatabaseHolder.modifiedHoldClass = new DatabaseHolder.AClass[DatabaseHolder.holdClass.length];
        for (int i = 0; i < classesList.size(); i++) {
            DatabaseHolder.holdClass[i] = new DatabaseHolder.AClass();
            DatabaseHolder.holdClass[i].clearAClass();
            DatabaseHolder.loadClassToHold(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString(), cultureChooseDropdown.getSelectionModel().getSelectedItem().toString(), classesList.get(i).toString(), i);
        }
    }

    /**
     * cancelSelectSpecies4Actions
     */
    @FXML
    public void cancelSelectDataForRosterActions() {
        Stage stage = (Stage) cancelSelectDataForRoster.getScene().getWindow();
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
        speciesChooseDropdown.setItems(DatabaseReader.populateDropdownsSpecies());
        gameTypeDropdown.setItems(FXCollections.observableArrayList(BuilderCORE.GAME_TYPES));
        speciesChooseDropdown.getSelectionModel().select(0);
        gameTypeDropdown.getSelectionModel().select(0);
        pointsTextField.addEventFilter(KeyEvent.KEY_TYPED, BuilderCORE.numeric_Validation(10));
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

    void setSpeciesAndCultureSelection(String Selection) {
        speciesChooseDropdown.getSelectionModel().select(speciesList.getSelectionModel().getSelectedItem());
        speciesChooseDropdownItemStateChangedActions();
        cultureChooseDropdown.getSelectionModel().select(Selection);
    }

}
