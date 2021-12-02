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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


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
    private Text totalGood, totalBad, totalProduced, currentStatus, temperature, humidity, vibration, barley, hops, malt, wheat, yeast;

    @FXML
    private TextField beerSpeed, beerAmount;

    @FXML
    private ObservableList<BeerType> beerTypeObservableList;
    @FXML
    private ObservableList<DefaultProduct> defaultProductObservableList;

    private Thread t1, t2;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        beerTypeObservableList = FXCollections.observableArrayList(domainHandler.ListOfBeerTypes());
        beerType.setItems(beerTypeObservableList);
        beerType.setValue(beerTypeObservableList.get(0));
        defaultProductObservableList = FXCollections.observableArrayList(domainHandler.listOfDefaultProducts());
        beerChoice.setItems(defaultProductObservableList);
        beerChoice.setValue(defaultProductObservableList.get(0));
        infoRunnable infoRunnable1 = new infoRunnable(5000,totalProduced);
        t1 = new Thread(infoRunnable1);
        t1.start();
        infoRunnable infoRunnable2 = new infoRunnable(5000, currentStatus);
        t2 = new Thread(infoRunnable2);
        t2.start();
    }
}
