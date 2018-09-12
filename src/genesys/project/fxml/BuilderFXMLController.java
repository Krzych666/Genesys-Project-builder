/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseHolder.TheModifiers;
import genesys.project.builder.DatabaseHolder.mainWindowData;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.Enums.Enmuerations.CharacteristicGroup;
import genesys.project.builder.GenesysProjectBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class BuilderFXMLController implements Initializable {

    /**
     * holding modifiers
     */
    public static final TheModifiers HOLD_MODIFIERS = new TheModifiers();

    /**
     * LifedomainValue currentLifeDomain
     */
    public static LifedomainValue currentLifeDomain;

    /**
     * CharacteristicGroup currentCharacteristicGroup
     */
    public static CharacteristicGroup currentCharacteristicGroup;

    @FXML
    private ListView speciesList;
    /**
     * speciesList - speciesId Map
     */
    private static Map speciesIdMap;
    private static int speciesID;
    @FXML
    private ListView classList;
    /**
     * classList - classesId Map
     */
    public static Map classesIdMap;
    public static int classID;

    @FXML
    private ListView rostersList;
    /**
     * rostersList - rosterId Map
     */
    public static Map rosterIdMap;
    public static int rosterID;

    @FXML
    private ListView culturesList;
    /**
     * culturesList - culturesId Map
     */
    public static Map culturesIdMap;
    public static int cultureID;

    @FXML
    private ListView cultureProgressList;
    /**
     * cultureProgressList - progressId Map
     */
    public static Map progressIdMap;
    public static int progressID;

    @FXML
    private ListView heroesList;
    /**
     * heroesList - heroesId Map
     */
    public static Map heroesIdMap;
    public static int heroID;

    @FXML
    private ListView skillsList;
    @FXML
    private ListView subSkillsList;
    @FXML
    private TextArea skillFullText;
    @FXML
    private ListView fullroster;
    @FXML
    private TextField strengthValue;
    @FXML
    private TextField toughnessValue;
    @FXML
    private TextField movementValue;
    @FXML
    private TextField martialValue;
    @FXML
    private TextField rangedValue;
    @FXML
    private TextField defenseValue;
    @FXML
    private TextField disciplineValue;
    @FXML
    private TextField willpowerValue;
    @FXML
    private TextField commandValue;
    @FXML
    private TextField mTValue;
    @FXML
    private TextField rTValue;
    @FXML
    private TextField moraleValue;
    @FXML
    private TextField pointsPerModelValue;
    @FXML
    private TextField woundsValue;
    @FXML
    private TextField sizeValue;
    @FXML
    private TextField attacksValue;
    @FXML
    private TextField classTypeValue;
    @FXML
    private TextField basedOnValue;
    @FXML
    private TextField ageValue;
    @FXML
    private TextField totalProgressPointsValue;
    @FXML
    private TextField remainingProgressPointsValue;
    @FXML
    private CheckBox showAsCoreSkills;
    @FXML
    private Menu progressMenu;
    @FXML
    private Menu battlesMenu;
    @FXML
    private MenuItem editProgressMenuItem;
    @FXML
    private MenuItem deleteProgressMenuItem;
    @FXML
    private MenuItem editBattleMenuItem;
    @FXML
    private MenuItem deleteBattleMenuItem;
    /**
     * createWindowWhatStage
     */
    public Stage createWindowWhatStage = new Stage();

    /**
     * createWindowWhatController
     */
    public CreateWindowWhatController createWindowWhatController;

    /**
     * editWindowStage
     */
    public Stage editWindowStage = new Stage();

    /**
     * editWindowController
     */
    public EditWindowController editWindowController;

    /**
     * duplicateWindowStage
     */
    public Stage duplicateWindowStage = new Stage();

    /**
     * duplicateWindowController
     */
    public DuplicateWindowController duplicateWindowController;

    /**
     * deleteWindowStage
     */
    public Stage deleteWindowStage = new Stage();

    /**
     * deleteWindowController
     */
    public DeleteWindowController deleteWindowController;

    private TextField[] valuesLabels;
    private Boolean simplifyToCoreSkills;
    private mainWindowData mainWindowDataPropagator;

    MenuItem createIt = new MenuItem("Create new");
    MenuItem editIt = new MenuItem("Edit selected");
    MenuItem duplicateIt = new MenuItem("Duplicate selected");
    MenuItem deleteIt = new MenuItem("Delete selected");
    final ContextMenu rightClickContextMenu = new ContextMenu(createIt, editIt, duplicateIt, deleteIt);

    private void editActionPerformed(String What) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/EditWindowFXML.fxml"));
            Parent root = loader.load();
            editWindowController = loader.getController();
            editWindowController.setDataLists(mainWindowDataPropagator);
            Scene scene = new Scene(root);
            editWindowStage.setScene(scene);
            editWindowStage.setTitle("Edit");
            if (What.equals("Choose")) {
                editWindowStage.show();
            } else {
                editWindowController.editWhat();
            }
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editChooseActionPerformed() {
        editActionPerformed("Choose");
    }

    private void createActionPerformed(String What, String Selection) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateWindowWhatFXML.fxml"));
            Parent root = loader.load();
            createWindowWhatController = loader.getController();
            createWindowWhatController.setDataLists(mainWindowDataPropagator);
            Scene scene = new Scene(root);
            createWindowWhatStage.setScene(scene);
            createWindowWhatStage.setTitle("Create");
            if (What.equals("Choose")) {
                createWindowWhatStage.show();
            } else {
                createWindowWhatController.newWhat(What, Selection);
            }
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createChooseActionPerformed() {
        createActionPerformed("Choose", null);
    }

    @FXML
    private void createSpeciesActionPerformed() {
        createActionPerformed("Species", null);
    }

    @FXML
    private void createCultureActionPerformed() {
        createActionPerformed("Culture", null);
    }

    @FXML
    private void createRosterActionPerformed() {
        createActionPerformed("Roster", null);
    }

    @FXML
    private void createHumanoidActionPerformed() {
        createActionPerformed("Humanoid", null);
    }

    @FXML
    private void createFeyActionPerformed() {
        createActionPerformed("Fey", null);
    }

    @FXML
    private void createReptiliaActionPerformed() {
        createActionPerformed("Reptilia", null);
    }

    @FXML
    private void createBiestActionPerformed() {
        createActionPerformed("Biest", null);
    }

    @FXML
    private void createInsectaActionPerformed() {
        createActionPerformed("Insecta", null);
    }

    private void duplicateActionPerformed(String menuItem) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DuplicateWindowFXML.fxml"));
            Parent root = loader.load();
            duplicateWindowController = loader.getController();
            duplicateWindowController.setDataLists(mainWindowDataPropagator);
            duplicateWindowController.setDuplicateWindowController(duplicateWindowController);
            Scene scene = new Scene(root);
            duplicateWindowStage.setScene(scene);
            duplicateWindowStage.setTitle("Duplicate");
            if (!menuItem.equals("Choose")) {
                duplicateWindowController.setSelected();
            }
            duplicateWindowStage.show();
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void duplicateChooseActionPerformed() {
        duplicateActionPerformed("Choose");
    }

    private void deleteActionPerformed(String menuItem) {
        try {
            GenesysProjectBuilder.hideOtherThanMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DeleteWindowFXML.fxml"));
            Parent root = loader.load();
            deleteWindowController = loader.getController();
            deleteWindowController.setDataLists(mainWindowDataPropagator);
            Scene scene = new Scene(root);
            deleteWindowStage.setScene(scene);
            deleteWindowStage.setTitle("Delete");
            if (!menuItem.equals("Choose")) {
                deleteWindowController.setSelected();
            }
            deleteWindowStage.show();
        } catch (IOException ex) {
            ErrorController.ErrorControllerMethod(ex);
            Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteChooseActionPerformed() {
        deleteActionPerformed("Choose");
    }

    @FXML
    private void speciesListMousePressed() {
        if (!speciesList.getSelectionModel().isEmpty()) {
            speciesID = Integer.parseInt(speciesIdMap.get(speciesList.getSelectionModel().getSelectedIndex()).toString());
            HOLD_MODIFIERS.clearModifiers();
            ObservableList dataSpecies = DatabaseReader.getSpeciesData(speciesID);
            currentLifeDomain = LifedomainValue.valueOf(dataSpecies.get(0).toString().split("\\|")[0]);
            culturesList.setItems(DatabaseReader.populateDropdownsCultures(speciesID));
            culturesList.getItems().remove(DatabaseHolder.TOPDROP);
            clearListsOnCultureClick();
            currentCharacteristicGroup = (CharacteristicGroup.valueOf(dataSpecies.get(0).toString().split("\\|")[1]));
            String simpleSkillList = dataSpecies.get(0).toString().split("\\|")[3];
            skillsList.setItems(BuilderCORE.getFullSkillsDescription(simpleSkillList));
            int cost = BuilderCORE.baseCost(speciesID, 0, 0, 0, heroesList.getSelectionModel());
            pointsPerModelValue.setText(Integer.toString(cost));
            populateLabels();
            subSkillsList.getItems().clear();
            skillFullText.setText("");
            ageValue.setText("");
            totalProgressPointsValue.setText("");
            remainingProgressPointsValue.setText("");
            progressMenu.setDisable(true);
            battlesMenu.setDisable(true);
        }
    }

    @FXML
    private void culturesListMousePressed() {
        if (!culturesList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty()) {
            ObservableList dataSpecies = DatabaseReader.getSpeciesData(speciesID);
            skillsList.setItems(BuilderCORE.getFullSkillsDescription(dataSpecies.get(0).toString().split("\\|")[3]));
            ObservableList tmp = FXCollections.observableArrayList();
            tmp.add(BuilderCORE.BASE);
            if (LifedomainValue.Fey.equals(currentLifeDomain)) {
                tmp.add(BuilderCORE.LESSER);
                tmp.add(BuilderCORE.GREATER);
            }
            cultureID = Integer.parseInt(culturesIdMap.get(culturesList.getSelectionModel().getSelectedIndex()).toString());
            ObservableList dataCulture = DatabaseReader.getCultureData(cultureID);

            classList.setItems(DatabaseReader.populateDropdownsClasses(cultureID));
            classList.getItems().remove(DatabaseHolder.TOPDROP);

            heroesList.setItems(DatabaseReader.populateDropdownsHeroes(cultureID, 0));
            heroesList.getItems().remove(DatabaseHolder.TOPDROP);

            rostersList.setItems(DatabaseReader.populateDropdownsRosters(cultureID));
            rostersList.getItems().remove(DatabaseHolder.TOPDROP);

            populateLabels();
            ageValue.setText(dataCulture.get(0).toString().split("\\|")[0]);
            totalProgressPointsValue.setText(dataCulture.get(0).toString().split("\\|")[1]);
            remainingProgressPointsValue.setText(dataCulture.get(0).toString().split("\\|")[2]);

            cultureProgressList.setItems(
                    BuilderCORE.generateProgressAndBattlesSortedList(
                            speciesList.getSelectionModel().getSelectedItem().toString(),
                            culturesList.getSelectionModel().getSelectedItem().toString(),
                            "both"));

            progressMenu.setDisable(false);
            battlesMenu.setDisable(false);
            if (cultureProgressList.getItems().isEmpty()) {
                editProgressMenuItem.setDisable(true);
                deleteProgressMenuItem.setDisable(true);
                editBattleMenuItem.setDisable(true);
                deleteBattleMenuItem.setDisable(true);
            } else {
                editProgressMenuItem.setDisable(false);
                deleteProgressMenuItem.setDisable(false);
                editBattleMenuItem.setDisable(false);
                deleteBattleMenuItem.setDisable(false);
            }
        }
        fullroster.getItems().clear();
        subSkillsList.getItems().clear();
        skillFullText.setText("");
    }

    @FXML
    private void classListMousePressed() {
        if (!classList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            heroesList.getSelectionModel().clearSelection();
            classID = Integer.parseInt(classesIdMap.get(classList.getSelectionModel().getSelectedIndex()).toString());
            loadClassDataIntoFront(classID);
        }
    }

    private void loadClassDataIntoFront(int classID) {
        String simpleSkillList;
        ObservableList dataSpecies = DatabaseReader.getSpeciesData(speciesID);
        if (classID == 0) {
            simpleSkillList = dataSpecies.get(0).toString().split("\\|")[3];
            classTypeValue.setText("");
            basedOnValue.setText("");
        } else {
            ObservableList dataClass = DatabaseReader.getClassData(classID);
            simpleSkillList = BuilderCORE.getSourceBaseSkills(dataSpecies, dataClass, speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString());
            classTypeValue.setText(dataClass.get(0).toString().split("\\|")[4]);
            basedOnValue.setText(dataClass.get(0).toString().split("\\|")[5]);
        }
        skillsList.setItems(BuilderCORE.getFullSkillsDescription(simpleSkillList));
        int cost = BuilderCORE.baseCost(speciesID, cultureID, classID, 0, heroesList.getSelectionModel());
        pointsPerModelValue.setText(Integer.toString(cost));
        populateLabels();
        subSkillsList.getItems().clear();
        skillFullText.setText("");
    }

    @FXML
    private void heroesListMousePressed() {
        if (!heroesList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            classList.getSelectionModel().clearSelection();
            heroID = Integer.parseInt(heroesIdMap.get(heroesList.getSelectionModel().getSelectedIndex()).toString());
            loadHeroDataIntoFront(heroID);
        }
    }

    private void loadHeroDataIntoFront(int HeroName) {
        ObservableList dataSpecies = DatabaseReader.getSpeciesData(speciesID);
        ObservableList dataHero = DatabaseReader.getHeroData(HeroName);
        ObservableList dataClass = DatabaseReader.getClassData(Integer.parseInt(classesIdMap.get(dataHero.get(0).toString().split("\\|")[3]).toString()));
        String simpleSkillList = BuilderCORE.getSourceBaseSkills(dataSpecies, dataClass, speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString());
        skillsList.setItems(BuilderCORE.getFullSkillsDescription(simpleSkillList));
        basedOnValue.setText(dataHero.get(0).toString().split("\\|")[1]);
        classTypeValue.setText(dataClass.get(0).toString().split("\\|")[4]);
        int cost = BuilderCORE.baseCost(speciesID, cultureID, Integer.parseInt(dataHero.get(0).toString().split("\\|")[3]), 0, heroesList.getSelectionModel());
        pointsPerModelValue.setText(Integer.toString(cost));
        populateLabels();
        subSkillsList.getItems().clear();
        skillFullText.setText("");
    }

    private void progresActions(String what, String selectedProgress) {
        switch (what) {
            case "AddProgress":
                createActionPerformed("Progress", culturesList.getSelectionModel().getSelectedItem().toString());
                break;
            case "AddBattle":
                createActionPerformed("Battle", culturesList.getSelectionModel().getSelectedItem().toString());
                break;
            case "EditProgress":
                editActionPerformed("Progress");
                break;
            case "EditBattle":
                editActionPerformed("Battle");
                break;
            case "DuplicateProgress":
                duplicateActionPerformed("Progress");
                break;
            case "DuplicateBattle":
                duplicateActionPerformed("Battle");
                break;
            case "DeleteProgress":
                deleteActionPerformed("Progress");
                break;
            case "DeleteBattle":
                deleteActionPerformed("Battle");
                break;
            default:
                break;
        }
    }

    @FXML
    private void addProgressActionPerformed() {
        if (!speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            progresActions("AddProgress", cultureProgressList.getSelectionModel().isEmpty() ? "" : cultureProgressList.getSelectionModel().getSelectedItem().toString());
        }
    }

    @FXML
    private void editProgressActionPerformed() {
        if (!speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty() && !cultureProgressList.getItems().isEmpty()) {
            progresActions("EditProgress", cultureProgressList.getSelectionModel().isEmpty() ? "" : cultureProgressList.getSelectionModel().getSelectedItem().toString());
        }
    }

    @FXML
    private void deleteProgressActionPerformed() {
        if (!speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty() && !cultureProgressList.getItems().isEmpty()) {
            progresActions("DeleteProgress", cultureProgressList.getSelectionModel().isEmpty() ? "" : cultureProgressList.getSelectionModel().getSelectedItem().toString());
        }
    }

    @FXML
    private void addBattleActionPerformed() {
        if (!speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            progresActions("AddBattle", cultureProgressList.getSelectionModel().isEmpty() ? "" : cultureProgressList.getSelectionModel().getSelectedItem().toString());
        }
    }

    @FXML
    private void editBattleActionPerformed() {
        if (!speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty() && !cultureProgressList.getItems().isEmpty()) {
            progresActions("EditBattle", cultureProgressList.getSelectionModel().isEmpty() ? "" : cultureProgressList.getSelectionModel().getSelectedItem().toString());
        }
    }

    @FXML
    private void deleteBattleActionPerformed() {
        if (!speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty() && !cultureProgressList.getItems().isEmpty()) {
            progresActions("DeleteBattle", cultureProgressList.getSelectionModel().isEmpty() ? "" : cultureProgressList.getSelectionModel().getSelectedItem().toString());
        }
    }

    /**
     * populateLabels
     */
    public void populateLabels() {
        for (int i = 0; i < valuesLabels.length; i++) {
            valuesLabels[i].setText(DatabaseReader.getCharacteristics(currentLifeDomain.toString(), currentCharacteristicGroup.toString())[i]);
        }
    }

    /**
     * clearLists
     */
    public void clearLists() {
        for (TextField valuesLabel : valuesLabels) {
            valuesLabel.setText("");
        }
        culturesList.getItems().clear();
        classList.getItems().clear();
        heroesList.getItems().clear();
        cultureProgressList.getItems().clear();
        rostersList.getItems().clear();
        fullroster.getItems().clear();
        speciesList.getSelectionModel().clearSelection();
        skillsList.getItems().clear();
        subSkillsList.getItems().clear();
        skillFullText.setText("");
        classTypeValue.setText("");
        basedOnValue.setText("");
        ageValue.setText("");
        totalProgressPointsValue.setText("");
        remainingProgressPointsValue.setText("");
    }

    /**
     * clearSelectionEvent
     */
    public void clearSelectionEvent() {
        clearLists();
        speciesList.setItems(DatabaseReader.getSpeciesList(speciesIdMap));
    }

    /**
     * clearLists
     */
    public void clearListsOnCultureClick() {
        classList.getItems().clear();
        heroesList.getItems().clear();
        cultureProgressList.getItems().clear();
        rostersList.getItems().clear();
        fullroster.getItems().clear();
        subSkillsList.getItems().clear();
        skillFullText.setText("");
        classTypeValue.setText("");
        basedOnValue.setText("");
    }

    @FXML
    private void rosterListMousePressed() {
        if (!rostersList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            cultureProgressList.getSelectionModel().clearSelection();
            rosterID = Integer.parseInt(rosterIdMap.get(rostersList.getSelectionModel().getSelectedIndex()).toString());
            fullroster.getItems().setAll(FXCollections.observableArrayList(DatabaseReader.getRosterData(rosterID).get(0).toString().split("\\|")[2].split(";")));
        }
    }

    @FXML
    private void cultureProgressListMousePressed() {
        if (!cultureProgressList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            rostersList.getSelectionModel().clearSelection();
            progressID = Integer.parseInt(progressIdMap.get(cultureProgressList.getSelectionModel().getSelectedIndex()).toString());
            ObservableList lst = FXCollections.observableArrayList();
            if (cultureProgressList.getSelectionModel().getSelectedItem().toString().startsWith("Progress: ")) {
                //String progressName = cultureProgressList.getSelectionModel().getSelectedItem().toString().replaceFirst("Progress: ", "");
                lst = FXCollections.observableArrayList(DatabaseReader.getProgressData(progressID).get(0).toString().split("\\|")[2].split(";"));
            }
            if (cultureProgressList.getSelectionModel().getSelectedItem().toString().startsWith("Battle: ")) {
                //String battleName = cultureProgressList.getSelectionModel().getSelectedItem().toString().replaceFirst("Battle: ", "");
                lst = FXCollections.observableArrayList(BuilderCORE.beautifyBattleData(DatabaseReader.getBattleData(progressID).get(0).toString()));
            }
            fullroster.getItems().setAll(lst);
        }
    }

    @FXML
    private void fullrosterListMousePressed() {
        if (!fullroster.getSelectionModel().isEmpty() && !rostersList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            String selected = fullroster.getSelectionModel().getSelectedItem().toString().split(" x")[0];
            if (classList.getItems().contains(selected)) {
                loadClassDataIntoFront(Integer.parseInt(classesIdMap.get(selected).toString()));
            } else if (heroesList.getItems().contains(selected)) {
                loadHeroDataIntoFront(Integer.parseInt(heroesIdMap.get(selected).toString()));
            } else {
                System.out.println("wrong class/hero");
            }
        }
        if (!fullroster.getSelectionModel().isEmpty() && !cultureProgressList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            //show details of progress/battle
        }
    }

    @FXML
    private void skillsListMousePressed() {
        skillFullText.setText("");
        if (!skillsList.getSelectionModel().isEmpty()) {
            subSkillsList.setItems(BuilderCORE.generateSubSkills(skillsList.getSelectionModel().getSelectedItem().toString().split(" \\(pts : ")[0], simplifyToCoreSkills));
        }
    }

    @FXML
    private void subSkillsListMousePressed() {
        if (!subSkillsList.getSelectionModel().isEmpty()) {
            skillFullText.setText(BuilderCORE.generateSubSkillText(subSkillsList.getSelectionModel().getSelectedItem().toString(), !simplifyToCoreSkills));
        }
    }

    @FXML
    private void showAsCoreSkillsPressed() {
        simplifyToCoreSkills = showAsCoreSkills.isSelected();
        int i = -1;
        if (!subSkillsList.getSelectionModel().isEmpty()) {
            i = subSkillsList.getSelectionModel().getSelectedIndex();
        }
        if (!skillsList.getSelectionModel().isEmpty()) {
            subSkillsList.setItems(BuilderCORE.generateSubSkills(skillsList.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
        if (-1 != i) {
            subSkillsList.getSelectionModel().select(i);
        }
    }

    private void checkListAndMenuItemThenDecide(String menuItem) {
        switch (menuItem) {
            case "Create new":
                if (rostersList.isFocused() && !culturesList.getSelectionModel().isEmpty()) {
                    createActionPerformed("Roster", culturesList.getSelectionModel().getSelectedItem().toString());
                    break;
                } else if (rostersList.isFocused() && culturesList.getSelectionModel().isEmpty()) {
                    createActionPerformed("Roster", null);
                    break;
                } else if (culturesList.isFocused() && !speciesList.getSelectionModel().isEmpty()) {
                    createActionPerformed("Culture", speciesList.getSelectionModel().getSelectedItem().toString());
                    break;
                } else if (culturesList.isFocused() && speciesList.getSelectionModel().isEmpty()) {
                    createActionPerformed("Culture", null);
                    break;
                } else if (speciesList.isFocused()) {
                    createActionPerformed("Species", null);
                    break;
                } else {
                    createActionPerformed("Choose", null);
                    break;
                }
            case "Edit selected":
                if (rostersList.isFocused() || culturesList.isFocused() || speciesList.isFocused()) {
                    editActionPerformed(menuItem);
                    break;
                } else {
                    editActionPerformed("Choose");
                    break;
                }
            case "Duplicate selected":
                if (rostersList.isFocused() || culturesList.isFocused() || speciesList.isFocused()) {
                    duplicateActionPerformed(menuItem);
                    break;
                } else {
                    duplicateActionPerformed("Choose");
                    break;
                }
            case "Delete selected":
                if (rostersList.isFocused() || culturesList.isFocused() || speciesList.isFocused()) {
                    deleteActionPerformed(menuItem);
                    break;
                } else {
                    deleteActionPerformed("Choose");
                    break;
                }
            default:
                break;
        }
    }

    public void loadMainWindowData(ListView speciesList, ListView culturesList, ListView rostersList, ListView cultureProgressList, Map speciesIdMap) {
        mainWindowDataPropagator.setSpeciesList(speciesList);
        mainWindowDataPropagator.setCulturesList(culturesList);
        mainWindowDataPropagator.setRostersList(rostersList);
        mainWindowDataPropagator.setCulturesProgressList(cultureProgressList);
        mainWindowDataPropagator.setSpeciesIdMap(speciesIdMap);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        simplifyToCoreSkills = false;
        this.valuesLabels = new TextField[]{strengthValue, toughnessValue, movementValue, martialValue, rangedValue, defenseValue, disciplineValue, willpowerValue, commandValue, woundsValue, attacksValue, sizeValue, mTValue, rTValue, moraleValue};
        speciesList.setItems(DatabaseReader.getSpeciesList(speciesIdMap));
        speciesList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (" ".equals(item)
                                || "--Humanoid--".equals(item)
                                || "--Fey--".equals(item)
                                || "--Reptilia--".equals(item)
                                || "--Biest--".equals(item)
                                || "--Insecta--".equals(item)) {
                            setDisable(true);
                        } else {
                            setDisable(false);
                        }
                        setText(item);
                    }
                };
            }
        });
        speciesList.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            if (speciesList.getSelectionModel().getSelectedItem() == null) {
                clearSelectionEvent();
            } else {
                speciesListMousePressed();
            }
        });

        EventHandler rightMouseListClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isSecondaryButtonDown()) {
                    rightClickContextMenu.show((Node) event.getTarget(), event.getScreenX(), event.getScreenY());
                }
            }
        };

        speciesList.setOnMousePressed(rightMouseListClick);
        culturesList.setOnMousePressed(rightMouseListClick);
        rostersList.setOnMousePressed(rightMouseListClick);
        cultureProgressList.setOnMousePressed(rightMouseListClick);

        rightClickContextMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkListAndMenuItemThenDecide(((MenuItem) event.getTarget()).getText());
            }
        });

        progressMenu.setDisable(true);
        battlesMenu.setDisable(true);
        loadMainWindowData(speciesList, culturesList, rostersList, cultureProgressList, speciesIdMap);
    }
}
