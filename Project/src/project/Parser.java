/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author akagami
 */
public class Parser {
    
    private ArrayList<Production> prods;
    private String loc;
    
    public Parser(){
        prods = new ArrayList<>();
    }
    
    public Parser(String localisation){
        prods = new ArrayList<>();
        loc = localisation;
    }
    
    public Production findProd(String mot){
        for (Production p : prods){
            if (mot.equals(p.getMot())){
                return p;
            }
        }
        return null;
    }
    
    public void grammarGetNonTerminalsBNF(){
        String line; //Servira à stocker la ligne en cours de traitement.
        try{
            BufferedReader file = new BufferedReader(new FileReader(loc));
            line = file.readLine();
            
            if (!"BNF".equals(line)){
                throw new InvalidFileException();
            }
            while ((line = file.readLine()) != null){
                String spl = line.split("::=")[0];
                if (null == this.findProd(spl.substring(1, spl.length()-2))){
                    prods.add(new Production(spl.substring(1, spl.length()-2)));
                }
            }
            file.close();
        }
        catch (Exception e){
            System.out.println("Incorrect file");
        }
        System.out.println(prods.get(2).getMot());
    }
    
    public void grammarCompleteBNF(){
        String line; //Servira à stocker la ligne en cours de traitement.
        try{
            BufferedReader file = new BufferedReader(new FileReader(loc));
            String delimiteurs1 = "|";
            String delimiteurs2 = "><\"";
            line = file.readLine();
            
            if (!"BNF".equals(line)){
                throw new InvalidFileException();
            }
            while ((line = file.readLine()) != null){
                String spl = line.split("::=")[1];
                StringTokenizer token1 = new StringTokenizer(spl, delimiteurs1, true);
                while (token1.hasMoreTokens()){
                    Expression exp = new Expression();
                    StringTokenizer token2 = new StringTokenizer(token1.nextToken(),delimiteurs2);
                }
            }
            
            
            file.close();
        }
        catch (Exception e){
            System.out.println("Incorrect file");
        }
    }
    
    public Grammar grammarReadBNF(){
        
        this.grammarGetNonTerminalsBNF();
        this.grammarCompleteBNF();
        
        //return (new Grammar(prods.get(0)));
        return null;
    }
}
