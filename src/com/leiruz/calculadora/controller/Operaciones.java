package com.leiruz.calculadora.controller;

import com.leiruz.calculadora.dto.maths.Matriz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Operaciones {

    public static boolean is_Matrix_square(Matriz matriz) {
        return Objects.equals(matriz.getRows(), matriz.getColumns());
    }

    private static List<BigDecimal> getMatrizCofactores(List<BigDecimal> matriz, final int orden, final int row, final int column) {
        List<BigDecimal> cofactores = new ArrayList<>();

        int i, j;
        i = j = 0;

        for (int x = 0; x < orden; ++x) {
            if (x == row) continue;
            for (int y = 0; y < orden; ++y) {
                if (y == column) continue;
                cofactores.add(i * (orden - 1) + j, matriz.get(x * orden + y));
                j++;
            }
            if (j >= orden) {
                ++i;
                j = 0;
            }
        }

        return cofactores;
    }

    private static int column_max_with_zeros(List<BigDecimal> matriz, int orden) {
        int contador_zeros = 0;
        int aux_contador_zeros = 0;
        int column_zeros = 0;

        for (int row = 0; row < orden; ++row) {
            for (int column = 0; column < orden; ++column) {
                if (Objects.equals(matriz.get(column * orden + row), BigDecimal.ZERO)) {
                    contador_zeros++;
                }
            }
            if (contador_zeros > aux_contador_zeros) {
                column_zeros = row;
                aux_contador_zeros = contador_zeros;
            }
            contador_zeros = 0;
        }
        return column_zeros;
    }

    public static BigDecimal calculateDeterminante(List<BigDecimal> matriz, int orden) {
        if (orden == 1)
            return matriz.get(0);
        else if (orden == 2)
            return (matriz.get(0).multiply(matriz.get(3))).subtract(matriz.get(1).multiply(matriz.get(2)));

        BigDecimal resultado = BigDecimal.ZERO;
        int column = column_max_with_zeros(matriz, orden);
        for (int x = 0; x < orden; ++x) {
            List<BigDecimal> cofactores = getMatrizCofactores(matriz, orden, x, column);
            if (!Objects.equals(BigDecimal.ZERO, matriz.get(x * orden + column))) {
                resultado = (matriz.get(x * orden + column).multiply((calculateDeterminante(cofactores, orden - 1))
                        .multiply(BigDecimal.valueOf(-1).pow(x * orden + column))))
                        .add(resultado);
            }
        }
        return resultado;
    }

    private void addRows(List<BigDecimal> matriz, final int columns, final int row1, final int row2, final int column, BigDecimal lcm) {
        BigDecimal factor1 = lcm.divide(matriz.get(row1 * columns + column), RoundingMode.DOWN);
        BigDecimal factor2 = lcm.divide(matriz.get(row2 * columns + column), RoundingMode.DOWN);
        if (((matriz.get(row1 * columns + column).compareTo(BigDecimal.ZERO) < 0) && (matriz.get(row2 * columns + column).compareTo(BigDecimal.ZERO) < 0))
                || ((matriz.get(row1 * columns + column).compareTo(BigDecimal.ZERO) > 0) && (matriz.get(row2 * columns + column).compareTo(BigDecimal.ZERO) > 0))) {
            factor1 = factor1.multiply(BigDecimal.valueOf(-1));
        } else {
            factor1 = factor1.abs();
            factor2 = factor2.abs();
        }

        for (int y = column; y < columns; ++y) {
            matriz.set(row2 * columns + y, (factor1.multiply(matriz.get(row1 * columns + y))).add(factor2.multiply(matriz.get(row2 * columns + y))));
        }

    }

    private BigDecimal get_lcm(BigDecimal bg1, BigDecimal bg2) {
        BigDecimal big = bg1.max(bg2);
        BigDecimal small = bg1.min(bg2);
        for (int i = big.intValue(); ; i += big.intValue()) {
            if (Objects.equals(BigDecimal.valueOf(i).remainder(small), BigDecimal.ZERO)) {
                return BigDecimal.valueOf(i);
            }
        }
    }

    private List<BigDecimal> calculateMatrizEscalonada(Matriz matriz) {
        List<BigDecimal> matriz_escalonada = new ArrayList<>(matriz.getMatriz());
        int row_pivot = 0;
        for (int y = 0; y < matriz.getColumns(); ++y) {
            for (int x = (row_pivot + 1); x < matriz.getRows(); ++x) {
                if (!Objects.equals(matriz_escalonada.get(x * matriz.getColumns() + y), BigDecimal.ZERO)) {
                    BigDecimal lcm = get_lcm(matriz_escalonada.get(row_pivot * matriz.getColumns() + y), matriz_escalonada.get(x * matriz.getColumns() + y));
                    addRows(matriz_escalonada, matriz.getColumns(), row_pivot, x, y, lcm);
                }
            }
            row_pivot++;
        }
        return matriz_escalonada;
    }

    private int get_total_rows_nonZeros(List<BigDecimal> matriz, int rows, final int columns) {
        int contador = 0;
        int rows_zeros = 0;

        for (int x = 0; x < rows; ++x) {
            for (int y = 0; y < columns; ++y) {
                if (Objects.equals(matriz.get(x * columns + y), BigDecimal.ZERO))
                    contador++;
            }
            if (contador == columns)
                rows_zeros++;
            contador = 0;
        }
        return rows - rows_zeros;
    }

    public static List<BigDecimal> calculateSuma(Matriz matrizA, Matriz matrizB) {
        if (!Objects.equals(matrizA.getRows(), matrizB.getRows()) || !Objects.equals(matrizA.getColumns(), matrizB.getColumns()))
            return null;

        List<BigDecimal> resultado = new ArrayList<>();
        int idx;
        for (int row = 0; row < matrizA.getRows(); row++) {
            for (int column = 0; column < matrizA.getColumns(); column++) {
                idx = row * matrizA.getColumns() + column;
                resultado.add(idx, matrizA.getMatriz().get(idx).add(matrizB.getMatriz().get(idx)));
            }
        }
        return resultado;
    }

    public List<BigDecimal> calculateResta(Matriz matrizA, Matriz matrizB) {
        if (!Objects.equals(matrizA.getRows(), matrizB.getRows()) || !Objects.equals(matrizA.getColumns(), matrizB.getColumns()))
            return null;

        List<BigDecimal> resultado = new ArrayList<>();
        int idx;
        for (int row = 0; row < matrizA.getRows(); row++) {
            for (int column = 0; column < matrizA.getColumns(); column++) {
                idx = row * matrizA.getColumns() + column;
                resultado.add(idx, matrizA.getMatriz().get(idx).subtract(matrizB.getMatriz().get(idx)));
            }
        }
        return resultado;
    }

    public List<BigDecimal> calculateProducto(Matriz matrizA, Matriz matrizB) {
        if (!Objects.equals(matrizA.getColumns(), matrizB.getRows()))
            return null;

        BigDecimal[] m3 = new BigDecimal[matrizA.getRows() * matrizB.getColumns()];
        int idx, iM1, iM2;

        for (int i = 0; i < matrizA.getRows() * matrizB.getColumns(); ++i) {
            m3[i] = BigDecimal.ZERO;
        }

        for (int row = 0; row < matrizA.getRows(); row++) {
            for (int column = 0; column < matrizB.getColumns(); column++) {
                for (int z = 0; z < matrizB.getRows(); z++) {
                    idx = row * matrizB.getColumns() + column;
                    iM1 = row * matrizB.getRows() + z;
                    iM2 = z * matrizB.getColumns() + column;
                    m3[idx] = matrizA.getMatriz().get(iM1).multiply(matrizB.getMatriz().get(iM2)).add(m3[idx]);
                }
            }
        }
        return new ArrayList<>(Arrays.asList(m3).subList(0, matrizA.getRows() * matrizB.getColumns()));
    }

    public List<BigDecimal> calculateProductoEscalar(BigDecimal number, Matriz matriz) {
        List<BigDecimal> resultado = new ArrayList<>();
        matriz.getMatriz().forEach(e -> resultado.add(e.multiply(number)));
        return resultado;
    }

    public List<BigDecimal> calculateTranspuesta(Matriz matriz) {
        List<BigDecimal> resultado = new ArrayList<>();
        int idx;
        for (int row = 0; row < matriz.getColumns(); row++) {
            for (int column = 0; column < matriz.getRows(); column++) {
                idx = row * matriz.getRows() + column;
                resultado.add(idx, matriz.getMatriz().get(column * matriz.getColumns() + row));
            }
        }
        return resultado;
    }

    public List<BigDecimal> calculateAdjunta(Matriz matriz) {
        List<BigDecimal> cofactores = new ArrayList<>();
        int orden = matriz.getRows();

        for (int row = 0; row < orden; ++row) {
            for (int column = 0; column < orden; ++column) {
                List<BigDecimal> matriz_cofactores = getMatrizCofactores(matriz.getMatriz(), orden, row, column);
                cofactores.add(row * orden + column,
                        BigDecimal.valueOf(-1).pow(row + column).multiply(calculateDeterminante(matriz_cofactores, orden - 1)));
            }
        }
        Matriz m_cofactores = new Matriz(orden, orden, cofactores);

        return calculateTranspuesta(m_cofactores);
    }

    public List<BigDecimal> calculateInversa(Matriz matriz) {
        int orden = matriz.getRows();
        BigDecimal determinante = calculateDeterminante(matriz.getMatriz(), orden);

        if (Objects.equals(determinante, BigDecimal.ZERO)) {
            System.out.println("La matriz no tiene inversa porque su determinante es 0");
            return null;
        }
        Matriz adjunta = new Matriz(orden, orden, calculateAdjunta(matriz));

        return calculateProductoEscalar(BigDecimal.ONE.divide(determinante, 20, RoundingMode.DOWN), adjunta);
    }

    private int calculateRange(Matriz matriz) {
        List<BigDecimal> escalonada = calculateMatrizEscalonada(matriz);
        return get_total_rows_nonZeros(escalonada, matriz.getRows(), matriz.getColumns());
    }

}
