module com.noxious_dream.cellularautomata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.noxious_dream.cellularautomata to javafx.fxml;
    exports com.noxious_dream.cellularautomata;
}