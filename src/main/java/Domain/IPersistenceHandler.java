package Domain;

import java.util.List;

public interface IPersistenceHandler {
    List<BeerType> queryAllBeerTypes();
    List<Batch> queryAllBatches();
    Batch insertBatch(int currentBatchID, String productName, int prodSpeed, int totalAmount, int totalGood, int totalBad);
    void deleteBatch(int batchID);

    BeerType getBeerType(int id);

    List<DefaultProduct> queryAllDefaultProducts();
}