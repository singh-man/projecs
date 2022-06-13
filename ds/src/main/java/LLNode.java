public class LLNode {
    int data;
    LLNode next;
    LLNode previous;

    public LLNode(int data, LLNode next) {
        this.data = data;
        this.next = next;
    }

    public LLNode(int data, LLNode next, LLNode previous) {
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
