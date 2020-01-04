import java.io.File;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    private static final int UP_TO = 40;
    private static final int MIN_BUCKET = 0;
    private static final int MAX_BUCKET = 1;
    private static final int MIN_MERGE = 5000;
    private static final int MAX_MERGE = 1000000;
    private static final int[] ARRAYS_LENGTH = {5, 10, 30, 50, 100, 200, 500, 700, 850, 1000};

    private static void mergeSort(int[] arr, int len) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (len < 2) {
            return;
        }

        int mid = len / 2;
        int[] left = new int[mid];
        int[] right = new int[len - mid];

        int i;
        for (i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        for (i = mid; i < len; i++) {
            right[i - mid] = arr[i];
        }

        mergeSort(left, mid);
        mergeSort(right, len - mid);

        mergeComp(arr, left, right, mid, len - mid);
    }

    private static void mergeComp(int[] arr, int[] arrLeft, int[] arrRight, int lenLeft, int lenRight) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < lenLeft && j < lenRight) {
            if (arrLeft[i] <= arrRight[j]) {
                arr[k++] = arrLeft[i++];
            } else {
                arr[k++] = arrRight[j++];
            }
        }

        while (i < lenLeft) {
            arr[k++] = arrLeft[i++];
        }

        while (j < lenRight) {
            arr[k++] = arrRight[j++];
        }
    }

    private static void bucketSort(double[] arr, int len) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Double>[] buckets = (ArrayList<Double>[]) new ArrayList[len];

        int i;
        for (i = 0; i < len; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (i = 0; i < len; i++) {
            int bucketIndex = (int) (len * arr[i]);
            (buckets[bucketIndex]).add(arr[i]);
        }

        for (i = 0; i < len; i++) {
            insertionSort(buckets[i]);
        }

        int index = 0;
        for (i = 0; i < arr.length; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[index] = buckets[i].get(j);
                index++;
            }
        }
    }

    private static void insertionSort(ArrayList<Double> arrayList) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i;
        int j;
        double key;

        for (i = 1; i < arrayList.size(); i++) {
            key = arrayList.get(i);
            j = i - 1;
            while (j >= 0 && arrayList.get(j) > key) {
                arrayList.set(j + 1, arrayList.get(j));
                j -= 1;
            }
            arrayList.set(j + 1, key);
        }
    }

    private static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    private static int[] fillWithInt(int length, int min, int max) {
        return new Random().ints(length, min, max).toArray();
    }

    private static double[] fillWithDouble(int length, int min, int max) {
        return new Random().doubles(length, min, max).toArray();
    }

    public static void main(String[] args) throws IOException {
        File fileM = new File("merge.txt");
        if (!fileM.exists()) {
            fileM.createNewFile();
        }

        File fileB = new File("bucket.txt");
        if (!fileB.exists()) {
            fileB.createNewFile();
        }

        File fileF = new File("fibonacci.txt");
        if (!fileF.exists()) {
            fileF.createNewFile();
        }
        FileWriter fileWriterM = new FileWriter(fileM, false);
        FileWriter fileWriterB = new FileWriter(fileB, false);
        FileWriter fileWriterF = new FileWriter(fileF, false);

        // MERGE SECTION
        try ( BufferedWriter bufferedWriterM = new BufferedWriter(fileWriterM)) {
            int[] mergeTempArray;

            for (int i : ARRAYS_LENGTH) {
                mergeTempArray = Test.fillWithInt(i, MIN_MERGE, MAX_MERGE);

                long startTime = System.currentTimeMillis();
                mergeSort(mergeTempArray, mergeTempArray.length);
                long endTime = System.currentTimeMillis();

                bufferedWriterM.write(i + " " + (endTime - startTime) + " milliseconds\n");
            }
        }
        // BUCKET SECTION
        try ( BufferedWriter bufferedWriterB = new BufferedWriter(fileWriterB)) {
            double[] bucketTempArray;

            for (int i : ARRAYS_LENGTH) {
                bucketTempArray = Test.fillWithDouble(i, MIN_BUCKET, MAX_BUCKET);

                long startTime = System.currentTimeMillis();
                bucketSort(bucketTempArray, bucketTempArray.length);
                long endTime = System.currentTimeMillis();

                bufferedWriterB.write(i + " " + (endTime - startTime) + " milliseconds\n");
            }
        }

        // FIBONACCI SECTION
        try ( BufferedWriter bufferedWriterF = new BufferedWriter(fileWriterF)) {
            for (int i = 0; i <= UP_TO; i++) {
                long startTime = System.currentTimeMillis();
                fibonacci(i);
                long endTime = System.currentTimeMillis();

                bufferedWriterF.write(i + " " + (endTime - startTime) + " milliseconds\n");
            }
        }
    }
}
