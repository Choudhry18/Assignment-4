import java.util.Arrays;
import java.util.LinkedList;
import java.lang.StringBuilder;

public class Board {

	private final int[][] current;
	private final int  len; 
	
	public Board(int[][] tiles) {
		len = tiles.length;
		this.current = copy(tiles);
	}
	

	
	public String  toString(){
		StringBuilder a = new StringBuilder();
		a.append(len);
		for(int i=0; i<len; i++) {
			a.append("\n");
			for(int j=0; j<len; j++) {
				int box = current[i][j];
				a.append(box + " ");
				if(box == 0) continue;
				if(box == (len*i)+j+1) continue;
			}
		}	
		return a.toString();
	}
	
	public int dimension() {
		return len;
	}
	
	public int hamming() {
		int hamming=0;
		for(int i=0;i<len;i++) {
			for(int j=0;j<len;j++) {
				int box = current[i][j];
				if(box == 0) continue;
				if(box == (len*i)+j+1) continue;
				hamming++;
			}
		}
		return hamming; 
	}
	
	public int manhattan() {
		int manhattan = 0;
		for(int i=0; i<len; i++) 
			for(int j=0; j<len; j++) {
				int box = current[i][j];
				if(box == 0) continue;
				if(box == (len*i)+j+1) continue;
				manhattan += Math.abs(((box-1)/len)-i);
				manhattan += Math.abs(((box-1)%len)-j);
			}
		return manhattan;
	}
	
	public boolean isGoal() {
		return hamming()==0;
	}
	
	public boolean equals(Object y) {
		if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.current, that.current);
	}
	
	public Iterable<Board> neighbors(){
		LinkedList<Board> boards = new LinkedList<>();
		for(int i=0; i<len; i++) 
			for(int j=0; j<len; j++) {
				if(current[i][j] != 0) continue;
				if(i-1>=0) {					
				    swapi(i-1,j,current);
				    boards.add(new Board(current));
				    swapi(i-1,j,current);
					}
				if(i+1<len) {
					swapi(i,j,current);
					boards.add(new Board(current));
					swapi(i,j,current);
					} 
				if(j-1>=0) {
					swapj(i,j-1,current);
					boards.add(new Board(current));
					swapj(i,j-1,current);
					}
				if(j+1<len) {
					swapj(i,j,current);
					boards.add(new Board(current));
					swapj(i,j,current);
					}
				}

		return boards;
	}
	public Board twin() {
		int[][] twin = copy(current);
		if(twin[0][0] != 0 && twin[1][0] !=0) swapi(0,0, twin);
		else swapi(0,1,twin);
		return new Board(twin);
	}
	
	private void swapi(int i, int j, int[][] array) {
		int num = array[i][j];
		array[i][j] = array[i+1][j];
		array[i+1][j] = num;
	}
	
	private void swapj(int i, int j, int[][] array) {
		int num = array[i][j];
		array[i][j] = array[i][j+1];
		array[i][j+1] = num;
	}
	
	private int[][] copy(int[][] array){
		int len = array.length;
		int[][] ans = new int[len][len];
		for(int i=0;i<len;i++) 
			for(int j=0;j<len;j++) 
				ans[i][j] = array[i][j];
		return ans;
	}

	public static void main(String[] args) {

	}
}
