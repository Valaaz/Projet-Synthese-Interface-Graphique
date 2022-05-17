package application;

public class MyClass {
    public native void myPrint(String s, int i);

    public native void findConvexHull(String infilename, String outfilename, int algo, int sort);

    public static void main(String[] args) {
        new MyClass().myPrint("Convexe", 10);
        new MyClass().findConvexHull("In", "Out", 1, 2);
    }

    static {
        System.loadLibrary("mylibrary");
    }
}
