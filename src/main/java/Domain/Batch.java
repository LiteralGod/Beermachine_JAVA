package Domain;

import Persistence.PersistenceHandler;

public class Batch {
    int currentBatchID;
    String batchName;
    int prodSpeed;
    int totalAmount;
    int totalGood;
    int totalBad;

    @Override
    public String toString() {
        return "Id: " + currentBatchID + " Product: " + batchName;
    }

    public Batch(int currentBatchID, String batchName, int prodSpeed, int totalAmount, int totalGood, int totalBad) {
        this.currentBatchID = currentBatchID;
        this.batchName = batchName;
        this.prodSpeed = prodSpeed;
        this.totalAmount = totalAmount;
        this.totalGood = totalGood;
        this.totalBad = totalBad;
    }

    public int getCurrentBatchID() {
        return currentBatchID;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getProdSpeed() {
        return prodSpeed;
    }

    public int getTotalGood() {
        return totalGood;
    }

    public int getTotalBad() {
        return totalBad;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Batch showBatch (int batchID) {
        return PersistenceHandler.getInstance().queryAllBatches().get(batchID);
    }

    public void showAllBatches() {
        PersistenceHandler.getInstance().queryAllBatches();
    }

    public void deleteBatch(int batchID) {
        PersistenceHandler.getInstance().deleteBatch(batchID);
    }
}
