import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.HierarchyException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class Test extends ListenerAdapter {

	public static void main(String[] args) throws LoginException, InterruptedException {
		//Warning.deleteAll();
		//Bot object
		JDABuilder example = new JDABuilder(AccountType.BOT);
		//Set the bot token (Sale en la pagina de developers de discord)
		example.setToken("") //Aqui va el token
		//Add a new object of Test as the event listner
		.addEventListener(new Test())
		//Connect the bot to the server
		.buildAsync();
	}
	
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContentRaw();
		
		if(event.getAuthor().isBot())
			return;
		
			WordsModeration.execute(event, msg);
		
		
	}
	
	
}
