import java.util.Scanner;

/** Problem 2: Movie Management System using a Doubly Linked List. */
public class MovieManagement {
    static class Node {
        String title, director; int year; double rating; Node next, prev;
        Node(String title, String director, int year, double rating) {
            this.title = title; this.director = director; this.year = year; this.rating = rating;
        }
        public String toString() {
            return title + " | " + director + " | " + year + " | Rating: " + rating;
        }
    }

    private Node head, tail;

    void addAtBeginning(String t, String d, int y, double r) {
        Node n = new Node(t, d, y, r);
        if (head == null) head = tail = n;
        else { n.next = head; head.prev = n; head = n; }
    }

    void addAtEnd(String t, String d, int y, double r) {
        Node n = new Node(t, d, y, r);
        if (tail == null) head = tail = n;
        else { tail.next = n; n.prev = tail; tail = n; }
    }

    /** Position is 1-based. */
    boolean addAtPosition(int pos, String t, String d, int y, double r) {
        if (pos < 1) return false;
        if (pos == 1) { addAtBeginning(t, d, y, r); return true; }
        Node cur = head;
        for (int i = 1; i < pos - 1 && cur != null; i++) cur = cur.next;
        if (cur == null) return false;
        if (cur == tail) { addAtEnd(t, d, y, r); return true; }
        Node n = new Node(t, d, y, r);
        n.next = cur.next; n.prev = cur;
        cur.next.prev = n; cur.next = n;
        return true;
    }

    boolean remove(String title) {
        for (Node c = head; c != null; c = c.next) {
            if (c.title.equalsIgnoreCase(title)) {
                if (c.prev != null) c.prev.next = c.next; else head = c.next;
                if (c.next != null) c.next.prev = c.prev; else tail = c.prev;
                return true;
            }
        }
        return false;
    }

    void searchByDirector(String d) {
        boolean found = false;
        for (Node c = head; c != null; c = c.next)
            if (c.director.equalsIgnoreCase(d)) { System.out.println(c); found = true; }
        if (!found) System.out.println("No movies found.");
    }

    void searchByRating(double r) {
        boolean found = false;
        for (Node c = head; c != null; c = c.next)
            if (c.rating == r) { System.out.println(c); found = true; }
        if (!found) System.out.println("No movies found.");
    }

    void displayForward() {
        if (head == null) { System.out.println("No movies."); return; }
        for (Node c = head; c != null; c = c.next) System.out.println(c);
    }

    void displayReverse() {
        if (tail == null) { System.out.println("No movies."); return; }
        for (Node c = tail; c != null; c = c.prev) System.out.println(c);
    }

    boolean updateRating(String title, double r) {
        for (Node c = head; c != null; c = c.next)
            if (c.title.equalsIgnoreCase(title)) { c.rating = r; return true; }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MovieManagement list = new MovieManagement();
        while (true) {
            System.out.println("\n--- Movie Management ---");
            System.out.println("1. Add at beginning\n2. Add at end\n3. Add at position\n4. Remove by title");
            System.out.println("5. Search by director\n6. Search by rating\n7. Display forward\n8. Display reverse");
            System.out.println("9. Update rating\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1, 2, 3 -> {
                    int pos = 0;
                    if (ch == 3) { System.out.print("Position (1-based): "); pos = Integer.parseInt(sc.nextLine().trim()); }
                    System.out.print("Title: ");    String t = sc.nextLine();
                    System.out.print("Director: "); String d = sc.nextLine();
                    System.out.print("Year: ");     int y = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Rating: ");   double r = Double.parseDouble(sc.nextLine().trim());
                    if (ch == 1) list.addAtBeginning(t, d, y, r);
                    else if (ch == 2) list.addAtEnd(t, d, y, r);
                    else if (!list.addAtPosition(pos, t, d, y, r)) System.out.println("Invalid position.");
                }
                case 4 -> { System.out.print("Title: "); System.out.println(list.remove(sc.nextLine()) ? "Removed." : "Not found."); }
                case 5 -> { System.out.print("Director: "); list.searchByDirector(sc.nextLine()); }
                case 6 -> { System.out.print("Rating: "); list.searchByRating(Double.parseDouble(sc.nextLine().trim())); }
                case 7 -> list.displayForward();
                case 8 -> list.displayReverse();
                case 9 -> {
                    System.out.print("Title: "); String t = sc.nextLine();
                    System.out.print("New rating: "); double r = Double.parseDouble(sc.nextLine().trim());
                    System.out.println(list.updateRating(t, r) ? "Updated." : "Not found.");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
