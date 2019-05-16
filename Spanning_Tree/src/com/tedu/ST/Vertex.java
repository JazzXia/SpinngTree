package com.tedu.ST;

public class Vertex implements Comparable<Vertex> {
	private String name;// �ڵ�����(A,B,C,D)
	private int minPath;// ���·������
	private int anotherIDinminEdge;// ��С�������нڵ����ӵ���һ���ڵ��ID,�����ڵ�֮�䱣������С�������еı�
	private boolean isMarked;// �ڵ��Ƿ��Ѿ�����(�Ƿ��Ѿ��������)���·���õ�

	// ������
	public Vertex(String name) {
		this.name = name;
		this.minPath = Integer.MAX_VALUE; // ��ʼ�������·������Ϊ�����
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

	// ����
	void setMarked(boolean flag) {
		this.isMarked = flag;
	}

	@Override
	public int compareTo(Vertex o) {
		// ��Ԫ���ʽ
		return o.minPath > minPath ? -1 : 1;
	}

}
