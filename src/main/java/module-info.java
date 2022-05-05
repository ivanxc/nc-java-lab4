module com.ivanxc.netcracker.collectionslab {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.github.librepdf.openpdf;

    opens com.ivanxc.netcracker.lab to javafx.fxml;
    exports com.ivanxc.netcracker.lab;
}