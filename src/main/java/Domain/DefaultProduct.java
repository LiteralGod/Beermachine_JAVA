package Domain;

public class DefaultProduct {
    BeerType beerType;
    int defaultSpeed;
    int defaultAmount;

    @Override
    public String toString() {
        return beerType.getType();
    }

    public DefaultProduct(BeerType beerType, int defaultSpeed, int defaultAmount) {
        this.beerType = beerType;
        this.defaultSpeed = defaultSpeed;
        this.defaultAmount = defaultAmount;
    }

    public BeerType getBeerType() {
        return beerType;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }
}
