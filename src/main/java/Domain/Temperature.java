package Domain;

public class Temperature {

    int batchID;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    int value;

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

    public Temperature(int batchID, int value) {
        this.batchID = batchID;
        this.value = value;
    }
}
