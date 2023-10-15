package engine;

import java.util.Map;
import java.util.Objects;

public class Resource<T> {
    protected Map<Integer, T> data;

    protected void remove(Integer key) { data.remove(key); }
    protected void put(Integer key, T obj) { data.put(key, obj); }
    public T get(Integer key) { return data.get(key); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource<?> resource = (Resource<?>) o;
        return Objects.equals(data, resource.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "data=" + data +
                '}';
    }
}
