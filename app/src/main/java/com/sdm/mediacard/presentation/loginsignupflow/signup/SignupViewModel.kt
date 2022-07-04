package com.sdm.mediacard.presentation.loginsignupflow.signup

import androidx.lifecycle.viewModelScope
import com.sdei.base.ViewModelG
import com.sdei.base.network.Resource
import com.sdei.base.validation.DataValidator
import com.sdei.base.validation.ValidationResult
import com.sdei.domaindata.data.remote.apipayload.SignupPayload
import com.sdei.domaindata.domain.repository.local.PreferencesHelper
import com.sdei.domaindata.domain.usecases.user.UserSignup
import com.sdm.mediacard.di.EmailValidatorInject
import com.sdm.mediacard.di.MobileValidatorInject
import com.sdm.mediacard.di.NamedValidatorInject
import com.sdm.mediacard.di.PasswordValidatorInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userSignup: UserSignup,
    private val protoPref: PreferencesHelper,
    @EmailValidatorInject private val emailValidator: DataValidator<String>,
    @PasswordValidatorInject private val passwordValidator: DataValidator<String>,
    @NamedValidatorInject private val nameValidator: DataValidator<String>,
    @MobileValidatorInject private val mobileValidator: DataValidator<String>
) : ViewModelG<SignupFormState>() {

    override val mutableState: MutableStateFlow<SignupFormState> =
        MutableStateFlow(SignupFormState())

    fun onEvent(event: SignupScreenEvents) {
        viewModelScope.launch {
            when (event) {
                is SignupScreenEvents.FirstNameChanged   -> {
                    updateUIState {
                        it.copy(firstName = event.value)
                    }
                }
                is SignupScreenEvents.LastNameChanged    -> {
                    updateUIState {
                        it.copy(lastName = event.value)
                    }
                }
                is SignupScreenEvents.UserNameChanged    -> {
                    updateUIState {
                        it.copy(username = event.value)
                    }
                }
                is SignupScreenEvents.EmailChanged       -> {
                    updateUIState {
                        it.copy(email = event.value)
                    }
                }
                is SignupScreenEvents.PasswordChanged    -> {
                    updateUIState {
                        it.copy(password = event.value)
                    }
                }
                is SignupScreenEvents.RePasswordChanged  -> {
                    updateUIState {
                        it.copy(repeatPassword = event.value)
                    }
                }
                is SignupScreenEvents.CountryCodeChanged -> {
                    updateUIState {
                        it.copy(countryCode = event.value)
                    }
                }
                is SignupScreenEvents.MobileChanged      -> {
                    updateUIState {
                        it.copy(mobile = event.value)
                    }
                }
                is SignupScreenEvents.SubmitClicked      -> {
                    validationWithoutFlow()
                }
            }
        }
    }

    private suspend fun validationWithoutFlow() {
        val fname = state().value.firstName
        val lname = state().value.lastName
        val username = state().value.username
        val email = state().value.email
        val countryCode = state().value.countryCode
        val mobile = state().value.mobile
        val password = state().value.password
        val repassword = state().value.repeatPassword

        val validFname = nameValidator.isValid(fname)
        val validLname = nameValidator.isValid(lname)
        val validUsername = nameValidator.isValid(username)
        val validEmail = emailValidator.isValid(email)
        val validPassword = passwordValidator.isValid(password)
        val validMobileNumber = mobileValidator.isValid(mobile)

        val validCountryCode = if (countryCode.isEmpty()) {
            ValidationResult.Failure("Please select country code")
        }
        else {
            ValidationResult.Success
        }

        val validRePassword = if (repassword.isEmpty()) {
            ValidationResult.Failure("Please enter re-password")
        }
        else if (repassword != password) {
            ValidationResult.Failure("Password and Re-password are not same!!")
        }
        else {
            ValidationResult.Success
        }

        val ifFailure = listOf<ValidationResult>(
            validFname, validLname, validUsername,
            validEmail, validCountryCode, validMobileNumber, validPassword, validRePassword
        ).any { it is ValidationResult.Failure }

        if (ifFailure) {
            updateUIState {
                it.copy(
                    firstNameError = if (validFname is ValidationResult.Failure) {
                        validFname.errorMsg
                    }
                    else {
                        null
                    },
                    lastNameError = if (validLname is ValidationResult.Failure) {
                        validLname.errorMsg
                    }
                    else {
                        null
                    },
                    usernameError = if (validUsername is ValidationResult.Failure) {
                        validUsername.errorMsg
                    }
                    else {
                        null
                    },
                    emailError = if (validEmail is ValidationResult.Failure) {
                        validEmail.errorMsg
                    }
                    else {
                        null
                    },
                    countryCodeError = if (validCountryCode is ValidationResult.Failure) {
                        validCountryCode.errorMsg
                    }
                    else {
                        null
                    },
                    mobileError = if (validMobileNumber is ValidationResult.Failure) {
                        validMobileNumber.errorMsg
                    }
                    else {
                        null
                    },
                    passwordError = if (validPassword is ValidationResult.Failure) {
                        validPassword.errorMsg
                    }
                    else {
                        null
                    },
                    repeatPasswordError = if (validRePassword is ValidationResult.Failure) {
                        validRePassword.errorMsg
                    }
                    else {
                        null
                    }
                )
            }
        }
        else {
            updateUIState {
                it.copy(
                    firstNameError = null,
                    lastNameError = null,
                    usernameError = null,
                    emailError = null,
                    countryCodeError = null,
                    mobileError = null,
                    passwordError = null,
                    repeatPasswordError = null
                )
            }


            userSignup(
                SignupPayload(
                    email = email,
                    firstName = fname, lastName = lname,
                    countryCode = countryCode, mobile = mobile,
                    username = username, password = password
                )
            ).onEach {
                when (it) {
                    is Resource.Loading -> {
                        updateUIState { state ->
                            state.copy(showLoading = true)
                        }
                    }
                    is Resource.Error   -> {
                        updateUIState { state ->
                            state.copy(
                                showLoading = false,
                                APIError = it.message
                            )
                        }
                    }
                    is Resource.Success -> {
                        updateUIState { state ->
                            state.copy(
                                showLoading = false,
                                loginSuccessful = it.data
                            )
                        }
                    }
                }
            }.collect()
        }
    }


}