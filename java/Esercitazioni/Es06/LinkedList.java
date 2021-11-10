import java.io.*;

class LinkedList{
    // collegamento al primo vagone
    Node first;

    // come costruttore va bene quello di default,
    // che mette first = null e crea la lista vuota

    // aggiunge un elemento in testa
    void addToFront(int n){
        Node aux = new Node(n); // crea nuovo vagone
        aux.next = first; // vi aggancia in coda l'attuale primo
        first = aux; // aggancia come primo quello nuovo
    }

    // aggiunge in fondo scorrendo tutta la lista
    void addToBack(int n){
        Node aux = new Node(n);
        Node cur = this.first;

        while(cur.next!=null){
            cur = cur.next;
        }

        cur.next = aux;
    }

    // se usassimo last per salvare ultimo nodo
    void addToBackLast(int n){
        Node aux = new Node(n);
        last.next = aux;
        last = aux;
    }

    
    // metodo per aggiungere un array
    void addToFront(int[] arr){
        // chiama addToFront per ogni elemento a in arr
        for(int a: arr) this.addToFront(a);
    }
    
    // aggiungi elemento n in posizione pos
    void addInPosition(int pos, int n){
        if(pos==0)
            this.addToFront(n);
        else{
            Node aux = first;

            for(int i=0; i<pos-1 && aux!=null; i++) aux = aux.next;

            if(aux!=null){
                Node newNode = new Node(n);
                newNode.next = aux.next;
                aux.next = newNode;
            }
        }
    }

    void printRecursive(){
        System.out.print("["); // la lista viene stampata fra []

        if (first!=null) first.print();

        System.out.println("]");
    }

    void print(){
        Node node = first;

        System.out.print("[");

        while(node!=null){
            System.out.print(node.content);
            node = node.next;

            System.out.print((node!=null) ? ", " : "");
        }

        System.out.println("]");
    }

    int lengthIterative(){
        int l = 0; // contatore
        Node aux = first;
        while (aux != null){
            l ++;
            aux = aux.next;
        }
        return l;
    }

    boolean isPresent(int n){
        if(first==null)
            return false;

        return first.isPresent(n);
    }

    int position(int n){
        int i=0;

        if(first==null)
            return -1;
        
        return first.position(n, i);
        
    }

    int length(){
        if(first==null){
            return 0;
        }else{
            return 1+first.length();
        }
    }

    boolean deepEquals(LinkedList l){
        if (l==null)
            return false;
        if (first==null)
            return (l.first==null);
        
        return first.deepEquals(l.first);
    }

    private static LinkedList readList(){
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        LinkedList list = new LinkedList();

        System.out.println("Inserire un valore per riga (terminare con una stringa non sia un intero)");

        try{
            // il ciclo termina solo in caso di eccezione
            while(true)
                // aggiunge alla lista un valore per riga
                list.addToFront( Integer.parseInt(b.readLine()) ); 
        }catch (Exception e){
            return list;
        }
    }

    public static void main(String[] args){
        
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();
        int n_arr[] = {1,2,3,4,5,6,7,8,9,10};

        list1.addToFront(0);
        list1.addToFront(n_arr);

        System.out.println("Lunghezza lista1: "+list1.length());
        System.out.print("LISTA 1 -> "); list1.print();

        System.out.println("\n------\n");

        list2 = LinkedList.readList();

        System.out.println("Lunghezza lista2: "+list2.length());
        System.out.print("LISTA 2 -> "); list2.print();

        System.out.println("\n------\n");

        System.out.print("lista1[hash:" + list1.hashCode() + "] -> ");
        list1.print();
        System.out.print("lista2[hash:" + list2.hashCode() + "] -> ");
        list2.print();
        System.out.println("Deep equals? " + list1.deepEquals(list2) );

        System.out.println("\n------\n");
        System.out.println("Test position() su lista 1:\n");

        /*Elementi tutti presenti*/
        for(int i=10; i>=0; i--){
            System.out.print(i+" in posizione "+list1.position(i)+" --> ");
            System.out.println((list1.position(i)==10-i)? "Test passed" : "Test NOt passed");
        }

        /*Elementi tutti non presenti*/
        for(int i=11; i<20; i++){
            System.out.print(i+" in posizione "+list1.position(i)+" --> ");
            System.out.println((list1.position(i)==-1)? "Test passed" : "Test NOT passed");
        }

        System.out.println("\n------\n");
        System.out.println("Test isPresent() su lista 1:\n");

        /*Ciclo con elementi tutti presenti*/
        for(int i=10; i>=0; i--){
            System.out.print( i + (list1.isPresent(i) ? " è presente" : " non è presente") + " --> ");
            System.out.println((list1.isPresent(i))? "Test passed" : "Test NOT passed");
        }

        /*Ciclo con elementi tutti non presenti*/
        for(int i=11; i<20; i++){
            System.out.print( i + (list1.isPresent(i) ? " è presente" : " non è presente") + " --> ");
            System.out.println((list1.isPresent(i)==false)? "Test passed" : "Test NOT passed");
        }

    }
}