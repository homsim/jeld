module com.homsim.jeld {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.xml.bind;
    requires com.google.guice;

    opens com.homsim.jeld to javafx.fxml;
    opens com.homsim.jeld.control to javafx.fxml;

    opens com.homsim.jeld.data.model to org.hibernate.orm.core;

    exports com.homsim.jeld;
}