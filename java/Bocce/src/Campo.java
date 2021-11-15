import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

class Campo implements Gioco{
    public static final int DEFAULT_TIRI=50;
    private double dimX, dimY;
    private int numTiri;
    private int punti;
    private int indiceBoccino;
    private int numBuche, numBocce;
    private Vector<Boccia> bocce;
    private Vector<Circle2D> buche;
    private Boccia bocciaMov;

    /**
     * Costruttore di Campo che crea i vector di bocce e di buche
     */
    public Campo(){
        bocce = new Vector<Boccia>();
        buche = new Vector<Circle2D>();
    }

    /**
     * Metodo che legge file e crea il campo.
     */
    public void inizializzaLeggendo(String nome){
        try{
            // Scanner per input da file
            Scanner s = new Scanner(new File(nome));

            // Legge dimensioni del campo
            if(s.next().equals("GIOCO")){
                this.dimX = s.nextDouble();
                this.dimY = s.nextDouble();
            }
            
            // Legge numero buche e le aggiunge una per una
            if(s.next().equals("BUCHE")){
                this.numBuche = s.nextInt();
                
                for(int i=0; i<numBuche; i++)
                    aggiungiBuca(s.nextDouble());
            }

            // Legge numero bocce e le aggiunge una per una
            if(s.next().equals("BOCCE")){
                this.numBocce = s.nextInt();
                
                for(int i=0; i<numBocce; i++)
                    aggiungiBoccia(s.nextDouble());

                posizionaBocce();
            }

            s.close();
        }catch(FileNotFoundException e){
            System.out.println("Errore: file inesistente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Metodo per aggiungere bocce */
    public void aggiungiBoccia(double radius){
        Boccia newBoccia = new Boccia(0,0,radius);

        bocce.add(newBoccia);

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
     * Metodo per posizionare la boccia b in modo casuale.
     */
    public void posizionaBoccia(Boccia b){
        // Rientra nel ciclo se la boccia è stata posizionata sopra una buca o su un'altra boccia
        do{
            b.resetCaduta();
            double randX = new Random().nextDouble();
            double randY = new Random().nextDouble();
            double x = b.getRadius() + ((dimX-b.getRadius())-b.getRadius())*randX;
            double y = b.getRadius() + ((dimY-b.getRadius())-b.getRadius())*randY;
            b.moveTo(x, y);
        }while(caduta(b) || controllaUrtiBocce(b));
    }

    /**
     * Metodo per posizionare le bocce in modo casuale.
     */
    public void posizionaBocce(){
        bocce.get(indiceBoccino).moveTo(dimX/2, dimY/2);

        for(int i=0; i<bocce.size(); i++){
            if(i!=indiceBoccino)
                posizionaBoccia(bocce.get(i));
            bocce.get(i).resetCaduta();
            System.err.println("Posizionata boccia " + i);
        }
    }

    /**
     * Metodo per posizionare le buche in campo in modo casuale.
     */
    public void aggiungiBuca(double radius) throws Exception{
        double randX = new Random().nextDouble();
        double randY = new Random().nextDouble();
        double x = radius + (((dimX/2)-20)-radius)*randX; // 20 raggio masssimo boccia
        double y = radius + (((dimY/2)-20)-radius)*randY; // 20 raggio masssimo boccia
        Circle2D newBuca = new Circle2D(0,0,radius);

        // Posiziona casualmente la buca, una per quadrante
        // Viene lasciato lo spazio al centro del campo per il boccino
        switch(buche.size()){
            case 0:
                newBuca.moveTo(x,y);
                break;
            case 1:
                newBuca.moveTo(x+dimX/2,y+dimY/2);
                break;
            case 2:
                newBuca.moveTo(x+dimX/2,y);
                break;
            case 3:
                newBuca.moveTo(x,y+dimY/2);
                break;
            default:
                throw new Exception("Errore: numero massimo di buche raggiunto");
        }

        buche.add(newBuca); // Rivedere coordinate
    }
  
    /** 
     * Ritorna il numero di buche.
     */
    public int numeroBuche(){
        return numBuche;
    }
    
    /** 
     * Ritorna il numero totale di bocce, incluso il boccino,
     * incluse quelle cadute in buca.
     */
    public int numeroBocce(){
        return numBocce;
    }
    
    /** 
     * Ritorna il numero di bocce cadute in buca.
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
     * Imprime al boccino una velocita' avente intensita' e 
     * direzione date, preparandolo per un nuovo tiro.
     * Ogni volta che questa funzione viene chiamata, il numero
     * di tiri a disposizione del giocatore diminuisce di uno.
     * @param intensita modulo del vettore velocita' espresso
     * in centimetri al secondo.
     * @param angoloDirezione  angolo formato dal vettore velocita' misurato 
     * in radianti partendo dalla direzione positiva dell'asse x.
     */
    public void preparaBoccino(double intensita, double angoloDirezione){
        numTiri--;
        bocciaMov=bocce.get(indiceBoccino);
        
        bocciaMov.setModulo(intensita);
        bocciaMov.setAlpha(angoloDirezione);
    }
    
    /**
     * Avanza il gioco di un intervallo di tempo pari a DELTA_T, 
     * spostando la boccia in moto ed eseguendo eventuali urti e cadute 
     * in buca. In assenza di urti diminuisce la velocita' della boccia
     * in moto sottraendo DECREMENTO.
     * Nel caso che la boccia in moto urti le sponde del campo, 
     * la stessa boccia viene rimbalzata indietro.
     * In caso di urto della boccia in moto contro un'altra boccia, 
     * la boccia urtata si mette in moto al posto di quella urtante.
     * Ritorna vero se, dopo, una boccia e' ancora in moto.
     * Ritorna falso se, dopo, tutte le bocce sono ferme.
     */
    public boolean evoluzioneDeltaT(){
        double x1 = bocciaMov.getX()+ DELTA_T*bocciaMov.getModulo()*Math.cos(bocciaMov.getAlpha());
        double y1 = bocciaMov.getY()+ DELTA_T*bocciaMov.getModulo()*Math.sin(bocciaMov.getAlpha());
        bocciaMov.moveTo(x1, y1);
        bocciaMov.decModulo(DECREMENTO);

        if(caduta(bocciaMov)){
            addPunti(1);
        }else{
            controllaUrtiSponde(bocciaMov);
            controllaUrtiBocce();
        }

        System.out.println("Posizione boccino: "+"("+bocce.get(indiceBoccino).getX()+","+bocce.get(indiceBoccino).getY()+")");

        return bocceFerme();
    }
    
    /** 
     * Ritorna l'indice di boccia corrispondente al boccino.
     */
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
        if(n<1){
            throw new Exception("Il numero di tiri deve essere >= 1");
        }
        numTiri=n;
    }
    
    /**
     * Ritorna la coordinata X della posizione della boccia di
     * indice dato, se la boccia non e' gia' caduta in buca, ritorna -1.
     * Gli indici validi sono da 0 a numeroBocce()-1.
     */
    public double bocciaX(int indiceBoccia){
        if(indiceBoccia<=numeroBocce()-1 &&  !bocce.get(indiceBoccia).isCaduta())
            return bocce.get(indiceBoccia).getX();
        
        return -1;
    }

    /**
     * Ritorna la coordinata Y della posizione della boccia di
     * indice dato, se la boccia e' gia' caduta in buca, ritorna -1.
     * Gli indici validi sono da 0 a numeroBocce()-1.
     */
    public double bocciaY(int indiceBoccia){
        if(indiceBoccia<=numeroBocce()-1 &&  !bocce.get(indiceBoccia).isCaduta())
            return bocce.get(indiceBoccia).getY();
        
        return -1;
    }

    /**
     * Ritorna vero se la boccia e' caduta in buca.
     */
    public boolean caduta(Boccia b){
        boolean caduta = false;

        if(b.isCaduta())
            return true;
            
        for(int i=0; i<buche.size() && caduta==false; i++)
            caduta = b.cadutaInBuca(buche.get(i));
        
        return caduta;
    }

    /**
     * Ritorna vero se la boccia di indice dato e' caduta in buca.
     * Gli indici validi sono da 0 a numeroBocce()-1.
     */
    public boolean caduta(int indiceBoccia){
        if(indiceBoccia<bocce.size() && indiceBoccia>=0)
            return caduta(bocce.get(indiceBoccia));

        return false;
    }

    /**
     * Ritorna la coordinata X della posizione della buca di indice dato.
     * Gli indici validi sono da 0 a numeroBuche()-1.
     */
    public double bucaX(int indiceBuca){
        return buche.get(indiceBuca).getX();
    }
    /**
     * Ritorna la coordinata Y della posizione della buca di indice dato. 
     * Gli indici validi sono da 0 a numeroBuche()-1.
     */
    public double bucaY(int indiceBuca){
        return buche.get(indiceBuca).getY();
    }
    
    /** 
     * Ritorna la dimensione del tavolo lungo l'asse X.
     */
    public double campoX(){
        return dimX;
    }

    /** 
     * Ritorna la dimensione del tavolo lungo l'asse Y.
     */
    public double campoY(){
        return dimY;
    }
    
    /**
     * Ritorna il diametro della boccia di indice dato.
     * Gli indici validi sono da 0 a numeroBocce()-1.
     */
    public double diametroBoccia(int indiceBoccia){
        return 2*bocce.get(indiceBoccia).getRadius();
    }
    
    /**
     * Ritorna il diametro della buca di indice dato.
     * Gli indici validi sono da 0 a numeroBuche()-1. 
     */
    public double diametroBuca(int indiceBuca){
        return 2*buche.get(indiceBuca).getRadius();
    }
    
    /**
     * Ritorna vero se e solo se tutte le bocce ancora presenti
     * in campo sono ferme.
     */
    public boolean bocceFerme(){
        for(Boccia b : bocce)
            if(!b.isFerma())
                return false;
        
        return true;
    }

    /**
     * Ritorna vero se e solo se il gioco e' finito, ovvero se il 
     * boccino e' gia' caduto in buca oppure se non ci sono piu' 
     * tiri a disposizione.
     */
    public boolean giocoFinito(){
        return caduta(indiceBoccino()) || numeroTiri()<=0;
    }

    /**
     * Ritorna il punteggio, pari al numero di bocce cadute in
     * buca, escluso il boccino.
     */
    public int punti(){
        return punti;
    }

    /**
     * Controlla se la boccia in movimento urta altre boocce (restituisce true se colpisce una boccia)
     */
    public boolean controllaUrtiBocce(){
        for(Boccia b: bocce){
            if(b!=bocciaMov && b.distance(bocciaMov)<=0){
                b.setModulo(bocciaMov.getModulo());
                b.setAlpha(bocciaMov.getAlpha());
                bocciaMov.setAlpha(0);
                bocciaMov.setModulo(0);
                bocciaMov = b; // La boccia colpita diventa la boccia in movimento
                return true;
            }
        }

        return false;
    }

    /**
     * Controlla se la boccia passata come parametro urta altre bocce.
     * Metodo utilizzato dal posizionaBoccia() per evitare di inserire due bocce sovrappposte in campo.
     * @param boccia
     * @return
     */
    public boolean controllaUrtiBocce(Boccia boccia){
        for(Boccia b: bocce){
            if(b!=boccia && b.distance(boccia)<=0)
                return true;
        }

        return false;
    }

    /**
     * Metodo per la gestione delle collisioni con le sponde della boccia in movimento.
     * @param b
     */
    public void controllaUrtiSponde(Boccia b){
        // Metodo nn funzionante, da rifare completamente
        if(b.getAlpha()<=Math.PI/2){
            if(b.urtoSponde(2, 3, dimX, dimY))
                b.setAlpha(0.25*Math.PI);         //NB RIVEDERE
            else if(b.urtoSponda(2, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
            else if(b.urtoSponda(3, dimX, dimY))
                b.setAlpha(Math.PI-b.getAlpha());

        }else if(b.getAlpha()>Math.PI/2 && b.getAlpha()<=Math.PI){
            if(b.urtoSponde(0, 3, dimX, dimY))
                b.setAlpha(0.75*Math.PI);         //NB RIVEDERE
            else if(b.urtoSponda(0, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
            else if(b.urtoSponda(3, dimX, dimY))
                b.setAlpha(Math.PI-b.getAlpha());

        }else if(b.getAlpha()>Math.PI && b.getAlpha()<=(1.5)*Math.PI){
            if(b.urtoSponde(0, 1, dimX, dimY))
                b.setAlpha(1.25*Math.PI);         //NB RIVEDERE
            else if(b.urtoSponda(0, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
            else if(b.urtoSponda(1, dimX, dimY))
                b.setAlpha(3*Math.PI-b.getAlpha());
        }else{
            if(b.urtoSponde(1, 2, dimX, dimY))
                b.setAlpha(1.75*Math.PI);         //NB RIVEDERE
            else if(b.urtoSponda(2, dimX, dimY))
                b.setAlpha(2*Math.PI-b.getAlpha());
            else if(b.urtoSponda(1, dimX, dimY))
                b.setAlpha(3*Math.PI-b.getAlpha());
        }

    }

    /**
     * Metodo per aggiungere punti al giocatore.
     * @param p
     */
    public void addPunti(int p){
        punti += p;
    }

    /**
     * Metodo per la stampa del campo.
     */
    public String toString(){
        String s = "{";
        s += "Campo: (" + dimX + ", " + dimY + ")\n";
        s += " Bocce: " + numeroBocce() + "\n";
        s += " Buche: " + numeroBuche() + "\n";
        s += " Boccino: " + indiceBoccino() + "\n";
        s += " NumeroTiri: " + numeroTiri() + "\n";
        s += " Punti: " + punti + "\n";
        s += " Bocce: " + bocce + "\n";
        s += " Buche: " + buche + " }";
        return s;
    }
}