class Segment2D{
    private Point2D first;
    private Point2D second;

    public Segment2D(Point2D a, Point2D b){
        first = new Point2D();
        second = new Point2D();
        first = a;
        second = b;
    }

    public Point2D getFirst(){
        return first;
    }

    public Point2D getSecond(){
        return second;
    }

    public Segment2D copy(){
        return new Segment2D(first.copy(), second.copy());
    }

    public boolean shallowEquals(Segment2D s){
        return ( (first==s.first) && (second==s.second) );
    }
    
    public boolean deepEquals(Segment2D s){
        return ( first.equals(s.first) && second.equals(s.second) ); 
    }

    public String toString(){
        return "segmento: { first: " + first + " second:" + second + " }";
    }
}