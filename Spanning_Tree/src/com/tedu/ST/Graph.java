package com.tedu.ST;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	private List<Vertex> vertexs;// ����
	private int[][] edges;// ��
	private List<Vertex> topVertexs;// ��������
	private Queue<Vertex> unVisited;// û�з��ʵĶ���,���������·��
	private int[][] minTree;// ��С�����������ڶ�ά������

	/**
	 * ��ʼ��
	 * 
	 * @param vertexs
	 * @param edges
	 */
	public Graph(List<Vertex> vertexs, int[][] edges) {
		this.vertexs = vertexs;
		this.topVertexs = new ArrayList<Vertex>();
		this.edges = edges;
		this.minTree = new int[this.vertexs.size()][this.vertexs.size()];
		initUnVisited();
	}

	/**
	 * �����������
	 * 
	 * @param id
	 */
	public void DFS(String vertexName) {
		int id = getIdOfVertexName(vertexName);
		if (id == -1)
			return;
		vertexs.get(id).setMarked(true);
		System.out.println("������" + vertexs.get(id).getName());
		List<Vertex> neighbors = getNeighbors(vertexs.get(id));
		for (int i = 0; i < neighbors.size(); i++) {
			if (!neighbors.get(i).isMarked()) {
				DFS(neighbors.get(i).getName());
			}
		}
	}

	/**
	 * �����������
	 * 
	 * @param startID
	 */
	public void BFS(String vertexName) {
		int startID = getIdOfVertexName(vertexName);
		if (startID == -1)
			return;
		List<Vertex> q = new ArrayList<Vertex>();
		q.add(vertexs.get(startID));
		vertexs.get(startID).setMarked(true);
		while (!q.isEmpty()) {
			Vertex curVertex = q.get(0);
			q.remove(0);
			System.out.println("������" + curVertex.getName());
			List<Vertex> neighbors = getNeighbors(curVertex);
			for (int i = 0; i < neighbors.size(); i++) {
				if (!neighbors.get(i).isMarked()) {
					neighbors.get(i).setMarked(true);
					q.add(neighbors.get(i));
				}
			}

		}

	}

	/**
	 * ��ȡ��С������
	 * 
	 * @return
	 */
	public int[][] getMinTree() {
		initMinTree();// ��ʼ����С������
		while (!allVisited()) {
			Vertex vertex = vertexs.get(getNotMarkedMinVertex());// ���ô���ڵ�
			System.out.println("�����ڵ� " + vertex.getName());
			// �����Ѿ���������·��������Ϊ"�ѷ���"
			vertex.setMarked(true);
			// ��ȡ����"δ����"���ھ�
			List<Vertex> neighbors = getNeighbors(vertex);
			System.out.println("�ھӸ���Ϊ��" + neighbors.size());
			// ������С������
			updateMinEdge(vertex, neighbors);
		}
		System.out.println("search over");
		setMinTree();

		return minTree;
	}

	/**
	 * ������С����������Գ�
	 */
	public void setMinTree() {
		for (int i = 0; i < vertexs.size(); i++) {
			if (vertexs.get(i).getAnotherIDinminEdge() != -1) {
				minTree[getIdOfVertexName(vertexs.get(i).getName())][vertexs.get(i)
						.getAnotherIDinminEdge()] = edges[getIdOfVertexName(vertexs.get(i).getName())][vertexs.get(i)
								.getAnotherIDinminEdge()];
				minTree[vertexs.get(i).getAnotherIDinminEdge()][getIdOfVertexName(
						vertexs.get(i).getName())] = edges[vertexs.get(i).getAnotherIDinminEdge()][getIdOfVertexName(
								vertexs.get(i).getName())];
			}
		}
	}

	/**
	 * ��ʼ����С������,�����нڵ�����ֵ���
	 */
	public void initMinTree() {
		for (int i = 0; i < this.vertexs.size(); i++) {
			for (int j = 0; j < this.vertexs.size(); j++) {
				this.minTree[i][j] = Integer.MAX_VALUE;
			}

		}
	}

	/**
	 * ������С������
	 * 
	 * @param vertex
	 * @param neighbors
	 */
	public void updateMinEdge(Vertex vertex, List<Vertex> neighbors) {
		// �������
		if (!isInGraph(vertex)) {
			System.out.println("��ǰ�ڵ㲻��ͼ��");
			return;
		}

		for (Vertex neighbor : neighbors) {
			int distance = edges[getIdOfVertexName(neighbor.getName())][getIdOfVertexName(vertex.getName())];
			if (neighbor.getAnotherIDinminEdge() == -1) {
				neighbor.setAnotherIDinminEdge(getIdOfVertexName(vertex.getName()));
				System.out.println(neighbor.getName() + " setEdge To " + vertex.getName()
						+ " "+edges[neighbor.getAnotherIDinminEdge()][getIdOfVertexName(neighbor.getName())]);
			} else if (distance < edges[getIdOfVertexName(neighbor.getName())][neighbor.getAnotherIDinminEdge()]) {
				neighbor.setAnotherIDinminEdge(getIdOfVertexName(vertex.getName()));
				System.out.println(neighbor.getName() + " setEdge To " + vertex.getName()
						+ " "+edges[neighbor.getAnotherIDinminEdge()][getIdOfVertexName(neighbor.getName())]);
			}
		}
	}

	/**
	 * �������������·��
	 */
	public void search() {
		while (!unVisited.isEmpty()) {
			Vertex vertex = unVisited.element();
			// �����Ѿ���������·��������Ϊ"�ѷ���"
			vertex.setMarked(true);
			// ��ȡ����"δ����"���ھ�
			List<Vertex> neighbors = getNeighbors(vertex);
			// �����ھӵ����·��
			updatesDistance(vertex, neighbors);
			pop();
		}
		System.out.println("search over");
	}

	/**
	 * ���������ھӵ����·��
	 */
	private void updatesDistance(Vertex vertex, List<Vertex> neighbors) {
		// �������
		if (!isInGraph(vertex)) {
			System.out.println("��ǰ�ڵ㲻��ͼ��");
			return;
		}
		for (Vertex neighbor : neighbors) {
			updateDistance(vertex, neighbor);
		}
	}

	/**
	 * �����ھӵ����·��
	 */
	private void updateDistance(Vertex vertex, Vertex neighbor) {
		// �������
		if (!isInGraph(vertex) || !isInGraph(neighbor)) {
			System.out.println("��ǰ�ڵ㲻��ͼ��");
			return;
		}
		int distance = getDistance(vertex, neighbor) + vertex.getPath();
		if (distance < neighbor.getPath()) {
			neighbor.setPath(distance);
		}
	}

	/**
	 * ��ʼ��δ���ʶ��㼯��
	 */
	private void initUnVisited() {
		unVisited = new PriorityQueue<Vertex>();
		for (Vertex v : vertexs) {
			unVisited.add(v);
		}
	}

	/**
	 * ��δ���ʶ��㼯����ɾ�����ҵ����·���Ľڵ�
	 */
	private void pop() {
		unVisited.poll();
	}

	/**
	 * ��ȡ���㵽Ŀ�궥��ľ���
	 */
	private int getDistance(Vertex source, Vertex destination) {
		// �������
		if (!isInGraph(source) || !isInGraph(destination)) {
			System.out.println("��ǰ�ڵ㲻��ͼ��");
			return -1;
		}

		int sourceIndex = vertexs.indexOf(source);
		int destIndex = vertexs.indexOf(destination);
		return edges[sourceIndex][destIndex];
	}

	/**
	 * ��ȡ��������(δ���ʵ�)�ھ�
	 */
	public List<Vertex> getNeighbors(Vertex v) {
		// �������
		if (!isInGraph(v)) {
			System.out.println("��ǰ�ڵ㲻��ͼ��");
			return null;
		}
		List<Vertex> neighbors = new ArrayList<Vertex>();
		int position = vertexs.indexOf(v);
		Vertex neighbor = null;
		int distance;
		for (int i = 0; i < vertexs.size(); i++) {
			if (i == position) {
				// ���㱾������
				continue;
			}
			distance = edges[position][i]; // �����ж���ľ���
			if (distance < Integer.MAX_VALUE) {
				// ���ھ�(��·���ɴ�)
				neighbor = getVertex(i);
				if (!neighbor.isMarked()) {
					// ����ھ�û�з��ʹ��������list;
					neighbors.add(neighbor);
				}
			}
		}
		return neighbors;
	}

	/**
	 * ���ݶ���λ�û�ȡ����
	 */
	private Vertex getVertex(int index) {
		if (index < 0 || index > vertexs.size() + 1) {
			System.out.println("��ȡIDΪ" + index + "�Ķ���ʧ��");
			return null;
		}
		return vertexs.get(index);
	}

	/**
	 * ��ӡͼ
	 */
	public void printGraph() {
		int verNums = vertexs.size();
		for (int row = 0; row < verNums; row++) {
			for (int col = 0; col < verNums; col++) {
				if (Integer.MAX_VALUE == edges[row][col]) {
					System.out.print("X");
					System.out.print(" ");
					continue;
				}
				System.out.print(edges[row][col]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * �ж��Ƿ�ȫ�����ʵ�
	 * 
	 * @return
	 */
	public boolean allVisited() {
		boolean flag = true;
		for (int i = 0; i < vertexs.size(); i++) {
			if (!vertexs.get(i).isMarked())
				flag = false;
		}
		return flag;
	}



	public int getIdOfVertexName(String name) {
		int id = -1;
		for (int i = 0; i < vertexs.size(); i++) {
			if (vertexs.get(i).getName().equals(name))
				id = i;
		}
		return id;
	}

	/**
	 * ��ȡ��������δ���ʹ��Ľڵ����С�ߣ��������ӽڵ��ID
	 * 
	 * @return
	 */
	public int getNotMarkedMinVertex() {
		int min = 10000;
		int id = 0;
		for (int i = 0; i < vertexs.size(); i++) {
			if (!vertexs.get(i).isMarked() && vertexs.get(i).getAnotherIDinminEdge() != -1) {
				if (min > edges[getIdOfVertexName(vertexs.get(i).getName())][vertexs.get(i).getAnotherIDinminEdge()]) {
					min = edges[getIdOfVertexName(vertexs.get(i).getName())][vertexs.get(i).getAnotherIDinminEdge()];
					id = i;

				}
			}
		}
		return id;
	}

	/**
	 * ���ͼ�еı����Ϣ
	 */
	public void clearGraph() {
		for (Vertex vertex : vertexs) {
			vertex.setMarked(false);
			vertex.setAnotherIDinminEdge(-1);
		}

	}

	private boolean isInGraph(Vertex v) {
		for (Vertex vertex : vertexs) {
			if (vertex.getName().equals(v.getName()))
				return true;
		}
		return false;
	}

}
