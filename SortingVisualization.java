
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortingVisualization extends JPanel {
    private int size = 100;
    private int gap = 6;
    private Integer[] numbers;
    private String currentAlgorithm = "Bubble Sort"; // Algorithm name
    private JButton bubbleSortButton;
    private JButton quickSortButton;

    public SortingVisualization() {
        // Initialize the array
        numbers = new Integer[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }

        // Shuffle the array
        shuffleArray();

        // Set up the JFrame
        JFrame frame = new JFrame("Sorting Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(gap * size + 50, size + 100);
        frame.setLayout(new BorderLayout());

        // Create buttons
        JPanel buttonPanel = new JPanel();
        bubbleSortButton = new JButton("Bubble Sort");
        quickSortButton = new JButton("Quick Sort");

        // Add action listeners to buttons
        bubbleSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetArray();
                new Thread(SortingVisualization.this::bubbleSort).start();
            }
        });

        quickSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetArray();
                new Thread(SortingVisualization.this::quickSort).start();
            }
        });

        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(quickSortButton);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Shuffle the array
    private void shuffleArray() {
        List<Integer> numberList = Arrays.asList(numbers);
        Collections.shuffle(numberList);
        numbers = numberList.toArray(new Integer[0]);
    }

    // Reset the array and shuffle it again
    private void resetArray() {
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }
        shuffleArray();
        repaint();
    }

    // Function for swapping the values and repainting the lines
    private void swap(int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;

        // Repaint the panel to visualize the swap
        repaint();
        try {
            Thread.sleep(50); // Adjust delay for better visualization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Bubble sort function
    private void bubbleSort() {
        currentAlgorithm = "Bubble Sort"; // Set current algorithm
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }
        System.out.println(Arrays.toString(numbers));
    }

    // Quick sort function
    private void quickSort() {
        currentAlgorithm = "Quick Sort"; // Set current algorithm
        quickSortHelper(0, size - 1);
        System.out.println(Arrays.toString(numbers));
    }

    // Helper method for Quick Sort
    private void quickSortHelper(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSortHelper(low, pi - 1);
            quickSortHelper(pi + 1, high);
        }
    }

    // Partition method for Quick Sort
    private int partition(int low, int high) {
        int pivot = numbers[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (numbers[j] < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    // Paint method to draw the lines
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK); // Set background color
        g.setColor(Color.GREEN);
        g.drawString(currentAlgorithm, 25, 20); // Draw algorithm name
        for (int i = 0; i < size; i++) {
            g.fillRect(i * gap + 25, getHeight() - numbers[i], gap - 1, numbers[i]);
        }
    }

    // Main method to run the visualization
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SortingVisualization::new);
    }
}
