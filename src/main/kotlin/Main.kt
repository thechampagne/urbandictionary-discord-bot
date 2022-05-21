import command.Search
import io.github.thexxiv.jda.command.manager.Listener
import io.github.thexxiv.jda.command.manager.Manager
import net.dv8tion.jda.api.JDABuilder

fun main() {
    // See https://github.com/thechampagne/jda-command-manager
    val manager = Manager(Config.PREFIX).addCommands(arrayOf(Search()))

    JDABuilder.createDefault(Config.TOKEN)
        .addEventListeners(Listener(manager))
        .build().awaitReady()
}