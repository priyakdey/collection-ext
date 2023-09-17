package com.priyakydey.collection.ext.example;

import com.priyakydey.collection.ext.heap.Heap;
import com.priyakydey.collection.ext.heap.MinHeap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Random;

/**
 * @author Priyak Dey
 */
public class MinHeapExample {

    public static void main(String[] args) {
        createHeapForNaturalOrder();
        createHeapForCustomComparator();
    }


    private static void createHeapForNaturalOrder() {
        // Generate a Heap of first 20 natural numbers
        Heap<Integer> heap = new MinHeap<>();

        for (int i = 1; i <= 20; i++) {
            heap.push(i);
        }

        // Pop first 6 numbers and get a svg dump of current state
        for (int i = 0; i < 6; i++) {
            System.out.println(heap.pop());
        }

        String svg = heap.toSvg();

        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter(new File("./examples/min_heap_natural_numbers.svg")))) {
            bw.write(svg);
        } catch (IOException ex) {
            System.err.printf("ERROR: Error when dumping svg to file.. - %s\n", ex.getMessage());
        }
    }


    private static void createHeapForCustomComparator() {
        // Generate a Heap of 10 employees
        Heap<Employee> heap = new MinHeap<>(
            Comparator.comparing(Employee::getFirstName).thenComparing(Employee::getLastName));

        String[] firstNames =
            new String[] {"Peria", "Osbert", "Barrie", "Correy", "Wildon", "Diann", "Lesli"};

        String[] lastNames =
            new String[] {"Bresland", "Bilverstone", "Sharpless", "Dunsford", "Tidder", "O'Hannen", "Dowry"};

        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            int firstNameIndex = random.nextInt(firstNames.length - 1);
            int lastNameIndex = random.nextInt(firstNames.length - 1);

            Employee employee =
                new Employee(i, firstNames[firstNameIndex], lastNames[lastNameIndex]);
            heap.push(employee);

        }

        String svg = heap.toSvg();

        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter(new File("./examples/min_heap_employees.svg")))) {
            bw.write(svg);
        } catch (IOException ex) {
            System.err.printf("ERROR: Error when dumping svg to file.. - %s\n", ex.getMessage());
        }
    }

    static class Employee {
        private final int id;
        private final String firstName;
        private final String lastName;

        public Employee(int id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return String.format("%d: %s %s", id, firstName, lastName);
        }
    }

}
