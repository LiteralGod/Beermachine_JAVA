package Domain;

public class Batch {
    int currentBatchID;
    String batchName;
    int totalAmount;
    int totalGood;
    int totalBad;

    public Batch(int currentBatchID, String batchName, int totalAmount, int totalGood, int totalBad) {
        this.currentBatchID = currentBatchID;
        this.batchName = batchName;
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

    public int getTotalGood() {
        return totalGood;
    }

    public int getTotalBad() {
        return totalBad;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public void showBatchID (int batchID) {

    }

    public void showAllBatches () {

    }

    public void deleteBatch (int batchID) {

    }
}
