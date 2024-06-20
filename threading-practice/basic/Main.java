public class Main extends Thread {
    /*** 
     * The major difference is that when a class extends the Thread class, you cannot 
     * extend any other class, but by implementing the Runnable interface, it is possible 
     * to extend from another class as well, like: class MyClass extends OtherClass 
     * implements Runnable. 
     ***/
    public static int amount = 0;

    public static void main(String[] args){
        Main thread  = new Main();
        thread.start();
        System.out.println("This code is outside of the thread");
        while(thread.isAlive()){
            //System.out.println("Waiting...");
        }
        System.out.println("Main: " + amount);
    }

    @ Override
    public void run() {
        System.out.println("-----");
        System.out.println("This code is running in a thread");
        amount++;
        System.out.println("Thread: " + amount);
        System.out.println("-----");
    } 
}
