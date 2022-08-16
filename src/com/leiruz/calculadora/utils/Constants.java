package com.leiruz.calculadora.utils;

public enum Constants {

    ROWS("filas"),
    COLUMNS("columnas");

    private final String value;

    Constants(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
