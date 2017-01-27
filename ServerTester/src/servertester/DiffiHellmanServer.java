/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertester;

import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DiffiHellmanServer {
    static BufferedReader in = null;
    static PrintWriter    out= null;

    static int b,B,A,K,g,p = 0;
    
    private static ServerSocket servers = null;
    private static Socket       fromclient = null;
    private static boolean conection = false; 

  public static void main(String[] args) throws IOException, Exception {
    System.out.println("Welcome to Server side");
    //*
    while(true){
        searchConnection();
        connectionSession();
        System.out.println("end of connection!");
    }
  }

    private static void searchConnection() throws IOException{
        
            servers = new ServerSocket(9999);
       
        try {
            System.out.print("Waiting for a client...");
            fromclient= servers.accept();
            System.out.println("Client connected");
            conection = true;
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
        }
    }
    
    private static void connectionSession() throws IOException, Exception{
        in  = new BufferedReader(new
        InputStreamReader(fromclient.getInputStream()));
        out = new PrintWriter(fromclient.getOutputStream(),true);
        String input = "";
        String output;
        String buffer = "";
        long wdt = 0;
        int b = 0;
        
        
        System.out.println("Wait for messages");
        while (true) {
            boolean lw = false;
            boolean eom = false;
            System.out.println("wdt = "+ wdt);
            if(in.ready()){
                input = in.readLine();
                lw = true;
                wdt = 0;
            }
            if(!input.equalsIgnoreCase("end of message")&&lw) {
                System.out.println("next line");
                buffer = buffer + input;
                //out.println("S :::"+input);
                System.out.println(buffer);
                lw = false;
            }
            if(input.equalsIgnoreCase("end of message")){
                eom = true;
            }
            if(eom){
                System.out.println("end of message");
                messagerAnalitic(buffer);
                buffer = "";
                eom = false;
                input = "";
            }
            if(wdt == 40000000){
                break;
            }
            wdt++;
        }
        System.out.println("Connection is lost");
        out.close();
        in.close();
        fromclient.close();
        servers.close();
    }
    
    public static int getNewRandom(){
        Random ran = new SecureRandom();
        return ran.nextInt(100);
    }
    
    private static void messagerAnalitic(String message) throws IOException, Exception{
        Lexer lexer = new Lexer();
	lexer.processInput(message);
	List<Token> tokens = lexer.getTokens();
        Parser parser = new Parser();
        parser.setTokens(tokens);
        parser.lang();
        System.out.println("End of Analitic");
        //System.out.print("\n"+"массив значений: "+parser.getVal());
    }
    
    
    
}



