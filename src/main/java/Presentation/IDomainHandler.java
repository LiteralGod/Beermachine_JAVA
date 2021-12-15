package Presentation;

import Domain.*;
import javafx.scene.text.Text;

import java.util.List;

public interface IDomainHandler {

   void StartMachine(float beerType, float beerSpeed, float setAmount, float batchID);

   void StopMachine();

   void ResetMachine();

   List<BeerType> ListOfBeerTypes();

   List<DefaultProduct> listOfDefaultProducts();

   float readBatchId();

   InfoRunnable handleRunnable(int sleepTime, Text tf);

   List<Batch> listOfBatches();

   Batch insertBatch(int batchID, String beerType, int speed, int totalAmount, int totalGood, int totalBad);

   int highestBatchId();

   void insertTemperature(int batchID, float value);

   void insertHumidity(int batchID, float value);

    List<Humidity> selectHumidity(int batchID);

   List<Temperature> selectTemperature(int batchID);
}
