package main.dataBase;

public class MyBST
{
    private TreeNode root;

    public MyBST()
    {
        this.root = null;
    }

    public void insert(Object obj)
    {
        if (obj != null && obj instanceof TreeIdentify)
        {
            root = insertRec(root, obj);
        }
        else
        {
            System.out.println("Error: Object must implement TreeIdentify.");
        }
    }
    private TreeNode insertRec(TreeNode root, Object obj)
    {
        if (root == null)
        {
            return new TreeNode(obj);
        }

        int newId = ((TreeIdentify)obj).getTreeID();
        int rootId = ((TreeIdentify) root.getData()).getTreeID();

        if (newId < rootId) // go left side of tree
        {
            root.setLeft(insertRec(root.getLeft(), obj));
        }
        else if (newId > rootId) //go right side of tree
        {
            root.setRight(insertRec(root.getRight(), obj));
        }
        return root;
    }

    public TreeNode searchRec(TreeNode root, int idToFind)
    {
        if(root == null)
            return null;

        int rootId = ((TreeIdentify)root.getData()).getTreeID();

        if(rootId == idToFind) // id found
        {
            return root;
        }
        if(rootId > idToFind) // search left side of tree
        {
            return searchRec(root.getLeft(), idToFind);
        }

        return searchRec(root.getRight(), idToFind); // search right side of tree
    }

    public void printInOrder()
    {
        printInOrderRec(root);
    }

    private void printInOrderRec(TreeNode root)
    {
        printInOrderRec(root.getLeft());
        System.out.println(root.getData().toString());
        printInOrderRec(root.getRight());
    }
}
