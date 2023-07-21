/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author AD
 */
public class Lab1 {
private static final int START_NUMBER = 1;
    private static final int END_NUMBER = 1000000;
    private static final int SO_THREADS = 3;

    private AtomicLong sum = new AtomicLong(0);

    public static void main(String[] args) {
        Lab1 calculator = new Lab1();
        calculator.tinhTong();
    }

    public void tinhTong() {
        Thread[] threads = new Thread[SO_THREADS];

        int segmentSize = (END_NUMBER - START_NUMBER + 1) / SO_THREADS;
        int start = START_NUMBER;
        int end = start + segmentSize - 1;

        for (int i = 0; i < SO_THREADS; i++) {
            if (i == SO_THREADS - 1) {
                end = END_NUMBER;
            }

            final int startNumber = start;
            final int endNumber = end;

            threads[i] = new Thread(() -> {
                long threadSum = calculatePartialSum(startNumber, endNumber);
                sum.addAndGet(threadSum);
            });

            threads[i].start();

            start = end + 1;
            end = start + segmentSize - 1;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Tổng các số nguyên tố từ 1 đến 1.000.000 là : " + sum);
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private long calculatePartialSum(int start, int end) {
        long partialSum = 0;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                partialSum += i;
            }
        }
        return partialSum;
    }
}
