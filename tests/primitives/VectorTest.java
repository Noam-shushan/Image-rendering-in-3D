package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * testing for Vector class
 *
 * @author Noam shushan 314717588
 */
class VectorTest {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);

    @Test
    void testZeroPoint(){
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {
            out.println("good: cannot create a zero point");
        }
    }

    @Test
    void add() {
        assertEquals(new Vector(-1, -2, -3), v1.add(v2), "add not working good");
    }

    @Test
    void subtract() {
        assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "subtract not working good");
    }

    @Test
    void scale() {
        Vector vscale = v1.scale(-1);
        assertEquals(v1.length(), vscale.length(), "scale not working good");
    }

    @Test
    void dotProduct() {
        Vector v3 = new Vector(0, 3, -2);
        assertTrue(isZero(v1.dotProduct(v3)),
                "ERROR: dotProduct() for orthogonal vectors is not zero");

        assertTrue(isZero(v1.dotProduct(v2) + 28),
            "ERROR: dotProduct() wrong value");
    }

    @Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    void lengthSquared() {
        assertEquals(0, v1.lengthSquared() - 14,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    @Test
    void length() {
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),
                "ERROR: length() wrong value");
    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        if (vCopy != vCopyNormalize)
            fail("ERROR: normalize() function creates a new vector");
        if (!isZero(vCopyNormalize.length() - 1))
            fail("ERROR: normalize() result is not a unit vector");
    }

    @Test
    void normalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        if (u == v)
            fail("ERROR: normalizated() function does not create a new vector");
    }
}