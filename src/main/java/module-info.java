module demo.test.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens demo.test.demo2 to javafx.fxml;
    exports demo.test.demo2;
}