module org.wt.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;


    opens org.wt.demo to javafx.fxml;
    exports org.wt.demo;
}