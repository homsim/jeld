module com.homsim.jeld {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires static lombok;
    requires jakarta.persistence;
    requires com.google.guice;

    opens com.homsim.jeld to javafx.fxml;
    exports com.homsim.jeld;
    exports com.homsim.jeld.control;
    opens com.homsim.jeld.control to javafx.fxml;
}