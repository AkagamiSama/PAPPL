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
    
    private LinkedList<Production> prods; //Pour la génération, on considère que
                                          //le premier élément correspond au début (S)
    
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
        /*
        J'ai un peu réfléchi (sisi, ça arrive) et je pense qu'ilvaut mieux faire
        une nouvelle classe pour générer avec une grammaire en attribut parce que
        si on veut faire de façon récursive, ce qui serait pas mal, on peut pas
        juste faire une méthode ici.
        L'idée serait de faire une nouvelle classe donc, avec un attribut Grammar,
        un attribut String et un attribut LinkedList<Production> qui démarrerait
        avec le premier élément de la liste prods de la Grammar.
        Pour la génération, on modifiera cete liste et il faudra faire une méthode
        qui testera si la liste est pleine de terminaux ou pas pour déterminer si
        on doit continuer ou pas. Après il suffira de parcourir la liste dans l'ordre
        et quand on tombe sur un mot non-terminal, on prend ce qui a avant, ce qui
        a après et on assemble ça avec le mot transformé.
        Schématiquement, pour S := AB; A := aBa ; B := b :
        <S>
        <A,B>
        <a,B,a,B>
        <a,b,a,B>
        <a,b,a,b>
        Et après on met tous dans la String.
        
        Cela dit, c'est juste une idée. Si tu vois une meilleure solution hésite
        pas ^^
        */
        return sentence;
    }
}
