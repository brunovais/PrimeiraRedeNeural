
package primeiraredeneural;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane game;
    @FXML
    private ImageView queijo;
    private BufferedImage lab;
    private Integer instantRGB;
    private Sensor s;
    private Queijo qjo;
    private Darwin dw;
    private RedeNeural rn;
    public PrimeiraRedeNeural prn = new PrimeiraRedeNeural();
    private Timeline tl;
    private int passarDoTempo = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setLab();
        this.qjo = new Queijo(this.game, this.queijo, this.lab);
        this.s = new Sensor(this.lab, this.game);
        this.rn = new RedeNeural();
        this.tl = new Timeline(
        new KeyFrame(Duration.millis(1), e -> this.atualizar())
        );
        tl.setCycleCount(Animation.INDEFINITE);
        this.config();
    }
    public void config(){
        this.dw = null;
        this.dw = new Darwin(this.game, 60);
        this.dw.setTempoDaGeracao(70);
        tl.play();
    }
    @FXML
    private void atualizar(){
        
        Boolean gameOverVencedor = dw.verificaVencedor();
        Boolean gameOverTime = dw.verificaTempoDaGeracao(this.passarDoTempo);
        Integer direita;
        Double pulseDireita;
        Integer esquerda;
        Double pulseEsquerda;
        Integer cima;
        Double pulseCima;
        Integer baixo;
        Double pulseBaixo;
        Integer tDireita;
        Integer tEsquerda;
        if (gameOverVencedor.equals(true)){
            for (int i = 0; i < this.dw.getPopulacao().size(); i++) {
                if (this.dw.getPopulacao().get(i).getVenceu().equals(true)){
                    this.dw.trataGenoma(this.dw.getPopulacao().get(i).getGenoma().toString());
                }
            }
        }
        if(gameOverTime.equals(true)){
            for (int i = 0; i < this.dw.getPopulacao().size(); i++) {
                if(this.dw.getPopulacao().get(i).getMorreu().equals(false)){
                    this.dw.adicionaNoRanking(this.dw.getPopulacao().get(i), this.qjo.getCheiro(this.dw.getPopulacao().get(i).getPosicaoX(), this.dw.getPopulacao().get(i).getPosicaoY()));
                }
            }
            this.passarDoTempo = 0;
            gameOverTime = false;
            tl.stop();
            this.dw.fimDaGeracao();
            this.dw.reStart();
            this.config();
        }else{
            this.passarDoTempo += 100;
            for (int i = 0; i < this.dw.getPopulacao().size(); i++) {
                if(this.dw.getPopulacao().get(i).getMorreu().equals(false)){
                    direita = this.s.activeSensor(this.dw.getPopulacao().get(i), 3);
                    esquerda = this.s.activeSensor(this.dw.getPopulacao().get(i), 2);
                    cima = this.s.activeSensor(this.dw.getPopulacao().get(i), 0);
                    baixo = this.s.activeSensor(this.dw.getPopulacao().get(i), 1);
                    Integer tDown = this.s.activeSensor(this.dw.getPopulacao().get(0), 6);
                    Integer tUp = this.s.activeSensor(this.dw.getPopulacao().get(0), 7);
                    tDireita = this.s.activeSensor(this.dw.getPopulacao().get(i), 4);
                    tEsquerda = this.s.activeSensor(this.dw.getPopulacao().get(i), 5);
                    
                    pulseDireita = this.rn.multiplicador(this.dw.getPopulacao().get(i), (tDireita + tEsquerda + tDown + tUp + direita + esquerda + cima + baixo), 5);
                    pulseEsquerda = this.rn.multiplicador(this.dw.getPopulacao().get(i), (tDireita + tEsquerda + tDown + tUp + direita + esquerda + cima + baixo), 5);
                    pulseCima = this.rn.multiplicador(this.dw.getPopulacao().get(i), (tDireita + tEsquerda + tDown + tUp + direita + esquerda + cima + baixo), 5);
                    pulseBaixo = this.rn.multiplicador(this.dw.getPopulacao().get(i), (tDireita + tEsquerda + tDown + tUp + direita + esquerda + cima + baixo), 5);
                    
                    //System.out.println("d" + direita.toString());
                    //System.out.println("dt" + direita.toString());
                    //System.out.println(pulseDireita.toString());

                    //tEsquerda = this.s.activeSensor(this.dw.getPopulacao().get(i), 5);

                    //System.out.println("e " + esquerda.toString());
                    //System.out.println("te " + tEsquerda.toString());

                   // System.out.println(pulseEsquerda.toString());

                   // System.out.println("c " + cima.toString());

                    //System.out.println(pulseCima.toString());

                   // System.out.println("b " + baixo.toString());


                    //System.out.println(pulseBaixo.toString());
                    this.decide(this.dw.getPopulacao().get(i), pulseDireita, pulseEsquerda, pulseCima, pulseBaixo); 
                }
            }  
        }
        
    }
    
    @FXML
    private void andaParaCima(Rato agent){
        //System.out.println("CIMA!!!");
        if (agent.getDirecao() != 0){
            agent.setRotacao(180);
            agent.setDirecao(0);
        }
        agent.setPosicaoY(agent.getPosicaoY()-10);
        this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    @FXML
    private void andaParaBaixo(Rato agent){
        //System.out.println("BAIXOO!!");
       if (agent.getDirecao() != 1){
            agent.setRotacao(0);
            agent.setDirecao(1);
        }
        agent.setPosicaoY(agent.getPosicaoY()+10);
        this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    @FXML
    private void andaParaEsquerda(Rato agent){
        //System.out.println("ESQUERDA");
       if (agent.getDirecao() != 2){
            agent.setRotacao(90);
            agent.setDirecao(2);
        }
        agent.setPosicaoX(agent.getPosicaoX()-10);
        this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    @FXML
    private void andaParaDireita(Rato agent){
        //System.out.println("DIREITAA!!");
        if (agent.getDirecao() != 3){
            agent.setRotacao(-90);
            agent.setDirecao(3);
        }
        agent.setPosicaoX(agent.getPosicaoX()+10);
        this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    
    private void setLab(){
        
        try {
            this.lab = ImageIO.read(new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\lab1.png"));
            System.out.println("labirinto ativo");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void getColliders(Rato agent, Integer x, Integer y){
        this.instantRGB =  this.lab.getRGB(x + 10, y + 10);
        Color c = new Color(this.instantRGB);
        Integer blue = c.getBlue();
        Integer red = c.getRed();
        Integer green = c.getGreen();
        
        if ((red + blue + green) < 200) {
           // System.out.println("Colidiu");
            int cheiroo = this.qjo.getCheiro(agent.getPosicaoX(), agent.getPosicaoY());
            agent.kill();
            dw.adicionaNoRanking(agent, cheiroo);
            
        }
        if ((red > 200) && (blue + green) < 100){
          //  System.out.println("VENCEU!");
            agent.setVenceu();
        }
    }
    private void decide(Rato agent, Double pulseDireita, Double pulseEsquerda, Double pulseCima, Double pulseBaixo){
        
        if (pulseDireita > pulseEsquerda && pulseDireita > pulseCima && pulseDireita > pulseBaixo ) {
           // System.out.println("direita");
           this.andaParaDireita(agent);
        }
        if (pulseBaixo > pulseDireita && pulseBaixo > pulseCima && pulseBaixo > pulseEsquerda ) {
          //  System.out.println("baixo");
           this.andaParaBaixo(agent);
        }
        if (pulseEsquerda > pulseDireita && pulseEsquerda > pulseCima && pulseEsquerda > pulseBaixo) {
         //  System.out.println("esquerda");
           this.andaParaEsquerda(agent);
        }
        if (pulseCima > pulseDireita && pulseEsquerda < pulseCima && pulseCima > pulseBaixo) {
           this.andaParaCima(agent);
        }else{
          //  System.out.println("empate");
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void andaParaCimaDbg(){
        //System.out.println("CIMA!!!");
        if (dw.getPopulacao().get(0).getDirecao() != 0){
            dw.getPopulacao().get(0).setRotacao(180);
            dw.getPopulacao().get(0).setDirecao(0);
        }
        dw.getPopulacao().get(0).setPosicaoY(dw.getPopulacao().get(0).getPosicaoY()-10);
        Integer  direita = this.s.activeSensor(this.dw.getPopulacao().get(0), 3);
        Integer  esquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 2);
        Integer  cima = this.s.activeSensor(this.dw.getPopulacao().get(0), 0);
        Integer  baixo = this.s.activeSensor(this.dw.getPopulacao().get(0), 1);
        Integer  tDireita = this.s.activeSensor(this.dw.getPopulacao().get(0), 4);
        Integer  tEsquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 5);
        Integer  tDown = this.s.activeSensor(this.dw.getPopulacao().get(0), 6);
        Integer  tUp = this.s.activeSensor(this.dw.getPopulacao().get(0), 7);
        Integer cheiro = this.qjo.getCheiro( dw.getPopulacao().get(0).getPosicaoX(),  dw.getPopulacao().get(0).getPosicaoY());
        System.out.println("CHEIRO: " + cheiro.toString());
        System.out.println("Direita: " + direita.toString());
        System.out.println("Esquerda: " + esquerda.toString());
        System.out.println("Cima: " + cima.toString());
        System.out.println("baixo: " + baixo.toString());
        System.out.println("tDireita: " + tDireita.toString());
        System.out.println("tEsquerda: " + tEsquerda.toString());
        //this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    @FXML
    private void andaParaBaixoDbg(){
        //System.out.println("BAIXOO!!");
       if (dw.getPopulacao().get(0).getDirecao() != 1){
            dw.getPopulacao().get(0).setRotacao(0);
            dw.getPopulacao().get(0).setDirecao(1);
        }
        dw.getPopulacao().get(0).setPosicaoY(dw.getPopulacao().get(0).getPosicaoY()+10);
        Integer  direita = this.s.activeSensor(this.dw.getPopulacao().get(0), 3);
        Integer  esquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 2);
        Integer  cima = this.s.activeSensor(this.dw.getPopulacao().get(0), 0);
        Integer  baixo = this.s.activeSensor(this.dw.getPopulacao().get(0), 1);
        Integer  tDireita = this.s.activeSensor(this.dw.getPopulacao().get(0), 4);
        Integer  tEsquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 5);
        Integer  tDown = this.s.activeSensor(this.dw.getPopulacao().get(0), 6);
        Integer  tUp = this.s.activeSensor(this.dw.getPopulacao().get(0), 7);
        Integer cheiro = this.qjo.getCheiro( dw.getPopulacao().get(0).getPosicaoX(),  dw.getPopulacao().get(0).getPosicaoY());
        System.out.println("CHEIRO: " + cheiro.toString());
        System.out.println("Direita: " + direita.toString());
        System.out.println("Esquerda: " + esquerda.toString());
        System.out.println("Cima: " + cima.toString());
        System.out.println("baixo: " + baixo.toString());
        System.out.println("tDireita: " + tDireita.toString());
        System.out.println("tEsquerda: " + tEsquerda.toString());
        //this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    @FXML
    private void andaParaEsquerdaDbg(){
        //System.out.println("ESQUERDA");
       if (dw.getPopulacao().get(0).getDirecao() != 2){
            dw.getPopulacao().get(0).setRotacao(90);
            dw.getPopulacao().get(0).setDirecao(2);
        }
        dw.getPopulacao().get(0).setPosicaoX(dw.getPopulacao().get(0).getPosicaoX()-10);
        Integer  direita = this.s.activeSensor(this.dw.getPopulacao().get(0), 3);
        Integer  esquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 2);
        Integer  cima = this.s.activeSensor(this.dw.getPopulacao().get(0), 0);
        Integer  baixo = this.s.activeSensor(this.dw.getPopulacao().get(0), 1);
        Integer  tDireita = this.s.activeSensor(this.dw.getPopulacao().get(0), 4);
        Integer  tEsquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 5);
        Integer  tDown = this.s.activeSensor(this.dw.getPopulacao().get(0), 6);
        Integer  tUp = this.s.activeSensor(this.dw.getPopulacao().get(0), 7);
        Integer cheiro = this.qjo.getCheiro( dw.getPopulacao().get(0).getPosicaoX(),  dw.getPopulacao().get(0).getPosicaoY());
        System.out.println("CHEIRO: " + cheiro.toString());
        System.out.println("Direita: " + direita.toString());
        System.out.println("Esquerda: " + esquerda.toString());
        System.out.println("Cima: " + cima.toString());
        System.out.println("baixo: " + baixo.toString());
        System.out.println("tDireita: " + tDireita.toString());
        System.out.println("tEsquerda: " + tEsquerda.toString());
        //this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
    @FXML
    private void andaParaDireitaDbg(){
        //System.out.println("DIREITAA!!");
        if (dw.getPopulacao().get(0).getDirecao() != 3){
            dw.getPopulacao().get(0).setRotacao(-90);
            dw.getPopulacao().get(0).setDirecao(3);
        }
        dw.getPopulacao().get(0).setPosicaoX(dw.getPopulacao().get(0).getPosicaoX()+10);
        Integer  direita = this.s.activeSensor(this.dw.getPopulacao().get(0), 3);
        Integer  esquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 2);
        Integer  cima = this.s.activeSensor(this.dw.getPopulacao().get(0), 0);
        Integer  baixo = this.s.activeSensor(this.dw.getPopulacao().get(0), 1);
        Integer  tDireita = this.s.activeSensor(this.dw.getPopulacao().get(0), 4);
        Integer  tEsquerda = this.s.activeSensor(this.dw.getPopulacao().get(0), 5);
        Integer  tDown = this.s.activeSensor(this.dw.getPopulacao().get(0), 6);
        Integer  tUp = this.s.activeSensor(this.dw.getPopulacao().get(0), 7);
        Integer cheiro = this.qjo.getCheiro( dw.getPopulacao().get(0).getPosicaoX(),  dw.getPopulacao().get(0).getPosicaoY());
        System.out.println("CHEIRO: " + cheiro.toString());
        System.out.println("Direita: " + direita.toString());
        System.out.println("Esquerda: " + esquerda.toString());
        System.out.println("Cima: " + cima.toString());
        System.out.println("baixo: " + baixo.toString());
        System.out.println("tDireita: " + tDireita.toString());
        System.out.println("tEsquerda: " + tEsquerda.toString());
        //this.getColliders(agent, (int) agent.getPosicaoX(), (int)agent.getPosicaoY());
    }
}