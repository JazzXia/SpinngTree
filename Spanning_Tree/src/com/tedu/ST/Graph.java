package com.tedu.ST;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
	private List<Vertex> vertexs;// 顶点
	private int[][] edges;// 边
	private List<Vertex> topVertexs;// 拓扑序列
	private Queue<Vertex> unVisited;// 没有访问的顶点,用于求最短路径
	private int[][] minTree;// 最小生成树保存在二维数组中

	/**
	 * 初始化
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
	 * 深度优先搜索
	 * 
	 * @param id
	 */
	public void DFS(String vertexName) {
		int id = getIdOfVertexName(vertexName);
		if (id == -1)
			return;
		vertexs.get(id).setMarked(true);
		System.out.println("遍历到" + vertexs.get(id).getName());
		List<Vertex> neighbors = getNeighbors(vertexs.get(id));
		for (int i = 0; i < neighbors.size(); i++) {
			if (!neighbors.get(i).isMarked()) {
				DFS(neighbors.get(i).getName());
			}
		}
	}

	/**
	 * 广度优先搜索
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
			System.out.println("遍历到" + curVertex.getName());
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
	 * 获取最小生成树
	 * 
	 * @return
	 */
	public int[][] getMinTree() {
		initMinTree();// 初始化最小生成树
		while (!allVisited()) {
			Vertex vertex = vertexs.get(getNotMarkedMinVertex());// 设置处理节点
			System.out.println("处理：节点 " + vertex.getName());
			// 顶点已经计算出最短路径，设置为"已访问"
			vertex.setMarked(true);
			// 获取所有"未访问"的邻居
			List<Vertex> neighbors = getNeighbors(vertex);
			System.out.println("邻居个数为：" + neighbors.size());
			// 更新最小生成树
			updateMinEdge(vertex, neighbors);
		}
		System.out.println("search over");
		setMinTree();

		return minTree;
	}

	/**
	 * 设置最小生成树矩阵对称
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
	 * 初始化最小生成树,将所有节点设置值最大
	 */
	public void initMinTree() {
		for (int i = 0; i < this.vertexs.size(); i++) {
			for (int j = 0; j < this.vertexs.size(); j++) {
				this.minTree[i][j] = Integer.MAX_VALUE;
			}

		}
	}

	/**
	 * 更新最小生成树
	 * 
	 * @param vertex
	 * @param neighbors
	 */
	public void updateMinEdge(Vertex vertex, List<Vertex> neighbors) {
		// 参数检测
		if (!isInGraph(vertex)) {
			System.out.println("当前节点不在图中");
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
	 * 搜索各顶点最短路径
	 */
	public void search() {
		while (!unVisited.isEmpty()) {
			Vertex vertex = unVisited.element();
			// 顶点已经计算出最短路径，设置为"已访问"
			vertex.setMarked(true);
			// 获取所有"未访问"的邻居
			List<Vertex> neighbors = getNeighbors(vertex);
			// 更新邻居的最短路径
			updatesDistance(vertex, neighbors);
			pop();
		}
		System.out.println("search over");
	}

	/**
	 * 更新所有邻居的最短路径
	 */
	private void updatesDistance(Vertex vertex, List<Vertex> neighbors) {
		// 参数检测
		if (!isInGraph(vertex)) {
			System.out.println("当前节点不在图中");
			return;
		}
		for (Vertex neighbor : neighbors) {
			updateDistance(vertex, neighbor);
		}
	}

	/**
	 * 更新邻居的最短路径
	 */
	private void updateDistance(Vertex vertex, Vertex neighbor) {
		// 参数检测
		if (!isInGraph(vertex) || !isInGraph(neighbor)) {
			System.out.println("当前节点不在图中");
			return;
		}
		int distance = getDistance(vertex, neighbor) + vertex.getPath();
		if (distance < neighbor.getPath()) {
			neighbor.setPath(distance);
		}
	}

	/**
	 * 初始化未访问顶点集合
	 */
	private void initUnVisited() {
		unVisited = new PriorityQueue<Vertex>();
		for (Vertex v : vertexs) {
			unVisited.add(v);
		}
	}

	/**
	 * 从未访问顶点集合中删除已找到最短路径的节点
	 */
	private void pop() {
		unVisited.poll();
	}

	/**
	 * 获取顶点到目标顶点的距离
	 */
	private int getDistance(Vertex source, Vertex destination) {
		// 参数检测
		if (!isInGraph(source) || !isInGraph(destination)) {
			System.out.println("当前节点不在图中");
			return -1;
		}

		int sourceIndex = vertexs.indexOf(source);
		int destIndex = vertexs.indexOf(destination);
		return edges[sourceIndex][destIndex];
	}

	/**
	 * 获取顶点所有(未访问的)邻居
	 */
	public List<Vertex> getNeighbors(Vertex v) {
		// 参数检测
		if (!isInGraph(v)) {
			System.out.println("当前节点不在图中");
			return null;
		}
		List<Vertex> neighbors = new ArrayList<Vertex>();
		int position = vertexs.indexOf(v);
		Vertex neighbor = null;
		int distance;
		for (int i = 0; i < vertexs.size(); i++) {
			if (i == position) {
				// 顶点本身，跳过
				continue;
			}
			distance = edges[position][i]; // 到所有顶点的距离
			if (distance < Integer.MAX_VALUE) {
				// 是邻居(有路径可达)
				neighbor = getVertex(i);
				if (!neighbor.isMarked()) {
					// 如果邻居没有访问过，则加入list;
					neighbors.add(neighbor);
				}
			}
		}
		return neighbors;
	}

	/**
	 * 根据顶点位置获取顶点
	 */
	private Vertex getVertex(int index) {
		if (index < 0 || index > vertexs.size() + 1) {
			System.out.println("获取ID为" + index + "的顶点失败");
			return null;
		}
		return vertexs.get(index);
	}

	/**
	 * 打印图
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
	 * 判断是否全部访问到
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
	 * 获取到连接着未访问过的节点的最小边，返回链接节点的ID
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
	 * 清除图中的标记信息
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
