public class AdaptiveHuffmanTest {
    static class TestCase {
        String input;
        String expectedEncoded;
        int rootValue;

        TestCase(String input, String expectedEncoded, int rootValue) {
            this.input = input;
            this.expectedEncoded = expectedEncoded;
            this.rootValue = rootValue;
        }
    }

    public static void main(String[] args) {
        TestCase[] testCases = {
            new TestCase("abcccaaaa", "011000010011000100001100011101000101110", 100), 
            new TestCase("aabb", "01100001100110001001", 100),          
            new TestCase("xyz", "011110000011110010001111010", 100),        
            new TestCase("", "", 100),                          
            new TestCase("aaaa", "01100001111", 100)          
        };

        boolean allTestsPassed = true;

        for (int i = 0; i < testCases.length; i++) {
            TestCase test = testCases[i];
            System.out.println("Test " + (i + 1) + " - Input: \"" + test.input + "\"");

            String encoded = Encoder.encodeSequence(test.input, test.rootValue);
            String decoded = Decoder.decode(encoded, test.rootValue);

            System.out.println("Original: " + test.input);
            System.out.println("Encoded:  " + encoded);
            System.out.println("Expected Encoded: " + test.expectedEncoded);
            System.out.println("Decoded:  " + decoded);

            System.out.println("Expected Encoded: " + encoded);
            boolean encodePass = test.expectedEncoded.equals(encoded);
            System.out.println("***** Compression Test *****");
            System.out.println(encodePass ? "Pass" : "Fail");

            boolean decodePass = test.input.equals(decoded);
            System.out.println("***** Decompression Test *****");
            System.out.println(decodePass ? "Pass" : "Fail");

            if (!encodePass || !decodePass) {
                allTestsPassed = false;
            }

            System.out.println("------------------------");
        }

        System.out.println("All Tests " + (allTestsPassed ? "Passed" : "Failed"));

        HuffmanTreeVisualizer.close();
    }
}