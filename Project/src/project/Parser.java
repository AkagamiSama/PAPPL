/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.io.*;

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
    
    public Grammar grammarRead(){
        String line; //Servira à stocker la ligne en cours de traitement.
        try{
            BufferedReader file = new BufferedReader(new FileReader(loc));
            line = file.readLine();
            System.out.println(line);
            
            file.close();
        }
        catch (Exception e){
            System.out.println("Incorrect file");
        }
        
        //return (new Grammar(prods.get(0)));
        return null;
    }
    
}
