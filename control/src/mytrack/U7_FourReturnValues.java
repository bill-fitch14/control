package mytrack;

public final class U7_FourReturnValues<R, S, T, F> {
	
	public final R first;
	public final S second;
	public final T third;
	public final F fourth;
	
	public U7_FourReturnValues(R first, S second, T third, F fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
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
	
	public F getFourth() {
		return fourth;
	}
}