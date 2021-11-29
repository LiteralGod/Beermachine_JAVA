package Domain;

public class BeerType {
    String type;

    @Override
    public String toString() {
        return type;
    }

    public BeerType(String type) {
        this.type = type;
    }
}
