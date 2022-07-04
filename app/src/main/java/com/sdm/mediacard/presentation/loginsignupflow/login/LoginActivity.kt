package com.sdm.mediacard.presentation.loginsignupflow.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sdm.mediacard.compose_ui.CircularLoading
import com.sdm.mediacard.compose_ui.ErrorTextView
import com.sdm.mediacard.compose_ui.LoginSignupTextField
import com.sdm.mediacard.utils.navigator.Screen

@Composable
fun LoginPage(navController: NavController, viewModel: LoginViewModel) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val stateOfUi by viewModel.state().collectAsState()

            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Cursive,
                    color = MaterialTheme.colors.secondary
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            LoginSignupTextField(
                value = stateOfUi.email,
                hint = "Email",
                true,
                updateKeyboardOptions = {
                    it.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                },
                modifier =Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp),
            ) {
                viewModel.onEvent(LoginScreenEvents.EmailChanged(it))
            }

            Spacer(modifier = Modifier.height(5.dp))

            ErrorTextView(
                str = stateOfUi.emailError, modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .padding(0.dp, 0.dp, 60.dp, 0.dp)
            )


            Spacer(modifier = Modifier.height(10.dp))

            LoginSignupTextField(
                value = stateOfUi.password,
                hint = "Password",
                true,
                updateKeyboardOptions = {
                    it.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                },
                modifier =Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp),
                visualTransformation = PasswordVisualTransformation()
            ) {
                viewModel.onEvent(LoginScreenEvents.PasswordChanged(it))
            }

            Spacer(modifier = Modifier.height(5.dp))

            ErrorTextView(
                str = stateOfUi.passwordError, modifier =Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .padding(0.dp, 0.dp, 60.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.onEvent(LoginScreenEvents.SubmitClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
                shape = RoundedCornerShape(50.dp),
            ) {
                Text(text = "Login")
            }
            ErrorTextView(
                str = stateOfUi.APIError, modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .padding(0.dp, 0.dp, 60.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString("Forgot password?"),
                onClick = { navController.navigate(Screen.ForgotPassword.route) },
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
            text = AnnotatedString("Sign up here"),
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

