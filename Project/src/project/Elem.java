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
public class Elem {
    
    private String name;
    private LinkedList<Elem> succ;
    private boolean finDuGame;
    public static LinkedList<String> sym;
    
    public Elem(){
        name = "";
        succ = new LinkedList<>();
        finDuGame = true;
    }
    
    public Elem(String nom){
        name = nom;
        succ = new LinkedList<>();
        finDuGame = true;
    }
    
    public void addElem(Elem element){
        succ.add(element);
        if (finDuGame){
            finDuGame = false;
        }
    }
    
    public void setFinal(){
        succ.clear();
        finDuGame = true;
    }
}
