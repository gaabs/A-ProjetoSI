
public class Pair implements Comparable<Pair>{
	int function;
	int real;
	
	public Pair(int function, int real){
		this.function = function;
		this.real = real;
	}
	
	@Override
	public int compareTo(Pair p) {
		if (function > p.function) return 1;
		if (function < p.function) return -1;
		
		//if (real > p.real) return 1;
		//if (real < p.real) return -1;
		
		return 0;
	}
	
	public String toString(){
		String str = String.format("(%d,%d)", function,real);
		
		
		return str;
	}
}
