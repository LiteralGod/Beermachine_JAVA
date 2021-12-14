package Domain;

import Persistence.PersistenceHandler;
import Presentation.IDomainHandler;
import javafx.scene.text.Text;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DomainHandler implements IDomainHandler {
    private static DomainHandler instance;
    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();
    Write writeToNode = new Write();
    Read readFromNode = new Read();
    Subscription subscripeToNode = new Subscription();

    public static DomainHandler getInstance() {
        if (instance == null) {
            instance = new DomainHandler();
        }
        return instance;
    }

    @Override
    public void StartMachine(float beerType, float beerSpeed, float setAmount) {
        writeToNode.StartMachine(beerType, beerSpeed, setAmount);
    }

    @Override
    public void StopMachine() {
        writeToNode.StopMachine();
    }

    @Override
    public void ResetMachine() {
        writeToNode.ResetMachine();
    }

    @Override
    public List<BeerType> ListOfBeerTypes() {
        return persistenceHandler.queryAllBeerTypes();
    }

    @Override
    public List<DefaultProduct> listOfDefaultProducts() {
        return persistenceHandler.queryAllDefaultProducts();
    }

    @Override
    public float readValue(String someString) {
        return readFromNode.readNode(someString);
    }

    @Override
    public InfoRunnable handleRunnable(int sleepTime, Text tf) {
        return new InfoRunnable(sleepTime, tf);
    }

    @Override
    public List<Batch> listOfBatches(){
        return persistenceHandler.queryAllBatches();
    }
}
