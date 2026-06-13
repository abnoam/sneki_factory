package main.dataStructures;

public interface MyQueue
{
    void offer(Object data);

    Object pull();
    Object peek();
    boolean isEmpty();
    int size();
}
