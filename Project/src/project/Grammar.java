/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Classe contenant la grammaire : le mot initial et ceux qui peuvent le remplacer.
 * @author akagami
 */
public class Grammar {
    /**
     * Le mot initial et ses remplaçants.
     */
    private Production prod;
    /**
     * Le constructeur par défaut, correspondant à une grammaire vide.
     */
    public Grammar(){
        prod = new Production();
    }
    /**
     * Le constructeur définissant la grammaire à partir d'une production existante.
     * @param prod de type Production : Mot initial de la grammaire.
     */
    public Grammar(Production prod){
        this.prod = prod;
    }
    /**
     * Permet de set l'attribut prod.
     * @param prod de type Production : Nouvelle valeur de l'attribut prod.
     */
    public void setProd(Production prod){
        this.prod = prod;
    }
    /**
     * Renvoie le pointeur vers l'attribut prod.
     * @return Le pointeur de prod.
     */
    public Production getProd(){
        return prod;
    }
    /**
     * Génère un mot à partir de la grammaire.
     * @return La phrase générée par la grammaire.
     */
    public String generate(){
        String result = this.processProd(prod);
        this.resetWeights();
        return result;
    }
    /**
     * Permet de traiter une production en renvoyant un String si le mot est terminal
     * ou en appliquant cette méthode à une expression suivant le mot si il est non terminal.
     * @param p : La production à traiter.
     * @return Le mot terminal.
     */
    public String processProd(Production p) {
        String result = new String();
        int nbOccurence = p.getMinOccurence() + Math.round(Math.round((p.getMaxOccurence() - p.getMinOccurence()) * Math.random()));

        for (int i = 0; i < nbOccurence; i++) {
            if (!p.isTerm()) {
                for (Production p1 : p.getRandomExpr().getMots()) {
                    result += this.processProd(p1);
                }
            } else {
                result = p.getMot();
            }
        }
        return result;
    }
    /**
     * Crée un fichier à l'emplacement fourni.
     * @param location de type String : Donne le nom et l'emplacement du fichier à créer.
     */
    public void fileCreator(String location){
        BufferedWriter file = null;
        try {
            file = new BufferedWriter(new FileWriter(location));
            file.write(this.generate());
        } catch (IOException ex) {
            Logger.getLogger(Grammar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (file != null) {
                    file.flush();
                    file.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Grammar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void resetProdWeights(Production p){
        boolean one = true;
        for (Double d : p.getWeights()){
            one = (one && d == 1.00d);
        }
        if(!one){
            for(int i = 0; i < p.getWeights().size(); i++){
                p.getWeights().set(i,1.00d);
                }
            for(Expression e : p.getExpr()){
                for(Production p1 : e.getMots()){
                    resetProdWeights(p1);
                }
            }
            
        }
    }
    
    public void resetWeights(){
        this.resetProdWeights(prod);
    }
}
