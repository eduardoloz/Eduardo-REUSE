
import java.util.ArrayList;

public class RecompositionMap implements Runnable{

    public static volatile boolean aThreadIsDone;

    sychronized boolean isThreadDoneAndSetIF

    @ Override
    public void run() {
        int x = (int) (Math.random() * 2000) + 1;
        try {
          Thread.sleep(x);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aThreadIsDone) return;

        System.out.println("Thread " + "(dummy variable for #)" + "slept for " + x + " milliseconds");
        aThreadIsDone = true;
        // Instead of multiple giving a result. Have the threads report to a main thread (make sure only one thread wins)
    }
    
    public static void main(String[] args) {
        /**
         * Allocates a new Thread object. This constructor has the same effect 
         * as Thread (null, target, gname), where gname is a newly generated name. 
         * Automatically generated names are of the form "Thread-"+n, where n is an integer.
         */
        int n;

        try {
            n = Integer.parseInt(args[0]);    
        } catch (Exception e) {
            return;
        }

        /**
         * Notes
         * - Maybe create futures? (don't have a race condition)
         * - Look into alternatives for the for loop
         * 
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