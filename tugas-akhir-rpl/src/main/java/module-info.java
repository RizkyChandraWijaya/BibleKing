module Obtineo {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires sqlite.jdbc;
    requires java.sql;

    opens id.ac.ukdw.fti.rpl.Obtineo to javafx.fxml;
    exports id.ac.ukdw.fti.rpl.Obtineo;
    exports id.ac.ukdw.fti.rpl.Obtineo.modal;
    exports id.ac.ukdw.fti.rpl.Obtineo.database;
}
