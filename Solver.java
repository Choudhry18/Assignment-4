import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {
	
	private SearchNode current;
	private final boolean solvable;
	private final Stack<Board> boards = new Stack<Board>();
	private final int moves;
	private final int len;
	
	public Solver(Board initial) {
		if(initial == null) throw new IllegalArgumentException();
		final MinPQ<SearchNode> queue = new MinPQ<>();
		len = initial.dimension();
		queue.insert(new SearchNode(initial,0,null));
		
		while(queue.min().game.isGoal()==false && !solve(queue.min())) {
			current = queue.delMin();
			current.insertNeighbours(queue);
			
		}
		current = queue.delMin();
		moves = current.moves;
		solvable = current.game.isGoal();
		
	}
	
	private boolean solve(SearchNode current) {
		boolean check;
		if (len ==2)
			check = current.game.manhattan()>1 && current.game.hamming()==1;
		else 
			check = current.game.hamming()==1 && current.game.manhattan()==2;
		return check;
	}
	
	public boolean isSolvable() {
		return solvable;
	}
	
	public int moves() {
		if(isSolvable())
		return moves;
		return -1;
	}
	
	public Iterable<Board> solution(){
		if(!isSolvable()) return null;
		if(solvable) {
			boards.push(current.game);
			while(current.prev != null) {
				current = current.prev;
				boards.push(current.game);
			}
		}
		return boards;
	}

	public static void main(String[] args) {
		
	}
	
	private final class SearchNode implements Comparable<SearchNode>{
		final Board game;
		final int moves,priorityM;
		final SearchNode prev;
		
		public SearchNode(Board a, int b, SearchNode c) {
			game = a;
			moves = b;
			prev = c;
			priorityM = moves + a.manhattan();
		}
		
		public int compareTo(SearchNode a) {
			return this.priorityM - a.priorityM;
		}
		
		public void insertNeighbours(MinPQ<SearchNode> priorityQueue) {
			for(Board neighbour : game.neighbors()) {
				if(prev!=null)
					if(neighbour.equals(prev.game)) continue;
				priorityQueue.insert(new SearchNode(neighbour,this.moves+1, this));			
			}
		}
		
	}

}
