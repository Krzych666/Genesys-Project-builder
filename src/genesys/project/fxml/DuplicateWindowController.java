/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseModifier;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import genesys.project.builder.Enums.Enmuerations.UseCases;

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
        cultureDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsCultures(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
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
        classDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsClasses(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        classDuplicateDropdown.getSelectionModel().select(0);
        heroDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsHeroes(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDuplicateDropdown.getSelectionModel().select(0);
        progressDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
        progressDuplicateDropdown.getSelectionModel().select(0);
        rosterDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsRosters(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
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
        heroDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsHeroes(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString()));
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
        if (!speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString();
        }
        if (!cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Class: " + classDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Hero: " + heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Progress: " + progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        if (!rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Roster: " + rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + " (in culture: " + cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ", in species: " + speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString() + ")";
        }
        temp += "    with new name: " + duplicateNewNameValue.getText();
        if (speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            temp = "  Nothing choosen";
        }
        toDuplicate.setText(temp);
    }

    /**
     *
     * @throws SQLException
     */
    public void commenceDuplicating() throws SQLException {
        if (!speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            duplicateSpecies(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString());
            duplicateCulture(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--");
            duplicateClass(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--");
            duplicateHero(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--");
            duplicateProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--");
            duplicateRoster(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--", "--all--");
        }
        if (!cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            duplicateCulture(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString());
            duplicateClass(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--");
            duplicateHero(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--");
            duplicateProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--");
            duplicateRoster(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), "--all--");
        }
        if (!classDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            duplicateClass(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), classDuplicateDropdown.getSelectionModel().getSelectedItem().toString());
        }
        if (!heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            duplicateHero(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), heroDuplicateDropdown.getSelectionModel().getSelectedItem().toString());
        }
        if (!progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            duplicateProgress(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), progressDuplicateDropdown.getSelectionModel().getSelectedItem().toString());
        }
        if (!rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            duplicateRoster(speciesDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), cultureDuplicateDropdown.getSelectionModel().getSelectedItem().toString(), rosterDuplicateDropdown.getSelectionModel().getSelectedItem().toString());
        }
    }

    private void duplicateSpecies(String selSpecies) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedSpecies WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        String[] columns = {"LifeDomain", "CharacteristicGroup", "Age", "Skills", "SpeciesModifiers"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        DatabaseModifier.executeSQL("INSERT INTO `CreatedSpecies`(LifeDomain, CharacteristicGroup,SpeciesName,Age,Skills,SpeciesModifiers) VALUES ('" + data.get(0).toString().split("\\|")[0] + "','" + data.get(0).toString().split("\\|")[1] + "','" + duplicateNewNameValue.getText() + "','" + data.get(0).toString().split("\\|")[2] + "''" + data.get(0).toString().split("\\|")[3] + "''" + data.get(0).toString().split("\\|")[4] + "');");
    }

    private void duplicateCulture(String selSpecies, String selCulture) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("--all--".equals(selCulture) ? "SELECT * FROM CreatedCultures WHERE SpeciesName =?" : "SELECT * FROM CreatedCultures WHERE SpeciesName =? AND CultureName =");
        stmt.setString(1, selSpecies);
        if (!"--all--".equals(selCulture)) {
            stmt.setString(2, selCulture);
        }
        String[] columns = {"CultureName", "Age"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        if ("--all--".equals(selCulture)) {
            for (int i = 0; i < data.size(); i++) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName,Age) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[1] + "');");
            }
        } else {
            DatabaseModifier.executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName,Age) VALUES ('" + duplicateNewNameValue.getText() + "','" + selSpecies + "','" + data.get(0).toString().split("\\|")[1] + "');");
        }
    }

    private void duplicateClass(String selSpecies, String selCulture, String selClass) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedClasses WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        String[] columns = {"ClassName", "Skills", "CultureName", "Advancements", "Type", "BasedOn", "AdditionalCost"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        for (int i = 0; i < data.size(); i++) {
            if ("--all--".equals(selClass) && "--all--".equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + data.get(i).toString().split("\\|")[1] + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "','" + data.get(i).toString().split("\\|")[5] + "','" + data.get(i).toString().split("\\|")[6] + "');");
            } else if ("--all--".equals(selClass) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + data.get(i).toString().split("\\|")[1] + "','" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "','" + data.get(i).toString().split("\\|")[5] + "','" + data.get(i).toString().split("\\|")[6] + "');");
            } else if (!"--all--".equals(selClass) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selClass)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[1] + "','" + selSpecies + "','" + selCulture + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "','" + data.get(i).toString().split("\\|")[5] + "','" + data.get(i).toString().split("\\|")[6] + "');");
            }
        }
    }

    private void duplicateHero(String selSpecies, String selCulture, String selHero) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedHeroes WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        String[] columns = {"HeroName", "CultureName", "Advancements", "BasedOn", "AdditionalCost"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        for (int i = 0; i < data.size(); i++) {
            if ("--all--".equals(selHero) && "--all--".equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "');");
            } else if ("--all--".equals(selHero) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "');");
            } else if (!"--all--".equals(selHero) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selHero)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + duplicateNewNameValue.getText() + "','" + selSpecies + "','" + selCulture + "','" + data.get(i).toString().split("\\|")[2] + "','" + data.get(i).toString().split("\\|")[3] + "','" + data.get(i).toString().split("\\|")[4] + "');");
            }
        }
    }

    private void duplicateProgress(String selSpecies, String selCulture, String selProgress) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedProgress WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        String[] columns = {"CultureName", "ProgressName", "Progress"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        for (int i = 0; i < data.size(); i++) {
            if ("--all--".equals(selProgress) && "--all--".equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[0] + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "');");
            } else if ("--all--".equals(selProgress) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "');");
            } else if (!"--all--".equals(selProgress) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selProgress)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + selSpecies + "','" + selCulture + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[2] + "');");
            }
        }
    }

    private void duplicateRoster(String selSpecies, String selCulture, String selRoster) throws SQLException {
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT * FROM CreatedRoster WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        String[] columns = {"RosterName", "CultureName", "Roster"};
        ObservableList data = BuilderCORE.getData(stmt, columns, null, 0);
        for (int i = 0; i < data.size(); i++) {
            if ("--all--".equals(selRoster) && "--all--".equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedRoster`(RosterName,SpeciesName,CultureName,Roster) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[1] + "','" + data.get(i).toString().split("\\|")[2] + "');");
            } else if ("--all--".equals(selRoster) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedRoster`(RosterName,SpeciesName,CultureName,Roster) VALUES ('" + data.get(i).toString().split("\\|")[0] + "','" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + data.get(i).toString().split("\\|")[2] + "');");
            } else if (!"--all--".equals(selRoster) && !"--all--".equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selCulture) && data.get(i).toString().split("\\|")[2].equals(selRoster)) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedRoster`(RosterName,SpeciesName,CultureName,Roster) VALUES ('" + duplicateNewNameValue.getText() + "','" + selSpecies + "','" + selCulture + "','" + data.get(i).toString().split("\\|")[2] + "');");
            }
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
            speciesDuplicateDropdown.setItems(DatabaseModifier.populateDropdownsSpecies());
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
