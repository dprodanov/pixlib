package ijaux.datatype;

/**
 * 
 * @author Dimiter Prodanov
 *
 * @param <T>
 * @param <ComparableType>
 */
public interface RankingSpace<T, ComparableType> {
	
	/**
	 * 
	 * @param cta
	 * @param ctb
	 * @return
	 */
	T min(ComparableType  cta, ComparableType  ctb);
	
	/**
	 * 
	 * @param cta
	 * @param ctb
	 * @return
	 */
	T max(ComparableType  cta, ComparableType  ctb);
	//public T rank(int r, ComparableType  ct);
	//public T median(ComparableType  ct);

}
