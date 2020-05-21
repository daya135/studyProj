package org.jzz.study.DataStructure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jzz.study.util.Print;

/** 模拟机器人场外地图(无向图)
 *  支持节点新增删除
 *	提供三种最短路径算法：dfs、djstl、floyd
 *	数组中有空节点，有大量过滤操作；
*/
public class Graph {
	public static final int MAX = 100;	//顶点容量
	private static final double UNREACH = Double.MAX_VALUE / MAX; //不可达, 除以MAX防止加和溢出
	private static final double NULLP = -2D;	//空顶点
	private static final boolean VISITED = true; //已访问
	private static final boolean UNVISIT = false; //未访问
	
	
	private Object[] V = new Object[MAX];	//顶点表
	private double[][] G = new double[MAX][MAX];	//边，邻接矩阵
	
	private boolean[] book = new boolean[MAX];	//标记
	private List<Integer> path = new LinkedList<Integer>();	//路径点
	private double minWeight = Double.MAX_VALUE;	//最小路线长度
	private double[][] floyDist = new double[MAX][MAX]; //floyd最短路径表(用来判断是否可达)
	private int[][] floydPath = new int[MAX][MAX]; //floyd算法前驱表
	private boolean isFloydOver = false; //记录floyd前驱表是否生成
	private long count = 0; //记录计算步骤
	
	public Graph() {
		for(int i = 0; i < MAX; i++) {
			Arrays.fill(G[i], NULLP);
		}
		Arrays.fill(V, null);
	}
	
	//增加一个节点, 返回下标值
	public int addNode(Object obj) {
		int i = MAX - 1;
		//从后往前扫天然空闲下标
		while(i >= 0 && V[i] == null ) {
			--i;
		}
		if (i + 1 == MAX) {	
			//无天然空余下标， 从前往后扫，查找有无被删除下标
			i = 0;
			while (V[i] != null) {
				++i;
			}
			if (i < MAX) {
				addNode(obj, i);
				return i;
			}
			else 
				throw new RuntimeException("顶点数达到最大，无法添加");
		} else {
			//有天然空余下标
			i++; 
			addNode(obj, i);
			return i;
		}
	}
	//添加节点到指定下标，初始化路径关系
	public void addNode(Object obj, int idx) {
		for (int i = 0; i < MAX; i++) {
			if (V[i] != null) {
				G[idx][i] = UNREACH;
				G[i][idx] = UNREACH;
			}
		}
		G[idx][idx] = 0D;
		V[idx] = obj;
		isFloydOver = false;
	}
	//添加节点间联系
	public void connect(int a, int b, Double weight) {
		if (a >= MAX || b >= MAX || V[a] == null || V[b] == null) {
			throw new RuntimeException("顶点不存在");
		}
		if (a == b) return;	//自身不能添加权值
		if (weight == 0) {
			//添加权值为0的边（即将两点设置为重复）则合并二者与其它边的关系
			/*考虑以下情况，做了两遍循环赋值：
			   1、a, b任意一方均可能为新增重复点（不约定传值顺序）；
			   2、a, b都有各自的边，将二者设置为重合（实际应用不大可能发生合并）
			*/
			for (int i = 0; i < MAX; i++) {
				if (V[i] != null) {
					G[b][i] = G[i][b] = G[a][i];
				}
			}
			for (int i = 0; i < MAX; i++) {
				if (V[i] != null) {
					G[a][i] = G[i][a] = G[b][i];
				}
			}
		}
		G[a][b] = G[b][a] = weight;
		isFloydOver = false;
	}
	//删除节点
	public void delete(int idx) {
		if (idx < 0 || idx >= MAX) {
			throw new RuntimeException("顶点不存在");
		}
		V[idx] = null;
		for (int i = 0; i < MAX; i++) {
			G[idx][i] = NULLP;
			G[i][idx] = NULLP;
		}
		isFloydOver = false;
	}
	/** 查找两点之间路线序列，跟实际的图有区别的是，如果两点之间有直连路线，则即使有其它更短路线也会返回此直连路线 */
	//ps：现实中两点之间直线最短，不会出现这样的情况，但在数据结构中可能出现，如: a-b=1 b-c=1 a-c=3 则a-c最短为：a-b-c）
	public Object[] findWay(int a, int b) {
		if (a >= MAX || b >= MAX || V[a] == null || V[b] == null || G[a][b] == NULLP) {
			throw new RuntimeException("顶点不存在");
		}
		List<Integer> route = null;
		if (G[a][b] < UNREACH) {	//两点直接可达
			route = new ArrayList<Integer>();
			route.add(b);
		} else {
//			route = findByDfs(a, b);
//			route = findByDjstl(a, b); 
			route = findByFloyd(a, b);
		}
		Print.printnb(String.format("(%2d->%2d) ", a, b));
		Print.print(route);
		return route == null ? null : (Object[])route.toArray();
	}
	@SuppressWarnings("unused")
	private List<Integer> findByDfs(int a, int b) {
		LinkedList<Integer> tempPath = new LinkedList<Integer>();
		path.clear();
		minWeight = Double.MAX_VALUE;
		Arrays.fill(book, UNVISIT);
		count = 0;
		book[a] = VISITED; //先标记起点
		dfs(a, b, 0, tempPath);
		/* 处理起点和终点间的重复点 */
		if (path.size() >= 2) { 
			LinkedList<Integer> temp = new LinkedList<Integer>();
			Integer p = path.get(path.size() - 1);
			temp.addFirst(p);
			for (int i = path.size() - 2; i >= 0; i--) {
				if (!isOverlap(p, path.get(i))) {	
					temp.addFirst(path.get(i));
				}
				p = path.get(i);
			}
			path = temp;
		}
		Print.print(count);
		return path;
	}
	
