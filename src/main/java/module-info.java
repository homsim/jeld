module com.homsim.jeld {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.homsim.jeld to javafx.fxml;
    exports com.homsim.jeld;
}