package Domain; /**
 * @author athil
 */

import java.util.List;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;

public class Write {
    public void StartMachine(float beerTypeID, float setSpeed, float setAmount) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://192.168.0.122:4840").get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), "192.168.0.122", 4840);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();

            //Set beerType
            NodeId nodeId2 = NodeId.parse("ns=6;s=::Program:Cube.Command.Parameter[1].Value");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(beerTypeID))).get();
            System.out.println(beerTypeID);

            //set Speed
            NodeId nodeId3 = NodeId.parse("ns=6;s=::Program:Cube.Command.MachSpeed");
            client.writeValue(nodeId3, DataValue.valueOnly(new Variant(setSpeed))).get();
            System.out.println(setSpeed);

            //set Amount if not 0
            if(setAmount != 0){
                NodeId nodeId4 = NodeId.parse("ns=6;s=::Program:Cube.Command.Parameter[2].Value");
                client.writeValue(nodeId4, DataValue.valueOnly(new Variant(setAmount))).get();

            }



            //Start request
            NodeId nodeId1 = NodeId.parse("ns=6;s=::Program:Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(2))).get();

            NodeId nodeId11 = NodeId.parse("ns=6;s=::Program:Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId11, DataValue.valueOnly(new Variant(true))).get();


        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    public void StopMachine() {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://192.168.0.122:4840").get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), "192.168.0.122", 4840);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();


            NodeId nodeId1 = NodeId.parse("ns=6;s=::Program:Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(3))).get();

            NodeId nodeId2 = NodeId.parse("ns=6;s=::Program:Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    public void ResetMachine() {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://192.168.0.122:4840").get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), "192.168.0.122", 4840);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();


            NodeId nodeId1 = NodeId.parse("ns=6;s=::Program:Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(1))).get();

            NodeId nodeId2 = NodeId.parse("ns=6;s=::Program:Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }
}
