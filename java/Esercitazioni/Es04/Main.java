import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        double n_in = 0, somma = 0;

        Scanner sc = new Scanner(System.in);

        System.out.println("\nPROVA CON Scanner");
        System.out.println("Inserire i numeri di cui si vuole far la somma. (Terminare la sequenza con un carattere non numerico): ");

        while(true){
            // Ripeti fino a che l'utente non innserisce un carattere non numerico
            // (ovvero fino a quando non si verifica un'eccezione)
            try{
                n_in = sc.nextDouble();
                somma += n_in;
            }catch(NumberFormatException e){
                break; // Esci dal ciclo
            }catch(Exception e){
                System.out.println("Errore generico");
                break; // Esci dal ciclo
            }
        }

        System.out.println("Somma = " + somma);

        /** --------------
          * N.B. Il secondo metodo non leggerà l'input se viene reindirizzato da file.
        */
        somma = 0; // Resetta somma

        System.out.println("PROVA CON BufferedReader");
        System.out.println("Inserire i numeri di cui si vuole far la somma. (Terminare la sequenza con un carattere non numerico): ");

        while(true){
            // Ripeti fino a che l'utente non innserisce un carattere non numerico
            // oppure vengono inseriti più caratteri sulla stessa riga.
            // (ovvero fino a quando non si verifica un'eccezione)
            try{
                s = br.readLine();
                n_in = Double.parseDouble(s);
                somma += n_in;
            }catch(NumberFormatException e){
                break; // Esci dal ciclo
            }catch(IOException e){
                break; // Esci dal ciclo
            }catch(Exception e){
                break; // Esci dal ciclo
            }
        }

        System.out.println("Somma = " + somma);

    }
}