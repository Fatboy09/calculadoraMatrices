package com.leiruz.calculadora.controller;

import com.leiruz.calculadora.dto.maths.Matriz;
import com.leiruz.calculadora.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private Matriz matrizC;
    @FXML
    private StackPane SP_mA;
    @FXML
    private StackPane SP_mB;
    @FXML
    private StackPane SP_mC;
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
        columnsMatrizA = columnsMatrizB = rowsMatrizA = rowsMatrizB = 3;
        matrizA = new Matriz(rowsMatrizA, columnsMatrizA, new ArrayList<>());
        matrizB = new Matriz(rowsMatrizB, columnsMatrizB, new ArrayList<>());
        matrizC = new Matriz();
    }

    @FXML
    public void handleButtonAdd_mA() {
        String s = cb_MA.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            this.columnsMatrizA++;
            matrizA.setColumns(this.columnsMatrizA);
        } else {
            this.rowsMatrizA++;
            matrizA.setRows(this.rowsMatrizA);
        }
        if (!SP_mA.getChildren().isEmpty()) {
            SP_mA.getChildren().clear();
        }
        SP_mA.getChildren().add(createMatrizGP(matrizA.getRows(), matrizA.getColumns(), false));
    }

    @FXML
    public void handleButtonAdd_mB() {
        String s = cb_MB.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            this.columnsMatrizB++;
            matrizB.setColumns(this.columnsMatrizB);
        } else {
            this.rowsMatrizB++;
            matrizB.setRows(this.rowsMatrizB);
        }
        if (!SP_mB.getChildren().isEmpty()) {
            SP_mB.getChildren().clear();
        }
        SP_mB.getChildren().add(createMatrizGP(matrizB.getRows(), matrizB.getColumns(), false));
    }

    @FXML
    public void handleButtonDel_mA() {
        String s = cb_MA.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            this.columnsMatrizA--;
            matrizA.setColumns(this.columnsMatrizA);
        } else {
            this.rowsMatrizA--;
            matrizA.setRows(this.rowsMatrizA);
        }
        if (!SP_mA.getChildren().isEmpty()) {
            SP_mA.getChildren().clear();
        }
        SP_mA.getChildren().add(createMatrizGP(matrizA.getRows(), matrizA.getColumns(), false));
    }

    @FXML
    public void handleButtonDel_mB() {
        String s = cb_MB.getValue();
        if (s.equals(Constants.COLUMNS.getValue())) {
            this.columnsMatrizB--;
            matrizB.setColumns(this.columnsMatrizB);
        } else {
            this.rowsMatrizB--;
            matrizB.setRows(this.rowsMatrizB);
        }
        if (!SP_mB.getChildren().isEmpty()) {
            SP_mB.getChildren().clear();
        }
        SP_mB.getChildren().add(createMatrizGP(matrizB.getRows(), matrizB.getColumns(), false));
    }

    private GridPane createMatrizGP(final int rows, final int columns, final boolean isGPMc) {
        GridPane auxGp = new GridPane();
        List<TextField> listTextFields = new ArrayList<>();

        if (isGPMc) {
            matrizC.getMatriz().forEach(bigDecimal -> listTextFields.add(new TextField(
                    bigDecimal.stripTrailingZeros().scale() <= 0 ? bigDecimal.toString() : bigDecimal.setScale(3, RoundingMode.HALF_UP).toString())));
            listTextFields.forEach(textField -> {
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(false);
            });
        } else {
            for (int e = 0; e < columns * rows; ++e) {
                TextField textField = textFieldSupplier.get();
                textField.setMaxWidth(100);
                textField.setMaxHeight(26);
                textField.setAlignment(Pos.CENTER);
                listTextFields.add(textField);
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                GridPane.setConstraints(listTextFields.get(row * columns + col), col, row);
            }
        }
        auxGp.setAlignment(Pos.CENTER);
        auxGp.setHgap(5D);
        auxGp.setVgap(5D);
        listTextFields.forEach(textField -> auxGp.getChildren().addAll(textField));
        StackPane.setMargin(auxGp, new Insets(5D));
        return auxGp;
    }

    @FXML
    public void handleButtonDet_mA() {
        if (!matrizA.getMatriz().isEmpty()) {
            matrizA.getMatriz().clear();
        }
        if (!SP_mC.getChildren().isEmpty()) {
            SP_mC.getChildren().clear();
        }
        if (Operaciones.is_Matrix_square(matrizA)) {
            inicializarMatriz(SP_mA, matrizA);
            matrizC.setRows(matrizA.getRows());
            matrizC.setColumns(matrizA.getColumns());
            matrizC.setMatriz(matrizA.getMatriz());
            BigDecimal determinante = Operaciones.calculateDeterminante(matrizA.getMatriz(), matrizA.getColumns());
            Label resultado = new Label(" = " + determinante.toString());
            resultado.setFont(Font.font("Fira Code", FontWeight.BOLD, FontPosture.REGULAR,25D));
            HBox hBox = new HBox();
            hBox.setSpacing(10D);
            hBox.setPadding(new Insets(10D));
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(createMatrizGP(matrizC.getRows(), matrizC.getColumns(), true),resultado);
            SP_mC.getChildren().addAll(hBox);
        }
    }

    @FXML
    public void handleButtonDet_mB() {
        if (!matrizB.getMatriz().isEmpty()) {
            matrizB.getMatriz().clear();
        }
        if (!SP_mC.getChildren().isEmpty()) {
            SP_mC.getChildren().clear();
        }
        if (Operaciones.is_Matrix_square(matrizB)) {
            inicializarMatriz(SP_mB, matrizB);
            matrizC.setRows(matrizB.getRows());
            matrizC.setColumns(matrizB.getColumns());
            matrizC.setMatriz(matrizB.getMatriz());
            BigDecimal determinante = Operaciones.calculateDeterminante(matrizB.getMatriz(), matrizB.getColumns());
            Label resultado = new Label(" = " + determinante.toString());
            resultado.setFont(Font.font("Fira Code", FontWeight.BOLD, FontPosture.REGULAR,25D));
            HBox hBox = new HBox();
            hBox.setSpacing(10D);
            hBox.setPadding(new Insets(10D));
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(createMatrizGP(matrizC.getRows(), matrizC.getColumns(), true),resultado);
            SP_mC.getChildren().addAll(hBox);
        }
    }

    private void inicializarMatriz(StackPane root, Matriz matriz) {
        GridPane gridPane = (GridPane) root.getChildren().get(0);
        gridPane.getChildren().forEach(node -> {
            TextField textField = (TextField) node;
            try {
                matriz.getMatriz().add(new BigDecimal(textField.getText()));
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.getMessage());
            }
        });
    }
}
