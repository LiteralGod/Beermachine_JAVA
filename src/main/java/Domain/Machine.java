package Domain;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;
import org.slf4j.Logger;

public class Machine {
    List<EndpointDescription> endpoints;
    String prefix = "opc.tcp://";
    String hostName = "127.0.0.1";
    int port = 4840;
    String endPoint = prefix + hostName + ":" + port;
    String nsString = "ns=6;s=::Program:";
    NodeId nodeId = NodeId.parse(nsString + "Cube.Admin.ProdProcessedCount");
    NodeId nodeId1 = NodeId.parse(nsString + "Cube.Status.StateCurrent");

    UShort ShortNodeValue;
    float floatNodeValue;
    int intNodeValue;

    Variant variant;
    DataValue dataValue;

    UaSubscription.ItemCreationCallback onItemCreated;

    public void setShortNodeValue(UShort shortNodeValue) {
        this.ShortNodeValue = shortNodeValue;
    }

    public UShort getShortNodeValue() {
        return ShortNodeValue;
    }

    public void setIntNodeValue(int intNodeValue) {
        this.intNodeValue = intNodeValue;
    }

    public int getIntNodeValue() {
        return intNodeValue;
    }

    public float getFloatNodeValue() {
        return floatNodeValue;
    }

    public void setFloatNodeValue(float floatNodeValue) {
        this.floatNodeValue = floatNodeValue;
    }

