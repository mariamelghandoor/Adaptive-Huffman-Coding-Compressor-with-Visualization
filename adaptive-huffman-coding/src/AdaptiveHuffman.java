import java.util.Scanner;

public class AdaptiveHuffman {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int root_value = 100; 

        while (true) {
            System.out.println("\nAdaptive Huffman Coding Menu:");
            System.out.println("1. Encode a sequence");
            System.out.println("2. Decode a sequence");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": // Encode
                    System.out.print("Enter the string to encode: ");
                    String inputToEncode = scanner.nextLine();
                    String encoded = Encoder.encodeSequence(inputToEncode, root_value);
                    System.out.println("Encoded result: " + encoded);
                    break;

                case "2": // Decode
                    System.out.print("Enter the encoded string to decode: ");
                    String inputToDecode = scanner.nextLine();
                    String decoded = Decoder.decode(inputToDecode, root_value);
                    System.out.println("Decoded result: " + decoded);
                    break;

                case "3": // Exit
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return; 

                default:
                    System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                    break;
            }
        }
    }
}