package com.sdm.mediacard.utils.navigator

sealed class Screen(val route: String) {
	object Login: Screen("login")
	object Signup: Screen("signup")
	object ForgotPassword: Screen("forgot_password")
}