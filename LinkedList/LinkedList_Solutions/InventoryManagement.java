import java.util.Scanner;

/** Problem 4: Inventory Management System using a Singly Linked List. */
public class InventoryManagement {
    static class Node {
        String name; int id; int qty; double price; Node next;
        Node(String name, int id, int qty, double price) {
            this.name = name; this.id = id; this.qty = qty; this.price = price;
        }
        public String toString() {
            return "ID: " + id + " | " + name + " | Qty: " + qty + " | Price: " + price;
        }
    }

    private Node head;

    void addAtBeginning(String name, int id, int qty, double price) {
        Node n = new Node(name, id, qty, price);
        n.next = head; head = n;
    }

    void addAtEnd(String name, int id, int qty, double price) {
        Node n = new Node(name, id, qty, price);
        if (head == null) { head = n; return; }
        Node t = head;
        while (t.next != null) t = t.next;
        t.next = n;
    }

    /** Position is 1-based. */
    boolean addAtPosition(int pos, String name, int id, int qty, double price) {
        if (pos < 1) return false;
        if (pos == 1) { addAtBeginning(name, id, qty, price); return true; }
        Node t = head;
        for (int i = 1; i < pos - 1 && t != null; i++) t = t.next;
        if (t == null) return false;
        Node n = new Node(name, id, qty, price);
        n.next = t.next; t.next = n;
        return true;
    }

    boolean remove(int id) {
        if (head == null) return false;
        if (head.id == id) { head = head.next; return true; }
        Node t = head;
        while (t.next != null && t.next.id != id) t = t.next;
        if (t.next == null) return false;
        t.next = t.next.next;
        return true;
    }

    boolean updateQuantity(int id, int qty) {
        Node n = searchById(id);
        if (n == null) return false;
        n.qty = qty;
        return true;
    }

    Node searchById(int id) {
        for (Node t = head; t != null; t = t.next) if (t.id == id) return t;
        return null;
    }

    Node searchByName(String name) {
        for (Node t = head; t != null; t = t.next) if (t.name.equalsIgnoreCase(name)) return t;
        return null;
    }

    double totalValue() {
        double sum = 0;
        for (Node t = head; t != null; t = t.next) sum += t.price * t.qty;
        return sum;
    }

    void display() {
        if (head == null) { System.out.println("Inventory is empty."); return; }
        for (Node t = head; t != null; t = t.next) System.out.println(t);
    }

    // ---- Merge sort ----
    void sort(boolean byName, boolean ascending) {
        head = mergeSort(head, byName, ascending);
    }

    private Node mergeSort(Node h, boolean byName, boolean asc) {
        if (h == null || h.next == null) return h;
        Node slow = h, fast = h.next;               // find middle
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        Node right = slow.next;
        slow.next = null;
        return merge(mergeSort(h, byName, asc), mergeSort(right, byName, asc), byName, asc);
    }

    private Node merge(Node a, Node b, boolean byName, boolean asc) {
        Node dummy = new Node("", 0, 0, 0), tail = dummy;
        while (a != null && b != null) {
            int cmp = byName ? a.name.compareToIgnoreCase(b.name) : Double.compare(a.price, b.price);
            if (!asc) cmp = -cmp;
            if (cmp <= 0) { tail.next = a; a = a.next; } else { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManagement inv = new InventoryManagement();
        while (true) {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. Add at beginning\n2. Add at end\n3. Add at position\n4. Remove by ID");
            System.out.println("5. Update quantity\n6. Search by ID\n7. Search by name\n8. Total inventory value");
            System.out.println("9. Sort\n10. Display all\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1, 2, 3 -> {
                    int pos = 0;
                    if (ch == 3) { System.out.print("Position (1-based): "); pos = Integer.parseInt(sc.nextLine().trim()); }
                    System.out.print("Item name: ");  String name = sc.nextLine();
                    System.out.print("Item ID: ");    int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Quantity: ");   int q = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Price: ");      double p = Double.parseDouble(sc.nextLine().trim());
                    if (ch == 1) inv.addAtBeginning(name, id, q, p);
                    else if (ch == 2) inv.addAtEnd(name, id, q, p);
                    else if (!inv.addAtPosition(pos, name, id, q, p)) System.out.println("Invalid position.");
                }
                case 4 -> { System.out.print("Item ID: "); System.out.println(inv.remove(Integer.parseInt(sc.nextLine().trim())) ? "Removed." : "Not found."); }
                case 5 -> {
                    System.out.print("Item ID: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("New quantity: "); int q = Integer.parseInt(sc.nextLine().trim());
                    System.out.println(inv.updateQuantity(id, q) ? "Updated." : "Not found.");
                }
                case 6 -> { System.out.print("Item ID: "); Node n = inv.searchById(Integer.parseInt(sc.nextLine().trim())); System.out.println(n == null ? "Not found." : n); }
                case 7 -> { System.out.print("Item name: "); Node n = inv.searchByName(sc.nextLine()); System.out.println(n == null ? "Not found." : n); }
                case 8 -> System.out.printf("Total inventory value: %.2f%n", inv.totalValue());
                case 9 -> {
                    System.out.print("Sort by (1 = name, 2 = price): "); boolean byName = sc.nextLine().trim().equals("1");
                    System.out.print("Order (1 = ascending, 2 = descending): "); boolean asc = sc.nextLine().trim().equals("1");
                    inv.sort(byName, asc);
                    inv.display();
                }
                case 10 -> inv.display();
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
