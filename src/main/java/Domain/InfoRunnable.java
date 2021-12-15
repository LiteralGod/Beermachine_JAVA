package Domain;

import javafx.application.Platform;
import javafx.scene.text.Text;

public class InfoRunnable implements Runnable {
    private long sleepTime;
    private boolean running = true;
    private Text tf;
    Machine subscribeToNode = new Machine();
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
            case "totalProduced" -> subscribeToNode.totalProduced();
            case "totalDefect" -> subscribeToNode.totalDefect();
            case "currentStatus" -> subscribeToNode.currentStatus();
            
            case "barley" -> subscribeToNode.barley();
            case "hops" -> subscribeToNode.hops();
            case "malt" -> subscribeToNode.malt();
            case "wheat" -> subscribeToNode.wheat();
            case "yeast" -> subscribeToNode.yeast();

            case "humidity" -> subscribeToNode.humidity();
            case "temperature" -> subscribeToNode.temperature();
            case "vibration" -> subscribeToNode.vibration();
            case "maintenance" -> subscribeToNode.maintenance();
            default -> setNodeValue(0);

        }
        while (running) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    switch (tf.getId()) {
                        case "totalProduced", "totalDefect", "currentStatus", "totalGood" -> {
                            tf.setText(String.valueOf(subscribeToNode.getIntNodeValue()));
                        }
                        case "barley", "hops", "malt", "wheat", "yeast" -> {
                            float newValue = ((subscribeToNode.getFloatNodeValue()/35000)*100);
                            tf.setText(String.format("%.1f", newValue) + " %");
                        }
                        case "humidity", "temperature", "vibration" -> {
                            tf.setText(String.valueOf(subscribeToNode.getFloatNodeValue()));
                        }
                        case "maintenance" ->{
                            tf.setText(String.valueOf(subscribeToNode.getShortNodeValue()));
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
