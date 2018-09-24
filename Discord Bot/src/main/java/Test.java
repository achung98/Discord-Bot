import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.Role;
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
		example.setToken("")
		//Add a new object of Test as the event listner
		.addEventListener(new Test())
		//Connect the bot to the server
		.buildAsync();
	}
	
	public void onMessageReceived(MessageReceivedEvent event) {
		User user = event.getAuthor();
		MessageChannel msgChannel = event.getChannel();
		Message msg = event.getMessage();
		String msgString = msg.getContentRaw();
		GuildController gc = new GuildController(event.getGuild());;
		
		if(event.getAuthor().isBot())
			return;
		
		WordsModeration.execute(event, msgString);
		
		if(msgString.contains("!role")) {
			//Create roles
			createRoles(user, msg, msgChannel, gc);
		}
		
		//Create text channel
		if(msgString.contains("!text")) {
			msg.delete().queue();
			//Get content of text
			String[] newText = msgString.split(" ");
			//Get the category (Canales de texto)
			Category cat = event.getMessage().getCategory();
			//Create the text channel in that category
			gc.createTextChannel(newText[1]).setParent(cat).queue();
		}
		
		//Create voice channel (Not working as intended, dont know how to fix it)
		if(msgString.contains("!voice")) {
			msg.delete().queue();
			//Get contents of text
			String[] newVoice = msgString.split(" ");
			//Creates new voice channel, this will create it at the top since i did not specify a parente category
			gc.createVoiceChannel(newVoice[1]).queue();
		}
	}
	
	//Will only work for admins, else it will throw an error
	private void createRoles(User user, Message msg, MessageChannel msgChan, GuildController gc) {
		//Delete the message
		msg.delete().queue();
		//Get different values of the message into an array
		String [] newRole = msg.getContentRaw().split(" ");
		if(newRole.length == 3) {
			//Create new role and give it a color
			gc.createRole().setName(newRole[1]).setColor(Integer.parseInt(newRole[2])).queue();
		} else if(newRole.length == 4){
			//Create new role, color and give it permission based on the permissions long
			gc.createRole().setName(newRole[1]).setColor(Integer.parseInt(newRole[2])).setPermissions(Long.parseLong(newRole[3])).queue();
		} else {
			//Tell the user by dm the correct syntax of the role
		 user.openPrivateChannel().queue((channel) ->
	        {
	            channel.sendMessage("The syntax is: !role RoleName RoleColor\nYou could also add permisions at the end if you know the integer value").queue();
	        });
		}
	}
	
	
}
