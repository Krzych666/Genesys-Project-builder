/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.AvailableSkillsLister;
import genesys.project.builder.BuilderCORE;
import genesys.project.builder.Enums.Enmuerations.LifedomainValue;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseWriter;
import java.io.IOException;
import java.net.URL;
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

/**
 * FXML Controller class
 *
 * @author krzysztofg
 */
public class CreateHoldWindowController implements Initializable {

    @FXML
    private ListView skillsList3;
    @FXML
    private ListView classList1;

    @FXML
    private Label rTValue3;
    @FXML
    private Label mTText3;
    @FXML
    private Label commandText3;
    @FXML
    private Label rTText3;
    @FXML
    private Label commandValue3;
    @FXML
    private Label moraleText3;
    @FXML
    private Label mTValue3;
    @FXML
    private Label moraleValue3;
    @FXML
    private Label sizeText3;
    @FXML
    private Label attacksText3;
    @FXML
    private Label woundsText3;
    @FXML
    private Label pointsPerModelValue3;
    @FXML
    private Label woundsValue3;
    @FXML
    private Label sizeValue3;
    @FXML
    private Label attacksValue3;
    @FXML
    private Label characteristicsText3;
    @FXML
    private Label targetNumbersText3;
    @FXML
    private Label pointsPerModelText3;
    @FXML
    private Label strengthText3;
    @FXML
    private Label strengthValue3;
    @FXML
    private Label toughnessText3;
    @FXML
    private Label toughnessValue3;
    @FXML
    private Label movementText3;
    @FXML
    private Label movementValue3;
    @FXML
    private Label martialText3;
    @FXML
    private Label martialValue3;
    @FXML
    private Label rangedText3;
    @FXML
    private Label rangedValue3;
    @FXML
    private Label defenseText3;
    @FXML
    private Label defenseValue3;
    @FXML
    private Label disciplineValue3;
    @FXML
    private Label willpowerValue3;
    @FXML
    private Label disciplineText3;
    @FXML
    private Label willpowerText3;
    @FXML
    private Label lifeDomainText3;
    @FXML
    private Label lifeDomainValue3;
    @FXML
    private Label skillsText3;
    @FXML
    private Label clasesLabel3;
    @FXML
    private Button addClassButton;
    @FXML
    private Button createSpeciesFinalButton;
    @FXML
    private Label speciesNameValue3;
    @FXML
    private Label speciesNameText3;
    @FXML
    private Label classTypeText3;
    @FXML
    private Label classTypeValue3;
    @FXML
    private Label basedOnText3;
    @FXML
    private Label basedOnValue3;
    @FXML
    private Label classesLeft3a;
    @FXML
    private Label classesLeft3b;

    /**
     * classCreatorWindowStage
     */
    public Stage classCreatorWindowStage = new Stage();
    private ClassCreatorWindowController classCreatorWindowController;
    private ListView speciesList;
    private Label[] valuesLabels3;

    /**
     *
     * @
     */
    @FXML
    public void classList1MousePressedActions() {
        if (!classList1.getSelectionModel().isEmpty()) {
            DatabaseHolder.classList1Holder = classList1.getSelectionModel().getSelectedItem().toString();
            int cost;
            if ("<base species>".equals(classList1.getSelectionModel().getSelectedItem().toString())) {
                skillsList3.setItems(AvailableSkillsLister.getAddedSkills(DatabaseHolder.holdSpecies.getSkills()));
                BuilderCORE.getSkillModifiers(DatabaseHolder.ruledskills);
                classTypeValue3.setText("");
                basedOnValue3.setText("");
                cost = BuilderCORE.baseAddedCost(DatabaseHolder.holdSpecies.getLifedomain(), DatabaseHolder.holdSpecies, null, 0, 0);
            } else {
                for (int i = 0; i < DatabaseHolder.holdClass.length; i++) {
                    if (DatabaseHolder.holdClass[i].getClassName().equals(classList1.getSelectionModel().getSelectedItem().toString())) {
                        DatabaseHolder.b = i;
                    }
                }
                DatabaseHolder.fullSkillList1 = "";
                skillsList3.setItems(AvailableSkillsLister.getAddedSkills(BuilderCORE.getBaseAddedSkills(classList1.getSelectionModel().getSelectedItem().toString())));
                BuilderCORE.getSkillModifiers(DatabaseHolder.ruledskills);
                classTypeValue3.setText(DatabaseHolder.holdClass[DatabaseHolder.b].getType());
                basedOnValue3.setText(DatabaseHolder.holdClass[DatabaseHolder.b].getBasedOn());
                cost = BuilderCORE.baseAddedCost(DatabaseHolder.holdSpecies.getLifedomain(), DatabaseHolder.holdSpecies, DatabaseHolder.holdClass, DatabaseHolder.b, 0);
            }
            String tex = Integer.toString(cost);
            pointsPerModelValue3.setText(tex);
            populateLabels();
        }
    }

