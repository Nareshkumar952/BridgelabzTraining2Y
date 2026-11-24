import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Problem 7: Social Media Friend Connections using a Singly Linked List of users. */
public class SocialMediaFriends {
    static class User {
        int id; String name; int age;
        List<Integer> friends = new ArrayList<>();   // list of friend IDs
        User next;
        User(int id, String name, int age) { this.id = id; this.name = name; this.age = age; }
        public String toString() { return "ID: " + id + " | " + name + " | Age: " + age; }
    }

    private User head;

    void addUser(int id, String name, int age) {
        if (findById(id) != null) { System.out.println("User ID already exists."); return; }
        User u = new User(id, name, age);
        if (head == null) { head = u; return; }
        User t = head;
        while (t.next != null) t = t.next;
        t.next = u;
    }

    User findById(int id) {
        for (User u = head; u != null; u = u.next) if (u.id == id) return u;
        return null;
    }

    User findByName(String name) {
        for (User u = head; u != null; u = u.next) if (u.name.equalsIgnoreCase(name)) return u;
        return null;
    }

    boolean addFriend(int a, int b) {
        User ua = findById(a), ub = findById(b);
        if (ua == null || ub == null || a == b || ua.friends.contains(b)) return false;
        ua.friends.add(b);
        ub.friends.add(a);
        return true;
    }

    boolean removeFriend(int a, int b) {
        User ua = findById(a), ub = findById(b);
        if (ua == null || ub == null || !ua.friends.contains(b)) return false;
        ua.friends.remove(Integer.valueOf(b));
        ub.friends.remove(Integer.valueOf(a));
        return true;
    }

    void mutualFriends(int a, int b) {
        User ua = findById(a), ub = findById(b);
        if (ua == null || ub == null) { System.out.println("User not found."); return; }
        boolean found = false;
        for (int f : ua.friends) {
            if (ub.friends.contains(f)) {
                User m = findById(f);
                System.out.println(m != null ? m : "ID: " + f);
                found = true;
            }
        }
        if (!found) System.out.println("No mutual friends.");
    }

    void displayFriends(int id) {
        User u = findById(id);
        if (u == null) { System.out.println("User not found."); return; }
        if (u.friends.isEmpty()) { System.out.println(u.name + " has no friends yet."); return; }
        System.out.println("Friends of " + u.name + ":");
        for (int f : u.friends) {
            User fr = findById(f);
            System.out.println("  " + (fr != null ? fr : "ID: " + f));
        }
    }

    void countFriends() {
        if (head == null) { System.out.println("No users."); return; }
        for (User u = head; u != null; u = u.next)
            System.out.println(u.name + " (ID " + u.id + "): " + u.friends.size() + " friend(s)");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SocialMediaFriends net = new SocialMediaFriends();
        while (true) {
            System.out.println("\n--- Social Media Friends ---");
            System.out.println("1. Add user\n2. Add friend connection\n3. Remove friend connection");
            System.out.println("4. Mutual friends\n5. Display friends of a user\n6. Search user by ID");
            System.out.println("7. Search user by name\n8. Count friends of each user\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1 -> {
                    System.out.print("User ID: "); int id = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Name: ");    String name = sc.nextLine();
                    System.out.print("Age: ");     int age = Integer.parseInt(sc.nextLine().trim());
                    net.addUser(id, name, age);
                }
                case 2, 3, 4 -> {
                    System.out.print("First user ID: ");  int a = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Second user ID: "); int b = Integer.parseInt(sc.nextLine().trim());
                    if (ch == 2) System.out.println(net.addFriend(a, b) ? "Connected." : "Could not connect (invalid IDs or already friends).");
                    else if (ch == 3) System.out.println(net.removeFriend(a, b) ? "Removed." : "No such connection.");
                    else net.mutualFriends(a, b);
                }
                case 5 -> { System.out.print("User ID: "); net.displayFriends(Integer.parseInt(sc.nextLine().trim())); }
                case 6 -> { System.out.print("User ID: "); User u = net.findById(Integer.parseInt(sc.nextLine().trim())); System.out.println(u == null ? "Not found." : u); }
                case 7 -> { System.out.print("Name: "); User u = net.findByName(sc.nextLine()); System.out.println(u == null ? "Not found." : u); }
                case 8 -> net.countFriends();
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
