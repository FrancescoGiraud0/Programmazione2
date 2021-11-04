/** Gruppo: Giraudo-Favareto
 * 
 *  Classe per descrivere un punto in un piano cartesiano.
 *  Un punto ha coordinate x e y, valori double.
 *  Implementati anche metodi per la distanza tra punti, spostameto di un punto nello spazio,
 *  shallow equality e shallow copy.
*/

class Point2D{
    // coordinate di questo punto
    double x, y;
    // coordinate di default
    static final private double DEFAULTCOORD = 0.0;

    // costruttore di classe con 2 parametri
    public Point2D(double x, double y){
        this.x=x; this.y=y;
    }
    

    // costruttore senza parametri
    public Point2D(){
        x=y=DEFAULTCOORD;
    }

    // Costruttore di copia
    public Point2D(Point2D p){
        x = p.x; y = p.y;
    }

    // restituisce il valore della prima coordinata
    public getX(){
        return x;
    }

    // restituisce il valore della seconda coordinata
    public getY(){
        return y;
    }

    // metodo di classe per uguaglianza shallow
    public static boolean equal(Point2D a, Point2D b){
        return ( (a.x==b.x) && (a.y==b.y) );
    }

    // assegna le coordinate di questo punto
    public void moveTo(double xx, double yy){
        x= xx; y= yy;
    }

    // calcola la distanza di questo punto dall'origine
    public double distance(){
        return Math.sqrt(x*x+ y*y);
    }

    // calcola la distanza di questo punto dal punto p
    public double distance(Point2D p){
        return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y));
    }

    public boolean equals(Point2D p){ 
        return ( (p.x==x) && (p.y==y) );
    }

    Point2D copy(){ 
        return new Point2D(x,y);
    }

    // metodo per stampa oggetto
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}