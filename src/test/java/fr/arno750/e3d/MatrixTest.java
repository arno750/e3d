package fr.arno750.e3d;

import fr.arno750.e3d.base.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixTest {

    @Test
    void whenGetNull() {
        Matrix n = Matrix.getNull();
        assertEquals(0, n.getDeterminant());
        Matrix m = new Matrix(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        assertEquals(n, Matrix.multiply(n, m));
        assertEquals(n, Matrix.multiply(m, n));
        double d = m.getDeterminant();
        m.add(n);
        assertEquals(d, m.getDeterminant());
    }

    @Test
    void whenGetIdentity() {
        Matrix i = Matrix.getIdentity();
        assertEquals(1, i.getDeterminant());
        Matrix m = new Matrix(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        assertEquals(m, Matrix.multiply(i, m));
        assertEquals(m, Matrix.multiply(m, i));
    }
}
