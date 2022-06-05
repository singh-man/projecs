import org.junit.Test;

public class LLMergeSorted {

    public Node sortedMerge(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        if (a.data < b.data) {
            a.next = sortedMerge(a.next, b);
            return a;
        } else {
            b.next = sortedMerge(b.next, a);
            return b;
        }
    }

    @Test
    public void test() {
        Node a = new Node(10, new Node(20, new Node(30, null)));
        Node b = new Node(11, new Node(15, new Node(31, null)));
        Node node = sortedMerge(a, b);
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

