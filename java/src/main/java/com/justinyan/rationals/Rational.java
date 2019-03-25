package com.justinyan.rationals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

public class Rational implements Comparable<Rational> {

  private BigInteger numerator;
  private BigInteger denominator;

  public Rational(BigInteger numerator, BigInteger denominator) throws ArithmeticException {
    // Maintain invariants of fully reduced fraction + always positive denominator as canonical
    // representation.
    BigInteger gcd = numerator.gcd(denominator);
    if (denominator.signum() == -1) {
      this.numerator = numerator.divide(gcd).negate();
      this.denominator = denominator.divide(gcd).negate();
    } else if (denominator.signum() == 0) {
      throw new ArithmeticException();
    } else {
      this.numerator = numerator.divide(gcd);
      this.denominator = denominator.divide(gcd);
    }
  }

  public String toString() {
    return numerator.toString() + " / " + denominator.toString();
  }

  @Override
  public boolean equals(Object o) {
    // If the object is compared with itself then return true
    if (o == this) {
      return true;
    }

    /* Check if o is an instance of Complex or not
    "null instanceof [type]" also returns false */
    if (!(o instanceof Rational)) {
      return false;
    }

    // typecast o to Complex so that we can compare data members
    Rational c = (Rational) o;

    // Compare the data members and return accordingly
    return numerator.equals(c.numerator) && denominator.equals(c.denominator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numerator.hashCode(), denominator.hashCode());
  }

  @Override
  public int compareTo(Rational otherRational) {
    BigInteger denomGcd = denominator.gcd(otherRational.denominator);
    BigInteger factor1 = otherRational.denominator.divide(denomGcd);
    BigInteger factor2 = denominator.divide(denomGcd);

    return numerator.multiply(factor1).compareTo(otherRational.numerator.multiply(factor2));
  }

  public Rational add(Rational addend) {
    BigInteger denomGcd = denominator.gcd(addend.denominator);
    BigInteger factor1 = addend.denominator.divide(denomGcd);
    BigInteger factor2 = denominator.divide(denomGcd);

    // Find the LCM of the denominator, multiply numerators by sufficient amount, add them together.
    BigInteger newNumerator = numerator.multiply(factor1).add(addend.numerator.multiply(factor2));
    Rational sum = new Rational(newNumerator, factor1.multiply(factor2).multiply(denomGcd));
    return sum;
  }

  public Rational subtract(Rational subtrahend) {
    BigInteger denomGcd = denominator.gcd(subtrahend.denominator);
    BigInteger factor1 = subtrahend.denominator.divide(denomGcd);
    BigInteger factor2 = denominator.divide(denomGcd);

    // Find the LCM of the denominator, multiply numerators by sufficient amount, subtract them.
    BigInteger newNumerator =
        numerator.multiply(factor1).subtract(subtrahend.numerator.multiply(factor2));
    Rational difference = new Rational(newNumerator, factor1.multiply(factor2).multiply(denomGcd));
    return difference;
  }

  public Rational multiply(Rational factor) {
    // Multiply numerators and denominators.
    Rational product =
        new Rational(
            numerator.multiply(factor.numerator), denominator.multiply(factor.denominator));
    return product;
  }

  public Rational divide(Rational divisor) {
    // Multiply numerator1xdenominator2 and vice versa.
    Rational quotient =
        new Rational(
            numerator.multiply(divisor.denominator), denominator.multiply(divisor.numerator));
    return quotient;
  }

  public BigDecimal render(int numberOfDigitsAfterDecimalPoint, RoundingMode roundingMode) {
    return new BigDecimal(numerator)
        .divide(new BigDecimal(denominator), numberOfDigitsAfterDecimalPoint, roundingMode);
  }

  public static Rational fromInt(Integer sourceInt) {
    return new Rational(BigInteger.valueOf(sourceInt), BigInteger.ONE);
  }

  public static Rational fromLong(Long sourceLong) {
    return new Rational(BigInteger.valueOf(sourceLong), BigInteger.ONE);
  }

  public static Rational fromBigInteger(BigInteger sourceBigInteger) {
    return new Rational(sourceBigInteger, BigInteger.ONE);
  }

  public static Rational fromBigDecimal(BigDecimal sourceBigDecimal) {
    return new Rational(
        sourceBigDecimal.unscaledValue(), BigInteger.TEN.pow(sourceBigDecimal.scale()));
  }

  public static Rational fromString(String sourceString) {
    return fromBigDecimal(new BigDecimal(sourceString));
  }

  public static Rational fromFloat(Float sourceFloat) {
    return fromString(Float.toString(sourceFloat));
  }

  public static Rational fromDouble(Double sourceDouble) {
    return fromString(Double.toString(sourceDouble));
  }

  public String serialize() {
    return "1:".concat(numerator.toString()).concat("/").concat(denominator.toString());
  }

  public static Rational deserialize(String sourceString) {
    Rational deser;
    String[] split = sourceString.split(":", 2);
    switch (split[0]) {
      case "1":
        String[] rawRat = split[1].split("/", 2);
        deser = new Rational(new BigInteger(rawRat[0]), new BigInteger(rawRat[1]));
        break;
      default:
        throw new UnsupportedOperationException();
    }
    return deser;
  }
}
