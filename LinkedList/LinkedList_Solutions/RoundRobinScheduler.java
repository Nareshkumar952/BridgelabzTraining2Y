import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Problem 6: Round Robin CPU Scheduling using a Circular Linked List.
 * All processes are assumed to arrive at time 0, so
 * turn-around time = completion time and waiting time = turn-around time - burst time.
 */
public class RoundRobinScheduler {
    static class Node {
        int pid, burst, remaining, priority; Node next;
        Node(int pid, int burst, int priority) {
            this.pid = pid; this.burst = burst; this.remaining = burst; this.priority = priority;
        }
    }

    private Node head, tail;
    private int size;

    void addProcess(int pid, int burst, int priority) {
        Node n = new Node(pid, burst, priority);
        if (head == null) { head = tail = n; n.next = n; }
        else { tail.next = n; tail = n; tail.next = head; }
        size++;
    }

    boolean remove(int pid) {
        if (head == null) return false;
        Node prev = tail, c = head;
        do {
            if (c.pid == pid) {
                if (size == 1) head = tail = null;
                else {
                    prev.next = c.next;
                    if (c == head) head = c.next;
                    if (c == tail) tail = prev;
                }
                size--;
                return true;
            }
            prev = c; c = c.next;
        } while (c != head);
        return false;
    }

    void displayQueue() {
        if (head == null) { System.out.println("  Queue: (empty)"); return; }
        StringBuilder sb = new StringBuilder("  Queue: ");
        Node c = head;
        do {
            sb.append("[P").append(c.pid).append(" rem=").append(c.remaining).append("] ");
            c = c.next;
        } while (c != head);
        System.out.println(sb);
    }

    void simulate(int quantum) {
        if (head == null) { System.out.println("No processes."); return; }
        List<int[]> finished = new ArrayList<>(); // {pid, burst, completionTime}
        int time = 0, round = 0;
        Node cur = head;
        while (size > 0) {
            round++;
            System.out.println("Round " + round + ":");
            int n = size;                    // processes present at start of this round
            for (int i = 0; i < n; i++) {
                Node next = cur.next;        // save before a possible removal
                int run = Math.min(quantum, cur.remaining);
                time += run;
                cur.remaining -= run;
                System.out.println("  P" + cur.pid + " ran " + run + " units (time = " + time + ")");
                if (cur.remaining == 0) {
                    finished.add(new int[]{cur.pid, cur.burst, time});
                    remove(cur.pid);
                }
                cur = next;
            }
            displayQueue();
        }
        double totalWT = 0, totalTAT = 0;
        System.out.println("\nPID | Burst | Waiting | Turn-around");
        for (int[] f : finished) {
            int tat = f[2], wt = tat - f[1];
            totalWT += wt; totalTAT += tat;
            System.out.printf("P%-2d | %5d | %7d | %11d%n", f[0], f[1], wt, tat);
        }
        System.out.printf("Average waiting time: %.2f%nAverage turn-around time: %.2f%n",
                totalWT / finished.size(), totalTAT / finished.size());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoundRobinScheduler rr = new RoundRobinScheduler();
        while (true) {
            System.out.println("\n--- Round Robin Scheduler ---");
            System.out.println("1. Add process\n2. Remove process\n3. Display queue\n4. Simulate\n0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());
            if (ch == 0) break;
            switch (ch) {
                case 1 -> {
                    System.out.print("Process ID: ");  int pid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Burst time: ");  int b = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Priority: ");    int p = Integer.parseInt(sc.nextLine().trim());
                    rr.addProcess(pid, b, p);
                }
                case 2 -> { System.out.print("Process ID: "); System.out.println(rr.remove(Integer.parseInt(sc.nextLine().trim())) ? "Removed." : "Not found."); }
                case 3 -> rr.displayQueue();
                case 4 -> { System.out.print("Time quantum: "); rr.simulate(Integer.parseInt(sc.nextLine().trim())); }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
