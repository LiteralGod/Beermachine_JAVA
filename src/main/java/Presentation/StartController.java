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
import java.util.ArrayList;
import java.util.ResourceBundle;


public class StartController implements Initializable {
    @FXML
    private Button startBtn, abortBtn, stopBtn, resetBtn, testBtn;
    @FXML
    private Label speedError;

    @FXML
    private ChoiceBox<BeerType> beerChoice;
    @FXML
    private ChoiceBox<BeerType> beerType;

    @FXML
    private Text totalGood, totalBad, temperature, humidity, vibration, barley, hops, malt, wheat, yeast;

    @FXML
    private TextField beerSpeed, beerAmount;

    @FXML
    private ObservableList<BeerType> tempObservableList;

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
            domainHandler.StartMachine(beerTypeID, Float.parseFloat(beerSpeed.getText()));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tempObservableList = FXCollections.observableArrayList(domainHandler.ListOfBeerTypes());
        beerType.setItems(tempObservableList);
        beerType.setValue(tempObservableList.get(0));
    }
}
