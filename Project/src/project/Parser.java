/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.io.*;

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
     * @throws project.InvalidFileException Renvoie une exception si le fichier fourni est invalide.
     */
    public void grammarGetNonTerminalsBNF() throws InvalidFileException {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {
                line = file.readLine();
                
                if (!"BNF".equals(line)) {
                    throw new InvalidFileException();
                }
                while ((line = file.readLine()) != null) {
                    String spl = line.split("(?<!\\\\)::=")[0].trim();
                    if (null == this.findProd(spl)) {
                        prods.add(new Production(spl));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    /**
     * Crée les mots non-terminaux et ajoute les dépendances entre les mots à partir
     * du fichier de grammaire.
     * @throws project.InvalidFileException Renvoie une exception si le fichier fourni est invalide.
     */
    public void grammarCompleteBNF() throws InvalidFileException {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {
                boolean terminal;
                String delimiteursLigne = "(?<!\\\\)::=";
                String delimiteursProduction = "(?<!\\\\)\\|";
                String delimiteursWords = "(\\s+(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)(?=(((\\\\<)|(\\\\>)|[^<>])*((?<!\\\\)<)((\\\\<)|(\\\\>)|[^<>])*((?<!\\\\)>))*((\\\\<)|(\\\\>)|[^<>])*$)|((?<!\\\\)\"))";
                file.readLine(); //lit la ligne BNF qui ne doit pas être processée dans la boucle
                
                
                while ((line = file.readLine()) != null) {
                    String spl = line.split(delimiteursLigne)[1];
                    String[]  productions = spl.split(delimiteursProduction);
                    for (String prodString : productions) {
                        Expression exp = new Expression();
                        String[] words = prodString.split(delimiteursWords);
                        for (String word : words) {
                            terminal = (!word.endsWith(">")|word.endsWith("\\>"));
                            word = word.replaceAll("\\\\<", "<");
                            word = word.replaceAll("\\\\>", ">");
                            word = word.replaceAll("\\\\\"", "\"");
                            word = word.replaceAll("\\\\::=", "::=");
                            word = word.replaceAll("\\\\\\|", "|");
                            if (terminal) {
                                if (this.findProd(word) == null) {
                                    prods.add(new Production(word));
                                }
                                exp.addMot(findProd(word));
                            }else
                            {
                                if (this.findProd(word) == null) {            
                                    throw new InvalidFileException();
                                }
                                exp.addMot(findProd(word));
                            }
                        }
                        if (!this.findProd(line.split(delimiteursLigne)[0].trim()).findExpr(exp)){
                                this.findProd(line.split(delimiteursLigne)[0].trim()).addExpr(exp);
                        }
                    }
                }
            }
        }catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    /**
     * Traite le fichier de grammaire et renvoie une instance de la classe Grammar
     * correspondante.
     * @return La grammaire correspondante au fichier.
     * @throws project.InvalidFileException Renvoie une exception si le fichier fourni est invalide.
     */
    public Grammar grammarReadBNF() throws InvalidFileException {

        this.grammarGetNonTerminalsBNF();
        this.grammarCompleteBNF();

        return (new Grammar(prods.get(0)));
    }
    
    
    //Méthodes pour l'EBNF. En construction.
    public void grammarGetNonTerminalsEBNF() {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {
                line = file.readLine();
                
                if (!"EBNF".equals(line)) {
                    throw new InvalidFileException();
                }
                while ((line = file.readLine()) != null) {
                    String spl = line.split("(?<!\\\\)=")[0].trim();
                    if (null == this.findProd(spl)) {
                        prods.add(new Production(spl));
                    }
                }
            }
        } catch (IOException | InvalidFileException e) {
            System.out.println("EBNF file should start with \"EBNF\"");
        }
    }    
}