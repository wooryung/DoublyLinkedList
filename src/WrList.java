import java.util.*;

public class WrList<E> implements List<E> {
    private WrList<E> origin;
    Node<E> head;
    Node<E> tail;
    int size;

    private static class Node<E> {
        E data;
        Node<E> prev;
        Node<E> next;

        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public WrList() { // 생성자
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);

        head.next = tail;
        size = 0;
    }

    public WrList(WrList<E> origin, Node<E> fromNode, Node<E> toNode, int subSize) { // 생성자
        head = fromNode.prev;
        tail = toNode;
        size = subSize;
        this.origin = origin;
    }

    private class WrListIterator implements ListIterator<E> {
        private Node<E> current;
        private Node<E> lastReturned;
        private int currentIndex;

        public WrListIterator(int index) { // 생성자. 시작 노드 설정
            current = (index == size) ? tail : getNode(index);
            currentIndex = index;
            lastReturned = null;
        }

        @Override
        public boolean hasNext() { // 현재 위치에 요소가 있는지 여부 반환
            return current != tail;
        }

        @Override
        public E next() { // 현재 요소를 반환하고 다음 위치로 이동
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = current;
            current = current.next;
            currentIndex++;

            return lastReturned.data;
        }

        @Override
        public boolean hasPrevious() { // 현재 위치 이전에 요소가 있는지 여부 반환
            return current.prev != head;
        }

        @Override
        public E previous() { // 이전 위치로 이동하여 요소 반환
            if (!hasPrevious())
                throw new NoSuchElementException();

            current = current.prev;
            currentIndex--;
            lastReturned = current;

            return lastReturned.data;
        }

        @Override
        public int nextIndex() { // 현재 요소의 인덱스 반환
            return currentIndex;
        }

        @Override
        public int previousIndex() { // 이전 요소의 인덱스 반환
            return currentIndex - 1;
        }

        @Override
        public void remove() { // 마지막으로 반환된 요소 삭제
            if (lastReturned == null)
                throw new IllegalStateException();

            if (current == lastReturned)
                current = current.next;
            else
                currentIndex--;

            removeNode(lastReturned);
            lastReturned = null;
        }

        @Override
        public void set(E e) { // 마지막으로 반환된 요소를 새로운 요소로 대체
            if (lastReturned == null)
                throw new IllegalStateException();

            lastReturned.data = e;
            lastReturned = null;
        }

        @Override
        public void add(E e) { // 현재 위치에 새로운 요소 추가
            Node<E> newNode = new Node<>(e, null, null);
            addNode(current, newNode);
            currentIndex++;
        }
    }

    @Override
    public int size() { // 리스트에 저장된 요소의 개수 반환
        return size;
    }

    @Override
    public boolean isEmpty() { // 리스트가 비어있는지 여부 반환
        return size == 0;
    }

