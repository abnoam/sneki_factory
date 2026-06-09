package main.dataStructures;
import main.baseClasses.Client;

public class TreeNode
{
    private Client data;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(Client data)
    {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public Client getData()
    {
        return data;
    }

    public void setData(Client data)
    {
        this.data = data;
    }
    public TreeNode getLeft()
    {
        return left;
    }
    public TreeNode getRight()
    {
        return right;
    }

    public void setLeft(TreeNode left)
    {
        this.left = left;
    }
    public void setRight(TreeNode right)
    {
        this.right = right;
    }

}
