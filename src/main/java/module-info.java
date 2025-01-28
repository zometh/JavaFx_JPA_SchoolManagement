module sn.zomethdev.school_management_jpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;  // Ajout de cette ligne

    opens sn.zomethdev.school_management_jpa to javafx.fxml;
    exports sn.zomethdev.school_management_jpa;

    // Ouvrir le package entities à la fois pour hibernate et jakarta.persistence
    opens sn.zomethdev.school_management_jpa.entities;  // Ouvrir complètement le package
    exports sn.zomethdev.school_management_jpa.entities;

    // Ajouter l'accès au package util
    opens sn.zomethdev.school_management_jpa.util;
}