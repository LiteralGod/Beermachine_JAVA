module Beermachine_JAVA {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.eclipse.milo.opcua.sdk.client;
    requires org.eclipse.milo.opcua.sdk.core;
    requires org.eclipse.milo.opcua.stack.client;
    requires org.eclipse.milo.opcua.stack.core;
    requires slf4j.api;
    requires java.sql;
    requires postgresql;

    opens Domain;
    opens Presentation;
    opens Persistence;
}