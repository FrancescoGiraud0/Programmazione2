/** Gruppo: Giraudo-Favareto
 *  Data: 29-09-2021
 */


public class Rectangle2D extends Point2D{

    /** Classe Rectangle2D per rappresentare rettangoli in 2 dimensioni.
     *  Altezza positiva verso l'alto, negativa verso il basso.
     *  Larghezza positiva verso destra, negativa verso sinistra.
    */
    
    private static final double DEFAULT_LEN = 0;
    private double altezza, larghezza;

    // Costrurre senza parametri
    public Rectangle2D(){
        super();
        altezza = DEFAULT_LEN; larghezza=DEFAULT_LEN;
    }

    // Costruttore con altezza e larghezza come parametri
    public Rectangle2D(double altezza, double larghezza){
        super();
        this.altezza = altezza; this.larghezza=larghezza;
        this.normalize();
    }

    // Costruttore coordinate centro, altezza e larghezza
    public Rectangle2D(double x, double y, double altezza, double larghezza){
        super(x,y);
        this.altezza = altezza; this.larghezza=larghezza;
        this.normalize();
    }

    //Funzione che permette di cambiare il valore di "altezza";
    void setAltezza(double a)
    {altezza=a; normalize();}
    
    //Funzione che permette di cambiare il valore di "larghezza";
    void setLarghezza(double l){
        larghezza=l;
        normalize();
    }

    // Restituisce Perimetro rettangolo
    public double perimetro(){
        return 2*Math.abs(altezza)+2*Math.abs(larghezza);
    }

    // Restituisce Area rettangolo
    public double area(){
        return Math.abs(altezza)*Math.abs(larghezza);
    }

    // Per normalizza un rettangolo in modo da non avere altezze e larghezze negative
    public void normalize(){
        if(larghezza<0){
            // Rende larghezza positiva
            larghezza = Math.abs(larghezza);
            // Ricalcola posizione nuovo punto di appoggio
            this.x -= larghezza;
        }

        if(altezza<0){
            // Rende altezza positiva
            altezza = Math.abs(altezza);
            // Ricalcola posizione nuovo punto di appoggio
            this.y -= altezza;
        }
    }

    // Funzione che ritorna tutte le informazioni sull'oggetto
    public String info(){
        String info = this.toString();
        
        // Elimina ultimo carattere .toString()
        info = info.substring(0, info.length()-1);

        // Aggiunge Area e Perimetro
        info += ", area: "+this.area()+", perimetro: "+this.perimetro()+"}";

        return info;
    }

    public String toString(){
        return "{O: ("+x+", "+y+"), h: "+altezza+", l: "+larghezza+"}";
    }


    /** Test main */
    public static void main(String[] args){
        Rectangle2D r1 = new Rectangle2D(-1,-1);

        System.out.println("Informazioni dopo creazione: ");

        System.out.println(r1);
        System.out.println("Perimetro: "+r1.perimetro()+", Area: "+r1.area());
        System.out.println(r1.info());

        System.out.println("\nInformazioni dopo moveTo(): ");

        // Prova metodo moveTo()
        r1.moveTo(1,1);
        System.out.println(r1);
        System.out.println(r1.info());

        System.out.println("\nInformazioni dopo setAltezza(): ");

        // Prova setAltezza
        // Nota metodo normalize() usato in setAltezza()
        r1.setAltezza(-3);
        System.out.println(r1.info());

        System.out.println("\nInformazioni dopo setLarghezza(): ");

        // Prova setLarghezza
        // Nota metodo normalize() usato in setLarghezza()
        r1.setLarghezza(-10);
        System.out.println(r1.info());
    }
}
