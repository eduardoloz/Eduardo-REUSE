
import java.util.ArrayList;

public class RecompositionMap implements Runnable {

    public static String printMsg = null;

    @ Override
    public void run() {
        try {
            veryFunctionalCode();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted by another thread");
            e.printStackTrace();
        }
    }

    synchronized static public String isThreadDone(String msg) {
        if (printMsg == null){
            printMsg = new String(msg);
        }
        return printMsg;
    }

    public static void veryFunctionalCode() throws InterruptedException{
        int x = (int) (Math.random() * 1500) + 1;
        Thread.sleep(x);
        int[] patterns = {1,2,3,4};
        int randomPattern = patterns[(int) (Math.random() * patterns.length)];
        isThreadDone("" + randomPattern);
    }

    public static void printOutput(){
        System.out.println(printMsg);
    }
    
    public static void main(String[] args) {
        int n = 5;
        
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
        printOutput();
    }
}