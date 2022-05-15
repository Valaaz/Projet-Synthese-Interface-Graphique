package application;

public class MyClass {
    public native void myPrint(String s, int i);

    public static void main(String[] args) {
        new MyClass().myPrint("Convexe", 10);
    }

    static {
        System.loadLibrary("mylibrary");
    }
}
