import ui.theme.MainTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import mainWindow.Page1
import mainWindow.Page2
import mainWindow.Page3
import mainWindow.TopBar
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import viewModels.MainViewModel
import viewModels.NavigationViewModel

@Composable
fun App(mainVM: MainViewModel) {
    MainTheme(mainVM.settingsVM.currentTheme) {
        val navVM = remember { NavigationViewModel() }
        Scaffold(
            topBar = {
                TopBar(navVM)
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                when (navVM.currentPage) {
                    3 -> Page3(mainVM)
                    2 -> {
                        d()
                        a()
                    }

                    else -> Page1(mainVM)
                }
            }
        }
    }
}

fun d() {
    Database.connect(
        url = "jdbc:h2:mem:test",
        driver = "org.h2.Driver",
        user = "postgres332",
        password = "Dima2004",
        databaseConfig = DatabaseConfig {
            transaction {
                //Do some stuff
                SchemaUtils.create(StarWarsFilms)
                //Do other stuff
            }
        }
    )

}

object StarWarsFilms : IntIdTable("STAR_WARS_FILMS") {
    val sequelId = integer("sequel_id").uniqueIndex()
    val name = varchar("name", 50)
    val director = varchar("director", 50)
}

//val sequelId = reference("sequel_id", StarWarsFilms.sequelId).uniqueIndex()

//val filmId = reference("film_id", StarWarsFilms)

val query = StarWarsFilms.selectAll()

fun a() {
    query.forEach {
        println(it[StarWarsFilms.sequelId] >= 7)
    }
}