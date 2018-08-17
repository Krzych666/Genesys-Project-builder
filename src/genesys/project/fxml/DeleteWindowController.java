/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import genesys.project.builder.BuilderCORE;
import genesys.project.builder.DatabaseHolder;
import genesys.project.builder.DatabaseReader;
import genesys.project.builder.DatabaseWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
public class DeleteWindowController implements Initializable {

    /**
     * @FXML private DeleteWindow = new javax.swing.JDialog;
     *
     */
    @FXML
    private ComboBox speciesDeleteDropdown;
    @FXML
    private ComboBox cultureDeleteDropdown;
    @FXML
    private ComboBox classDeleteDropdown;
    @FXML
    private ComboBox heroDeleteDropdown;
    @FXML
    private ComboBox progressDeleteDropdown;
    @FXML
    private ComboBox rosterDeleteDropdown;
    @FXML
    private Label speciesDeleteLabel;
    @FXML
    private Label cultureDeleteLabel;
    @FXML
    private Label classDeleteLabel;
    @FXML
    private Label heroDeleteLabel;
    @FXML
    private Label progressDeleteLabel;
    @FXML
    private Label rosterDeleteLabel;
    @FXML
    private Button deleteFinishButton;
    @FXML
    private Button deleteCancelButton;

    /**
     * deleteAreYouSureStage
     */
    public Stage deleteAreYouSureStage = new Stage();
    private DeleteAreYouSureController deleteAreYouSureController;

    private ListView speciesList;
    private ListView cultureList;
    private ListView rosterList;
    private ListView cultureProgressList;
    private Label toDelete;

