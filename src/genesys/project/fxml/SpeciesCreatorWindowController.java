/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.AvailableSkillsLister;
import genesys.project.builder.BuilderCORE;
import genesys.project.builder.Enums.Enmuerations;
import genesys.project.builder.Enums.Enmuerations.MainClasificationValue;
import genesys.project.builder.Enums.Enmuerations.MainDomainValue;
import genesys.project.builder.Enums.Enmuerations.MainKingdomValue;
import genesys.project.builder.Enums.Enmuerations.MainLineageValue;
import genesys.project.builder.Enums.Enmuerations.MainOrderValue;
import genesys.project.builder.Enums.Enmuerations.MainRegionValue;
import genesys.project.builder.Enums.Enmuerations.PrimaryChooserValue;
import genesys.project.builder.Enums.Enmuerations.SecondaryChooserValue;
import static genesys.project.builder.Enums.Enmuerations.PrimaryChooserValue.*;
import static genesys.project.builder.Enums.Enmuerations.SecondaryChooserValue.*;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseHolder.AFey;
import genesys.project.builder.DatabaseHolder.AReptilia;
import genesys.project.builder.DatabaseHolder.ABiest;
import genesys.project.builder.DatabaseHolder.AInsecta;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseWriter;
import java.io.IOException;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
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
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class SpeciesCreatorWindowController implements Initializable {

    @FXML
    private ListView skillsList1;
    @FXML
    private ListView availableSkillsList;
    @FXML
    private Label rTValue1;
    @FXML
    private Label mTText1;
    @FXML
    private Label rTText1;
    @FXML
    private Label moraleText1;
    @FXML
    private Label moraleValue1;
    @FXML
    private Label sizeText1;
    @FXML
    private Label attacksText1;
    @FXML
    private Label woundsText1;
    @FXML
    private Label pointsPerModelValue1;
    @FXML
    private Label woundsValue1;
    @FXML
    private Label sizeValue1;
    @FXML
    private Label attacksValue1;
    @FXML
    private Label characteristicsText1;
    @FXML
    private Label targetNumbersText1;
    @FXML
    private Label skillsText1;
    @FXML
    private Label pointsPerModelText1;
    @FXML
    private Label strengthText1;
    @FXML
    private Label strengthValue1;
    @FXML
    private Label toughnessText1;
    @FXML
    private Label toughnessValue1;
    @FXML
    private Label movementText1;
    @FXML
    private Label movementValue1;
    @FXML
    private Label martialText1;
    @FXML
    private Label martialValue1;
    @FXML
    private Label rangedText1;
    @FXML
    private Label rangedValue1;
    @FXML
    private Label defenseText1;
    @FXML
    private Label defenseValue1;
    @FXML
    private Label disciplineValue1;
    @FXML
    private Label willpowerValue1;
    @FXML
    private Label disciplineText1;
    @FXML
    private Label willpowerText1;
    @FXML
    private Label commandText1;
    @FXML
    private Label commandValue1;
    @FXML
    private Label mTValue1;
    @FXML
    private Label nameInputTextField;
    @FXML
    private Label classInputTextField;
    @FXML
    private Label classNameValue;
    @FXML
    private TextField nameInputField;
    @FXML
    private Label lifeDomainText;
    @FXML
    private Label lifeDomainValue;
    @FXML
    private ComboBox skillSetChooser;
    @FXML
    private ComboBox skillSubSetChooser;
    @FXML
    private ComboBox primaryChooser;
    @FXML
    private ComboBox secondaryChooser;
    @FXML
    private Label primaryChooserText;
    @FXML
    private Label secondaryChooserText;
    @FXML
    private Label skillSetText;
    @FXML
    private Label skillSubSetText;
    @FXML
    private Button moveSkillButton;
    @FXML
    private Label skillsLeft1a;
    @FXML
    private Label skillsLeft1b;
    @FXML
    private CheckBox outcastsCheckbox;
    @FXML
    private Button createFinish;
    @FXML
    private ListView subSkillsList1;
    @FXML
    private TextArea subSkillText1;
    @FXML
    private CheckBox showAsCoreSkills;

    /**
     * createHoldWindowStage
     */
    public Stage createHoldWindowStage = new Stage();
    private CreateHoldWindowController createHoldWindowController;

    private ListView speciesList;
    private Label[] valuesLabels;
    private Boolean simplifyToCoreSkills;
    private String primaryChooserString = "", secondaryChooserString = "";

    /**
     *
     */
    public void createSpecies() {
        if (DatabaseHolder.isModyfyinfg) {
            nameInputField.setText(DatabaseHolder.holdSpecies.getSpeciesName());
        } else {
            DatabaseHolder.holdSpecies.setSkills("");
            nameInputField.setText(">enter name here<");
        }
        DatabaseHolder.arcana = false;
        DatabaseHolder.outcasts = false;
        lifeDomainValue.setText(DatabaseHolder.holdSpecies.getLifedomain().toString());
        classNameValue.setText("<base species>");
        if (DatabaseHolder.holdSpecies.getSkills() == null || "".equals(DatabaseHolder.holdSpecies.getSkills())) {
            skillsList1.getItems().clear();
        } else {
            skillsList1.setItems(AvailableSkillsLister.getAddedSkills(DatabaseHolder.holdSpecies.getSkills()));
            BuilderFXMLController.getSkillModifiers(DatabaseHolder.ruledskills);
        }
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                skillSetText.setText(BuilderCORE.HUMANOIDSKILS);
                skillSubSetText.setText(BuilderCORE.HUMANOIDSUBSKILS);
                break;
            case Fey:
                skillSetText.setText(BuilderCORE.FEYSKILS);
                skillSubSetText.setText(BuilderCORE.FEYSUBSKILS);
                ((AFey) DatabaseHolder.holdSpecies).setMainDomain(MainDomainValue.Light);
                ((AFey) DatabaseHolder.holdSpecies).setSecondaryDomain(MainDomainValue.Twilight);
                break;
            case Reptilia:
                skillSetText.setText(BuilderCORE.REPTILIASKILS);
                skillSubSetText.setText(BuilderCORE.REPTILIASUBSKILS);
                ((AReptilia) DatabaseHolder.holdSpecies).setMainLineage(MainLineageValue.Draconic);
                break;
            case Biest:
                skillSetText.setText(BuilderCORE.BIESTSKILS);
                skillSubSetText.setText(BuilderCORE.BIESTSUBSKILS);
                ((ABiest) DatabaseHolder.holdSpecies).setMainKingdom(MainKingdomValue.NONE);
                primaryChooserString = MainKingdomValue.NONE.getText();
                ((ABiest) DatabaseHolder.holdSpecies).setMainRegion(MainRegionValue.Caverns);
                secondaryChooserString = MainRegionValue.Caverns.getText();
                break;
            case Insecta:
                skillSetText.setText(BuilderCORE.INSECTASKILS);
                skillSubSetText.setText(BuilderCORE.INSECTASUBSKILS);
                ((AInsecta) DatabaseHolder.holdSpecies).setMainClasification(MainClasificationValue.NONE);
                ((AInsecta) DatabaseHolder.holdSpecies).setMainOrder(MainOrderValue.NONE);
                primaryChooserString = MainClasificationValue.NONE.getText();
                secondaryChooserString = MainOrderValue.NONE.getText();
                break;
        }
        skillSetChooser.setItems(DatabaseReader.getSkillSet());
        skillSetChooser.getSelectionModel().select(0);
        skillSubSetChooser.setItems(DatabaseReader.getSubSkillSet(skillSetChooser.getSelectionModel().getSelectedItem().toString(), primaryChooserString, secondaryChooserString));
        skillSubSetChooser.getSelectionModel().select(0);
        setAvailableSkills();
        pointsPerModelValue1.setText("0");
        populateLabels();
        skillsLeft1a.setText(BuilderCORE.skillsCanTake());
        skillsLeft1b.setText(BuilderCORE.skillsLeftModify("0", true));
    }

    void setAvailableSkills() {
        int age = Integer.max(DatabaseHolder.holdCulture.getAge(), DatabaseHolder.holdSpecies.getAge());
        availableSkillsList.setItems(AvailableSkillsLister.getAvailableSkills(DatabaseHolder.holdSpecies.getLifedomain(), age, skillSubSetChooser.getSelectionModel().getSelectedItem().toString(), skillsList1.getItems()));
        availableSkillsList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (" ".equals(item)
                                || "--Primary Traits--".equals(item)
                                || "--Secondary Traits--".equals(item)
                                || "--Lesser Traits--".equals(item)
                                || "--Greater Traits--".equals(item)
                                || "--Lesser Powers--".equals(item)
                                || "--Greater Powers--".equals(item)
                                || "--Ancestral Traits--".equals(item)
                                || "--Reptilian Lineage--".equals(item)) {
                            setDisable(true);
                        } else {
                            setDisable(false);
                        }
                        setText(item);
                    }
                };
            }
        });
    }

    /**
     *
     *
     */
    public void wipeSkills() {
        skillsList1.getItems().clear();
        DatabaseHolder.holdSpecies.setAllTraitsAndPowers(0, 0, 0, 0, 0, 0);
        skillsLeft1b.setText(BuilderCORE.skillsLeftModify("0", true));
        DatabaseHolder.holdSpecies.setSkills("");
        BuilderFXMLController.HOLD_MODIFIERS.setWoundsModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setAttacksModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setSizeModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setStrengthModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setToughnessModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setMovementModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setMartialModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setRangedModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setDefenseModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setDisciplineModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setWillpowerModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setCommandModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setMTModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setRTModifier(0);
        BuilderFXMLController.HOLD_MODIFIERS.setMoraleModifier(0);
        populateLabels();
        skillSetChooser.setItems(DatabaseReader.getSkillSet());
        skillSetChooser.getSelectionModel().select(0);
        setAvailableSkills();
    }

    /**
     *
     *
     */
    @FXML
    public void createFinishActions() {
        setMaxClassNumber();
        Stage stage = (Stage) createFinish.getScene().getWindow();
        stage.hide();
        DatabaseHolder.holdSpecies.setSpeciesName(nameInputField.getText());
        DatabaseHolder.holdSpecies.setAge(DatabaseReader.findMaxAge(skillsList1));
        if (DatabaseHolder.isModyfyinfg) {
            DatabaseHolder.isModyfyinfg = !DatabaseHolder.isModyfyinfg;
            DatabaseWriter.modifySpecies();
            DatabaseHolder.holdSpecies = null;
            DatabaseHolder.modifiedHoldSpecies = null;
            speciesList.setItems(DatabaseReader.getSpeciesList());
            speciesList.getSelectionModel().clearSelection();
        } else {
            DatabaseHolder.holdCulture.setSpeciesName(nameInputField.getText());
            DatabaseHolder.holdCulture.setCultureName(nameInputField.getText());
            if (createHoldWindowStage.isShowing()) {
                createHoldWindowStage.requestFocus();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateHoldWindowFXML.fxml"));
                    Parent root = loader.load();
                    createHoldWindowController = loader.getController();
                    createHoldWindowController.setSpeciesList(speciesList);
                    Scene scene = new Scene(root);
                    createHoldWindowStage.setScene(scene);
                    createHoldWindowStage.setTitle("Create Classes for " + DatabaseHolder.holdCulture.getSpeciesName() + " - " + DatabaseHolder.holdCulture.getCultureName());
                    createHoldWindowStage.show();
                } catch (IOException ex) {
                    ErrorController.ErrorController(ex);
                    Logger.getLogger(SpeciesCreatorWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     *
     *
     */
    @FXML
    public void outcastCheckboxActions() {
        DatabaseHolder.outcasts = !DatabaseHolder.outcasts;
        DatabaseHolder.holdSpecies.setSpeciesModifiers("Outcasts=" + DatabaseHolder.outcasts + ",SecondaryDomain=" + ((AFey) DatabaseHolder.holdSpecies).getSecondaryDomain().getText());
        PrimaryChooseActions();
    }

    /**
     *
     *
     */
    @FXML
    public void PrimaryChooseActions() {
        switch (PrimaryChooserValue.getEnum(primaryChooser.getSelectionModel().getSelectedItem().toString())) {
            case Light:
            case Darkness:
                ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setMainDomain(MainDomainValue.getEnum(primaryChooser.getSelectionModel().getSelectedItem().toString()));
                secondaryChooser.setVisible(false);
                secondaryChooserText.setVisible(false);
                break;
            case Twilight:
                ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setMainDomain(MainDomainValue.Twilight);
                if (DatabaseHolder.outcasts) {
                    secondaryChooser.setVisible(false);
                    secondaryChooserText.setVisible(false);
                    ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setSecondaryDomain(MainDomainValue.Twilight);
                    wipeSkills();
                } else {
                    secondaryChooser.setVisible(true);
                    secondaryChooser.getSelectionModel().select(0);
                    secondaryChooserText.setVisible(true);
                    SecondaryChooseActions();
                }
                break;
            case Ursidae:
            case CanusLupis:
            case AvianAves:
            case Bor:
            case Ovis:
            case Taurus:
            case Feline:
            case Vermin:
            case Caballis:
            case Ichthyes:
                ((DatabaseHolder.ABiest) DatabaseHolder.holdSpecies).setMainKingdom(MainKingdomValue.getEnum(primaryChooser.getSelectionModel().getSelectedItem().toString()));
                DatabaseHolder.holdSpecies.setCharacteristicGroup(Enmuerations.CharacteristicGroup.getEnum(primaryChooser.getSelectionModel().getSelectedItem().toString()));
                primaryChooserString = primaryChooser.getSelectionModel().getSelectedItem().toString();
                break;
            case Arachnea:
            case Crustacea:
            case Insecta:
            case Myriapoda:
                ((DatabaseHolder.AInsecta) DatabaseHolder.holdSpecies).setMainClasification(MainClasificationValue.getEnum(primaryChooser.getSelectionModel().getSelectedItem().toString()));
                DatabaseHolder.holdSpecies.setCharacteristicGroup(Enmuerations.CharacteristicGroup.getEnum(primaryChooser.getSelectionModel().getSelectedItem().toString()));
                primaryChooserString = primaryChooser.getSelectionModel().getSelectedItem().toString();
                break;
            case NONE:
                DatabaseHolder.holdSpecies.setCharacteristicGroup(Enmuerations.CharacteristicGroup.standard);
                primaryChooserString = primaryChooser.getSelectionModel().getSelectedItem().toString();
                break;
            default:
                break;
        }

        skillsLeft1a.setText(BuilderCORE.skillsCanTake());
        wipeSkills();
    }

    /**
     *
     *
     */
    @FXML
    public void SecondaryChooseActions() {
        switch (SecondaryChooserValue.getEnum(secondaryChooser.getSelectionModel().getSelectedItem().toString())) {
            case Light:
            case Darkness:
                ((DatabaseHolder.AFey) DatabaseHolder.holdSpecies).setSecondaryDomain(MainDomainValue.getEnum(secondaryChooser.getSelectionModel().getSelectedItem().toString()));
                break;
            default:
                break;
        }
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                break;
            case Fey:
                DatabaseHolder.holdSpecies.setSpeciesModifiers("Outcasts=" + DatabaseHolder.outcasts + ",SecondaryDomain=" + ((AFey) DatabaseHolder.holdSpecies).getSecondaryDomain().getText());
                break;
            case Reptilia:
                break;
            case Biest:
                secondaryChooserString = secondaryChooser.getSelectionModel().getSelectedItem().toString();
                break;
            case Insecta:
                break;
        }
        wipeSkills();
    }

    /**
     *
     *
     */
    @FXML
    public void moveSkillButtonActions() {
        moveSkill();
        availableSkillsList.getSelectionModel().clearSelection();
        skillsList1.getSelectionModel().clearSelection();

    }

    void moveSkill() {
        if (DatabaseHolder.holdSpecies.getSkills().length() > 1) {
            if (!";".equals(DatabaseHolder.holdSpecies.getSkills().substring(DatabaseHolder.holdSpecies.getSkills().length() - 1))) {
                DatabaseHolder.holdSpecies.addSkills(";");
            }
        }
        if (!availableSkillsList.getSelectionModel().isEmpty()) {
            skillsLeft1b.setText(BuilderCORE.skillsLeftModify(availableSkillsList.getSelectionModel().getSelectedItem().toString(), true));
            DatabaseHolder.holdSpecies.addSkills(availableSkillsList.getSelectionModel().getSelectedItem().toString());
            DatabaseHolder.holdSpecies.addSkills(";");
        }
        if (!skillsList1.getSelectionModel().isEmpty()) {
            skillsLeft1b.setText(BuilderCORE.skillsLeftModify(skillsList1.getSelectionModel().getSelectedItem().toString().split(" \\(p")[0], false));
            DatabaseHolder.holdSpecies.setSkills(DatabaseHolder.holdSpecies.getSkills().replace(skillsList1.getSelectionModel().getSelectedItem().toString().split(" \\(p")[0] + ";", ""));
        }
        if (!("".equals(DatabaseHolder.holdSpecies.getSkills()) || DatabaseHolder.holdSpecies.getSkills() == null)) {
            skillsList1.setItems(AvailableSkillsLister.getAddedSkills(DatabaseHolder.holdSpecies.getSkills()));
            BuilderFXMLController.getSkillModifiers(DatabaseHolder.ruledskills);
        } else {
            skillsList1.getItems().clear();
            BuilderFXMLController.HOLD_MODIFIERS.clearModifiers();
        }
        skillsLeft1a.setText(BuilderCORE.skillsCanTake());
        DatabaseReader.getCharacteristics(DatabaseHolder.holdSpecies.getLifedomain().toString(), DatabaseHolder.holdSpecies.getCharacteristicGroup().toString());
        int cost = BuilderCORE.baseAddedCost(DatabaseHolder.holdSpecies.getLifedomain(), DatabaseHolder.holdSpecies, null, DatabaseHolder.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue1.setText(tex);
        populateLabels();
        setAvailableSkills();
        DatabaseHolder.holdSpecies.setNumberOfSkills(DatabaseHolder.holdSpecies.getSkills().split(";").length);
        subSkillsList1.getItems().clear();
        subSkillText1.setText("");
    }

    /**
     *
     *
     */
    @FXML
    public void skillSetChooserItemStateChangedActions() {
        if (skillSetChooser.getSelectionModel().isEmpty()) {
            skillSetChooser.setItems(DatabaseReader.getSkillSet());
            skillSetChooser.getSelectionModel().select(0);
        }
        skillSubSetChooser.setItems(DatabaseReader.getSubSkillSet(skillSetChooser.getSelectionModel().getSelectedItem().toString(), primaryChooserString, secondaryChooserString));
        skillSubSetChooser.getSelectionModel().select(0);
    }

    /**
     *
     *
     */
    @FXML
    public void skillSubSetChooserItemStateChangedActions() {
        if (skillSubSetChooser.getSelectionModel().isEmpty()) {
            skillSubSetChooser.setItems(DatabaseReader.getSubSkillSet(skillSetChooser.getSelectionModel().getSelectedItem().toString(), primaryChooserString, secondaryChooserString));
            skillSubSetChooser.getSelectionModel().select(0);
        }
        setAvailableSkills();
    }

    /**
     * availableSkillsListMousePressedActions
     *
     */
    @FXML
    public void availableSkillsListMousePressedActions() {
        skillsList1.getSelectionModel().clearSelection();
        subSkillText1.setText("");
        if (!availableSkillsList.getSelectionModel().isEmpty()) {
            subSkillsList1.setItems(BuilderCORE.generateSubSkills(availableSkillsList.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    /**
     * skillsList1MousePressedActions
     *
     */
    @FXML
    public void skillsList1MousePressedActions() {
        availableSkillsList.getSelectionModel().clearSelection();
        subSkillText1.setText("");
        if (!skillsList1.getSelectionModel().isEmpty()) {
            subSkillsList1.setItems(BuilderCORE.generateSubSkills(skillsList1.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    /**
     * setMaxClassNumber
     */
    public void setMaxClassNumber() {
        int a = 0, b = 0, c = 0;
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                a = Integer.parseInt(martialValue1.getText());
                b = Integer.parseInt(rangedValue1.getText());
                c = Integer.parseInt(defenseValue1.getText());
                break;
            case Fey:
                a = Integer.parseInt(disciplineValue1.getText());
                b = Integer.parseInt(willpowerValue1.getText());
                c = Integer.parseInt(commandValue1.getText());
                break;
            case Reptilia:
            case Biest:
            case Insecta:
                a = Integer.parseInt(strengthValue1.getText());
                b = Integer.parseInt(toughnessValue1.getText());
                c = Integer.parseInt(movementValue1.getText());
                break;
        }
        DatabaseHolder.holdSpecies.setMaxNumberOfLowClases(max(max(a, b), c));
        DatabaseHolder.holdSpecies.setMaxNumberOfMidClases(min(min(max(a, b), max(a, c)), max(min(a, b), min(a, c))));
        DatabaseHolder.holdSpecies.setMaxNumberOfHigClases(min(min(a, b), c));
    }

    /**
     *
     *
     */
    public void populateLabels() {
        for (int i = 0; i < valuesLabels.length; i++) {
            valuesLabels[i].setText(DatabaseReader.getCharacteristics(DatabaseHolder.holdSpecies.getLifedomain().toString(), DatabaseHolder.holdSpecies.getCharacteristicGroup().toString())[i]);
        }
    }

    @FXML
    private void subSkillsListMouse1Pressed() {
        if (!subSkillsList1.getSelectionModel().isEmpty()) {
            subSkillText1.setText(BuilderCORE.generateSubSkillText(subSkillsList1.getSelectionModel().getSelectedItem().toString(), !simplifyToCoreSkills));
        }
    }

    @FXML
    private void showAsCoreSkillsPressed() {
        simplifyToCoreSkills = showAsCoreSkills.isSelected();
        int i = -1;
        if (!subSkillsList1.getSelectionModel().isEmpty()) {
            i = subSkillsList1.getSelectionModel().getSelectedIndex();
        }
        if (!availableSkillsList.getSelectionModel().isEmpty()) {
            subSkillsList1.setItems(BuilderCORE.generateSubSkills(availableSkillsList.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
        if (!availableSkillsList.getSelectionModel().isEmpty()) {
            subSkillsList1.setItems(BuilderCORE.generateSubSkills(availableSkillsList.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
        if (-1 != i) {
            subSkillsList1.getSelectionModel().select(i);
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
        this.valuesLabels = new Label[]{strengthValue1, toughnessValue1, movementValue1, martialValue1, rangedValue1, defenseValue1, disciplineValue1, willpowerValue1, commandValue1, woundsValue1, attacksValue1, sizeValue1, mTValue1, rTValue1, moraleValue1};

        if (!DatabaseHolder.isModyfyinfg) {
            DatabaseHolder.holdSpecies.setAge(1);
            DatabaseHolder.holdCulture.setAge(1);
        }
        createSpecies();
        switch (DatabaseHolder.holdSpecies.getLifedomain()) {
            case Humanoid:
                primaryChooser.setVisible(false);
                secondaryChooser.setVisible(false);
                primaryChooserText.setVisible(false);
                secondaryChooserText.setVisible(false);
                outcastsCheckbox.setVisible(false);
                break;
            case Fey:
                primaryChooser.setVisible(true);
                primaryChooser.setItems(FXCollections.observableArrayList(PrimaryChooserValue.Light.getText(), PrimaryChooserValue.Darkness.getText(), PrimaryChooserValue.Twilight.getText()));
                primaryChooser.getSelectionModel().select(0);
                secondaryChooser.setVisible(false);
                secondaryChooser.setItems(FXCollections.observableArrayList(SecondaryChooserValue.Light.getText(), SecondaryChooserValue.Darkness.getText()));
                secondaryChooser.getSelectionModel().select(0);
                primaryChooserText.setVisible(true);
                primaryChooserText.setText("Path");
                secondaryChooserText.setVisible(false);
                secondaryChooserText.setText("Sphere");
                outcastsCheckbox.setVisible(true);
                DatabaseHolder.outcasts = false;
                break;
            case Reptilia:
                primaryChooser.setVisible(false);
                secondaryChooser.setVisible(false);
                primaryChooserText.setVisible(false);
                secondaryChooserText.setVisible(false);
                outcastsCheckbox.setVisible(false);
                break;
            case Biest:
                primaryChooser.setVisible(true);
                primaryChooser.setItems(FXCollections.observableArrayList(PrimaryChooserValue.NONE.getText(), PrimaryChooserValue.Ursidae.getText(), PrimaryChooserValue.CanusLupis.getText(), PrimaryChooserValue.AvianAves.getText(), PrimaryChooserValue.Bor.getText(), PrimaryChooserValue.Ovis.getText(), PrimaryChooserValue.Taurus.getText(), PrimaryChooserValue.Feline.getText(), PrimaryChooserValue.Vermin.getText(), PrimaryChooserValue.Caballis.getText(), PrimaryChooserValue.Ichthyes.getText()));
                primaryChooser.getSelectionModel().select(0);
                secondaryChooser.setVisible(true);
                secondaryChooser.setItems(FXCollections.observableArrayList(SecondaryChooserValue.Caverns.getText(), SecondaryChooserValue.Desert.getText(), SecondaryChooserValue.Forests.getText(), SecondaryChooserValue.Marsh.getText(), SecondaryChooserValue.Mountains.getText(), SecondaryChooserValue.Moon.getText(), SecondaryChooserValue.Oceans.getText(), SecondaryChooserValue.Plains.getText(), SecondaryChooserValue.Sky.getText(), SecondaryChooserValue.Tundra.getText()));
                secondaryChooser.getSelectionModel().select(0);
                primaryChooserText.setVisible(true);
                primaryChooserText.setText("Kingdom");
                secondaryChooserText.setVisible(true);
                secondaryChooserText.setText("Region");
                outcastsCheckbox.setVisible(false);
                break;
            case Insecta:
                primaryChooser.setVisible(true);
                primaryChooser.setItems(FXCollections.observableArrayList(PrimaryChooserValue.NONE.getText(), PrimaryChooserValue.Arachnea.getText(), PrimaryChooserValue.Crustacea.getText(), PrimaryChooserValue.Insecta.getText(), PrimaryChooserValue.Myriapoda.getText()));
                primaryChooser.getSelectionModel().select(0);
                secondaryChooser.setVisible(false);
                secondaryChooser.setItems(FXCollections.observableArrayList(SecondaryChooserValue.NONE.getText(), SecondaryChooserValue.Arachnid.getText(), SecondaryChooserValue.Scorpionoid.getText(), SecondaryChooserValue.Decapod.getText(), SecondaryChooserValue.Isopod.getText(), SecondaryChooserValue.Coleoptera.getText(), SecondaryChooserValue.Dipteran.getText(), SecondaryChooserValue.Formicadae.getText(), SecondaryChooserValue.Mantid.getText(), SecondaryChooserValue.Vespidae.getText(), SecondaryChooserValue.Centipedea.getText(), SecondaryChooserValue.Millipedea.getText()));
                secondaryChooser.getSelectionModel().select(0);
                primaryChooserText.setVisible(true);
                primaryChooserText.setText("Classification");
                secondaryChooserText.setVisible(false);
                secondaryChooserText.setText("Order");
                outcastsCheckbox.setVisible(false);
                break;
        }
        simplifyToCoreSkills = false;
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
