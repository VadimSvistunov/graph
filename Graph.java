package com.company;

public class Graph {

    private Node first = null;
    private Node last = null;
    private int lastIndex = -1;

    void add(int vertex, int cost) {
        if(first == null) {
            first = new Node(vertex, cost, ++lastIndex);
            last = first;
        } else {
            last.next = new Node(vertex, cost, ++lastIndex);
            last = last.next;
        }
    }

    int getSize() {
        return lastIndex + 1;
    }

    int cost(int vertex) {
        if(!isEmpty()) {
            Node current = first;
            do {
                if(current.vertex == vertex) {
                    return current.cost;
                }
            } while((current = current.next) != null);
        }
        return Integer.MAX_VALUE / 100;
    }

    int getVertex(int index) {
        if(!isEmpty()) {
            Node current = first;
            do {
                if(index == current.index)
                    return current.vertex;
            } while((current= current.next) != null);
        }
        return -1;
    }

    boolean isEmpty() {
        return first == null;
    }

    public class Node {
        Node next;
        int vertex;
        int index;
        int cost;
        Node(int vertex, int cost, int index) {
            this.vertex = vertex;
            this.index = index;
            this.cost = cost;
            next = null;
        }
    }

}
