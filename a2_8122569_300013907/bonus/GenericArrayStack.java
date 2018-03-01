
// Author: Kyle Pinkerton, David Hew-Wing
// Student number: 8122569, 300013907
// Course: ITI 1121-B0
// Assignment: 2
// Question: 

public class GenericArrayStack<E> implements Stack<E> {
   
   private E[] elems;
   private int top;

   // Constructor
   @SuppressWarnings("unchecked")
    public GenericArrayStack(int capacity ) {
        elems = (E[]) new Object[capacity];
        top = -1;

    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        
        return (top == -1);

    }

    public void push(E elem ) {
        
        if (top == elems.length){
            System.out.println("Stack is full.");
        }else{
            top++;
            elems[top] = elem;
        }

    }
    public E pop() {
        
        if(top == -1){
            System.out.println("Stack is empty");
        }else{
            int temp = top;
            top--;
            return elems[temp];
        }
        return elems[top];
    }

    public E peek() {
        
        if(top == -1){
            System.out.println("Stack is empty");
        }else{
            return elems[top];
        }
    return elems[top];
    }
}
