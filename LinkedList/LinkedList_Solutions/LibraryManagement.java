import java.util.Scanner;

/** Problem 5: Library Management System using a Doubly Linked List. */
public class LibraryManagement {
    static class Node {
        String title, author, genre; int id; boolean available; Node next, prev;
        Node(String title, String author, String genre, int id, boolean available) {
            this.title = title; this.author = author; this.genre = genre; this.id = id; this.available = available;
        }
        public String toString() {
            return "ID: " + id + " | " + title + " | " + author + " | " + genre + " | "
                    + (available ? "Available" : "Issued");
        }
    }

    private Node head, tail;

    void addAtBeginning(String t, String a, String g, int id, boolean av) {
        Node n = new Node(t, a, g, id, av);
        if (head == null) head = tail = n;
        else { n.next = head; head.prev = n; head = n; }
    }

    void addAtEnd(String t, String a, String g, int id, boolean av) {
        Node n = new Node(t, a, g, id, av);
        if (tail == null) head = tail = n;
        else { tail.next = n; n.prev = tail; tail = n; }
    }

    /** Position is 1-based. */
    boolean addAtPosition(int pos, String t, String a, String g, int id, boolean av) {
        if (pos < 1) return false;
        if (pos == 1) { addAtBeginning(t, a, g, id, av); return true; }
        Node c = head;
        for (int i = 1; i < pos - 1 && c != null; i++) c = c.next;
        if (c == null) return false;
        if (c == tail) { addAtEnd(t, a, g, id, av); return true; }
        Node n = new Node(t, a, g, id, av);
        n.next = c.next; n.prev = c;
        c.next.prev = n; c.next = n;
        return true;
    }

    boolean remove(int id) {
        for (Node c = head; c != null; c = c.next) {
            if (c.id == id) {
                if (c.prev != null) c.prev.next = c.next; else head = c.next;
                if (c.next != null) c.next.prev = c.prev; else tail = c.prev;
                return true;
            }
        }
        return false;
    }

    void searchByTitle(String title) {
        boolean found = false;
        for (Node c = head; c != null; c = c.next)
            if (c.title.equalsIgnoreCase(title)) { System.out.println(c); found = true; }
        if (!found) System.out.println("No books found.");
    }

    void searchByAuthor(String author) {
        boolean found = false;
        for (Node c = head; c != null; c = c.next)
            if (c.author.equalsIgnoreCase(author)) { System.out.println(c); found = true; }
        if (!found) System.out.println("No books found.");
    }

    boolean updateAvailability(int id, boolean available) {
        for (Node c = head; c != null; c = c.next)
            if (c.id == id) { c.available = available; return true; }
        return false;
    }

    void displayForward() {
        if (head == null) { System.out.println("Library is empty."); return; }
        for (Node c = head; c != null; c = c.next) System.out.println(c);
    }

    void displayReverse() {
        if (tail == null) { System.out.println("Library is empty."); return; }
        for (Node c = tail; c != null; c = c.prev) System.out.println(c);
    }

    int count() {
        int n = 0;
        for (Node c = head; c != null; c = c.next) n++;
        return n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryManagement lib = new LibraryManagement();
        while (true) {
            System.out.println("\n--- Library Management ---");
            System.out.println("1. Add at beginning\n2. Add at end\n3. Add at position\n4. Remove by ID");
            System.out.println("5. Search by title\n6. Search by author\n7. Update availability");
            System.out.println("8. Display forward\n9. Display reverse\n10. Count books\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1, 2, 3 -> {
                    int pos = 0;
                    if (ch == 3) { System.out.print("Position (1-based): "); pos = Integer.parseInt(sc.nextLine().trim()); }
                    System.out.print("Title: ");  String t = sc.nextLine();
                    System.out.print("Author: "); String a = sc.nextLine();
                    System.out.print("Genre: ");  String g = sc.nextLine();
                    System.out.print("Book ID: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Available? (y/n): "); boolean av = sc.nextLine().trim().equalsIgnoreCase("y");
                    if (ch == 1) lib.addAtBeginning(t, a, g, id, av);
                    else if (ch == 2) lib.addAtEnd(t, a, g, id, av);
                    else if (!lib.addAtPosition(pos, t, a, g, id, av)) System.out.println("Invalid position.");
                }
                case 4 -> { System.out.print("Book ID: "); System.out.println(lib.remove(Integer.parseInt(sc.nextLine().trim())) ? "Removed." : "Not found."); }
                case 5 -> { System.out.print("Title: "); lib.searchByTitle(sc.nextLine()); }
                case 6 -> { System.out.print("Author: "); lib.searchByAuthor(sc.nextLine()); }
                case 7 -> {
                    System.out.print("Book ID: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Available? (y/n): "); boolean av = sc.nextLine().trim().equalsIgnoreCase("y");
                    System.out.println(lib.updateAvailability(id, av) ? "Updated." : "Not found.");
                }
                case 8 -> lib.displayForward();
                case 9 -> lib.displayReverse();
                case 10 -> System.out.println("Total books: " + lib.count());
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
