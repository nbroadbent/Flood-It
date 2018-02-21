import java.util.NoSuchElementException;
import java.lang.*;

/** Implements the interface <code>FrequencyTable</code> using linked
 *  elements. The linked structure is circular and uses a dummy node.
 *
 * @author Marcel Turcotte (turcott@eecs.uottawa.ca)
 */

public class LinearFrequencyTable implements FrequencyTable {

    // Linked elements

    private static class Elem {

	   private String key;
	   private long count;
	   private Elem previous;
	   private Elem next;

	   private Elem(String key, Elem previous, Elem next) {
	       this.key = key;
	       this.count = 0;
	       this.previous = previous;
	       this.next = next;
	   }
    }

    private Elem head;
    private int size;

    /** Constructs and empty <strong>FrequencyTable</strong>.
     */

    public LinearFrequencyTable() {
	   head = new Elem(null, null, null); // dummy node
	   head.previous = head; // making the dummy node circular
	   head.next = head; // making the dummy node circular
	   size = 0;
    }



     /** Creates an entry in the frequency table and initializes its
     *  count to zero. The keys are kept in order (according to their
     *  method <strong>compareTo</strong>).
     *
     *  @param key key with which the specified value is to be associated
     *  @throws IllegalArgumentException if the key was alreaddy present
     */

    public void init(String key){

		if (key == null)
			throw new IllegalArgumentException("key is null");
		
        if(size == 0){
            //add(key);
            create(key); 
        }
        else{
            if(!keycheck(key))
                throw new IllegalArgumentException("key is already present");
            else
                //add(key);
                create(key); 
        }
        
    }

    private void create(String key){
        Elem dummy = new Elem(key, head.previous, head);
		head.previous = dummy;
		dummy.previous.next = dummy;
		size++;
    }

    private void inOrder(){
        LinkedList<String> l=keys();
        System.out.println(keys());
        long [] values= values();
        System.out.println( displayValues(values) );
        //System.out.println(values[0]);
        head = new Elem(null, null, null); // dummy node
        head.previous = head; // making the dummy node circular
        head.next = head; // making the dummy node circular
        size = 0;

        for(int i=0; i<l.size(); i++){
            //System.out.println(values[i]);
            create2(l.get(i), values[i]);
        }
    }

    private void create2(String key, long count){
        //System.out.println(count);
        Elem dummy = new Elem(key, head.previous, head);
        head.previous = dummy;
        dummy.previous.next = dummy;
        dummy.count=count;
        size++;
    }

    public void add( String o ) {
        if ( o == null ){throw new IllegalArgumentException( "null" );}

        if ( head.next == head ) { // special case: empty list
            head.next = new Elem( o, head, head.next );}

        else {  
            Elem p = head;
            // i)  there is at least one node
            // ii) o is greater than p
            while ( p.next != head && (p.next.key).compareTo( o ) < 0 ) {
                p = p.next;
            }
            Elem q = p.next; // the node that follows

            p.next = new Elem( o, p, q );

            q.previous = p.next;
        }
        size++;
    }

    private boolean keycheck(String key){
        Elem p = head;
		
        while (p.next != null && p.next != head){
            if(p.key == key)
                return false;
            p = p.next;   
        }
		return p.key != key;
    }

    /** The size of the frequency table.
     *
     * @return the size of the frequency table
     */
    public int size(){
	   return size;
    }
	
    /** Returns the frequency value associated with this key.
     *
     *  @param key key whose frequency value is to be returned
     *  @return the frequency associated with this key
     *  @throws NoSuchElementException if the key is not found
     */
    public long get(String key) {
        if(key ==null){
            throw new IllegalStateException("key is null");
        }
        // made a dummy node
       Elem dummy = head;
       while(dummy.next!=null && dummy.next!=head){
            // if dummy key value is same then break
            if (dummy.key==key){
                //return dummy.count;
                break;
            }
            //if not found, then next
            dummy=dummy.next;
       }
       // if key is not found
       if(dummy.key!=key){
           throw new NoSuchElementException("element not found");
       }

       //returns the count value
       return dummy.count;
    }

    /** The method updates the frequency associed with the key by one.
     *
     *  @param key key with which the specified value is to be associated
     *  @throws NoSuchElementException if the key is not found
     */

    public void update(String key){
        if(key == null || size == 0)
           throw new NoSuchElementException("key not found");

        boolean found = false;
		Elem n = head.next;
		while(n != head){ 
			if(n.key == key){
				n.count++; 
                found = true;
				break; 
			}
			n = n.next; 
		}
        
        if (!found){
            if(n.key == key){
                n.count++; 
                //inOrder(); 
            } 
            else{
    			throw new NoSuchElementException("key not found"); 
            }
        }
        //if (found) //inOrder();

    }

