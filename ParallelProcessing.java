package Queue;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class ParallelProcessing {

    private static int heapSize;

    public static void sort(long[][] a) {
        buildHeap(a);
        while (heapSize > 1) {
            swap(a, 0, heapSize - 1);
            heapSize--;
            heapify(a, 0);
        }
    }

    private static void buildHeap(long[][] a) {
        heapSize = a[1].length;
        for (int i = a[1].length / 2; i >= 0; i--) {
            heapify(a, i);
        }
    }

    private static void heapify(long[][] a, int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < heapSize && a[1][i] > a[1][l]) {
            smallest = l;
        }
        if (r < heapSize && a[1][smallest] > a[1][r]) {
            smallest = r;
        }
        if (i != smallest) {
            swap(a, i, smallest);
            heapify(a, smallest);
        }
    }

    private static int right(int i) {
        return 2 * i + 2;
    }

    private static int left(int i) {
        return 2 * i + 1;
    }

    private static void swap(long[][] a, int i, int j) {

        long temp = a[1][i];
        a[1][i] = a[1][j];
        a[1][j] = temp;

        temp = a[0][i];
        a[0][i] = a[0][j];
        a[0][j] = temp;

//        if(a[1][i] != a[1][j]) {
//            temp = a[0][i];
//            a[0][i] = a[0][j];
//            a[0][j] = temp;
//        }
    }


    static void showArray(long[][] array) {
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void main(String[] args) {

        // нумерация задач и процессоров происходит с нуля

        Scanner scanner = new Scanner(System.in);


        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Queue<Long> tasks = new ArrayBlockingQueue<>(m);

        long[][] processors = new long[2][n];
        Arrays.fill(processors[1], -1);
        for (int i = 0; i < n; i++) {
            processors[0][i] = i;
        }

        for (int i = 0; i < m; i++) {
            tasks.add(scanner.nextLong());
        }

        // необходимо запоминать номера процессоров
        // minHeap по двумерному массиву?
        // [0][i] - номер процессора
        // [1][i] - задача, обрабатываемая процессором


        buildHeap(processors);

        // повторить m раз (m задач)
        int t = 0;  // time
        while (!tasks.isEmpty()) {
            showArray(processors);
            long taskTime;
            if (processors[1][0] <= 0) {   // если есть свободный процессор, то
                processors[1][0] = tasks.poll();    // дать процессору задачу из очереди
                System.out.println(processors[0][0] + " " + t);   // вывести информацию о том что процессор n в момент времени t взял задачу m
            } else {
                taskTime = processors[1][0];        // узнаём время потраченное на предыдущую (старую) задачу
                processors[1][0] = tasks.poll();    // взять процессор с минимальной загрузкой (корень) и заменить задачу в корне на новую задачу из очереди
                t += taskTime;  // добавляем время, потраченное на задачу к глобальному time счётчику
                System.out.println(processors[0][0] + " " + t);   // вывести информацию о том что процессор n в момент времени t взял задачу m
                for (int i = 0; i < n; i++) {
                    // вычесть у всех работающих задач время задачи в корне (симуляция течения времени для остальных задач)
                    processors[1][i] -= taskTime;
                }

            }
            buildHeap(processors);   // сортировка процессоров (вытесняем процессор с минимальной загрузкой в корень)

        }

    }

}
