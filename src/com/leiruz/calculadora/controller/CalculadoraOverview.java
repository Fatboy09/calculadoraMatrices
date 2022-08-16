package com.leiruz.calculadora.controller;

import com.leiruz.calculadora.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculadoraOverview implements Initializable {

    private final ObservableList<String> strings = FXCollections.observableArrayList("filas", "columnas");
    private int columnsMatrizA;
    private int rowsMatrizA;
    private int columnsMatrizB;
    private int rowsMatrizB;

    @FXML
    private ComboBox<String> cb_MA;
    @FXML
    private ComboBox<String> cb_MB;
    @FXML
    private GridPane gp_MA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_MA.setValue("filas");
        cb_MB.setValue("filas");
        cb_MA.setItems(strings);
        cb_MB.setItems(strings);
        columnsMatrizA = columnsMatrizB = rowsMatrizA = rowsMatrizB = 3;

    }

    @FXML
    public void handleButtonAdd_mA() {
        String s = cb_MA.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            for (int row = 0; row < this.rowsMatrizA; ++row) {
                TextField textField = new TextField();
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                gp_MA.addColumn(columnsMatrizA,textField);
            }
            ++columnsMatrizA;
        } else {
            for (int column = 0; column < this.columnsMatrizA; ++column) {
                TextField textField = new TextField();
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                gp_MA.addRow(rowsMatrizA,textField);
            }
            ++rowsMatrizA;
        }
    }
}
