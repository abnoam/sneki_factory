package main.dataStructures;

public class LinkedList
{
    private  LinkedNode first;


    public LinkedList()
    {
        first = null;
    }

    public LinkedList (Object data)
    {
        first = new LinkedNode(data, null);
    }

    public boolean isEmpty()
    {
        return (first == null);
    }

    public void addLast(Object data)
    {
        LinkedNode newLink = new LinkedNode(data, null);
        if(isEmpty())
        {
            first = newLink;
        }
        else
        {
            LinkedNode linkPointer = first;
            while(linkPointer.getNext() != null)
            {
                linkPointer = linkPointer.getNext();
            }
            linkPointer.setNext(newLink);
        }
    }

    public Object removeFirst()
    {
        Object ans;
        if (isEmpty())
        {
            ans = null;
        }
        else
        {
            ans = first.getData();
            first = first.getNext();
        }
        return ans;
    }

    public LinkedNode getFirst()
    {
        return first;
    }

    public Object getMiddle()
    {
        Object ans;
        if(isEmpty())
        {
            ans = null;
        }
        else
        {
            LinkedNode current = first;
            LinkedNode jumper = first;

            while ( (jumper != null) && jumper.getNext() != null)
            {
                current = current.getNext();
                jumper = jumper.getNext().getNext();
            }
            ans = current.getData();
        }
        return ans;
    }
}
