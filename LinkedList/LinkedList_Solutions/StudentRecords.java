import java.util.Scanner;

/** Problem 1: Student Record Management using a Singly Linked List. */
public class StudentRecords {
    static class Node {
        int roll; String name; int age; String grade; Node next;
        Node(int roll, String name, int age, String grade) {
            this.roll = roll; this.name = name; this.age = age; this.grade = grade;
        }
        public String toString() {
            return "Roll: " + roll + " | Name: " + name + " | Age: " + age + " | Grade: " + grade;
        }
    }

    private Node head;

    void addAtBeginning(int roll, String name, int age, String grade) {
        Node n = new Node(roll, name, age, grade);
        n.next = head;
        head = n;
    }

    void addAtEnd(int roll, String name, int age, String grade) {
        Node n = new Node(roll, name, age, grade);
        if (head == null) { head = n; return; }
        Node t = head;
        while (t.next != null) t = t.next;
        t.next = n;
    }

    /** Position is 1-based. */
    boolean addAtPosition(int pos, int roll, String name, int age, String grade) {
        if (pos < 1) return false;
        if (pos == 1) { addAtBeginning(roll, name, age, grade); return true; }
        Node t = head;
        for (int i = 1; i < pos - 1 && t != null; i++) t = t.next;
        if (t == null) return false;
        Node n = new Node(roll, name, age, grade);
        n.next = t.next;
        t.next = n;
        return true;
    }

    boolean delete(int roll) {
        if (head == null) return false;
        if (head.roll == roll) { head = head.next; return true; }
        Node t = head;
        while (t.next != null && t.next.roll != roll) t = t.next;
        if (t.next == null) return false;
        t.next = t.next.next;
        return true;
    }

    Node search(int roll) {
        for (Node t = head; t != null; t = t.next) if (t.roll == roll) return t;
        return null;
    }

    void display() {
        if (head == null) { System.out.println("No records."); return; }
        for (Node t = head; t != null; t = t.next) System.out.println(t);
    }

    boolean updateGrade(int roll, String grade) {
        Node n = search(roll);
        if (n == null) return false;
        n.grade = grade;
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentRecords list = new StudentRecords();
        while (true) {
            System.out.println("\n--- Student Records ---");
            System.out.println("1. Add at beginning\n2. Add at end\n3. Add at position\n4. Delete by roll");
            System.out.println("5. Search by roll\n6. Display all\n7. Update grade\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1, 2, 3 -> {
                    int pos = 0;
                    if (ch == 3) { System.out.print("Position (1-based): "); pos = Integer.parseInt(sc.nextLine().trim()); }
                    System.out.print("Roll: ");  int roll = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Name: ");  String name = sc.nextLine();
                    System.out.print("Age: ");   int age = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Grade: "); String grade = sc.nextLine();
                    if (ch == 1) list.addAtBeginning(roll, name, age, grade);
                    else if (ch == 2) list.addAtEnd(roll, name, age, grade);
                    else if (!list.addAtPosition(pos, roll, name, age, grade)) System.out.println("Invalid position.");
                }
                case 4 -> {
                    System.out.print("Roll: ");
                    System.out.println(list.delete(Integer.parseInt(sc.nextLine().trim())) ? "Deleted." : "Not found.");
                }
                case 5 -> {
                    System.out.print("Roll: ");
                    Node n = list.search(Integer.parseInt(sc.nextLine().trim()));
                    System.out.println(n == null ? "Not found." : n);
                }
                case 6 -> list.display();
                case 7 -> {
                    System.out.print("Roll: "); int roll = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New grade: "); String g = sc.nextLine();
                    System.out.println(list.updateGrade(roll, g) ? "Updated." : "Not found.");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
