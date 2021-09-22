class SommaInteri{
    public static void main(String[] arg){
        int somma=0;

        for(int i=0;i<arg.length;i++){
            somma += Integer.parseInt(arg[i]);
        }

        System.out.println("Somma: "+somma);
    }
}