    /**
     * addClassButtonActions
     */
    @FXML
    public void addClassButtonActions() {
        if (classCreatorWindowStage.isShowing()) {
            classCreatorWindowStage.requestFocus();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/ClassCreatorWindowFXML.fxml"));
                Parent root = loader.load();
                classCreatorWindowController = loader.getController();
                classCreatorWindowController.setClassList1(classList1);
                classCreatorWindowController.setClassesLeft3b(classesLeft3b);
                Scene scene = new Scene(root);
                classCreatorWindowStage.setScene(scene);
                classCreatorWindowStage.setTitle("Create Class for " + DatabaseHolder.holdCulture.getSpeciesName() + " - " + DatabaseHolder.holdCulture.getCultureName());
                classCreatorWindowStage.show();
            } catch (IOException ex) {
                ErrorController.ErrorController(ex);
                Logger.getLogger(CreateHoldWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @
     */
    @FXML
    public void createSpeciesFinalButtonActions() {
        if (DatabaseHolder.isModyfying) {
            DatabaseHolder.isModyfying = !DatabaseHolder.isModyfying;
            for (int i = 0; i < DatabaseHolder.holdClass.length; i++) {
                if (DatabaseHolder.holdClass[i].getSkills().length() > 2) {
                    DatabaseHolder.holdClass[i].setSkills(DatabaseHolder.holdClass[i].getSkills().substring(0, DatabaseHolder.holdClass[i].getSkills().length() - 1));
                }
                DatabaseWriter.modifyClass(i);
            }
            //DatabaseModifier.modifyHero();
            //DatabaseModifier.modifyProgress();
            //DatabaseModifier.modifyRoster();
            DatabaseHolder.holdSpecies = null;
            DatabaseHolder.modifiedHoldSpecies = null;
        } else {
            DatabaseWriter.writeToDB();
        }

        speciesList.setItems(DatabaseReader.getSpeciesList());
        speciesList.getSelectionModel().clearSelection();
        Stage stage = (Stage) createSpeciesFinalButton.getScene().getWindow();
        stage.hide();
    }

    /**
     *
     * @
     */
    public void createHoldUpdate() {
        lifeDomainValue3.setText(DatabaseHolder.holdSpecies.getLifedomain().toString());
        speciesNameValue3.setText(DatabaseHolder.holdSpecies.getSpeciesName());
        ObservableList tmp = FXCollections.observableArrayList();
        tmp.add("<base species>");
        if (LifedomainValue.Fey.equals(BuilderFXMLController.currentLifeDomain)) {
            tmp.add("<lesser species>");
            tmp.add("<greater species>");
        }
        BuilderCORE.setNumberOfClases();
        for (DatabaseHolder.AClass holdClas : DatabaseHolder.holdClass) {
            if (holdClas.getClassName() != null && !holdClas.getClassName().equals("")) {
                tmp.add(holdClas.getClassName());
            }

        }
        classList1.setItems(tmp);
        clearLists3();
        classesLeft3a.setText(DatabaseReader.classCanTake());
        classesLeft3b.setText(DatabaseHolder.classLeftModify());
    }

    /**
     *
     * @
     */
    public void populateLabels() {
        for (int i = 0; i < valuesLabels3.length; i++) {
            valuesLabels3[i].setText(DatabaseReader.getCharacteristics(DatabaseHolder.holdSpecies.getLifedomain().toString(), DatabaseHolder.holdSpecies.getCharacteristicGroup().toString())[i]);
        }
    }

    /**
     * clearLists3
     */
    public void clearLists3() {
        strengthValue3.setText("");
        toughnessValue3.setText("");
        movementValue3.setText("");
        martialValue3.setText("");
        rangedValue3.setText("");
        defenseValue3.setText("");
        disciplineValue3.setText("");
        willpowerValue3.setText("");
        commandValue3.setText("");
        mTValue3.setText("");
        rTValue3.setText("");
        moraleValue3.setText("");
        pointsPerModelValue3.setText("");
        woundsValue3.setText("");
        sizeValue3.setText("");
        attacksValue3.setText("");
        skillsList3.getItems().clear();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.valuesLabels3 = new Label[]{strengthValue3, toughnessValue3, movementValue3, martialValue3, rangedValue3, defenseValue3, disciplineValue3, willpowerValue3, commandValue3, woundsValue3, attacksValue3, sizeValue3, mTValue3, rTValue3, moraleValue3};
        createHoldUpdate();
        if (!DatabaseHolder.isModyfying && DatabaseHolder.holdCulture != null && DatabaseHolder.holdCulture.getAge() < 1) {
            DatabaseHolder.holdCulture.setAge(1);
        }
    }

    void setSpeciesList(ListView speciesList) {
        this.speciesList = speciesList;
    }

}
