import org.junit.Test;

/**
 *       1          |         1
 *   3        10    |    10       3
 * 6  5     9  15   |  15  9     5  6
 */
public class TTraversalAndMirror {

    Node mirror(Node node) {
        if (node == null) return node;

        Node left = mirror(node.left);
        Node right = mirror(node.right);
        // Swap nodes
        node.left = right;
        node.right = left;

        return node;
    }

    @Test
    public void testMirror() {
        Node root = new Node(1,
                new Node(3,
                        new Node(6, null, null),
                        new Node(5, null, null)),
                new Node(10,
                        new Node(9, null, null),
                        new Node(15, null, null))
        );
        System.out.println(root);
        printPreorder(root); System.out.println("PreOrder: ");
        printPostorder(root); System.out.println("PostOrder: ");
        printInorder(root); System.out.println("InOrder: ");
        Node mirror = mirror(root);
        printInorder(root); System.out.println("After mirror InOrder: ");
    }

    void printPostorder(Node node) {
        if (node == null) return;
        printPostorder(node.left);
        printPostorder(node.right);
        System.out.print(node.data + " ");
    }

    void printInorder(Node node) {
        if (node == null) return;
        printInorder(node.left);
        System.out.print(node.data + " ");
        printInorder(node.right);
    }

    void printPreorder(Node node) {
        if (node == null) return;
        System.out.print(node.data + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }

    private class Node {
        int data;
        Node left;
        Node right;

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
