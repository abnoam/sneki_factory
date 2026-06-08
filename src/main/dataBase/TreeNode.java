package main.dataBase;

public class TreeNode
{
    private Object data;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(Object data)
    {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
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
