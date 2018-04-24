/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.AvailableSkillsLister;
import genesys.project.builder.BuilderCORE;
import genesys.project.builder.Enums.Enmuerations.UseCases;
import genesys.project.builder.DatabaseModifier;
import genesys.project.builder.GenesysProjectBuilder;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import static genesys.project.builder.BuilderCORE.chooseConnection;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class ClassCreatorWindowController implements Initializable {

    /**
     * @FXML private ClassCreatorWindow2 = new javax.swing.JDialog;
     * @FXML private jScrollPane10 = new javax.swing.JScrollPane;
     * @FXML private SkillsList2 = new javax.swing.JList<>;
     * @FXML private jScrollPane12 = new javax.swing.JScrollPane;
     * @FXML private AvailableSkillsList1 = new javax.swing.JList<>;
     * @FXML private jScrollPane15 = new javax.swing.JScrollPane;
     * @FXML private SkillsList4 = new javax.swing.JList<>;
     *
     */
    @FXML
    private ListView skillsList2;
    @FXML
    private ListView skillsList4;
    @FXML
    private ListView availableSkillsList1;

    @FXML
    private Label rTValue2;
    @FXML
    private Label mTText2;
    @FXML
    private Label rTText2;
    @FXML
    private Label moraleText2;
    @FXML
    private Label moraleValue2;
    @FXML
    private Label sizeText2;
    @FXML
    private Label attacksText2;
    @FXML
    private Label woundsText2;
    @FXML
    private Label pointsPerModelValue2;
    @FXML
    private Label woundsValue2;
    @FXML
    private Label sizeValue2;
    @FXML
    private Label attacksValue2;
    @FXML
    private Label characteristicsText2;
    @FXML
    private Label targetNumbersText2;
    @FXML
    private Label skillsText2;
    @FXML
    private Label pointsPerModelText2;
    @FXML
    private Label strengthText2;
    @FXML
    private Label strengthValue2;
    @FXML
    private Label toughnessText2;
    @FXML
    private Label toughnessValue2;
    @FXML
    private Label movementText2;
    @FXML
    private Label movementValue2;
    @FXML
    private Label martialText2;
    @FXML
    private Label martialValue2;
    @FXML
    private Label rangedText2;
    @FXML
    private Label rangedValue2;
    @FXML
    private Label defenseText2;
    @FXML
    private Label defenseValue2;
    @FXML
    private Label disciplineValue2;
    @FXML
    private Label willpowerValue2;
    @FXML
    private Label disciplineText2;
    @FXML
    private Label willpowerText2;
    @FXML
    private Label commandText2;
    @FXML
    private Label commandValue2;
    @FXML
    private Label mTValue2;
    @FXML
    private Label nameInputTextField2;
    @FXML
    private Label classInputTextField2;
    @FXML
    private Label speciesNameText2;
    @FXML
    private Label speciesNameValue2;
    @FXML
    private TextField nameInputField2;
    @FXML
    private Label lifeDomainText2;
    @FXML
    private Label lifeDomainValue2;
    @FXML
    private ComboBox skillSetChooser2;
    @FXML
    private Label skillSetText2;
    @FXML
    private ComboBox skillSubSetChooser2;
    @FXML
    private Label skillSubSetText2;
    @FXML
    private Button moveSkillButton2;
    @FXML
    private Label skillsLeft2;
    @FXML
    private Label skillsLeftActual2;
    @FXML
    private Button createFinish2;
    @FXML
    private Label baseSkillsText2;
    @FXML
    private Label basedOnText2;
    @FXML
    private ComboBox basedOnChooser;
    @FXML
    private Label classTypeText2;
    @FXML
    private ComboBox classTypeChooser;
    @FXML
    private ListView subSkillsList2;
    @FXML
    private TextArea subSkillText2;
    @FXML
    private CheckBox showAsCoreSkills;

    private Label classesLeft3b;
    private Label[] valuesLabels2;
    private ListView classList1;
    private ListView speciesList;
    private Boolean simplifyToCoreSkills;

    /**
     * skillsList2MousePressedActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void skillsList2MousePressedActions() throws SQLException {
        availableSkillsList1.getSelectionModel().clearSelection();
        skillsList4.getSelectionModel().clearSelection();
        if (!skillsList2.getSelectionModel().isEmpty()) {
            subSkillsList2.setItems(BuilderCORE.generateSubSkills(skillsList2.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    /**
     * availableSkillsList1MousePressedActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void availableSkillsList1MousePressedActions() throws SQLException {
        skillsList2.getSelectionModel().clearSelection();
        skillsList4.getSelectionModel().clearSelection();
        if (!availableSkillsList1.getSelectionModel().isEmpty()) {
            subSkillsList2.setItems(BuilderCORE.generateSubSkills(availableSkillsList1.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    /**
     * skillsList4MousePressedActions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void skillsList4MousePressedActions() throws SQLException {
        availableSkillsList1.getSelectionModel().clearSelection();
        skillsList2.getSelectionModel().clearSelection();
        if (!skillsList4.getSelectionModel().isEmpty()) {
            subSkillsList2.setItems(BuilderCORE.generateSubSkills(skillsList4.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    private void setAvailableSkills(ObservableList exclude) throws SQLException {
        availableSkillsList1.setItems(AvailableSkillsLister.getAvailableSkills(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdCulture.getAge(), skillSubSetChooser2.getSelectionModel().getSelectedItem().toString(), exclude));
        availableSkillsList1.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
     * @throws SQLException
     */
    @FXML
    public void basedOnChooserItemStateChangedActions() throws SQLException {
        DatabaseModifier.holdClass[DatabaseModifier.b].setBasedOn(basedOnChooser.getSelectionModel().getSelectedItem().toString());
        showBase();
        setAvailableSkills(BuilderCORE.mergeListViews(skillsList2, skillsList4));
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void classTypeChooserItemStateChangedActions() throws SQLException {
        DatabaseModifier.holdClass[DatabaseModifier.b].setType(classTypeChooser.getSelectionModel().getSelectedItem().toString());
        showBase();
        setAvailableSkills(BuilderCORE.mergeListViews(skillsList2, skillsList4));
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void skillSetChooser2ItemStateChangedActions() throws SQLException {
        if (skillSetChooser2.getSelectionModel().isEmpty()) {
            skillSetChooser2.setItems(DatabaseModifier.getSkillSet());
            skillSetChooser2.getSelectionModel().select(0);
        }
        skillSubSetChooser2.setItems(DatabaseModifier.getSubSkillSet(skillSetChooser2.getSelectionModel().getSelectedItem().toString()));
        skillSubSetChooser2.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void skillSubSetChooser2ItemStateChangedActions() throws SQLException {
        if (skillSubSetChooser2.getSelectionModel().isEmpty()) {
            skillSubSetChooser2.setItems(DatabaseModifier.getSubSkillSet(skillSetChooser2.getSelectionModel().getSelectedItem().toString()));
            skillSubSetChooser2.getSelectionModel().select(0);
        }
        setAvailableSkills(BuilderCORE.mergeListViews(skillsList2, skillsList4));
    }

    /**
     *
     * @throws SQLException
     */
    @FXML
    public void moveSkillButton2Actions() throws SQLException {
        moveSkill1();
        availableSkillsList1.getSelectionModel().clearSelection();
        setAvailableSkills(BuilderCORE.mergeListViews(skillsList2, skillsList4));
        showBase();
        /*skillList 2 or 4 set?*/
        DatabaseModifier.getAddedSkills(DatabaseModifier.holdClass[DatabaseModifier.b].getSkills() + DatabaseModifier.holdSpecies.getSkills());
        BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        int cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, DatabaseModifier.holdClass, DatabaseModifier.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue2.setText(tex);
        populateLabels();
        subSkillsList2.getItems().clear();
        subSkillText2.setText("");
    }

    void moveSkill1() throws SQLException {
        if (availableSkillsList1.getSelectionModel().getSelectedItem() != null) {
            //SkillsLeftActual2.setText(skillsLeftModify(AvailableSkillsList1.getSelectedValue(),true));
            DatabaseModifier.holdClass[DatabaseModifier.b].addSkills(availableSkillsList1.getSelectionModel().getSelectedItem().toString());
            DatabaseModifier.holdClass[DatabaseModifier.b].addSkills(";");
        }
        if (skillsList2.getSelectionModel().getSelectedItem() != null) {
            //SkillsLeftActual2.setText(skillsLeftModify(SkillsList2.getSelectedValue().split(" \\(")[0],false));
            DatabaseModifier.holdClass[DatabaseModifier.b].setSkills(DatabaseModifier.holdClass[DatabaseModifier.b].getSkills().replace(skillsList2.getSelectionModel().getSelectedItem().toString().split(" \\(")[0] + ";", ""));
        }

        if (!("".equals(DatabaseModifier.holdClass[DatabaseModifier.b].getSkills()) || DatabaseModifier.holdClass[DatabaseModifier.b].getSkills() == null)) {
            skillsList2.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdClass[DatabaseModifier.b].getSkills()));
            BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        } else {
            skillsList2.getItems().clear();
            BuilderFXMLController.HOLD_MODIFIERS.clearModifiers();

        }
        //SkillsLeft.setText(skillsCanTake(LifeDomain));
        int cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, null, DatabaseModifier.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue2.setText(tex);
        populateLabels();
        setAvailableSkills(skillsList2.getItems()/**
         * was it skillsList1? or4?*
         */
        );
    }

    /**
     * populateBasedOnClasses
     */
    public void populateBasedOnClasses() {
        ObservableList lst = FXCollections.observableArrayList();
        lst.add("<base species>");
        for (int i = 1; i < DatabaseModifier.numberOfClases + 1; i++) {
            lst.add(DatabaseModifier.holdClass[i - 1].getClassName());
        }
        basedOnChooser.setItems(lst);
        basedOnChooser.getSelectionModel().select(0);
    }

    /**
     *
     * @throws SQLException
     */
    public void showBase() throws SQLException {
        if ("<base species>".equals(basedOnChooser.getSelectionModel().getSelectedItem().toString())) {
            skillsList4.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdSpecies.getSkills()));
            BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        } else {
            skillsList4.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.getBaseAddedSkills(basedOnChooser.getSelectionModel().getSelectedItem().toString())));
            BuilderFXMLController.getSkillModifiers(DatabaseModifier.ruledskills);
        }
        int cost = DatabaseModifier.baseAddedCost(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdSpecies, DatabaseModifier.holdClass, DatabaseModifier.b, 0);
        String tex = Integer.toString(cost);
        pointsPerModelValue2.setText(tex);
        populateLabels();
    }

    /**
     *
     * @throws SQLException
     */
    public void populateDropdownsClassType() throws SQLException {
        ObservableList tmp = FXCollections.observableArrayList();
        chooseConnection(UseCases.COREdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassTypes FROM StartingCharacteristics WHERE LifeDomain =?");
        stmt.setString(1, DatabaseModifier.holdSpecies.getLifedomain().toString());
        String[] types = BuilderCORE.getValue(stmt, "ClassTypes").split(",");
        tmp.setAll((Object[]) types);
        classTypeChooser.setItems(tmp);
        classTypeChooser.getSelectionModel().select(0);
    }

    /**
     * createFinish2Actions
     *
     * @throws java.sql.SQLException
     */
    @FXML
    public void createFinish2Actions() throws SQLException {
        //SpeciesCreatorWindowController.SetMaxClassNumber(); //why?
        Stage stage = (Stage) createFinish2.getScene().getWindow();
        stage.hide();
        if (DatabaseModifier.classIsModyfying) {
            DatabaseModifier.classIsModyfying = !DatabaseModifier.classIsModyfying;
            DatabaseModifier.modifyClass(0);
            DatabaseModifier.holdSpecies = null;
            DatabaseModifier.modifiedHoldSpecies = null;
            speciesList.setItems(BuilderCORE.getSpeciesList());
            speciesList.getSelectionModel().clearSelection();
        } else {
            DatabaseModifier.numberOfClases++;
            DatabaseModifier.recognizeClassDo(DatabaseModifier.holdSpecies.getLifedomain(), DatabaseModifier.holdClass[DatabaseModifier.b].getType(), true);
            DatabaseModifier.holdClass[DatabaseModifier.b].setClassName(nameInputField2.getText());
            classList1.getItems().add(DatabaseModifier.holdClass[DatabaseModifier.b].getClassName());
            classesLeft3b.setText(DatabaseModifier.classLeftModify());
            DatabaseModifier.searchFreeClassSpot();
            DatabaseModifier.setNumberOfClases();
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void populateLabels() throws SQLException {
        for (int i = 0; i < valuesLabels2.length; i++) {
            valuesLabels2[i].setText(GenesysProjectBuilder.CORE.getCharacteristics(DatabaseModifier.holdSpecies.getLifedomain().toString(), DatabaseModifier.holdSpecies.getCharacteristicGroup().toString())[i]);
        }
    }

    /**
     * clearLists2
     */
    public void clearLists2() {
        strengthValue2.setText("");
        toughnessValue2.setText("");
        movementValue2.setText("");
        martialValue2.setText("");
        rangedValue2.setText("");
        defenseValue2.setText("");
        disciplineValue2.setText("");
        willpowerValue2.setText("");
        commandValue2.setText("");
        mTValue2.setText("");
        rTValue2.setText("");
        moraleValue2.setText("");
        pointsPerModelValue2.setText("");
        woundsValue2.setText("");
        sizeValue2.setText("");
        attacksValue2.setText("");
        skillsList2.getItems().clear();
    }

    @FXML
    private void subSkillsListMouse2Pressed() throws SQLException {
        if (!subSkillsList2.getSelectionModel().isEmpty()) {
            subSkillText2.setText(BuilderCORE.generateSubSkillText(subSkillsList2.getSelectionModel().getSelectedItem().toString(), !simplifyToCoreSkills));
        }
    }

    @FXML
    private void showAsCoreSkillsPressed() throws SQLException {
        simplifyToCoreSkills = showAsCoreSkills.isSelected();
        int i = -1;
        if (!subSkillsList2.getSelectionModel().isEmpty()) {
            i = subSkillsList2.getSelectionModel().getSelectedIndex();
        }
        if (!skillsList2.getSelectionModel().isEmpty()) {
            subSkillsList2.setItems(BuilderCORE.generateSubSkills(skillsList2.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
        if (!availableSkillsList1.getSelectionModel().isEmpty()) {
            subSkillsList2.setItems(BuilderCORE.generateSubSkills(availableSkillsList1.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
        if (!skillsList4.getSelectionModel().isEmpty()) {
            subSkillsList2.setItems(BuilderCORE.generateSubSkills(skillsList4.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
        if (-1 != i) {
            subSkillsList2.getSelectionModel().select(i);
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
        this.valuesLabels2 = new Label[]{strengthValue2, toughnessValue2, movementValue2, martialValue2, rangedValue2, defenseValue2, disciplineValue2, willpowerValue2, commandValue2, woundsValue2, attacksValue2, sizeValue2, mTValue2, rTValue2, moraleValue2};
        lifeDomainValue2.setText(DatabaseModifier.holdSpecies.getLifedomain().toString());
        switch (DatabaseModifier.holdSpecies.getLifedomain()) {
            case Humanoid:
                skillSetText2.setText(BuilderCORE.HUMANOIDSKILS);
                skillSubSetText2.setText(BuilderCORE.HUMANOIDSUBSKILS);
                break;
            case Fey:
                skillSetText2.setText(BuilderCORE.FEYSKILS);
                skillSubSetText2.setText(BuilderCORE.FEYSUBSKILS);
                break;
            case Reptilia:
                skillSetText2.setText(BuilderCORE.REPTILIASKILS);
                skillSubSetText2.setText(BuilderCORE.REPTILIASUBSKILS);
                break;
            case Biest:
                break;
            case Insecta:
                break;
        }
        speciesNameValue2.setText(DatabaseModifier.holdSpecies.getSpeciesName());
        populateBasedOnClasses();
        try {
            populateDropdownsClassType();
            skillSetChooser2.setItems(DatabaseModifier.getSkillSet());
            skillSetChooser2.getSelectionModel().select(0);
            skillSubSetChooser2.setItems(DatabaseModifier.getSubSkillSet(skillSetChooser2.getSelectionModel().getSelectedItem().toString()));
            skillSubSetChooser2.getSelectionModel().select(0);
            if (DatabaseModifier.classIsModyfying) {
                createFinish2.setText("Modify Class");
                nameInputField2.setText(DatabaseModifier.holdClass[0].getClassName());
                basedOnChooser.getSelectionModel().select(DatabaseModifier.holdClass[0].getBasedOn());
                classTypeChooser.getSelectionModel().select(DatabaseModifier.holdClass[0].getType());
                skillsList2.setItems(DatabaseModifier.getAddedSkills(DatabaseModifier.holdClass[0].getSkills()));
                showBase();
                setAvailableSkills(BuilderCORE.mergeListViews(skillsList2, skillsList4));
            } else {
                createFinish2.setText("Add Class");
                nameInputField2.setText(">enter name here<");
                clearLists2();
                DatabaseModifier.holdClass[DatabaseModifier.b].setBasedOn(basedOnChooser.getSelectionModel().getSelectedItem().toString());
                DatabaseModifier.holdClass[DatabaseModifier.b].setType(classTypeChooser.getSelectionModel().getSelectedItem().toString());
                DatabaseModifier.searchFreeClassSpot();
                DatabaseModifier.holdClass[DatabaseModifier.b].setAdditionalCost("0");
                setAvailableSkills(DatabaseModifier.excludeSkills.getItems());
                showBase();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClassCreatorWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        simplifyToCoreSkills = false;
    }

    void setClassesLeft3b(Label classesLeft3b) {
        this.classesLeft3b = classesLeft3b;
    }

    void setClassList1(ListView classList1) {
        this.classList1 = classList1;
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
