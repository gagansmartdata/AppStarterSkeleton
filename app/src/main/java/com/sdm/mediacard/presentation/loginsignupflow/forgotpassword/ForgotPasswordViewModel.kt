package com.sdm.mediacard.presentation.loginsignupflow.forgotpassword

import androidx.lifecycle.viewModelScope
import com.sdei.base.ViewModelG
import com.sdei.base.network.Resource
import com.sdei.base.validation.DataValidator
import com.sdei.base.validation.ValidationResult
import com.sdei.domaindata.domain.usecases.user.ForgotPassword
import com.sdm.mediacard.di.EmailValidatorInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPassword: ForgotPassword,
    @EmailValidatorInject private val emailValidator: DataValidator<String>
    ) : ViewModelG<ForgotPasswordFormState>() {

    override val mutableState: MutableStateFlow<ForgotPasswordFormState> =
        MutableStateFlow(ForgotPasswordFormState())

    fun onEvent(event: ForgotPasswordEvents) {
        viewModelScope.launch {
            when (event) {
                is ForgotPasswordEvents.EmailChanged    -> {
                    updateUIState {
                        it.copy(email = event.value)
                    }
                }
                is ForgotPasswordEvents.SubmitClicked   -> {
                    validateAndHitAPI()
                }
                is ForgotPasswordEvents.ShowHideDialog   -> {
                    updateUIState {
                        it.copy(showSuccessDialog = event.showDialog)
                    }
                }
            }
        }
    }


    private suspend fun validateAndHitAPI() {
        val email = state().value.email

        val validEmail = emailValidator.isValid(email)

        if (validEmail is ValidationResult.Failure) {
            updateUIState {
                it.copy(emailError = validEmail.errorMsg)
            }
        }
        else {
            updateUIState {
                it.copy(emailError = null)
            }

            forgotPassword(email).onEach {
                delay(1000)
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
                                emailError = it.message
                            )
                        }
                    }
                    is Resource.Success -> {
                        updateUIState { state ->
                            state.copy(
                                showLoading = false,
                                showSuccessDialog = true,
                                requestSubmitted = it.message
                            )
                        }
                    }
                }
            }.collect()
        }
    }
}
