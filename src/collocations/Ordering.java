package collocations;

/**
 * This class is used only to separate sorting from the class that should be
 * sorted
 *
 * @author dj
 */
public class Ordering {

    /**
     * Method which does one iteration of quicksort
     *
     * @param arr
     * @param left
     * @param right
     * @param sortBy
     * @return
     */
    private static int partition(BigramWithMeasures arr[], int left, int right, String sortBy){
        int i = left;
        int j = right;
        BigramWithMeasures tmp;
        BigramWithMeasures pivot = arr[(left + right) / 2];
        while (i <= j) {
            while (arr[i].getValue(sortBy) > pivot.getValue(sortBy))
                i++;
            while (arr[j].getValue(sortBy) < pivot.getValue(sortBy))
                j--;
            if (i <= j) {
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }
        return i;
    }

    /**
     * Method to start do quicksorting recursively
     *
     * @param arr
     * @param left
     * @param right
     * @param sortBy
     */
    public static void quickSort(BigramWithMeasures arr[], int left, int right, String sortBy) {
          int index = partition(arr, left, right, sortBy);
          if (left < index - 1)
                quickSort(arr, left, index - 1, sortBy);
          if (index < right)
                quickSort(arr, index, right, sortBy);
    }


}
