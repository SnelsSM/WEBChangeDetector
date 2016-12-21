package threads;

import controllers.MainController;
import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import objects.Task;

import java.io.IOException;

/**
 * Created by snels on 15.12.2016.
 */
public class TaskStart extends Thread {
    private Task task;
    private int oldDocumentInt;
    private String oldDocumentString;
    private MainController mainController;

    public TaskStart(Task task, MainController mainController) {
        this.task = task;
        this.mainController = mainController;
        task.setStarted(true);
        this.oldDocumentInt = 0;
        this.oldDocumentString = "";
        this.setDaemon(true);
        this.start();
    }

    public void run() {
        while (task.isStarted()) {

            Document doc = getDocument();
            String filterElement = task.getFilterElement();

            if (task.isTaskFilter()) {
                if (filterElement.equals("Class")) {
                    Elements byClass = doc.getElementsByClass(task.getFilterElementName());
                    if (!byClass.toString().equals(oldDocumentString) && !oldDocumentString.equals(""))
                        isChanged();
                    oldDocumentString = byClass.toString();
                }
                if (filterElement.equals("Id")) {
                    Element byId = doc.getElementById(task.getFilterElementName());
                    if (byId == null) return;
                    if (!byId.toString().equals(oldDocumentString) && !oldDocumentString.equals(""))
                        isChanged();
                    oldDocumentString = byId.toString();
                }
            } else {
                Element byBody = doc.body();
                int i = byBody.toString().length();
                if (Math.abs(i - oldDocumentInt) > task.getError() && oldDocumentInt != 0)
                    isChanged();
                oldDocumentInt = i;
            }

            pause();
        }
    }

    private Document getDocument() {
        Document doc = null;
        try {
            doc = Jsoup.connect(task.getTaskURL()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private void isChanged() {
        task.setChanged(true);
        Platform.runLater(new Runnable() {
            public void run() {
                mainController.refreshList();
                Notifications.create()
                        .title("Обнаружено изменение!")
                        .text("Изменение в \"" + task.getTaskName() + "\"")
                        .showInformation();
            }
        });
    }

    private void pause() {
        try {
            Thread.sleep(task.getTaskRefTime() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
