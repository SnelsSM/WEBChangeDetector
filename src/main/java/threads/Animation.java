package threads;

import controllers.MainController;

/**
 * Created by snels on 15.12.2016.
 */
public class Animation extends Thread {
    private int show;
    private MainController mainController;
    private int maxHeight = 210;
    private int middleHeight = 44;
    private int usingHeight;

    public Animation(MainController mainController, int show) {
        this.mainController = mainController;
        this.show = show;
        this.start();
    }


    public void run() {
        usingHeight = (int)(mainController.getHeightAnchorPane());

        switch (show) {
            case 0:
                for (int i = usingHeight; i >= 0; i -= 2) {
                    mainController.setAnchorHeight(i);
                    pause();
                }
                break;

            case 1:
                for (int i = usingHeight; i >= middleHeight; i -= 2) {
                    mainController.setAnchorHeight(i);
                    pause();
                }
                break;

            case 2:
                for (int i = usingHeight; i <= middleHeight; i += 2) {
                    mainController.setAnchorHeight(i);
                    pause();
                }
                break;

            case 3:
                for (int i = usingHeight; i <= maxHeight; i += 2) {
                    mainController.setAnchorHeight(i);
                    pause();
                }
                break;

        }

    }

    private void pause() {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
