package Presentation;

import Domain.Batch;
import Domain.BeerType;
import Domain.DefaultProduct;
import Domain.InfoRunnable;
import javafx.scene.text.Text;

import java.util.List;

public interface IDomainHandler {

   void StartMachine(float beerType, float beerSpeed, float setAmount);

   void StopMachine();

   void ResetMachine();

   List<BeerType> ListOfBeerTypes();

   List<DefaultProduct> listOfDefaultProducts();

   float readValue(String someString);

   InfoRunnable handleRunnable(int sleepTime, Text tf);

   List<Batch> listOfBatches();
}
