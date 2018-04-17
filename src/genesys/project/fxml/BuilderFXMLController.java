/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.BuilderCORE.Enmuerations.LifedomainValue;
import genesys.project.builder.BuilderCORE.Enmuerations.UseCases;
import genesys.project.builder.BuilderCORE.TheModifiers;
import genesys.project.builder.GenesysProjectBuilder;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import static genesys.project.builder.BuilderCORE.chooseConnection;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

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
     * category number
     */
    private static int catnumber;

    /**
     * where
     */
    private static int where;

    /**
     * currentLifeDomain
     */
    public static LifedomainValue currentLifeDomain;
    private String fullSkillList;

    @FXML
    private ListView speciesList;
    @FXML
    private ListView classList;
    @FXML
    private ListView rostersList;
    @FXML
    private ListView culturesList;
    @FXML
    private ListView cultureProgressList;
    @FXML
    private ListView heroesList;
    @FXML
    private ListView skillsList;
    @FXML
    private ListView subSkillsList;
    @FXML
    private TextArea skillFullText;
    @FXML
    private TextArea fullroster;
    @FXML
    private Button create;
    @FXML
    private Button delete;
    @FXML
    private Button duplicate;
    @FXML
    private Button edit;
    @FXML
    private Label speciesLabel;
    @FXML
    private Label clasesLabel;
    @FXML
    private Label culturesLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private Label rosterLabel;
    @FXML
    private Label heroesLabel;
    @FXML
    private Label pointsPerModelText;
    @FXML
    private Label strengthText;
    @FXML
    private Label strengthValue;
    @FXML
    private Label toughnessText;
    @FXML
    private Label toughnessValue;
    @FXML
    private Label movementText;
    @FXML
    private Label movementValue;
    @FXML
    private Label martialText;
    @FXML
    private Label martialValue;
    @FXML
    private Label rangedText;
    @FXML
    private Label rangedValue;
    @FXML
    private Label defenseText;
    @FXML
    private Label defenseValue;
    @FXML
    private Label disciplineValue;
    @FXML
    private Label willpowerValue;
    @FXML
    private Label disciplineText;
    @FXML
    private Label willpowerText;
    @FXML
    private Label commandText;
    @FXML
    private Label commandValue;
    @FXML
    private Label mTValue;
    @FXML
    private Label rTValue;
    @FXML
    private Label mTText;
    @FXML
    private Label rTText;
    @FXML
    private Label moraleText;
    @FXML
    private Label moraleValue;
    @FXML
    private Label sizeText;
    @FXML
    private Label attacksText;
    @FXML
    private Label woundsText;
    @FXML
    private Label pointsPerModelValue;
    @FXML
    private Label woundsValue;
    @FXML
    private Label sizeValue;
    @FXML
    private Label attacksValue;
    @FXML
    private Label characteristicsText;
    @FXML
    private Label targetNumbersText;
    @FXML
    private Label skillsText;
    @FXML
    private Label classTypeText;
    @FXML
    private Label classTypeValue;
    @FXML
    private Label basedOnText;
    @FXML
    private Label basedOnValue;
    @FXML
    private CheckBox showAsCoreSkills;

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

    private Label[] valuesLabels;
    private Boolean simplifyToCoreSkills;

    @FXML
    private void editActionPerformed() throws IOException {
        //BuilderGuiJframe.EditNameOnlyCheckbox.setSelected(false);
        //DatabaseModifier.PopulateDropdownsSpecies(MainButtons.Edit);
        if (editWindowStage.isShowing()) {
            editWindowStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/EditWindowFXML.fxml"));
            Parent root = loader.load();
            editWindowController = loader.getController();
            editWindowController.setSpeciesList(speciesList);
            Scene scene = new Scene(root);
            editWindowStage.setScene(scene);
            editWindowStage.show();
        }
    }

    @FXML
    private void createActionPerformed() throws IOException {
        if (createWindowWhatStage.isShowing()) {
            createWindowWhatStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/CreateWindowWhatFXML.fxml"));
            Parent root = loader.load();
            createWindowWhatController = loader.getController();
            createWindowWhatController.setSpeciesList(speciesList);
            Scene scene = new Scene(root);
            createWindowWhatStage.setScene(scene);
            createWindowWhatStage.show();
        }
    }

    @FXML
    private void duplicateActionPerformed() throws IOException {
        //DatabaseModifier.PopulateDropdownsSpecies(MainButtons.Duplicate);
        //BuilderGuiJframe.DuplicateNewNameValue.setText(">enter name here<");
        if (duplicateWindowStage.isShowing()) {
            duplicateWindowStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DuplicateWindowFXML.fxml"));
            Parent root = loader.load();
            duplicateWindowController = loader.getController();
            duplicateWindowController.setSpeciesList(speciesList);
            duplicateWindowController.setDuplicateWindowController(duplicateWindowController);
            Scene scene = new Scene(root);
            duplicateWindowStage.setScene(scene);
            duplicateWindowStage.show();
        }

    }

    @FXML
    private void deleteActionPerformed() throws IOException {
        //DatabaseModifier.PopulateDropdownsSpecies(MainButtons.Delete);
        if (deleteWindowStage.isShowing()) {
            deleteWindowStage.requestFocus();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DeleteWindowFXML.fxml"));
            Parent root = loader.load();
            deleteWindowController = loader.getController();
            deleteWindowController.setSpeciesList(speciesList);
            Scene scene = new Scene(root);
            deleteWindowStage.setScene(scene);
            deleteWindowStage.show();
        }

    }

    @FXML
    private void speciesListMousePressed() throws SQLException {
        if (!speciesList.getSelectionModel().isEmpty()) {
            HOLD_MODIFIERS.clearModifiers();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomain FROM CreatedSpecies WHERE SpeciesName = ?");
            stmt.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            currentLifeDomain = (LifedomainValue.valueOf(BuilderCORE.getValue(stmt, "LifeDomain")));
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedSpecies WHERE SpeciesName = ?");
            stmt1.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            skillsList.setItems(getSkills("SpeciesName", BuilderCORE.getValue(stmt1, "Skills"), speciesList.getSelectionModel().getSelectedItem().toString(), null));
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT CultureName FROM CreatedCultures WHERE SpeciesName = ?");
            stmt3.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            culturesList.setItems(BuilderCORE.getData(stmt3, "CultureName", null));
            int cost = baseCost(currentLifeDomain, speciesList.getSelectionModel().getSelectedItem().toString(),
                    null/*culturesList.getSelectionModel().getSelectedItem().toString()*/,
                    null/*classList.getSelectionModel().getSelectedItem().toString()*/,
                    0);
            String tex = Integer.toString(cost);
            pointsPerModelValue.setText(tex);
            populateLabels();
            subSkillsList.getItems().clear();
            skillFullText.setText("");
        }
    }

    @FXML
    private void culturesListMousePressed() throws SQLException {
        if (!culturesList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty()) {
            skillsList.setItems(getSkills("SpeciesName", speciesList.getSelectionModel().getSelectedItem().toString(), speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString()));
            ObservableList tmp = FXCollections.observableArrayList();
            tmp.add(BuilderCORE.BASE);
            if (LifedomainValue.Fey.equals(currentLifeDomain)) {
                tmp.add(BuilderCORE.LESSER);
                tmp.add(BuilderCORE.GREATER);
            }
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT ClassName FROM CreatedClasses WHERE SpeciesName = ? AND (CultureName = ? OR CultureName is null)");
            stmt.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(2, culturesList.getSelectionModel().getSelectedItem().toString());
            tmp.addAll(BuilderCORE.getData(stmt, "ClassName", null));
            classList.setItems(tmp);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT ProgressName FROM CreatedProgress WHERE SpeciesName = ? AND CultureName = ?");
            stmt1.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt1.setString(2, culturesList.getSelectionModel().getSelectedItem().toString());
            cultureProgressList.setItems(BuilderCORE.getData(stmt1, "ProgressName", null));
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT HeroName FROM CreatedHeroes WHERE SpeciesName = ? AND CultureName = ?");
            stmt2.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt2.setString(2, culturesList.getSelectionModel().getSelectedItem().toString());
            heroesList.setItems(BuilderCORE.getData(stmt2, "HeroName", null));
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT RosterName FROM CreatedRosters WHERE SpeciesName = ? AND CultureName = ?");
            stmt3.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt3.setString(2, culturesList.getSelectionModel().getSelectedItem().toString());
            rostersList.setItems(BuilderCORE.getData(stmt3, "RosterName", null));
            populateLabels();
        }
        subSkillsList.getItems().clear();
        skillFullText.setText("");
    }

    @FXML
    private void classListMousePressed() throws SQLException {
        if (!classList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            heroesList.getSelectionModel().clearSelection();
            if (BuilderCORE.BASE.equals(classList.getSelectionModel().getSelectedItem().toString())) {
                skillsList.setItems(getSkills("SpeciesName", BuilderCORE.BASE, speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString()));
                classTypeValue.setText("");
                basedOnValue.setText("");
            } else {
                skillsList.setItems(getSkills("ClassName", classList.getSelectionModel().getSelectedItem().toString(), speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString()));
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Type FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
                stmt.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
                stmt.setString(2, culturesList.getSelectionModel().getSelectedItem().toString());
                stmt.setString(3, classList.getSelectionModel().getSelectedItem().toString());
                classTypeValue.setText(BuilderCORE.getValue(stmt, "Type"));
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
                stmt1.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
                stmt1.setString(2, culturesList.getSelectionModel().getSelectedItem().toString());
                stmt1.setString(3, classList.getSelectionModel().getSelectedItem().toString());
                basedOnValue.setText(BuilderCORE.getValue(stmt1, "BasedOn"));
            }
            int cost = baseCost(currentLifeDomain, speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString(), classList.getSelectionModel().getSelectedItem().toString(), 0);
            String tex = Integer.toString(cost);
            pointsPerModelValue.setText(tex);
            populateLabels();
        }
        subSkillsList.getItems().clear();
        skillFullText.setText("");
    }

    @FXML
    private void heroesListMousePressed() throws SQLException {
        if (!heroesList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            classList.getSelectionModel().clearSelection();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedHeroes WHERE HeroName = ? AND SpeciesName = ? AND CultureName = ?");
            stmt.setString(1, heroesList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(2, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(3, culturesList.getSelectionModel().getSelectedItem().toString());
            skillsList.setItems(getSkills("ClassName", BuilderCORE.getValue(stmt, "BasedOn"), speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString()));
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedHeroes WHERE HeroName = ? AND SpeciesName = ? AND CultureName = ?");
            stmt1.setString(1, heroesList.getSelectionModel().getSelectedItem().toString());
            stmt1.setString(2, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt1.setString(3, culturesList.getSelectionModel().getSelectedItem().toString());
            String BasedOn = BuilderCORE.getValue(stmt1, "BasedOn");
            basedOnValue.setText(BasedOn);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT Type FROM CreatedClasses WHERE ClassName = ? AND SpeciesName = ? AND CultureName = ?");
            stmt2.setString(1, BasedOn);
            stmt2.setString(2, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt2.setString(3, culturesList.getSelectionModel().getSelectedItem().toString());
            String HeroClass = BuilderCORE.getValue(stmt2, "Type");
            classTypeValue.setText(HeroClass);
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedHeroes WHERE HeroName = ? AND SpeciesName = ? AND CultureName = ?");
            stmt3.setString(1, heroesList.getSelectionModel().getSelectedItem().toString());
            stmt3.setString(2, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt3.setString(3, culturesList.getSelectionModel().getSelectedItem().toString());
            int cost = baseCost(currentLifeDomain, speciesList.getSelectionModel().getSelectedItem().toString(), culturesList.getSelectionModel().getSelectedItem().toString(), BuilderCORE.getValue(stmt3, "BasedOn"), 0);
            String tex = Integer.toString(cost);
            pointsPerModelValue.setText(tex);
            populateLabels();
        }
        subSkillsList.getItems().clear();
        skillFullText.setText("");
    }

    /**
     *
     * @param tabl
     * @param name
     * @param fSp
     * @param fCu
     * @return
     * @throws SQLException
     */
    public ObservableList<String> getSkills(String tabl, String name, String fSp, String fCu) throws SQLException {
        fullSkillList = "";
        String[] lst = getBaseSkills(tabl, name, fSp, fCu).split(",");
        fullSkillList = "";
        String[] lstref = getBaseSkills(tabl, name, fSp, fCu).split(",");
        List<String> ruledskills = new ArrayList<>();
        for (int i = 0; i < lst.length; i++) {
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT SkillRules FROM Skills WHERE SkillName = ?");
            stmt.setString(1, lstref[i]);
            String[] skl = ((BuilderCORE.getValue(stmt, "SkillRules")).split(";"));
            chooseConnection(UseCases.COREdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
            stmt1.setString(1, lstref[i]);
            lst[i] += " (pts : " + (BuilderCORE.getValue(stmt1, "PointCost")) + ") : ";
            for (int j = 0; j < skl.length; j++) {
                lst[i] += ((skl[j].split("_"))[0]);
                if ((skl[j].split("_")).length >= 2) {
                    lst[i] += (" " + ((skl[j].split("_"))[1]));
                }
                if ((skl[j].split("_")).length >= 3) {
                    lst[i] += (" " + ((skl[j].split("_"))[2]));
                }
                if (j < skl.length - 1) {
                    lst[i] += ", ";
                }
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT LifeDomainTree2 FROM Skills WHERE SkillName = ?");
                stmt3.setString(1, lstref[i]);
                ruledskills.add((BuilderCORE.getValue(stmt3, "LifeDomainTree2")) + ">" + skl[j]);
            }
        }
        ObservableList<String> tmp = FXCollections.observableArrayList();
        tmp.addAll(lst);
        if (!ruledskills.get(0).equals(">")) {
            getSkillModifiers(ruledskills);
        }
        return tmp;
    }

    private String getBaseSkills(String colu, String name, String fSp, String fCu) throws SQLException {
        if ("SpeciesName".equals(colu) || name.equals(BuilderCORE.BASE)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedSpecies WHERE SpeciesName = ?");
            stmt.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            fullSkillList = BuilderCORE.getValue(stmt, "Skills");
        }
        if ("ClassName".equals(colu) && (!name.equals(BuilderCORE.BASE))) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
            stmt.setString(1, fSp);
            stmt.setString(2, fCu);
            stmt.setString(3, name);
            String skillsThis = BuilderCORE.getValue(stmt, "Skills");
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
            stmt1.setString(1, fSp);
            stmt1.setString(2, fCu);
            stmt1.setString(3, name);
            String basedOn = BuilderCORE.getValue(stmt1, "BasedOn");
            String skillsBefore = "";
            if (!basedOn.equals("") && !basedOn.equals("null")) {
                skillsBefore = getBaseSkills(colu, basedOn, fSp, fCu);
            }
            if (!skillsThis.equals("") && !skillsThis.equals("null") && !skillsBefore.equals("")) {
                fullSkillList = (skillsThis + ";" + skillsBefore);
            } else {
                fullSkillList = (skillsBefore);
            }
        }
        return fullSkillList;
    }

    /**
     *
     * @param lifeDomain
     * @param species
     * @param culture
     * @param classname
     * @param points
     * @return
     * @throws SQLException
     */
    public int baseCost(LifedomainValue lifeDomain, String species, String culture, String classname, int points) throws SQLException {
        int a = 1;
        chooseConnection(UseCases.Userdb);
        PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Type FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
        stmt.setString(1, species);
        stmt.setString(2, culture);
        stmt.setString(3, classname);
        switch (BuilderCORE.getValue(stmt, "Type")) {
            case "Standard Class":
                a = 1;
                break;
            case "Elite Class":
                a = 2;
                break;
            case "Leader Class":
                a = 3;
                break;
            case "Unique Class":
                a = 2;
                break;
            default:
                break;
        }
        if (classname != null && !classname.equals(BuilderCORE.BASE) && !classname.equals(BuilderCORE.LESSER) && !classname.equals(BuilderCORE.GREATER)) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt1 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
            stmt1.setString(1, species);
            stmt1.setString(2, culture);
            stmt1.setString(3, classname);
            String[] lst = ((BuilderCORE.getValue(stmt1, "Skills")).split(","));
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt3 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
            stmt3.setString(1, species);
            stmt3.setString(2, culture);
            stmt3.setString(3, classname);
            String basedOn = BuilderCORE.getValue(stmt3, "BasedOn");
            points += a * baseCost(lifeDomain, species, culture, basedOn, points);
            for (String lst1 : lst) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt2 = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
                stmt2.setString(1, lst1);
                String add = BuilderCORE.getValue(stmt2, "PointCost");
                if (add.equals("")) {
                    add = "0";
                }
                if (add.contains("/")) {
                    add = add.split("/")[0];
                }
                if (!basedOn.equals("") && !basedOn.equals("null")) {
                    if (!"".equals(add)) {
                        points += Integer.parseInt(add);
                    }
                }
            }
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt4 = BuilderCORE.getConnection().prepareStatement("SELECT AdditionalCost FROM CreatedClasses WHERE SpeciesName = ? AND CultureName = ? AND ClassName = ?");
            stmt4.setString(1, species);
            stmt4.setString(2, culture);
            stmt4.setString(3, classname);
            points += Integer.parseInt(BuilderCORE.getValue(stmt4, "AdditionalCost"));
        } else {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt5 = BuilderCORE.getConnection().prepareStatement("SELECT Skills FROM CreatedSpecies WHERE SpeciesName = ?");
            stmt5.setString(1, speciesList.getSelectionModel().getSelectedItem().toString());
            String[] lst = ((BuilderCORE.getValue(stmt5, "Skills")).split(","));
            for (String lst1 : lst) {
                chooseConnection(UseCases.COREdb);
                PreparedStatement stmt6 = BuilderCORE.getConnection().prepareStatement("SELECT PointCost FROM Skills WHERE SkillName = ?");
                stmt6.setString(1, lst1);
                String add = BuilderCORE.getValue(stmt6, "PointCost");
                if (add.equals("")) {
                    add = "0";
                }
                if (add.contains("/")) {
                    add = add.split("/")[0];
                }
                points += Integer.parseInt(add);
            }
        }
        if (!heroesList.getSelectionModel().isEmpty()) {
            String selHero = heroesList.getSelectionModel().getSelectedItem().toString();
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt7 = BuilderCORE.getConnection().prepareStatement("SELECT BasedOn FROM CreatedHeroes WHERE SpeciesName = ? AND CultureName = ? AND HeroName = ?");
            stmt7.setString(1, species);
            stmt7.setString(2, culture);
            stmt7.setString(3, selHero);
            if (BuilderCORE.getValue(stmt7, "BasedOn").equals(classname)) {
                chooseConnection(UseCases.Userdb);
                PreparedStatement stmt8 = BuilderCORE.getConnection().prepareStatement("SELECT AdditionalCost FROM CreatedHeroes WHERE SpeciesName = ? AND CultureName = ? AND HeroName = ?");
                stmt8.setString(1, species);
                stmt8.setString(2, culture);
                stmt8.setString(3, selHero);
                points += Integer.parseInt(BuilderCORE.getValue(stmt8, "AdditionalCost"));
            }
        }
        return points;
    }

    /**
     *
     * @param ruledskills
     */
    public static void getSkillModifiers(List<String> ruledskills) {
        String[][] tmp = new String[2][ruledskills.size()];
        catnumber = 0;
        List<String> wheretabl = new ArrayList<>();
        for (int i = 0; i < ruledskills.size(); i++) {
            if (i == 0) {
                wheretabl.add(ruledskills.get(i).split(">")[0]);
                tmp[0][i] = ruledskills.get(i).split(">")[0];
                catnumber++;
            }
            if (!ruledskills.get(i).split(">")[0].equals(wheretabl.get(catnumber - 1))) {
                wheretabl.add(ruledskills.get(i).split(">")[0]);
                tmp[0][i] = ruledskills.get(i).split(">")[0];
                catnumber++;
            }
        }

        int[][][] increasestable = new int[catnumber][2][15];
        for (int i = 0; i < catnumber; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 15; k++) {
                    increasestable[i][j][k] = 0;
                }
            }
        }

        for (int i = 0; i < ruledskills.size(); i++) {
            tmp[0][i] = ruledskills.get(i).split(">")[0];
            tmp[1][i] = ruledskills.get(i).split(">")[1];
            if (tmp[1][i].contains("Increase") || tmp[1][i].contains("Decrease") || tmp[1][i].contains("Model Size")) {
                int a = 2;
                if (tmp[1][i].contains("Increase")) {
                    a = 0;
                }
                if (tmp[1][i].contains("Decrease") || tmp[1][i].contains("Model Size")|| tmp[1][i].contains("Increase Size")) {
                    a = 1;
                }
                for (int u = 0; u < wheretabl.size(); u++) {
                    if (tmp[0][i].equals(wheretabl.get(u))) {
                        where = u;
                    }
                }

                String stat = tmp[1][i].split(" ")[1].split("_")[0];
                switch (stat) {
                    case "Wounds":
                        increasestable[where][a][0] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Attacks":
                        increasestable[where][a][1] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Size":
                        increasestable[where][a][2] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Strength":
                        increasestable[where][a][3] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Toughness":
                        increasestable[where][a][4] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Movement":
                        increasestable[where][a][5] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Martial":
                        increasestable[where][a][6] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Ramged":
                        increasestable[where][a][7] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Defense":
                        increasestable[where][a][8] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Discipline":
                        increasestable[where][a][9] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Willpower":
                        increasestable[where][a][10] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Command":
                        increasestable[where][a][11] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "MT":
                        increasestable[where][a][12] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "RT":
                        increasestable[where][a][13] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    case "Morale":
                        increasestable[where][a][14] += Integer.parseInt(tmp[1][i].split(" ")[1].split("_")[1]);
                        break;
                    default:
                        break;
                }
            }
        }

        for (int s = 0; s < 15; s++) {
            int maxinc = 0;
            int totdec = 0;
            for (int i = 0; i < catnumber; i++) {
                if (increasestable[i][0][s] > maxinc) {
                    maxinc = increasestable[i][0][s];
                }
                totdec += increasestable[i][1][s];
            }
            switch (s) {
                case 0:
                    HOLD_MODIFIERS.setWoundsModifier(maxinc - totdec);
                    break;
                case 1:
                    HOLD_MODIFIERS.setAttacksModifier(maxinc - totdec);
                    break;
                case 2:
                    HOLD_MODIFIERS.setSizeModifier(totdec);
                    break;
                case 3:
                    HOLD_MODIFIERS.setStrengthModifier(maxinc - totdec);
                    break;
                case 4:
                    HOLD_MODIFIERS.setToughnessModifier(maxinc - totdec);
                    break;
                case 5:
                    HOLD_MODIFIERS.setMovementModifier(maxinc - totdec);
                    break;
                case 6:
                    HOLD_MODIFIERS.setMartialModifier(maxinc - totdec);
                    break;
                case 7:
                    HOLD_MODIFIERS.setRangedModifier(maxinc - totdec);
                    break;
                case 8:
                    HOLD_MODIFIERS.setDefenseModifier(maxinc - totdec);
                    break;
                case 9:
                    HOLD_MODIFIERS.setDisciplineModifier(maxinc - totdec);
                    break;
                case 10:
                    HOLD_MODIFIERS.setWillpowerModifier(maxinc - totdec);
                    break;
                case 11:
                    HOLD_MODIFIERS.setCommandModifier(maxinc - totdec);
                    break;
                case 12:
                    HOLD_MODIFIERS.setMTModifier(maxinc - totdec);
                    break;
                case 13:
                    HOLD_MODIFIERS.setRTModifier(maxinc - totdec);
                    break;
                case 14:
                    HOLD_MODIFIERS.setMoraleModifier(maxinc - totdec);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void populateLabels() throws SQLException {
        for (int i = 0; i < valuesLabels.length; i++) {
            valuesLabels[i].setText(GenesysProjectBuilder.CORE.getCharacteristics(currentLifeDomain.toString())[i]);
        }
    }

    /**
     * clearLists
     */
    public void clearLists() {
        for (Label valuesLabel : valuesLabels) {
            valuesLabel.setText("");
        }
        culturesList.getItems().clear();
        classList.getItems().clear();
        heroesList.getItems().clear();
        cultureProgressList.getItems().clear();
        rostersList.getItems().clear();
        speciesList.getSelectionModel().clearSelection();
        skillsList.getItems().clear();
        subSkillsList.getItems().clear();
        skillFullText.setText("");
        classTypeValue.setText("");
        basedOnValue.setText("");
    }

    /**
     *
     * @throws SQLException
     */
    public void clearSelectionEvent() throws SQLException {
        clearLists();
        speciesList.setItems(BuilderCORE.getSpeciesList());
    }

    @FXML
    private void rosterListMousePressed() throws SQLException {
        if (!rostersList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Roster FROM CreatedRosters WHERE RosterName = ? AND SpeciesName = ? AND CultureName = ?");
            stmt.setString(1, rostersList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(2, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(3, culturesList.getSelectionModel().getSelectedItem().toString());
            String roster = BuilderCORE.getValue(stmt, "PointCost");
        }
    }

    @FXML
    private void cultureProgressListMousePressed() throws SQLException {
        if (!cultureProgressList.getSelectionModel().isEmpty() && !speciesList.getSelectionModel().isEmpty() && !culturesList.getSelectionModel().isEmpty()) {
            chooseConnection(UseCases.Userdb);
            PreparedStatement stmt = BuilderCORE.getConnection().prepareStatement("SELECT Progress FROM CreatedProgress WHERE ProgressName = ? AND SpeciesName = ? AND CultureName = ?");
            stmt.setString(1, cultureProgressList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(2, speciesList.getSelectionModel().getSelectedItem().toString());
            stmt.setString(3, culturesList.getSelectionModel().getSelectedItem().toString());
            String add = BuilderCORE.getValue(stmt, "PointCost");
        }
    }

    @FXML
    private void skillsListMousePressed() throws SQLException {
        skillFullText.setText("");
        if (!skillsList.getSelectionModel().isEmpty()) {
            subSkillsList.setItems(BuilderCORE.generateSubSkills(skillsList.getSelectionModel().getSelectedItem().toString(), simplifyToCoreSkills));
        }
    }

    @FXML
    private void subSkillsListMousePressed() throws SQLException {
        if (!subSkillsList.getSelectionModel().isEmpty()) {
            skillFullText.setText(BuilderCORE.generateSubSkillText(subSkillsList.getSelectionModel().getSelectedItem().toString(), !simplifyToCoreSkills));
        }
    }

    @FXML
    private void showAsCoreSkillsPressed() throws SQLException {
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

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        simplifyToCoreSkills = false;
        this.valuesLabels = new Label[]{strengthValue, toughnessValue, movementValue, martialValue, rangedValue, defenseValue, disciplineValue, willpowerValue, commandValue, woundsValue, attacksValue, sizeValue, mTValue, rTValue, moraleValue};
        try {
            speciesList.setItems(BuilderCORE.getSpeciesList());
        } catch (SQLException ex) {
            Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                try {
                    clearSelectionEvent();
                } catch (SQLException ex) {
                    Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    speciesListMousePressed();
                } catch (SQLException ex) {
                    Logger.getLogger(BuilderFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}
