package mytrack;


public final class U5_TwoReturnValues<R, S> {
	
	public final R first;
	public final S second;
	
	public U5_TwoReturnValues(R first, S second) {
		this.first = first;
		this.second = second;
	}
	
	public R getFirst() {
		return first;
	}
	
	public S getSecond() {
		return second;
	}
}
