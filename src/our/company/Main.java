package our.company;

import java.util.Arrays;

public class Main {

    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        checkWithoutMulti(arr);
        checkWithMulti(arr);
    }

    public static void checkWithoutMulti(float[] arr){
        long startTime = System.currentTimeMillis();
        arrayProcessing(arr);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public static void arrayProcessing(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void checkWithMulti(float[] arr){
        float[] firstArr = new float[h];
        float[] secondArr = new float[h];
        long startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, firstArr, 0, h);
        System.arraycopy(arr, h, secondArr, 0, h);

        Thread t1 = new Thread(() -> {
            arrayProcessing(firstArr);
        });
        Thread t2 = new Thread(() -> {
            arrayProcessing(secondArr);
        });

        t1.start();
        t2.start();

        float[] generalArr = new float[size];
        System.arraycopy(firstArr, 0, generalArr, 0, h);
        System.arraycopy(secondArr, 0, generalArr, h, h);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

    }
}
