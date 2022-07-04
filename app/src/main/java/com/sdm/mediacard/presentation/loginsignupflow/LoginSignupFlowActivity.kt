package com.sdm.mediacard.presentation.loginsignupflow

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import com.sdm.mediacard.R
import com.sdei.base.BaseActivity
import com.sdm.mediacard.databinding.ActivityLoginBinding
import com.sdm.mediacard.presentation.loginsignupflow.forgotpassword.ForgotPasswordPage
import com.sdm.mediacard.presentation.loginsignupflow.forgotpassword.ForgotPasswordViewModel
import com.sdm.mediacard.presentation.loginsignupflow.login.LoginPage
import com.sdm.mediacard.presentation.loginsignupflow.login.LoginViewModel
import com.sdm.mediacard.presentation.loginsignupflow.signup.SignUpPage
import com.sdm.mediacard.presentation.loginsignupflow.signup.SignupViewModel
import com.sdm.mediacard.utils.navigator.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSignupFlowActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_login
    override var binding: ActivityLoginBinding
        get() = setUpBinding()
        set(value) {}

    private val loginViewModel: LoginViewModel by viewModels()
    private val signupViewModel: SignupViewModel by viewModels()
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()


    override fun onCreate() {
        binding.composeLogin.apply {
            setContent {
                Mdc3Theme {
                    StartLoginJourney()
                }
            }
        }
    }

    @Composable
    fun StartLoginJourney() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = Screen.Login.route) {
            composable(Screen.Login.route) {
                LoginPage(navController,loginViewModel)
            }
            composable(route = Screen.Signup.route) {
                SignUpPage(navController,signupViewModel)
            }
            composable(route = Screen.ForgotPassword.route) {
                ForgotPasswordPage(navController,forgotPasswordViewModel)
            }
        }
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginSignupFlowActivity::class.java))
        }
    }

}