import org.junit.Test;

public class LLReverse {

    /**
     * 1 -> 2 -> 3 -> 4 -> 5 -> 6
     * on 5th it passes 6 which means base case/exit criteria
     */
    public Node reverse(Node head) {
        if (head == null || head.next == null) return head;
        Node rev = reverse(head.next);
        head.next.next = head; // on final stack 5 -> 6 -> = 5
        head.next = null; // and 5 -> 6 to 5 -> null
        return rev;
    }

    @Test
    public void test() {
        Node a = new Node(10, new Node(20, new Node(30, null)));
        Node node = reverse(a);
        System.out.println(node);
    }

    private class Node {
        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }
}