rationals
=========

Rationals is a numeric type designed to give infinite precision arithmetic for the four basic math operations (`+`, `-`, `*`, `/`), which is useful for use cases like accounting or financial math, which frequently struggle with rounding and/or precision loss as they perform division.

## Current Landscape

Many languages actually support "arbitrary precision" numeric types, such as Java's BigDecimal.  Interestingly, the problem with this particular kind of type is in the distinction between *infinite* precision and *arbitrary* precision.  Namely, if you divide `1.0 / 3.0`, even a BigDecimal will not give you `.333....` - rather, it will allow you to specify how many repeating 3s you want, with the rest rounded off.

This loss of precision can be extremely difficult to reason about as multiplication and division occur multiple times, and it requires any system performing arithmetic to have a shared and consistent rounding policy.

## Design

We believe there's a better way to do this.  The Rational Numbers form an algebraic Field over the four basic math operations, which means that any system can perform infinite-precision math on a rational number without worrying about precision loss at all, and that the only place you need to actually have a rounding policy with any consistency are the *edges* of your system, when an actual decimal number needs to be rendered for some purpose.

At a high level, we simply need to convert inbound Decimals to Rationals which will always be lossless, and to convert outbound Rationals to Decimals which is the only place we need policies regarding precision and rounding:

    Decimal -> Rational -> Rational -> Rational -> Rational -> Decimal

This informs the approach and featureset we build:

- A `Rational`s class that provides lossless precision operations for the class of Rational numbers.
- Conversion utilities to/from all major numeric types, along with precision specification mechanisms for the precise handling of precision loss and rounding policies.
- A string serialization format and SerDe utilities to allow for cross-language transport.

The general idea is to use infinite-precision *integer* formats in order to store numerator/denominator values and preserving those through all calculations, where the canonical representation is always the *fully reduced* fraction with the denominator *always* being a positive Integer.

Only when an actual decimal needs to be *rendered* do we perform the division and rounding as needed.

The serialization format simply uses String in order to avoid precision loss:

    - `<version#>:<payload>`
    - Version 1:
        - `1:<numerator>/<denominator>`
        - E.g., `.5` would be `1:1/2`
