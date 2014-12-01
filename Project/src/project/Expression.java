/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.LinkedList;
/**
 * Classe représentant une liste de production pouvant remplacer une production
 * non-terminale.
 * @author akagami
 */
public class Expression {
    /**
     * Ensemble de mots pouvant remplacer un mot non-terminal.
     */
    private final LinkedList<Production> mots;
    /**
     * Constructeur par défaut : crée une liste d'expression vide.
     */
    public Expression(){
        mots = new LinkedList<>();
    }
    /**
     * Permet d'ajouter une production - un mot - à la liste pouvant remplacer une production.
     * @param mot de type Production : Le mot à ajouter en fin de liste.
     */
    public void addMot(Production mot){
        mots.add(mot);
    }
    /**
     * Supprime tous les mots de la liste.
     */
    public void clearList(){
        mots.clear();
    }
    /**
     * Supprime un mot en particulier de la liste.
     * @param mot de type Production : Le mot à supprimer de la liste.
     */
    public void deleteMot(Production mot){
        mots.remove(mot);
    }
    /**
     * Getter donnant accès à la liste de Productions.
     * @return La liste de Productions.
     */
    public LinkedList<Production> getMots(){
        return mots;
    }
}
