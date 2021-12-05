import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Giraudo Francesco - Favareto Francesco
 * Classe per per la gestione del campo di gioco.
 * La seguente classe implementa l'interfaccia Gioco (@see Gioco).
 * 
 * Vedi anche:
 * @see Boccia 
 * @see Circle2D 
 * @see Point2D
 */
public class Campo implements Gioco{
    /** Numero tiri default */
    public static final int DEFAULT_TIRI=50;
    private double dimX, dimY;
    private int numTiri;
    private int indiceBoccino;
    private int numBuche, numBocce;
    private Vector<Boccia> bocce;
    private Vector<Circle2D> buche;
    private Boccia bocciaMov;

    /**
     * Costruttore di Campo che crea i vector di bocce e di buche.
     */
    public Campo(){
        bocce = new Vector<Boccia>();
        buche = new Vector<Circle2D>();
    }

    /**
     * Metodo per creare il campo leggendo da file.
     * Crea campo della dimesione letta da file e carica buche e bocce, delle dimensioni lette da file.
     * @param nome nome del file da leggere
     */
    public void inizializzaLeggendo(String nome) throws Exception{
        try{
            // Scanner per input da file
            File f = new File(nome);
            Scanner s = new Scanner(f);

            // Legge dimensioni del campo
            if(s.next().equals("GIOCO")){
                this.dimX = s.nextDouble();
                this.dimY = s.nextDouble();
            }
            
            // Legge numero buche e le aggiunge una per una
            if(s.next().equals("BUCHE")){
                this.numBuche = s.nextInt();
                
                for(int i=0; i<numBuche; i++)
                    aggiungiBuca(s.nextDouble()/2);
            }

            // Legge numero bocce e le aggiunge una per una
            if(s.next().equals("BOCCE")){
                this.numBocce = s.nextInt();
                
                for(int i=0; i<numBocce; i++)
                    aggiungiBoccia(s.nextDouble()/2);

                posizionaBocce();
            }

            s.close();
        }catch(FileNotFoundException e){
            System.out.println("Errore: file inesistente");
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Metodo che prende in input il raggio della boccia e la aggiunge al campo.
     * Controllo anche se l'ultima boccia aggiunta è quella con il raggio minore,
     * in questo caso essa diventa il boccino (viene salvato l'indice della boccia).
     * @param radius raggio della boccia da aggiungere
     */
    public void aggiungiBoccia(double radius){
        // Crea boccia in posizione (0,0)
        Boccia newBoccia = new Boccia(0,0,radius);

        bocce.add(newBoccia);
        
        // Metodo size() restituisce il numero di elementi contenuti nel vector
        if(bocce.size()==1){
            // Se è la prima boccia inserita, allora sarà il boccino
            indiceBoccino = 0;
        }else{
            // Se no controlla se è più piccola rispetto alla più piccola inserita fino ad ora
            if(newBoccia.getRadius()<bocce.get(indiceBoccino).getRadius())
                indiceBoccino = bocce.size()-1;
        }
    }

    /**
     * Metodo per posizionare la boccia b in modo casuale in campo.
     * La boccia viene posizionata in una posizione casuale del campo, nel caso in cui la boccia
     * cadrebbe in buca viene ri-posizionata in un'altra posizione generata casualmente.
     * Si ripete questo procedimento fino a che non viene generata una posizione valida.
     * @param b boccia da posizionare in campo
     */
    public void posizionaBoccia(Boccia b){
        do{
            // Viene settato l'attributo caduta della boccia a falso
            b.resetCaduta();
            // Genera casualmente due valori double
            double randX = new Random().nextDouble();
            double randY = new Random().nextDouble();
            // Genera una posizione casuale in campo (con controllo che la boccia non intersechi sponde)
            double x = b.getRadius() + ((dimX-b.getRadius())-b.getRadius())*randX;
            double y = b.getRadius() + ((dimY-b.getRadius())-b.getRadius())*randY;
            // Muove la boccia nella posizione generata
            b.moveTo(x, y);
        }while(caduta(b) || controllaUrtiBocce(b));
        // Rientra nel ciclo se la boccia è stata posizionata sopra una buca o su un'altra boccia

        // Resetta stato caduta della boccia
        b.resetCaduta();
    }

    /**
     * Metodo per posizionare bocce e boccino.
     * Il boccino viene posizionato al centro del campo.
     * Le bocce vengono posizionate in modo casuale in campo, viene chiamato il metodo posizionaBoccia per
     * ogni boccia in campo.
     */
    public void posizionaBocce(){
        // Sposta il boccino al centro del campo
        bocce.get(indiceBoccino).moveTo(dimX/2, dimY/2);

        // Per ogni boccia in campo, tranne il boccino viene chiamato il metodo posizionaBoccia
        for(int i=0; i<bocce.size(); i++){
            if(i!=indiceBoccino)
                posizionaBoccia(bocce.get(i));
        }
    }

    /**
     * Metodo per posizionare le buche in campo in modo casuale.
     * Il campo viene diviso in quattro quadranti, in cui le buche vengono posizionate in modo casuale (una
     * per quadrante).
     * Viene riservato uno spazio centrale per posizionare il boccino.
     * @throws Exception lancia eccezione se raggiunge numero massimo di buche
     * @param radius raggio della buca da aggiungere
     */
    public void aggiungiBuca(double radius) throws Exception{
        // Genera due valori double casuali
        double randX = new Random().nextDouble();
        double randY = new Random().nextDouble();
        // Genera una posizione casuale nel primo quadrante
        double x = radius + (((dimX/2)-20)-radius)*randX; // 20 raggio masssimo boccia (spazio per boccino)
        double y = radius + (((dimY/2)-20)-radius)*randY; // 20 raggio masssimo boccia (spazio per boccino)
        Circle2D newBuca = new Circle2D(0,0,radius);

        // Posiziona casualmente la buca, una per quadrante
        // Viene lasciato lo spazio al centro del campo per il boccino
        switch(buche.size()){
            // Se prima buca da inserire, posiziona in primo quadrante
            case 0:
                newBuca.moveTo(x,y);
                break;
            // Se seconda buca da inserire, sposta in secondo quadrante
            case 1:
                newBuca.moveTo(x+dimX/2,y+dimY/2);
                break;
            // Se terza buca da inserire, sposta in terzo quadrante
            case 2:
                newBuca.moveTo(x+dimX/2,y);
                break;
            // Se quarta buca da inserire, sposta in quarto quadrante
            case 3:
                newBuca.moveTo(x,y+dimY/2);
                break;
            // Lancia eccezione se si prova a inserire più di 4 buche
            default:
                throw new Exception("Errore: numero massimo di buche raggiunto");
        }

        buche.add(newBuca); // Aggiunge buca al vector delle buche
    }
  
    /** 
     * Ritorna il numero di buche.
     * @return numero totale di buche
     */
    public int numeroBuche(){
        return numBuche;
    }
    
    /** 
     * Ritorna il numero totale di bocce, incluso il boccino,
     * incluse quelle cadute in buca.
     * @return numero totale di bocce
     */
    public int numeroBocce(){
        return numBocce;
    }
    
    /** 
     * Ritorna il numero di bocce cadute in buca.
     * @return numero di bocce cadute in buca
     */
    public int numeroCadute(){
        int numCadute=0;

        for(Boccia b : bocce){
            if(b.isCaduta())
                numCadute++;
        }

        return numCadute;
    }
    
    /** 
     * Imprime al boccino una velocita' avente intensita' e direzione date, preparandolo per un nuovo tiro.
     * Ogni volta che questa funzione viene chiamata, il numerodi tiri a disposizione del giocatore diminuisce
     * di uno.
     * @param intensita modulo del vettore velocita' espresso in centimetri al secondo.
     * @param angoloDirezione  angolo formato dal vettore velocita' misurato in radianti partendo dalla
     * direzione positiva dell'asse x.
     */
    public void preparaBoccino(double intensita, double angoloDirezione){
        numTiri--;
        bocciaMov=bocce.get(indiceBoccino);
        
        bocciaMov.setModulo(intensita);
        bocciaMov.setAlpha(angoloDirezione);
    }
    
    /**
     * Avanza il gioco di un intervallo di tempo pari a DELTA_T, spostando la boccia in moto ed eseguendo
     * eventuali urti e cadute in buca. In assenza di urti diminuisce la velocita' della boccia in moto
     * sottraendo DECREMENTO. Nel caso che la boccia in moto urti le sponde del campo, la stessa boccia
     * viene rimbalzata indietro. In caso di urto della boccia in moto contro un'altra boccia, la boccia
     * urtata si mette in moto al posto di quella urtante. Ritorna vero se, dopo, una boccia e' ancora in
     * moto. Ritorna falso se, dopo, tutte le bocce sono ferme.
     * @return vero se ci sono bocce in movimento, falso se sono tutte ferme.
     */
    public boolean evoluzioneDeltaT(){
        // Calcola nuove coordinate dopo uno scatto
        double x1 = bocciaMov.getX()+ DELTA_T*bocciaMov.getModulo()*Math.cos(bocciaMov.getAlpha());
        double y1 = bocciaMov.getY()+ DELTA_T*bocciaMov.getModulo()*Math.sin(bocciaMov.getAlpha());
        // Sposta nelle boccia nella nuova posizione
        bocciaMov.moveTo(x1, y1);
        // Diminuisci modulo del vettore velocita' della boccia in movimento
        bocciaMov.decModulo(DECREMENTO);

        // (La metodo caduta() si occupa di cambiare fermare la boccia, cambiare lo stato in "caduta")
        if(!caduta(bocciaMov)){
            // Se la boccia in movimento non è caduta controlla gli urti con le sponde
            controllaUrtiSponde(bocciaMov);
            // Controlla anche urti con altre bocce
            controllaUrtiBocce();
        }
        
        return bocceFerme();
    }
    
    /** 
     * Ritorna l'indice di boccia corrispondente al boccino.
     * @return indice boccino
     */
    public int indiceBoccino(){
        return indiceBoccino;
    }

    /**
     * Ritorna il numero di tiri di boccino disponibili al giocatore.
     * @return numero di tiri disponibili
     */
    public int numeroTiri(){
        return numTiri;
    }

    /** 
     * Stabilisce il numero di tiri disponibili, che devono essere almeno uno. Solleva eccezione se
     * l'argomento è minore di 1. 
     * @param n  numero di tiri
     */
    public void cambiaNumeroTiri(int n) throws Exception{
        if(n<1){
            throw new Exception("Il numero di tiri deve essere >= 1");
        }
        numTiri=n;
    }
    
    /**
     * Ritorna la coordinata X della posizione della boccia di indice dato, se la boccia e' gia' caduta in 
     * buca, ritorna -1. Gli indici validi sono da 0 a numBocce-1.
     * @param indiceBoccia indice della boccia di cui si vuole conoscere la coordinata X
     * @return coordinata X della boccia
     */
    public double bocciaX(int indiceBoccia){
        // Se l'indice e' valido e la boccia non e' caduta
        if(indiceBoccia<=numeroBocce()-1 &&  !bocce.get(indiceBoccia).isCaduta())
            // Restituisci la coordinata X
            return bocce.get(indiceBoccia).getX();
        
        return -1;
    }

    /**
     * Ritorna la coordinata Y della posizione della boccia di indice dato, se la boccia e' gia' caduta in 
     * buca, ritorna -1. Gli indici validi sono da 0 a numBocce-1.
     * @param indiceBoccia indice della boccia di cui si vuole conoscere la coordinata X
     * @return coordinata Y della boccia
     */
    public double bocciaY(int indiceBoccia){
        // Se l'indice e' valido e la boccia non e' caduta
        if(indiceBoccia<=numeroBocce()-1 &&  !bocce.get(indiceBoccia).isCaduta())
            // Restituisci la coordinata Y
            return bocce.get(indiceBoccia).getY();
        
        return -1;
    }

    /**
     * Metodo che ritorna vero se la boccia è caduta in buca, falso altrimenti.
     * @param b Boccia di cui si vuole conoscere lo stato
     * @return vero se la boccia è caduta in buca, falso altrimenti
     */
    public boolean caduta(Boccia b){
        boolean caduta = false;

        // Se la boccia è caduta in buca ritorna vero
        if(b.isCaduta())
            return true;
        
        // Controlla se la boccia è caduta in una delle buche in campo durante questo scatto
        // Se cade in una delle buche in campo, si interrompe il ciclo
        for(int i=0; i<buche.size() && caduta==false; i++)
            caduta = b.cadutaInBuca(buche.get(i));
        
        return caduta;
    }

    /**
     * Ritorna vero se la boccia di indice dato e' caduta in buca.
     * Gli indici validi sono da 0 a numeroBocce()-1.
     * Overload di caduta(Boccia b).
     * @param indiceBoccia indice della boccia di cui si vuole conoscere lo stato
     * @return vero se la boccia è caduta in buca, falso altrimenti
     */
    public boolean caduta(int indiceBoccia){
        // Se l'indice e' valido
        if(indiceBoccia<bocce.size() && indiceBoccia>=0)
            // Restituisci lo stato della boccia chiamando il metodo con parametro di tipo Boccia
            return caduta(bocce.get(indiceBoccia));

        return false;
    }

    /**
     * Ritorna la coordinata X della posizione della buca di indice dato.
     * Gli indici validi sono da 0 a numeroBuche()-1.
     * @param indiceBuca indice della buca di cui si vuole conoscere la coordinata X
     * @return coordinata X della buca
     */
    public double bucaX(int indiceBuca){
        return buche.get(indiceBuca).getX();
    }
    /**
     * Ritorna la coordinata Y della posizione della buca di indice dato. 
     * Gli indici validi sono da 0 a numeroBuche()-1.
     * @param indiceBuca indice della buca di cui si vuole conoscere la coordinata X
     * @return coordinata X della buca
     */
    public double bucaY(int indiceBuca){
        return buche.get(indiceBuca).getY();
    }
    
    /** 
     * Ritorna la dimensione del campo lungo l'asse X.
     * @return dimensione del campo lungo l'asse X
     */
    public double campoX(){
        return dimX;
    }

    /** 
     * Ritorna la dimensione del campo lungo l'asse Y.
     * @return dimensione del campo lungo l'asse Y
     */
    public double campoY(){
        return dimY;
    }
    
    /**
     * Ritorna il diametro della boccia di indice dato.
     * Gli indici validi sono da 0 a numeroBocce()-1.
     * @param indiceBoccia indice della boccia di cui si vuole conoscere il diametro
     * @return diametro della boccia
     */
    public double diametroBoccia(int indiceBoccia){
        return 2*bocce.get(indiceBoccia).getRadius();
    }
    
    /**
     * Ritorna il diametro della buca di indice dato.
     * Gli indici validi sono da 0 a numeroBuche()-1.
     * @param indiceBuca indice della buca di cui si vuole conoscere il diametro
     * @return diametro della buca
     */
    public double diametroBuca(int indiceBuca){
        return 2*buche.get(indiceBuca).getRadius();
    }
    
    /**
     * Ritorna vero se e solo se tutte le bocce ancora presenti in campo sono ferme.
     * @return vero se e solo se tutte le bocce sono ferme
     */
    public boolean bocceFerme(){
        for(Boccia b : bocce)
            if(!b.isFerma())
                return false;
        
        return true;
    }

    /**
     * Ritorna vero se e solo se il gioco e' finito, ovvero se il boccino e' gia' caduto in buca oppure se non ci sono piu' tiri a 
     * disposizione del giocatore.
     * @return vero se il gioco è finito
     */
    public boolean giocoFinito(){
        // Se tutte le bocce sono cadute in buca e rimane solo il boccino in gioco
        // (il numero di bocce cadute è uguale al totale tranne il boccino ed il boccino non è caduto in buca)
        if(numeroCadute()==numeroBocce()-1 && !bocce.get(indiceBoccino).isCaduta())
            return true;

        // Gestisce eccezione indiceBoccino non valido
        try{
            // Se boccino caduto in buca o numero tiri finiti
            if(caduta(indiceBoccino()) || numeroTiri()<=0)
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return false;
    }

    /**
     * Ritorna il punteggio, pari al numero di bocce cadute in buca, escluso il boccino.
     * @return punteggio giocatore
     */
    public int punti(){
        int punti=numeroCadute();

        if(bocce.get(indiceBoccino).isCaduta())
            punti--;
        
        return punti;
    }

    /**
     * Controlla se la boccia in movimento urta altre bocce (restituisce true se colpisce una boccia).
     * @return vero se la boccia in movimento urta altre bocce
     */
    public boolean controllaUrtiBocce(){
        // Per ogni boccia nel vector bocce
        for(Boccia b: bocce){
            // Se la boccia b non è in movimento, non è caduta e sta urtando la boccia in movimento, 
            if(b!=bocciaMov && b.distance(bocciaMov)<=0 && !b.isCaduta()){
                // La boccia b diventa la nuova boccia in movimento prendendo direzione e velocita' della boccia in movimento
                b.setModulo(bocciaMov.getModulo());
                b.setAlpha(bocciaMov.getAlpha());
                // La boccia in movimento viene fermata
                bocciaMov.setAlpha(0);
                bocciaMov.setModulo(0);

                // La boccia colpita diventa la boccia in movimento
                bocciaMov = b;
                // Ritorna vero
                return true;
            }
        }

        // Ritorna falso se non ci sono urti
        return false;
    }

    /**
     * Controlla se la boccia passata come parametro urta altre bocce.
     * @param boccia boccia da controllare
     * @return vero se la boccia urta altre bocce in campo
     */
    public boolean controllaUrtiBocce(Boccia boccia){
        // Per tutte le bocce in campo
        for(Boccia b: bocce){
            // Se la boccia b non è "boccia", non è caduta e la distanza è <=0, allora c'è un urto
            if(b!=boccia && b.distance(boccia)<=0 && !b.isCaduta())
                return true;
        }

        // La boccia "boccia" non è urta nessuna boccia in campo
        return false;
    }

    /**
     * Metodo per la gestione delle collisioni con le sponde della boccia in movimento.
     * Questo metodo cambia l'angolo formato dal vettore direzione con la direzione positiva dell'asse x in base alla sponde/alle 
     * sponde che viene urtata e la direzione della boccia in movimento.
     * @param b boccia di cui si vuole gestire la collisione con le sponde
     */
    public void controllaUrtiSponde(Boccia b){
        /* Lista sponde:
            - 0: sponda sinistra
            - 1: sponda inferiore
            - 2: sponda destra
            - 3: sponda superiore
        */

        // Boccia con angolo del vettore velocita' rispetto all'asse x in [0, pi/2]
        if(b.getAlpha()<=Math.PI/2){
            // N.B. Può urtare solo sponde 2 e 3!
            // Se urta contemporaneamente con le sponde 2 e 3
            if(b.urtoSponde(2, 3, dimX, dimY))
                // Cambia angolo direzione del vettore velocita'
                b.setAlpha(1.25*Math.PI);
            // Se urta solo sponda 2
            else if(b.urtoSponda(2, dimX, dimY))
                b.setAlpha(Math.PI-b.getAlpha());
            // Se urta solo sponda 3
            else if(b.urtoSponda(3, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
        // Boccia con angolo del vettore velocita' rispetto all'asse x in (pi/2, pi]
        }else if(b.getAlpha()>Math.PI/2 && b.getAlpha()<=Math.PI){
            // N.B. Può urtare solo sponde 0 e 3!
            // Se urta contemporaneamente con le sponde 0 e 3
            if(b.urtoSponde(0, 3, dimX, dimY))
                b.setAlpha(1.75*Math.PI);
            // Se urta solo sponda 0
            else if(b.urtoSponda(0, dimX, dimY))
                b.setAlpha(Math.PI-b.getAlpha());
            // Se urta solo sponda 3
            else if(b.urtoSponda(3, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
        // Boccia con angolo del vettore velocita' rispetto all'asse x in (pi,1.5*pi]
        }else if(b.getAlpha()>Math.PI && b.getAlpha()<=(1.5)*Math.PI){
            // N.B. Può urtare solo sponde 0 e 1!
            // Se urta contemporaneamente con le sponde 0 e 1
            if(b.urtoSponde(0, 1, dimX, dimY))
                b.setAlpha(0.25*Math.PI);
            // Se urta solo sponda 0
            else if(b.urtoSponda(0, dimX, dimY))
                b.setAlpha(3*Math.PI-b.getAlpha());
            // Se urta solo sponda 1
            else if(b.urtoSponda(1, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
        // Boccia con angolo del vettore velocita' rispetto all'asse x in (pi,1.5*pi]
        }else{
            // N.B. Può urtare solo sponde 1 e 2!
            // Se urta contemporaneamente con le sponde 1 e 2
            if(b.urtoSponde(1, 2, dimX, dimY))
                b.setAlpha(0.75*Math.PI);
            // Se urta solo sponda 2
            else if(b.urtoSponda(2, dimX, dimY))
                b.setAlpha(3*Math.PI-b.getAlpha());
            // Se urta solo sponda 1
            else if(b.urtoSponda(1, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
        }

    }

    /**
     * Metodo per la stampa del campo.
     * @return stringa descrizione campo
     */
    public String toString(){
        String s = "{";
        s += " Campo: (" + dimX + ", " + dimY + ")\n";
        s += " Bocce: " + numeroBocce() + "\n";
        s += " Buche: " + numeroBuche() + "\n";
        s += " Boccino: " + indiceBoccino() + "\n";
        s += " NumeroTiri: " + numeroTiri() + "\n";
        s += " Punti: " + punti() + "\n";
        s += " Bocce: " + bocce + "\n";
        s += " Buche: " + buche + " }";
        return s;
    }
}