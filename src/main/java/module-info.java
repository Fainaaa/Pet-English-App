module com.github.metakol.ourpetproject{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens com.github.fainaaa to javafx.fxml;
    opens com.github.fainaaa.controllers to javafx.fxml;
    opens com.github.fainaaa.entities to javafx.base;
    exports com.github.fainaaa;
    exports com.github.fainaaa.entities to com.fasterxml.jackson.databind;
}