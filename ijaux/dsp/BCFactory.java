package ijaux.dsp;


import ijaux.hypergeom.index.Indexing;

public class BCFactory implements BoundaryCondition<int[]> {
	Indexing<int[]> aind;
	int[] scoords;
	int[] dim;
	
	public BCFactory(Indexing<int[]> sind) {
		aind=sind;
		scoords=new int[sind.getNDim()];
		dim=sind.getDim();
	}
	
	public static BoundaryCondition<int[]> create(BCTypes type, Indexing<int[]> aind)	{		
		BCFactory factory=new BCFactory(aind);
		switch (type ) {
			case TRANSLATED: return factory.new  TranslatedCondition(aind);
			case MIRROR: return factory.new  MirrorCondition(aind);
			case STATIC: return factory.new  StaticCondition(aind);
		}
		
		return null;
			
	}
	
	@Override
	public int getIndexAt(int index) {
		int sind=aind.index();
		aind.setIndexAndUpdate(index);
		int[] coords=aind.getCoordinates();
		coords=getCoordsAt(coords);
		int ret=aind.indexOf(coords);
		aind.setIndexAndUpdate(sind);
		return ret;
	}
	
	////////////////////////////////////
	public class MirrorCondition extends BCFactory {
		
		public MirrorCondition(Indexing<int[]> sind) {
			super(sind);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int[] getCoordsAt(int[] coords) {	 
			for (int i=0; i<coords.length;i++) {
				scoords[i]=coords[i];
				final int a=Math.abs(coords[i]) % dim[i]+1;
				if (coords[i]>=dim[i]) {					 
					scoords[i]=   dim[i] - a;
				} 
				
				if (coords[i] < 0) {
					scoords[i]=    a-1;
				}
			}
			return scoords;
		}

		
		
		@Override
		public String toString() {
			return "MirrorCondition";
		}
		
	} // end class
	
public class TranslatedCondition extends BCFactory {
	
	
	public TranslatedCondition(Indexing<int[]> sind) {
		super(sind);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] getCoordsAt(int[] coords) {	 
		for (int i=0; i<coords.length;i++) {
			scoords[i]=coords[i];
			//final int a=coords[i]% dim[i];
			if (coords[i]>dim[i])
				scoords[i]=coords[i]-dim[i];
			if (coords[i]<0)
				scoords[i]=coords[i]+dim[i];
		}
		return scoords;
	}

	@Override
	public String toString() {
		return "TranslatedCondition";
	}
	 
} // end class

public class StaticCondition extends BCFactory {
	
	
	public StaticCondition(Indexing<int[]> sind) {
		super(sind);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] getCoordsAt(int[] coords) {	 
		for (int i=0; i<coords.length;i++) {
			scoords[i]=coords[i];
			if (coords[i]>=dim[i])
				scoords[i]=dim[i]-1;
			if (coords[i]<0)
				scoords[i]=0;
		}
		return scoords;
	}

	
	 
	@Override
	public String toString() {
		return "StaticCondition";
	}
} // end class

@Override
public int[] getCoordsAt(int[] coords) {
	// TODO Auto-generated method stub
	return null;
}


}