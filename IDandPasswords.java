import java.util.HashMap;

public class IDandPasswords {

	HashMap<String,String> logininfo = new HashMap<String,String>();
	
    // add new login info for new users here 
	IDandPasswords(){
		
		logininfo.put("Jordan","12345");
		
	}
	
	public HashMap getLoginInfo(){
		return logininfo;
	}
}