import org.junit.Test;

/**
 *       1         |         1
 *   3       10    |    10       3
 * 6  5    9  15   |  15  9    5  6
 */
public class BTTraversalAndMirror {

    BTNode mirror(BTNode node) {
        if (node == null) return node;

        BTNode left = mirror(node.left);
        BTNode right = mirror(node.right);
        // Swap nodes
        node.left = right;
        node.right = left;

        return node;
    }

    @Test
    public void testMirror() {
        BTNode root = new BTNode(1,
                new BTNode(3,
                        new BTNode(6, null, null),
                        new BTNode(5, null, null)),
                new BTNode(10,
                        new BTNode(9, null, null),
                        new BTNode(15, null, null))
        );
        System.out.println(root);
        printPreorder(root); System.out.println("PreOrder: ");
        printPostorder(root); System.out.println("PostOrder also called Bottom's UP: ");
        printInorder(root); System.out.println("InOrder: ");
        BTNode mirror = mirror(root);
        printInorder(root); System.out.println("After mirror InOrder: ");
    }

    void printPostorder(BTNode node) {
        if (node == null) return;
        printPostorder(node.left);
        printPostorder(node.right);
        System.out.print(node.data + " ");
    }

    void printInorder(BTNode node) {
        if (node == null) return;
        printInorder(node.left);
        System.out.print(node.data + " ");
        printInorder(node.right);
    }

    void printPreorder(BTNode node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }

}
