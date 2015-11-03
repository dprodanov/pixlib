/**
 * 
 */
package ijaux.parallel;

import ijaux.datatype.access.Access;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dimiter Prodanov
 * 
 * basic class for 
 *
 */
public abstract class AccessTask implements ForkJoinTask, Runnable {

	/**
	 * 
	 */
	
	protected final Access<?> accessin;
	protected AtomicInteger id=new AtomicInteger(-1);
	protected int offset=0;
	protected int limit=-1;
	protected int overlap=0;
	final int n_cpus = Runtime.getRuntime().availableProcessors(); 
	public boolean _debug=false;
	
	public AccessTask( Access<?> accesin, int _overlap) {	 
		this.accessin=accesin;
		overlap=_overlap;

	}

 
	/* (non-Javadoc)
	 * @see ijaux.funct.ThreadedTask#fork(int)
	 */
	@Override
	public void fork(int _id) {
		id.set(_id);
		if (_debug)	
			System.out.println("forking "+id);

		int size=accessin.size()[0]*accessin.size()[1]; // PagedAccess
		if (size==0) // ArrayAccess
			size=accessin.size()[0];
		
		if (_debug)
		System.out.println(accessin.getClass()+" "+accessin.size()[0] +" "+accessin.size()[1]);
		
		int csize=size/n_cpus;

		//System.out.println("size "+size+" csize "+csize);
			offset=_id*csize;
		limit=offset+ csize +overlap; 
		if (_id== n_cpus-1)
			limit=size;

	}

	 

}
