//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720

public class GameStack implements Stack<DotInfo> {

    public DotInfo[] stack;
    private int top;
    private int size;

    /** Contructor
        @param size
    */
    public GameStack(int size){
        this.size = size;
        top = 0;
        stack = new DotInfo[size*size];
    }

    /**
     * Tests if this Stack is empty.
     *
     * @return true if this Stack is empty; and false otherwise.
     */
    public boolean isEmpty(){
		// Returns true if empty
        return (stack[0] == null);
    }

    /**
     * Returns a reference to the top element; does not change
     * the state of this Stack.
     *
     * @return The top element of this stack without removing it.
     */

    public DotInfo peek(){
		// Return top element of stack
        return (stack[top]);
    }

    /**
     * Removes and returns the element at the top of this stack.
     *
     * @return The top element of this stack.
     */
	
    public DotInfo pop(){
		// Save the top of stack
        DotInfo saved = stack[top];

		// Assign top of stack to null and decrement top
        stack[top--] = null;
		
		// Don't let top go below 0
		if (top < 0)
			top = 0;

        return saved;
    }

    /**
     * Puts an element onto the top of this stack.
     *
     * @param element the element be put onto the top of this stack.
     */

	
    public void push(DotInfo element){
		stack[top++] = element;
    }

    /**
     * a getter method for stack.
     *
     * 
     */

	
    public DotInfo[] getStack(){
        return stack;
    }

    /**
     * Getter method for the top of the stack
     *
     * 
     */

	
	public int getTop(){
		return top;
	}
}