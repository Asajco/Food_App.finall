package com.example.foodapp

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


const val HOME_ROUTE = "home"
const val NOTE_ROUTE = "note"

@Composable
fun MainView() {
    val userVM = viewModel<UserViewModel>()

    if (userVM.userName.value.isEmpty()){
        LoginView(userVM)
    }else{
        MainScaffoldView()
    }
}

@Composable
fun MainScaffoldView() {

    val navController = rememberNavController()
    Scaffold (
        topBar ={ TopBarView()},
        bottomBar = { BottomBarView(navController)},
        content = { MainContentView(navController)}
    )
}

@Composable
fun MainContentView(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HOME_ROUTE){
        composable(route = HOME_ROUTE){ HomeView() }
        composable(route = NOTE_ROUTE){ NoteView()}
    }
}

@Composable
fun HomeView() {

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "MENU", fontFamily = FontFamily.Monospace, fontSize = 20.sp)

        Image(
            painter = painterResource(id = R.drawable.burger),
            contentDescription = "burger",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp))
        Text(text = "Hamburger / 5.40 ", fontFamily = FontFamily.Monospace)

        Divider(thickness = 2.dp, modifier = Modifier.padding(20.dp))

        Image(
            painter = painterResource(id = R.drawable.bacon),
            contentDescription = "burger",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                )
        Text(text = "Bacon burger/ 5.90", fontFamily = FontFamily.Monospace)

        Divider(thickness = 2.dp, modifier = Modifier.padding(20.dp))

        Image(painter =
            painterResource(id = R.drawable.cheese ),
            contentDescription = "burger",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp))

        Text(text = "Cheese burger / 6.00", fontFamily = FontFamily.Monospace)

        Divider(thickness = 2.dp, modifier = Modifier.padding(20.dp))

        Image(painter =
        painterResource(id = R.drawable.onion ),
            contentDescription = "onion",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp))

        Text(text = "Onion Rings / 4.00 / 8 pieces", fontFamily = FontFamily.Monospace)

        Divider(thickness = 2.dp, modifier = Modifier.padding(20.dp))
    }

}
@Composable
fun NoteView() {

    var noteText by remember{ mutableStateOf("") }
    val noteVM = viewModel<NoteViewModel>(LocalContext.current as ComponentActivity)

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        OutlinedTextField(
            value = noteText,
            onValueChange = {noteText = it},
            label = { Text(text = "Your order here", fontFamily = FontFamily.Monospace) },
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { noteVM.addNote(Note(noteText)) },

        )
        {
            Text(text = "Order", fontFamily = FontFamily.Monospace)
        }

        Spacer(modifier = Modifier.height(10.dp))


        noteVM.notes.value.forEach {
            Divider(thickness = 2.dp)
            Text(text = it.message)
        }
        Divider(thickness = 2.dp)
    }
}

@Composable
fun BottomBarView(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Yellow),
        horizontalArrangement =  Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "home",
            modifier = Modifier.clickable { navController.navigate(HOME_ROUTE) }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_food),
            contentDescription = "note",
            modifier = Modifier.clickable { navController.navigate(NOTE_ROUTE) }
        )
        

    }
}


@Composable
fun TopBarView() {
    val userVm = viewModel<UserViewModel>()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,


    )
    {
        Text(text = userVm.userName.value, fontFamily = FontFamily.Monospace)
        OutlinedButton(onClick = { userVm.logoutUser() }) {
            Text(text = "Log out", fontFamily = FontFamily.Monospace)
        }
    }
}


@Composable
fun LoginView(userVM: UserViewModel) {
    var email by  remember {
        mutableStateOf("")
    }
    var password by  remember {
        mutableStateOf("")
    }




    Column(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email:", fontFamily = FontFamily.Monospace) }

        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password:", fontFamily = FontFamily.Monospace) },
            visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.padding(20.dp))


        OutlinedButton(onClick = {userVM.loginUser(email, password)},) {
            Text(text = "Login", fontFamily = FontFamily.Monospace)
        }


    }


}