package ijaux;

public interface Constants {
	
	String libVersion="1.0";
	/*
	 * Iteration pattern constants 
	 */
	int IP_SINGLE=1; 
	int IP_FWD=2; 
	int IP_BCK=3;
	
	int	IP_BLOCK=10;
	
	int IP_DIR=20;
	
	int IP_DIRZ=30;
	
	int IP_PRIM=100;
	
	int IP_MAP=200;
	
	int IP_DEFAULT=IP_FWD+IP_SINGLE+IP_PRIM;
	
	/*
	 *  byte access constants
	 */
	int byteMask=0xFF;
	
	int short12Mask=0xFFF;
	
	int shortMask=0xFFFF;
	
	int intMask=0xFFFFFF;
	
	/*
	 * Indexing pattern constants
	 */
	int BASE_INDEXING=1;
	int CENTERED_INDEXING=-1;
	
	/*
	 * StructureElement constants
	 */
	int POINT=1;
	int LINE=2;
	int CUBE=3;
	
	/*
	 * ImageJ constants
	 */
	int CZT=0, CTZ=1, ZCT=2, ZTC=3, TCZ=4, TZC=5, _Z=-1;
	
	int FFT_ABS=0, FFT_R=1;
	
}
