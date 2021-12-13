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
            case "totalGood" ->{
                subscripeToNode.totalGood();
            }

            case "hops" -> subscripeToNode.hops();
            case "malt" -> subscripeToNode.malt();
            case "wheat" ->subscripeToNode.wheat();
            case "yeast" ->subscripeToNode.yeast();
            case "humidity" -> subscripeToNode.humidity();
            case "temperature" -> subscripeToNode.temperature();
            case "vibration" -> subscripeToNode.vibration();
            default -> setNodeValue(0);

        }
        while (running) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    switch (tf.getId()) {
                        case "totalProduced", "totalDefect", "currentStatus", "totalGood" -> {
                            tf.setText(String.valueOf(subscripeToNode.getIntNodeValue()));
                        }
                        case "barley", "hops", "malt", "wheat", "yeast" -> {
                            float newValue = ((subscripeToNode.getFloatNodeValue()/35000)*100);
                            tf.setText(String.format("%.1f", newValue) + " %");
                        }
                        case "humidity", "temperature", "vibration" -> {
                            tf.setText(String.valueOf(subscripeToNode.getFloatNodeValue()));
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
