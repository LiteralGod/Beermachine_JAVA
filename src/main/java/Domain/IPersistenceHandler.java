package Domain;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;

public interface IPersistenceHandler {
    List<BeerType> queryAllBeerTypes();
    List<Batch> queryAllBatches();
    void insertBatch(int currentBatchID, String productName, int totalAmount, int totalGood, int totalBad);
    void deleteBatch(int batchID);

    BeerType getBeerType(int id);

    List<DefaultProduct> queryAllDefaultProducts();
}