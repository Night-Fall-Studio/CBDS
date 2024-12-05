package com.github.nightfall.cbds.cr;

import com.badlogic.gdx.utils.Array;
import finalforeach.cosmicreach.savelib.utils.IDynamicArray;

public class DynamicArray<E> extends Array<E> implements IDynamicArray<E> {
    public DynamicArray(Class<E> clazz) {
        super(clazz);
    }

    public DynamicArray(Class<E> clazz, int initialCapacity) {
        super(true, initialCapacity, clazz);
    }

    public int size() {
        return this.size;
    }

    public E[] items() {
        return this.items;
    }
}
