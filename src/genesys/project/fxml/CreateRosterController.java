/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.Enums;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void speciesChooseDropdownItemStateChangedActions() throws IOException, SQLException {
        if (!speciesChooseDropdown.getSelectionModel().getSelectedItem().equals("--CHOOSE--")) {
            cultureChooseDropdown.setItems(DatabaseModifier.populateDropdownsCultures(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString()));
            cultureChooseDropdown.getSelectionModel().select(0);
        } else {
            cultureChooseDropdown.getItems().clear();
        }
    }

    /**
     *
     * @throws IOException
     * @throws java.sql.SQLException
     * @throws java.lang.CloneNotSupportedException
     */
    @FXML
    public void selectDataForRosterActions() throws IOException, SQLException, CloneNotSupportedException {
        if (pointsTextField.getText().isEmpty()) {
            pointsTextField.setText("0");
        }
        if (!speciesChooseDropdown.getSelectionModel().getSelectedItem().equals("--CHOOSE--") && !cultureChooseDropdown.getSelectionModel().getSelectedItem().equals("--CHOOSE--")) {
            loadSpecies();
            DatabaseModifier.holdRoster = new DatabaseModifier.ARoster();
            DatabaseModifier.holdRoster.setRosterName(rosterNameTextField.getText());
            DatabaseModifier.holdRoster.setSpeciesName(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString());
            DatabaseModifier.holdRoster.setCultureName(cultureChooseDropdown.getSelectionModel().getSelectedItem().toString());
            Stage stage = (Stage) selectDataForRoster.getScene().getWindow();
            stage.hide();
            if (rosterCreatorWindowStage.isShowing()) {
                rosterCreatorWindowStage.requestFocus();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/RosterCreatorWindowFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                rosterCreatorWindowController = loader.getController();
                rosterCreatorWindowController.setMaxPoints(pointsTextField.getText());
                rosterCreatorWindowStage.setScene(scene);
                rosterCreatorWindowStage.show();
            }
        }

    }

    private void loadSpecies() throws SQLException, CloneNotSupportedException {
        DatabaseModifier.loadSpecies(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString());
        DatabaseModifier.holdCulture = new DatabaseModifier.ACulture();
        DatabaseModifier.loadCulture(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString(), cultureChooseDropdown.getSelectionModel().getSelectedItem().toString());
        chooseConnection(Enums.Enmuerations.UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassName FROM CreatedClasses WHERE SpeciesName=? AND CultureName =?");
        stmt.setString(1, speciesChooseDropdown.getSelectionModel().getSelectedItem().toString());
        stmt.setString(2, cultureChooseDropdown.getSelectionModel().getSelectedItem().toString());
        String[] columns = {"ClassName"};
        ObservableList<String> classesList = FXCollections.observableArrayList();
        classesList = BuilderCORE.getData(stmt, columns, null);
        DatabaseModifier.holdClass = new DatabaseModifier.AClass[classesList.size()];
        for (int i = 0; i < classesList.size(); i++) {
            DatabaseModifier.holdClass[i] = new DatabaseModifier.AClass();
            DatabaseModifier.holdClass[i].clearAClass();
            DatabaseModifier.loadClass(speciesChooseDropdown.getSelectionModel().getSelectedItem().toString(), cultureChooseDropdown.getSelectionModel().getSelectedItem().toString(), classesList.get(i), i);
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
        try {
            speciesChooseDropdown.setItems(DatabaseModifier.populateDropdownsSpecies());
            gameTypeDropdown.setItems(FXCollections.observableArrayList(BuilderCORE.GAME_TYPES));
        } catch (SQLException ex) {
            Logger.getLogger(CreateCultureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        speciesChooseDropdown.getSelectionModel().select(0);
        gameTypeDropdown.getSelectionModel().select(0);
        pointsTextField.addEventFilter(KeyEvent.KEY_TYPED, BuilderCORE.numeric_Validation(10));
    }

}