    @Override
    public boolean contains(Object o) { // 지정된 요소가 리스트에 있는지 여부 반환
        Node<E> current = head.next;

        while (current != tail) {
            if (Objects.equals(current.data, o))
                return true;

            current = current.next;
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() { // 리스트의 요소에 대한 반복자 반환
        return new WrListIterator(0);
    }

    @Override
    public Object[] toArray() { // 리스트의 요소를 배열로 반환
        int i = 0;
        Node<E> current = head.next;
        Object[] array = new Object[size];

        while (current != tail) {
            array[i++] = current.data;
            current = current.next;
        }

        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) { // 리스트의 요소를 지정된 배열로 반환
        if (a.length < size)
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);

        int i = 0;
        Object[] result = a;
        Node<E> current = head.next;

        while (current != tail) {
            result[i++] = current.data;
            current = current.next;
        }

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(E e) { // 리스트의 끝에 지정된 요소 추가
        Node<E> position = tail;
        Node<E> newNode = new Node<>(e, null, null);
        addNode(position, newNode);

        return true;
    }

    @Override
    public boolean remove(Object o) { // 리스트에서 지정된 요소 제거
        if (isEmpty())
            return false;

        Node<E> current = head.next;

        while (current != tail) {
            if (Objects.equals(current.data, o)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) { // 지정된 컬렉션의 모든 요소가 리스트에 있는지 여부 반환
        for (Object o : c)
            if (!contains(o))
                return false;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) { // 리스트의 끝에 지정된 컬렉션의 모든 요소 추가
        boolean modified = false;

        for (E e : c) {
            add(e);
            modified = true;
        }

        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) { // 지정된 인덱스에서 지정된 컬렉션의 모든 요소 추가
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        if (c.isEmpty())
            return false;

        Node<E> position = (index == size) ? tail : getNode(index);

        for (E e : c) {
            Node<E> newNode = new Node<>(e, null, null);
            addNode(position, newNode);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) { // 지정된 컬렉션에 포함된 모든 요소를 리스트에서 제거
        boolean modified = false;
        Node<E> current = head.next;

        while (current != tail) {
            if (c.contains(current.data)) {
                removeNode(current);
                modified = true;
            }
            current = current.next;
        }

        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) { // 지정된 컬렉션에 포함된 요소만 리스트에 남겨둠
        boolean modified = false;
        Node<E> current = head.next;

        while (current != tail) {
            if (!c.contains(current.data)) { // 지정된 컬렉션에 포함되지 않은 요소 삭제
                removeNode(current);
                modified = true;
            }
            current = current.next;
        }

        return modified;
    }

    @Override
    public void clear() { // 리스트에서 모든 요소 제거
        head.next = tail;
        tail.prev = head;
        clearSize(size);
    }

    @Override
    public E get(int index) { // 지정된 인덱스에 있는 요소 반환
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        return getNode(index).data;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> current = head.next;

        for (int i = 0; i < index; i++)
            current = current.next;

        return current;
    }

    @Override
    public E set(int index, E element) { // 지정된 인덱스에 있는 요소를 새 요소로 대체
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> current = getNode(index);
        E oldElement = current.data;
        current.data = element;

        return oldElement;
    }

    @Override
    public void add(int index, E element) { // 지정된 인덱스에서 지정된 요소 추가
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node<E> newNode = new Node<>(element, null, null);
        Node<E> position = (index == size) ? tail : getNode(index);
        addNode(position, newNode);
    }

    @Override
    public E remove(int index) { // 지정된 인덱스에 있는 요소 제거
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<E> node = getNode(index);
        removeNode(node);

        return node.data;
    }

    @Override
    public int indexOf(Object o) { // 지정된 요소의 첫 번째 인덱스 반환
        int index = 0;
        Node<E> current = head.next;

        while (current != tail) {
            if (Objects.equals(current.data, o))
                return index;

            index++;
            current = current.next;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) { // 지정된 요소의 마지막 인덱스 반환
        int index = size - 1;
        Node<E> current = tail.prev;

        while (current != head) {
            if (Objects.equals(current.data, o))
                return index;

            index--;
            current = current.prev;
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() { // 리스트의 요소에 대한 ListIterator 반환
        return new WrListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) { // 리스트의 지정된 위치에서 시작하는 ListIterator 반환
        return new WrListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) { // 지정된 범위에 해당하는 리스트 반환
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        Node<E> fromNode = (fromIndex == size) ? tail : getNode(fromIndex);
        Node<E> toNode = (toIndex == size) ? tail : getNode(toIndex);

        return new WrList<>(this, fromNode, toNode, toIndex - fromIndex);
    }

    private void removeNode(Node<E> node) { // 노드를 받아와서 삭제
        node.prev.next = node.next;
        node.next.prev = node.prev;
        decSize();
    }

    private void addNode(Node<E> position, Node<E> node) {
        node.prev = position.prev;
        node.next = position;
        position.prev.next = node;
        position.prev = node;
        incSize();
    }

    private void incSize() {
        size++;

        if (origin != null)
            origin.incSize();
    }

    private void decSize() {
        size--;

        if (origin != null)
            origin.decSize();
    }

    private void clearSize(int beforeSize) {
        size = 0;

        if (origin != null)
            for (int i = 0; i < beforeSize; i++)
                origin.decSize();
    }
}
