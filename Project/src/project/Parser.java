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
 *
 * @author akagami
 */
public class Parser {

    private ArrayList<Production> prods;
    private String loc;

    public Parser() {
        prods = new ArrayList<>();
    }

    public Parser(String localisation) {
        prods = new ArrayList<>();
        loc = localisation;
    }

    public Production findProd(String mot) {
        for (Production p : prods) {
            if (mot.equals(p.getMot())) {
                return p;
            }
        }
        return null;
    }

    public void grammarGetNonTerminalsBNF() {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            BufferedReader file = new BufferedReader(new FileReader(loc));
            line = file.readLine();

            if (!"BNF".equals(line)) {
                throw new InvalidFileException();
            }
            while ((line = file.readLine()) != null) {
                String spl = line.split("::=")[0];
                if (null == this.findProd(spl)) {
                    prods.add(new Production(spl));
                }
            }
            file.close();
        } catch (Exception e) {
            System.out.println("Incorrect file");
        }
    }

    public void grammarCompleteBNF() {
        String line; //Servira à stocker la ligne en cours de traitement.
        try {
            BufferedReader file = new BufferedReader(new FileReader(loc));
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
                                    if (this.findProd("<"+s+"> ") == null) {
                                        
                                        throw new InvalidFileException();
                                    }
                                    exp.addMot(findProd("<"+s+"> "));
                                    
                                    break;
                                }
                        }
                        this.findProd(line.split("::=")[0]).addExpr(exp);
                    }
                }
            }

            file.close();
        } catch (IOException | InvalidFileException e) {
            System.out.println("Incorrect file2");
        }
    }

    public Grammar grammarReadBNF() {

        this.grammarGetNonTerminalsBNF();
        this.grammarCompleteBNF();

        return (new Grammar(prods.get(0)));
    }
}
