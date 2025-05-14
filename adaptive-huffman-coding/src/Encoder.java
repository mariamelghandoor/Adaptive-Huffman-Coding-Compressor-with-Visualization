import java.util.Map;
import java.util.HashMap;

public class Encoder {
    /// search for letter
    public static void encode(Character letter, Node root, Map<Character, String> map, StringBuilder sequence) {
        Node node = HuffmanTree.find_node(root, letter);
        if (node == null) { // not found create it
            // System.out.println("not found");
            /// find nyt to expand from it
            Node nyt_node = HuffmanTree.find_NYT(root);
            if (nyt_node == null)///avoid nullpointerexception if no tree yet
                nyt_node = root;
            if (sequence.length() != 0)// first letter no nyt before it
                sequence.append(nyt_node.binary_code);
            ///append letter then construct tree
            sequence.append(map.get(letter));
            Node new_node = new Node(0, letter);
            HuffmanTree.assign_right_and_NYT(nyt_node, new_node);
            HuffmanTree.assign_binary(root);
            HuffmanTree.assign_number(root);
            HuffmanTree.trace_and_swap_then_increment(new_node, root);
        } else { /// i already have the letter just increment and check swap conditions
            sequence.append(node.binary_code);
            HuffmanTree.trace_and_swap_then_increment(node, root);
        }
    }

    /// Convert string to character array
    private static char[] splitStringToSymbols(String input) {
        return input.toCharArray();
    }

    /// take the string and compress using ASCII codes
    public static String encodeSequence(String input, int root_value) {
        StringBuilder sequence = new StringBuilder();
        /// initialize root then start
        Node root = new Node(0, null);
        HuffmanTree.number_the_root(root, root_value);
        HuffmanTreeVisualizer.visualize(root, "Initial NYT node", root);
        // Create ASCII mapping 
        Map<Character, String> asciiMap = new HashMap<>();
        for (int i = 0; i < 128; i++) {
            String binary = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            asciiMap.put((char)i, binary);
        }

        char[] symbols = splitStringToSymbols(input);
        for (char c : symbols) {
            // System.out.println("Inserting: " + c);
            encode(c, root, asciiMap, sequence);
            try {
                Thread.sleep(1000); // Pause 1 second to observe each step
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the tree is: ");
        HuffmanTree.print_tree(root);
        System.out.println("the sequence is: " + sequence);
        
        return sequence.toString();
    }
}