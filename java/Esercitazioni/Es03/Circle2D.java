/** Gruppo Giraudo-Favareto
 *  Data: 06-10-2021
 *  
 *  Implementazione della classe Circle2D che estende Point2D.
*/

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

    public static void main(String[] args){

        try{
            // NOTA: c è visibile solo nello scope dove viene creato, in questo caso solo nel blocco try
            Circle2D c   = new Circle2D( Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            Circle2D c10 = new Circle2D(10, 0, 5);
            Point2D  p10 = new Point2D(10,0);

            // Informazioni sul cerchio
            System.out.println(c);
            System.out.println(c.info());

            // Distanze da testare
            System.out.println( "Distanza da (0,0): " + c.distance() );
            System.out.println( "Distanza da " + p10 + ": " + c.distance(p10));

            // Aggiunta perchè già creato il metodo per le distanze tra cerchi
            System.out.println( "Distanza da cerchio " + c10 + " : " + c.distance(c10));

        }catch(IndexOutOfBoundsException e){
            System.out.println("Errore: inserire almeno tre valori double da terminale");
        }catch(NumberFormatException e){
            System.out.println("Errore: conversione da stringa a double non riuscita");
        }catch(NegativeException e){
            System.out.println("Errore: inserito raggio del cerchio negativo");
        }catch(Exception e){
            System.out.println("Errore: cerchio non creato correttamente, qualcosa è andato storto");
        }
    
    }

}