package mainWindow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import viewModels.MainViewModel
import java.sql.Connection
import java.sql.DriverManager

@Composable
fun Page2(mainVM: MainViewModel) {
    Column {
        Button(onClick = { dataB() }) {
            Text("Database connect")
        }
        Button(onClick = {  }) {
            Text("Database print")
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mainVM.textField,
            onValueChange = { mainVM.textField = it },
            label = { Text("Title OutlinedTextField") }
        )
        Text(mainVM.textField)
    }
}

fun dataB() {
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val driver = "org.postgresql.Driver"
    val user = "postgres"
    val password = "Dima2004"
    val db = Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )

    try {
        Class.forName(driver)
        val connection: Connection = DriverManager.getConnection(url, user, password)
        println("Connected to the database!")
        // Ваш код работы с базой данных здесь
    } catch (e: Exception) {
        e.printStackTrace()
    }
}