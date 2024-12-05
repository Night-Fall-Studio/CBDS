package com.github.nightfall.cbds.cr;

import com.badlogic.gdx.utils.ByteArray;
import finalforeach.cosmicreach.savelib.IByteArray;

public class DynamicByteArray extends ByteArray implements IByteArray {
    public DynamicByteArray() {
    }

    public void addAll(IByteArray bytes) {
        this.addAll(bytes.items(), 0, bytes.size());
    }

    public int size() {
        return this.size;
    }

    public byte[] items() {
        return this.items;
    }
}
