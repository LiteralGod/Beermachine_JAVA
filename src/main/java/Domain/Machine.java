package Domain;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;
import org.slf4j.Logger;

public class Machine {
    List<EndpointDescription> endpoints;
    String prefix = "opc.tcp://";
    String hostName = "192.168.0.122";
    int port = 4840;
    String nsString = "ns=6;s=::Program:";
    Variant variant;

    NodeId nodeId = NodeId.parse(nsString + "Cube.Admin.ProdProcessedCount");
    NodeId nodeId1 = NodeId.parse(nsString + "Cube.Status.StateCurrent");

    DataValue dataValue;
    OpcUaClient client;


    public void connect() {
        try {
            endpoints = DiscoveryClient.getEndpoints(prefix + hostName).get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), hostName, port);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);
            client = OpcUaClient.create(cfg.build());
            client.connect().get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public int readNode(String someString) {
        this.connect();
        try {
            if (someString.equals("totalProduced")) {
                dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId)
                        .get();
            } else {
                dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId1)
                        .get();
            }

            variant = dataValue.getValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return (int) variant.getValue();
    }

    public void StartMachine(float beerTypeID, float setSpeed, float setAmount) {
        this.connect();
        try {
            //Set beerType
            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.Parameter[1].Value");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(beerTypeID))).get();
            System.out.println(beerTypeID);

            //set Speed
            NodeId nodeId3 = NodeId.parse(nsString + "Cube.Command.MachSpeed");
            client.writeValue(nodeId3, DataValue.valueOnly(new Variant(setSpeed))).get();
            System.out.println(setSpeed);

            //set Amount if not 0
            if (setAmount != 0) {
                NodeId nodeId4 = NodeId.parse(nsString + "Cube.Command.Parameter[2].Value");
                client.writeValue(nodeId4, DataValue.valueOnly(new Variant(setAmount))).get();

            }


            //Start request
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(2))).get();

            NodeId nodeId11 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId11, DataValue.valueOnly(new Variant(true))).get();


        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void StopMachine() {
        this.connect();
        try {
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(3))).get();

            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void ResetMachine() {
        this.connect();
        try {
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(1))).get();

            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
