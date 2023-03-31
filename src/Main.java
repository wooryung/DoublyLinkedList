import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        Iterator<String> iterator = arrayList.iterator();
        String data2 = iterator.next();
        data2 = iterator.next();
        data2 = iterator.next();

        WrList<String> list = new WrList<>();
        list.add(0, "5");
        list.add("1");
        list.add("3");
        list.add("2");
        list.add("3"); // [5, 1, 3, 2, 3]
        int lastIndexOf = list.lastIndexOf("2");
        list.remove("3"); // [5, 1, 2, 3]
        list.add(0, "4"); // [4, 5, 1, 2, 3]

        String[] array = new String[1];
        Object[] array2 = new Object[0];
        array = list.toArray(array);
        array2 = list.toArray();

        List<String> test3 = new ArrayList<>();
        Boolean addAll2 = list.addAll(0, test3);

        List<String> test = new ArrayList<>();
        test.add("10");
        test.add("20");
        Boolean addAll = list.addAll(0, test); // [10, 20, 4, 5, 1, 2, 3]
        Boolean containsAll = list.containsAll(test);
        Boolean contains = list.contains("3");
        String get = list.get(2);
        String set = list.set(1, "30"); // [10, 30, 4, 5, 1, 2, 3]
//        String remove = list.remove(6);
        int indexOf = list.indexOf("31");

        List<String> test2 = new ArrayList<>();
//        test2.add("10");
//        test2.add("30");
        test2.add("4");
        test2.add("5");
//        test2.add("1");
//        test2.add("2");
        test2.add("3");
//        Boolean removeAll = list.removeAll(test2); // [10, 30, 1, 2]
//        Boolean retainAll = list.retainAll(test); // [10, 30]
//        Boolean retainAll2 = list.retainAll(test3);

//        list.clear();

        Iterator<String> iterator1 = list.iterator();
        String data = iterator1.next();

        List<String> subList = new WrList<>();
        subList = list.subList(7, 7);
//        array2 = subList.toArray();
//        subList.remove("4");
//        subList.add("20");
//        subList.clear();

        List<String> subSubList = new WrList<>();
        subSubList = subList.subList(0, 0);
        subSubList.clear();

        List<String> list1 = new WrList<>();
        ListIterator<String> iterator2 = list1.listIterator();
        Boolean hasNext = iterator2.hasNext();
        Boolean hasPrevious = iterator2.hasPrevious();
        iterator2.add("1");
        iterator2.add("2");
        int nextIndex = iterator2.nextIndex();
        String previous = iterator2.previous();
        int previousIndex = iterator2.previousIndex();
        iterator2.set("3");
        iterator2.remove();
    }
}