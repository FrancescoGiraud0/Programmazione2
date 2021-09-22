class HelloYou{
    public static void main(String[] arg){
        if(arg.length>0){
            System.out.print("Hello");
            for(int i=0;i<arg.length;i++){
                System.out.print(" "+arg[i]);
            }
            System.out.println("!");
        }else{
            System.out.println("Hello You!");
        }
    }
}
