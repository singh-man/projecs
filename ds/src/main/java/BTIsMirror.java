import org.junit.Assert;
import org.junit.Test;

/**
 *       1
 *   2       2
 * 4   5   5  4
 */
public class BTIsMirror {

    boolean isMirror(BTNode node1, BTNode node2) {
        if (node1 == null && node2 == null) return true;

        if (node1 != null && node2 != null
            && node1.data == node2.data) {
            return isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);
        }
        return false;
    }

    @Test
    public void testMirror() {
        BTNode root = new BTNode(1,
                new BTNode(2,
                        new BTNode(4, null, null),
                        new BTNode(5, null, null)),
                new BTNode(2,
                        new BTNode(5, null, null),
                        new BTNode(4, null, null))
        );
        Assert.assertEquals(true, isMirror(root, root));

        root.left.left.left = new BTNode(6, null, null);
        Assert.assertEquals(false, isMirror(root, root));
    }

}
