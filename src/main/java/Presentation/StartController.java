package Presentation;

import Domain.*;
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
    public void setDefaultProduct(ActionEvent event){
        if(beerChoice.getSelectionModel().getSelectedItem() != null){
            beerType.setValue(beerChoice.getValue().getBeerType());
            beerSpeed.setText(String.valueOf(beerChoice.getValue().getDefaultSpeed()));
            beerAmount.setText(String.valueOf(beerChoice.getValue().getDefaultAmount()));
        }
    }

    public void handleThreads(){
        ExecutorService executor = Executors.newFixedThreadPool(11);
        InfoRunnable ir1 = new InfoRunnable(200,totalProduced);
        InfoRunnable ir2 = new InfoRunnable(200, currentStatus);
        InfoRunnable ir3 = new InfoRunnable(200, totalDefect);
        InfoRunnable ir4 = new InfoRunnable(200,barley);
        InfoRunnable ir5 = new InfoRunnable(200,hops);
        InfoRunnable ir6 = new InfoRunnable(200,malt);
        InfoRunnable ir7 = new InfoRunnable(200,wheat);
        InfoRunnable ir8 = new InfoRunnable(200,yeast);
        InfoRunnable ir9 = new InfoRunnable(200,humidity);
        InfoRunnable ir10 = new InfoRunnable(200, temperature);
        InfoRunnable ir11 = new InfoRunnable(200, vibration);
        executor.execute(ir1);
        executor.execute(ir2);
        executor.execute(ir3);
        executor.execute(ir4);
        executor.execute(ir5);
        executor.execute(ir6);
        executor.execute(ir7);
        executor.execute(ir8);
        executor.execute(ir9);
        executor.execute(ir10);
        executor.execute(ir11);

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
