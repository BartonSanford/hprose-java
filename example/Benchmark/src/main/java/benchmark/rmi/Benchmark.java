package benchmark.rmi;

import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Benchmark {

    public static void main(String[] args) throws NamingException, RemoteException, InterruptedException {
        System.out.println("START RMI:");
        String url = "rmi://localhost/serviceRMI";
        Context namingContext = new InitialContext();
        final IService serviceRMI = (IService) namingContext.lookup(url);

        int threadNumber = 40;
        final int roundNumber = 25000;
        Thread[] threads = new Thread[threadNumber];
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < roundNumber; i++) {
                            serviceRMI.hello("World!" + i);
                        }
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            };
            threads[i].start();
        }
        for (int i = 0; i < threadNumber; i++) {
            if (threads[i].isAlive()) {
                threads[i].join();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时: " + (end - start) + "ms");
        System.out.println((long)(((double)(threadNumber * roundNumber) * 1000 / (end - start))) + " QPS");
        System.out.println("END");
    }
}
