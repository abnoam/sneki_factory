package main.dataStructures;

public class QueueAsList implements  MyQueue
{
    private LinkedNode front;
    private  LinkedNode rear;
    private  int size;

    public QueueAsList()
    {
        this.front = this.rear = null;
        this.size = 0;
    }
    public void offer(Object data)
    {
        LinkedNode newNode = new LinkedNode(data);
        if(this.rear == null)
        {
            this.front = this.rear = newNode;
        }
        else
        {
            this.rear.setNext(newNode);
            this.rear = newNode;
        }
        size++;
    }

    public Object pull()
    {
        if (this.front == null)
            return null;
        Object temp = this.front.getData();
        this.front = this.front.getNext();

        if(this.front == null)
            this.rear = null;

        size--;
        return temp;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public Object peek()
    {
        if(this.front == null)
        {
            return null;
        }
        return this.front.getData();
    }
}
