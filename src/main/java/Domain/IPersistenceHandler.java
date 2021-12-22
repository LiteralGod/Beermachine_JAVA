package Domain;

import java.util.List;

public interface IPersistenceHandler {
    List<BeerType> queryAllBeerTypes();
    List<Batch> queryAllBatches();
    Batch insertBatch(int currentBatchID, String productName, int prodSpeed, int totalAmount, int totalGood, int totalBad);

    void insertTemperature(int batchID, float value);

    void insertHumidity(int batchID, float value);

    void deleteBatch(int batchID);

    BeerType getBeerType(int id);

    List<Temperature> getTemperatures(int id);

    List<Humidity> getHumidity(int id);

    List<DefaultProduct> queryAllDefaultProducts();

    Integer queryHighestBatchID();
}