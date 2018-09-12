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
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseHolder.IDDataSet;
import genesys.project.builder.DatabaseHolder.mainWindowData;
import genesys.project.builder.DatabaseReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

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

    /**
     * rosterCreatorWindowStage
     */
    public Stage rosterCreatorWindowStage = new Stage();
    private RosterCreatorWindowController rosterCreatorWindowController;

    //private ListView speciesList;
    //private ListView cultureList;
    //private ListView rosterList;
    //private ListView cultureProgressList;
    /**
     * editWindowNameOnlyStage
     */
    public Stage editWindowNameOnlyStage = new Stage();
    private EditWindowNameOnlyController editWindowNameOnlyController;
    private Label editWindowNameOnlyText1;
    private Label editWindowNameOld;
    private mainWindowData mainWindowDataPropagator;
    private IDDataSet iDDataPropagator;

    /**
     * culturesList - culturesId Map
     */
    //public static Map culturesIdMap;
    public static Map classesIdMap;

    /**
     * editActions
     */
    @FXML
    public void editActions() {

    }

    /**
     * speciesEditDropdownItemStateChangedActions
     */
    @FXML
    public void speciesEditDropdownItemStateChangedActions() {
        iDDataPropagator.setSpeciesID(Integer.parseInt(mainWindowDataPropagator.getSpeciesIdMap().get(mainWindowDataPropagator.getSpeciesList().getSelectionModel().getSelectedIndex()).toString()));
        cultureEditDropdown.setItems(DatabaseReader.populateDropdownsCultures(iDDataPropagator.getSpeciesID()));
        cultureEditDropdown.getSelectionModel().select(0);
    }

    /**
     * cultureEditDropdownItemStateChangedActions
     */
    @FXML
    public void cultureEditDropdownItemStateChangedActions() {
        if (cultureEditDropdown.getSelectionModel().isEmpty()) {
            cultureEditDropdown.getSelectionModel().select(0);
        }
        iDDataPropagator.setCultureID(DatabaseReader.getCultureID(iDDataPropagator.getSpeciesID(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString()));
        classEditDropdown.setItems(DatabaseReader.populateDropdownsClasses(iDDataPropagator.getCultureID()));
        classEditDropdown.getSelectionModel().select(0);

        heroEditDropdown.setItems(DatabaseReader.populateDropdownsHeroes(iDDataPropagator.getCultureID(), iDDataPropagator.getClassID()));
        heroEditDropdown.getSelectionModel().select(0);

        progressEditDropdown.setItems(FXCollections.observableArrayList(DatabaseHolder.TOPDROP));
        progressEditDropdown.getItems().addAll(
                BuilderCORE.generateProgressAndBattlesSortedList(
                        speciesEditDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureEditDropdown.getSelectionModel().getSelectedItem().toString(),
                        "both"));
        progressEditDropdown.getSelectionModel().select(0);

        rosterEditDropdown.setItems(DatabaseReader.populateDropdownsRosters(iDDataPropagator.getCultureID()));
        rosterEditDropdown.getSelectionModel().select(0);
    }

    /**
     * classEditDropdownItemStateChangedActions
     */
    @FXML
    public void classEditDropdownItemStateChangedActions() {
        if (classEditDropdown.getSelectionModel().isEmpty()) {
            classEditDropdown.getSelectionModel().select(0);
        }
        iDDataPropagator.setClassID(DatabaseReader.getClassID(iDDataPropagator.getCultureID(), classEditDropdown.getSelectionModel().getSelectedItem().toString()));
        Object sel = classEditDropdown.getSelectionModel().getSelectedItem();
        heroEditDropdown.setItems(DatabaseReader.populateDropdownsHeroes(iDDataPropagator.getCultureID(), iDDataPropagator.getClassID()));
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
        iDDataPropagator.setHeroID(DatabaseReader.getHeroID(iDDataPropagator.getCultureID(), heroEditDropdown.getSelectionModel().getSelectedItem().toString()));
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
        iDDataPropagator.setRosterID(DatabaseReader.getHeroID(iDDataPropagator.getCultureID(), rosterEditDropdown.getSelectionModel().getSelectedItem().toString()));
        Object sel = rosterEditDropdown.getSelectionModel().getSelectedItem();
        heroEditDropdown.getSelectionModel().select(0);
        progressEditDropdown.getSelectionModel().select(0);
        classEditDropdown.getSelectionModel().select(0);
        rosterEditDropdown.getSelectionModel().select(sel);
    }

    /**
     * editFinishButtonActions
     */
    @FXML
    public void editFinishButtonActions() {
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
     * commenceEditing
     */
    public void commenceEditing() {
        if (!speciesEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseHolder.loadSpeciesToHold(iDDataPropagator.getSpeciesID());
        }
        if (!speciesEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && cultureEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseHolder.currentTable = DBTables.CreatedSpecies;
                toNameOnly();
                editWindowNameOld.setText(DatabaseHolder.holdSpecies.getSpeciesName());
                editWindowNameOnlyText1.setText("Change the folowing species name:");
            } else {
                modificator(Modificators.ModifySpecies);
            }
        }
        if (!cultureEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && classEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && heroEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && progressEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && rosterEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseHolder.holdCulture = new DatabaseHolder.ACulture();
            DatabaseHolder.loadCultureToHold(iDDataPropagator.getCultureID());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseHolder.currentTable = DBTables.CreatedCultures;
                toNameOnly();
                editWindowNameOld.setText(DatabaseHolder.holdCulture.getCultureName());
                editWindowNameOnlyText1.setText("Change the folowing culture name:");
            } else {
                DatabaseHolder.numberOfClases = DatabaseReader.getNumberOfClases(speciesEditDropdown.getSelectionModel().getSelectedItem().toString(), cultureEditDropdown.getSelectionModel().getSelectedItem().toString());
                BuilderCORE.setNumberOfClases();
                DatabaseHolder.modifiedHoldClass = new DatabaseHolder.AClass[DatabaseHolder.holdClass.length];
                for (int i = 0; i < DatabaseHolder.numberOfClases; i++) {

                    DatabaseHolder.loadClassToHold(iDDataPropagator.getClassID() /*classEditDropdown.getItems().get(i + 1).toString()*/, i);
                }
                modificator(Modificators.ModifyCulture);
            }
        }

        if (!classEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP) && heroEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseHolder.holdCulture = new DatabaseHolder.ACulture();
            DatabaseHolder.loadCultureToHold(iDDataPropagator.getCultureID());
            DatabaseHolder.numberOfClases = 1;
            BuilderCORE.setNumberOfClases();
            DatabaseHolder.modifiedHoldClass = new DatabaseHolder.AClass[1];
            DatabaseHolder.loadClassToHold(iDDataPropagator.getClassID(), 0);
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseHolder.currentTable = DBTables.CreatedClasses;
                toNameOnly();
                editWindowNameOld.setText(DatabaseHolder.holdClass[0].getClassName());
                editWindowNameOnlyText1.setText("Change the folowing class name:");
            } else {
                modificator(Modificators.ModifyClass);
            }
        }

        if (!heroEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseHolder.holdHero = new DatabaseHolder.AHero();
            DatabaseHolder.loadHeroToHold(iDDataPropagator.getHeroID());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseHolder.currentTable = DBTables.CreatedHeroes;
                toNameOnly();
                editWindowNameOld.setText(DatabaseHolder.holdHero.getHeroName());
                editWindowNameOnlyText1.setText("Change the folowing hero name:");
            } else {
                modificator(Modificators.ModifyHero);
            }
        }

        if (!progressEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            String progressType = "";
            String textName = "";
            if (progressEditDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Progress: ")) {
                progressType = "progress";
                DatabaseHolder.holdProgress = new DatabaseHolder.AProgress();
                iDDataPropagator.setProgressID(DatabaseReader.getProgressID(iDDataPropagator.getCultureID(), progressEditDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Progress: ", "")));
                DatabaseHolder.loadProgressToHold(iDDataPropagator.getProgressID());
                textName = DatabaseHolder.holdProgress.getProgressName();
            }
            if (progressEditDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Battle: ")) {
                progressType = "battle";
                DatabaseHolder.holdBattle = new DatabaseHolder.ABattle();
                iDDataPropagator.setBattleID(DatabaseReader.getBattleID(iDDataPropagator.getCultureID(), progressEditDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Battle: ", "")));
                DatabaseHolder.loadBattleToHold(iDDataPropagator.getBattleID());
                textName = DatabaseHolder.holdBattle.getBattleName();
            }
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseHolder.currentTable = DBTables.CreatedProgress;
                toNameOnly();
                editWindowNameOld.setText(textName);
                editWindowNameOnlyText1.setText("Change the folowing " + progressType + " name:");
            } else {
                modificator(Modificators.ModifyProgress);
            }
        }

        if (!rosterEditDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            DatabaseHolder.holdRoster = new DatabaseHolder.ARoster();
            DatabaseHolder.loadRosterToHold(iDDataPropagator.getRosterID());
            if (editNameOnlyCheckbox.isSelected()) {
                DatabaseHolder.currentTable = DBTables.CreatedRosters;
                toNameOnly();
                editWindowNameOld.setText(DatabaseHolder.holdRoster.getRosterName());
                editWindowNameOnlyText1.setText("Change the folowing roster name:");
            } else {
                DatabaseHolder.loadSpeciesToHold(iDDataPropagator.getSpeciesID());
                DatabaseHolder.holdCulture = new DatabaseHolder.ACulture();
                DatabaseHolder.loadCultureToHold(iDDataPropagator.getCultureID());
                ObservableList classesList = DatabaseReader.populateDropdownsClasses(iDDataPropagator.getCultureID());
                classesList.remove(DatabaseHolder.TOPDROP);
                DatabaseHolder.holdClass = new DatabaseHolder.AClass[classesList.size()];
                DatabaseHolder.modifiedHoldClass = new DatabaseHolder.AClass[DatabaseHolder.holdClass.length];
                for (int i = 0; i < classesList.size(); i++) {
                    DatabaseHolder.holdClass[i] = new DatabaseHolder.AClass();
                    DatabaseHolder.holdClass[i].clearAClass();
                    DatabaseHolder.loadClassToHold(iDDataPropagator.getClassID(), i);
                }
                modificator(Modificators.ModifyRoster);
            }
        }

    }

    /**
     * toNameOnly
     */
    public void toNameOnly() {
        if (editWindowNameOnlyStage.isShowing()) {
            editWindowNameOnlyStage.requestFocus();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/EditWindowNameOnlyFXML.fxml"));
                Parent root = loader.load();
                editWindowNameOnlyController = loader.getController();
                editWindowNameOnlyController.setDataLists(mainWindowDataPropagator);
                editWindowNameOnlyController.setIDData(iDDataPropagator);
                editWindowNameOnlyText1 = editWindowNameOnlyController.getEditWindowNameOnlyText1();
                editWindowNameOld = editWindowNameOnlyController.getEditWindowNameOld();
                Scene scene = new Scene(root);
                editWindowNameOnlyStage.setScene(scene);
                editWindowNameOnlyStage.setTitle("Edit Name");
                editWindowNameOnlyStage.show();
            } catch (IOException ex) {
                ErrorController.ErrorControllerMethod(ex);
                Logger.getLogger(EditWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void modificator(Enum what) {
        try {
            switch (DatabaseHolder.holdSpecies.getLifedomain()) {
                case Humanoid:
                    DatabaseHolder.arcana = Boolean.valueOf(DatabaseHolder.holdSpecies.getSpeciesModifiers().split("=")[1]);
                    break;
                case Fey:
                    ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setMainDomain(MainDomainValue.getEnum(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1]));
                    DatabaseHolder.outcasts = Boolean.valueOf(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[1].split("=")[1]);
                    ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setSecondaryDomain(MainDomainValue.getEnum(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[2].split("=")[1]));
                    break;
                case Reptilia:
                    ((DatabaseHolder.AReptilia) DatabaseHolder.holdSpecies).setMainLineage(MainLineageValue.getEnum(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[0].split("=")[1]));
                    DatabaseHolder.arcana = Boolean.valueOf(DatabaseHolder.holdSpecies.getSpeciesModifiers().split(",")[1].split("=")[1]);
                    break;
                case Biest:
                    break;
                case Insecta:
                    break;
            }
            DatabaseHolder.isModyfying = true;
            if (Modificators.ModifySpecies.equals(what)) {
                if (speciesCreatorWindowStage.isShowing()) {
                    speciesCreatorWindowStage.requestFocus();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/SpeciesCreatorWindowFXML.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    speciesCreatorWindowController = loader.getController();
                    speciesCreatorWindowController.setDataLists(mainWindowDataPropagator);
                    speciesCreatorWindowController.setIDData(iDDataPropagator);
                    speciesCreatorWindowStage.setScene(scene);
                    speciesCreatorWindowStage.setTitle("Modify Species");
                    speciesCreatorWindowStage.show();
                }
                //GenesysProjectBuilder.CORE.getCharacteristics(UseCases.CreatingSpecies,BuilderFXMLController.HOLD_MODIFIERS);
                //populateLabels();
            }
            if (Modificators.ModifyCulture.equals(what)) {
                if (createHoldWindowStage.isShowing()) {
                    createHoldWindowStage.requestFocus();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateHoldWindowFXML.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    createHoldWindowController = loader.getController();
                    createHoldWindowController.setDataLists(mainWindowDataPropagator);
                    createHoldWindowController.setIDData(iDDataPropagator);
                    createHoldWindowStage.setScene(scene);
                    createHoldWindowStage.setTitle("Modify Classes");
                    createHoldWindowStage.show();
                }
            }
            if (Modificators.ModifyClass.equals(what)) {
                if (classCreatorWindowStage.isShowing()) {
                    classCreatorWindowStage.requestFocus();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/ClassCreatorWindowFXML.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    classCreatorWindowController = loader.getController();
                    classCreatorWindowController.setDataLists(mainWindowDataPropagator);
                    classCreatorWindowController.setIDData(iDDataPropagator);
                    classCreatorWindowStage.setScene(scene);
                    createHoldWindowStage.setTitle("Modify Class");
                    classCreatorWindowStage.show();
                }
            }
            if (Modificators.ModifyHero.equals(what)) {
            }
            if (Modificators.ModifyProgress.equals(what)) {
                //TODO progress creation window;
            }
            if (Modificators.ModifyRoster.equals(what)) {
                if (rosterCreatorWindowStage.isShowing()) {
                    rosterCreatorWindowStage.requestFocus();
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/RosterCreatorWindowFXML.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        rosterCreatorWindowController = loader.getController();
                        rosterCreatorWindowController.setMaxPoints(DatabaseHolder.holdRoster.getMaxPoints());
                        rosterCreatorWindowController.setIDData(iDDataPropagator);
                        rosterCreatorWindowStage.setScene(scene);
                        rosterCreatorWindowStage.setTitle("Create Roster " + DatabaseHolder.holdRoster.getRosterName() + " for " + DatabaseHolder.holdSpecies.getSpeciesName() + " - " + DatabaseHolder.holdCulture.getCultureName());
                        rosterCreatorWindowStage.show();
                    } catch (IOException ex) {
                        ErrorController.ErrorControllerMethod(ex);
                        Logger.getLogger(CreateRosterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(EditWindowController.class.getName()).log(Level.SEVERE, null, ex);
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
        speciesEditDropdown.setItems(DatabaseReader.populateDropdownsSpecies());
        speciesEditDropdown.getSelectionModel().select(0);
        speciesEditDropdownItemStateChangedActions();
        cultureEditDropdownItemStateChangedActions();
    }

    void setDataLists(mainWindowData mainWindowDataPropagator) {
        this.mainWindowDataPropagator = mainWindowDataPropagator;
    }

    void editWhat() {
        if (!mainWindowDataPropagator.getSpeciesList().getSelectionModel().isEmpty()) {
            speciesEditDropdown.getSelectionModel().select(mainWindowDataPropagator.getSpeciesList().getSelectionModel().getSelectedItem());
            speciesEditDropdownItemStateChangedActions();
            if (!mainWindowDataPropagator.getCulturesList().getSelectionModel().isEmpty() && !mainWindowDataPropagator.getSpeciesList().isFocused()) {
                cultureEditDropdown.getSelectionModel().select(mainWindowDataPropagator.getCulturesList().getSelectionModel().getSelectedItem());
                cultureEditDropdownItemStateChangedActions();
                if (!mainWindowDataPropagator.getRostersList().getSelectionModel().isEmpty() && !mainWindowDataPropagator.getCulturesList().isFocused()) {
                    rosterEditDropdown.getSelectionModel().select(mainWindowDataPropagator.getRostersList().getSelectionModel().getSelectedItem());
                    rosterEditDropdownItemStateChangedActions();
                }
                if (!mainWindowDataPropagator.getCulturesProgressList().getSelectionModel().isEmpty() && !mainWindowDataPropagator.getCulturesList().isFocused()) {
                    progressEditDropdown.getSelectionModel().select(mainWindowDataPropagator.getCulturesProgressList().getSelectionModel().getSelectedItem());
                    rosterEditDropdownItemStateChangedActions();
                }
            }
        }
        commenceEditing();
    }

}
