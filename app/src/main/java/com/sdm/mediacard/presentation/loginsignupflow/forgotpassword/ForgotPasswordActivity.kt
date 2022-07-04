package com.sdm.mediacard.presentation.loginsignupflow.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sdm.mediacard.compose_ui.BasicButton
import com.sdm.mediacard.compose_ui.CircularLoading
import com.sdm.mediacard.compose_ui.CustomDialog
import com.sdm.mediacard.compose_ui.ErrorTextView
import com.sdm.mediacard.presentation.loginsignupflow.signup.SignupScreenEvents
import com.sdm.mediacard.utils.navigator.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordPage(navController: NavController, viewModel: ForgotPasswordViewModel) {
    val currentKeyboard = LocalSoftwareKeyboardController.current
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val stateOfUi by viewModel.state().collectAsState()

            if (stateOfUi.showSuccessDialog) {
                CustomDialog(
                    title = "Request Submitted!",
                    text = stateOfUi.requestSubmitted.toString(),
                    onConfirmClicked = {
                        viewModel.onEvent(ForgotPasswordEvents.ShowHideDialog(false))
                        navController.popBackStack()
                    }
                ){
                    navController.popBackStack()//go back after dialog is closed
                }
            }


            Text(
                text = "Forgot Password",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive,
                    color = MaterialTheme.colors.secondary
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                isError = stateOfUi.emailError != null,
                modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp),
                singleLine = true,
                label = { Text(text = "Email") },
                value = stateOfUi.email,
                keyboardActions = KeyboardActions(
                    onDone = {
                        currentKeyboard?.hide()
                        viewModel.onEvent(ForgotPasswordEvents.SubmitClicked)
                    }),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordEvents.EmailChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            ErrorTextView(
                str = stateOfUi.emailError, modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .padding(0.dp, 0.dp, 60.dp, 0.dp)
            )


            Spacer(modifier = Modifier.height(20.dp))

            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)
                    .height(50.dp),
                txt = "Submit"

            ){
                viewModel.onEvent(ForgotPasswordEvents.SubmitClicked)
            }

            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString("Go Back"),
                onClick = { navController.popBackStack() },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                )
            )

            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularLoading(stateOfUi.showLoading)
            }
        }

        ClickableText(
            text = AnnotatedString("Create a new Account"),
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            onClick = { navController.navigate(Screen.Signup.route) },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.primary
            )
        )
    }

}

