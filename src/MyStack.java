/**
 *@author mshaik52 Student Number 250959996
 * This class is the template for implements the STACK ADT interaface
 * this class holds the instance variables and methods for manipulating stacks
 * @param <T>
 */

public class MyStack<T> implements MyStackADT<T> {
    /**
     * instance variables
     * arrayStack, numItems, maximumCapacity, default_Capacity
     *
     */
    private T[] arrayStack;
    private int numItems;
    private int maximumCapacity = 1000;
    private int default_Capacity = 10;

    /**
     *
     * @param initialCapacity
     * @param maxCap
     */
    //constructor for class
    public MyStack(int initialCapacity, int maxCap) {
        numItems = 0;
        maximumCapacity = maxCap;
        default_Capacity = initialCapacity;
        arrayStack = (T[]) (new Object[default_Capacity]);

    }

    //constructor with default values

    /**
     * constructor with default values
     */
    public MyStack() {
        numItems = 0;
        maximumCapacity = 1000;
        default_Capacity = 10;
        arrayStack = (T[]) (new Object[default_Capacity]);

    }

    //push data into top of stack

    /**
     *
     * @param dataItem data item to be pushed onto stack
     * @throws OverflowException
     */
    public void push(T dataItem) throws OverflowException       //to increase size of array do that same thing you did in first assignment.
    {

        //expand by 3 times
        if (numItems == arrayStack.length && arrayStack.length <= 60) {
            //expand the capacity
            expandBy3Times();
            arrayStack[numItems] = dataItem;
            numItems++;
        }
        //expand by 100
        else if (numItems == arrayStack.length && arrayStack.length > 60 && arrayStack.length < maximumCapacity) {
            expandBy100();
            arrayStack[numItems] = dataItem;
            numItems++;

        } else {
            arrayStack[numItems] = dataItem;
            numItems++;

        }

    }

    //expand capacity method
    private void expandBy3Times() {
        T[] larger = (T[]) (new Object[arrayStack.length * 3]);

        for (int i = 0; i < arrayStack.length; i++) {
            larger[i] = arrayStack[i];

        }
        arrayStack = larger;


    }

    //expand capacity by 100

    private void expandBy100() {

        T[] larger = (T[]) (new Object[arrayStack.length + 100]);

        if (larger.length > maximumCapacity) {

            throw new OverflowException("Overflow exception");
        }

        for (int i = 0; i < arrayStack.length; i++) {
            larger[i] = arrayStack[i];

        }
        arrayStack = larger;

        if (arrayStack.length > maximumCapacity) {

            throw new OverflowException("Overflow exception");
        }

    }

    //return true or false depending on whether on stack is populated or empty
    public boolean isEmpty() {
        if (numItems == 0) {
            return true;
        } else {
            return false;
        }
    }

    //get the value at the top of the stack

    /**
     *
     * @return
     * @throws EmptyStackException
     */
    public T pop() throws EmptyStackException {

        if (isEmpty()) {
            throw new EmptyStackException("Empty Stack");
        }

        numItems--;
        T poppedItem = arrayStack[numItems];
        arrayStack[numItems] = null;

        return poppedItem;
    }

    //shows whats at the top of the stack
    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException("Empty Stack");

        }

        return arrayStack[numItems - 1];

    }

    //how many items are stored in the stack
    public int size() {
        return numItems;


    }

    //toString method
    public String toString() {
        String result = "";

        for (int i = 0; i < numItems; i++) {
            result = result + arrayStack[i].toString();

        }
        return result;

    }


}

