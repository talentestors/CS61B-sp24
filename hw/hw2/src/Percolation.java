import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF unionUF;
	private final boolean[][] grid;
	private final int N;

	private int numOfOpen;

	public Percolation(int N) {
		unionUF = new WeightedQuickUnionUF(N * N);
		grid = new boolean[N][N];
		this.N = N;
		this.numOfOpen = 0;
	}

	public void open(int row, int col) {
		if (isOpen(row, col)) {
			return;
		}
		grid[row][col] = true;
		++numOfOpen;
		int s = Math.max(0, col - 1);
		int e = Math.min(N, col + 2);
		for (int j = s; j < e; j++) {
			if (isOpen(row, j)) {
				unionUF.union(row * N + col, row * N + j);
			}
		}
		s = Math.max(0, row - 1);
		e = Math.min(N, row + 2);
		for (int i = s; i < e; i++) {
			if (isOpen(i, col)) {
				unionUF.union(row * N + col, i * N + col);
			}
		}
	}

	public boolean isOpen(int row, int col) {
		return grid[row][col];
	}

	public boolean isFull(int row, int col) {
		if (!isOpen(row, col)) {
			return false;
		}
		if (row == 0) {
			return true;
		}
		for (int i = 0; i < N; i++) {
			if (isOpen(0, i) && unionUF.connected(i, row * N + col)) {
				return true;
			}
		}
		return false;
	}

	public int numberOfOpenSites() {
		return numOfOpen;
	}

	public boolean percolates() {
		int bottom = (N - 1) * N;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (isOpen(0, i) && isOpen(N - 1, j) && unionUF.connected(i, bottom + j)) {
					return true;
				}
			}
		}
		return false;
	}

}
