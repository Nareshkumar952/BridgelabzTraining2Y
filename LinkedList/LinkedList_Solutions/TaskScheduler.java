import java.util.Scanner;

/** Problem 3: Task Scheduler using a Circular Linked List. */
public class TaskScheduler {
    static class Node {
        int id; String name; int priority; String dueDate; Node next;
        Node(int id, String name, int priority, String dueDate) {
            this.id = id; this.name = name; this.priority = priority; this.dueDate = dueDate;
        }
        public String toString() {
            return "ID: " + id + " | " + name + " | Priority: " + priority + " | Due: " + dueDate;
        }
    }

    private Node head, tail, current;

    void addAtBeginning(int id, String name, int p, String due) {
        Node n = new Node(id, name, p, due);
        if (head == null) { head = tail = current = n; n.next = n; }
        else { n.next = head; head = n; tail.next = head; }
    }

    void addAtEnd(int id, String name, int p, String due) {
        Node n = new Node(id, name, p, due);
        if (head == null) { head = tail = current = n; n.next = n; }
        else { tail.next = n; tail = n; tail.next = head; }
    }

    /** Position is 1-based. */
    boolean addAtPosition(int pos, int id, String name, int p, String due) {
        if (pos < 1) return false;
        if (pos == 1) { addAtBeginning(id, name, p, due); return true; }
        if (head == null) return false;
        Node c = head;
        for (int i = 1; i < pos - 1; i++) {
            c = c.next;
            if (c == head) return false; // wrapped around: position too large
        }
        if (c == tail) { addAtEnd(id, name, p, due); return true; }
        Node n = new Node(id, name, p, due);
        n.next = c.next;
        c.next = n;
        return true;
    }

    boolean remove(int id) {
        if (head == null) return false;
        Node prev = tail, c = head;
        do {
            if (c.id == id) {
                if (c == head && c == tail) { head = tail = current = null; return true; }
                if (current == c) current = c.next;
                prev.next = c.next;
                if (c == head) head = c.next;
                if (c == tail) tail = prev;
                return true;
            }
            prev = c; c = c.next;
        } while (c != head);
        return false;
    }

    void viewCurrentAndMoveNext() {
        if (current == null) { System.out.println("No tasks."); return; }
        System.out.println("Current task: " + current);
        current = current.next;
        System.out.println("Next task:    " + current);
    }

    void display() {
        if (head == null) { System.out.println("No tasks."); return; }
        Node c = head;
        do { System.out.println(c); c = c.next; } while (c != head);
    }

    void searchByPriority(int p) {
        if (head == null) { System.out.println("No tasks."); return; }
        boolean found = false;
        Node c = head;
        do { if (c.priority == p) { System.out.println(c); found = true; } c = c.next; } while (c != head);
        if (!found) System.out.println("No tasks with that priority.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskScheduler list = new TaskScheduler();
        while (true) {
            System.out.println("\n--- Task Scheduler ---");
            System.out.println("1. Add at beginning\n2. Add at end\n3. Add at position\n4. Remove by ID");
            System.out.println("5. View current & move next\n6. Display all\n7. Search by priority\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1, 2, 3 -> {
                    int pos = 0;
                    if (ch == 3) { System.out.print("Position (1-based): "); pos = Integer.parseInt(sc.nextLine().trim()); }
                    System.out.print("Task ID: ");  int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Name: ");     String name = sc.nextLine();
                    System.out.print("Priority: "); int p = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Due date: "); String due = sc.nextLine();
                    if (ch == 1) list.addAtBeginning(id, name, p, due);
                    else if (ch == 2) list.addAtEnd(id, name, p, due);
                    else if (!list.addAtPosition(pos, id, name, p, due)) System.out.println("Invalid position.");
                }
                case 4 -> { System.out.print("Task ID: "); System.out.println(list.remove(Integer.parseInt(sc.nextLine().trim())) ? "Removed." : "Not found."); }
                case 5 -> list.viewCurrentAndMoveNext();
                case 6 -> list.display();
                case 7 -> { System.out.print("Priority: "); list.searchByPriority(Integer.parseInt(sc.nextLine().trim())); }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
