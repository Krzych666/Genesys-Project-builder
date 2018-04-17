/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainClasificationValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainDomainValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainKingdomValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainLineageValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainOrderValue;
import genesys.project.builder.BuilderCORE.Enmuerations.MainRegionValue;
import genesys.project.builder.BuilderCORE.Enmuerations.UseCases;
import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.DatabaseModifier.AFey;
import genesys.project.builder.DatabaseModifier.AReptilia;
import genesys.project.builder.DatabaseModifier.ABiest;
import genesys.project.builder.DatabaseModifier.AInsecta;
import genesys.project.builder.GenesysProjectBuilder;
import java.io.IOException;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
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
import javafx.stage.Stage;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
    private ComboBox secondaryPathChooser;
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
    private RadioButton lightSetButton;
    @FXML
    private RadioButton darknessSetButton;
    @FXML
    private RadioButton twilightSetButton;
    private ToggleGroup feyPath;
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

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    public void createSpecies() throws IOException, SQLException {
        if (DatabaseModifier.isModyfyinfg) {
            nameInputField.setText(DatabaseModifier.holdSpecies.getSpeciesName());
        } else {
            DatabaseModifier.holdSpecies.setSkills("");
            nameInputField.setText(">enter name here<");
        }
        DatabaseModifier.arcana = false;
        DatabaseModifier.outcasts = false;
        lifeDomainValue.setText(DatabaseModifier.holdSpecies.getLifedomain().toString());
        classNameValue.setText("<base species>");
        if (DatabaseModifier.holdSpecies.getSkills() == null || "".equals(DatabaseModifier.holdSpecies.getSkills())) {
            skillsList1.getItems().clear();
        } else {
            skillsList1.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdSpecies.getSkills()));
            BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        }
        switch (DatabaseModifier.holdSpecies.getLifedomain()) {
            case Humanoid:
                skillSetText.setText(BuilderCORE.HUMANOIDSKILS);
                skillSubSetText.setText(BuilderCORE.HUMANOIDSUBSKILS);
                break;
            case Fey:
                skillSetText.setText(BuilderCORE.FEYSKILS);
                skillSubSetText.setText(BuilderCORE.FEYSUBSKILS);
                ((AFey) DatabaseModifier.holdSpecies).setMainDomain(MainDomainValue.Light);
                ((AFey) DatabaseModifier.holdSpecies).setSecondaryDomain(MainDomainValue.Twilight);
                break;
            case Reptilia:
                skillSetText.setText(BuilderCORE.REPTILIASKILS);
                skillSubSetText.setText(BuilderCORE.REPTILIASUBSKILS);
                ((AReptilia) DatabaseModifier.holdSpecies).setMainLineage(MainLineageValue.Draconic);
                break;
            case Biest:
                skillSetText.setText(BuilderCORE.BIESTSKILS);
                skillSubSetText.setText(BuilderCORE.BIESTSUBSKILS);
                ((ABiest) DatabaseModifier.holdSpecies).setMainKingdom(MainKingdomValue.NONE);
                ((ABiest) DatabaseModifier.holdSpecies).setMainRegion(MainRegionValue.NONE);
                break;
            case Insecta:
                skillSetText.setText(BuilderCORE.INSECTASKILS);
                skillSubSetText.setText(BuilderCORE.INSECTASUBSKILS);
                ((AInsecta) DatabaseModifier.holdSpecies).setMainClasification(MainClasificationValue.NONE);
                ((AInsecta) DatabaseModifier.holdSpecies).setMainOrder(MainOrderValue.NONE);
                break;
        }
        skillSetChooser.setItems(DatabaseModifier.getSkillSet());
        skillSetChooser.getSelectionModel().select(0);
        skillSubSetChooser.setItems(DatabaseModifier.getSubSkillSet(skillSetChooser.getSelectionModel().getSelectedItem().toString()));
        skillSubSetChooser.getSelectionModel().select(0);
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT SkillName FROM Skills WHERE LifeDomainTree2 = ?");
        stmt.setString(1, skillSubSetChooser.getSelectionModel().getSelectedItem().toString());
        availableSkillsList.setItems(BuilderCORE.getData(stmt, "SkillName", skillsList1.getItems()));
        pointsPerModelValue1.setText("0");
        populateLabels();
        skillsLeft1a.setText(DatabaseModifier.skillsCanTake());
        skillsLeft1b.setText(DatabaseModifier.skillsLeftModify("0", true));
    }

    /**
     *
     * @throws SQLException
     */
    public void wipeFeySkills() throws SQLException {
        skillsList1.getItems().clear();
        DatabaseModifier.holdSpecies.setAllTraitsAndPowers(0, 0, 0, 0, 0, 0);
        skillsLeft1b.setText(DatabaseModifier.skillsLeftModify("0", true));
        DatabaseModifier.holdSpecies.setSkills("");
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
        skillSetChooser.setItems(DatabaseModifier.getSkillSet());
        skillSetChooser.getSelectionModel().select(0);
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT SkillName FROM Skills WHERE LifeDomainTree2 = ?");
        stmt.setString(1, skillSubSetChooser.getSelectionModel().getSelectedItem().toString());
        availableSkillsList.setItems(BuilderCORE.getData(stmt, "SkillName", skillsList1.getItems()));
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void createFinishActions() throws IOException, SQLException {
        setMaxClassNumber();
        Stage stage = (Stage) createFinish.getScene().getWindow();
        stage.hide();
        DatabaseModifier.holdSpecies.setSpeciesName(nameInputField.getText());
        DatabaseModifier.excludeSkills = skillsList1;
        if (DatabaseModifier.isModyfyinfg) {
            DatabaseModifier.isModyfyinfg = !DatabaseModifier.isModyfyinfg;
            DatabaseModifier.modifySpecies();
            DatabaseModifier.holdSpecies = null;
            DatabaseModifier.modifiedHoldSpecies = null;
            speciesList.setItems(BuilderCORE.getSpeciesList());
            speciesList.getSelectionModel().clearSelection();
        } else {
            DatabaseModifier.holdCulture.setSpeciesName(nameInputField.getText());
            DatabaseModifier.holdCulture.setCultureName(nameInputField.getText());
            if (createHoldWindowStage.isShowing()) {
                createHoldWindowStage.requestFocus();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateHoldWindowFXML.fxml"));
                Parent root = loader.load();
                createHoldWindowController = loader.getController();
                createHoldWindowController.setSpeciesList(speciesList);
                Scene scene = new Scene(root);
                createHoldWindowStage.setScene(scene);
                createHoldWindowStage.show();
            }
        }

    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void outcastCheckboxActions() throws SQLException {
        DatabaseModifier.outcasts = !DatabaseModifier.outcasts;
        DatabaseModifier.holdSpecies.setSpeciesModifiers("Outcasts=" + DatabaseModifier.outcasts + ",SecondaryDomain=" + ((AFey) DatabaseModifier.holdSpecies).getSecondaryDomain().getText());
        if (lightSetButton.isSelected()) {
            lightSetButtonActions();
        }
        if (darknessSetButton.isSelected()) {
            darknessSetButtonActions();
        }
        if (twilightSetButton.isSelected()) {
            twilightSetButtonActions();
        }
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void lightSetButtonActions() throws SQLException {
        if (!lightSetButton.isSelected()) {
            lightSetButton.setSelected(true);
        }
        darknessSetButton.setSelected(false);
        twilightSetButton.setSelected(false);
        secondaryPathChooser.setVisible(false);
        ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setMainDomain(MainDomainValue.Light);
        skillsLeft1a.setText(DatabaseModifier.skillsCanTake());
        wipeFeySkills();
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void darknessSetButtonActions() throws SQLException {
        if (!darknessSetButton.isSelected()) {
            darknessSetButton.setSelected(true);
        }
        lightSetButton.setSelected(false);
        twilightSetButton.setSelected(false);
        secondaryPathChooser.setVisible(false);
        ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setMainDomain(MainDomainValue.Darkness);
        skillsLeft1a.setText(DatabaseModifier.skillsCanTake());
        wipeFeySkills();
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void twilightSetButtonActions() throws SQLException {
        if (!twilightSetButton.isSelected()) {
            twilightSetButton.setSelected(true);
        }
        lightSetButton.setSelected(false);
        darknessSetButton.setSelected(false);
        ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setMainDomain(MainDomainValue.Twilight);
        skillsLeft1a.setText(DatabaseModifier.skillsCanTake());
        if (DatabaseModifier.outcasts) {
            secondaryPathChooser.setVisible(false);
            ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setSecondaryDomain(MainDomainValue.Twilight);
            wipeFeySkills();
        } else {
            secondaryPathChooser.setVisible(true);
            secondaryPathChooser.getSelectionModel().select(0);
            twilightSecondaryChooseActions();
        }
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void twilightSecondaryChooseActions() throws SQLException {
        switch (secondaryPathChooser.getSelectionModel().getSelectedItem().toString()) {
            case BuilderCORE.LIGHT:
                ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setSecondaryDomain(MainDomainValue.Light);
                break;
            case BuilderCORE.DARKNESS:
                ((DatabaseModifier.AFey) DatabaseModifier.holdSpecies).setSecondaryDomain(MainDomainValue.Darkness);
                break;
            default:
                break;
        }
        DatabaseModifier.holdSpecies.setSpeciesModifiers("Outcasts=" + DatabaseModifier.outcasts + ",SecondaryDomain=" + ((AFey) DatabaseModifier.holdSpecies).getSecondaryDomain().getText());
        wipeFeySkills();
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void moveSkillButtonActions() throws SQLException {
        moveSkill();
        availableSkillsList.getSelectionModel().clearSelection();
        skillsList1.getSelectionModel().clearSelection();

    }

    void moveSkill() throws SQLException {
        if (DatabaseModifier.holdSpecies.getSkills().length() > 1) {
            if (!";".equals(DatabaseModifier.holdSpecies.getSkills().substring(DatabaseModifier.holdSpecies.getSkills().length() - 1))) {
                DatabaseModifier.holdSpecies.addSkills(";");
            }
        }
        if (availableSkillsList.getSelectionModel().getSelectedItem() != null) {
            skillsLeft1b.setText(DatabaseModifier.skillsLeftModify(availableSkillsList.getSelectionModel().getSelectedItem().toString(), true));
            DatabaseModifier.holdSpecies.addSkills(availableSkillsList.getSelectionModel().getSelectedItem().toString());
            DatabaseModifier.holdSpecies.addSkills(";");
        }
        if (skillsList1.getSelectionModel().getSelectedItem() != null) {
            skillsLeft1b.setText(DatabaseModifier.skillsLeftModify(skillsList1.getSelectionModel().getSelectedItem().toString().split(" \\(p")[0], false));
            DatabaseModifier.holdSpecies.setSkills(DatabaseModifier.holdSpecies.getSkills().replace(skillsList1.getSelectionModel().getSelectedItem().toString().split(" \\(p")[0] + ";", ""));
        }
        if (!("".equals(DatabaseModifier.holdSpecies.getSkills()) || DatabaseModifier.holdSpecies.getSkills() == null)) {
            skillsList1.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdSpecies.getSkills()));
            BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        } else {
            skillsList1.getItems().clear();
            BuilderFXMLController.HOLD_MODIFIERS.clearModifiers();
        }
        skillsLeft1a.setText(DatabaseModifier.skillsCanTake());
        GenesysProjectBuilder.CORE.getCharacteristics(DatabaseModifier.holdSpecies.getLifedomain().toString());
        int cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, null, DatabaseModifier.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue1.setText(tex);
        populateLabels();
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT SkillName FROM Skills WHERE LifeDomainTree2 = ?");
        stmt.setString(1, skillSubSetChooser.getSelectionModel().getSelectedItem().toString());
        availableSkillsList.setItems(BuilderCORE.getData(stmt, "SkillName", skillsList1.getItems()));
        DatabaseModifier.holdSpecies.setNumberOfSkills(DatabaseModifier.holdSpecies.getSkills().split(";").length);
        subSkillsList1.getItems().clear();
        subSkillText1.setText("");
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void skillSetChooserItemStateChangedActions() throws SQLException {
        if (skillSetChooser.getSelectionModel().isEmpty()) {
            skillSetChooser.setItems(DatabaseModifier.getSkillSet());
            skillSetChooser.getSelectionModel().select(0);
        }
        skillSubSetChooser.setItems(DatabaseModifier.getSubSkillSet(skillSetChooser.getSelectionModel().getSelectedItem().toString()));
        skillSubSetChooser.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void skillSubSetChooserItemStateChangedActions() throws SQLException {
        if (skillSubSetChooser.getSelectionModel().isEmpty()) {
            skillSubSetChooser.setItems(DatabaseModifier.getSubSkillSet(skillSetChooser.getSelectionModel().getSelectedItem().toString()));
            skillSubSetChooser.getSelectionModel().select(0);
        }
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT SkillName FROM Skills WHERE LifeDomainTree2 = ?");
        stmt.setString(1, skillSubSetChooser.getSelectionModel().getSelectedItem().toString());
        availableSkillsList.setItems(BuilderCORE.getData(stmt, "SkillName", skillsList1.getItems()));
    }

    /**
     * availableSkillsListMousePressedActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void availableSkillsListMousePressedActions() throws SQLException {
        skillsList1.getSelectionModel().clearSelection();
        subSkillText1.setText("");
        if (!availableSkillsList.getSelectionModel().isEmpty()) {
            subSkillsList1.setItems(BuilderCORE.generateSubSkills(availableSkillsList.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    /**
     * skillsList1MousePressedActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void skillsList1MousePressedActions() throws SQLException {
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
        switch (DatabaseModifier.holdSpecies.getLifedomain()) {
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
                a = Integer.parseInt(strengthValue1.getText());
                b = Integer.parseInt(toughnessValue1.getText());
                c = Integer.parseInt(movementValue1.getText());
                break;
            case Biest:
                break;
            case Insecta:
                break;
        }
        DatabaseModifier.holdSpecies.setMaxNumberOfLowClases(max(max(a, b), c));
        DatabaseModifier.holdSpecies.setMaxNumberOfMidClases(min(min(max(a, b), max(a, c)), max(min(a, b), min(a, c))));
        DatabaseModifier.holdSpecies.setMaxNumberOfHigClases(min(min(a, b), c));
    }

    /**
     *
     * @throws SQLException
     */
    public void populateLabels() throws SQLException {
        for (int i = 0; i < valuesLabels.length; i++) {
            valuesLabels[i].setText(GenesysProjectBuilder.CORE.getCharacteristics(DatabaseModifier.holdSpecies.getLifedomain().toString())[i]);
        }
    }

    @FXML
    private void subSkillsListMouse1Pressed() throws SQLException {
        if (!subSkillsList1.getSelectionModel().isEmpty()) {
            subSkillText1.setText(BuilderCORE.generateSubSkillText(subSkillsList1.getSelectionModel().getSelectedItem().toString(), !simplifyToCoreSkills));
        }
    }

    @FXML
    private void showAsCoreSkillsPressed() throws SQLException {
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
        secondaryPathChooser.setItems(FXCollections.observableArrayList(BuilderCORE.LIGHT, BuilderCORE.DARKNESS));
        try {
            createSpecies();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(SpeciesCreatorWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (DatabaseModifier.holdSpecies.getLifedomain() != LifedomainValue.Fey) {
            lightSetButton.setVisible(false);
            darknessSetButton.setVisible(false);
            twilightSetButton.setVisible(false);
            outcastsCheckbox.setVisible(false);
            secondaryPathChooser.setVisible(false);
        } else {
            lightSetButton.setVisible(true);
            lightSetButton.setSelected(true);
            darknessSetButton.setVisible(true);
            twilightSetButton.setVisible(true);
            outcastsCheckbox.setVisible(true);
            DatabaseModifier.outcasts = false;
            secondaryPathChooser.setVisible(false);
        }
        simplifyToCoreSkills = false;
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
