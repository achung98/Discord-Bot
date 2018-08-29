import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.HierarchyException;
import net.dv8tion.jda.core.managers.GuildController;

public class WordsModeration {
	
	//HierarchyException: If the bot try to kick someone with the same power as the bot or higher, dont catch it.
	public static void execute(MessageReceivedEvent event, String msg) throws HierarchyException {
		MessageChannel channel = event.getChannel(); //Get where the text channel in which the message was sent
		GuildController gc = new GuildController(event.getGuild());; //Get the server
		User user = event.getAuthor(); //Get the user who sent the message
		Member member = event.getMember(); //Get the user as a Member
		
		if(checkWords(msg)) {
			event.getMessage().delete().queue();
			if(!Warning.checkMember(member)) {
				channel.sendMessage("You have " + Warning.getWarnings(member) + " warning/s " + user.getAsMention()).queue();
			} else
				//Ban the user for two days
				gc.ban(member, 2).queue();
		}
	}
	
	private static boolean checkWords(String msg) {
		Scanner sc = null;
		//Stores the words
		List<String> wordList = new ArrayList<String>();
		
		try {
			//Read the text files that has the racist shit
			sc = new Scanner(new FileInputStream("Racist slurs.txt"));
			while(sc.hasNextLine()) {
				//Add each word to the list
				wordList.add(sc.nextLine());
			}
		} catch(FileNotFoundException e) {
			System.out.println("File not found");
		} finally {
			sc.close();
		}
		
		//foreach loop. Check is the message written by the user has one of the words in the text file
		for(String s : wordList) {
			if(msg.contains(s))
				return true;
		}
		return false;
	}
}
