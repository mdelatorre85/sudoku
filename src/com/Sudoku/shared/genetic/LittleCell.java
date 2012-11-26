package com.Sudoku.shared.genetic;

import java.io.Serializable;

public class LittleCell implements Comparable<LittleCell>, Serializable {

	private static final long serialVersionUID = -3557874517837645254L;
	private int x, y;

	@SuppressWarnings("unused")
	private LittleCell() {
	}

	public LittleCell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int compareTo(LittleCell o) {
		return getIndex() - o.getIndex();
	}

	private int getIndex() {
		return x * 10 + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LittleCell) {
			return getIndex() == ((LittleCell) obj).getIndex();
		}
		return false;
	}

	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		sb.append(",");
		sb.append(y);
		return sb.toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		sb.append(x);
		sb.append(",");
		sb.append(y);
		sb.append(")");
		return sb.toString();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}