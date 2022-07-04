package com.sdm.mediacard.compose_ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.data.CountryData
import com.togitech.ccp.data.utils.getDefaultLangCode
import com.togitech.ccp.data.utils.getLibCountries

@Composable
fun CircularLoading(show: Boolean) {
    if (show) {
        CircularProgressIndicator()
    }
}


@Composable
fun ErrorTextView(str: Any?, fontSize : TextUnit = 9.sp, modifier: Modifier = Modifier
    .wrapContentHeight()
    .wrapContentWidth()) {
    val stringError = str?.let {
        when (str) {
            is String -> {
                str.toString()
            }
            is Int    -> {
                stringResource(str)
            }
            else      -> {
                ""
            }
        }
    } ?: ""

    Text(
        modifier = modifier,
        text = stringError,
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colors.error
        )
    )
}


@Composable
fun BasicButton(modifier: Modifier,txt:String, onClickAction:()->Unit)
{
    Button(
        onClick = onClickAction,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Text(text = txt)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginSignupTextField(value : String, hint: String, isSingleLine:Boolean,
                         visualTransformation: VisualTransformation = VisualTransformation.None,
                         updateKeyboardOptions: (KeyboardOptions) -> KeyboardOptions = {it},
                         modifier: Modifier, onValueChanged:(str:String)->Unit) {
    val currentKeyboard = LocalSoftwareKeyboardController.current

    var keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next,capitalization = KeyboardCapitalization.Sentences)
    keyboardOptions = updateKeyboardOptions(keyboardOptions)

    OutlinedTextField(
        modifier = modifier,
        singleLine = isSingleLine,
        label = { Text(text = hint) },
        value = value,
        keyboardActions = KeyboardActions(
            onDone = { currentKeyboard?.hide()}),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChanged
    )
}

// With Country Phone
@Composable
fun SelectCountryWithCountryCode(modifier: Modifier=Modifier.padding(vertical = 0.dp, horizontal = 0.dp),
        onValueChanged:(str:String)->Unit,onCountryPick:(str:CountryData)->Unit) {
    val context = LocalContext.current
   // var phoneCode by rememberSaveable { mutableStateOf(getDefaultPhoneCode(context)) }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val isValidPhone by remember { mutableStateOf(true) }
  //  var verifyText by remember { mutableStateOf("") }
    val defaultCountry = getLibCountries().single {
        it.countryCode == defaultLang
    }
    onCountryPick(defaultCountry)//return default first

    TogiCountryCodePicker(
        rowPadding = modifier,
        pickedCountry = {
            //phoneCode = it.countryPhoneCode
            defaultLang = it.countryCode
            onCountryPick(it)
        },
        defaultCountry = defaultCountry,
        focusedBorderColor = MaterialTheme.colors.primary,
        dialogAppBarTextColor = Color.Black,
        dialogAppBarColor = Color.White,
        error = isValidPhone,
        text = phoneNumber.value,
        onValueChange = {
            phoneNumber.value = it
            onValueChanged(it)
        }
    )
   /* val fullPhoneNumber = "$phoneCode${phoneNumber.value}"
    val checkPhoneNumber = checkPhoneNumber(
        phone = phoneNumber.value,
        fullPhoneNumber = fullPhoneNumber,
        countryCode = defaultLang
    )*/
}



@Composable
fun CustomDialog(
    title:String,
    text:String,
    onConfirmClicked: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true,
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // TITLE
                Text(text = title, style = MaterialTheme.typography.subtitle1)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body2
                    )
                }

                Button(onClick = onConfirmClicked, Modifier.fillMaxWidth().padding(20.dp)) {
                    Text(text = "OK")
                }
            }
        }
    }
}