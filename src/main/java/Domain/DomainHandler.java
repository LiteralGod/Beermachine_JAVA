package Domain;

import Persistence.PersistenceHandler;
import Presentation.IDomainHandler;

import java.util.List;

public class DomainHandler implements IDomainHandler {
    private static DomainHandler instance;
    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();
    Write writeToNode = new Write();
    Read readFromNode = new Read();
    Subscription subscripeToNode = new Subscription();
    
    public static DomainHandler getInstance(){
        if (instance == null){
            instance = new DomainHandler();
        }
        return instance;
    }

    @Override
    public void StartMachine(float beerType, float beerSpeed) {
        writeToNode.StartMachine(beerType, beerSpeed);
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


}
