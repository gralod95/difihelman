/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientprobnik;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author macbook
 */
public class Parser {
    private List<Token> tokens;
    private Token currentToken;
    private int currentTokenNumber = 0;
    private int numOfRTok = 0;
    private List<Token> variables = new ArrayList<Token>();
    private List<Token> varVal = new ArrayList<Token>();
    private List<String> valVar = new ArrayList<String>();
    private String nameOfVar;
    private boolean acs = true;
    
    private String B,content = "";
    int i = 0;
    
    public void setTokens(List<Token> tokens){
		this.tokens = tokens;
	}
    
    public boolean match(){
        currentToken = tokens.get(currentTokenNumber);
        currentTokenNumber++;
        numOfRTok++;
        return false;
    }
    
    public void lang() throws Exception {
        boolean exist = false;
        while ( currentTokenNumber < tokens.size() && expr() ) {
            exist = true;
        }
        if (!exist) {
            System.out.print("end");
        }
    }
    
    private boolean expr() throws Exception{
        System.out.print("Вход в expr"+"\n");
        if(message1()||serverMessage()){
            return true;  
        }
        return false;
    }
    
    private boolean message1() throws NoSuchAlgorithmException{
        System.out.println("Вход message1");
        numOfRTok = 0;
        ws();
        match();
        if(currentToken.getName().equals("MESSAGE1")){
            if(FBRK_OP()){
                if(B()){
                    if(ASSIGN_OP()){
                        if(VALL()){
                            B = currentToken.getValue().substring(1);
                            if(VALL_CL()){
if(SM()){
    if(FBRK_CL()){
        System.out.println(" B: "+B);
        Brain.getM(B);
        return true;  
    }
}
                            }
                        }
                    }
                }
            }
        }
        currentTokenNumber-=numOfRTok;
        return false;
    }
    
    private boolean serverMessage() throws NoSuchAlgorithmException, IOException{
        System.out.println("Вход serverMessage");
        numOfRTok = 0;
        ws();
        match();
        if(currentToken.getName().equals("SERVER_MESSAGE")){
            if(FBRK_OP()){
                messageContent();
                System.out.println(content);
            }
        }
        currentTokenNumber-=numOfRTok;
        return false;
    }
    
    private void messageContent(){
        System.out.print("Вход в messageContent"+"\n");
        ws();
        match();
        if(!currentToken.getName().equals("VALL")){
            currentTokenNumber--;
            numOfRTok--;
        }else{
            content = content + Brain.getChar(Brain.unhashCode(currentToken.getValue().substring(1), i));
            i++;
            if(VALL_CL()){
                if(SM()){
                messageContent();
                }
            }
        }
    }  
    
    
    private boolean FBRK_OP(){
        System.out.println("Вход FBRK_OP");
        ws();
        match();
        if(currentToken.getName().equals("FBRK_OP")){
        return true;
        }
        return false;
    }
    
    private boolean B(){
        System.out.println("Вход B");
        ws();
        match();
        if(currentToken.getName().equals("B")){
        return true;
        }
        return false;
    }
    
    
    
    private boolean ASSIGN_OP(){
        System.out.println("Вход ASSIGN_OP");
        ws();
        match();
        if(currentToken.getName().equals("ASSIGN_OP")){
        return true;
        }
        return false;
    }
    
    private boolean VALL(){
        System.out.println("Вход VALL");
        ws();
        match();
        if(currentToken.getName().equals("VALL")){
        return true;
        }
        return false;
    }
    
    private boolean VALL_CL(){
        System.out.println("Вход VALL_CL");
        ws();
        match();
        if(currentToken.getName().equals("VALL_CL")){
        return true;
        }
        return false;
    }
    
    private boolean SM(){
        System.out.println("Вход SM");
        ws();
        match();
        if(currentToken.getName().equals("SM")){
        return true;
        }
        return false;
    }
    
    private boolean FBRK_CL(){
        System.out.println("Вход FBRK_CL");
        ws();
        match();
        if(currentToken.getName().equals("FBRK_CL")){
        return true;
        }
        return false;
    }
    
    private void ws(){
        System.out.print("Вход в ws"+"\n");
        match();
        if(!currentToken.getName().equals("WS")){
            currentTokenNumber--;
            numOfRTok--;
        }else{
            ws();
        }
        
    }
}