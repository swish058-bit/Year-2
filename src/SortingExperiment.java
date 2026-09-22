import java.util.Arrays;
import java.util.Random;

public class SortingExperiment {

    private static final Random random = new Random();

    // Generate random array
    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt();
        }

        return arr;
    }

    // Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {

                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    // Selection Sort
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // Merge Sort
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {

        if (left >= right) {
            return;
        }

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {

        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {

            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // Heap Sort
    public static void heapSort(int[] arr) {

        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {

            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int size, int root) {

        int largest = root;

        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != root) {

            int temp = arr[root];
            arr[root] = arr[largest];
            arr[largest] = temp;

            heapify(arr, size, largest);
        }
    }

    // Timing Method
    public static double timeSort(int[] arr, String algorithm) {

        long startTime = System.nanoTime();

        switch (algorithm) {

            case "Bubble":
                bubbleSort(arr);
                break;

            case "Selection":
                selectionSort(arr);
                break;

            case "Merge":
                mergeSort(arr);
                break;

            case "Heap":
                heapSort(arr);
                break;

            case "Java":
                Arrays.sort(arr);
                break;
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }

    public static void main(String[] args) {

        int[] sizes = {
                1000,
                2000,
                5000,
                10000,
                20000,
                50000,
                100000,
                1000000,
        };

        String[] algorithms = {
                "Bubble",
                "Selection",
                "Merge",
                "Heap",
                "Java"
        };

        int trials = 5;

        System.out.printf(
                "%-10s %-12s %-12s %-12s %-12s %-12s%n",
                "Size",
                "Bubble",
                "Selection",
                "Merge",
                "Heap",
                "Java"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (int size : sizes) {

            double[] averages = new double[algorithms.length];

            for (int trial = 0; trial < trials; trial++) {

                int[] original = generateRandomArray(size);

                for (int a = 0; a < algorithms.length; a++) {

                    int[] copy = Arrays.copyOf(original, original.length);

                    averages[a] += timeSort(copy, algorithms[a]);
                }
            }

            System.out.printf("%-10d", size);

            for (int a = 0; a < algorithms.length; a++) {
                averages[a] /= trials;
                System.out.printf("%-12.2f", averages[a]);
            }

            System.out.println();
        }
    }
}