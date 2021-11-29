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
    class Write {
        public static void main(String[] args) {
            try
            {
                List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://127.0.0.1:4840").get();

                OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
                cfg.setEndpoint(endpoints.get(0));

                OpcUaClient client = OpcUaClient.create(cfg.build());
                client.connect().get();

                NodeId nodeId1 = NodeId.parse("ns=6;s=::Program:Cube.Command.CmdChangeRequest");
                client.writeValue(nodeId1, DataValue.valueOnly(new Variant(true))).get();

            }
            catch(Throwable ex)
            {
                ex.printStackTrace();
            }

        }
    }
}
