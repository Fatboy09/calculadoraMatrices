package com.leiruz.calculadora.dto.maths;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.List;

public class Matriz {

    private int rows;
    private int columns;
    private List<BigDecimal> matriz;

    public Matriz(int rows, int columns, List<BigDecimal> matriz) {
        setRows(rows);
        setColumns(columns);
        setMatriz(matriz);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<BigDecimal> getMatriz() {
        return matriz;
    }

    public void setMatriz(List<BigDecimal> matriz) {
        this.matriz = matriz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Matriz matriz = (Matriz) o;

        return new EqualsBuilder().append(getRows(), matriz.getRows()).append(getColumns(), matriz.getColumns()).append(getMatriz(), matriz.getMatriz()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getRows()).append(getColumns()).append(getMatriz()).toHashCode();
    }
}
