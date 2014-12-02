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
 * Classe représentant le parser de fichier permettant de lire le fichier de grammaire
 * et de le convertir en Grammar.
 * @author akagami
 */
public class Parser {
    /**
     * Liste de toutes les productions relatives à la grammaire en cours de traitement.
     */
    private ArrayList<Production> prods;
    /**
     * Localisation du fichier contenant la grammaire.
     */
    private String loc;
    /**
     * Constructeur par défaut. Crée un parser avec une liste de production vide
     * et pas d'emplacement de fichier.
     */
    public Parser() {
        prods = new ArrayList<>();
        loc = new String();
    }
    /**
     * Constructeur créant un parser avec une lsite vide et une localisation
     * fournie par l'utilisateur.
     * @param localisation de type String : La localisation du fichier.
    */
    public Parser(String localisation) {
        prods = new ArrayList<>();
        loc = localisation;
    }
    /**
     * Setter permettant de changer l'emplacement du fichier de grammaire.
     * @param loc de type String : Le nouvel emplacement du fichier.
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }
    /**
     * Permet de déterminer si une production a déjà été créée ou pas.
     * @param mot : L'étiquette de la production.
     * @return La production si elle existe, null sinon.
     */
    public Production findProd(String mot) {
        for (Production p : prods) {
            if (mot.equals(p.getMot())) {
                return p;
            }
        }
        return null;
    }
    /**
     * Crée et ajoute tous les mots non-terminaux à la liste des Productions
     * à partir du fichier de grammaire.
     */
    public void grammarGetNonTerminalsBNF() {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {
                line = file.readLine();
                
                if (!"BNF".equals(line)) {
                    throw new InvalidFileException();
                }
                while ((line = file.readLine()) != null) {
                    String spl = line.split("::=")[0].trim();
                    if (null == this.findProd(spl)) {
                        prods.add(new Production(spl));
                    }
                }
            }
        } catch (IOException | InvalidFileException e) {
            System.out.println("Incorrect file");
        }
    }
    /**
     * Crée les mots non-terminaux et ajoute les dépendances entre les mots à partir
     * du fichier de grammaire.
     */
    public void grammarCompleteBNF() {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {
                boolean terminal = true;
                String delimiteurs1 = "|";
                String delimiteurs2 = "><\"";
                line = file.readLine();
                
                if (!"BNF".equals(line)) {
                    throw new InvalidFileException();
                }
                while ((line = file.readLine()) != null) {
                    String spl = line.split("::=")[1];
                    StringTokenizer token1 = new StringTokenizer(spl, delimiteurs1);
                    while (token1.hasMoreTokens()) {
                        Expression exp = new Expression();
                        StringTokenizer token2 = new StringTokenizer(token1.nextToken(), delimiteurs2, true);
                        while (token2.hasMoreTokens()) {
                            String s = token2.nextToken();
                            switch (s) {
                                case "\"":
                                    terminal = true;
                                    break;
                                case "<":
                                    terminal = false;
                                    break;
                                case ">":
                                    break;
                                case " ":
                                    break;
                                default:
                                    if (terminal) {
                                        if (this.findProd(s) == null) {
                                            prods.add(new Production(s));
                                        }
                                        exp.addMot(findProd(s));
                                        break;
                                    } else {
                                        if (this.findProd("<"+s+">") == null) {
                                            
                                            throw new InvalidFileException();
                                        }
                                        exp.addMot(findProd("<"+s+">"));
                                        
                                        break;
                                    }
                            }
                            if (!this.findProd(line.split("::=")[0].trim()).findExpr(exp)){
                                this.findProd(line.split("::=")[0].trim()).addExpr(exp);
                            }
                        }
                    }
                }
            }
        } catch (IOException | InvalidFileException e) {
            System.out.println("Incorrect file2");
        }
    }
    /**
     * Traite le fichier de grammaire et renvoie une instance de la classe Grammar
     * correspondante.
     * @return La grammaire correspondante au fichier.
     */
    public Grammar grammarReadBNF() {

        this.grammarGetNonTerminalsBNF();
        this.grammarCompleteBNF();

        return (new Grammar(prods.get(0)));
    }
}