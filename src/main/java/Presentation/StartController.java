package Presentation;

import Domain.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StartController implements Initializable {
    @FXML
    private Button startBtn, abortBtn, stopBtn, resetBtn, testBtn, saveBtn, stateChartBtn;
    @FXML
    private Label speedError, statusError;

    @FXML
    private ChoiceBox<DefaultProduct> beerChoice;
    @FXML
    private ChoiceBox<BeerType> beerType;

    @FXML
    private Text totalGood, totalDefect, totalProduced, currentStatus, temperature, humidity, vibration, barley, hops, malt, wheat, yeast, maintenance,
            batch_batchID, batch_productProduced, batch_speed, batch_totalAmount, batch_totalGood, batch_totalBad, currentBatchId;

    @FXML
    private TextField beerSpeed, beerAmount;

    @FXML
    private ListView<Batch> batchListView;

    @FXML
    private ListView<Temperature> temperatureListView;
    @FXML
    private ListView<Humidity> humidityListView;



    @FXML
    private ObservableList<BeerType> beerTypeObservableList;
    @FXML
    private ObservableList<DefaultProduct> defaultProductObservableList;
    @FXML
    private ObservableList<Batch> listOfBatchesObservableList;
    @FXML
    private ObservableList<Temperature> listOfTemperatureObservableList;
    @FXML
    private ObservableList<Humidity> listOfHumidityObservableList;

    IDomainHandler domainHandler = new DomainHandler();


    @FXML
    public void setScene(ActionEvent event) throws IOException {


    }

    public void showStateChart() {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("stateChart.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("State Chart");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void StartMachine() {
        if (beerSpeed.getText().isEmpty()) {
            speedError.setVisible(true);

        } else if (!currentStatus.getText().equals(String.valueOf(4))) {
            statusError.setVisible(true);
            speedError.setVisible(false);
        } else {
            speedError.setVisible(false);
            statusError.setVisible(false);
            float beerTypeID = 0;
            switch (beerType.getValue().toString()) {
                case "Pilsner" -> {
                    beerTypeID = 0;
                }
                case "Wheat" -> {
                    beerTypeID = 1;
                }
                case "IPA" -> {
                    beerTypeID = 2;
                }
                case "Stout" -> {
                    beerTypeID = 3;
                }
                case "Ale" -> {
                    beerTypeID = 4;
                }
                case "Alcohol Free" -> {
                    beerTypeID = 5;
                }

            }
            System.out.println(domainHandler.highestBatchId());
            domainHandler.StartMachine(beerTypeID, Float.parseFloat(beerSpeed.getText()), Float.parseFloat(beerAmount.getText()), domainHandler.highestBatchId() + 1);
            currentBatchId.setText(String.valueOf(domainHandler.highestBatchId() + 1));
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(this.handleTemperatureBatchData(5000));
            executor.execute(this.handleHumidityBatchData(5000));

        }
    }


    @FXML
    public void StopMachine() {
        domainHandler.StopMachine();
    }

    @FXML
    public void ResetMachine() {
        domainHandler.ResetMachine();
    }

    @FXML
    public void setDefaultProduct(ActionEvent event) {
        if (beerChoice.getSelectionModel().getSelectedItem() != null) {
            beerType.setValue(beerChoice.getValue().getBeerType());
            beerSpeed.setText(String.valueOf(beerChoice.getValue().getDefaultSpeed()));
            beerAmount.setText(String.valueOf(beerChoice.getValue().getDefaultAmount()));
        }
    }

    @FXML
    public Thread handleGoodAmount(int sleepTime) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                while (running) {
                    Platform.runLater(new Runnable() {
                                          @Override
                                          public void run() {
                                              totalGood.setText(String.valueOf(Integer.parseInt(totalProduced.getText()) - Integer.parseInt(totalDefect.getText())));
                                          }
                                      }

                    );
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
        });
    }

    @FXML
    public Thread handleTemperatureBatchData(int sleepTime) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                System.out.println(running);
                while (running) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            domainHandler.insertTemperature(Integer.parseInt(currentBatchId.getText()), Float.parseFloat(temperature.getText()));
                        }
                    });
                    synchronized (this) {
                        try {
                            Thread.sleep(sleepTime);
                            if (!currentStatus.getText().equals("6")) {
                                running = false;
                                System.out.println(running);
                                saveBatch();
                            }
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread() + " Has been stopped");
                            running = false;
                        }
                    }
                }
            }
        });
    }

    @FXML
    public Thread handleHumidityBatchData(int sleepTime) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                System.out.println(running);
                while (running) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            domainHandler.insertHumidity(Integer.parseInt(currentBatchId.getText()), Float.parseFloat(humidity.getText()));
                        }
                    });

                    synchronized (this) {
                        try {
                            Thread.sleep(sleepTime);
                            if (!currentStatus.getText().equals("6")) {
                                running = false;
                                System.out.println(running);
                                System.out.println(totalProduced.getText());
                            }
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread() + " Has been stopped");
                            running = false;
                        }
                    }
                }
            }
        });
    }

    @FXML
    public void setBatchData(MouseEvent e) {
        if (batchListView.getSelectionModel().getSelectedItem() != null) {
            batch_batchID.setText(String.valueOf(batchListView.getSelectionModel().getSelectedItem().getCurrentBatchID()));
            batch_productProduced.setText(batchListView.getSelectionModel().getSelectedItem().getBatchName());
            batch_speed.setText(String.valueOf(batchListView.getSelectionModel().getSelectedItem().getProdSpeed()));
            batch_totalAmount.setText(String.valueOf(batchListView.getSelectionModel().getSelectedItem().getTotalAmount()));
            batch_totalGood.setText(String.valueOf(batchListView.getSelectionModel().getSelectedItem().getTotalGood()));
            batch_totalBad.setText(String.valueOf(batchListView.getSelectionModel().getSelectedItem().getTotalBad()));
            updateHumidityListView();
            updateTempListView();
        }
    }

    public void saveBatch() {
        domainHandler.insertBatch(
                domainHandler.highestBatchId() + 1,
                beerType.getValue().getType(),
                Integer.parseInt(beerSpeed.getText()),
                Integer.parseInt(totalProduced.getText()),
                Integer.parseInt(totalGood.getText()),
                Integer.parseInt(totalDefect.getText()));
    }

    public void updateListView() {
        listOfBatchesObservableList = FXCollections.observableArrayList(domainHandler.listOfBatches());
        batchListView.setItems(listOfBatchesObservableList);
    }

    public void updateTempListView() {
        listOfTemperatureObservableList = FXCollections.observableArrayList(domainHandler.selectTemperature(batchListView.getSelectionModel().getSelectedItem().getCurrentBatchID()));
        temperatureListView.setItems(listOfTemperatureObservableList);
    }
    public void updateHumidityListView() {
        listOfHumidityObservableList = FXCollections.observableArrayList(domainHandler.selectHumidity(batchListView.getSelectionModel().getSelectedItem().getCurrentBatchID()));
        humidityListView.setItems(listOfHumidityObservableList);
    }

    public void handleThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(13);
        executor.execute(domainHandler.handleRunnable(200, currentStatus));
        executor.execute(domainHandler.handleRunnable(200, totalProduced));
        executor.execute(domainHandler.handleRunnable(200, totalDefect));
        executor.execute(this.handleGoodAmount(200));
        executor.execute(domainHandler.handleRunnable(200, barley));
        executor.execute(domainHandler.handleRunnable(200, hops));
        executor.execute(domainHandler.handleRunnable(200, malt));
        executor.execute(domainHandler.handleRunnable(200, wheat));
        executor.execute(domainHandler.handleRunnable(200, yeast));
        executor.execute(domainHandler.handleRunnable(200, humidity));
        executor.execute(domainHandler.handleRunnable(200, temperature));
        executor.execute(domainHandler.handleRunnable(200, vibration));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        beerTypeObservableList = FXCollections.observableArrayList(domainHandler.ListOfBeerTypes());
        beerType.setItems(beerTypeObservableList);
        beerType.setValue(beerTypeObservableList.get(0));
        defaultProductObservableList = FXCollections.observableArrayList(domainHandler.listOfDefaultProducts());
        beerChoice.setItems(defaultProductObservableList);
        beerChoice.setValue(defaultProductObservableList.get(0));
        this.handleThreads();
        updateListView();
    }
}
