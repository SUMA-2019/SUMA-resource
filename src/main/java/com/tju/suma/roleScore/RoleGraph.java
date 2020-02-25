package com.tju.suma.roleScore;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RoleGraph {
    public final List<Integer> poolWeightList = new ArrayList<>();
    public final Map<Integer, RoleGraph.Vertex> vertexMap = new ConcurrentHashMap<>();
    public final Map<Integer, List<Integer>> adjList = new ConcurrentHashMap<>();

    private int nPool;
    private int weightPool;
    private static final RoleGraph graph = new RoleGraph();
    //用栈实现深度优先搜索
    private Stack<Integer> theStack = new Stack<>();

    /**
     * 顶点类
     */
    public static RoleGraph getRoleGraph(){
        return graph;
    }
    static class Vertex {
        public int weight;
        public boolean wasVisited;
        public int poolIndex;

        public Vertex() {
            this.weight = 1;
            this.wasVisited = false;
            this.poolIndex = -1;
        }
    }

    private RoleGraph() {
        this.nPool = 0;
    }

    //将顶点添加到数组中，是否访问标志置为wasVisited=false(未访问)
    public void addVertex(int lab) {
        if (vertexMap.containsKey(lab)) {
            int tmp = vertexMap.get(lab).weight;
            vertexMap.get(lab).weight = tmp + 1;
        } else {
            RoleGraph.Vertex v = new RoleGraph.Vertex();
            vertexMap.put(lab, v);
        }

    }

    //注意用邻接矩阵表示边，是对称的，两部分都要赋值
    public void addEdge(int start, int end) {
        if (!adjList.containsKey(start)) {
            adjList.put(start, new ArrayList<>());
        }
        adjList.get(start).add(end);

        if (!adjList.containsKey(end)) {
            adjList.put(end, new ArrayList<>());
        }
        adjList.get(end).add(start);
    }

    /**
     * 深度优先搜索算法:
     * 1、用peek()方法检查栈顶的顶点
     * 2、用getAdjUnvisitedVertex()方法找到当前栈顶点邻接且未被访问的顶点
     * 3、第二步方法返回值不等于-1则找到下一个未访问的邻接顶点，访问这个顶点，并入栈
     * 如果第二步方法返回值等于 -1，则没有找到，出栈
     */
    public void depthFirstSearch() {
        //从第一个顶点开始访问
        for (Map.Entry<Integer, Vertex> integerVertexEntry : vertexMap.entrySet()) {
            weightPool = 0;
            int lab = integerVertexEntry.getKey();
            Vertex vTmp = integerVertexEntry.getValue();
            boolean flagVisit = vTmp.wasVisited;
            if (!flagVisit) {
                vTmp.wasVisited = true;
                theStack.push(lab);
                vTmp.poolIndex = nPool;
                weightPool = weightPool + vTmp.weight;
                while (!theStack.isEmpty()) {
                    boolean flag = getAdjUnvisitedVertex(theStack.peek());
                    //如果当前顶点值为-1，则表示没有邻接且未被访问顶点，那么出栈顶点
                    if (!flag) {
                        theStack.pop();
                    }
                }
            }
            nPool++;
            poolWeightList.add(weightPool);
        }

    }

    //找到与某一顶点邻接且未被访问的顶点
    public boolean getAdjUnvisitedVertex(Integer v) {
        boolean flag = false;
        if (adjList.containsKey(v)) {
            List<Integer> vList = adjList.get(v);
            for (int vTmp : vList) {
                Vertex vertexTmp = vertexMap.get(vTmp);
                if (!vertexTmp.wasVisited) {
                    theStack.push(vTmp);
                    vertexTmp.poolIndex = nPool;
                    weightPool = weightPool + vertexTmp.weight;
                    vertexTmp.wasVisited = true;
                    flag = true;
                }
            }
        }
        return flag;
    }

    public int getPropertyWeight(int equiPro1) {
        if (vertexMap.containsKey(equiPro1)) {
            int poolIndexTmp = vertexMap.get(equiPro1).poolIndex;
            return poolWeightList.get(poolIndexTmp);
        }
        return 0;
    }
}