    /**
     *
     * @
     */
    @FXML
    public void speciesDeleteDropdownItemStateChangedActions() {
        cultureDeleteDropdown.setItems(DatabaseReader.populateDropdownsCultures(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        cultureDeleteDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @
     */
    @FXML
    public void cultureDeleteDropdownItemStateChangedActions() {
        if (cultureDeleteDropdown.getSelectionModel().isEmpty()) {
            cultureDeleteDropdown.getSelectionModel().select(0);
        }
        classDeleteDropdown.setItems(
                DatabaseReader.populateDropdownsClasses(
                        speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        classDeleteDropdown.getSelectionModel().select(0);

        heroDeleteDropdown.setItems(
                DatabaseReader.populateDropdownsHeroes(
                        speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        classDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDeleteDropdown.getSelectionModel().select(0);

        progressDeleteDropdown.setItems(FXCollections.observableArrayList(DatabaseHolder.TOPDROP));
        progressDeleteDropdown.getItems().addAll(
                BuilderCORE.generateProgressAndBattlesSortedList(
                        speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        "both"));
        progressDeleteDropdown.getSelectionModel().select(0);

        rosterDeleteDropdown.setItems(
                DatabaseReader.populateDropdownsRosters(
                        speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        rosterDeleteDropdown.getSelectionModel().select(0);
    }

    /**
     *
     * @
     */
    @FXML
    public void classDeleteDropdownItemStateChangedActions() {
        if (classDeleteDropdown.getSelectionModel().isEmpty()) {
            classDeleteDropdown.getSelectionModel().select(0);
        }
        Object sel = classDeleteDropdown.getSelectionModel().getSelectedItem();

        heroDeleteDropdown.setItems(
                DatabaseReader.populateDropdownsHeroes(
                        speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString(),
                        classDeleteDropdown.getSelectionModel().getSelectedItem().toString()));
        heroDeleteDropdown.getSelectionModel().select(0);

        progressDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(0);
        classDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * heroDeleteDropdownItemStateChangedActions
     */
    @FXML
    public void heroDeleteDropdownItemStateChangedActions() {
        Object sel = heroDeleteDropdown.getSelectionModel().getSelectedItem();
        progressDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(0);
        heroDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * progressDeleteDropdownItemStateChangedActions
     */
    @FXML
    public void progressDeleteDropdownItemStateChangedActions() {
        Object sel = progressDeleteDropdown.getSelectionModel().getSelectedItem();
        heroDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(0);
        classDeleteDropdown.getSelectionModel().select(0);
        progressDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * rosterDeleteDropdownItemStateChangedActions
     */
    @FXML
    public void rosterDeleteDropdownItemStateChangedActions() {
        Object sel = rosterDeleteDropdown.getSelectionModel().getSelectedItem();
        heroDeleteDropdown.getSelectionModel().select(0);
        progressDeleteDropdown.getSelectionModel().select(0);
        classDeleteDropdown.getSelectionModel().select(0);
        rosterDeleteDropdown.getSelectionModel().select(sel);
    }

    /**
     * deleteFinishButtonActions
     *
     */
    @FXML
    public void deleteFinishButtonActions() {
        prepareDeleteScript();
        if (deleteAreYouSureStage.isShowing()) {
            deleteAreYouSureStage.requestFocus();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/genesys/project/fxml/DeleteAreYouSureFXML.fxml"));
                Parent root = loader.load();
                deleteAreYouSureController = loader.getController();
                deleteAreYouSureController.setSpeciesList(speciesList);
                deleteAreYouSureController.setDeleteWindow(deleteFinishButton.getScene().getWindow());
                Scene scene = new Scene(root);
                deleteAreYouSureStage.setScene(scene);
                deleteAreYouSureStage.setTitle("Confirm Deletion");
                deleteAreYouSureStage.show();
                toDelete = deleteAreYouSureController.getToDelete();
            } catch (IOException ex) {
                ErrorController.ErrorControllerMethod(ex);
                Logger.getLogger(DeleteWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        showWhatToDelete();
    }

    /**
     * deleteCancelButtonActions
     */
    @FXML
    public void deleteCancelButtonActions() {
        Stage stage = (Stage) deleteCancelButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * prepareDeleteScript
     */
    public void prepareDeleteScript() {
        StringBuilder temp = new StringBuilder();
        if (speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.append(DatabaseWriter.DELETEFROMSTRING[0]).append(";")
                    .append(DatabaseWriter.DELETEFROMSTRING[1]).append(";")
                    .append(DatabaseWriter.DELETEFROMSTRING[2]).append(";")
                    .append(DatabaseWriter.DELETEFROMSTRING[3]).append(";")
                    .append(DatabaseWriter.DELETEFROMSTRING[4]).append(";")
                    .append(DatabaseWriter.DELETEFROMSTRING[5]).append(";");
        }
        if (!speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());

            temp.append(DatabaseWriter.DELETEFROMSTRING[0]).append(DatabaseWriter.WHERESTRING1).append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');")
                    .append(DatabaseWriter.DELETEFROMSTRING[1]).append(DatabaseWriter.WHERESTRING1).append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');")
                    .append(DatabaseWriter.DELETEFROMSTRING[2]).append(DatabaseWriter.WHERESTRING1).append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');")
                    .append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING1).append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');")
                    .append(DatabaseWriter.DELETEFROMSTRING[4]).append(DatabaseWriter.WHERESTRING1).append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');")
                    .append(DatabaseWriter.DELETEFROMSTRING[5]).append(DatabaseWriter.WHERESTRING1).append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");
        }
        if (!cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());

            temp.append(DatabaseWriter.DELETEFROMSTRING[1]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[2]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[4]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[5]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");
        }
        if (!classDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());

            temp.append(DatabaseWriter.DELETEFROMSTRING[2]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[2]).append(classDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[2]).append(classDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[4]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");

            temp.append(DatabaseWriter.DELETEFROMSTRING[5]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");
        }
        if (!heroDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append(DatabaseWriter.DELETEFROMSTRING[3]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[3]).append(heroDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");
        }
        if (!progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            if (progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Progress: ")) {
                temp.append(DatabaseWriter.DELETEFROMSTRING[4]).append(DatabaseWriter.WHERESTRING1)
                        .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                        .append(DatabaseWriter.ANDSTRING1)
                        .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                        .append(DatabaseWriter.ANDSTRING1)
                        .append(DatabaseWriter.NAMESTRING[4]).append(progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Progress: ", "")).append("');");
            }
            if (progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Battle: ")) {
                temp.append(DatabaseWriter.DELETEFROMSTRING[6]).append(DatabaseWriter.WHERESTRING1)
                        .append(DatabaseWriter.NAMESTRING[6]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                        .append(DatabaseWriter.ANDSTRING2)
                        .append(DatabaseWriter.NAMESTRING[7]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                        .append(DatabaseWriter.ORSTRING1)
                        .append(DatabaseWriter.NAMESTRING[8]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                        .append(DatabaseWriter.ANDSTRING2)
                        .append(DatabaseWriter.NAMESTRING[9]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                        .append(DatabaseWriter.ANDSTRING3)
                        .append(DatabaseWriter.NAMESTRING[10]).append(progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Battle: ", "")).append("';");
            }
        }
        if (!rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append(DatabaseWriter.DELETEFROMSTRING[5]).append(DatabaseWriter.WHERESTRING1)
                    .append(DatabaseWriter.NAMESTRING[0]).append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[1]).append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(DatabaseWriter.ANDSTRING1)
                    .append(DatabaseWriter.NAMESTRING[5]).append(rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append("');");
        }
        DatabaseWriter.tempDeletescript = temp.toString();
    }

    /**
     * showWhatToDelete
     */
    public void showWhatToDelete() {
        StringBuilder temp = new StringBuilder();
        if (speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.append("  All Species");
        }
        if (!speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append("  Species: ").append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString());
        }
        if (!cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append("  Culture: ").append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(" (in species: ").append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append(")");
        }
        if (!classDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append("  Class: ").append(classDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(" (in culture: ").append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(", in species: ").append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append(")");
        }
        if (!heroDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append("  Hero: ").append(heroDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(" (in culture: ").append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(", in species: ").append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append(")");
        }
        if (!progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            if (progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Progress: ")) {
                temp.append("  Progress: ").append(progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Progress: ", ""));
            }
            if (progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().startsWith("Battle: ")) {
                temp.append("  Battle History: ").append(progressDeleteDropdown.getSelectionModel().getSelectedItem().toString().replaceFirst("Battle: ", ""));
            }
            temp.append(" (in culture: ").append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(", in species: ").append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append(")");
        }
        if (!rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString().equals(DatabaseHolder.TOPDROP)) {
            temp.delete(0, temp.length());
            temp.append("  Roster: ").append(rosterDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(" (in culture: ").append(cultureDeleteDropdown.getSelectionModel().getSelectedItem().toString())
                    .append(", in species: ").append(speciesDeleteDropdown.getSelectionModel().getSelectedItem().toString()).append(")");
        }
        toDelete.setText(temp.toString());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speciesDeleteDropdown.setItems(DatabaseReader.populateDropdownsSpecies());
        speciesDeleteDropdown.getSelectionModel().select(0);
        speciesDeleteDropdownItemStateChangedActions();
        cultureDeleteDropdownItemStateChangedActions();
    }

    void setDataLists(ListView speciesList, ListView cultureList, ListView rosterList, ListView cultureProgressList) {
        this.speciesList = speciesList;
        this.cultureList = cultureList;
        this.rosterList = rosterList;
        this.cultureProgressList = cultureProgressList;
    }

    void setSelected() {
        if (!speciesList.getSelectionModel().isEmpty()) {
            speciesDeleteDropdown.getSelectionModel().select(speciesList.getSelectionModel().getSelectedItem());
            speciesDeleteDropdownItemStateChangedActions();
            if (!cultureList.getSelectionModel().isEmpty() && !speciesList.isFocused()) {
                cultureDeleteDropdown.getSelectionModel().select(cultureList.getSelectionModel().getSelectedItem());
                cultureDeleteDropdownItemStateChangedActions();
                if (!rosterList.getSelectionModel().isEmpty() && !cultureList.isFocused()) {
                    rosterDeleteDropdown.getSelectionModel().select(rosterList.getSelectionModel().getSelectedItem());
                    rosterDeleteDropdownItemStateChangedActions();
                }
            }
        }
    }

}
