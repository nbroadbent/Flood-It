//import java.util.EmptyStackException;
import java.io.Serializable;

public class GenericLinkedStack<E> implements Serializable, Stack<E>{

	private Elem<E> top;
    public DotInfo[] stack;


	public GenericLinkedStack(){
		top = null; // not need contructor actually
    }
	private static class Elem<T> implements Serializable{
		private T info; //the top same as value
		private Elem<T> next;
		private Elem(T info, Elem<T> next){
			this.info = info;
			this.next = next;}
	}

	public void push( E element ){
    	////Elem newElement=  new Elem(elem,top);
    	//newElement.info=elem;
    	//newElement.next=top;
    	///top=newElement;
        if(element == null){
            throw new NullPointerException("Cannot stack a null element");
        }

    	top = new Elem<E>(element,top);
    }
	
	public boolean isEmpty(){
		return (top == null);
	}

    public  E peek(){
        if (isEmpty()){
            //throw new IllegalStateException("Stack is empty");
            throw new EmptyStackException("Stack is empty");
        }	
    	return (top.info);
    }

    public E pop(){
    	//stack is not empty
        if (isEmpty())
            throw new EmptyStackException("Stack is empty");
        
    	E tmp= top.info;
    	top = top.next;
    	
    	return tmp;
    }

    public String toString(){
        String result="[";
        Elem<E> current = top;

        while (current != null){
            result += current.info; //+", ";
            current = current.next;
			
            if(current == null)
                break;
            else
                result += ",";
        }

        result += "]";
        return result;

    }
	
	/*
    public DotInfo[] getStack(){
        return stack;
    }
    public Elem<E> getTop(){
        return top;
    }
    */
	
    public static void main(String [] args){
        GenericLinkedStack<Integer> l= new GenericLinkedStack<Integer>();
		l.push(1);
		l.push(2);
        //System.out.println(l);
        //System.out.println(l.peek());
        System.out.print(l.pop());
        //LinkedStack<Integer> l1= new LinkedStack<Integer>();
        //System.out.println(l.peek());
    }

   
 

}