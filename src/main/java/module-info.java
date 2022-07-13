module com.github.metakol.ourpetproject{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

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
    opens com.github.fainaaa.controllers.for_studying_scenes to javafx.fxml;
    opens com.github.fainaaa.controllers.for_intermediate_scenes to javafx.fxml;
    opens com.github.fainaaa.controllers.for_testing_scenes to javafx.fxml;
    exports com.github.fainaaa.entities.for_grading_test to com.fasterxml.jackson.databind;
    opens com.github.fainaaa.entities.for_grading_test to javafx.base;
    opens com.github.fainaaa.controllers.for_results_scenes to javafx.fxml;

}