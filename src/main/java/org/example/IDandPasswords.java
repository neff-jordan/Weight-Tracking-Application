package org.example;
import java.util.HashMap;

public class IDandPasswords {

	HashMap<String,String> logininfo = new HashMap<String,String>();
	
    // add new login info for new users here 
	IDandPasswords(){
		
		logininfo.put("Jordan","12345");
		
	}

	public void createLoginInfo(String username, String password) {
		logininfo.put(username, password);
	}

	//private void userList(User user) { }
	
	public HashMap getLoginInfo(){
		return logininfo;
	}
}