	/** 深搜,时间复杂度为:N的阶乘，实测当连通集超过28时，计算步骤达到千万级，30则破亿， 效率低下*/
	private void dfs(int start, int end, double tempWeight, LinkedList<Integer> tempPath) {
		if(V[start] == null || V[end] == null) 
			return;	//跳过空节点
		if (start == end) {
			if (tempWeight < minWeight) {
				minWeight = tempWeight;
				path.clear();
				path.addAll(tempPath);
			}
			return;
		}
		for (int i = 0; i < MAX; i++) {
			count++;
			if (V[i] == null) continue;
			if (start != i && G[start][i] < UNREACH && !book[i]) {	//start != i要单独判断，因为允许权值为0的边
				book[i] = VISITED;
				tempPath.addLast(Integer.valueOf(i));
				dfs(i, end, tempWeight + G[start][i], tempPath);
				book[i] = UNVISIT;
				if (i + 1 < MAX) 
					tempPath.removeLast();
			}
		}
	}
	@SuppressWarnings("unused")
	private List<Integer> findByDjstl(int a, int b) {
		LinkedList<Integer> tempPath = new LinkedList<Integer>(); //临时路径点
		double[] dist = new double[MAX]; //单源最短路径，djstl
		int[] prev = new int[MAX]; //记录前驱节点
		count = 0;
		djstl(a, prev, dist);
		path.clear();
		int u = b;
		while (prev[u] != -1) {
			u = prev[u];
			tempPath.addFirst(u);
		}
		if (tempPath.size() > 0)	//到目标点的前驱序列存在，则将目标点添加到最后
			tempPath.addLast(b);
		path = tempPath;
		//（这个算法不会产生重复点）
		Print.print(count);
		return path;
	}
	
	/** 迪杰斯特拉算法，Olog(n^2) */
	private void djstl(int s, int[] prev, double[] dist) {
		//初始化
		for (int i = 0; i < MAX; i++) {
			if (V[i] == null) continue;
			prev[i] = -1;	//前驱节点赋值为-1
			book[i] = UNVISIT;
			if (G[s][i] >= UNREACH) {
				dist[i] = UNREACH;
			} else {
				dist[i] = G[s][i];
			} 
		}
		//顶点自身初始化
		book[s] = VISITED;	//标记起点
		dist[s] = 0;
		
		int k = s;	//当前节点
		for(int i = 1; i < MAX; i++) {//遍历MAX-1次；每次找出一个顶点的最短路径
			minWeight = UNREACH;	
			for (int j = 0; j < MAX; j++) {
				if (V[j] == null) continue;
				if (!book[j] && dist[j] < minWeight) {
					minWeight = dist[j];
					k = j;
				}
			}
			book[k] = VISITED;
			for (int j = 0; j < MAX; j++) {
				if (V[j] == null) continue;
				if (!book[j] && dist[j] > dist[k] + G[k][j]) {
					dist[j] = dist[k] + G[k][j];
					prev[j] = k; //记录前驱节点
				}
			}
			count += 2 * MAX;
		}
	}
	/** Floyd算法， 如果已经建立了路径表，则可直接得到任意点之间最短路径，多次查找时效率最高 */
	public List<Integer> findByFloyd(int a, int b) {
		LinkedList<Integer> tempPath = new LinkedList<Integer>(); //临时路径点
		count = 0;
		if (!isFloydOver) {
			floyd();
			isFloydOver = true;
		}
		if (floyDist[a][b] >= UNREACH) return null;	//不存在可达路径
		int u = floydPath[a][b];	//中转节点
		while (u!=b) {
			tempPath.addLast(u);
			u = floydPath[u][b];
		}
		tempPath.addLast(b);
		return tempPath;
	}
	
