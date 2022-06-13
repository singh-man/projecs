import org.junit.Test;

/**
 *       1
 *   3      -10
 * 6  5    9   15
 */
public class BTMaxPathSum {

    private int max = Integer.MIN_VALUE;

    int postOrderMaxSumPath(BTNode node) {
        if (node == null) return 0;
        int left = Math.max(postOrderMaxSumPath(node.left), 0);
        int right = Math.max(postOrderMaxSumPath(node.right), 0);
        max = Math.max(max, left + right + node.data); // Each node and its children max value
        return Math.max(left, right) + node.data;
    }

    @Test
    public void testPostOrderMaxSumPath() {
        BTNode root = new BTNode(1,
                new BTNode(3,
                        new BTNode(6, null, null),
                        new BTNode(5, null, null)),
                new BTNode(-10,
                        new BTNode(9, null, null),
                        new BTNode(15, null, null))
        );
        System.out.println(postOrderMaxSumPath(root));
    }
}