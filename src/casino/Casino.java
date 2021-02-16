/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

/*
    Requires com.googlecode.json-simple:json-simple:1.1.1
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Casino {

    static public String WelcomeMode;
    
    static private String LoggedInUsername;
    static private int UserBalance;
    
    static WelcomeGUI welcomeGUI = new WelcomeGUI();
    static EnterDetailsGUI enterDetailsGUI = new EnterDetailsGUI();
    static MainMenu mainMenuGUI = new MainMenu();
    static HigherLowerGUI higherOrLower = new HigherLowerGUI();
    static BlackJackGUI blackJack = new BlackJackGUI();
    static Roulette roulette = new Roulette();
    
    public static void main(String[] args) {
        
        // Setting up GUI's (Could put it in a method but im lazy)
        welcomeGUI.setLocationRelativeTo(null);
        welcomeGUI.setVisible(true);
        welcomeGUI.setTitle("Royal Casino");
        enterDetailsGUI.setVisible(false);
        enterDetailsGUI.setTitle("Royal Casino");
        mainMenuGUI.setVisible(false);
        mainMenuGUI.setTitle("Royal Casino");
        higherOrLower.setVisible(false);
        higherOrLower.setTitle("Higher or Lower - Royal Casino");
        blackJack.setVisible(false);
        blackJack.setTitle("Blackjack - Royal Casino");
        // roulette.setVisible(false);
        roulette.setTitle("Roulette - Royal Casino");
   
        mainMenuGUI.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // save balance code here

                try {
                    JSONObject Data = (JSONObject) (new JSONParser().parse(GetJson()));
                    JSONObject Users = (JSONObject) Data.get("Users");
                    JSONObject CurrentUser = (JSONObject) Users.get(LoggedInUsername);

                    CurrentUser.replace("Balance", Integer.toString(UserBalance));

                    FileWriter file = new FileWriter("Data.json");

                    file.write(Data.toJSONString());
                    file.flush();
                    
                    System.out.println("Saved data - exiting");
                    System.exit(0);

                } catch (Exception f) {
                    System.out.println("Unable to save data - " + f);
                    System.exit(0);
                }
                
            }
        });
    }
    
    public static String GetJson(){ try{
        
        File wordsFile = new File("Data.json");
        Scanner myReader = new Scanner(wordsFile);
        
        String Json = "";
        
        while (myReader.hasNextLine()) {
            Json = Json + myReader.nextLine();
        }
        
        return Json;
        
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return null;
    }
    
    public static void LoadHigherOrLower(){
        
        mainMenuGUI.setVisible(false);
        higherOrLower.setVisible(true);
        higherOrLower.UpdateBalance(UserBalance);
        higherOrLower.setLocation(mainMenuGUI.getLocation());
    }
    
    public static void LoadBlackJack(){
        
        mainMenuGUI.setVisible(false);
        blackJack.setVisible(true);
        blackJack.UpdateBalance(UserBalance);
        blackJack.setLocation(mainMenuGUI.getLocation());
    }
    
    public static void UnloadGames(int NewBalance){
        
        UserBalance = NewBalance;
        
        higherOrLower.setVisible(false);
        blackJack.setVisible(false);
        
        mainMenuGUI.UpdateInfo(LoggedInUsername, Integer.toString(UserBalance));
        mainMenuGUI.setLocationRelativeTo(null);
        mainMenuGUI.setVisible(true);
    }
    
    
    
    
    public static JSONObject GetUser(String Username){ try { // Error catching the whole method because im a cool kid ;)

        
        String Json = GetJson();
        
        
        JSONObject Data = (JSONObject) (new JSONParser().parse(Json));
        
        JSONObject Users = (JSONObject) Data.get("Users");
        
        JSONObject User = (JSONObject) Users.get(Username);
        
        return User;
        
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return null;
        
    }
    
    public static void LoadMainMenu(String Username, int Balance){
        
        enterDetailsGUI.setVisible(false);
        mainMenuGUI.setLocationRelativeTo(null);
        mainMenuGUI.setVisible(true);
        LoggedInUsername = Username;
        UserBalance = Balance;
        mainMenuGUI.UpdateInfo(LoggedInUsername, Integer.toString(UserBalance));
    }
    
    public static void WelcomeGUIEvent(String Mode){
        
        welcomeGUI.setVisible(false);
        WelcomeMode = Mode;
        enterDetailsGUI.setLocation(welcomeGUI.getLocation());
        enterDetailsGUI.setVisible(true);
        enterDetailsGUI.ModeChange(Mode);
    }
    
}
