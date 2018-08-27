import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Test extends ListenerAdapter {

	public static void main(String[] args) throws LoginException {
		//Bot object
		JDABuilder example = new JDABuilder(AccountType.BOT);
		//Set the bot token (Sale en la pagina de developers de discord)
		example.setToken("NDgyMzQyNDY4NzcyNDI5ODI0.DmM94Q.4uQ8T-K1Gr2C-IyBokWP2GCwcGk")
		//Add a new object of Test as the event listner
		.addEventListener(new Test())
		//Connect the bot to the server
		.buildAsync();
	}
	
	public void onMessageReceived(MessageReceivedEvent event) {
		MessageChannel channel = event.getChannel(); //Stores the channel that the message was received from
		//If the message received was sent by the bot, get out of the method
		if(event.getAuthor().isBot())
			return;
		//If the message is "Hola"
		if(event.getMessage().getContentRaw().equals("Hola")) {
			//Send the message "Mundo" in the channel.
			channel.sendMessage("Mundo").queue();
		} else
			channel.sendMessage("Chupalo cuequito").queue();
	}
}
