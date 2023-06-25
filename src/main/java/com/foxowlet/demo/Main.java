package com.foxowlet.demo;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
        long start = System.nanoTime();
        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
        }
        System.out.println(System.nanoTime() - start);
        System.out.println(sum);
        start = System.nanoTime();
        sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        System.out.println(System.nanoTime() - start);
        System.out.println(sum);
    }
}
