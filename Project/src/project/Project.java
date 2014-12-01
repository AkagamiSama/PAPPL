/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import GUI.MainGUI;

/**
 *
 * @author akagami
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Production a = new Production("pute ");
//        Production b = new Production("pute ");
//        Production c = new Production("pute ");
//        
//        Production B = new Production("B");
//        Expression Be1 = new Expression(); 
//        Be1.addMot(b);
//        B.addExpr(Be1);
//        Expression Be2 = new Expression();
//        Be2.addMot(c);
//        B.addExpr(Be2);
//        
//        Production A = new Production("A");
//        Expression Ae1 = new Expression();
//        Ae1.addMot(a);
//        Ae1.addMot(B);
//        A.addExpr(Ae1);
//        Expression Ae2 = new Expression();
//        Ae2.addMot(a);
//        Ae2.addMot(A);
//        Ae2.addMot(A);
//        A.addExpr(Ae2);
//        
//        Production S = new Production("S");
//        Expression Se = new Expression();
//        Se.addMot(A);
//        Se.addMot(B);
//        S.addExpr(Se);
//        Grammar g = new Grammar(S);
//        
//        String test = g.generate();
//        
//        //System.out.println(test);
//        
        //Parser parser = new Parser("Grammar.txt");
        //parser.grammarReadBNF().fileCreator("C:\\Users\\Benjamin\\Desktop\\Cours\\PAPPL\\text.txt");
        MainGUI window = new MainGUI();
        window.setVisible(true);
    }
}
