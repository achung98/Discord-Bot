import net.dv8tion.jda.core.entities.Member;

public class Warning {
	//Create the list that is going to be used
	private static UsersLinkedList userList = new UsersLinkedList();
	
	//Check if member is already in the list or not
	public static boolean checkMember(Member member) {
		if(userList.contains(member)) {
			int warnings = userList.getWarning(member);
			//If the member was on the list, get check the number of warnings
			if(userList.getWarning(member) < 3) {
				userList.setWarning(member, warnings+1);
			} else {
				return true;
			}
		} else {
			//Add the member to the list
			userList.add(member);
		}
		return false;
	}
	
	public static int getWarnings(Member member) {
		return userList.getWarning(member);
	}
	
	public static void deleteAll() {
		userList.delete();
	}
	
	public static int getBans(Member member) {
		return userList.getBanTimes(member);
	}
}

