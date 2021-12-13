package Domain;

public class Batch {
    int nextBatchID;
    int currentBatchID;
    String batchName;
    int[] batchOutput;

    public Batch(int nextBatchID, int currentBatchID, String batchName, int[] batchOutput) {
        this.nextBatchID = nextBatchID;
        this.currentBatchID = currentBatchID;
        this.batchName = batchName;
        this.batchOutput = batchOutput;
    }

    public int getCurrentBatchID() {
        return currentBatchID;
    }

    public int[] getBatchOutput() {
        return batchOutput;
    }

    public void setNextBatchID(int nextBatchID) {
        this.nextBatchID = nextBatchID;
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
