package org.example;
public class Main {
    public static void main(String[] args) { 
        // current line counter = 177
        // goal = 1500+ 
        IDandPasswords idandPasswords = new IDandPasswords();
		LoginPage loginPage = new LoginPage(idandPasswords.getLoginInfo());
    }
}