
package primeiraredeneural;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Sensor {
    private Integer myPositionX;
    private Integer myPositionY;
    private int myDirectionEyes;
    private int sensorRange;
    private int RGB;
    private BufferedImage laberinto;
    private Rato agent;
    private AnchorPane game;
    private Boolean mostraSensor = false;
    
    public Sensor(BufferedImage l, AnchorPane game){
        this.game = game;
        this.laberinto = l;
        
    }
    private void getPosition(){
        this.myPositionX = (int) this.agent.getPosicaoX() + 10;
        this.myPositionY = (int) this.agent.getPosicaoY() + 10;
        
    }
    public Integer activeSensor(Rato agent, int direction){
        this.agent = agent;
        this.myDirectionEyes = direction;
        this.getPosition();
        Integer scan = 0;
        switch(this.myDirectionEyes){
            case 3:
                scan = this.rightScan();
                break;
            case 2:
                scan = this.leftScan();
                break;
            case 1:
                scan = this.downScan();
                break;
            case 0:
                scan = this.upScan();
                break;
            case 4:
                scan = this.tRightScan();
                break;
            case 5:
                scan = this.tLeftScan();
                break;
            case 6:
                scan = this.tDownScan();
                break;
            case 7:
                scan = this.tUpScan();
                break;
        }
        
        return scan;
     }
    public Integer upScan(){
        //System.out.println("scan up");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        for (int y = this.myPositionY; y > 0 ; y --){
            instantRGB =  this.laberinto.getRGB(this.myPositionX, y);
            Color c = new Color(instantRGB);
            blue = c.getBlue();
            red = c.getRed();
            green = c.getGreen();
            Integer tot = blue + red + green;
            if(tot < 200 || pixNumber >= 50){
                return pixNumber/10;
            }
            if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\green.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(this.myPositionX);
                imgv.setLayoutY(y);
                this.game.getChildren().add(imgv);
            }
                pixNumber++;
            }
        return 999; 
    }
    public Integer downScan(){
      // System.out.println("scan down");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        for (int y = this.myPositionY; y < this.laberinto.getWidth() ; y ++){
            instantRGB =  this.laberinto.getRGB(this.myPositionX, y);
            Color c = new Color(instantRGB);
            blue = c.getBlue();
            red = c.getRed();
            green = c.getGreen();
            Integer tot = blue + red + green;
            if(tot < 200 || pixNumber >= 50){
                return pixNumber/10;
            }
            if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\green.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(this.myPositionX);
                imgv.setLayoutY(y);
                this.game.getChildren().add(imgv);
            }
            pixNumber++;
            }
        return 999; 
    }
    
    public Integer leftScan(){
        //System.out.println("scan left");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        for (int x = this.myPositionX; x > 0 ; x --){
                instantRGB =  this.laberinto.getRGB(x, this.myPositionY);
                Color c = new Color(instantRGB);
                blue = c.getBlue();
                red = c.getRed();
                green = c.getGreen();
                Integer tot = blue + red + green;
                if(tot < 200 || pixNumber >= 50){
                    return pixNumber/10;
                }
                if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\green.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(x);
                imgv.setLayoutY(this.myPositionY);
                this.game.getChildren().add(imgv);
                }
                pixNumber++;
            }
        return 999;
    }
    public Integer rightScan(){
        //System.out.println("scan direta");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        for (int x = this.myPositionX; x < this.laberinto.getHeight() ; x ++){
                instantRGB =  this.laberinto.getRGB(x, this.myPositionY);
                Color c = new Color(instantRGB);
                blue = c.getBlue();
                red = c.getRed();
                green = c.getGreen();
                Integer tot = blue + red + green;
                if(tot < 200 || pixNumber >= 50){
                    return pixNumber/10;
                }
                if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\green.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(x);
                imgv.setLayoutY(this.myPositionY);
                this.game.getChildren().add(imgv);
                }
                pixNumber++;
            }
        return 999;
    } 
     public Integer tRightScan(){
        //System.out.println("scan direta");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        int y = this.myPositionY;
        for (int x = this.myPositionX; x < this.laberinto.getHeight() ; x ++){
            instantRGB =  this.laberinto.getRGB(x, y);
            Color c = new Color(instantRGB);
            blue = c.getBlue();
            red = c.getRed();
            green = c.getGreen();
            Integer tot = blue + red + green;
            if(tot < 200 || pixNumber >= 50){
                return pixNumber/10;
            }
            if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\red.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(x);
                imgv.setLayoutY(y);
                this.game.getChildren().add(imgv);
            }
            pixNumber++;
            y--;
        }
        return 999;
    }
     public Integer tLeftScan(){
        //System.out.println("scan left");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        int y = this.myPositionY;
        for (int x = this.myPositionX; x < this.laberinto.getHeight() ; x ++){
            //arrYOF BOUNDS
            instantRGB =  this.laberinto.getRGB(x, y);
            Color c = new Color(instantRGB);
            blue = c.getBlue();
            red = c.getRed();
            green = c.getGreen();
            Integer tot = blue + red + green;
            if(tot < 200 || pixNumber >= 50){
                return pixNumber/10;
            }
            if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\red.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(x);
                imgv.setLayoutY(y);
                this.game.getChildren().add(imgv);
            }
            pixNumber++;
            y++;
        }
        return 999;
    }
    public Integer tDownScan(){
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        int x = this.myPositionX;
        for (int y = this.myPositionY; y < this.laberinto.getWidth(); y --){
            instantRGB =  this.laberinto.getRGB(x, y);
            Color c = new Color(instantRGB);
            blue = c.getBlue();
            red = c.getRed();
            green = c.getGreen();
            Integer tot = blue + red + green;
            if(tot < 200 || pixNumber >= 50){
                return pixNumber/10;
            }
            if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\red.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(x);
                imgv.setLayoutY(y);
                this.game.getChildren().add(imgv);
            }
            pixNumber++;
            x--;
        }
        return 999;
    }
    public Integer tUpScan(){
        //System.out.println("scan left");
        int pixNumber = 0;
        Integer instantRGB = 0;
        Integer blue;
        Integer red;
        Integer green;
        int x = this.myPositionX;
        for (int y = this.myPositionY; y < this.laberinto.getWidth(); y ++){
            instantRGB =  this.laberinto.getRGB(x, y);
            Color c = new Color(instantRGB);
            blue = c.getBlue();
            red = c.getRed();
            green = c.getGreen();
            Integer tot = blue + red + green;
            if(tot < 200 || pixNumber >= 50){
                return pixNumber/10;
            }
            if (this.mostraSensor.equals(true)){
                File file = new File("C:\\Users\\bruno\\Desktop\\primeiraRedeNeural\\src\\primeiraredeneural\\red.jpg");
                Image img = new javafx.scene.image.Image(file.toURI().toString());
                ImageView imgv = new ImageView(img);
                imgv.setLayoutX(x);
                imgv.setLayoutY(y);
                this.game.getChildren().add(imgv);
            }
            pixNumber++;
            x--;
        }
        return 999;
    }
    
}
