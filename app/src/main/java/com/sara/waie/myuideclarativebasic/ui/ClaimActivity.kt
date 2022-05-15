package com.sara.waie.myuideclarativebasic.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.LifecycleOwner
import com.sara.waie.myuideclarativebasic.R
import com.sara.waie.myuideclarativebasic.model.*
import com.sara.waie.myuideclarativebasic.ui.theme.MyUiDeclarativeBasicTheme
import com.sara.waie.myuideclarativebasic.utils.ChoiceState
import com.sara.waie.myuideclarativebasic.utils.TextFieldState
import com.sara.waie.myuideclarativebasic.utils.Validators
import com.sara.waie.myuideclarativebasic.vm.ClainActivityViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "ClaimActivity"

@AndroidEntryPoint
class ClaimActivity : ComponentActivity(), LifecycleOwner {

    val mViewModel: ClainActivityViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainContent()
        }

    }

    /**
     * function to show main content of claim form screen
     * */
    @Composable
    fun mainContent() {
        MyUiDeclarativeBasicTheme {
            val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
            val coroutineScope = rememberCoroutineScope()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = R.string.screen_claim_form),
                                maxLines = 1
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {

                            }) {
                                Icon(Icons.Filled.ArrowBack, getString(R.string.con_des_back))
                            }
                        },
                        actions = {
                            TextButton(onClick = {
                                Log.d(TAG, " onButton Click ")
//                                if (mViewModel.claimFormState.validate()) {
                                if (mViewModel.submitForm()) {
                                    Log.d(TAG, " mViewModel.submitForm() sssssssssss")
                                    coroutineScope.launch {

                                        val snackbarResult =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = getString(R.string.form_submited_success_message),
                                                actionLabel = getString(R.string.submit_new_form)
                                            )
                                        when (snackbarResult) {
                                            SnackbarResult.Dismissed -> {
                                                //clear all
                                                clearForm()
                                            }
                                            SnackbarResult.ActionPerformed -> {
                                                clearForm()
                                            }
                                        }
                                    }

                                }
//                                }
                            }) {
                                Text(
                                    text = stringResource(id = R.string.action_submit).uppercase(
                                        Locale.getDefault()
                                    ),
                                    style = TextStyle(Color.White),

                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            }


                        })
                },
                content = {
                    Column {
                        val response = mViewModel.uiState.collectAsState().value
                        Card(elevation = 5.dp) {
                            claimMainTypeDropDown(response.Claims)
                        }
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            showForm()
                        }

                    }
                }
            )


        }
    }

    /**
     * compose function to show Dropdown to select type of Claim form
     * eg:Travel
     * **/
    @Composable
    fun claimMainTypeDropDown(claims: ArrayList<Claim> = arrayListOf()) {
        var expanded by remember { mutableStateOf(false) }
        var textfieldSize by remember { mutableStateOf(Size.Zero) }
        val icon = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = mViewModel.claimTypeSelected.collectAsState().value.Claimtype?.name ?: "",
                onValueChange = { },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    }
                    .clickable {
                        expanded = !expanded
                    },
                label = { Text(getString(R.string.select_claim_type), color = Color.Black) },
                trailingIcon = {
                    Icon(icon, "select claim type",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                claims.forEach { claim ->
                    DropdownMenuItem(onClick = {
                        mViewModel._claimTypeSelected.value = claim
                        expanded = false
                    }) {
                        Text(text = claim.Claimtype?.name!!)
                    }
                }
            }
        }
    }

    /**
     * compose function to show selected type of form with dynamic fields
     * */
    @Composable
    fun showForm(selectedClaim: Claim = mViewModel.claimTypeSelected.collectAsState().value) {
        if (selectedClaim.Claimtypedetails.size > 0) {
            mViewModel.claimFormState.cleanForm()

            selectedClaim.Claimtypedetails.forEach { claimtypedetail ->
                when (claimtypedetail.claimfield?.type) {
                    ClaimFieldType.DROPDOWN.claimType -> {
                        var validators = mutableListOf<Validators>()
                        if (claimtypedetail.claimfield?.required.equals("1")) {
                            validators.add(Validators.Required())
                        }
                        mViewModel.claimFormState.addField(
                            claimtypedetail.claimfieldId.toString()!!,
                            ChoiceState(
                                claimtypedetail.claimfieldId.toString()!!,
                                validators
                            )
                        )
                        addFieldDropDown(claimtypedetail)
                    }
                    ClaimFieldType.SINGLELINE_TEXT.claimType -> {
                        var validators = mutableListOf<Validators>()
                        if (claimtypedetail.claimfield?.required.equals("1")) {
                            validators.add(Validators.Required())
                        }
                        mViewModel.claimFormState.addField(
                            claimtypedetail.claimfieldId.toString()!!,
                            TextFieldState(
                                claimtypedetail.claimfieldId.toString()!!,
                                validators = validators
                            )
                        )
                        addFieldInputText(claimtypedetail, false, false)
                    }
                    ClaimFieldType.SINGLELINE_TEXT_ALLCAPS.claimType -> {
                        var validators = mutableListOf<Validators>()
                        if (claimtypedetail.claimfield?.required.equals("1")) {
                            validators.add(Validators.Required())
                        }

                        mViewModel.claimFormState.addField(
                            claimtypedetail.claimfieldId.toString()!!,
                            TextFieldState(
                                claimtypedetail.claimfieldId.toString()!!,
                                validators = validators
                            )
                        )
                        addFieldInputText(claimtypedetail, true, false)
                    }
                    ClaimFieldType.SINGLELINE_TEXT_NUMERIC.claimType -> {
                        Log.d(
                            TAG,
                            "addFieldInputText called claimtypedetail.claimfield? " + claimtypedetail.claimfield?.id
                        )
                        var validators = mutableListOf<Validators>()
                        if (claimtypedetail.claimfield?.required.equals("1")) {
                            validators.add(Validators.Required())
                        }

                        mViewModel.claimFormState.addField(
                            claimtypedetail.claimfieldId.toString()!!,
                            TextFieldState(
                                claimtypedetail.claimfieldId.toString()!!,
                                validators = validators
                            )
                        )
                        addFieldInputText(claimtypedetail, false, true)
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = getString(R.string.end_of_the_form), color = Color.Gray)
            }

        }
    }


    /**
     * compose function for Text Input Field in form
     * **/
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun addFieldInputText(
        claimtypedetail: Claimtypedetail,
        isAllCaps: Boolean,
        isNumeric: Boolean
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current
        val fieldstate =
            mViewModel.claimFormState.getState<TextFieldState>(claimtypedetail.claimfieldId.toString()!!)
        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = fieldstate?.value ?: "",
                onValueChange = { fieldstate.change(it) },
                enabled = true,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        keyboardController?.hide()
                    },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text,
                    capitalization = if (isAllCaps) KeyboardCapitalization.Characters else KeyboardCapitalization.None,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                label = { Text(claimtypedetail.claimfield?.label ?: "", color = Color.DarkGray) }
            )
            if (fieldstate.hasError) {
                Text(text = fieldstate.errorMessage, color = Color.Red)
            }

        }

    }

    /**
     * compose function for Dropdown Field in form
     * **/
    @Composable
    fun addFieldDropDown(claimtypedetail: Claimtypedetail) {

        var expanded by remember { mutableStateOf(false) }

        var textfieldSize by remember { mutableStateOf(Size.Zero) }

        val fieldstate =
            mViewModel.claimFormState.getState<ChoiceState>(claimtypedetail.claimfieldId.toString()!!)

        val icon = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = fieldstate?.value ?: "",
                onValueChange = { },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textfieldSize = coordinates.size.toSize()
                    }
                    .clickable {
                        expanded = !expanded
                    },
                label = { Text(claimtypedetail.claimfield?.label ?: "", color = Color.DarkGray) },
                trailingIcon = {
                    Icon(icon, "select field " + claimtypedetail.claimfield?.label,
                        Modifier.clickable { expanded = !expanded })
                }
            )
            if (fieldstate.hasError) {
                Text(text = fieldstate.errorMessage, color = Color.Red)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
            ) {
                claimtypedetail.claimfield?.claimfieldoptions?.forEach { optoin ->

                    DropdownMenuItem(onClick = {
                        fieldstate.change(optoin.name ?: "")
                        expanded = false
                    }) {
                        Text(text = optoin.name ?: "No option available")
                    }


                }
            }


        }
    }

    /**
     * function to clear the current form state
     * **/
    fun clearForm() {
        mViewModel._claimTypeSelected.value = Claim()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        mainContent()
    }


}
