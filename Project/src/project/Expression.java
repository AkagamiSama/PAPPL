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
public class Expression {
    
    private LinkedList<Production> mots; //Ensemble de mots pouvant remplacer
                                         //un mot non-terminal.
    
    public Expression(){
        mots = new LinkedList<>();
    }
    
    public void addMot(Production mot){
        mots.add(mot);
    }
    
    public void clearList(){
        mots.clear();
    }
    
    public void deleteMot(Production mot){
        mots.remove(mot);
    }
    
    public LinkedList<Production> getMots(){
        return mots;
    }
}
