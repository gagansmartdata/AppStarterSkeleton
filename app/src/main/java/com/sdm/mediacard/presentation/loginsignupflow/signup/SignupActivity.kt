package com.sdm.mediacard.presentation.loginsignupflow.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sdm.mediacard.compose_ui.BasicButton
import com.sdm.mediacard.compose_ui.ErrorTextView
import com.sdm.mediacard.compose_ui.LoginSignupTextField
import com.sdm.mediacard.compose_ui.SelectCountryWithCountryCode


@Composable
fun SignUpPage(navController: NavController, viewModel: SignupViewModel) {
    val scrollState = rememberScrollState()
    Box(Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val stateOfUi by viewModel.state().collectAsState()

            if (stateOfUi.emailError!=null) {
                ErrorTextView(stateOfUi.emailError,40.sp)
            }

            Text(text = "Sign Up", style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive, color = MaterialTheme.colors.secondary))

            val commonTextfieldModifier=Modifier.fillMaxWidth().padding(50.dp,0.dp,50.dp,0.dp)
            val commonSpacerModifier=Modifier.height(10.dp)

            LoginSignupTextField(
                value = stateOfUi.firstName,
                hint = "First Name",
                true,
                modifier =commonTextfieldModifier,
            ) {
                viewModel.onEvent(SignupScreenEvents.FirstNameChanged(it))
            }

            Spacer(modifier = commonSpacerModifier)

            LoginSignupTextField(
                value = stateOfUi.lastName,
                hint = "last Name",
                true,
                modifier = commonTextfieldModifier
            ) {
                viewModel.onEvent(SignupScreenEvents.LastNameChanged(it))
            }

            Spacer(modifier = commonSpacerModifier)

            LoginSignupTextField(
                value = stateOfUi.username,
                hint = "username",
                true,
                modifier = commonTextfieldModifier
            ) {
                viewModel.onEvent(SignupScreenEvents.UserNameChanged(it))
            }
            Spacer(modifier = commonSpacerModifier)
            LoginSignupTextField(
                value = stateOfUi.email,
                hint = "email",
                true,
                updateKeyboardOptions = {
                    it.copy(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    )
                },
                modifier =commonTextfieldModifier
            ) {
                viewModel.onEvent(SignupScreenEvents.EmailChanged(it))
            }
            Spacer(modifier =commonSpacerModifier)
            SelectCountryWithCountryCode(Modifier.padding(vertical = 8.dp, horizontal = 50.dp),
            {
                viewModel.onEvent(SignupScreenEvents.MobileChanged(it))
            },{
                    viewModel.onEvent(SignupScreenEvents.CountryCodeChanged(it.countryPhoneCode.replace("+","")))
                })

            LoginSignupTextField(
                value = stateOfUi.password,
                hint = "Password",
                true,
                visualTransformation = PasswordVisualTransformation(),
                updateKeyboardOptions = {
                    it.copy(
                        keyboardType = KeyboardType.Password
                    )
                },
                modifier = commonTextfieldModifier
            ) {
                viewModel.onEvent(SignupScreenEvents.PasswordChanged(it))
            }
            Spacer(modifier =commonSpacerModifier)
            LoginSignupTextField(
                value = stateOfUi.repeatPassword,
                hint = "Re-Password",
                isSingleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                updateKeyboardOptions = {
                    it.copy(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    )
                },
                modifier = commonTextfieldModifier
            ) {
                viewModel.onEvent(SignupScreenEvents.RePasswordChanged(it))
            }
            Spacer(modifier = commonSpacerModifier.height(20.dp))
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp, 0.dp, 50.dp, 0.dp)
                    .height(50.dp), txt = "Sign Up"
            ){
                viewModel.onEvent(SignupScreenEvents.SubmitClicked)
            }
            Spacer(modifier = commonSpacerModifier)
            BasicButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp, 0.dp, 50.dp, 0.dp)
                    .height(50.dp), txt = "Go Back"
            ){
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}