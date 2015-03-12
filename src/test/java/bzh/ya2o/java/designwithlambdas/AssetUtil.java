package bzh.ya2o.java.designwithlambdas;

import java.util.List;
import java.util.function.Predicate;

public class AssetUtil {
    static int totalAssetValues(final List<Asset> assets) {
        return totalAssetValues(assets, asset -> true);
    }

    static int totalAssetValues(final List<Asset> assets, Predicate<Asset> assetSelector) {
        return assets.stream()
                .filter(assetSelector)
                .mapToInt(Asset::getValue)
                .sum();
    }

}
