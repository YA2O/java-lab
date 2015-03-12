package bzh.ya2o.java.designwithlambdas;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static bzh.ya2o.java.designwithlambdas.Asset.AssetType.BOND;
import static bzh.ya2o.java.designwithlambdas.Asset.AssetType.STOCK;

public class AssetTest {
    final List<Asset> assets = Collections.unmodifiableList(Arrays.asList(
            new Asset(BOND, 1000),
            new Asset(BOND, 2000),
            new Asset(STOCK, 3000),
            new Asset(STOCK, 4000)
    ));

    @Test
    public void total() {
        System.out.println(AssetUtil.totalAssetValues(assets));
    }

    @Test
    public void totalBonds() {
        System.out.println(AssetUtil.totalAssetValues(assets, asset -> asset.getType() == BOND));
    }
}
