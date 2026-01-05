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

    // opens com.homsim.jeld.data.parse to jakarta.xml.bind;
    /*
    this does not work because the package exposure is not recursive...
    I think currently my only option is to state all packages manually:
     */
    opens com.homsim.jeld.data.parse.camt_052_001_01 to jakarta.xml.bind;
    opens com.homsim.jeld.data.parse.camt_052_001_08 to jakarta.xml.bind;

    opens com.homsim.jeld.data to org.hibernate.orm.core;

    exports com.homsim.jeld;
    opens com.homsim.jeld.control.dataimport to javafx.fxml;
}