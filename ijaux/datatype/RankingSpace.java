package ijaux.datatype;


public interface RankingSpace<T, ComparableType> {
	
	T min(ComparableType  cta, ComparableType  ctb);
	T max(ComparableType  cta, ComparableType  ctb);
	//public T rank(int r, ComparableType  ct);
	//public T median(ComparableType  ct);

}
