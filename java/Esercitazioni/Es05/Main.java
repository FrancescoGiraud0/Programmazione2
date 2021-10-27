class Main{

    static void testPunti(Poin2D p1, Point2D p2){
        System.out.println("Test di uguaglianza tra i punti");
        System.out.println("p1 [" + p1.hashCode() + "] = "+ p1);
        System.out.println("p2 [" + p2.hashCode() + "] = "+ p2);
        System.out.println("sono identici? "+ (p1==p2));
        System.out.println("sono uguali? "+ p1.equals(p2));
    }

    static void testSegmenti(Segment2D s1, Segment2D s2){
        System.out.println("Test di uguaglianza tra i punti");
        System.out.println("s1 [" + s1.hashCode() + "] = "+ s1);
        System.out.println("s2 [" + s2.hashCode() + "] = "+ s2);
        System.out.println("Identity? "+ (s1==s2));
        System.out.println("Shallow equals? "+ s1.shallowEquals(s2) );
        System.out.println("Deep equals? "+ s1.deepEquals(s2) );
    }

    public static void main(String[] args){
        Point2D a = new Point2D(1,1);
        Point2D b = new Point2D(2,1);

        Segment2D s1 = new Segment2D(a, b);
        Segment2D s2 = s1;
        Segment2D s3 = new Segment2D(a, b);
        Segment2D s4 = new Segment2D(a.copy(), b.copy());
        Segment2D s5 = s1.copy();
        Segment2D s6 = new Segment2D(new Point2D(3,0), new Point2D(4,0));

        // Test
        System.out.println("S1 - S2");
        testSegmenti(s1, s2);
        System.out.println("S1 - S3");
        testSegmenti(s1, s3);
        System.out.println("S1 - S4");
        testSegmenti(s1, s4);
        System.out.println("S1 - S5");
        testSegmenti(s1, s5);
        System.out.println("S1 - S6");
        testSegmenti(s1, s6);
        
    }
}