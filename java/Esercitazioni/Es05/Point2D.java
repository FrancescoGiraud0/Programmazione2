/** Gruppo: Giraudo-Favareto
 *  Data: 29-09-2021
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

    // Costruttore di copia
    Point2D(Point2D p){
        x = p.x; y = p.y;
    }

    // metodo di classe per uguaglianza shallow
    static boolean equal(Point2D a, Point2D b){
        return ( (a.x==b.x) && (a.y==b.y) );
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

    boolean equals(Point2D p){ 
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