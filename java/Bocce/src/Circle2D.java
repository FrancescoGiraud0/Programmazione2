/** Gruppo Giraudo-Favareto
 *  Data: 18-10-2021
*/

import java.util.*;

public class Circle2D extends Point2D{
    private double radius;

    // Classe per eccezione raggio negativo
    class NegativeException extends Exception{
        NegativeException(String msg){ 
            super(msg + " in " + Circle2D.this.toString());
        }
    }

    // Costruttore con 3 parametri
    public Circle2D(double x, double y, double radius) throws NegativeException{
        // Imposta coordinate centro
        super(x,y);
        // Setta il raggio ( gestione dell'eccezione in setRadius() )
        setRadius(radius);

    }

    // Metodo per settare il raggio del cerchio
    public void setRadius(double radius) throws NegativeException{
        // Solleva eccezione se radius è negativo
        if(radius>=0)
            this.radius = radius;
        else
            throw new NegativeException("Negative radius " + radius);
    }

    // Metodo per ottenere il raggio
    public double getRadius(){
        return radius;
    }

    // Metodo che ritorna area del cerchio
    public double area(){
        return Math.PI*radius*radius;
    }
    
    // Metodo che ritorna la circonferenza del cerchio
    public double circonferenza(){
        return 2*Math.PI*radius;
    }

    // Metodo che ritorna la distanza da un punto generico p
    public double distance(Point2D p){
        // Calcola distanza centro-punto
        double dist_p = super.distance(p);

        // Controlla che non sia punto interno al cerchio
        if(dist_p<radius)
            return 0;
        else
            // Restituisci la differenza tra distanza centro-punto e il raggio
            // quindi restituisce la distanza tra p e il punto della circonferenza
            // più vicino
            return dist_p-radius;
    }

    // Distanza dall'origine
    public double distance(){
        // Restituisce distanza valore di distance rispetto al punto (0,0)
        return distance(new Point2D(0,0));
    }

    // Distanza da un altro cerchio c
    public double distance(Circle2D c){
        // Upcasting a Point2D per ottenere centro come punto
        Point2D centro_c = c;
        
        // Se la distanza tra i due centri è minore della somma dei raggi
        // allora i due cerchi si intersecano (distanza=0)
        if(super.distance(centro_c)<=c.getRadius()+this.getRadius())
            return 0;
        else
            // Restituisce la distanza tra i punti delle circonferenze più vicini tra loro 
            return super.distance(c)-c.getRadius()-this.getRadius();
    }

    public String toString(){
        return "{O: (" + x + "," + y + ") radius: " + getRadius() + "}";
    }

    // Metodo per informazioni oggetto
    public String info(){
        return "{O: (" + x + "," + y + ") radius: " + getRadius() + " area: "+area()+" crf: "+circonferenza()+"}";
    }

}