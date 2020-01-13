public interface Stack<T> {

    public static class StackException extends Exception {
		public StackException(String message) {
			super(message);
		}
	}

	public static class OverflowException extends StackException {
		public OverflowException() {
			super("Stack overflow");
		}
	}

	public static class UnderflowException extends StackException {
		public UnderflowException() {
			super("Stack underflow");
		}
	}

	public static class EmptyException extends StackException {
		public EmptyException() {
			super("Stack is empty");
		}
	}

	public boolean isEmpty();
	public void push(T item) throws Stack.OverflowException;
	public T pop() throws Stack.UnderflowException;
	public T top() throws Stack.EmptyException;

}
