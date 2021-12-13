package Presentation;

import Domain.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StartController implements Initializable {
    @FXML
    private Button startBtn, abortBtn, stopBtn, resetBtn, testBtn;
    @FXML
    private Label speedError;

    @FXML
    private ChoiceBox<DefaultProduct> beerChoice;
    @FXML
    private ChoiceBox<BeerType> beerType;

    @FXML
    private Text totalGood, totalDefect, totalProduced, currentStatus, temperature, humidity, vibration, barley, hops, malt, wheat, yeast;

    @FXML
    private TextField beerSpeed, beerAmount;

    @FXML
    private ObservableList<BeerType> beerTypeObservableList;
    @FXML
    private ObservableList<DefaultProduct> defaultProductObservableList;

    IDomainHandler domainHandler = new DomainHandler();


    @FXML
    public void setScene(ActionEvent event) throws IOException {


    }

    @FXML
    public void StartMachine() {
        if (beerSpeed.getText().isEmpty()) {
            speedError.setVisible(true);
        } else {
            speedError.setVisible(false);
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
            domainHandler.StartMachine(beerTypeID, Float.parseFloat(beerSpeed.getText()), Float.parseFloat(beerAmount.getText()));
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
    public void totalCount() {
        // subscription.Subscribe();
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

    public void handleThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(12);
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

    }
}
