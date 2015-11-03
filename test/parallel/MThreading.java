package test.parallel;

import ijaux.parallel.ForkJoinTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 */

/**
 * @author adminprodanov
 *
 */
public class MThreading implements ForkJoinTask{
	
	  

	static final int n_cpus = Runtime.getRuntime().availableProcessors(); 
	 
	 final RunnerThread[] threads =  new RunnerThread[n_cpus];
	 public final ForkJoinTask[] proc=new ForkJoinTask[n_cpus];
	 
	 boolean debug=false;
	 
	
	 
	 public void run() {
		 fork();
		 join();
		 for (ForkJoinTask f:proc)
			 f.join();
	 }
	 
	 public void fork() {
		 for (int ithread = 0; ithread < threads.length; ithread++) { 
			 threads[ithread] = new  RunnerThread(ithread) ;			 
	         threads[ithread].start(); 
	         if(debug)
	        	 System.out.println("starting "+threads[ithread].id);
		 }
	 }
	 
	 private AtomicInteger counter=new AtomicInteger(0);
	 
	 private AtomicInteger cnt=new AtomicInteger(-1);
	 
	 public void join() {
		 for (int ithread = 0; ithread < threads.length; ithread++) { 
	         try  
	         {     
	        	 threads[ithread].join();  	        	
	         } catch (InterruptedException ie) {
	        	 counter.incrementAndGet();
	         }
		 }
	 }
	 
	 /**
	 * @param args
	 */
	public static void main(String[] args) {
	
		MThreading m=new MThreading();
		m.run();
		System.out.print(m.counter);

	}
	
	private class RunnerThread extends Thread {
		AtomicInteger id=new AtomicInteger(-1);
		boolean _debug=debug;
		
		public RunnerThread(int _id) {
			id.set(_id);			
			setPriority(Thread.NORM_PRIORITY);  
		}
		
		public void run() {
			int cntu=cnt.incrementAndGet();
			if (cntu<n_cpus) {
				Proc aproc=new Proc();
				aproc.fork(id.get());
				aproc.run();
				proc[cntu]=aproc;
			}
			if(_debug)
        		System.out.println("finishing "+  id);
		}
	}
	
	
	private class Proc implements ForkJoinTask{
		 

		AtomicInteger id=new AtomicInteger(-1);
		
		
		// interface method
		public void run() {
			System.out.println("processing "+id);
		}
		
		// interface method
		public void join(){
			System.out.println("joining "+id);
		}

		public void fork(int _id) {
			id.set(_id);	
			System.out.println("setting "+_id);
		}
	}


	public void fork(int id) {
		// TODO Auto-generated method stub
		
	}

	

}
