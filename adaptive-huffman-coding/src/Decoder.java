import java.util.Map;
import java.util.HashMap;

public class Decoder {
    public static String decode(String sequence, int root_value) {
        if(sequence.length() == 0) return "";
        ///used as when decompress i have binary not letters i want to find letters by their binary
        Map<String, Character> swappedMap = swapMap();
        /// initilaze root
        Node root = new Node(0, null);
        HuffmanTree.number_the_root(root, root_value);

        int index_of_binary = 8; // ASCII uses 8 bits
        int binary_length_index = 1;
        StringBuilder symbol_list = new StringBuilder();
        boolean found = false;
        /// first letter doesnt apply the nyt first rule
        Character letter = swappedMap.get(sequence.substring(0, 8));
        Node new_node_start = new Node(0, letter);
        HuffmanTree.assign_right_and_NYT(root, new_node_start);
        HuffmanTree.assign_binary(root);
        HuffmanTree.assign_number(root);
        HuffmanTree.trace_and_swap_then_increment(new_node_start, root);
        symbol_list.append(letter);

        HuffmanTree.print_tree(root);
        try {
            Thread.sleep(1000); // Pause after first step
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (index_of_binary < sequence.length()) {
            //// search up to 8 values infront of me
            while(binary_length_index < 8 && binary_length_index <= sequence.length() - index_of_binary){
                Node node = HuffmanTree.find_symbol_using_binary(root, sequence.substring(index_of_binary, index_of_binary + binary_length_index));
                
                if(node != null && HuffmanTree.is_leaf(node)){
                    if(HuffmanTree.isNYT(node)){
                        String temp = sequence.substring(index_of_binary + binary_length_index, index_of_binary + binary_length_index + 8);
                        letter = swappedMap.get(temp);
                        // System.out.println();
                        Node new_node = new Node(0, letter);
                        HuffmanTree.assign_right_and_NYT(node, new_node);
                        HuffmanTree.assign_binary(root);
                        HuffmanTree.assign_number(root);
                        HuffmanTree.trace_and_swap_then_increment(new_node, root);
                        symbol_list.append(letter);
                        index_of_binary += (binary_length_index + 8);
                    }else{
                        // System.out.println("found: " + node.symbol);
                        symbol_list.append(node.symbol);
                        HuffmanTree.trace_and_swap_then_increment(node, root);
                        index_of_binary += binary_length_index;
                    }
                    found = true;
                    break;
                }else binary_length_index++;
            }
            if(!found){
                index_of_binary++;
                System.out.println("error: no symbol found");
            }
            binary_length_index = 1;
            found = false;
            try {
                Thread.sleep(1000); // Pause after each step
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the tree is: ");
        HuffmanTree.print_tree(root);
        System.out.println("the sequence is: " + symbol_list);
        HuffmanTreeVisualizer.close();
        return symbol_list.toString();
    }

    /// Create swapped ASCII map 
    private static Map<String, Character> swapMap() {
        Map<String, Character> swappedMap = new HashMap<>();
        for (int i = 0; i < 128; i++) {
            String binary = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            swappedMap.put(binary, (char)i);
        }
        return swappedMap;
    }
}