public class Node {
    Integer value;
    Node left;
    Node right;
    Character symbol;
    String binary_code;
    Integer number;
    Node parent;

    Node(int value, Character symbol) {
        this.symbol = symbol;
        this.value = value;
        left = right = null;
        binary_code = "";
        number = null;
        parent = null;
    }

    Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.value = left.value + right.value;
        binary_code = null;
        number = null;
        parent = null;
    }
}