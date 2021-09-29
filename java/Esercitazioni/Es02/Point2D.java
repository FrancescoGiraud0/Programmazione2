/** Gruppo: Giraudo-Favareto
 *  Data: 29-09-2021
 * 
 *  Consegnare la classe Point2D completa di tutte le parti aggiunte e una nuova funzione "main"
 *  (nella stessa classe Point2D o in un'altra classe) che faccia le seguenti cose:
 * 
 *      - Crea un punto con due coordinate prese dalla command-line
 *      - Crea un altro punto in (10,10)
 *      - Crea un terzo punto con il costruttore di default
 *      - Stampa i tre punti
 *      - Stampa le distanze fra tutte le possibili coppie di punti 
 * 
 *  Nelle stampe ci devono essere anche le parole necessarie a capire che cosa viene stampato 
 *  (supponete che l'output venga letto da qualcuno che non sa che cosa vi ho chiesto di fare).
 * 
 *  Il programma non si deve piantare se sulla command-line sono dati meno di due argomenti 
 *  (guardate la lunghezza dell'array e se < 2 non continuate).Invece può anche piantarsi se le
 *  stringhe sulla command-line non rappresentano numeri (lasciate che Java dia i suoi errori).
*/

class Point2D{
    // coordinate di questo punto
    double x, y;
    // coordinate di default
    static final double DEFAULTCOORD = 0.0;

    // costruttore di classe con 2 parametri
    public Point2D(double x, double y){
        this.x=x; this.y=y;
    }

    // costruttore senza parametri
    public Point2D(){
        x=y=DEFAULTCOORD;
    }

    // assegna le coordinate di questo punto
    public void moveTo(double xx, double yy){
        x= xx; y= yy;
    }

    // calcola la distanza di questo punto dall'origine
    double distance(){
        return Math.sqrt(x*x+ y*y);
    }

    // calcola la distanza di questo punto dal punto p
    double distance(Point2D p){
        return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y));
    }

    // metodo per stampa oggetto
    public String toString(){
        return "Punto (" + x + "," + y + ")";
    }

    /** Main per Point2D */
    public static void main(String[] args){

        // Controlla che ci siano almeno 2 valori in args
        // Termina se args.length<2
        if(args.length<2){
            return;
        }

        // Crea punto p con le coordinate prese da console
        Point2D p1 = new Point2D(Double.parseDouble(args[0]), Double.parseDouble(args[1]));

        // Crea un punto in (10,10)
        Point2D p2 = new Point2D(10,10);

        // Crea un punto con il costruttore di default
        Point2D p3 = new Point2D();

        // Stampa i tre punti
        System.out.println("Punto 1 -> " + p1);
        System.out.println("Punto 2 -> " + p2);
        System.out.println("Punto 3 -> " + p3);

        // Stampa le distanze fra tutte le possibili coppie di punti
        // Distanza 1<->2
        System.out.println("\nDistanza Punto1<->Punto2 = "+p1.distance(p2));

        // Distanza 1<->3
        System.out.println("Distanza Punto1<->Punto3 = "+p1.distance(p3));

        // Distanza 2<->3
        System.out.println("Distanza Punto2<->Punto3 = "+p2.distance(p3));

    }
}