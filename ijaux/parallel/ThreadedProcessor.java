package ijaux.parallel;



import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicInteger;

/*
 *  Basic class for parallel processing
 *  
 */
public class ThreadedProcessor extends ParallelFramework implements ForkJoinTask, Runnable{

	 
	protected final RunnerThread[] threads =  new RunnerThread[n_cpus];

	public final ForkJoinTask[] proc=new ForkJoinTask[n_cpus];

	private AtomicInteger idcounter=new AtomicInteger(-1);

	 	
	public ThreadedProcessor() {
		if(debug)
			System.out.println("starting a ThreadedProcessor");
	}
	
	@Override
	public void run() {
		time=-System.currentTimeMillis();
		forkAll();
		join();
		time+=System.currentTimeMillis();
	}

	private void forkAll() {
		for (int ithread = 0; ithread < threads.length; ithread++) { 
			fork(ithread);			
		}
	}

	 
	@Override
	public void join() {
		for (int i = 0; i < threads.length; i++) { 
			try {     
				threads[i].join();  	 

			} catch (InterruptedException ie) {
				icounter.incrementAndGet();
			}
		}
		
		for (int i = 0; i < threads.length; i++) { 
			proc[i].join();			
		}
	}

	 

	@Override
	public void fork(int id) {
		threads[id] = new  RunnerThread(proc[id]) ;
		threads[id].start(); 
		if(debug)
			System.out.println("starting "+threads[id].id);
		
	}
	
/////////////////////////////////////////
	/*
	 * Helper class
	 */
	protected class RunnerThread extends Thread {
		
		AtomicInteger id=new AtomicInteger(-1);
		
		boolean _debug=debug;
		
		ForkJoinTask proc=null;
		

		public RunnerThread(ForkJoinTask task) {
			final int cntu=idcounter.incrementAndGet();
			id.set(cntu);			
			setPriority(Thread.NORM_PRIORITY); 			
			task.fork(id.get());
			proc = task;
		}

		public void run() {
			proc.run();
			if(_debug)
				System.out.println("finishing "+  id);
		}
	} // end class
	
	//private ArrayList<ForkJoinTask> tasklist=new ArrayList<ForkJoinTask>();
	//private int sz=0;
	/*
	@Override
	public void execute(Runnable command) {
		if (command instanceof ForkJoinTask) {
			if (sz<n_cpus) 
				tasklist.add((ForkJoinTask)command);
			sz=tasklist.size();
			if(debug)
				System.out.println("sz "+  sz);
		} else {
			throw new IllegalArgumentException(command+" not a ThreadedTask instance");
		}
		
	}
	*/
	

////////////////////////
	
/*	class WithinThreadExecutor implements Executor {
		public void execute(Runnable r) {
			r.run();
		};
	}

	class ThreadPerTaskExecutor implements Executor {
		public void execute(Runnable r) {
			new Thread(r).start();
		};
	}*/

}
