
package primeiraredeneural;

import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class RedeNeural {
    
    private Rato agent;
    private ArrayList<Double> genes = new ArrayList<Double>();
    private Double ew1;
    private Double ew2;
    private Double ew3;
    private Double ew4;
    private Double neuronio;
    //private ArrayList<Double> sinapse = new ArrayList<Double>();
    
    public Double multiplicador(Rato agent, Integer eyes, int nNeuronio){
        ArrayList<Double> sinapse = new ArrayList<Double>();
        this.agent = agent;
        int i = this.agent.granomaCheckPoint;
        int fim = this.agent.granomaCheckPoint + 20;
        this.getGenes();
        while(i < fim){
            //System.out.println("Peso" + i + ": " + this.genes.get(i).toString());
            this.ew1 = ((this.genes.get(i)/10000) * (eyes));
            i++;
            this.ew2 = ((this.genes.get(i)/10000) * (eyes));
            i++;
            this.ew3 = ((this.genes.get(i)/10000)* (eyes));
            i++;
            this.ew4 = ((this.genes.get(i)/10000) * (eyes));
            i++;
            sinapse.add(this.setNeuronio(this.ew1 ,  this.ew2,  this.ew3,  this.ew4, i));
        }
        if (fim == 100) {
            fim = 0;
        }
        this.agent.granomaCheckPoint = fim;
        return this.outputSinapse(sinapse);
    }
    
    private void getGenes(){
        this.genes = this.agent.getGenoma();
    }
    //tangente hiperbolica
    private Double setNeuronio(Double enw1, Double enw2, Double enw3, Double enw4, int i){
        Double x = enw1 + enw2 + enw3 + enw4;
        
        //System.out.println(enw1.toString());
        //System.out.println(enw2.toString());
        //System.out.println(enw3.toString());
        //System.out.println(enw4.toString());
       

        //Double euler = 2.7182818284590;
        Double sinapse = 1/(1 + Math.exp(-x));
        //Double cosh = (Math.pow(euler, x) + Math.pow(euler, -x))/2;
        //Double senh = (Math.pow(euler, x) - Math.pow(euler, -x))/2;
        //Double sinapse = senh/cosh;
        //System.out.println("pulso: " + sinapse.toString());
        return sinapse;
    }
    
    private Double outputSinapse(ArrayList<Double> sinapse){
        Double pulse = 0d;
        for (int i = 0; i < sinapse.size(); i++) {
            pulse += sinapse.get(i);
        }
        pulse = pulse/5;
        //System.out.println("sinapse: " + pulse.toString());
        return pulse;
    }

}
