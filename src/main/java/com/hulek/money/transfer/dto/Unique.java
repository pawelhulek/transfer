package com.hulek.money.transfer.dto;

import java.util.Objects;

public final class Unique<T> {
    private final String id;
    private final T value;

    public Unique(String id, T value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unique<?> unique = (Unique<?>) o;
        return Objects.equals(id, unique.id) &&
                Objects.equals(value, unique.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "Unique{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
