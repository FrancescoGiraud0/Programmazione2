/** Specifiche della classe per il motore del gioco.
 * Se una classe implementa correttamente questa interfaccia, potr&agrave;
 * essere usata nel visualizzatore.
 * <P>
 * Il gioco avviene su un campo rettangolare con sponde.
 * Nel campo ci sono da una a otto bocce, di cui una distinta come boccino.
 * Le bocce hanno in generale ciascuna un diametro diverso, il boccino
 * ha il diametro pi&ugrave; piccolo di tutte le altre.
 * <BR>
 * Il campo ha anche da una a quattro buche rotonde di diametro
 * maggiore di quello di qualunque boccia.
 * <BR>
 * Il giocatore tira il boccino, questa operazione imprime al boccino
 * una velocit&agrave;.
 * Finch&eacute; non incontra ostacoli, la boccia in moto (inizialmente 
 * il boccino) prosegue in linea retta.
 * Ma durante il suo moto pu&ograve; incontrare degli ostacoli:
 * <UL>
 * <LI>
 * Se il centro della boccia in moto viene a trovarsi dentro una buca,
 * la boccia cade in buca ed esce dal gioco.
 * <LI>
 * Se la boccia in moto urta contro una sponda del campo, rimbalza indietro.
 * <LI>
 * Se la boccia urta in moto un'altra boccia, la boccia che era in moto
 * si ferma e parte l'altra boccia, che si comporter&agrave; a sua volta    
 * nello stesso modo.
 * </UL>
 * Il movimento della boccia, in assenza di urti, rallenta progressivamente
 * col tempo, per cui dopo un po' il gioco sar&agrave; di nuovo fermo.
 * Allora il giocatore potr&agrave; tirare di nuovo il boccino, ecc. ecc.
 * <BR>
 * Il giocatore ha a disposizione un numero massimo di tiri, che determina
 * il livello di difficolt&agrave; del gioco.
 * <BR>
 * Il gioco finisce quando il boccino finisce in buca, oppure quando il
 * giocatore ha esaurito il numero di tiri a disposizione. Il punteggio
 * del giocatore &egrave; pari al numero di bocce, diverse dal boccino,   
 * finite in buca.
 * <BR>
 * La simulazione del gioco avviene in modo discreto a scatti,
 * con un intervallo di tempo fissato (piccolo) tra uno scatto
 * e il successivo.
 */ 
public interface Gioco 
{
  /** Intervallo di tempo tra due scatti di simulazione, in secondi. */
  double DELTA_T = 0.02;

  /** Decremento della velocita' dopo ogni scatto di simulazione,
   * in cm al secondo. */
  double DECREMENTO = 0.05;
  
  /** Inizializza il gioco leggendo da file. 
   * Il file contiene nell'ordine:<UL>
   * <LI>La parola chiave GIOCO seguita da due interi 
   * che rappresentano le dimensioni del campo di gioco
   * <LI>La parola chiave BUCHE seguita da un intero N che
   * rappresenta il numero di buche (da 1 a 4)
   * <LI>N interi positivi che rappresentano i diametri 
   * delle N buche
   * <LI>La parola chiave BOCCE seguita da un intero M che
   * rappresenta il numero di bocce (da 1 a 8)
   * <LI>M interi positivi che rappresentano i diametri 
   * delle M bocce</UL>
   * Crea il campo da gioco con le dimensioni indicate e vi 
   * colloca le N buche e le M bocce in posizioni valide
   * (ovvero dentro al campo e senza intersezioni fra loro),
   * sceglie come boccino una fra le bocce di dimensione minima.
   * Lancia eccezione se per qualche motivo non riesce a leggere
   * dal file. */
  void inizializzaLeggendo(String nome) throws Exception;
  
  /** Ritorna il numero di buche. */
  int numeroBuche();
  
  /** Ritorna il numero totale di bocce, incluso il boccino,
   * incluse quelle cadute in buca. */
  int numeroBocce();
  
  /** Ritorna il numero di bocce cadute in buca. */
  int numeroCadute();
  
  /** Imprime al boccino una velocita' avente intensita' e 
   * direzione date, preparandolo per un nuovo tiro.
   * Ogni volta che questa funzione viene chiamata, il numero
   * di tiri a disposizione del giocatore diminuisce di uno.
   * @param intensita modulo del vettore velocita' espresso
   * in centimetri al secondo.
   * @param angoloDirezione  angolo formato dal vettore velocita' misurato 
   * in radianti partendo dalla direzione positiva dell'asse x. */
  void preparaBoccino(double intensita, double angoloDirezione);
  
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
  boolean evoluzioneDeltaT();
  
  /** Ritorna l'indice di boccia corrispondente al boccino. */
  int indiceBoccino();

  /** Ritorna il numero di tiri di boccino disponibili al giocatore. */
  int numeroTiri();
  
  /** Stabilisce il numero di tiri disponibili, che devono essere 
   * almeno uno. Solleva eccezione se l'argomento &egrave; &lt; 1. 
   * @param n  numero di tiri, deve essere &gt;= 1
   */
  void cambiaNumeroTiri(int n) throws Exception;
  
  /** Ritorna la coordinata X della posizione della boccia di
   * indice dato, se la boccia non e' gia' caduta in buca, ritorna -1.
   * Gli indici validi sono da 0 a numeroBocce()-1. */
  double bocciaX(int indiceBoccia);
  /** Ritorna la coordinata Y della posizione della boccia di
   * indice dato, se la boccia e' gia' caduta in buca, ritorna -1.
   * Gli indici validi sono da 0 a numeroBocce()-1. */
  double bocciaY(int indiceBoccia);

  /** Ritorna vero sse la boccia di indice dato e' caduta in buca.
   * Gli indici validi sono da 0 a numeroBocce()-1. */
  boolean caduta(int indiceBoccia);

  /** Ritorna la coordinata X della posizione della buca di indice dato.
   * Gli indici validi sono da 0 a numeroBuche()-1. */
  double bucaX(int indiceBuca);
  /** Ritorna la coordinata Y della posizione della buca di indice dato. 
   * Gli indici validi sono da 0 a numeroBuche()-1. */
  double bucaY(int indiceBuca);
  
  /** Ritorna la dimensione del tavolo lungo l'asse X. */
  double campoX();
  /** Ritorna la dimensione del tavolo lungo l'asse Y. */
  double campoY();
  
  /** Ritorna il diametro della boccia di indice dato.
   * Gli indici validi sono da 0 a numeroBocce()-1. */
  double diametroBoccia(int indiceBoccia);
  
  /** Ritorna il diametro della buca di indice dato.
   * Gli indici validi sono da 0 a numeroBuche()-1. */
  double diametroBuca(int indiceBuca);
  
  /** Ritorna vero se e solo se tutte le bocce ancora presenti
   * in campo sono ferme. */
  boolean bocceFerme(); 

  /** Ritorna vero se e solo se il gioco e' finito, ovvero se il 
   * boccino e' gia' caduto in buca oppure se non ci sono piu' 
   * tiri a disposizione. */
  boolean giocoFinito();

  /** Ritorna il punteggio, pari al numero di bocce cadute in
   * buca, escluso il boccino. */
  int punti();
}
