module htl.steyr.springdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.core;
    requires jakarta.persistence;
    requires jakarta.annotation;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires org.hibernate.orm.core;

    opens htl.steyr.springdesktop to javafx.fxml, spring.core;
    opens htl.steyr.springdesktop.model to org.hibernate.orm.core, spring.core;
    opens htl.steyr.springdesktop.repository to spring.core;
    exports htl.steyr.springdesktop;
    exports htl.steyr.springdesktop.controller;
    opens htl.steyr.springdesktop.controller to javafx.fxml, spring.core;
}
