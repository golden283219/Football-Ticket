package dao;


public class Bridge {

    static int i=1;
    public void println(String msg) {
        System.out.println("msg from js: " + msg);
    }

    public void clickMap() {
        System.out.println("map clicked " + i);

    }
}
