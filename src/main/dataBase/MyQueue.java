package main.dataBase;

public interface MyQueue
{
    void offer(Object data);

    Object poll();
    Object peek();
    boolean isEmpty();
    int size();
}
