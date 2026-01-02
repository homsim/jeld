module com.homsim.jeld {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.xml.bind;
    requires com.google.guice;
    requires java.money;
    requires org.javamoney.moneta;
    requires org.apache.tika.core;

    opens com.homsim.jeld to javafx.fxml;
    opens com.homsim.jeld.control to javafx.fxml;

    opens com.homsim.jeld.data to org.hibernate.orm.core;

    exports com.homsim.jeld;
    opens com.homsim.jeld.control.dataimport to javafx.fxml;
}