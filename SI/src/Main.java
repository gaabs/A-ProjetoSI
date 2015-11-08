import java.util.PriorityQueue;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		Scanner scan = new Scanner(System.in);
		
		int a,b;
		while (true){
			a = scan.nextInt();
			b = scan.nextInt();
			
			pq.add(new Pair(a,b));
			
			System.out.println(pq.toString());
		}
		
	}
}
