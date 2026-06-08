package main.dataBase;

public class LinkedNode
{
    private Object data;
    private LinkedNode next;

    public LinkedNode(Object data, LinkedNode next)
    {
        this.data = data;
        this.next = next;
    }

    public LinkedNode(Object data)
    {
        this(data, null);
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public LinkedNode getNext()
    {
        return next;
    }
    public void setNext(LinkedNode next)
    {
        this.next = next;
    }
}
