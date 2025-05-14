import java.util.*;

////// notes for me to remember what i was doing
public class HuffmanTree {
    ///// da el main function that does most of the alg
    public static void trace_and_swap_then_increment(Node node, Node root) {
        if (node == null) {
            return;
        }
        // print_tree(root);
        // System.out.println("----------------------");
        if (node.value == 0) {///// if i am a new node only increment and go to my parent
            node.value += 1;
            // if (node != root)
            //     swap_operation(node.parent, root);
        } else { ////// else swap first then increment
            if (node != root)
                swap_operation(node, root);
            node.value += 1;
        }
        // System.out.println("Incremented value of " + node.symbol + " to " + node.value);
        // System.out.println("----------------------");
        if (node.parent != null) { // wa recursevly do that again to my parent
            trace_and_swap_then_increment(node.parent, root);
        }
        String message = "Incremented " + (node.symbol != null ? node.symbol : "NYT") + " to " + node.value;
        HuffmanTreeVisualizer.visualize(root, message, node);// Visualize after increment
    }

    ///// dy swaps node if after seaching the whole tree i found the conditions satisfied
    public static void swap_operation(Node node, Node root) {
        //// search
        Node swapCandidate = find_nearest_swap_candidate(root, node, null, 0);
        if (swapCandidate != null && swapCandidate.parent != null) {
            swap_nodes(node, swapCandidate);
            ////re assign the values
            assign_number(root);
            assign_binary(root);
            // System.out.println("Swapped " + node.symbol + " with " + swapCandidate.symbol);
            // Visualize after swap
            String message = "Swapped " + (node.symbol != null ? node.symbol : "NYT") + " with " + 
                            (swapCandidate.symbol != null ? swapCandidate.symbol : "NYT");
            HuffmanTreeVisualizer.visualize(root, message, node);
        }
    }

    ////traversing the tree dfs pioratizing the nearst to the root == smallest depth
    private static Node find_nearest_swap_candidate(Node current, Node target, Node bestCandidate, int depth) {
        if (target.parent == null || current == null) {
            return bestCandidate;
        }
        if (current.value == target.value && current.number > target.number && target.parent != current) {
            bestCandidate = current;
        }
        bestCandidate = find_nearest_swap_candidate(current.right, target, bestCandidate, depth + 1);
        bestCandidate = find_nearest_swap_candidate(current.left, target, bestCandidate, depth + 1);
        return bestCandidate;
    }

    ///// swap the the node parests and if the other is left child i become left  child
    /// i dont swap children its a whole complete tree swap
    private static void swap_nodes(Node a, Node b) {
        if (a == null || b == null || a == b)
            return;
        Node aParent = a.parent;
        Node bParent = b.parent;
        boolean aIsLeft = aParent != null && aParent.left == a;
        boolean bIsLeft = bParent != null && bParent.left == b;
        if (aParent != null) {
            if (aIsLeft)
                aParent.left = b;
            else
                aParent.right = b;
        }
        if (bParent != null) {
            if (bIsLeft)
                bParent.left = a;
            else
                bParent.right = a;
        }
        a.parent = bParent;
        b.parent = aParent;
    }

    //// this initializes the root vars
    public static void number_the_root(Node root, int value) {
        root.number = value;
        root.binary_code = "";
    }

    //// traverse the whole tree dfs and assign the binary code
    public static void assign_binary(Node root) {
        if (root.left == null && root.right == null) {
            return;
        }
        root.left.binary_code = root.binary_code + "0";
        root.right.binary_code = root.binary_code + "1";
        assign_binary(root.left);
        assign_binary(root.right);
    }

    //// traverse the whole tree bfs and assign the numbers
    public static void assign_number(Node root) {
        if (root.left == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<Node>();
        if (root.right != null)
            queue.add(root.right);
        if (root.left != null)
            queue.add(root.left);
        int count = root.number - 1;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            node.number = count--;
            if (node.right != null)
                queue.add(node.right);
            if (node.left != null)
                queue.add(node.left);
        }
    }

    //// dy bnadyha when i get a new letter so that i can expand the tree from nyt 
    /// and then assign the right values and assign parents and childs 
    public static void assign_right_and_NYT(Node root, Node right) {
        root.right = right;
        right.parent = root;
        Node nyt_node = new Node(0, null);
        root.left = nyt_node;
        nyt_node.parent = root;
        String message = "Inserted symbol: " + (right.symbol != null ? right.symbol : "NYT");
        HuffmanTreeVisualizer.visualize(root, message, right); // Visualize after adding new node
    }

    ///// check if nyt
    public static boolean isNYT(Node root) {
        if (root == null)
            return false;
        return root.symbol == null && root.parent != null && root.value == 0;
    }

    ///b7awl ala2y dfs if the letter was seen before or not if yes i return the node to increament on it
    public static Node find_node(Node root, Character symbol) {
        if (root == null) {
            return null;
        }
        if (root.symbol == symbol) {
            return root;
        }
        Node left = find_node(root.left, symbol);
        Node right = find_node(root.right, symbol);
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }

    /// this is used to be able to expand from nyt so i search for it dfs
    public static Node find_NYT(Node root) {
        if (root == null) {
            return null;
        }
        if (isNYT(root)) {
            return root;
        }
        Node left = find_NYT(root.left);
        Node right = find_NYT(root.right);
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }

    ///// when decreapting i search the tree with the binaary code i have from string
    public static Node find_symbol_using_binary(Node node, String binary) {
        if (node == null) {
            return null;
        }
        if (binary.equals(node.binary_code)) {
            return node;
        }
        Node leftResult = find_symbol_using_binary(node.left, binary);
        return (leftResult != null) ? leftResult : find_symbol_using_binary(node.right, binary);
    }

    /// to check if the node i found actualy an nyt or letter not a null node 
    /// i use it when searching with binary to not take a parent node and mistake it for letter
    public static boolean is_leaf(Node root) {
        return root.left == null && root.right == null;
    }

    ///// just to trace
    public static void print_tree(Node root) {
        if (root == null) {
            return;
        }
        System.out.println(root.symbol + " " + root.value + " " + root.number + " " + root.binary_code);
        print_tree(root.left);
        print_tree(root.right);
    }
}