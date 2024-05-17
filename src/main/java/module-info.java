module fr.ul.miage.weiss.s8_projet_baignoire {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens fr.ul.miage.weiss.s8_projet_poo to javafx.fxml;
    exports fr.ul.miage.weiss.s8_projet_poo;
}