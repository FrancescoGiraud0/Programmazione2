import java.lang.Math;

/**
 * Classe per rappresentare Bocce nel campo.
 */
public class Boccia extends Circle2D{
    private double moduloVel=0;
    private double alphaVel=0;
    private boolean caduta=false;

    /**
     * Costruttore della classe Boccia
     * @param x posizione x della boccia
     * @param y posizione y della boccia
     * @param radius raggio della boccia
     */
    public Boccia(double x, double y, double radius){
        super(x, y, radius);
        caduta = false;
        moduloVel = alphaVel = 0;
    }

    /**
     * Metodo che setta modulo velocità della boccia
     * @param m
     */
    public void setModulo(double m){
        if(m<0) 
            m = -m;

        moduloVel = m;
    }

    /**
     * Metodo che decrementa modulo velocità della boccia
     * @param dec valore di decremento
     */
    public void decModulo(double dec){
        if(moduloVel-dec<0)
            moduloVel=0;
        else
            moduloVel-=dec;
    }

    /**
     * Metodo che setta angolo velocità della boccia
     * @param alpha valore dell'angolo in radianti
     */
    public void setAlpha(double alpha){
        while(alpha<0 || alpha>=2*Math.PI){
            if(alpha<0)
                alpha += 2*Math.PI;
            else
                alpha -= 2*Math.PI;
        }

        alphaVel = alpha;
    }

    /**
     * Metodo che restituisce modulo velocità della boccia
     * @return modulo velocità
     */
    public double getModulo(){
        return moduloVel;
    }

    /**
     * Metodo che restituisce angolo velocità della boccia
     * @return angolo alpha in radianti
     */
    public double getAlpha(){
        return alphaVel;
    }

    /**
     * Metodo verifica la caduta nella buca b
     * @param b buca da controllare
     * @return true se la boccia è caduta nella buca b, false altrimenti
     */
    public boolean cadutaInBuca(Circle2D buca){
        Point2D centroBuca = buca; // upcasting a Point2D per usare centro come punto
        Point2D centroBoccia = this; // upcasting a Point2D per usare centro come punto
        
        // Se la distanza tra i centri è minore del raggio della buca
        if(centroBoccia.distance(centroBuca)<=buca.getRadius()){
            caduta = true;
            setModulo(0);
            setAlpha(0);
            return true;
        }
        
        return false;
    }

    /**
     * Metodo per resettare caduta, usato all'inizio del gioco per posizionare
     * le bocce.
     */
    public void resetCaduta(){
        caduta = false;
    }

    /**
     * Metodo che restituisce true se la boccia è caduta in buca, false altrimenti
     * @return true se la boccia è caduta in buca, false altrimenti
     */
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
                dist = -1; // TODO Eccezione
                break;
        }

        return dist;
    }

    /**
     * Metodo per verifica urto con una della 4 sponde del campo
     * @param i indice della sponda (0:sinistra,1:basso,2:destra,3:alto)
     */
    public boolean urtoSponda(int i, double dimX, double dimY){
        return distanzaSponda(i, dimX, dimY)<=0.001;
    } 

    /**
     * Metodo per verifica urto con più sponde del campo contemporaneamente
     * @param i indice della prima sponda (0:sinistra,1:basso,2:destra,3:alto)
     * @param j indice della seconda sponda (0:sinistra,1:basso,2:destra,3:alto)
     * @param dimX  lunghezza del campo
     * @param dimY  altezza y del campo
     * @return
     */
    public boolean urtoSponde(int i, int j, double dimX, double dimY){
        return distanzaSponda(i, dimX, dimY)<=0 && distanzaSponda(j, dimX, dimY)<=0;
    } 

    /**
     * Metodo per verificare se la boccia è ferma
     * @return true se la boccia il modulo è 0, false altrimenti
     */
    public boolean isFerma(){
        return moduloVel<=0;
    }

    /**
     * Metodo per stampare informazioni sulla boccia
     * @return stringa con informazioni sulla boccia
     */
    public String toString(){
        return  "{x:"+getX()+ ", y:"+getY()+", radius:"+getRadius()+
                ", modulo: "+moduloVel+", alpha: "+alphaVel+"}";
    }
}