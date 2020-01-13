public class ListStack<T> implements Stack<T> {
	
	// The stack ADT implemented using our singly linked list class.

	SinglyLinkedList<T> stack;

	public ListStack() {
		stack = new SinglyLinkedList<T>();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public T top() throws Stack.EmptyException {
		if (stack.isEmpty()) throw new Stack.EmptyException();
		return stack.head();
	}

	@Override
	public void push(T item) {
		stack.prepend(item);
	}

	@Override
	public T pop() throws Stack.UnderflowException {
		if (stack.isEmpty()) throw new Stack.UnderflowException();
		return stack.removeHead();
	}

}
