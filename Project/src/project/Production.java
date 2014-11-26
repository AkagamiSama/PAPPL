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
public class Production {
    
    private String mot; //Mot pouvant être terminal ou non-terminal
    private LinkedList<Expression> expr; //Si le mot est terminal, cette liste
                                         //est vide
    private LinkedList<Double> weights; //Poids pour chaque expression définissant
                                        //sa probabilité à être sélectionnée pour
                                        //le remplacement du mot non-terminal.
    
    public Production(){
        mot = "";
        expr = new LinkedList<>();
        weights = new LinkedList<>();
    }
    
    public Production(String txt){
        mot = txt;
        expr = new LinkedList<>();
        weights = new LinkedList<>();
    }
    
    public String getMot(){
        return mot;
    }
    
    public void setMot(String txt){
        mot = txt;
    }
    
    public void addExpr(Expression expression){
        expr.add(expression);
        weights.add(1.00d);
    }
    
    public void removeExpr(Expression expression){
        int i = expr.indexOf(expression);
        expr.remove(i);
        weights.remove(i);
    }
    
    public void clearExpr(){
        expr.clear();
    }
    
    public LinkedList<Expression> getExpr(){
        return expr;
    }
    
    public LinkedList<Double> getWeights(){
        return weights;
    }
    
    public boolean isTerm(){
        return (expr==null);
    }
    
    public void halfWeight(int i){
        weights.set(i, weights.get(i)/2);
    }
    
    public Expression getRandomExpr(){
        double totalWeight = 0.00d;
        for (double w : weights){
            totalWeight += w;
        }
        int indexRandom = -1;
        double random = Math.random()*totalWeight;
        for (int i = 0; i < expr.size(); i++){
            random -= weights.get(i);
            if (random <= totalWeight){
                indexRandom = i;
                break;
            }
        }
        halfWeight(indexRandom);
        return expr.get(indexRandom);
    }
    
}
