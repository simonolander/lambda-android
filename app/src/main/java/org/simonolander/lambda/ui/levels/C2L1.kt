package org.simonolander.lambda.ui.levels
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.material.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import org.simonolander.lambda.data.*
//import org.simonolander.lambda.ui.ChatView
//import org.simonolander.lambda.ui.QuestionView
//import org.simonolander.lambda.ui.theme.LambdaTheme
//
//@Composable
//fun C2L1View(function: () -> Unit) {
//    val dialog = DialogBuilder()
//        .message("Welcome back!")
//        .message("Today we're going to talk about booleans.")
//        .message("Are you familiar with booleans in programming?")
//        .question(
//            "Are you familiar with booleans in programming?",
//            "Yes" to DialogBuilder()
//                .message("Good!")
//                .message("You probably agree that the main use of a boolean is deciding between two outcomes.")
//                .build()!!,
//            "No" to DialogBuilder()
//                .message("Not a problem!")
//                .message("You're probably familiar with numbers, and that numbers can have values like 1, 2, 3, and so on.")
//                .message("A boolean can have only two values: true or false.")
//                .message("They are often used to decide between two outcomes. A bartender bot may have the following piece of logic built in:")
//                .message("if\n\tclient_is_over_18\nthen\n\tserve beer\nelse\n\tserve orange juice")
//                .message("In this example, client_is_over_18 refers to a boolean value: true if the client is indeed over 18 years old, false otherwise.")
//                .message("λ-calculus does not directly provide us with values for true and false.")
//                .message("We will have to construct them ourselves.")
//                .message("Instead of thinking about what true and false is, we're going to think about what they're useful for: choosing between two outcomes.")
//                .message("In the if-then-else statement above, we choose between two things to serve our client.")
//                .message("If client_is_over_18 is true, we choose the first thing: beer.")
//                .message("If client_is_over_18 is false, we choose the second thing: orange juice.")
//                .message("Thinking of booleans as functions that choose between two values allows us to express them as lambdas.")
//                .message("The boolean true is a function that when presented with two options chooses the first one: λx y. x")
//                .message("How do you think that we should write false?")
//                .build()!!,
//        )
//    val chatViewModel = remember { ChatViewModel(dialog) }
//    val currentDialog = chatViewModel.currentDialog
//    LambdaTheme {
//        Column {
//            ChatView(
//                chatViewModel.chatMessages,
//                chatViewModel.typing,
//                Modifier.fillMaxHeight(if (currentDialog is Question) 0.5f else 1.0f),
//                chatViewModel::nextMessage
//            )
//            if (currentDialog is Question) {
//                QuestionView(question = currentDialog, onResponse = chatViewModel::respond)
//            }
//        }
//    }
//}
//
//@Composable
//@Preview
//private fun Preview() {
//    Surface {
//        C2L1View {}
//    }
//}
//
//
