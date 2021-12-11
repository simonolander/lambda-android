package org.simonolander.lambda.engine

// Boolean logic
val TRUE = "true" to parse("λa b. a")
val FALSE = "false" to parse("λa b. b")
val NOT = "not" to parse("λa. a false true")
val AND = "and" to parse("λa b. a b a")
val OR = "or" to parse("λa b. a a b")
val XOR = "xor" to parse("λa b. and (or a b) (not (and a b))")
val EQ = "eq" to parse("λa b. not (xor a b)")

// Church numerals
val SUCC = "succ" to parse("λa f x. f (a f x)")
val ADD = "add" to parse("λa b f x. a f (b f x)")
val MULT = "mult" to parse("λa b f. a (b f)")
val POW = "pow" to parse("λa b. b a")
val PRED = "pred" to parse("λn f x. n (λg h. h (g f)) (λu.x) λu.u")
val SUB = "sub" to parse("λa b. b pred a")
val ZERO = "0" to parse("λf x. x")
val ONE = "1" to parse("λf x. f x")
val TWO = "2" to parse("λf x. f (f x)")
val IS_ZERO = "isZero" to parse("λn. n (λx. false) true")
val LEQ = "leq" to parse("λa b. isZero (sub a b)")

fun churchNumeral(n: Int): Pair<String, Expression> {
    assert(n >= 0)
    return "$n" to parse("λf x." + "f(".repeat(n) + "x" + ")".repeat(n))
}

// Church pairs
val PAIR = "pair" to parse("λa b c. c a b")
val FST = "fst" to parse("λp. p true")
val SND = "snd" to parse("λp. p false")