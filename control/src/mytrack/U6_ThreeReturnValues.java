package mytrack;

public final class U6_ThreeReturnValues<R, S, T> {
	
	public final R first;
	public final S second;
	public final T third;
	
	public U6_ThreeReturnValues(R first, S second, T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public R getFirst() {
		return first;
	}
	
	public S getSecond() {
		return second;
	}
	
	public T getThird() {
		return third;
	}
}