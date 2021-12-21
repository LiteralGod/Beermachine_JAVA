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
    String hostName = "127.0.0.1";
    int port = 4840;
    String nsString = "ns=6;s=::Program:";
    Variant variant;

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

    public void StartMachine(float beerTypeID, float setSpeed, float setAmount, float batchID) {
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

            //set Batch ID
            NodeId nodeId5 = NodeId.parse(nsString + "Cube.Command.Parameter[0].Value");
            client.writeValue(nodeId5, DataValue.valueOnly(new Variant(batchID))).get();



            //Start request
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(2))).get();

            NodeId nodeId11 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId11, DataValue.valueOnly(new Variant(true))).get();


        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
    public float readBatchID() {
        this.connect();
        try {
            NodeId nodeId = NodeId.parse(nsString + "Cube.Status.Parameter[0].Value");
            dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId)
                    .get();
            variant = dataValue.getValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        System.out.println(variant.getValue());
        return (float) variant.getValue();
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

    public void AbortMachine() {
        this.connect();
        try {
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(4))).get();

            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
    public void ClearMachine() {
        this.connect();
        try {
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            client.writeValue(nodeId1, DataValue.valueOnly(new Variant(5))).get();

            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            client.writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