    public OpcUaClient connect() {
        try {
            endpoints = DiscoveryClient.getEndpoints(endPoint).get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), hostName, port);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);
            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();
            return client;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void subscriptionFloat (String nodeValue) {
        try {
            this.connect();

            NodeId nodeId = NodeId.parse(nodeValue);

            // what to read
            ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

            // create a subscription @ 1000ms
            UaSubscription subscription = connect().getSubscriptionManager().createSubscription(1000.0).get();

            // important: client handle must be unique per item
            UInteger clientHandle = subscription.getSubscriptionId();
            MonitoringParameters parameters = new MonitoringParameters(
                    clientHandle,
                    1000.0,     // sampling interval
                    null,       // filter, null means use default
                    Unsigned.uint(10),   // queue size
                    true        // discard oldest
            );

            // creation request
            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

            // setting the consumer after the subscription creation
            UaSubscription.ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionValueFloat);
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, Arrays.asList(request), onItemCreated).get();

            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId=" + item.getReadValueId().getNodeId());
                } else {
                    System.out.println("failed to create item for nodeId=" + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void subscriptionInt (String nodeValue) {
        try {
            this.connect();

            NodeId nodeId = NodeId.parse(nodeValue);

            // what to read
            ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

            // create a subscription @ 1000ms
            UaSubscription subscription = connect().getSubscriptionManager().createSubscription(1000.0).get();

            // important: client handle must be unique per item
            UInteger clientHandle = subscription.getSubscriptionId();
            MonitoringParameters parameters = new MonitoringParameters(
                    clientHandle,
                    1000.0,     // sampling interval
                    null,       // filter, null means use default
                    Unsigned.uint(10),   // queue size
                    true        // discard oldest
            );

            // creation request
            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

            // setting the consumer after the subscription creation
            UaSubscription.ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionValueInt);
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, Arrays.asList(request), onItemCreated).get();

            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId=" + item.getReadValueId().getNodeId());
                } else {
                    System.out.println("failed to create item for nodeId=" + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void subscriptionChar (String nodeValue) {
        try {
            this.connect();

            NodeId nodeId = NodeId.parse(nodeValue);

            // what to read
            ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

            // create a subscription @ 1000ms
            UaSubscription subscription = connect().getSubscriptionManager().createSubscription(1000.0).get();

            // important: client handle must be unique per item
            UInteger clientHandle = subscription.getSubscriptionId();
            MonitoringParameters parameters = new MonitoringParameters(
                    clientHandle,
                    1000.0,     // sampling interval
                    null,       // filter, null means use default
                    Unsigned.uint(10),   // queue size
                    true        // discard oldest
            );

            // creation request
            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

            // setting the consumer after the subscription creation
            UaSubscription.ItemCreationCallback onItemCreated = (item, id) -> item.setValueConsumer(this::onSubscriptionValueChar);
            List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both, Arrays.asList(request), onItemCreated).get();

            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    System.out.println("item created for nodeId=" + item.getReadValueId().getNodeId());
                } else {
                    System.out.println("failed to create item for nodeId=" + item.getReadValueId().getNodeId() + " (status=" + item.getStatusCode() + ")");
                }
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void onSubscriptionValueFloat(UaMonitoredItem item, DataValue value) {
        this.setFloatNodeValue((Float) value.getValue().getValue());
    }
    private void onSubscriptionValueInt(UaMonitoredItem item, DataValue value) {
        this.setIntNodeValue((Integer) value.getValue().getValue());
    }
    private void onSubscriptionValueChar(UaMonitoredItem item, DataValue value) {
        this.setShortNodeValue((UShort) value.getValue().getValue());
    }

    public void totalProduced() {
        this.subscriptionInt(nsString + "Cube.Admin.ProdProcessedCount");
    }

    public void totalDefect(){
        this.subscriptionInt(nsString + "Cube.Admin.ProdDefectiveCount");
    }

    public void totalGood(){
        this.subscriptionInt(nsString + "product.good");
    }

    public void currentStatus(){
        this.subscriptionInt(nsString + "Cube.Status.StateCurrent");
    }

    public void humidity(){
        this.subscriptionFloat(nsString + "Cube.Status.Parameter[2].Value");
    }

    public void temperature(){
        this.subscriptionFloat(nsString + "Cube.Status.Parameter[3].Value");
    }

    public void vibration(){
        this.subscriptionFloat(nsString + "Cube.Status.Parameter[4].Value");
    }

    public void barley(){
        this.subscriptionFloat(nsString + "Inventory.Barley");
    }

    public void hops(){
        this.subscriptionFloat(nsString + "Inventory.Hops");
    }

    public void malt(){
        this.subscriptionFloat(nsString + "Inventory.Malt");
    }

    public void wheat(){
        this.subscriptionFloat(nsString + "Inventory.Wheat");
    }

    public void yeast(){
        this.subscriptionFloat(nsString + "Inventory.Yeast");
    }

    public void maintenance(){
        this.subscriptionChar(nsString + "Maintenance.Counter");
    }

    public int readNode(String someString) {
        try {
            this.connect();

            if (someString.equals("totalProduced")) {
                dataValue = connect().readValue(0, TimestampsToReturn.Both, nodeId)
                        .get();
            } else {
                dataValue = connect().readValue(0, TimestampsToReturn.Both, nodeId1)
                        .get();
            }

            variant = dataValue.getValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return (int) variant.getValue();
    }

    public void StartMachine(float beerTypeID, float setSpeed, float setAmount) {
        try {
            this.connect();

            //Set beerType
            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.Parameter[1].Value");
            connect().writeValue(nodeId2, DataValue.valueOnly(new Variant(beerTypeID))).get();
            System.out.println(beerTypeID);

            //set Speed
            NodeId nodeId3 = NodeId.parse(nsString + "Cube.Command.MachSpeed");
            connect().writeValue(nodeId3, DataValue.valueOnly(new Variant(setSpeed))).get();
            System.out.println(setSpeed);

            //set Amount if not 0
            if (setAmount != 0) {
                NodeId nodeId4 = NodeId.parse(nsString + "Cube.Command.Parameter[2].Value");
                connect().writeValue(nodeId4, DataValue.valueOnly(new Variant(setAmount))).get();

            }


            //Start request
            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            connect().writeValue(nodeId1, DataValue.valueOnly(new Variant(2))).get();

            NodeId nodeId11 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            connect().writeValue(nodeId11, DataValue.valueOnly(new Variant(true))).get();


        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void StopMachine() {
        try {
            this.connect();

            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            connect().writeValue(nodeId1, DataValue.valueOnly(new Variant(3))).get();

            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            connect().writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void ResetMachine() {
        try {
            this.connect();

            NodeId nodeId1 = NodeId.parse(nsString + "Cube.Command.CntrlCmd");
            connect().writeValue(nodeId1, DataValue.valueOnly(new Variant(1))).get();

            NodeId nodeId2 = NodeId.parse(nsString + "Cube.Command.CmdChangeRequest");
            connect().writeValue(nodeId2, DataValue.valueOnly(new Variant(true))).get();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
