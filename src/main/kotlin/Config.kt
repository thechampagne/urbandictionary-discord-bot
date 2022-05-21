import io.github.cdimascio.dotenv.Dotenv

object Config {
    val TOKEN: String = Dotenv.load().get("TOKEN").toString() // Located in .env file in resources
    const val PREFIX: String = "!"
}