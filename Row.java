public class Row<T> {
	private final T _phase;
	public Row(T phase) {
		_phase = phase;
	}

	@Override
	public String toString() {
		return _phase.toString();
	}
}

