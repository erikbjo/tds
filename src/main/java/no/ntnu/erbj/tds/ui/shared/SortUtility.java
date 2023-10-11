package no.ntnu.erbj.tds.ui.shared;

import java.util.Comparator;
import java.util.List;

/**
 * Utility class for sorting lists.
 *
 * @version 1.0
 */
public class SortUtility {

  /**
   * Sorts a list of objects based on a specific comparator.
   *
   * @param <T> the type of objects in the list
   * @param list the list to be sorted
   * @param comparator the comparator defining the sort order
   * @return a new list containing the sorted objects
   */
  public static <T> List<T> sortBy(List<T> list, Comparator<T> comparator) {
    return list.stream().sorted(comparator).toList();
  }
}
