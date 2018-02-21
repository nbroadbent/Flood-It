import java.util.NoSuchElementException;

/** Implements the interface <code>FrequencyTable</code> using a
 *  binary search tree.
 *
 * @author Marcel Turcotte (turcott@eecs.uottawa.ca)
 */

public class TreeFrequencyTable implements FrequencyTable {

    // Stores the elements of this binary search tree (frequency
    // table)
    
    private static class Elem {
    
        private String key;
        private long count;
    
        private Elem left;
        private Elem right;
    
        private Elem(String key) {
            this.key = key;   this.count = 0;
            left = null;      right = null;
        }
    }

    private Elem root = null; // A reference to the root element
    private int size = 0; // The size of the tree
    
    /** The size of the frequency table.
     *
     * @return the size of the frequency table
     */
    
    public int size() {
        return size;
    }
  
    /** Creates an entry in the frequency table and initializes its
     *  count to zero.
     *
     * @param key key with which the specified value is to be associated
     */
  
    public void init(String key) {
    if (key==null ){
        throw new IllegalStateException("no keys");
    }
        add(key);
        size++;
        addkeylist(key);
    //keylist.addLast(key);
    }

    private void add(String value){
        //Node<E> current=root;
        if(root==null){
            root = new Elem(value);
            root.key=value;
            //return true;
        }
        else{
            Elem current=root;
            boolean done=false;
            while(!done){
                int results= value.compareTo(current.key);
                if(results==0){
                    //size++;
                    done=true;
                    System.out.println("key already exist!");
                }
                else if(results<0){
                    if(current.left==null){
                        current.left=new Elem(value);
                        //size++;
                        done=true;
                    }
                    else{
                        current=current.left;
                    }
                }
                else{
                    if(current.right==null){
                        current.right=new Elem(value);
                        //size++;
                        done=true;
                    }
                    else{
                        current=current.right;
                    }
                }   
            }
            //return false;
        }
        }
    //own methods
    private boolean contains(String key){
        return true;
    }
    //own methods
    private boolean contains(String key, Elem current){
        if(current == null){
            return false;
        }

        if(key.compareTo(current.key)<0){
            return contains(key, current.left);
        }
        else if(key.compareTo(current.key)>0){
            return true;
        }
        else{
            return contains(key, current.right);
        }
    }


    /** The method updates the frequency associed with the key by one.
     *
     * @param key key with which the specified value is to be associated
     */
  
    public void update(String key) {
        // if key is null entry and does not contain
    if(key==null || !contains(key) ){
	   throw new IllegalStateException("key not found");
    }
    Elem current=root;
    boolean done=false;
        // search to update since it contains-->(true)
        while(!done){
                int results= key.compareTo(current.key);
                if(results==0){
                    current.count++;
                    done=true;
                    //System.out.println("key updated!");
                }
                else if(results<0){
                    if(current.left!=null){
                        current=current.left;
                    }
                }
                else{
                    if(current.right!=null){
                        current=current.right;
                    }
        
                }
        }
    }
  
    /**
     * Looks up for key in this TreeFrequencyTable, returns associated value.
     *
     * @param key value to look for
     * @return value the value associated with this key
     * @throws NoSuchElementException if the key is not found
     */
  
    public long get(String key) {
        // if key is null entry and does not contain
        if(key==null || !contains(key) ){
            throw new IllegalStateException("key is null");
        }
        Elem current=root;
        boolean done=false;
        long toBeReturned=0;
        // search to get key since it contains-->(true)
        while(!done){
                int results= key.compareTo(current.key);
                if(results==0){
                    toBeReturned=current.count;
                    done=true;
                    //System.out.println("key updated!");
                }
                else if(results<0){
                    if(current.left!=null){
                        current=current.left;
                    }
                }
                else{
                    if(current.right!=null){
                        current=current.right;
                    }
                }
        }
        return toBeReturned;
    }
  
    /** Returns the list of keys in order, according to the method compareTo of the key
     *  objects.
     *
     *  @return the list of keys in order
     */

    public LinkedList<String> keys() {
        if(size==0){
	       throw new IllegalStateException("there is no key to make a list");
        }
        
        if(size==1){
            //System.out.println("passing line 201");
            LinkedList<String> list= new LinkedList<String>();
            list.addLast(root.key);
            return list;
        }
        //String s= toString(root);
        //list.addLast(s);

        return makingList();

    }

    private LinkedList<String> keylist2 = new LinkedList<String>(); 

    private void addkeylist(String key){
        keylist2.addLast(key);
    }
    private LinkedList<String> makingList(){
        LinkedList<String> keylist = new LinkedList<String>();
        for(int i=0; i<keylist2.size(); i++ ){
            //System.out.println("testline 218");
            String keydata = keylist2.get(i);
            Long strength= get(keydata);

            if(i==0){
                keylist.addFirst(keydata);
                //System.out.println("LINE224");
                //System.out.println(keylist);
            
            }

            else if(i>0){
                //System.out.println("testline 227");
                String keydataPrevious = keylist.get(i-1);
                Long previousStrength = get(keydataPrevious);

                int results = strength.compareTo(previousStrength);

                if(results ==1|| results==0){
                    keylist.addLast(keydata);
                }
                else if(results==-1){
                    //System.out.println("smaller");
                    Long currentMin=strength;
                    int minIndex=0;

                    for (int j=0; j<keylist.size();j++){
                        String getSmallerKey=keylist.get(j);
                        Long getLong= get(getSmallerKey);
                        if(getLong<currentMin){
                            //System.out.println("testline 244");
                            currentMin=getLong;
                            minIndex=j;
                        }
                    } 
                    keylist.add(minIndex,keydata);
                }

            }
        }
        return keylist;
    }
    

    /** Returns the values in the order specified by the method compareTo of the key
     *  objects.
     *
     *  @return the values
     */

    public long[] values() {
        if(size==0){
	       throw new IllegalStateException("there is no keys to make a list");
        }
        LinkedList<String> keylist = makingList(); 
        LinkedList<Long> countlist= new LinkedList<Long>();
        long [] array = new long[ keylist.size() ];
        for(int i=0; i<keylist.size() ; i++){
            String getKey= keylist.get(i);
            Long getCount= get(getKey);
            countlist.add(i,getCount);
            //System.out.println(countlist);
            array[i]=countlist.get(i);
        }
        return array;
    }

    private static String displayValues(long [] array){
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

    /** Returns a String representation of the tree.
     *
     * @return a String representation of the tree.
     */

    public String toString() {
        return toString( root );
    }

    // Helper method.
  
    private String toString(Elem current) {
    
        if (current == null) {
            return "{}";
        }
    
        return "{" + toString(current.left) + "[" + current.key + "," + current.count + "]" + toString(current.right) + "}";
    }
    public static void main(String [] args){
        TreeFrequencyTable t= new TreeFrequencyTable();
        t.init("Key1"); t.init("Key2"); t.init("Key3");
        System.out.println( t);
        t.update("Key1"); t.update("Key1"); t.update("Key1");
        t.update("Key3"); t.update("Key3"); t.update("Key3");
        t.update("Key3"); t.update("Key3"); t.update("Key3");
        System.out.println(t);
        //System.out.println(t.get("Key1"));
        System.out.println("==============");
        System.out.println(t.keys());
        long[] countss= t.values();
        System.out.println(  displayValues( countss ) );

    }
  
}