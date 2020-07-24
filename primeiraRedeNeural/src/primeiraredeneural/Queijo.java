
package primeiraredeneural;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class Queijo {
    private int gambParaVideoxx = 0;
    private int gambParaVideoX = 0;
     private int gambParaVideoY = 0;
     private int countGamb = 0;
     
     
    private BufferedImage laberinto;
    private ImageView queijo;
    private int cheiroMatriz[][];
    private AnchorPane game;
    public Queijo(AnchorPane game, ImageView q, BufferedImage i){
        this.game = game;
        this.queijo = q;
        this.laberinto = i;
        this.cheiroMatriz = new int[(int) this.laberinto.getHeight()][(int) this.laberinto.getWidth()];
    }
        
    public Integer getCheiro(int x, int y){
        x += 10;
        y += 10;
        //dab = sqrt(pow(xb-xa, 2) + pow(yb-ya, 2))
        Double qjoX = this.queijo.getLayoutX();
        Double qjoY = this.queijo.getLayoutY();
        
        Integer dab = (int) Math.floor(Math.sqrt(Math.pow(x - qjoX, 2) + Math.pow(y - qjoY, 2)));
        return dab;
    }
    
}