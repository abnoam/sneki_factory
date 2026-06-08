package main.dataBase;

public class StackAsList implements MyStack
{
    private LinkedNode top;
    private int size;

    public StackAsList()
    {
        this.top = null;
        this.size = 0;
    }

    public void push(Object data)
    {
        LinkedNode newNode = new LinkedNode(data,this.top);
        this.top = newNode;

        size++;
    }

    public Object pop()
    {
        if(isEmpty())
        {
            return null;
        }
        Object temp = this.top.getData();
        this.top = this.top.getNext();
        size--;
        return temp;
    }

    public Object peek()
    {
        if (isEmpty())
        {
            return null;
        }
        return this.top.getData();
    }
    
    public boolean isEmpty()
    {
        return this.top == null;
    }
    
    public int size()
    {
        return this.size;
    }
}
