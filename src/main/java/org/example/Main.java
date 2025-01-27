package org.example;
import java.util.Comparator;

class MyArrayList<T> {
    private T[] array;
    private int size;
    private static int defaultCapacity = 10;

    public MyArrayList() {
        this(defaultCapacity);
    }
    public void printAll(){
        for(int i = 0; i< array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    public MyArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive.");
        }
        array = (T[]) new Object[initialCapacity];
        size = 0;
    }

    public void add(T element) { // Добавляет элемент в конец списка
        add(size, element);
    }

    public void add(int index, T element) { // Добавляет элемент по индексу
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    public T get(int index) { // Возвращает элемент по индексу
        checkIndex(index);
        return array[index];
    }

    public T remove(int index) { // Удаляет и возвращает элемент по индексу
        checkIndex(index);
        T removedElement = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null; // Удаляем ссылку, чтобы помочь сборщику мусора
        return removedElement;
    }

    public void clear() { // Очищает список
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public int size() { // Возвращает размер списка
        return size;
    }

    public void sort(Comparator<T> comparator) { // Сортировка списка с помощью QuickSort
        if (comparator == null) {
            throw new NullPointerException("Comparator cannot be null");
        }
        QuickSort.quickSort(this, comparator);
    }

    private void ensureCapacity(int minCapacity) { // Увеличивает емкость массива, если необходимо
        if (minCapacity > array.length) {
            int newCapacity = array.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            resize(newCapacity);
        }
    }

    private void resize(int newCapacity) { // Изменяет размер массива
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void checkIndex(int index) { // Проверка индекса
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) { // Проверка индекса для метода add(int, T)
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    public void swap(int i, int j) { // Метод для обмена элементов
        checkIndex(i);
        checkIndex(j);
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}


class QuickSort { // Класс QuickSort, отдельный от MyArrayList

    public static <T> void quickSort(MyArrayList<T> list, Comparator<T> comparator) {
        quickSortRecursive(list, 0, list.size() - 1, comparator);
    }

    private static <T> void quickSortRecursive(MyArrayList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSortRecursive(list, low, pivotIndex - 1, comparator);
            quickSortRecursive(list, pivotIndex + 1, high, comparator);
        }
    }

    private static <T> int partition(MyArrayList<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high); // Выбираем последний элемент в качестве опорного
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) { // Сравниваем элементы
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(MyArrayList<T> list, int i, int j) {
        list.swap(i,j); // Используем метод swap из MyArrayList
    }
}



public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> intList = new MyArrayList<>();
        intList.add(10);
        intList.add(7);
        intList.add(8);
        intList.add(9);
        intList.add(1);
        intList.add(5);

        System.out.println("Integer list before sorting: ");
        intList.printAll();
        intList.sort(Integer::compareTo); // Сортировка с помощью Integer::compareTo
        System.out.println("Integer list after sorting: ");
        intList.printAll();
        System.out.println("Delete first element: ");
        intList.remove(1);
        intList.printAll();
        System.out.println("Added by index (first element)");
        intList.add(1, 12);
        intList.printAll();
        System.out.println("Get() check (index 1): ");
        int a = intList.get(1);
        System.out.println(a);
        System.out.println("Clear() check:");
        intList.clear();
        intList.printAll();

        MyArrayList<String> stringList = new MyArrayList<>();
        stringList.add("banana");
        stringList.add("apple");
        stringList.add("orange");
        stringList.add("grape");

        System.out.println("\nString list before sorting: ");
        stringList.printAll();
        stringList.sort(String::compareTo); // Сортировка с помощью String::compareTo
        System.out.println("String list after sorting: ");
        stringList.printAll();
    }
}