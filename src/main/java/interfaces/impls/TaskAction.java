package interfaces.impls;

import controllers.EditController;
import controllers.MainController;
import interfaces.ITaskAction;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.Task;
import threads.TaskStart;

import java.util.Optional;

public class TaskAction implements ITaskAction{
    private MainController mainController;

    public TaskAction(MainController mainController) {
        this.mainController = mainController;
    }

    public void add(Task task) {
        start(task);
        task.setStarted(true);
        mainController.addToTaskList(task);
    }

    public void open(Task task) {
        mainController.openURL(task.getTaskURL());
    }

    public void remove(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/Dialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog");
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/WCD.png")));
        alert.setTitle("Удаление задания");
        alert.setHeaderText(null);
        alert.setContentText("Вы действительно хотите удалить задание \"" + task.getTaskName() + "\"?");

        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            task.setStarted(false);
            task.setNull();
            mainController.removeTask(task);
        }
    }

    public void play(Task task) {
        if (task.isStarted()) {
            task.setStarted(false);
        } else {
            start(task);
        }
        mainController.refreshList();
    }

    public void edit(Task task) {

    }

    public  void start(Task task) {
        new TaskStart(task, mainController);
    }
}