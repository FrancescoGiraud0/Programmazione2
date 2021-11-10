import java.lang.Math;

public class Boccia extends Circle2D{

    private double moduloVel;
    private double alphaVel;
    private boolean caduta = false;

    public Boccia(double x, double y, double radius){
        super(x,y,radius);
        caduta = false;
        moduloVel = alphaVel = 0;
    }

    public void setModulo(double m){
        if(m<0) 
            m = -m;

        moduloVel = m;
    }

    public void decModulo(double dec){
        if(moduloVel-dec<0)
            moduloVel=0;
        else
            moduloVel-=dec;
    }

    public void setAlpha(double alpha){
        while(alpha<0 || alpha>=2*Math.PI){
            if(alpha<0)
                alpha += 2*Math.PI;
            else
                alpha -= 2*Math.PI;
        }

        alphaVel = alpha;
    }

    public double getModulo(){
        return moduloVel;
    }

    public double getAlpha(){
        return alphaVel;
    }

    public boolean cadutaInBuca(Buca b){
        Point2D centroBuca = b; // upcasting a Point2D per usare centro come punto
        
        // Se la distanza della boccia (NON del centro della boccia, ma proprio la boccia)
        // dal centro della buca e' minore del raggio della buca, allora cade
        if(distance(centroBuca)<=b.getRadius()){
            caduta = true;
            return true;
        }
        
        return false;
    }

    public boolean isCaduta(){
        return caduta;
    }
    
    public double distanzaSponda(int i, double dimX, double dimY){
        double dist = 0;

        switch(i){
            case 0:
                dist = this.x - this.getRadius();
                break;
            case 1:
                dist = this.y - this.getRadius();
                break;
            case 2:
                dist = dimX - (this.x + this.getRadius());
                break;
            case 3:
                dist = dimY - (this.y + this.getRadius());
                break;
            default:
                // TODO: Segnala errore (Eccezione?)
                break;
        }

        return dist;

    } 

    public boolean urtoSponda(int i, double dimX, double dimY){
        return distanzaSponda(i, dimX, dimY)<=0; 
    } 

    public boolean urtoSponde(int i, int j, double dimX, double dimY){
        return distanzaSponda(i, dimX, dimY)<=0 && distanzaSponda(j, dimX, dimY)<=0;
    } 

    public boolean isFerma(){
        return moduloVel<=0;
    }

    public static void main(String[] args){
        Boccia b = Boccia(50.01, 50.01, 10.1);

        System.out.println(b);
    }

}