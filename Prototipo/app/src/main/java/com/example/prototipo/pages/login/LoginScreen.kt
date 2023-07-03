package com.example.prototipo.pages.login

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.prototipo.R
import com.example.prototipo.components.CreateChannelNotification
import com.example.prototipo.components.notificacionImagen
import com.example.prototipo.components.notificacionSencilla
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

// Componentes para crear el login con su respectivo funcionamiento.
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    // token para poder usar el login mediante de google este se obtiene mediante firebase en el provedor de google en claves publicas
    val token = "114529748082-n1lkbkkp698erun7g5m0j0vi8mivoat1.apps.googleusercontent.com"

    // Canal e información que usaran las notificaciones las cuales daran aviso si el usuario creo su cuenta o se logueo.
    val idNotification = 0
    val context: Context = LocalContext.current
    val idChannel: String = stringResource(R.string.canalTienda)
    val name = stringResource(R.string.canalTienda)
    val descriptionText = stringResource(R.string.canalNotificacion)

    val textShort = "¡Bienvenido de nuevo! Gracias por iniciar sesión"
    val textLong: String = "¡Bienvenido al CBA! nuestro centro es el más grande de " +
            "cundinamarca. Lo que nos caracteriza es nuestro compromiso con el medio ambiente." +
            "Esperamos que disfrute todo lo que nuestro centro ofrece. " +
            "Muchas Gracias"

    val iconoBig = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.sena
    )

    val imagenBig = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.auditorio
    )

    //Función de creación propia como corrutina
    LaunchedEffect(Unit){
        CreateChannelNotification(
            idChannel,
            context,
            name,
            descriptionText
        )
    }

    // Launcher el cual sera fundamental para realizar el login mediante de Google, su funcionamiento consiste en
    // verificar los datos del usuario Google mediante el provedor y crear su respectiva cuenta en nuestro aplicativo por medio del viewModel
    // el cual logueara en firebase este usuario Google por medio de su credencial.
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .StartActivityForResult()
    ){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredential(credential){
                navController.popBackStack()
                notificacionSencilla(
                    context,
                    idChannel,
                    idNotification,
                    "SENA CBA",
                    textShort
                )
            }
        } catch (ex: Exception){
            Log.d("LoginFirebase", "GoogleSignIn falló")
        }
    }

    //Variable para controlar la acción: True::Login - False::Create
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.surface)
        ) {
            if(isSystemInDarkTheme()){
                Image(painterResource(id = R.drawable.sena_1), contentDescription = null)
            } else {
                Image(painterResource(id = R.drawable.sena_2), contentDescription = null)
            }
            Text(text = "Centro de Biotecnología Agropecuaria", fontWeight = FontWeight.Bold, fontSize = 32.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            if (showLoginForm.value){
                Text(
                    text = "Inicia Sesión"
                )
                UserForm(
                    isCreateAccount = false
                ){
                    email, password ->
                    Log.d("TiendaApp", "Inicio sesion con $email y $password")
                    // Se llama del viewModel a la función de login por medio de correo y contraseña.
                    viewModel.signInWithEmailAndPassword(email, password){
                        navController.popBackStack()
                        notificacionSencilla(
                            context,
                            idChannel,
                            idNotification,
                            "SENA CBA",
                            textShort
                        )
                    }
                }
            } else {
                Text(
                    text = "Crear Cuenta Nueva"
                )
                UserForm(
                    isCreateAccount = true
                ){
                    email, password ->
                    Log.d("TiendaApp", "Creando cuenta con $email y $password")
                    // Se llama del viewModel a la función de Crear Usuario por medio de correo y contraseña.
                    viewModel.createUserWithEmailAndPassword(email, password){
                        navController.popBackStack()
                        notificacionImagen(
                            context,
                            idChannel,
                            idNotification + 2,
                            "SENA CBA",
                            textLong,
                            iconoBig,
                            imagenBig
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(15.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                val text1 =
                    if (showLoginForm.value)
                        "¿No tienes cuenta?"
                    else
                        "¿Ya tienes cuenta?"
                val text2 =
                    if (showLoginForm.value)
                        "Registrate"
                    else
                        "Inicia Sesión"
                Text(
                    text = text1
                )
                Text(
                    text = text2,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    color = Color.Blue
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        // Se llama al laucher que se creo para el logueo mediante de Google.
                        val opciones = GoogleSignInOptions.Builder(
                            GoogleSignInOptions.DEFAULT_SIGN_IN
                        )
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                        val googleSignInCliente = GoogleSignIn.getClient(context, opciones)
                        launcher.launch(googleSignInCliente.signInIntent)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Login con Google",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                )
                Text(
                    text = "Login con Google",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Funcion la cual tendra los diseños de los inputs y el botón de enviar.
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean,
    onDone: (String, String) -> Unit ={ email, pwd -> }
){

    val email = rememberSaveable{
        mutableStateOf("")
    }

    val password = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val validState = remember(email.value, password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            emailState = email
        )
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible
        )
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        SubmitButton(
            textId =
                if (isCreateAccount)
                    "Crear Cuenta"
                else
                    "Iniciar Sesión",
            inputValid = validState
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

// Diseño para el boton de envio cuando el usuario se loguea o se registra mediante correo y contraseña.
@Composable
fun SubmitButton(
    textId: String,
    inputValid: Boolean,
    onClic: () -> Unit
){
    Button(
        onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValid
    ) {
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp)
        )
    }
}

// Input de contraseña.
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
){
    val visualTransformation =
        if (passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank())
                PasswordVisibleIcon(passwordVisible)
            else null
        }
    )
}

// Icono el cual se conectara con una función para que la contraseña sea visible o no.
@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
){
    val image =
        if (passwordVisible.value)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }
}

// Input de correo electronico.
@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
){
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

// Diseño generico para los inputs.
@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
){
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}