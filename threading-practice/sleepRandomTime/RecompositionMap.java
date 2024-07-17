
import java.util.ArrayList;

public class RecompositionMap implements Runnable {

    public static String globalPrintMsg = null;

    @ Override
    public void run() {
        try {
            veryFunctionalCode();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted by another thread");
        }
    }

    synchronized public static String isThreadDone(String msg) {
        if (globalPrintMsg == null){
            globalPrintMsg = msg;
        }
        return globalPrintMsg;
    }

    public static void veryFunctionalCode() throws InterruptedException{
        int x = (int) (Math.random() * 1500) + 1;
        Thread.sleep(x);
        int[] patterns = {1,2,3,4};
        int randomPattern = patterns[(int) (Math.random() * patterns.length)];
        isThreadDone("" + "The message is " + randomPattern);
    }

    public static void printOutput(){
        System.out.println(globalPrintMsg);
    }

    public static ArrayList<RecompositionMap> createStrategies(int number) throws InterruptedException {
        ArrayList<RecompositionMap> strategies = new ArrayList<>();
        for(int i = 0; i < number; i++){
            RecompositionMap sampleStrategy = new RecompositionMap();
            strategies.add(sampleStrategy);
        }
        return strategies;
    }

    //  Interrupt other threads as killing and stopping is now deprectaed in Java
    synchronized static public void interruptOtherThreads (ArrayList<Thread> threadList){
        for (Thread thread : threadList){
            if (!Thread.currentThread().equals(thread)){
                thread.interrupt();
            }
        }
    }
    /**
     * Potential Problems:
     * 1. How to deal with InterruptedExceptions?
     */
    
    public static void main(String[] args) {
        int n = 10;

		// Create strategies
        ArrayList<RecompositionMap> strategies = null;

        try {
            strategies = createStrategies(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

		// If strategies is not null create the strategies
        if (strategies != null) {
			for (RecompositionMap strategy : strategies) {
				strategy.run();
			}
		}

        printOutput();
    }
}


