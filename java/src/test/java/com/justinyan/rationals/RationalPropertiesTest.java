package com.justinyan.rationals;

import net.jqwik.api.*;

import java.math.BigDecimal;

public class RationalPropertiesTest {

  @Property
  boolean serdeIsBijective(@ForAll BigDecimal sourceBD) {
    Rational sourceRat = Rational.fromBigDecimal(sourceBD);
    return sourceRat.equals(Rational.deserialize(sourceRat.serialize()));
  }

  @Property
  boolean additionIsCommutative(@ForAll BigDecimal sourceBD1, @ForAll BigDecimal sourceBD2) {
    Rational sourceRat1 = Rational.fromBigDecimal(sourceBD1);
    Rational sourceRat2 = Rational.fromBigDecimal(sourceBD2);
    return sourceRat1.add(sourceRat2).equals(sourceRat2.add(sourceRat1));
  }

  @Property
  boolean multiplicationIsCommutative(@ForAll BigDecimal sourceBD1, @ForAll BigDecimal sourceBD2) {
    Rational sourceRat1 = Rational.fromBigDecimal(sourceBD1);
    Rational sourceRat2 = Rational.fromBigDecimal(sourceBD2);
    return sourceRat1.multiply(sourceRat2).equals(sourceRat2.multiply(sourceRat1));
  }


}