	/** Floyd算法, 执行一次则记录所有点之间的最短路径关系 */
	private void floyd() {
		for (int i = 0; i < MAX; i++) {
			if (V[i] == null) continue;
			for(int j = 0; j < MAX; j++) {
				if (V[j] == null) continue;
				floyDist[i][j] = G[i][j];
				floydPath[i][j] = j;
			}
		}
		for (int k = 0; k < MAX; k++) {
			if (V[k] == null) continue;
			for (int i = 0; i < MAX; i++) {
				if (V[i] == null) continue;
				for (int j = 0; j < MAX; j++) {
					if(V[j] == null) continue;
					if(floyDist[i][j] > floyDist[i][k] + floyDist[k][j] && i != j) {
						floyDist[i][j] = floyDist[i][k] + floyDist[k][j];
						floydPath[i][j] = floydPath[i][k];
					}
				}
			}
		}
	}
	
	/** 判断两个点是否在同一个位置（如“权值绝对值小于0.1；权值等于0”） */
	private boolean isOverlap(int a, int b) {
		if (a != b && G[a][b] == 0) {
			return true;
		}
		return false;
	}
	
	public void print() {
		print(G, V);
	}
	
	/** 打矩阵（顶点存在则打印）*/
	public static void print(Object matrix, Object[] Objs) {
		Print.printnb(" G  ");
		for (int i = 0; i < MAX; i++) {
			if (Objs[i] == null) continue;
			Print.printnb(String.format("%2d  ", i));
		}
		Print.print();
		for (int i = 0; i < MAX; i++) {
			if (Objs[i] == null) continue;
			Print.printnb(String.format("%2d  ", i));
			Object arr = Array.get(matrix, i);
			for (int j = 0; j < MAX; j++) {
				if (Objs[j] == null) continue;
				double value = Double.valueOf(Array.get(arr, j).toString());
				value =  value >= UNREACH ? -1 : value;	//最大则转换为-2
				Print.printnb(String.format("%2.0f  ", value));
			}
			Print.print();
		}
	}
	
	public static Graph build1() {
		Graph graph = new Graph();
		for (int i = 0; i < 30; i++) {
			graph.addNode(i);
		}
		graph.connect(0, 1, 2D);
		graph.connect(1, 2, 4D);
		graph.connect(1, 8, 8D);
		graph.connect(2, 7, 8D);
		graph.connect(2, 3, 4D);
		graph.connect(3, 4, 4D);
		graph.connect(3, 6, 8D);
		graph.connect(4, 5, 8D);
		graph.connect(5, 6, 4D);
		graph.connect(5, 21, 4D);
		graph.connect(6, 7, 4D);
		graph.connect(6, 22, 4D);
		graph.connect(7, 8, 4D);
		graph.connect(8, 9, 4D);
		graph.connect(9, 10, 4D);
		graph.connect(10, 11, 2D);
		graph.connect(10, 23, 4D);
		graph.connect(11, 12, 4D);
		graph.connect(12, 13, 4D);
		graph.connect(13, 14, 2D);
		graph.connect(14, 15, 6D);
		graph.connect(15, 16, 4D);
		graph.connect(16, 17, 2D);
		graph.connect(16, 18, 4D);
		graph.connect(18, 19, 2D);
		graph.connect(19, 20, 4D);
		graph.connect(19, 22, 4D);
		graph.connect(19, 23, 4D);
		graph.connect(20, 21, 4D);
		graph.connect(17, 25, 0D);	//25和17号点重合
		graph.connect(19, 26, 0D);	//26和19号点重合
		graph.connect(6, 27, 0D);	//27和6号点重合
		
		graph.print();
		graph.findWay(0,4);
		graph.findWay(17,25);
		graph.findWay(17,16);
		graph.findWay(25,1);
		graph.findWay(1,25);
		graph.delete(19);	//删除19号点
		graph.findWay(25,1);
		graph.findWay(1,25);
		graph.delete(22);	//删除22号点,断开一个关键路口
		graph.findWay(25,2);
		graph.findWay(2,25);
		graph.delete(26);	//删除26号点
		graph.delete(27);	//删除27号点，断开另一个关键路口
		graph.findWay(25,2);
		graph.findWay(2,25);
		
		graph.findWay(0, 28); //不可达测试
		return graph;
	}
	
	public static Graph build2() {
		Graph graph = new Graph();
		for (int i = 0; i < 5; i++) {
			graph.addNode(i);
		}
		graph.connect(0, 1, 2D);
		graph.connect(0, 4, 10D);
//		graph.connect(0, 2, 4D);
		graph.connect(1, 2, 3D);
//		graph.connect(1, 4, 7D);
		graph.connect(2, 3, 4D);
		graph.connect(3, 4, 5D);
		
		graph.print();
//		graph.findWay(1,2);
		graph.findWay(1,3);
		return graph;
	}
	
	public static void main(String[] args) {
		build1();
//		build2();

	}
	
}
