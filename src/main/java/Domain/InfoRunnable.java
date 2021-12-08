package Domain;

import javafx.application.Platform;
import javafx.scene.text.Text;

public class InfoRunnable implements Runnable {
    private long sleepTime;
    private boolean running = true;
    private Text tf;
    Subscription subscripeToNode = new Subscription();
    int nodeValue = 0;

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }


    public int getNodeValue() {
        return nodeValue;
    }

    public InfoRunnable(long sleepTime, Text tf) {
        this.sleepTime = sleepTime;
        this.tf = tf;
    }

    @Override
    public void run() {
        switch (tf.getId()) {
            case "totalProduced" -> {
                subscripeToNode.totalProduced();

            }
            case "totalDefect" -> {
                subscripeToNode.totalDefect();
            }
            case "currentStatus" -> {
                subscripeToNode.currentStatus();
            }
            case "barley" -> {
                subscripeToNode.barley();
            }
            default -> setNodeValue(0);
        }
        while (running) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    switch (tf.getId()) {
                        case "totalProduced", "totalDefect", "currentStatus" -> {
                            tf.setText(String.valueOf(subscripeToNode.getIntNodeValue()));
                        }
                        case "barley" -> {
                            tf.setText(String.format("%.1f", subscripeToNode.getFloatNodeValue()) + " %");
                        }
                        default -> setNodeValue(0);
                    }
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
