package controllers;

import interfaces.ITaskAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import objects.Task;

import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML private TextField taskName;
    @FXML private CheckBox filterCheckBox;
    @FXML private ChoiceBox filterChoiceBox;
    @FXML private TextField filterElementName;
    @FXML private Label errorInt;
    @FXML private Slider errorSlider;
    @FXML private ChoiceBox refreshTime;
    @FXML private VBox filterVBox;
    @FXML private VBox defaultVBox;

    private ITaskAction iTaskAction;
    private MainController mainController;
    private Task task;

    public EditController(ITaskAction iTaskAction, MainController mainController) {
        this.iTaskAction = iTaskAction;
        this.mainController = mainController;
    }

    public void setTask(Task task) {
        this.task = task;

        this.taskName.setText(task.getTaskName());
        this.refreshTime.setValue(task.getTaskRefTime());
        this.filterCheckBox.setSelected(task.isTaskFilter());
        this.filterChoiceBox.setValue(task.getFilterElement());
        this.filterElementName.setText(task.getFilterElementName());
        this.errorSlider.setValue(task.getError());
        this.errorInt.setText(Integer.toString(task.getError()));
        filterEnabled(task.isTaskFilter());
    }

    public void filterEnabled(boolean enable) {
        if (enable) {
            filterVBox.setDisable(false);
            defaultVBox.setDisable(true);
        } else {
            filterVBox.setDisable(true);
            defaultVBox.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshTime.setItems(FXCollections.observableArrayList(15, 30, 45, 60));
        filterChoiceBox.setItems(FXCollections.observableArrayList("Class", "Id"));

        filterCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    filterEnabled(filterCheckBox.isSelected());
            }
        });

        errorSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                errorInt.setText(Integer.toString((int)errorSlider.getValue()));
            }
        });
    }

    @FXML
    private void saveTask(ActionEvent event) {
        if (taskName.getText().equals("")) return;
        task.setTaskName(taskName.getText());
        if (filterCheckBox.isSelected())
            task.setTaskFilter(true);
        else
            task.setTaskFilter(false);
        task.setFilterElement((String)filterChoiceBox.getValue());
        task.setFilterElementName(filterElementName.getText());
        task.setTaskRefTime((int)refreshTime.getValue());
        task.setError((int)errorSlider.getValue());

        iTaskAction.edit(task);
        mainController.refreshList();
        mainController.getDialog().hide();
    }

    @FXML
    private void cancelTask() {
        mainController.getDialog().hide();
    }
}
