/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
/**
 *
 * @author akagami
 */
public class Production {
    
    private String mot; //Mot pouvant être terminal ou non-terminal
    private ArrayList<Expression> expr; //Si le mot est terminal, cette liste
                                         //est vide
    private ArrayList<Double> weights; //Poids pour chaque expression définissant
                                        //sa probabilité à être sélectionnée pour
                                        //le remplacement du mot non-terminal.
    
    public Production(){
        mot = "";
        expr = new ArrayList<>();
        weights = new ArrayList<>();
    }
    
    public Production(String txt){
        mot = txt;
        expr = new ArrayList<>();
        weights = new ArrayList<>();
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
    
    public ArrayList<Expression> getExpr(){
        return expr;
    }
    
    public ArrayList<Double> getWeights(){
        return weights;
    }
    
    public boolean isTerm(){
        return (expr.size()==0);
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
            if (random <= 0.0d){
                indexRandom = i;
                break;
            }
        }
        halfWeight(indexRandom);
        return expr.get(indexRandom);
    }
    
}
