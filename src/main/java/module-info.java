module com.example.testefxlogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires spring.security.crypto;


    opens com.example.testefxlogin to javafx.fxml;
    exports com.example.testefxlogin;
}