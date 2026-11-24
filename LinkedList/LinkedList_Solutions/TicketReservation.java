import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/** Problem 9: Online Ticket Reservation System using a Circular Linked List. */
public class TicketReservation {
    static class Node {
        int ticketId; String customer, movie; int seat; String bookingTime; Node next;
        Node(int ticketId, String customer, String movie, int seat, String bookingTime) {
            this.ticketId = ticketId; this.customer = customer; this.movie = movie;
            this.seat = seat; this.bookingTime = bookingTime;
        }
        public String toString() {
            return "Ticket: " + ticketId + " | " + customer + " | " + movie + " | Seat: " + seat + " | Booked: " + bookingTime;
        }
    }

    private Node head, tail;
    private int count;

    void addTicket(int id, String customer, String movie, int seat) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Node n = new Node(id, customer, movie, seat, time);
        if (head == null) { head = tail = n; n.next = n; }
        else { tail.next = n; tail = n; tail.next = head; }
        count++;
    }

    boolean remove(int id) {
        if (head == null) return false;
        Node prev = tail, c = head;
        do {
            if (c.ticketId == id) {
                if (count == 1) head = tail = null;
                else {
                    prev.next = c.next;
                    if (c == head) head = c.next;
                    if (c == tail) tail = prev;
                }
                count--;
                return true;
            }
            prev = c; c = c.next;
        } while (c != head);
        return false;
    }

    void display() {
        if (head == null) { System.out.println("No tickets booked."); return; }
        Node c = head;
        do { System.out.println(c); c = c.next; } while (c != head);
    }

    void searchByCustomer(String name) { search(name, true); }
    void searchByMovie(String movie)   { search(movie, false); }

    private void search(String key, boolean byCustomer) {
        if (head == null) { System.out.println("No tickets booked."); return; }
        boolean found = false;
        Node c = head;
        do {
            String field = byCustomer ? c.customer : c.movie;
            if (field.equalsIgnoreCase(key)) { System.out.println(c); found = true; }
            c = c.next;
        } while (c != head);
        if (!found) System.out.println("No matching tickets.");
    }

    int totalTickets() { return count; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketReservation sys = new TicketReservation();
        while (true) {
            System.out.println("\n--- Ticket Reservation ---");
            System.out.println("1. Book ticket\n2. Remove ticket by ID\n3. Display tickets");
            System.out.println("4. Search by customer\n5. Search by movie\n6. Total booked tickets\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1 -> {
                    System.out.print("Ticket ID: ");  int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Customer: ");   String c = sc.nextLine();
                    System.out.print("Movie: ");      String m = sc.nextLine();
                    System.out.print("Seat number: "); int s = Integer.parseInt(sc.nextLine().trim());
                    sys.addTicket(id, c, m, s);
                }
                case 2 -> { System.out.print("Ticket ID: "); System.out.println(sys.remove(Integer.parseInt(sc.nextLine().trim())) ? "Removed." : "Not found."); }
                case 3 -> sys.display();
                case 4 -> { System.out.print("Customer: "); sys.searchByCustomer(sc.nextLine()); }
                case 5 -> { System.out.print("Movie: "); sys.searchByMovie(sc.nextLine()); }
                case 6 -> System.out.println("Total tickets: " + sys.totalTickets());
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
