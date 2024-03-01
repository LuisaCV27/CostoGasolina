package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    CostGasLayout("Android")
                }
            }
        }
    }
}

@Composable
fun CostGasLayout(name: String) {
    var precioGas by remember{
        mutableStateOf("")
    }
    var cantLitros by remember{
        mutableStateOf("")
    }
    var propina by remember{
        mutableStateOf("")
    }
    var estadoSwitch by remember{
        mutableStateOf(false)
    }

    val precioLitro = precioGas.toDoubleOrNull() ?:0.0
    val cantLitrosNum = cantLitros.toDoubleOrNull() ?:0.0
    val propinaNum = propina.toDoubleOrNull() ?:0.0
    val total = calcularMonto(precioLitro,cantLitrosNum, propinaNum, estadoSwitch)

    Box (
        contentAlignment = Alignment.Center
    ){
        Column {
            Text(
                text = stringResource(R.string.calcular_monto),
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            EditNumberField(
                label = R.string.ingresa_gasolina,
                leadingIcon = R.drawable.money_gas,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = precioGas,
                onValueChanged = { precioGas = it },
                modifier = Modifier.fillMaxWidth()
            )
            EditNumberField(
                label = R.string.ingresa_litros,
                leadingIcon = R.drawable.gas_station,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = cantLitros,
                onValueChanged = { cantLitros = it },
                modifier = Modifier.fillMaxWidth()
            )
            EditNumberField(
                label = R.string.propina,
                leadingIcon = R.drawable.propina,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = propina,
                onValueChanged = { propina = it },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "¿Quieres agregar la propina?",
                )
                Switch(
                    checked = estadoSwitch,
                    onCheckedChange = { estadoSwitch = it },
                    enabled = true,
                )
            }
            Text(
                text = "\n SU TOTAL ES:  $total",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )

        }
    }


}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  }, //expresión lambda
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions,
        modifier = modifier,
        onValueChange = onValueChanged
    )

}


private fun calcularMonto(precioGas: Double, cantLitros: Double, propina: Double, estadoSwitch: Boolean): String{
    if (estadoSwitch){
        val monto = precioGas * cantLitros + propina
        return NumberFormat.getCurrencyInstance().format(monto)
    }else{
        val monto = precioGas * cantLitros
        return NumberFormat.getCurrencyInstance().format(monto)
    }
}

@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout("Android")
    }
}