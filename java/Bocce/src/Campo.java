class Campo implements Gioco{
    public static final int DEFAULT_TIRI=10;
    private double dimX, dimY;
    private int numTiri=0;
    private int punti=0;
    private int indiceBoccino;
    private int numBuche, numBocce;
    private Boccia[] bocce;
    private Buca[] buche;
    private Boccia bocciaMov;


    public void inizializzaLeggendo(String nome) throws Exception{
        // TODO
    }
  
    /** Ritorna il numero di buche. */
    public int numeroBuche(){
        return numBuche;
    }
    
    /** Ritorna il numero totale di bocce, incluso il boccino,
     * incluse quelle cadute in buca. */
    public int numeroBocce(){
        return numBocce;
    }
    
    /** Ritorna il numero di bocce cadute in buca. */
    public int numeroCadute(){
        // TODO;
    }
    
    /** Imprime al boccino una velocita' avente intensita' e 
     * direzione date, preparandolo per un nuovo tiro.
     * Ogni volta che questa funzione viene chiamata, il numero
     * di tiri a disposizione del giocatore diminuisce di uno.
     * @param intensita modulo del vettore velocita' espresso
     * in centimetri al secondo.
     * @param angoloDirezione  angolo formato dal vettore velocita' misurato 
     * in radianti partendo dalla direzione positiva dell'asse x. */
    public void preparaBoccino(double intensita, double angoloDirezione){
        // TODO
    }
    
    /** Avanza il gioco di un intervallo di tempo pari a DELTA_T, 
     * spostando la boccia in moto ed eseguendo eventuali urti e cadute 
     * in buca. In assenza di urti diminuisce la velocita' della boccia
     * in moto sottraendo DECREMENTO.
     * Nel caso che la boccia in moto urti le sponde del campo, 
     * la stessa boccia viene rimbalzata indietro.
     * In caso di urto della boccia in moto contro un'altra boccia, 
     * la boccia urtata si mette in moto al posto di quella urtante.
     * Ritorna vero se, dopo, una boccia e' ancora in moto.
     * Ritorna falso se, dopo, tutte le bocce sono ferme. */
    public boolean evoluzioneDeltaT(){

    }
    
    /** Ritorna l'indice di boccia corrispondente al boccino. */
    public int indiceBoccino(){
        return indiceBoccino;
    }

    /** Ritorna il numero di tiri di boccino disponibili al giocatore. */
    public int numeroTiri(){
        return numTiri;
    }

    /** Stabilisce il numero di tiri disponibili, che devono essere 
     * almeno uno. Solleva eccezione se l'argomento &egrave; &lt; 1. 
     * @param n  numero di tiri, deve essere &gt;= 1
     */
    public void cambiaNumeroTiri(int n) throws Exception{
        // TODO
    }
    
    /** Ritorna la coordinata X della posizione della boccia di
     * indice dato, se la boccia non e' gia' caduta in buca, ritorna -1.
     * Gli indici validi sono da 0 a numeroBocce()-1. */
    public double bocciaX(int indiceBoccia){
        // TODO
    }

    /** Ritorna la coordinata Y della posizione della boccia di
     * indice dato, se la boccia e' gia' caduta in buca, ritorna -1.
     * Gli indici validi sono da 0 a numeroBocce()-1. */
    public double bocciaY(int indiceBoccia){
        // TODO
    }

    /** Ritorna vero se la boccia di indice dato e' caduta in buca.
     * Gli indici validi sono da 0 a numeroBocce()-1. */
    public boolean caduta(int indiceBoccia){
        // TODO
    }

    /** Ritorna la coordinata X della posizione della buca di indice dato.
     * Gli indici validi sono da 0 a numeroBuche()-1. */
    public double bucaX(int indiceBuca){
        return buche[indiceBuca].getX();
    }
    /** Ritorna la coordinata Y della posizione della buca di indice dato. 
     * Gli indici validi sono da 0 a numeroBuche()-1. */
    public double bucaY(int indiceBuca){
        return buche[indiceBuca].getY();
    }
    
    /** Ritorna la dimensione del tavolo lungo l'asse X. */
    public double campoX(){
        return dimX;
    }
    /** Ritorna la dimensione del tavolo lungo l'asse Y. */
    public double campoY(){
        return dimY;
    }
    
    /** Ritorna il diametro della boccia di indice dato.
     * Gli indici validi sono da 0 a numeroBocce()-1. */
    public double diametroBoccia(int indiceBoccia){
        return 2*bocce[indiceBoccia].getRadius();
    }
    
    /** Ritorna il diametro della buca di indice dato.
     * Gli indici validi sono da 0 a numeroBuche()-1. */
    public double diametroBuca(int indiceBuca){
        return 2*buche[indiceBuca].getRadius();
    }
    
    /** Ritorna vero se e solo se tutte le bocce ancora presenti
     * in campo sono ferme. */
    public boolean bocceFerme(){
        // TODO
    }

    /** Ritorna vero se e solo se il gioco e' finito, ovvero se il 
     * boccino e' gia' caduto in buca oppure se non ci sono piu' 
     * tiri a disposizione. */
    public boolean giocoFinito(){
        // TODO
    }

    /** Ritorna il punteggio, pari al numero di bocce cadute in
     * buca, escluso il boccino. */
    public int punti(){
        return punti;
    }
}