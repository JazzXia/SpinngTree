package com.tedu.ST;

import java.util.ArrayList;
import java.util.List;
public class Test {
	public static void main(String[] args) {
		
		List<Vertex> vertexs = new ArrayList<Vertex>();
		Vertex a = new Vertex("A");// 0����һ���ڵ�����·������Ϊ0
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D",0);
		Vertex e = new Vertex("E");
		Vertex f = new Vertex("F");
		Vertex g = new Vertex("G");
		//��ӵ�����
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
		// ����
				System.out.println("------------------------------------");
				System.out.println("��ǰͼ״̬��");
				graph.printGraph();
				System.out.println("------------------------------------");
				System.out.println("������ȱ�����");
				System.out.println("��ʼ�ڵ㣺D");
				graph.DFS("D");
				graph.clearGraph();
				System.out.println("------------------------------------");
				System.out.println("������ȱ�����");
				System.out.println("��ʼ�ڵ㣺D");
				graph.BFS("D");
				graph.clearGraph();

				// ��С������
				System.out.println("------------------------------------");
				System.out.println("��С��������");
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
				
				// ���·��
				System.out.println("------------------------------------");
				System.out.println("���·��");
				graph.search();
				System.out.println("�ڵ�" + d.getName() + "�����ʼ�ڵ����С����Ϊ��" + c.getPath());
	}
}
