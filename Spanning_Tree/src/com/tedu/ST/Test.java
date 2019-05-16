package com.tedu.ST;

import java.util.ArrayList;
import java.util.List;
public class Test {
	public static void main(String[] args) {
		
		List<Vertex> vertexs = new ArrayList<Vertex>();
		Vertex a = new Vertex("A");// 0到第一个节点的最短路径设置为0
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D",0);
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		Vertex g = new Vertex("G");
		//添加到集合
		vertexs.add(a);
		vertexs.add(b);
		vertexs.add(c);
		vertexs.add(d);
		vertexs.add(e);
		vertexs.add(f);
		vertexs.add(g);
		int[][] edges = 
			{   { Integer.MAX_VALUE, 7, Integer.MAX_VALUE, 5, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE },
				{ 7, Integer.MAX_VALUE, 8, 9, 7, Integer.MAX_VALUE, Integer.MAX_VALUE },
				{ Integer.MAX_VALUE, 8, Integer.MAX_VALUE, Integer.MAX_VALUE, 5, Integer.MAX_VALUE, Integer.MAX_VALUE },
				{ 5, 9, Integer.MAX_VALUE, Integer.MAX_VALUE, 15, 6, Integer.MAX_VALUE },
				{ Integer.MAX_VALUE, 7, 5, 15, Integer.MAX_VALUE, 8, 9 },
				{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 6, 8, Integer.MAX_VALUE, 11 },
				{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 9, 11, Integer.MAX_VALUE }
			};
		
		Graph graph =new Graph(vertexs, edges);
		// 遍历
				System.out.println("------------------------------------");
				System.out.println("当前图状态：");
				graph.printGraph();
				System.out.println("------------------------------------");
				System.out.println("深度优先遍历：");
				System.out.println("开始节点：D");
				graph.DFS("D");
				graph.clearGraph();
				System.out.println("------------------------------------");
				System.out.println("广度优先遍历：");
				System.out.println("开始节点：D");
				graph.BFS("D");
				graph.clearGraph();

				// 最小生成树
				System.out.println("------------------------------------");
				System.out.println("最小生成树：");
				int[][] minTree = graph.getMinTree();
				for (int i = 0; i < vertexs.size(); i++) {
					for (int j = 0; j < vertexs.size(); j++) {
						if (minTree[i][j] == Integer.MAX_VALUE)
							minTree[i][j] = 0;
						System.out.print(minTree[i][j] + " ");
					}
					System.out.println();
				}
				graph.clearGraph();
				
				// 最短路径
				System.out.println("------------------------------------");
				System.out.println("最短路径");
				graph.search();
				System.out.println("节点" + d.getName() + "距离初始节点的最小距离为：" + c.getPath());
	}
}
