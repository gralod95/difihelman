/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author macbook
 */
public class brain {
    public static void analiticFirstPackage(String setG,String setP, String setA){
        DiffiHellmanServer.g = Integer.valueOf(setG);
        DiffiHellmanServer.p = Integer.valueOf(setP);
        DiffiHellmanServer.A = Integer.valueOf(setA);
        DiffiHellmanServer.b = getNewRandom();
        DiffiHellmanServer.B = modExp(DiffiHellmanServer.g, DiffiHellmanServer.b, DiffiHellmanServer.p);
        System.out.println(" B: "+DiffiHellmanServer.B);
        DiffiHellmanServer.K = modExp(DiffiHellmanServer.A, DiffiHellmanServer.b, DiffiHellmanServer.p);
        System.out.println(" K: "+DiffiHellmanServer.K);
        sendMessageToClient("message1{\n" +
"B = \""+DiffiHellmanServer.B+"\";\n" +
"}\n" +
"end of message");
    }
    public static int modExp(int b, int e, int m){
       BigInteger b1, e1, m1;

	// create a BigInteger exponent

        b1 = new BigInteger(Integer.toString(b));
        e1 = new BigInteger(Integer.toString(e));
        m1 = new BigInteger(Integer.toString(m));
        // perform modPow operation on bi1 using bi2 and exp
	b1 = b1.modPow(e1, m1);
        int res = b1.intValue();
        return res;
    }
    public static int getNewRandom(){
        Random ran = new SecureRandom();
        return ran.nextInt(100);
    }
    public static void sendMessageToClient(String message){
        DiffiHellmanServer.out.println(message);
    }
    public static String getChar(int code){
        char[] charPair = Character.toChars(code);
        String symbol = new String(charPair);
        return symbol;
    }
    public static int unhashCode(String hashCode, int i){
        return (Integer.valueOf(hashCode) - i)/DiffiHellmanServer.K;
    }
    public static void answermessagesesson() throws IOException{
        BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
        String message;
        message = inu.readLine();
        sendAnswerMessageToClient(message);
    }
    
    private static void sendAnswerMessageToClient(String message){
        String hashMessage = "";
        for(int i = 0; i<message.length();i++){
        int code = Character.codePointAt(message, i);
        int hashCode = hashCode(code, i);
        if(hashMessage.equals("")){
            hashMessage = "\""+hashCode+"\"";
        }else{
            hashMessage = hashMessage+" ; \""+hashCode+"\"";
        }
        //System.out.println("char = "+message.charAt(i)+"\n"+ "int = "+code);
        }
        System.out.println(hashMessage);
        DiffiHellmanServer.out.println("serverMessage{"+hashMessage+"}\n"
                + "end of message");
    }
    public static int hashCode(int code, int i){
        return code*DiffiHellmanServer.K+i;
    }
}
