package command

import io.github.thexxiv.jda.command.manager.Command
import io.github.thexxiv.urbandictionary.UrbanDictionary
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color
import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import kotlin.random.Random

class Search : Command {
    override fun run(args: List<String>, event: GuildMessageReceivedEvent) {
        val input = args.stream().map { obj: Any -> obj.toString() }.collect(Collectors.joining(" "))
        val user = event.message.author.name
        val icon = event.message.author.avatarUrl
        val bot = event.jda.selfUser.name
        val botIcon = event.jda.selfUser.avatarUrl
        val message = event.channel.sendMessageEmbeds(EmbedBuilder().setDescription("**Loading...** :hourglass:").setColor(Color.decode("#ffac33")).build())
            .delay(3, TimeUnit.SECONDS).complete()
        try {
            if (!indexExists(args, 0)) {
                message.editMessageEmbeds(
                    EmbedBuilder().setDescription("**You have to write a term** :warning:").setColor(Color.decode("#ffcc4d")).build()
                ).queue()
            } else {
                val urban = UrbanDictionary.search(input, 1)
                if (urban != null) {
                    val random = Random.nextInt(urban.size)
                    val term = urban[random]
                    val r = Random.nextInt(255);
                    val g = Random.nextInt(255);
                    val b = Random.nextInt(255);
                    message.editMessageEmbeds(EmbedBuilder()
                        .setAuthor(bot,null,botIcon)
                        .setDescription(
                            "**`Word:`** **${term.word}**\n\n" +
                                    "**`Definition:`** **${term.definition}**\n\n" +
                                    "**`Example:`** **${term.example}**\n\n" +
                                    "**`Likes:`** **${term.thumbs_up}**\n\n" +
                                    "**`Dislikes:`** **${term.thumbs_down}**\n\n" +
                                    "**`Author:`** **${term.author}**\n\n" +
                                    "**`Written on:`** **${term.written_on}**"
                        )
                        .setColor(Color(r,g,b))
                        .setFooter("Requested by $user", icon)
                        .setTimestamp(Instant.now())
                        .build()).queue()
                } else {
                    message.editMessageEmbeds(
                        EmbedBuilder().setDescription("**Something went wrong** :bangbang:").setColor(Color.decode("#be1931")).build()
                    ).queue()
                }
            }
        } catch (ex: Exception) {
            message.editMessageEmbeds(
                EmbedBuilder().setDescription("**Something went wrong** :bangbang:").setColor(Color.decode("#be1931")).build()
            ).queue()
        }
    }

    override fun getCommand(): String {
        return "search"
    }
}

fun indexExists(list: List<String>, index: Int): Boolean {
    return index >= 0 && index < list.size
}