import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class HuffmanTreeVisualizer extends Application {
    private static Node currentRoot;
    private static Pane pane = new Pane();
    private static Stage primaryStage;
    private static Text statusText = new Text(10, 20, "");
    // private static final double NODE_RADIUS = 20;
    private static final double VERTICAL_GAP = 80;
    // private static final double HORIZONTAL_GAP = 60;
    private static Node lastModifiedNode; // Track the last modified node

    // update the visualization with a new tree root and message
    public static void visualize(Node root, String message, Node modifiedNode) {
        currentRoot = root;
        lastModifiedNode = modifiedNode; // Highlight this node
        if (!Platform.isFxApplicationThread()) {
            if (primaryStage == null) {
                new Thread(() -> launch(HuffmanTreeVisualizer.class)).start();
                try {
                    Thread.sleep(500); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> updateTree(message));
        } else {
            updateTree(message);
        }
    }

    //  close the visualization
    public static void close() {
        Platform.runLater(() -> {
            if (primaryStage != null) {
                primaryStage.close();
                Platform.exit();
            }
        });
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        pane.getChildren().add(statusText); 
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("Huffman Tree Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        updateTree("Initializing tree");
    }

    // Update the tree visualization with message
    private static void updateTree(String message) {
        pane.getChildren().clear();
        pane.getChildren().add(statusText); // Re-add status text
        statusText.setText(message); // Update status message
        if (currentRoot != null) {
            drawTree(currentRoot, 400, 50, 200, new HashMap<>());
        }
    }

    // Recursively draw the tree with highlighting
    private static void drawTree(Node node, double x, double y, double xOffset, Map<Node, Double> xPositions) {
        if (node == null) return;

        String label = node.symbol != null ? node.symbol + " (" + node.value + ")" : "NYT (" + node.value + ")";
        Text text = new Text(x - 10, y, label);
        
        // Highlight if this is the last modified node
        if (node == lastModifiedNode) {
            text.setFill(Color.RED); // Highlight in red
            // Revert to black after a delay
            new Thread(() -> {
                try {
                    Thread.sleep(800); // Highlight for 0.8 seconds
                    Platform.runLater(() -> text.setFill(Color.BLACK));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            text.setFill(Color.BLACK);
        }
        
        pane.getChildren().add(text);
        xPositions.put(node, x);

        if (node.left != null) {
            double leftX = x - xOffset;
            double childY = y + VERTICAL_GAP;
            Line line = new Line(x, y + 10, leftX, childY - 10);
            pane.getChildren().add(line);
            drawTree(node.left, leftX, childY, xOffset / 2, xPositions);
        }
        if (node.right != null) {
            double rightX = x + xOffset;
            double childY = y + VERTICAL_GAP;
            Line line = new Line(x, y + 10, rightX, childY - 10);
            pane.getChildren().add(line);
            drawTree(node.right, rightX, childY, xOffset / 2, xPositions);
        }
    }
}