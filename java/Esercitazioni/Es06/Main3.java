class Main3{
    
	public static void main(String[] arg){
	    
		LinkedList laLista = new LinkedList(); // lista vuota
		
		//Aggiunta di vari numeri alla lista
		laLista.addToFront(5);
		
		laLista.addToFront(4);
		
		laLista.addToFront(3);
		
		laLista.addToFront(2);
		
		laLista.addToFront(1);
		
		laLista.print();
		
		System.out.println("La lista e' lunga "+laLista.length()+" caselle");
		
		
		
		
        LinkedList laLista1 = new LinkedList();
		
		laLista1 = laLista1.readList();
		
		System.out.print("Prima serie di numeri inserita: ");
		
	    laLista1.print();
		
		LinkedList laLista2 = new LinkedList();
		
		laLista2 = laLista2.readList();
		
		System.out.println("Seconda serie di numeri inserita: ");
		
		laLista2.print();
		
		if(laLista1.deepEquals(laLista2)){
			System.out.println("le due serie di numeri inseriti sono uguali");
		}
		else{
			System.out.println("le due serie di numeri inseriti sono diverse");
		}
		
	}
	
}
	
		
		
		
		