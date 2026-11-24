import java.util.Scanner;

/** Problem 8: Undo/Redo for a Text Editor using a Doubly Linked List (bounded history). */
public class TextEditorUndoRedo {
    static class Node {
        String text; Node next, prev;
        Node(String text) { this.text = text; }
    }

    private final int maxHistory;
    private Node head, tail, current;
    private int size;

    TextEditorUndoRedo(int maxHistory) {
        this.maxHistory = maxHistory;
        addState("");                       // initial empty document
    }

    /** Record a new state. Anything after the current node (the redo history) is discarded. */
    void addState(String text) {
        Node n = new Node(text);
        if (current == null) {
            head = tail = current = n;
            size = 1;
            return;
        }
        // Drop the redo branch
        Node c = current.next;
        while (c != null) { size--; c = c.next; }
        current.next = n;
        n.prev = current;
        tail = n;
        current = n;
        size++;
        // Enforce the history limit by dropping the oldest state
        if (size > maxHistory) {
            head = head.next;
            head.prev = null;
            size--;
        }
    }

    boolean undo() {
        if (current.prev == null) return false;
        current = current.prev;
        return true;
    }

    boolean redo() {
        if (current.next == null) return false;
        current = current.next;
        return true;
    }

    String currentText() { return current.text; }

    int historySize() { return size; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TextEditorUndoRedo editor = new TextEditorUndoRedo(10);
        while (true) {
            System.out.println("\n--- Text Editor ---");
            System.out.println("1. Type text (appends to document)\n2. Undo\n3. Redo\n4. Show current text\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1 -> {
                    System.out.print("Text to add: ");
                    editor.addState(editor.currentText() + sc.nextLine());
                    System.out.println("Added. History size: " + editor.historySize());
                }
                case 2 -> System.out.println(editor.undo() ? "Undone." : "Nothing to undo.");
                case 3 -> System.out.println(editor.redo() ? "Redone." : "Nothing to redo.");
                case 4 -> System.out.println("Current text: \"" + editor.currentText() + "\"");
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
