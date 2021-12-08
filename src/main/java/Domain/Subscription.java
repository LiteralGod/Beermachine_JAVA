package Domain;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author athil
 */

import java.util.Arrays;
import java.util.List;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.UaRuntimeException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;

public class Subscription {

    float floatNodeValue;

    int intNodeValue;
    OpcUaClient client;

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

    public void connectFloat(NodeId nodeId) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://127.0.0.1:4840").get();

            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(endpoints.get(0));

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();

            // what to read
            ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

            // create a subscription @ 1000ms
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

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
    public void connectInt(NodeId nodeId) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://127.0.0.1:4840").get();

            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(endpoints.get(0));

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();

            // what to read
            ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

            // create a subscription @ 1000ms
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

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

    private void onSubscriptionValueFloat(UaMonitoredItem item, DataValue value) {
        float newValue = (Float) value.getValue().getValue();
        this.setFloatNodeValue((newValue)/35000*100);
    }
    private void onSubscriptionValueInt(UaMonitoredItem item, DataValue value) {
        this.setIntNodeValue((Integer) value.getValue().getValue());
    }

    public float totalProduced() {
        try {
            NodeId nodeId = NodeId.parse("ns=6;s=::Program:Cube.Admin.ProdProcessedCount");
            this.connectInt(nodeId);
        } catch (UaRuntimeException e) {
            e.printStackTrace();
        }
        return this.getFloatNodeValue();
    }

    public void totalDefect(){
        try {
            NodeId nodeId = NodeId.parse("ns=6;s=::Program:Cube.Admin.ProdDefectiveCount");
            this.connectInt(nodeId);
        } catch (UaRuntimeException e) {
            e.printStackTrace();
        }
    }
    public void currentStatus(){
        try {
            NodeId nodeId = NodeId.parse("ns=6;s=::Program:Cube.Status.StateCurrent");
            this.connectInt(nodeId);
        } catch (UaRuntimeException e) {
            e.printStackTrace();
        }

    }
    public void barley(){
        try {
            NodeId nodeId = NodeId.parse("ns=6;s=::Program:Inventory.Barley");
            this.connectFloat(nodeId);
        } catch (UaRuntimeException e) {
            e.printStackTrace();
        }
    }

}
