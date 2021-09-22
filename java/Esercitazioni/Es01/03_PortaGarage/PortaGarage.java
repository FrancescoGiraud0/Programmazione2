public class PortaGarage {
    // Stato porta garage. true=aperto, false=chiuso
    private boolean stato = false;

    public boolean getStato(){
        return stato;
    }
    
    public void apri(){
        stato = true;
    }

    public void chiudi(){
        stato = false;
    }
}
