import org.junit.Assert;
import org.junit.Test;

public class LLDetectLoop {

    public boolean cycle(Node a) {
        if (a == null) return false;
        Node slow , fast;
        slow = fast = a;
        for(;slow.next != null && fast.next != null && fast.next.next !=null;) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) return true;
        }
        return false;
    }

    @Test
    public void test() {
        Node a = new Node(10, null);
        Node b = new Node(20, null);
        Node c = new Node(30, null);
        Node d = new Node(40, null);
        Node e = new Node(50, null);
        Node f = new Node(60, null);
        Node g = new Node(70, null);
        a.next = b; b.next = c; c.next = d; d.next = e;
        e.next = f; f.next = g;
        g.next = c; //adding loop

        Assert.assertEquals(true, cycle(a));

        g.next = null; // removing loop
        Assert.assertEquals(false, cycle(a));

    }

    private class Node {
        int data;
        Node next;
        Node previous;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node(int data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    ", previous=" + previous +
                    '}';
        }
    }
}

