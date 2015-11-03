package test.parallel;
import ij.ImageJ;
import ij.ImagePlus;
import ijaux.PixLib;
import ijaux.Util;
import ijaux.datatype.UnsupportedTypeException;
import ijaux.hypergeom.PixelCube;
import ijaux.hypergeom.Region;
import ijaux.hypergeom.index.BaseIndex;
import ijaux.parallel.ForkJoinJob;
import ijaux.parallel.ForkJoinTask;
import ijaux.parallel.ThreadedProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class MThreading4 {

	static int n_cpus = Runtime.getRuntime().availableProcessors();
	
	public MThreading4() {
		System.out.println("detected " + n_cpus +" CPUs");
		int poolsize=2*n_cpus;
		System.out.println("thread pool size: "+ poolsize);
		int[] dims = {100, 100};
		
		int size=Util.cumprod(dims);
		
		//byte[] pixels=Util.rampByte(size, 50);
		//PixelCube<Byte,BaseIndex> pc=new PixelCube<Byte,BaseIndex>( dims,pixels );
		/*
		new ImageJ();
		PixLib plib=new PixLib();
		ImagePlus imgp2;
		try {
			imgp2 = plib.imageFrom("input", pc);
			imgp2.show();
		} catch (UnsupportedTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 
		
				
		ThreadFactory factory =	Executors.defaultThreadFactory();
		 
		ThreadPoolExecutor executor =(ThreadPoolExecutor) Executors.newFixedThreadPool(poolsize, factory);
			
		ArrayList<ForkJoinJob<Boolean>> tasks=new ArrayList<ForkJoinJob<Boolean>>();
		for (int i=0; i<poolsize; i++) {
			tasks.add(new ConvTask2 (i));
		}
		try {
			exec(executor,tasks);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();
		for (ForkJoinJob<Boolean> c: tasks) {
			 c.join();
		}
			
		/*
		
		ThreadedTask[] proc =new ThreadedTask[poolsize];
		for (int i=0; i<poolsize; i++) {	 
			proc[i]=  mtc.new ConvTask (i);
		}	
		*/
		 
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new MThreading4();
		
	}
	
	public <T> void exec(final Executor e, final Collection<ForkJoinJob<T>> jobs)
			throws InterruptedException {
		ExecutorCompletionService <T> ecs = new ExecutorCompletionService<T>(e);
		for (Callable<T> s : jobs) {
			ecs.submit(s);
			}
		final int n = jobs.size();
		int cnt=0;
		System.out.println("retriving tasks ... " +n);
		
		while (cnt<n ) {		        	
			Future<T> ft=ecs.take();
			try {
				if (ft.isDone()) {
					T result=ft.get();
					System.out.println(result +" "+cnt);
					// we do something with the result
					cnt++;
				}
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	class ConvTask2 implements ForkJoinJob<Boolean>  {
		AtomicInteger id=new AtomicInteger(-1);
		
		public ConvTask2(int _id) {			
			fork(_id);
		}
		
		@Override
		public Boolean call() throws Exception {
			try {
				System.out.println("running id "+id);
				synchronized (this) {
					wait(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
	
		public void fork(int _id) {
			id.set(_id);
				System.out.println("forking "+id);
			
		}

		public void join() {
				System.out.println("joining "+id);			
			
		}
		
		@Override
		public String toString() {
			return id.toString();
		}
	}
	/* TOO LIMITING for pixel operations
	 * 
	class ConvThreadPoolExecutor  extends ThreadPoolExecutor  {

		public ConvThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
			// TODO Auto-generated constructor stub
		}

		 protected void afterExecute(Runnable r, Throwable t) {
			 super.afterExecute(r,t);
			 ((ThreadedTask) r).join();
		 }

		
	}
	*/
//////////////////
	class ConvTask implements ForkJoinTask, Runnable {
		AtomicInteger id=new AtomicInteger(-1);
		boolean _debug=true;
		
		private boolean isFnished=false;
		
		public ConvTask(int id) {
			System.out.println("starting id "+id);
			//fork(id);
		}
	 
		@Override
		public void run() {
			if (_debug)
				System.out.println("processing id "+id);
			try {
				wait(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isFnished=true;
		}

		@Override
		public void fork(int _id) {
			id.set(_id);
			if (_debug)	
				System.out.println("forking "+id);
			
		}

		@Override
		public void join() {
			if (_debug)	
				System.out.println("joining "+id);			
			
		}
		
		public boolean isFnished () {
			return isFnished;
		}
	}
/*	class ThreadPerTaskExecutor implements Executor {
		public void execute(Runnable r) {
			new Thread(r).start();
		};*/
}
