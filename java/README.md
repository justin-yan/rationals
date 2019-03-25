rationals-java
==============

Due to a lack of operator overloading, the Java version of this library implements a pattern similar to the BigDecimal/BigInteger implementations, where there are various constructors from various types, and `add`, `subtract`, `multiply`, and `divide` are all implemented as regular java methods.

A Rational itself is represented as two BigIntegers, one for the numerator, and one for the denominator.  The canonical representation is always as the fully reduced fraction with a positive integer as the denominator.

These can then be compared, tested for equality, and rendered to various output types as desired.

```java
Rational a = Rational.fromString("3.2");
Rational b = Rational.fromString("5.1");
Rational c = Rational.fromString("16.32");
assertEquals(c, a.multiply(b)); // True
```

## Usage

TODO: Until a release strategy is configured, you can just copy the module into your existing codebase.

## Developing

- `mvn package` will test and build the project.
- We follow Google Java style, which is enforced via the `fmt-maven-plugin`
    - You can either integrate it into your IDE or run it on the [command line](https://github.com/coveo/fmt-maven-plugin#command-line) to reformat your code.


### Verification

All of the following verification techniques are integrated into the build.

- Unit tests handled by junit5.
    - Jacoco for code coverage statistics, with 100% coverage required.
    - [jqwik](https://jqwik.net/docs/current/user-guide.html#creating-a-property) is used for property based testing.
- Static analysis handled by Spotbugs, with a clean run required.
- CI is handled by TravisCI with Codecov.io on all PRs.
