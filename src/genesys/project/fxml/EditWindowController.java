/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.Enums.Enmuerations.DBTables;
import genesys.project.builder.Enums.Enmuerations.MainDomainValue;
import genesys.project.builder.Enums.Enmuerations.MainLineageValue;
import genesys.project.builder.Enums.Enmuerations.Modificators;
import genesys.project.builder.Enums.Enmuerations.UseCases;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import genesys.project.builder.DatabaseModifier;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import static genesys.project.builder.DatabaseModifier.holdSpecies;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class EditWindowController implements Initializable {

    /**
     * @FXML private EditWindow = new javax.swing.JDialog;
     *
     */
    @FXML
    private ComboBox speciesEditDropdown;
    @FXML
    private ComboBox cultureEditDropdown;
    @FXML
    private ComboBox classEditDropdown;
    @FXML
    private ComboBox heroEditDropdown;
    @FXML
    private ComboBox progressEditDropdown;
    @FXML
    private ComboBox rosterEditDropdown;
    @FXML
    private Label speciesEditLabel;
    @FXML
    private Label cultureEditLabel;
    @FXML
    private Label classEditLabel;
    @FXML
    private Label heroEditLabel;
    @FXML
    private Label progressEditLabel;
    @FXML
    private Label rosterEditLabel;
    @FXML
    private Button editFinishButton;
    @FXML
    private Button editCancelButton;
    @FXML
    private CheckBox editNameOnlyCheckbox;

    /**
     * speciesCreatorWindowStage
     */
    public Stage speciesCreatorWindowStage = new Stage();
    private SpeciesCreatorWindowController speciesCreatorWindowController;

    /**
     * createHoldWindowStage
     */
    public Stage createHoldWindowStage = new Stage();
    private CreateHoldWindowController createHoldWindowController;

    /**
     * classCreatorWindowStage
     */
    public Stage classCreatorWindowStage = new Stage();
    private ClassCreatorWindowController classCreatorWindowController;
    private ListView speciesList;

    /**
     * editWindowNameOnlyStage
     */
    public Stage editWindowNameOnlyStage = new Stage();
    private EditWindowNameOnlyController editWindowNameOnlyController;
    private Label editWindowNameOnlyText1;
    private Label editWindowNameOld;

    /**
     * editActions
     */
    @FXML
    public void editActions() {

    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void speciesEditDropdownItemStateChangedActions() throws SQLException {
        cultureEditDropdown.setItems(DatabaseModifier.populateDropdownsCultures(speciesEditDropdown.getSelectionModel().getSelectedItem().toString()));
        cultureEditDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void cultureEditDropdownItemStateChangedActions() throws SQLException {
        if (cultureEditDropdown.getSelectionModel().isEmpty()) {
            cultureEditDropdown.getSelectionModel().select(0);
        }
        classEditDropdown.setItems(DatabaseModifier.populateDropdownsClasses(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString()));
        classEditDropdown.getSelectionModel().select(0);
        heroEditDropdown.setItems(DatabaseModifier.populateDropdownsHeroes(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), classEditDropdown.getSelectionModel().getSelectedItem().toString()));
        heroEditDropdown.getSelectionModel().select(0);
        progressEditDropdown.setItems(DatabaseModifier.populateDropdownsProgress(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString()));
        progressEditDropdown.getSelectionModel().select(0);
        rosterEditDropdown.setItems(DatabaseModifier.populateDropdownsRosters(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString()));
        rosterEditDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void classEditDropdownItemStateChangedActions() throws SQLException {
        if (classEditDropdown.getSelectionModel().isEmpty()) {
            classEditDropdown.getSelectionModel().select(0);
        }
        Object sel = classEditDropdown.getSelectionModel().getSelectedItem();
        heroEditDropdown.setItems(DatabaseModifier.populateDropdownsHeroes(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), classEditDropdown.getSelectionModel().getSelectedItem().toString()));
        heroEditDropdown.getSelectionModel().select(0);
        progressEditDropdown.getSelectionModel().select(0);
        rosterEditDropdown.getSelectionModel().select(0);
        classEditDropdown.getSelectionModel().select(sel);
    }

    /**
     * heroEditDropdownItemStateChangedActions
     */
    @FXML
    public void heroEditDropdownItemStateChangedActions() {
        Object sel = heroEditDropdown.getSelectionModel().getSelectedItem();
        progressEditDropdown.getSelectionModel().select(0);
        rosterEditDropdown.getSelectionModel().select(0);
        heroEditDropdown.getSelectionModel().select(sel);
    }

    /**
     * progressEditDropdownItemStateChangedActions
     */
    @FXML
    public void progressEditDropdownItemStateChangedActions() {
        Object sel = progressEditDropdown.getSelectionModel().getSelectedItem();
        heroEditDropdown.getSelectionModel().select(0);
        rosterEditDropdown.getSelectionModel().select(0);
        classEditDropdown.getSelectionModel().select(0);
        progressEditDropdown.getSelectionModel().select(sel);
    }

    /**
     * rosterEditDropdownItemStateChangedActions
     */
    @FXML
    public void rosterEditDropdownItemStateChangedActions() {
        Object sel = rosterEditDropdown.getSelectionModel().getSelectedItem();
        heroEditDropdown.getSelectionModel().select(0);
        progressEditDropdown.getSelectionModel().select(0);
        classEditDropdown.getSelectionModel().select(0);
        rosterEditDropdown.getSelectionModel().select(sel);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws CloneNotSupportedException
     */
    @FXML
    public void editFinishButtonActions() throws IOException, SQLException, CloneNotSupportedException {
        Stage stage = (Stage) editFinishButton.getScene().getWindow();
        stage.hide();
        commenceEditing();
    }

    /**
     * editCancelButtonActions
     */
    @FXML
    public void editCancelButtonActions() {
        Stage stage = (Stage) editCancelButton.getScene().getWindow();
        stage.hide();
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws CloneNotSupportedException
     */
    public void commenceEditing() throws IOException, SQLException, CloneNotSupportedException {
        if (!speciesEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            DatabaseModifier.loadSpecies(speciesEditDropdown.getSelectionModel().getSelectedItem().toString());
        }
        if (!speciesEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && cultureEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseModifier.currentTable = DBTables.CreatedSpecies;
                toNameOnly();
                editWindowNameOld.setText(DatabaseModifier.holdSpecies.getSpeciesName());
                editWindowNameOnlyText1.setText("Change the folowing species name:");
            } else {
                modificator(Modificators.ModifySpecies);
            }
        }
        if (!cultureEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && classEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && heroEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && progressEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && rosterEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            DatabaseModifier.holdCulture = new DatabaseModifier.ACulture();
            DatabaseModifier.loadCulture(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseModifier.currentTable = DBTables.CreatedCultures;
                toNameOnly();
                editWindowNameOld.setText(DatabaseModifier.holdCulture.getCultureName());
                editWindowNameOnlyText1.setText("Change the folowing culture name:");
            } else {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT COUNT (*) FROM CreatedClasses WHERE SpeciesName=? AND CultureName =?");
                stmt.setString(1, speciesEditDropdown.getSelectionModel().getSelectedItem().toString());
                stmt.setString(2, cultureEditDropdown.getSelectionModel().getSelectedItem().toString());
                DatabaseModifier.numberOfClases = Integer.parseInt(BuilderCORE.getValue(stmt, "COUNT (*)"));
                DatabaseModifier.setNumberOfClases();
                for (int i = 0; i < DatabaseModifier.numberOfClases; i++) {
                    DatabaseModifier.loadClass(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), classEditDropdown.getItems().get(i + 1).toString(), i);
                }
                modificator(Modificators.ModifyCulture);
            }
        }

        if (!classEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP) && heroEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            DatabaseModifier.holdCulture = new DatabaseModifier.ACulture();
            DatabaseModifier.loadCulture(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString());
            DatabaseModifier.numberOfClases = 1;
            DatabaseModifier.setNumberOfClases();
            DatabaseModifier.loadClass(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), classEditDropdown.getSelectionModel().getSelectedItem().toString(), 0);
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseModifier.currentTable = DBTables.CreatedClasses;
                toNameOnly();
                editWindowNameOld.setText(DatabaseModifier.holdClass[0].getClassName());
                editWindowNameOnlyText1.setText("Change the folowing class name:");
            } else {
                modificator(Modificators.ModifyClass);
            }
        }

        if (!heroEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            DatabaseModifier.holdHero = new DatabaseModifier.AHero();
            DatabaseModifier.loadHero(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), heroEditDropdown.getSelectionModel().getSelectedItem().toString());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseModifier.currentTable = DBTables.CreatedHeroes;
                toNameOnly();
                editWindowNameOld.setText(DatabaseModifier.holdHero.getHeroName());
                editWindowNameOnlyText1.setText("Change the folowing hero name:");
            } else {
                modificator(Modificators.ModifyHero);
            }
        }

        if (!progressEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            DatabaseModifier.holdProgress = new DatabaseModifier.AProgress();
            DatabaseModifier.loadProgress(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), progressEditDropdown.getSelectionModel().getSelectedItem().toString());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseModifier.currentTable = DBTables.CreatedProgress;
                toNameOnly();
                editWindowNameOld.setText(DatabaseModifier.holdProgress.getProgressName());
                editWindowNameOnlyText1.setText("Change the folowing progress name:");
            } else {
                modificator(Modificators.ModifyProgress);
            }
        }

        if (!rosterEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseModifier.TOPDROP)) {
            DatabaseModifier.holdRoster = new DatabaseModifier.ARoster();
            DatabaseModifier.loadRoster(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString(), rosterEditDropdown.getSelectionModel().getSelectedItem().toString());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseModifier.currentTable = DBTables.CreatedRosters;
                toNameOnly();
                editWindowNameOld.setText(DatabaseModifier.holdRoster.getRosterName());
                editWindowNameOnlyText1.setText("Change the folowing roster name:");
            } else {
                modificator(Modificators.ModifyRoster);
            }
        }

    }

    /**
     *
     * @throws IOException
     */
    public void toNameOnly() throws IOException {
        if (editWindowNameOnlyStage.isShowing()) {
            editWindowNameOnlyStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/EditWindowNameOnlyFXML.fxml"));
            Parent root = loader.load();
            editWindowNameOnlyController = loader.getController();
            editWindowNameOnlyController.setSpeciesList(speciesList);
            editWindowNameOnlyText1 = editWindowNameOnlyController.getEditWindowNameOnlyText1();
            editWindowNameOld = editWindowNameOnlyController.getEditWindowNameOld();
            Scene scene = new Scene(root);
            editWindowNameOnlyStage.setScene(scene);
            editWindowNameOnlyStage.show();
        }
    }

    private void modificator(Enum what) throws IOException {

        switch (holdSpecies.getLifedomain()) {
            case Humanoid:
                DatabaseModifier.arcana = Boolean.valueOf(DatabaseModifier.holdSpecies.getSpeciesModifiers().split("=")[1]);
                break;
            case Fey:
                ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setMainDomain(MainDomainValue.getEnum(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1]));
                DatabaseModifier.outcasts = Boolean.valueOf(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[1].split("=")[1]);
                ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setSecondaryDomain(MainDomainValue.getEnum(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[2].split("=")[1]));
                break;
            case Reptilia:
                ((DatabaseModifier.AReptilia) DatabaseModifier.holdSpecies).setMainLineage(MainLineageValue.getEnum(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1]));
                DatabaseModifier.arcana = Boolean.valueOf(DatabaseModifier.holdSpecies.getSpeciesModifiers().split(",")[1].split("=")[1]);
                break;
            case Biest:
                break;
            case Insecta:
                break;
        }
        if (Modificators.ModifySpecies.equals(what)) {
            DatabaseModifier.isModyfyinfg = true;
            if (speciesCreatorWindowStage.isShowing()) {
                speciesCreatorWindowStage.requestFocus();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/SpeciesCreatorWindowFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                speciesCreatorWindowController = loader.getController();
                speciesCreatorWindowController.setSpeciesList(speciesList);
                speciesCreatorWindowStage.setScene(scene);
                speciesCreatorWindowStage.show();
            }
            //GenesysProjectBuilder.CORE.getCharacteristics(UseCases.CreatingSpecies,BuilderFXMLController.HOLD_MODIFIERS);
            //populateLabels();
        }
        if (Modificators.ModifyCulture.equals(what)) {
            DatabaseModifier.isModyfyinfg = true;
            if (createHoldWindowStage.isShowing()) {
                createHoldWindowStage.requestFocus();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateHoldWindowFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                createHoldWindowController = loader.getController();
                createHoldWindowController.setSpeciesList(speciesList);
                createHoldWindowStage.setScene(scene);
                createHoldWindowStage.show();
            }
        }
        if (Modificators.ModifyClass.equals(what)) {
            DatabaseModifier.classIsModyfying = true;
            if (classCreatorWindowStage.isShowing()) {
                classCreatorWindowStage.requestFocus();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/ClassCreatorWindowFXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                classCreatorWindowController = loader.getController();
                classCreatorWindowController.setSpeciesList(speciesList);
                classCreatorWindowStage.setScene(scene);
                classCreatorWindowStage.show();
            }
        }
        if (Modificators.ModifyHero.equals(what)) {
        }
        if (Modificators.ModifyProgress.equals(what)) {
        }
        if (Modificators.ModifyRoster.equals(what)) {
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
            speciesEditDropdown.setItems(DatabaseModifier.populateDropdownsSpecies());
        } catch (SQLException ex) {
            Logger.getLogger(EditWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        speciesEditDropdown.getSelectionModel().select(0);
        try {
            speciesEditDropdownItemStateChangedActions();
            cultureEditDropdownItemStateChangedActions();
        } catch (SQLException ex) {
            Logger.getLogger(EditWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
