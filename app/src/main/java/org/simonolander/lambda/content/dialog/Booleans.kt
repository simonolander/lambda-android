package org.simonolander.lambda.content.dialog

import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.lambdaCalculus

val booleanDialog = run {
    DialogBuilder()
        .message("Hello!")
        .message("In this lesson we're going to be exploring booleans in $lambdaCalculus.")
        .message("A boolean is a type of data that can be one of two values: true or false.")
        .message("Booleans are used primarily for deciding between two outcomes.")
        .message("Like everything else in $lambdaCalculus, we represent booleans using functions.")
        .message("A boolean in $lambdaCalculus is simply a function, that when presented with two arguments, returns one of them.")
        .message("The boolean true chooses the first argument. False chooses the second.")
        .message("Please go ahead and write the true function for me.")
        .message("I'll give you a hint, you've already seen this function in a previous lesson.")
        .build()
}
