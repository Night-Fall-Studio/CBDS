package com.github.nightfall.cbds.cr;

import com.badlogic.gdx.utils.ObjectMap;
import finalforeach.cosmicreach.savelib.utils.IObjectMap;
import java.util.function.BiConsumer;

public class CRObjectMap<K, V> extends ObjectMap<K, V> implements IObjectMap<K, V> {
    public CRObjectMap() {
    }

    public CRObjectMap(ObjectMap<K, V> srcMap) {
        super(srcMap);
    }

    public void putAll(IObjectMap<K, V> srcMap) {
        if (srcMap instanceof CRObjectMap<K, V> o) {
            super.putAll(o);
        } else {
            this.ensureCapacity(srcMap.size());
            srcMap.forEachEntry(this::put);
        }
    }

    public void forEachEntry(BiConsumer<K, V> entryConsumer) {

        for (Entry<K, V> kvEntry : this.entries()) {
            entryConsumer.accept(kvEntry.key, kvEntry.value);
        }

    }

    public int size() {
        return this.size;
    }
}
