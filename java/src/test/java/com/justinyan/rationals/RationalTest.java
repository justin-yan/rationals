package com.justinyan.rationals;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

public class RationalTest {

  @Test
  public void sanityTest() {
    assertEquals(Rational.fromInt(1), Rational.fromInt(1));
  }

  @Test
  public void constructorTest() {
    Rational whole = Rational.fromString("1");
    Rational part = Rational.fromString("2.1");
    assertEquals(whole, Rational.fromInt(1));
    assertEquals(whole, Rational.fromLong(1L));
    assertEquals(whole, Rational.fromBigInteger(BigInteger.ONE));
    assertEquals(part, Rational.fromFloat(2.1f));
    assertEquals(part, Rational.fromDouble(2.1));
    assertThrows(NumberFormatException.class, () -> Rational.fromString("blahblah"));
  }

  @Test
  public void fieldOperationsTest() {
    Rational a = Rational.fromString("3.2");
    Rational b = Rational.fromString("5.1");
    Rational c = Rational.fromString("16.32");
    Rational d = Rational.fromString("8.3");
    Rational e = Rational.fromString("3.20");
    Rational f = Rational.fromString("1.5");
    Rational g = Rational.fromString(".75");
    Rational h = Rational.fromString("1.25");
    assertEquals(c, a.multiply(b));
    assertEquals(b, c.divide(a));
    assertEquals(d, a.add(b));
    assertEquals(a, d.subtract(b));
    assertEquals(a.compareTo(b), -1);
    assertEquals(a, a);
    assertNotEquals(a, "random");
    assertEquals(a, e);
    assertNotEquals(a, b);
    assertNotEquals(f, g);
    assertNotEquals(g, h);
    assertEquals(a.hashCode(), e.hashCode());
  }

  @Test
  public void edgeCasesTest() {
    Rational a = Rational.fromString("0");
    Rational b = Rational.fromString("5.1");
    assertEquals(a, b.multiply(a));
    assertEquals(b, b.add(a));
    assertEquals(b, b.subtract(a));
    assertThrows(ArithmeticException.class, () -> b.divide(a));

    Rational c = Rational.fromString("-1");
    Rational d = Rational.fromString("-5.1");
    Rational e = Rational.fromString("-6.1");
    Rational f = Rational.fromString("-4.1");
    assertEquals(d, b.multiply(c));
    assertEquals(e, d.add(c));
    assertEquals(f, d.subtract(c));
    assertEquals(d, b.divide(c));
  }

  @Test
  public void renderTest() {
    Rational elt = Rational.fromString("1.444");
    assertEquals(elt.toString(), "361 / 250");
    assertEquals(elt.render(2, RoundingMode.DOWN).toString(), "1.44");
  }

  @Test
  public void serializationOtherTest() {
    assertThrows(UnsupportedOperationException.class, () -> Rational.deserialize("0:blah/blah"));
  }
}
