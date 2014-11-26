/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.LinkedList;
/**
 *
 * @author akagami
 */
public class Grammar {
    
    private LinkedList<Production> prods;
    
    public Grammar(){
        prods = new LinkedList<>();
    }
    
    public void addProd(Production prod){
        prods.add(prod);
    }
    
    public void removeProd(Production prod){
        prods.remove(prod);
    }
    
    public LinkedList<Production> getProds(){
        return prods;
    }
    
    public String generate(){
        String sentence = "";
        //toDo
        return sentence;
    }
}
