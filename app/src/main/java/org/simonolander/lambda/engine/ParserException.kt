package org.simonolander.lambda.engine

import java.lang.IllegalArgumentException

class ParserException(override val message: String) : IllegalArgumentException(message)
