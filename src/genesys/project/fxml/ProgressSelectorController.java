/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesys.project.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 *
 * @author krzysztofg
 */
public class ProgressSelectorController implements Initializable {

    @FXML
    private Label DescriptionMessage;
    @FXML
    private ComboBox progressChooseDropdown;
    @FXML
    private Button OKButton;
    @FXML
    private Button CancelButton;

    private ListView rostersList;
    private ObservableList cultureProgressList;

    /**
     * OKButtonActions
     */
    @FXML
    public void OKButtonActions() {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * CancelButtonActions
     */
    @FXML
    public void CancelButtonActions() {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.hide();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void setProgressAndRosters(ObservableList cultureProgressList, ListView rostersList) {
        this.cultureProgressList = cultureProgressList;
        this.rostersList = rostersList;
    }

    void setProgresFieldTo(String selectedProgress) {
        progressChooseDropdown.setItems(cultureProgressList);
        if (progressChooseDropdown.getItems().contains(selectedProgress)) {
            progressChooseDropdown.getSelectionModel().select(selectedProgress);
        }
    }

}
