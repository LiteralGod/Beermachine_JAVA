package Presentation;

import Domain.Read;
import Domain.Subscription;
import Domain.Write;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class StartController implements Initializable {
    @FXML
    private Button startBtn, abortBtn, stopBtn, resetBtn;

    @FXML
    private ChoiceBox<String> beerChoice, beerType;

    @FXML
    private Text totalGood, totalBad, temperature, humidity, vibration, barley, hops, malt, wheat, yeast;

    @FXML
    private TextField beerSpeed, beerAmount;

    @FXML
    private ObservableList<String> tempObservableList;

    Read read = new Read();
    Write write = new Write();
    Subscription subscription = new Subscription();


    @FXML
    public void setScene(ActionEvent event) throws IOException {


    }
    @FXML
    public void StartMachine(){
        write.writeToNode(startBtn.getText());
    }

    @FXML
    public void StopMachine(){
        write.writeToNode(stopBtn.getText());
    }
    @FXML
    public void ResetMachine(){
        write.writeToNode(resetBtn.getText());
    }

    @FXML
    public void totalCount(){
       // subscription.Subscribe();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> tempArraylist = new ArrayList<>();
        tempArraylist.add("hej");
        tempArraylist.add("med");
        tempArraylist.add("dig");
        tempObservableList = FXCollections.observableArrayList(tempArraylist);
        beerChoice.setItems(tempObservableList);
        beerSpeed.setText("ns=6;s=::Program:Cube.Status.StateCurrent");


        System.out.println(read.readNode(beerSpeed.getText()).toString());

    }
}
