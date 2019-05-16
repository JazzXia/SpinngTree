package com.tedu.ST;

public class Vertex implements Comparable<Vertex> {
	private String name;// 节点名称(A,B,C,D)
	private int minPath;// 最短路径长度
	private int anotherIDinminEdge;// 最小生成树中节点链接的另一个节点的ID,两个节点之间保存着最小生成树中的边
	private boolean isMarked;// 节点是否已经出列(是否已经处理完毕)最短路径用到

	// 构造器
	public Vertex(String name) {
		this.name = name;
		this.minPath = Integer.MAX_VALUE; // 初始设置最短路径长度为无穷大
		this.anotherIDinminEdge = -1;
		this.setMarked(false);
	}

	public Vertex(String name, int path) {
		this.name = name;
		this.minPath = path;
		this.setMarked(false);
	}

	public int getAnotherIDinminEdge() {
		return anotherIDinminEdge;
	}

	public void setAnotherIDinminEdge(int anotherIDinminEdge) {
		this.anotherIDinminEdge = anotherIDinminEdge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPath() {
		return minPath;
	}

	public void setPath(int path) {
		this.minPath = path;
	}

	boolean isMarked() {
		return this.isMarked;

	}

	// 开关
	void setMarked(boolean flag) {
		this.isMarked = flag;
	}

	@Override
	public int compareTo(Vertex o) {
		// 三元表达式
		return o.minPath > minPath ? -1 : 1;
	}

}
