package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static ArrayList<Graph> graph = new ArrayList<>();
    static final int SIZE = 5;
    static ArrayList<String> vector;
    static ArrayList<Boolean> flagVector;
    static int[][] graphMatrix = new int[SIZE][SIZE];
    static int[][] minCost = new int[SIZE][SIZE];

    public static void main(String[] args) {
        fillGraph();
        fillMatrix();
        searchMinCost();
        int first = 3, second = 4;
        int min = algDijkstra(first, second);
        searchAllWaves(first, second);
        System.out.printf("\nMinimal cost : %d", min);
        System.out.printf("\nEccentricity : %d", searchEccentricity());
    }

    static void searchAllWaves(int a, int b) {
        vector = new ArrayList<>();
        vector.add(Integer.toString(a));
        flagVector = new ArrayList<>();
        flagVector.add(false);
        int count = 0;
        recursiveSearch(a, b, count);
        for(int i = 0; i < vector.size(); i++) {
            if(flagVector.get(i)) {
                String str = vector.get(i);
                int sum = 0;
                sum = calculate(str, 0, 1, sum);
                System.out.printf("\n%s : %d", str, sum);
            }
        }

    }

    static int calculate(String str, int a, int b, int sum) {
        if(b < str.length()) {
            String tmp = str.substring(a, b);
            sum += graph.get(Integer.parseInt(tmp) - 1).cost(Integer.parseInt(str.substring(++a, ++b)));
            sum = calculate(str, a, b, sum);
        }
        return sum;
    }

    static void recursiveSearch(int a, int b, int count) {
        if (!graph.get(a - 1).isEmpty()) {
            String temp = vector.get(count);
            temp += Integer.toString(graph.get(a - 1).getVertex(0));
            vector.set(count, temp);
            if (Integer.toString(b).equals(Character.toString(temp.charAt(temp.length() - 1)))) {
                flagVector.set(count, true);
            } else {
                recursiveSearch(graph.get(a - 1).getVertex(0), b, count);
            }
            for (int i = 1; i < graph.get(a - 1).getSize(); i++) {
                count++;
                vector.add(temp.substring(0, temp.length() - 1) + graph.get(a - 1).getVertex(i));
                flagVector.add(false);
                String tmp = vector.get(count);
                if (Integer.toString(b).equals(Character.toString(tmp.charAt(tmp.length() - 1)))) {
                    flagVector.set(count, true);
                } else {
                    recursiveSearch(graph.get(a - 1).getVertex(i), b, count);
                }
            }
        }
    }

    static int algDijkstra ( int a, int b){
        int[] arr = new int[graph.size()];
        boolean[] flags = new boolean[graph.size()];
        Arrays.fill(arr, Integer.MAX_VALUE / 2);
        arr[a - 1] = 0;
        flags[a - 1] = true;
        return recusrive(a, b, arr, flags);
    }

    static int recusrive ( int a, int b, int[] arr, boolean[] flags){
        for (int i = 0; i < graph.get(a - 1).getSize(); i++) {
            if (arr[graph.get(a - 1).getVertex(i) - 1] > graph.get(a - 1).cost(graph.get(a - 1).getVertex(i)) + arr[a - 1])
                arr[graph.get(a - 1).getVertex(i) - 1] = graph.get(a - 1).cost(graph.get(a - 1).getVertex(i)) + arr[a - 1];
        }
        int min = b - 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < arr[min] && !flags[i]) {
                min = i;
                flags[i] = true;
                break;
            }
        }
        if (min != b - 1)
            arr[b - 1] = recusrive(min + 1, b, arr, flags);
        return arr[b - 1];
    }

    static int searchEccentricity() {
        int[] max = new int[SIZE];

        for(int j = 0; j < SIZE; j++) {
            max[j] = minCost[0][j];
            for (int i = 1; i < SIZE; i++)
                if (max[j] < minCost[i][j])
                    max[j] = minCost[i][j];
        }
        int min = 0;
        for(int i = 1; i < SIZE; i++)
            if(max[min] > max[i])
                min = i;
        return min + 1;
    }

    static void searchMinCost() {
        for(int k = 0; k < SIZE; k++)
            for(int i = 0; i < SIZE; i++)
                for(int j = 0; j < SIZE; j++)
                    if(minCost[i][k] + minCost[k][j] < minCost[i][j])
                        minCost[i][j] = minCost[i][k] + minCost[k][j];
    }

    static void fillMatrix() {
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                if(i == j)
                    graphMatrix[i][j] = 0;
                else
                    graphMatrix[i][j] = graph.get(i).cost(j + 1);
        for(int i = 0; i < SIZE; i++)
            System.arraycopy(graphMatrix[i], 0, minCost[i], 0, SIZE);
    }


    static void fillGraph() {
        for (int i = 0; i < SIZE; i++)
            graph.add(new Graph());
        /*graph.get(0).add(2,8);
        graph.get(0).add(3,5);
        graph.get(1).add(1,3);
        graph.get(2).add(2,2);*/

        graph.get(0).add(2, 1);
        graph.get(1).add(3, 2);
        graph.get(2).add(5, 4);
        graph.get(2).add(4, 2);
        graph.get(3).add(3, 3);
        graph.get(3).add(2, 1);
        graph.get(4).add(4, 5);
    }
}

