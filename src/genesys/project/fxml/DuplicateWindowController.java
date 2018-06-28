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
import javafx.collections.FXCollections;
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
        String[] tmp = new String[4];
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomain FROM CreatedSpecies WHERE SpeciesName =?");
        stmt.setString(1, selSpecies);
        tmp[0] = BuilderCORE.getValue(stmt, "LifeDomain");
        tmp[1] = duplicateNewNameValue.getText();
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedSpecies WHERE SpeciesName =?");
        stmt1.setString(1, selSpecies);
        tmp[2] = BuilderCORE.getValue(stmt1, "Skills");
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT SpeciesModifiers FROM CreatedSpecies WHERE SpeciesName =?");
        stmt2.setString(1, selSpecies);
        tmp[3] = BuilderCORE.getValue(stmt2, "SpeciesModifiers");
        DatabaseModifier.executeSQL("INSERT INTO `CreatedSpecies`(LifeDomain,SpeciesName,Skills,SpeciesModifiers) VALUES ('" + tmp[0] + "','" + tmp[1] + "','" + tmp[2] + "','" + tmp[3] + "');");
    }

    private void duplicateCulture(String selSpecies, String selCulture) throws SQLException {
        if ("--all--".equals(selCulture)) {
            ObservableList tmp = FXCollections.observableArrayList();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedCultures WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"CultureName"};
            tmp.addAll(BuilderCORE.getData(stmt, columns, null));
            for (int i = 0; i < tmp.size(); i++) {
                DatabaseModifier.executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName) VALUES ('" + tmp.get(i) + "','" + duplicateNewNameValue.getText() + "');");
            }
        } else {
            String tmp = duplicateNewNameValue.getText();
            DatabaseModifier.executeSQL("INSERT INTO `CreatedCultures`(CultureName,SpeciesName) VALUES ('" + tmp + "','" + selSpecies + "');");
        }
    }

    private void duplicateClass(String selSpecies, String selCulture, String selClass) throws SQLException {
        if ("--all--".equals(selClass) && "--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassName FROM CreatedClasses WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"ClassName"};
            ObservableList lst = BuilderCORE.getData(stmt, columns, null);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedClasses WHERE SpeciesName =?");
            stmt1.setString(1, selSpecies);
            String[] columns1 = {"CultureName"};
            ObservableList lst1 = BuilderCORE.getData(stmt1, columns1, null);
            for (int i = 0; i < lst.size(); i++) {
                int[] tablesint = {1, 3, 4, 5, 6};
                String[] tmp = new String[7];
                tmp[0] = lst.get(i).toString();
                tmp[2] = lst1.get(i).toString();
                for (int j = 0; j < BuilderCORE.CLASS_TABLES.length; j++) {
                    chooseConnection(UseCases.Userdb);
                    PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT ? FROM CreatedClasses WHERE SpeciesName =? AND CultureName =? AND ClassName =?");
                    stmt2.setString(1, BuilderCORE.CLASS_TABLES[j]);
                    stmt2.setString(2, selSpecies);
                    stmt2.setString(3, tmp[2]);
                    stmt2.setString(4, tmp[0]);
                    tmp[tablesint[j]] = BuilderCORE.getValue(stmt2, BuilderCORE.CLASS_TABLES[j]);
                }
                DatabaseModifier.executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + tmp[0] + "','" + tmp[1] + "','" + duplicateNewNameValue.getText() + "','" + tmp[2] + "','" + tmp[3] + "','" + tmp[4] + "','" + tmp[5] + "','" + tmp[6] + "');");
            }
        } else if ("--all--".equals(selClass) && !"--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT ClassName FROM CreatedClasses WHERE SpeciesName =? AND CultureName =?");
            stmt3.setString(1, selSpecies);
            stmt3.setString(2, selCulture);
            String[] columns = {"ClassName"};
            ObservableList lst = BuilderCORE.getData(stmt3, columns, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[6];
                tmp[0] = lst.get(i).toString();
                for (int j = 0; j < BuilderCORE.CLASS_TABLES.length; j++) {
                    chooseConnection(UseCases.Userdb);
                    PreparedStatement stmt4 = BuilderCORE.getConnection().prepareStatement("SELECT ? FROM CreatedClasses WHERE SpeciesName =? AND CultureName =? AND ClassName =?");
                    stmt4.setString(1, BuilderCORE.CLASS_TABLES[j]);
                    stmt4.setString(2, selSpecies);
                    stmt4.setString(3, selCulture);
                    stmt4.setString(4, tmp[0]);
                    tmp[j + 1] = BuilderCORE.getValue(stmt4, BuilderCORE.CLASS_TABLES[j]);
                }
                DatabaseModifier.executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + tmp[0] + "','" + tmp[1] + "','" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + tmp[2] + "','" + tmp[3] + "','" + tmp[4] + "','" + tmp[5] + "');");
            }
        } else {
            String[] tmp = new String[6];
            tmp[0] = duplicateNewNameValue.getText();
            for (int j = 0; j < BuilderCORE.CLASS_TABLES.length; j++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt5 = BuilderCORE.getConnection().prepareStatement("SELECT ? FROM CreatedClasses WHERE SpeciesName =? AND CultureName =? AND ClassName =?");
                stmt5.setString(1, BuilderCORE.CLASS_TABLES[j]);
                stmt5.setString(2, selSpecies);
                stmt5.setString(3, selCulture);
                stmt5.setString(4, selClass);
                tmp[j + 1] = BuilderCORE.getValue(stmt5, BuilderCORE.CLASS_TABLES[j]);
            }
            DatabaseModifier.executeSQL("INSERT INTO `CreatedClasses`(ClassName,Skills,SpeciesName,CultureName,Advancements,Type,BasedOn,AdditionalCost) VALUES ('" + tmp[0] + "','" + tmp[1] + "','" + selSpecies + "','" + selCulture + "','" + tmp[2] + "','" + tmp[3] + "','" + tmp[4] + "','" + tmp[5] + "');");
        }
    }

    private void duplicateHero(String selSpecies, String selCulture, String selHero) throws SQLException {
        if ("--all--".equals(selHero) && "--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT HeroName FROM CreatedHeroes WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"HeroName"};
            ObservableList lst = BuilderCORE.getData(stmt, columns, null);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedHeroes WHERE SpeciesName =?");
            stmt1.setString(1, selSpecies);
            String[] columns1 = {"CultureName"};
            ObservableList lst1 = BuilderCORE.getData(stmt1, columns1, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[5];
                tmp[0] = lst.get(i).toString();
                tmp[1] = lst1.get(i).toString();
                for (int j = 0; j < BuilderCORE.HERO_TABLES.length; j++) {
                    chooseConnection(UseCases.Userdb);
                    PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT ? FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
                    stmt2.setString(1, BuilderCORE.HERO_TABLES[j]);
                    stmt2.setString(2, selSpecies);
                    stmt2.setString(3, tmp[1]);
                    stmt2.setString(4, tmp[0]);
                    tmp[j + 2] = BuilderCORE.getValue(stmt2, BuilderCORE.HERO_TABLES[j]);
                }
                DatabaseModifier.executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + tmp[0] + "','" + duplicateNewNameValue.getText() + "','" + tmp[1] + "','" + tmp[2] + "','" + tmp[3] + "','" + tmp[4] + "');");
            }
        } else if ("--all--".equals(selHero) && !"--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT HeroName FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =?");
            stmt3.setString(1, selSpecies);
            stmt3.setString(2, selCulture);
            String[] columns3 = {"HeroName"};
            ObservableList lst = BuilderCORE.getData(stmt3, columns3, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[4];
                tmp[0] = lst.get(i).toString();
                for (int j = 0; j < BuilderCORE.HERO_TABLES.length; j++) {
                    chooseConnection(UseCases.Userdb);
                    PreparedStatement stmt4 = BuilderCORE.getConnection().prepareStatement("SELECT ? FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
                    stmt4.setString(1, BuilderCORE.HERO_TABLES[j]);
                    stmt4.setString(2, selSpecies);
                    stmt4.setString(3, selCulture);
                    stmt4.setString(4, tmp[0]);
                    tmp[j + 1] = BuilderCORE.getValue(stmt4, BuilderCORE.HERO_TABLES[j]);
                }
                DatabaseModifier.executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + tmp[0] + "','" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + tmp[1] + "','" + tmp[2] + "','" + tmp[3] + "');");
            }
        } else {
            String[] tmp = new String[4];
            tmp[0] = duplicateNewNameValue.getText();
            for (int j = 0; j < BuilderCORE.HERO_TABLES.length; j++) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt5 = BuilderCORE.getConnection().prepareStatement("SELECT ? FROM CreatedHeroes WHERE SpeciesName =? AND CultureName =? AND HeroName =?");
                stmt5.setString(1, BuilderCORE.HERO_TABLES[j]);
                stmt5.setString(2, selSpecies);
                stmt5.setString(3, selCulture);
                stmt5.setString(4, selHero);
                tmp[j + 1] = BuilderCORE.getValue(stmt5, BuilderCORE.HERO_TABLES[j]);
            }
            DatabaseModifier.executeSQL("INSERT INTO `CreatedHeroes`(HeroName,SpeciesName,CultureName,Advancements,BasedOn,AdditionalCost) VALUES ('" + tmp[0] + "','" + selSpecies + "','" + selCulture + "','" + tmp[1] + "','" + tmp[2] + "','" + tmp[3] + "');");
        }
    }

    private void duplicateProgress(String selSpecies, String selCulture, String selProgress) throws SQLException {
        if ("--all--".equals(selProgress) && "--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ProgressName FROM CreatedProgress WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"ProgressName"};
            ObservableList lst = BuilderCORE.getData(stmt, columns, null);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedProgress WHERE SpeciesName =?");
            stmt1.setString(1, selSpecies);
            String[] columns1 = {"CultureName"};
            ObservableList lst1 = BuilderCORE.getData(stmt1, columns1, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[3];
                tmp[0] = lst.get(i).toString();
                tmp[1] = lst1.get(i).toString();
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Progress FROM CreatedProgress WHERE SpeciesName =? AND CultureName =? AND ProgressName =?");
                stmt2.setString(1, selSpecies);
                stmt2.setString(2, tmp[1]);
                stmt2.setString(3, tmp[0]);
                tmp[2] = BuilderCORE.getValue(stmt2, "Progress");
                DatabaseModifier.executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + duplicateNewNameValue.getText() + "','" + tmp[1] + "','" + tmp[0] + "','" + tmp[2] + "');");
            }
        } else if ("--all--".equals(selProgress) && !"--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT ProgressName FROM CreatedProgress WHERE SpeciesName =? AND CultureName =?");
            stmt3.setString(1, selSpecies);
            stmt3.setString(2, selCulture);
            String[] columns3 = {"ProgressName"};
            ObservableList lst = BuilderCORE.getData(stmt3, columns3, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[2];
                tmp[0] = lst.get(i).toString();
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt4 = BuilderCORE.getConnection().prepareStatement("SELECT Progress FROM CreatedProgress WHERE SpeciesName =? AND CultureName =? AND ProgressName =?");
                stmt4.setString(1, selSpecies);
                stmt4.setString(2, selCulture);
                stmt4.setString(3, tmp[0]);
                tmp[1] = BuilderCORE.getValue(stmt4, "Progress");
                DatabaseModifier.executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + tmp[0] + "','" + tmp[1] + "');");
            }
        } else {
            String[] tmp = new String[2];
            tmp[0] = duplicateNewNameValue.getText();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt5 = BuilderCORE.getConnection().prepareStatement("SELECT Progress FROM CreatedProgress WHERE SpeciesName =? AND CultureName =? AND ProgressName =?");
            stmt5.setString(1, selSpecies);
            stmt5.setString(2, selCulture);
            stmt5.setString(3, selProgress);
            tmp[1] = BuilderCORE.getValue(stmt5, "Progress");
            DatabaseModifier.executeSQL("INSERT INTO `CreatedProgress`(SpeciesName,CultureName,ProgressName,Progress) VALUES ('" + selSpecies + "','" + selCulture + "','" + tmp[0] + "','" + tmp[1] + "');");
        }
    }

    private void duplicateRoster(String selSpecies, String selCulture, String selRoster) throws SQLException {
        if ("--all--".equals(selRoster) && "--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT RosterName FROM CreatedRosters WHERE SpeciesName =?");
            stmt.setString(1, selSpecies);
            String[] columns = {"RosterName"};
            ObservableList lst = BuilderCORE.getData(stmt, columns, null);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedRosters WHERE SpeciesName =?");
            stmt1.setString(1, selSpecies);
            String[] columns1 = {"CultureName"};
            ObservableList lst1 = BuilderCORE.getData(stmt1, columns1, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[3];
                tmp[0] = lst.get(i).toString();
                tmp[1] = lst1.get(i).toString();
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Roster FROM CreatedRosters WHERE SpeciesName =? AND CultureName =? AND RosterName =?");
                stmt2.setString(1, selSpecies);
                stmt2.setString(2, tmp[1]);
                stmt2.setString(3, tmp[0]);
                tmp[2] = BuilderCORE.getValue(stmt2, "Roster");
                DatabaseModifier.executeSQL("INSERT INTO `CreatedRosters`(RosterName,SpeciesName,CultureName,Roster) VALUES ('" + tmp[0] + "','" + duplicateNewNameValue.getText() + "','" + tmp[1] + "','" + tmp[2] + "');");
            }
        } else if ("--all--".equals(selRoster) && !"--all--".equals(selCulture)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT RosterName FROM CreatedRosters WHERE SpeciesName =? AND CultureName =?");
            stmt3.setString(1, selSpecies);
            stmt3.setString(2, selCulture);
            String[] columns3 = {"RosterName"};
            ObservableList lst = BuilderCORE.getData(stmt3, columns3, null);
            for (int i = 0; i < lst.size(); i++) {
                String[] tmp = new String[2];
                tmp[0] = lst.get(i).toString();
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt4 = BuilderCORE.getConnection().prepareStatement("SELECT Roster FROM CreatedRosters WHERE SpeciesName =? AND CultureName =? AND RosterName =?");
                stmt4.setString(1, selSpecies);
                stmt4.setString(2, selCulture);
                stmt4.setString(3, tmp[0]);
                tmp[1] = BuilderCORE.getValue(stmt4, "Roster");
                DatabaseModifier.executeSQL("INSERT INTO `CreatedRosters`(RosterName,SpeciesName,CultureName,Roster) VALUES ('" + tmp[0] + "','" + selSpecies + "','" + duplicateNewNameValue.getText() + "','" + tmp[1] + "');");
            }
        } else {
            String[] tmp = new String[2];
            tmp[0] = duplicateNewNameValue.getText();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt5 = BuilderCORE.getConnection().prepareStatement("SELECT Roster FROM CreatedRosters WHERE SpeciesName =? AND CultureName =? AND RosterName =?");
            stmt5.setString(1, selSpecies);
            stmt5.setString(2, selCulture);
            stmt5.setString(3, selRoster);
            tmp[1] = BuilderCORE.getValue(stmt5, "Roster");
            DatabaseModifier.executeSQL("INSERT INTO `CreatedRosters`(RosterName,SpeciesName,CultureName,Roster) VALUES ('" + tmp[0] + "','" + selSpecies + "','" + selCulture + "','" + tmp[1] + "');");
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

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

    void setDuplicateWindowController(DuplicateWindowController duplicateWindowController) {
        this.duplicateWindowController = duplicateWindowController;
    }

}
