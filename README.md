# Adaptive Huffman Coding with Visualization
A Java program implementing adaptive Huffman coding for text compression and decompression, with a JavaFX-based visualization of the Huffman tree.
##Features

- Encodes text strings into binary sequences using adaptive Huffman coding
- Decodes binary sequences back to original text
- Supports ASCII characters with 8-bit binary codes
- Visualizes the Huffman tree dynamically using JavaFX, highlighting node changes
- Includes a test suite to verify encoding and decoding accuracy
- Interactive menu for encoding, decoding, or exiting

## Requirements

1. Java 8 or higher
2. JavaFX SDK (included in Java 8, or configure separately for later versions)
3. For Java 11+: Add VM options: --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls

## Structure
```
adaptive-huffman-coding/
├── .vscode/
│   ├── launch.json
│   └── settings.json
├── bin/
├── lib/
├── src/
│   ├── AdaptiveHuffman.java
│   ├── Decoder.java
│   ├── Encoder.java
│   ├── HuffmanTree.java
│   ├── Node.java
├── test/
│   └── AdaptiveHuffmanTest.java
├── visualization/
│   └── HuffmanTreeVisualizer.java
├── Mini Project Report (1).pdf
└── README.md

```

## Usage

1. Clone the repository:
   ```bash
      https://github.com/mariamelghandoor/Adaptive-Huffman-Coding-Compressor-with-Visualization.git


2. Navigate to the project directory:
   ```
    cd adaptive-huffman-coding


4. Compile and run:
   ```
    javac AdaptiveHuffman.java Encoder.java Decoder.java HuffmanTree.java Node.java AdaptiveHuffmanTest.java HuffmanTreeVisualizer.java
    java AdaptiveHuffman


5. Follow the menu to encode or decode a string, or run tests via java AdaptiveHuffmanTest.


## Running Tests

- Execute java AdaptiveHuffmanTest to run predefined test cases, verifying encoding and decoding for various inputs.

## Notes

- The program initializes the Huffman tree with a root value of 100.
- Visualization pauses for 1 second per step to show tree updates.
- The visualizer highlights modified nodes in red briefly.
- Paths and delays are hardcoded; modify source code for customization.

## License
MIT License
