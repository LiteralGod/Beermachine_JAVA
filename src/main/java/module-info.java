module Beermachine_JAVA {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.eclipse.milo.opcua.sdk.client;
    requires org.eclipse.milo.opcua.sdk.core;
    requires org.eclipse.milo.opcua.stack.client;
    requires org.eclipse.milo.opcua.stack.core;
    requires org.slf4j;
    requires java.sql;
    requires mysql.connector.java;
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.apache.jsp;
    requires org.eclipse.jetty.webapp;
    requires org.bouncycastle.provider;

    opens Domain;
    opens Presentation;
    opens Persistence;
}