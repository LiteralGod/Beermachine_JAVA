package Domain;

public class Humidity {

    int batchID;
    int value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int getBatchID() {
        return batchID;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Humidity(int batchID, int value) {
        this.batchID = batchID;
        this.value = value;
    }
}
