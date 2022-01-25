package org.simonolander.lambda.engine

// Basic
val ID = "id" to parse("λ x. x")
val CONST = "const" to parse("λx y. x")
val KITE = "ki" to parse("λx y. y")
val APPLY = "apply" to parse("λf x. f x")
val FLIP = "flip" to parse("λf a b. f b a")

// Boolean logic
val TRUE = "true" to parse("λa b. a")
val FALSE = "false" to parse("λa b. b")
val NOT = "not" to parse("λa. a false true")
val AND = "and" to parse("λa b. a b a")
val OR = "or" to parse("λa b. a a b")
val IF = "if" to parse("λp x y. p x y")
val XOR = "xor" to parse("λa b. a (not b) b") // Alt. λa b. and (or a b) (not (and a b))

// Church numerals
val SUCC = "succ" to parse("λa f x. f (a f x)")
val ADD = "add" to parse("λa b f x. a f (b f x)")
val MULT = "mult" to parse("λa b f. a (b f)")
val POW = "pow" to parse("λa b. b a")
val PRED = "pred" to parse("λn f x. n (λg h. h (g f)) (λu.x) λu.u")
val SUB = "sub" to parse("λa b. b pred a")
val ZERO = "zero" to parse("λn. n (λx. false) true")
val LEQ = "leq" to parse("λa b. zero (sub a b)")
val EQ = "eq" to parse("λa b. and (leq a b) (leq b a)")

fun churchNumeral(n: Int): Pair<String, Expression> {
    assert(n >= 0)
    return "$n" to parse("λf x." + "f(".repeat(n) + "x" + ")".repeat(n))
}

fun churchNumerals(n: Int): Array<Pair<String, Expression>> {
    assert(n >= 0)
    return (0..n).map(::churchNumeral).toTypedArray()
}

// Church pairs
val PAIR = "pair" to parse("λa b f. f a b")
val FST = "fst" to parse("λp. p true")
val SND = "snd" to parse("λp. p false")
val NULL = "null" to parse("λx. true")
val IS_NULL = "isNull" to parse("λp. p (λ a b. false)")
