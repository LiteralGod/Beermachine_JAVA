package Domain;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class infoRunnable implements Runnable {
    private int i; //Index of current picture
    private long sleepTime;
    private boolean running = true;
    private Text tf;
    Read readNode = new Read();
    Subscription subscripeToNode = new Subscription();


    public infoRunnable(long sleepTime, Text tf) {
        this.i = i;
        this.sleepTime = sleepTime;
        this.tf = tf;
    }

    public void handleCount(){}
    @Override
    public void run() {
        while (running) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    tf.setText(String.valueOf(subscripeToNode.Subscribe()));
                }
            });
            synchronized (this) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread() + " Has been stopped");
                    running = false;
                }

            }
        }
    }

}
