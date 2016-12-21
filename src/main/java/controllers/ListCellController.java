package controllers;

import interfaces.ITaskAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import objects.Task;

import java.io.IOException;

/**
 * Created by snels on 15.12.2016.
 */
public class ListCellController extends ListCell<Task> {
    private FXMLLoader loader;
    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");
    private Task task;
    private ITaskAction taskAction;
    private MainController mainController;

    @FXML private AnchorPane anchorPane;
    @FXML private Label changeLabel;
    @FXML private Button taskButton;
    @FXML private Button buttonPlayStop;
    @FXML private Button editButton;
    @FXML private Button delButton;

    public ListCellController(ITaskAction taskAction, MainController mainController) {
        this.taskAction = taskAction;
        this.mainController = mainController;
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        this.task = task;
        super.updateItem(task, empty);

        if(empty || task == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/ListCell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            taskButton.setText(task.getTaskName());
            if (task.isStarted())
                buttonPlayStop.setGraphic(fontAwesome.create("STOP"));
            else
                buttonPlayStop.setGraphic(fontAwesome.create("PLAY"));
            delButton.setGraphic(fontAwesome.create("CLOSE"));
            editButton.setGraphic(fontAwesome.create("GEAR"));

            if(task.isChanged())
                taskButton.setId("button_changed");
            else
                taskButton.setId("button_default");

            setText(null);
            setGraphic(anchorPane);
        }
    }

    @FXML
    private void openTask() {
        taskAction.open(task);
        task.setChanged(false);
        mainController.refreshList();
    }

    @FXML
    private void taskPlayStop() {
        taskAction.play(task);
    }

    @FXML
    private void taskEdit() {
        mainController.addModalityWindow();
        EditController editController = mainController.getEditController();
        editController.setTask(task);
    }

    @FXML
    private void taskDelete() {
        taskAction.remove(task);
    }

    public void setTaskAction(ITaskAction taskAction) {
        this.taskAction = taskAction;
    }
}
