package com.leiruz.calculadora.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculadoraOverview implements Initializable {

    private final ObservableList<String> strings = FXCollections.observableArrayList("filas", "columnas");

    @FXML
    private ComboBox<String> cb_MA;
    @FXML
    private ComboBox<String> cb_MB;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_MA.setValue("filas");
        cb_MB.setValue("filas");
        cb_MA.setItems(strings);
        cb_MB.setItems(strings);

    }
}
