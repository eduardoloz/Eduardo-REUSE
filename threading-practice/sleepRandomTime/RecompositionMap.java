
import java.util.ArrayList;

public class RecompositionMap implements Runnable{

    public static boolean globalIsDone = false;

    @ Override
    public void run() {
        int x = (int) (Math.random() * 2000) + 1;
        try {
          Thread.sleep(x);  
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted by another thread");
            e.printStackTrace();
        }
        if (isThreadDone()){
            System.out.println("Program executed within: " + x + " ms.");
        }
    }

    synchronized public boolean isThreadDone() {
        boolean isDone = globalIsDone;
        globalIsDone = true;
        return isDone;
    }
    
    public static void main(String[] args) {
        /**
         * Allocates a new Thread object. This constructor has the same effect 
         * as Thread (null, target, gname), where gname is a newly generated name. 
         * Automatically generated names are of the form "Thread-"+n, where n is an integer.
         */
        int n = 1;
        try {
            n = Integer.parseInt(args[0]);    
        } catch (NumberFormatException e) {
            System.out.println("String cannot be parsed to an integer");
        }
        /**
         * Notes
         * - Maybe create futures? (don't have a race condition)
         * - Look into alternatives for the for loop
         */
        // Thread thread = new Thread(recompMap);
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < n; i++){
            // Add the current thread to the collection
            RecompositionMap recompMap = new RecompositionMap();
            // Create a new thread and pass the instance of RecompositionMap to it
            Thread thread = new Thread(recompMap);
            thread.start();
            threads.add(thread);
        }
        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
        System.out.println("All threads have finished");
    }
}