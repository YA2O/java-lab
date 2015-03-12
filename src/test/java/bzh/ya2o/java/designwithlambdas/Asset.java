package bzh.ya2o.java.designwithlambdas;

public class Asset {

    enum AssetType {
        BOND,
        STOCK;
    }

    private final AssetType type;
    private final int value;

    public Asset(final AssetType type, final int value) {
        this.type = type;
        this.value = value;
    }

    public AssetType getType() {
        return this.type;
    }

    public int getValue() {
        return this.value;
    }
}
