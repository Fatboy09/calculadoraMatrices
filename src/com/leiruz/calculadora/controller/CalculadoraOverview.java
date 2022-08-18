package com.leiruz.calculadora.controller;

import com.leiruz.calculadora.dto.maths.Matriz;
import com.leiruz.calculadora.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class CalculadoraOverview implements Initializable {

    private final ObservableList<String> strings = FXCollections.observableArrayList("filas", "columnas");
    private final Supplier<TextField> textFieldSupplier = TextField::new;
    private int columnsMatrizA;
    private int rowsMatrizA;
    private int columnsMatrizB;
    private int rowsMatrizB;
    private Matriz matrizA;
    private Matriz matrizB;

    @FXML
    private ComboBox<String> cb_MA;
    @FXML
    private ComboBox<String> cb_MB;
    @FXML
    private GridPane gp_MA;
    @FXML
    private GridPane gp_MB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_MA.setValue("filas");
        cb_MB.setValue("filas");
        cb_MA.setItems(strings);
        cb_MB.setItems(strings);
        columnsMatrizA = columnsMatrizB = rowsMatrizA = rowsMatrizB = 3;
        matrizA = new Matriz(rowsMatrizA,columnsMatrizA,new ArrayList<>());
        matrizB = new Matriz(rowsMatrizB,columnsMatrizB,new ArrayList<>());
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
            matrizB.setColumns(columnsMatrizA);
        } else {
            for (int column = 0; column < this.columnsMatrizA; ++column) {
                TextField textField = new TextField();
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                gp_MA.addRow(rowsMatrizA,textField);
            }
            ++rowsMatrizA;
            matrizA.setRows(rowsMatrizA);
        }
    }

    @FXML
    public void handleButtonAdd_mB() {
        String s = cb_MB.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            for (int row = 0; row < this.rowsMatrizB; ++row) {
                TextField textField = new TextField();
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                gp_MB.addColumn(columnsMatrizB,textField);
            }
            ++columnsMatrizB;
            matrizB.setColumns(columnsMatrizB);
        } else {
            for (int column = 0; column < this.columnsMatrizB; ++column) {
                TextField textField = new TextField();
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                gp_MB.addRow(rowsMatrizB,textField);
            }
            ++rowsMatrizB;
            matrizB.setRows(rowsMatrizB);
        }
    }

    @FXML
    public void handleButtonDel_mA() {
        String s = cb_MA.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            for (int row = 0; row < this.rowsMatrizA; ++row) {
                System.out.println(row * matrizA.getColumns() + (columnsMatrizA - 1));
                System.out.println(matrizA.getColumns());
                gp_MA.getChildren().remove(row * matrizA.getColumns() + (columnsMatrizA));
            }

        } else {
            for (int column = 0; column < this.columnsMatrizA; ++column) {
                //gp_MA.getChildren().remove((rowsMatrizA - 1) * matrizA.getColumns() + column);
            }
        }
    }

    @FXML
    public void handleButtonDel_mB() {
        /*String s = cb_MA.getValue();
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
        }*/
    }
}
