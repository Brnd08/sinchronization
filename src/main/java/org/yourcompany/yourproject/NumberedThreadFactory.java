package org.yourcompany.yourproject;
import java.util.concurrent.ThreadFactory;
/**
 * Thread Factory implementation that returns Thread instances which 
 * names denote consecutive numbers only for convenience. 
 * Returned Threads executes given Runnable instances.
 * @author brandon.velazquez
 */
public class NumberedThreadFactory implements ThreadFactory{
    private static short threadsCount = 0;
    @Override
    public synchronized Thread newThread(Runnable r){
        var thread = new Thread(r);
        thread.setName("T: " + String.valueOf(threadsCount++));
        return thread;
    }
    public static void resetCounter(){
        threadsCount = 0;
    }
}