    /** Returns the list of keys in order, according to the method
     *  <strong>compareTo</strong> of the key objects.
     *
     *  @return the list of keys in order
     */

    public LinkedList<String> keys(){
        LinkedList<String> l = new LinkedList<String>();
        Elem p = head.next;                                               
        int counter = 0;
		
        while(p != head){
            if(size == 1){ 
                l.addLast(p.key);
                break;
            }
            else{
                Elem q = p.previous;  
                Long current = p.count; 
                Long previous = q.count;
                int result = ((current).compareTo(previous));
				
				if( result==1 || result==0){
					l.add(counter, p.key); 
				}
				else if (result == -1){
					Long min = current; 
					int minIndex = 0;
					
					for(int i = 0; i < l.size(); i++){
						String getSmallerKey = l.get(i);
						Long getLong = get(getSmallerKey);
						
						if(getLong < min){
							min = getLong;
							minIndex = i; 
						}
					}
					l.add(minIndex,p.key);
				} 
            }
            p = p.next;
            counter++;
        }
		return l;
    }

    private LinkedList<Long> obtainValuesFromKeys(){
        LinkedList<Long> countlist = new LinkedList<Long>();
        try{
            LinkedList<String> key = keys(); 
			int keysize = key.size();
			
            for(int i = 0; i < keysize; i++){
                long getCount = get(key.get(i)); //System.out.println(getCount);
                countlist.add(i, getCount);
            }      
        }
        catch(IllegalStateException e){
            System.err.println("there are no keys to make a list");
        }
        return countlist;
    }

    /** Returns an array containing the frequencies of the keys in the
     *  order specified by the method <strong>compareTo</strong> of
     *  the key objects.
     *
     *  @return an array of frequency counts
     */

    public long[] values() {
        if (size == 0)
           throw new IllegalStateException("there are no keys");
        
        long[] array = new long[size];
        LinkedList<Long> countlist = obtainValuesFromKeys();
		
        for(int i = 0; i < array.length; i++){
            array[i] = countlist.get(i);
        }
        return array;
    }

    public long[] values_WRONG() {
        if (size == 0)
	       throw new UnsupportedOperationException("IMPLEMENT THIS METHOD");
        
        long[] array = new long[size];
        Elem p = head.next;
        int counter = 0;
		
        while(p != head && counter < size){
            if(counter == 0){ 
                array[0] = p.count;
			}
            else{
                Elem q = p.previous;  

                Long current = p.count;  
				Long previous = q.count;

                int result = ((current).compareTo(previous));
				
                if(result == 1 || result == 0){
                    array[counter] = current;
                }
                else if (result == -1){
                    long rememberPrevious = array[counter-1];
                    array[counter-1] = current;
                    array[counter] = rememberPrevious;
				}
            }
            p = p.next;
            counter++;
        }
        array = sort(array);
        return array;
    }
    //not using
    private long[] sort(long[] array){
        int n = array.length;
        long temp = 0;
		
        for (int i = 0; i < n; i++){
            for(int j = 1; j < (n-1); j++){
                if(array[j-1] > array[j]){
                    temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }
	
    //OWN METHOD
    //testing the array from values() method
    private String displayValues(long [] array){
        if (array==null || array.length==0){
           throw new IllegalStateException("there is nothing to display");
        }
        String s="[";
        for (int i=0; i<array.length; i++){
            s+=array[i];
            if(i==array.length-1){
                s+="]";
                break;
            }
            s+=",";
        }
        return s;
    }

    /** Returns a <code>String</code> representations of the elements
     * of the frequency table.
     *
     *  @return the string representation
     */
    public String toString(){

	StringBuffer str = new StringBuffer("{");
	Elem p = head.next;

	   while (p != head) {
	       str.append("{key="+p.key+", count="+p.count+"}");

	            if (p.next != head) {
		          str.append(",");
	            }
	            p = p.next;
	   }
	   str.append("}");

	return str.toString();
    }

    public static void main(String[] args){
        LinearFrequencyTable test= new LinearFrequencyTable();
        test.init("Key1"); test.init("Key2"); test.init("Key3"); test.init("Key0");  
        System.out.println("initital state"); System.out.println(test);

        test.update("Key3"); 
        test.update("Key2"); test.update("Key2");  
        test.update("Key1"); test.update("Key1"); test.update("Key1");
        System.out.println(test);
        //System.out.println(test.size());
        System.out.println("getting keys");
        System.out.println( test.get("Key1") ); System.out.println( test.get("Key2") ); System.out.println( test.get("Key3") );

        System.out.println( "testing keylist & displaying their respective values in ascending order");
        System.out.println( test.keys() );
        System.out.println( test.displayValues(  test.values() ) );
        System.out.println("=====================================");
        String s="101";
       
       
    }
    


}
