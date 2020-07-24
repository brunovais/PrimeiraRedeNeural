
package primeiraredeneural;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Darwin {
    private int numeroDeIndividos;
    private ArrayList<Rato> populacao = new ArrayList<Rato>();
    private AnchorPane game;
    private ArrayList<Integer> rank = new ArrayList<Integer>();
    private ArrayList<String> idRank = new ArrayList<String>();
    private int tempoDaGeracao;
    private int numeroDeCampeoes;
    
    public Darwin(AnchorPane game, int ni){
        this.game = game;
        this.numeroDeIndividos = ni;
        this.geraPopulacao();
    }
    
    private void geraPopulacao(){
        
        for (Integer i = 0; i < this.numeroDeIndividos; i++) {
            //Label l = new Label();
            //l.setLayoutX(35);
            //l.setLayoutY(24);
            //l.setScaleX(2d);
            //l.setScaleY(2d);
            //l.setText(i.toString());
            File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\rato.png");
            Image img = new javafx.scene.image.Image(file.toURI().toString());
            ImageView imgv = new ImageView(img);
            imgv.setFitHeight(25);
            imgv.setFitWidth(23);
            imgv.setLayoutX(35);
            imgv.setLayoutY(24);
            imgv.setId(i.toString());
            if(i > 0){
                imgv.setVisible(false);
                //l.setVisible(false);
            }
            game.getChildren().add(imgv);
            //game.getChildren().add(l);
            if(i == 0){
                this.populacao.add(new Rato((ImageView) game.lookup("#" + i.toString()), true));
            }
            else{
                this.populacao.add(new Rato((ImageView) game.lookup("#" + i.toString()), false));
            }
            
        }
    }
    public ArrayList<Rato> getPopulacao(){
        return this.populacao;
    }
    public void adicionaNoRanking(Rato rato, Integer cheiro){
        //if(cheiro == 0 && rato.getMorreu().equals(true)){
        //    System.out.println("mortos não entram no ranking");
       // }
        if(cheiro > 0){
            this.idRank.add(rato.getID());
          if(rato.getMorreu().equals(true)){
                this.rank.add(cheiro);
            }
            else{
                this.rank.add(cheiro);
            }
        }

    }
    
    public Boolean verificaVencedor(){
        for (int i = 0; i < this.populacao.size(); i++) {
            if (this.populacao.get(i).getVenceu().equals(true)){
                return true;
            }
        }
        return false;
    }

    public boolean verificaTempoDaGeracao(int atual){
        if((this.tempoDaGeracao * 1000) <= atual){
            return true;
        }
        return false;
    }
    public void setTempoDaGeracao(int segundos){
        this.tempoDaGeracao = segundos;
    }
    public void fimDaGeracao(){
       // for (int i = 0; i < this.rank.size(); i++) {
          //  if(this.rank.get(i) > -1){
             //  this.rank.remove(i);
           //    this.idRank.remove(i);
         //   }
       // }
        this.personalSort();
        this.selecionaMelhoresGenes();
        
    }
    private void personalSort() {
            Integer numAux = 0;
            String idRato = "";
            for(int x = 0; x < this.rank.size(); x++) {
                    for(int y = 0; y < this.rank.size(); y++) {
                            if (x != y && this.rank.get(x) < this.rank.get(y)) {
                                    numAux = this.rank.get(y);
                                    idRato = this.idRank.get(y);
                                    this.rank.set(y, this.rank.get(x));
                                    this.idRank.set(y, this.idRank.get(x));
                                    this.rank.set(x, numAux);
                                    this.idRank.set(x, idRato);
                                    numAux = 0;
                                    idRato = "";
                                    y = 0;
                            }
                    }
            }
    }
    private void selecionaMelhoresGenes(){
        System.out.println("idRanki: " + this.idRank);
        System.out.println("ranking: " + this.rank.toString());
        System.out.println("campeao: " + this.idRank.get(0));
        Integer idCampeao = Integer.parseInt(this.idRank.get(0));
        String genomaCampeao = this.populacao.get(idCampeao).getGenoma().toString();
        this.trataGenoma(genomaCampeao);
       
    }
    
    public void trataGenoma(String genoma){
        genoma = genoma.replaceAll(", ", "|");
        genoma = genoma.replace("[", "");
        genoma = genoma.replace("]", "");
        this.salvaGenoma(genoma);
    }
    private void salvaGenoma(String genoma){
        //gambiarra D+ 
        System.out.println("genoma final: " + genoma);
        do{
            File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\genoma.txt");
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\genoma.txt");
                fw.write(genoma);
                System.out.println("escreveu!");
                fw.close();
                break;
                
            } catch (IOException ex) {
                System.out.println("excluiu");
                file.delete();
            }            
        }while(true);
    }
    public void reStart(){
        for (int i = 0; i < this.populacao.size(); i++) {
          game.getChildren().remove(this.populacao.get(i).getRato());
        }
        this.idRank = null;
        this.rank = null;
        this.populacao = null;
    }
}
