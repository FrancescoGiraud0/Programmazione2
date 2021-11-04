class Node{
    // contenuto di questo vagone
    int content;
    // collegamento al prossimo vagone
    Node next;

    // costruttore, crea il vagone isolato con dentro n
    Node(int n){
        content = n;
        next = null;
    }

    void print(){
        System.out.print("" + content);
        if (next!=null){
            System.out.print(","); // metto virgola per separare
            next.print(); // chiamata ricorsiva
        }
    }

    boolean isPresent(int n){
        if(content==n)
            return true;
        
        if(next==null)
            return false;
        
        return next.isPresent(n);
    }

    int position(int n, int i){
        if(content==n)
            return i;
        
        if(next==null)
            return -1;
        
        return next.position(n, i+1);
    }

    int length(){
        if(this.next==null)
            return 0;
        else
            return 1+next.length();
    }

    boolean deepEquals(Node other){
        
        if (other==null) 
            return false;
        if (other.content!=content) 
            return false;
        if (next==null) 
            return (other.next==null);
        
        return next.deepEquals(other.next);
    }
}