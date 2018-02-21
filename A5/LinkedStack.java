/** Implements the interface <code>Stack</code> using linked elements.
 *
 *
 * @author  Marcel Turcotte (turcotte@eecs.uottawa.ca)
 */

public class LinkedStack<E> implements Stack<E> {

    // Objects of the class Elem are used to store the elements of the
    // stack.
    
    private static class Elem<T> {
        private T value;
        private Elem<T> next;

        private Elem(T value, Elem<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    // Reference to the top element
    
    private Elem<E> top;

    /** Returns <code>true</code> if this stack is empty, and
     * <code>false</code> otherwise.
     *
     * @return <code>true</code> if this stack is empty, and
     * <code>false</code> otherwise.
     */

    public boolean isEmpty() {
        return top == null;
    }

    /** Inserts an element onto the stack.
     *
     * @param value the element to be inserted
     */

    public void push(E value) {
		if (value == null) 
			throw new NullPointerException();
	
        top = new Elem<E>(value, top);
    }

    /** Returns the top element, without removing it.
     *
     * @return the top element
     */

    public E peek() {
        return top.value;
    }

    /** Removes and returns the top element.
     *
     * @return the top element
     */

    public E pop() {

	// pre-condition: the stack is not empty
        E saved = top.value;
        top = top.next;
        return saved;
    }

    /** Removes the top element of the stack. The element inserted at
     * the bottom of the stack.
     */
    //{e,d,c,b,a} then {c,b,a,e,d}
	
	public void roll() {
        if(isEmpty())
	       throw new IllegalStateException("stack is empty");
		
		recRollRemove(new LinkedStack<E>(), 0, null); 
    }
	
	private void recRollRemove(LinkedStack<E> l, int count, E captured){		
		if (!isEmpty()){
			if(count == 0){
				captured = pop();
			}
			else{
				E remove = pop();
				l.push(remove);
			}
			recRollRemove(l, ++count, captured);
		}
		else{
			// Piece everything together
			push(captured);
			recRollCombine(l);
		}
	}
	
	private void recRollCombine(LinkedStack<E> l){
		if (!l.isEmpty()){
			push(l.pop());
			recRollCombine(l);
		}
	}

    /** Removes the botttom element. The element is inserted on the
     * top of the stack.
     */

    public void unroll() {
        if(isEmpty())
           throw new IllegalStateException("stack is empty");
		
		recUnrollRemove(new LinkedStack<E>());
    }

	private void recUnrollRemove(LinkedStack<E> l){		
		if (!isEmpty()){
			E remove = pop();
			l.push(remove);

			recUnrollRemove(l);
		}
		else{
			// Piece everything together
			recUnrollCombine(l, l.pop());
		}
	}
	
	private void recUnrollCombine(LinkedStack<E> l, E remember){
		if (!l.isEmpty()){
			E removed = l.pop();
            push(removed);
			
			recUnrollCombine(l, remember);
		}
		else{
			push(remember);
		}
	}

    /** Returns a string representation of the stack.
     *
     * @return a string representation
     */

    @Override public String toString() {
	StringBuffer stackStr = new StringBuffer("{");

	Elem<E> current = top;
	
	while (current != null) {
	    stackStr.append(current.value);
	    if (current.next != null) {
		stackStr.append(",");
	    }
	    current = current.next;
	}
	stackStr.append("}");

	return stackStr.toString();
    }
}