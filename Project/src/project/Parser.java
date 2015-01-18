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
                    String spl = line.split("::=(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)")[0].trim();
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
                String delimiteursLigne = "::=(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)";
                String delimiteursProduction = "\\|(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)";

                String delimiteursWords = "(\\s+(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)(?=(((\\\\<)|(\\\\>)|[^<>])*((?<!\\\\)<)((\\\\<)|(\\\\>)|[^<>])*((?<!\\\\)>))*((\\\\<)|(\\\\>)|[^<>])*$)|((?<!\\\\)\"))";
                file.readLine(); //lit la ligne BNF qui ne doit pas être processée dans la boucle
                
                while ((line = file.readLine()) != null) {
                    if (line.split(delimiteursLigne).length > 1){
                    //Permet de gérer les lignes vides
                    //Utile si on veut que le fichier de grammaire ressemble à quelque chose
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
                                word = word.replaceAll("\\\\n", "\n");
                                if (terminal) {                                    
                                    if (this.findProd(word) == null){
                                        prods.add(new Production(word));
                                    }
                                    exp.addMot(findProd(word));
                                } else{
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

        return new Grammar(prods.get(0));
    }
    
    
    //Méthodes pour l'EBNF. En construction.
    
    public Grammar grammarReadEBNF() throws InvalidFileException {

        this.grammarGetNonTerminalsEBNF();
        this.grammarCompleteEBNF();

        return new Grammar(prods.get(0));
    }
        
    public void grammarGetNonTerminalsEBNF() {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {
                line = file.readLine();
                
                if (!"EBNF".equals(line)) {
                    throw new InvalidFileException();
                }
                while ((line = file.readLine()) != null) {
                    String spl = line.split("=(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)")[0].trim();
                    if (null == this.findProd(spl)) {
                        prods.add(new Production(spl));
                    }
                }
            }
        } catch (IOException | InvalidFileException e) {
            System.out.println("EBNF file should start with \"EBNF\"");
        }
    }    
    
    public void grammarCompleteEBNF() throws InvalidFileException {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            try (BufferedReader file = new BufferedReader(new FileReader(loc))) {

                boolean terminal;
                String delimiteursLigne = "=(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)";
                String delimiteursExpression = "\\|(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)(?=(((\\\\\\{)|[^\\{])*((?<!\\\\)\\{)(\\\\\\{|[^\\{])*((?<!\\\\)\\}))*((\\\\\\{)|[^\\}])*$)";
                file.readLine(); //lit la ligne EBNF qui ne doit pas être processée dans la boucle
                
                while ((line = file.readLine()) != null) {
                    if (line.split(delimiteursLigne).length > 1){
                    //Permet de gérer les lignes vides
                    //Utile si on veut que le fichier de grammaire ressemble à quelque chose
                        String id = line.split(delimiteursLigne)[0].trim();
                        String spl = line.split(delimiteursLigne)[1];
                        String[]  expressions = spl.split(delimiteursExpression);
                        for (String expr : expressions){
                            this.findProd(id).addExpr(this.ebnfReadExpr(expr));
                        }
                      }
                  }
              }
          }catch (IOException e) {
              System.out.println(e.getLocalizedMessage());
          }
    }
    
    public Expression ebnfReadExpr(String expr) throws InvalidFileException{
        Expression result = new Expression();
        String delimiteursWords = ",(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)(?=(((\\\\\\{)|[^\\{])*((?<!\\\\)\\{)(\\\\\\{|[^\\{])*((?<!\\\\)\\}))*((\\\\\\{)|[^\\}])*$)";
        String delimiteursExpression = "\\|(?=(((\\\\\")|[^\"])*((?<!\\\\)\")(\\\\\"|[^\"])*((?<!\\\\)\"))*((\\\\\")|[^\"])*$)(?=(((\\\\\\{)|[^\\{])*((?<!\\\\)\\{)(\\\\\\{|[^\\{])*((?<!\\\\)\\}))*((\\\\\\{)|[^\\}])*$)";
        String[] words = expr.split(delimiteursWords);
        for (String word : words){
            if(word.trim().startsWith("\"")){
                word = word.trim().replaceAll("(?<!\\\\)\"", "");
                if (this.findProd(word) == null) {
                    prods.add(new Production(word));
                }
                result.addMot(findProd(word));
            } else if (word.trim().startsWith("{") && word.trim().endsWith("}")){
                Production p = new Production("",0,Production.MAX_RANDOM);
                word = word.trim().replaceFirst("(?<!\\\\)\\{", "");
                word = word.substring(0, word.trim().length()-1);                
                String[] wordexprs = word.split(delimiteursExpression);
                for(String wordexpr : wordexprs){
                    p.addExpr(ebnfReadExpr(wordexpr));
                }
                result.addMot(p);
            } else if (word.trim().startsWith("{") && word.trim().endsWith(")")){ //regarde là : il devrait passer par ici pour {"prout"} mais ne le fait pas et je vois pas pourquoi
                String nbOccurString = word.trim().split("\\((?=(((\\\\\\{)|[^\\{])*((?<!\\\\)\\{)(\\\\\\{|[^\\{])*((?<!\\\\)\\}))*((\\\\\\{)|[^\\{])*$)")[1]; //string vide à remplacer par parenthese fermante pas entre {}
                nbOccurString = nbOccurString.substring(0, nbOccurString.length()-1);
                String[] nbOccur = nbOccurString.split("-");
                Production p = new Production("",Integer.parseInt(nbOccur[0]),Integer.parseInt(nbOccur[1]));
                word = word.split("\\((?=(((\\\\\\{)|[^\\{])*((?<!\\\\)\\{)(\\\\\\{|[^\\{])*((?<!\\\\)\\}))*((\\\\\\{)|[^\\{])*$)")[0].trim().replaceFirst("(?<!\\\\)\\{", ""); //idem
                word = word.substring(0, word.trim().length()-1);
                String[] wordexprs = word.split(delimiteursExpression);
                for(String wordexpr : wordexprs){
                    p.addExpr(ebnfReadExpr(wordexpr));
                }
                result.addMot(p);
            } else{
                word = word.trim();
                if (this.findProd(word) == null) {
                    System.out.print("error " + word);
                    throw new InvalidFileException();
                }
                result.addMot(findProd(word));
            }
        }
        return result;
    }
    
    public Grammar grammarRead() throws InvalidFileException {
        try (BufferedReader file = new BufferedReader(new FileReader(loc))){
            if("BNF".equals(file.readLine())){
                this.grammarGetNonTerminalsBNF();
                this.grammarCompleteBNF();
            }else{
                this.grammarGetNonTerminalsEBNF();
                this.grammarCompleteEBNF();
            }
        }catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
        return new Grammar(prods.get(0));
    }
}
