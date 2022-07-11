package bot

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun returnCategories(): String {
    val categories = mutableMapOf<Int, String>()
    categories.put(1, "Zupki")
    categories.put(2, "Ketchupy")
    categories.put(3, "Chipsy")

    var response = ""

    categories.forEach { category ->
        response += "${category.key}: ${category.value}\n"
    }

    return  response
}

fun returnCategoriesItmes(categoryId: String): String {
    val zupki = mutableMapOf<Int, String>()
    zupki.put(1, "Zupka chińska 10zł")
    zupki.put(2, "Zupka japońska 15zł")

    val ketchupy = mutableMapOf<Int, String>()
    ketchupy.put(1, "Heinz 15zł")
    ketchupy.put(2, "Tortex 18zł")
    ketchupy.put(3, "Pudliszki 4zł")

    val chips = mutableMapOf<Int, String>()
    chips.put(1, "Lay's 25zł")
    chips.put(2, "Cheetos 20zł")
    chips.put(3, "Crunchips 30zł")
    chips.put(3, "Pringles 40zł")

    var response = ""

    when (categoryId.toInt()) {
        1 -> {
            zupki.forEach { category ->
                response += "${category.key}: ${category.value}\n"
            }

            return response
        }
        2 -> {
            ketchupy.forEach { category ->
                response += "${category.key}: ${category.value}\n"
            }
            return response
        }
        3 -> {
            chips.forEach { category ->
                response += "${category.key}: ${category.value}\n"
            }
            return response
        }
        else -> {
            return "Nieprawidłowa kategoria"
        }
    }
}

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }

    routing {
        post("/hello") {
            call.respondText("Witaj w jestem botem!")
        }

        post("/category") {
            call.respondText(returnCategories())
        }

        post("/products") {
            val formParameters = call.receiveParameters()
            var id = formParameters["text"]
            if (id == null || id == "") {
                id = "-1"
            }

            call.respondText(returnCategoriesItmes(id))
        }
    }
}