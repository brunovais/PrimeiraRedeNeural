
package primeiraredeneural;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Rato {
    private int probabilidadeDeMutacaoPorGene = 2;
    private Double gravidadeDaMutacao = 0.99d;
    private int mutacaoGrave = 1;   
    private ArrayList<Double> genes = new ArrayList<Double>();
    private ImageView agent;
    private Boolean morreu;
    private Boolean venceu;
    private int direcao;
    private Label number;
    public int granomaCheckPoint = 0;
    public Rato(ImageView agent, Boolean primeiro){
        this.agent = agent;
        this.morreu = false;
        this.venceu = false;
        //this.number = l;
        this.setGenoma();
        //this.setFirstGenoma();
        if (primeiro.equals(false)){
            this.mutacaoGenerica();
       }
    }
    
    private void mutacaoGenerica(){
        Integer numeroRandomico;
        for (int j = 0; j < this.genes.size(); j++) {
            numeroRandomico = (int)(Math.random() * 100);
            if(numeroRandomico < this.probabilidadeDeMutacaoPorGene){
                this.genes.set(j, this.genes.get(j) * (this.gravidadeDaMutacao));
                if(this.mutacaoGrave < (int)(Math.random() * 100)){
                    this.genes.set(j, this.genes.get(j) * -1);
                }
            }
        }       
    }
    public ArrayList<Double> getGenoma(){
        return this.genes;
    }
    public void setFirstGenoma(){
        for (int i = 0; i < 100; i++) {
            this.genes.add((Math.random() * 1000));
            if(50 < (int)(Math.random() * 100)){
                    this.genes.set(i, this.genes.get(i) * -1);
                }
        }
    }
    public void setGenoma(){
        String genomaString = "";
        String[] genomaStringArr;
        try {
            genomaString = new Scanner(new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\genoma.txt")).useDelimiter("\\Z").next();
            
        }catch (FileNotFoundException ex) {
            Logger.getLogger(Rato.class.getName()).log(Level.SEVERE, null, ex);
        }
        genomaStringArr = genomaString.split("\\|");
        for (int i = 0; i < genomaStringArr.length; i++) {
            genes.add( Double.parseDouble(genomaStringArr[i]));
        }
    }
    public int getPosicaoX(){
        return (int) this.agent.getLayoutX();
    }
     public int getPosicaoY(){
        return (int) this.agent.getLayoutY();
    }
    public void setPosicaoX(int x){
        this.agent.setLayoutX(x);
        //this.number.setLayoutX(x);
    }
    public void setPosicaoY(int y){
        this.agent.setLayoutY(y);
        //this.number.setLayoutY(y);
    }
    public void setRotacao(int n){
        this.agent.setRotate(n);
    }
    public void setDirecao(int n){
        this.direcao = n;
    }
    public int getDirecao(){
        return this.direcao;
    }
    public String getID(){
        return this.agent.getId();
    }
    public Boolean getMorreu(){
        return this.morreu;
    }
    public void kill(){
        this.morreu = true;
    }
    public Boolean getVenceu(){
        return this.venceu;
    }
    public void setVenceu(){
        this.venceu = true;
    }
    public ImageView getRato(){
        return this.agent;
    }
       
}
