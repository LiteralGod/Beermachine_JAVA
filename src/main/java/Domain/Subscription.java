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
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;

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

    public void connectFloat(String nodeValue) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://192.168.0.122:4840").get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), "192.168.0.122", 4840);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();
            NodeId nodeId = NodeId.parse(nodeValue);

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
    public void connectInt(String nodeValue) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://192.168.0.122:4840").get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), "192.168.0.122", 4840);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();
            NodeId nodeId = NodeId.parse(nodeValue);

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
        this.setFloatNodeValue((Float) value.getValue().getValue());
    }
    private void onSubscriptionValueInt(UaMonitoredItem item, DataValue value) {
        this.setIntNodeValue((Integer) value.getValue().getValue());
    }

    public void totalProduced() {
        this.connectInt("ns=6;s=::Program:Cube.Admin.ProdProcessedCount");
    }

    public void totalDefect(){
        this.connectInt("ns=6;s=::Program:Cube.Admin.ProdDefectiveCount");
    }
    public void totalGood(){
        this.connectInt("ns=6;s=::Program:product.good");
    }
    public void currentStatus(){
        this.connectInt("ns=6;s=::Program:Cube.Status.StateCurrent");
    }
    public void humidity(){
        this.connectFloat("ns=6;s=::Program:Cube.Status.Parameter[2].Value");
    }
    public void temperature(){
        this.connectFloat("ns=6;s=::Program:Cube.Status.Parameter[3].Value");
    }

    public void vibration(){
        this.connectFloat("ns=6;s=::Program:Cube.Status.Parameter[4].Value");
    }
    public void barley(){
        this.connectFloat("ns=6;s=::Program:Inventory.Barley");
    }
    public void hops(){
        this.connectFloat("ns=6;s=::Program:Inventory.Hops");

    }


    public void malt(){
        this.connectFloat("ns=6;s=::Program:Inventory.Malt");

    }
    public void wheat(){
        this.connectFloat("ns=6;s=::Program:Inventory.Wheat");

    }
    public void yeast(){
        this.connectFloat("ns=6;s=::Program:Inventory.Yeast");

    }

}
