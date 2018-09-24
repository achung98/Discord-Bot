import net.dv8tion.jda.core.entities.Member;

public class UsersLinkedList {
	private class UserNode {
		//Number of warnings
		private int warnings;
		//Specific memberId
		private Member member;
		//Number of bans
		private int banNum;
		
		//Link to the next member
		private UserNode next;
		
		//Default constructor
		public UserNode() {
			warnings = 0;
			member = null;
			banNum = 0;
		}
		
		//Specific member constructor
		public UserNode(Member member, int warnings) {
			this.warnings = warnings;
			this.member = member;
			banNum = 0;
		}

		//Getters and setters
		public Member getMember() {
			return member;
		}

		public void setMember(Member member) {
			this.member = member;
		}

		public int getWarnings() {
			return warnings;
		}

		public void setWarnings(int warnings) {
			this.warnings = warnings;
		}
		
		public void setBanNum(int banNum) {
			this.banNum = banNum;
		}
		
		public int getBanNum() {
			return banNum;
		}
	}
	//Start of the user list
	private UserNode head;
	
	//Add new user to the end
	public void add(Member member) {
		//If the list is empty, add the node at the beginning
		if(head == null) {
			head = new UserNode(member, 1);
			return;
		}
		//Create a pointer to the head(start of the list)
		UserNode it = head;
		//Check if the next node is empty
		while(it.next !=null)
			//Go to the next node until it is one before the last one
			it = it.next;
		//Add the member at the end
		it.next = new UserNode(member, 1);
	}
	
	/*Con lo de arriba puedes entender mas o menos todo lo de abajo*/
	
	//Search if user is already on the list
	public boolean contains(Member member) {
		if(head == null)
			return false;
		//If the member is found at the beginning
		if(head.getMember().equals(member))
			return true;
		UserNode it = head;
		while(it.next != null) {
			if(it.getMember().equals(member)) {
				return true;
			}
			it = it.next;
		}
		return false;
	}
	
	//Get warnings from member
	public int getWarning(Member member) {
		UserNode it = head;
		while(it != null) {
			if(it.getMember().equals(member))
				break;
			it = it.next;
		}
		return it.getWarnings();
	}
	
	//Update warnings from member
	public void setWarning(Member member, int warnings) {
		UserNode it = head;
		while(it != null) {
			if(it.getMember().equals(member)) {
				it.setWarnings(warnings);
				break;
			}
			it = it.next;
		}
	}
	
	//Get number of times the user has been banned
	public int getBanTimes(Member member) {
		UserNode it = head;
		while(it != null) {
			if(it.getMember().equals(member)) 
				break;
			it = it.next;
		}
		return it.getBanNum();
	}
	
	//Remove user from the list
	public void remove(Member member) {
		if(head == null)
			return;
		if(head.getMember().equals(member))
			head = null;
		UserNode it = head;
		while(it.next != null) {
			if(it.next.getMember().equals(member)) {
				//If the user has not been banned 3 times
				if(it.next.getBanNum() != 3) {
					it.next.banNum++;
					break;
				} else {
					it = it.next.next;
					break;
				}
			}
			it = it.next;
		}
	}
	
	public void delete() {
		head = null;
	}
}
