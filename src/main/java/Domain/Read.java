package Domain;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;
import org.slf4j.Logger;

/**
 *
 * @author athil
 */
public class Read {
    Variant variant;

    public int readNode(String someString) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints("opc.tcp://192.168.0.122:4840").get();
            EndpointDescription configPoint = EndpointUtil.updateUrl(endpoints.get(0), "192.168.0.122", 4840);
            OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
            cfg.setEndpoint(configPoint);

            OpcUaClient client = OpcUaClient.create(cfg.build());
            client.connect().get();

            NodeId nodeId = NodeId.parse("ns=6;s=::Program:Cube.Admin.ProdProcessedCount");
            NodeId nodeId1 = NodeId.parse("ns=6;s=::Program:Cube.Status.StateCurrent");

            DataValue dataValue;
            if (someString.equals("totalProduced")) {
                dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId)
                        .get();
            }
            else {
                dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId1)
                        .get();
            }

            variant = dataValue.getValue();
        }catch (Throwable ex) {
            ex.printStackTrace();
        }
        return (int) variant.getValue();

    }

}
