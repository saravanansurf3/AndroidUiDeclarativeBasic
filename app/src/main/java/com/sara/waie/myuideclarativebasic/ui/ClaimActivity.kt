package com.sara.waie.myuideclarativebasic.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sara.waie.myuideclarativebasic.R
import com.sara.waie.myuideclarativebasic.model.Claim
import com.sara.waie.myuideclarativebasic.ui.theme.MyUiDeclarativeBasicTheme
import com.sara.waie.myuideclarativebasic.vm.ClainActivityViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
const val  TAG="ClaimActivity"
@AndroidEntryPoint
class ClaimActivity : ComponentActivity(), LifecycleOwner {

    private val viewModel: ClainActivityViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainContent()
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun mainContent(mainViewModel: ClainActivityViewmodel = viewModel()) {
    MyUiDeclarativeBasicTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.screen_cliam_form), maxLines = 1)
                    },
                    navigationIcon = {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Filled.ArrowBack, "back")
                        }
                    },
                    actions = {
                        Text(
                            text = stringResource(id = R.string.action_submit).uppercase(Locale.getDefault()),
                            style = TextStyle(Color.White),
                            modifier = Modifier
                                .padding(8.dp)
                        )

                    })
            },
            content = {
                Column {
                    Log.d(TAG,"inside mainContent")
                    val response=mainViewModel.uiState.collectAsState().value
                    claimDropDown(response.Claims)

                }
            }
        )


    }


}

@Composable
fun claimDropDown(claims:ArrayList<Claim> = arrayListOf()) {
    Log.d(TAG,"inside claimDropDown")
    var expanded by remember { mutableStateOf(false) }
    var selectedClaim by remember { mutableStateOf(Claim()) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedClaim.Claimtype?.name?:"",
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
            label = { Text("ClimeType") },
            trailingIcon = {
                Icon(icon, "contentDescription",
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
                        selectedClaim = claim
                        expanded = false
                    }) {
                        Text(text = claim.Claimtype?.name!!)
                    }


            }
        }
        showForm(selectedClaim)
    }

}

@Composable
fun showForm(selectedClaim:Claim){
Log.d(TAG,"showForm")
    selectedClaim.Claimtype?.let {
        Text(text = it.name?:"")
    }
    selectedClaim.Claimtypedetail.forEach {

    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    mainContent()
}