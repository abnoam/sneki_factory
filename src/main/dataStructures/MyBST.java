package main.dataStructures;
import main.baseClasses.Client;

public class MyBST
{
    private TreeNode root;

    public MyBST()
    {
        this.root = null;
    }

    public void insert(Client client)
    {
        if (client != null)
        {
            root = insertRec(root, client);
        }
        else
        {
            System.out.println("Error: Object must implement client.");
        }
    }
    private TreeNode insertRec(TreeNode root, Client client)
    {
        if (root == null)
        {
            return new TreeNode(client);
        }

        int newId = client.getClientID();
        int rootId = root.getData().getClientID();

        if (newId < rootId) // go left side of tree
        {
            root.setLeft(insertRec(root.getLeft(), client));
        }
        else if (newId > rootId) //go right side of tree
        {
            root.setRight(insertRec(root.getRight(), client));
        }
        return root;
    }

    private TreeNode searchRecByID(TreeNode root, int clientID)
    {
        if(root == null || root.getData().getClientID() == clientID)
            return root;


        if(root.getData().getClientID() > clientID) // search left side of the tree
        {
            return searchRecByID(root.getLeft(), clientID);
        }

        return searchRecByID(root.getRight(), clientID); // search right side of the tree
    }

    public TreeNode searchByID(int clientID)
    {
        return searchRecByID(root,clientID);
    }

    public void printInOrder()
    {
        printInOrderRec(root);
    }

    private void printInOrderRec(TreeNode root)
    {if (root != null)
        { //67
        printInOrderRec(root.getLeft());
        System.out.println(root.getData().toString());
        printInOrderRec(root.getRight());
        }
    }
}
