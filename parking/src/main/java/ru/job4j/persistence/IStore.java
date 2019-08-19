package ru.job4j.persistence;

public interface IStore<V> {
    public <V> V addEntity(V item);

    public <V> V deleteEntity(V item);

    public <V> V updateEntity(V item);
}
