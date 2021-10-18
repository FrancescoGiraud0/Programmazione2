import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        double n_in = 0, somma = 0;

        Scanner sc = new Scanner(System.in);

        System.out.println("\nPROVA CON Scanner");
        System.out.println("Inserire i numeri di cui si vuole far la somma. (Terminare la sequenza con un carattere non numerico)");

        while(true){
            try{
                n_in = sc.nextDouble();
                System.out.println(n_in); // da eliminare
                somma += n_in;
            }catch(NumberFormatException e){
                break;
            }catch(Exception e){
                break;
            }
        }

        System.out.println("Somma = " + somma);

        /** --------------
          * N.B. Il secondo metodo non leggerà il file.
        */
        somma = 0;

        System.out.println("PROVA CON BufferedReader");
        System.out.println("Inserire i numeri di cui si vuole far la somma. (Terminare la sequenza con un carattere non numerico)");

        while(true){
            try{
                s = br.readLine();
                n_in = Double.parseDouble(s);
                System.out.println(n_in); // da eliminare
                somma += n_in;
            }catch(NumberFormatException e){
                break;
            }catch(IOException e){
                break;
            }catch(Exception e){
                break;
            }
        }

        System.out.println("Somma = " + somma);

    }
}