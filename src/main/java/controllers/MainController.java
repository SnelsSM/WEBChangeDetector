package controllers;

import interfaces.ITaskAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.MaskerPane;
import objects.Task;
import interfaces.impls.TaskAction;
import threads.Animation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by snels on 15.12.2016.
 */
public class MainController implements Initializable {

    @FXML private AnchorPane rootAnchorPane;
    @FXML private ListView listView;
    @FXML private WebView webView;
    @FXML private TextField urlTextField;
    @FXML private Button urlButton;
    @FXML private AnchorPane addTaskAnchorPane;
    @FXML private HBox filterHBox;
    @FXML private VBox defaultVBox;
    @FXML private Label errorLabel;
    @FXML private Slider errorSlider;
    @FXML private TextField taskName;
    @FXML private CheckBox filterCheckBox;
    @FXML private ChoiceBox filterChoiceBox;
    @FXML private TextField filterTextField;
    @FXML private ChoiceBox timeChoiceBox;
    @FXML private MaskerPane pleaseWait;

    private Stage dialog;
    private WebEngine webEngine;
    private MainController mainController = this;
    private ITaskAction taskAction = new TaskAction(mainController);
    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private EditController editController = new EditController(taskAction, mainController);
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlEdit;


    public void initialize(URL location, ResourceBundle resources) {

        timeChoiceBox.setValue(60);
        addTaskAnchorPane.setPrefHeight(0);
        webEngine = webView.getEngine();
        pleaseWait.setVisible(false);

        listView.setItems(taskList);
        listView.setCellFactory(listCellController -> new ListCellController(taskAction, mainController));



        try {
            fxmlLoader.setLocation(getClass().getResource("/EditWindow.fxml"));
            fxmlLoader.setController(editController);
            fxmlEdit = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        filterHBox.setDisable(true);

        filterCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (filterCheckBox.isSelected()) {
                    filterHBox.setDisable(false);
                    defaultVBox.setDisable(true);
                } else {
                    filterHBox.setDisable(true);
                    defaultVBox.setDisable(false);
                }
            }
        });

        errorLabel.setText("0");
        errorSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                errorLabel.setText(Integer.toString((int)errorSlider.getValue()));
            }
        });

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                urlTextField.setText(webEngine.getLocation());
                if (newValue != Worker.State.SUCCEEDED) { return; }
                // Страница полностью загрузилась
                pleaseWait.setVisible(false);
                new Animation(mainController, 2);
            }
        });

        filterChoiceBox.setItems(FXCollections.observableArrayList("Class", "Id"));
        timeChoiceBox.setItems(FXCollections.observableArrayList(15, 30, 45, 60));
        filterChoiceBox.setValue("Class");
        timeChoiceBox.setValue(60);

    }

    // FXML voids

    @FXML
    private void openURL() {

        String url = urlTextField.getText();
        if (url.equals(""))
            return;
        if (!url.contains("http"))
            url = "http://" + url;

        if (getHeightAnchorPane() != 0)
            new Animation(mainController, 0);

        openURL(url);
    }

    @FXML
    private void moreAddTask() {
        new Animation(mainController, 3);
    }

    @FXML
    private void lessAddTask() {
        new Animation(mainController, 1);
    }

    @FXML
    private void addTask() {
        String task = taskName.getText();
        String url = webEngine.getLocation();
        int time = (Integer)timeChoiceBox.getSelectionModel().getSelectedItem();
        String element = (String)filterChoiceBox.getSelectionModel().getSelectedItem();
        String elName = filterTextField.getText();
        int error = (int) errorSlider.getValue();

        if (task.length() != 0) {
            if (filterCheckBox.isSelected())
                taskAction.add(new Task(task, url, time, true, element, elName, error));
            else
                taskAction.add(new Task(task, url, time, false, error));
            new Animation(mainController, 0);
            formDefault();
        }
    }

    // methods

    public void setAnchorHeight(int i) {
        addTaskAnchorPane.setPrefHeight(i);

    }

    public void addToTaskList(Task task) {
        taskList.add(task);
    }

    public double getHeightAnchorPane() {
        return addTaskAnchorPane.getPrefHeight();
    }

    public void formDefault() {
        taskName.clear();
        filterCheckBox.setSelected(false);
        filterChoiceBox.setValue("Class");
        filterTextField.clear();
        timeChoiceBox.setValue(60);
    }

    public void openURL(String url) {
        pleaseWait.setVisible(true);
        webEngine.load(url);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    public AnchorPane getStage() {
        return rootAnchorPane;
    }

    public void refreshList() {
        listView.setItems(null);
        listView.setItems(taskList);
    }

    public void addModalityWindow() {
        if (dialog == null) {
            dialog = new Stage();
            dialog.getIcons().add(new Image(getClass().getResourceAsStream("/WCD.png")));
            dialog.setTitle("Редактирование задания");
            dialog.setResizable(false);
            dialog.setScene(new Scene(fxmlEdit));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(rootAnchorPane.getScene().getWindow());
        }
            dialog.show();
    }

    public EditController getEditController() {
        return editController;
    }

    public Stage getDialog() {
        return dialog;
    }

}
