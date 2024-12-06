package com.github.nightfall.cbds;

import com.github.nightfall.cbds.cr.*;
import com.github.nightfall.cbds.io.serial.api.INamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedDeserializer;
import com.github.nightfall.cbds.io.serial.api.IUnNamedSerializer;
import com.github.nightfall.cbds.io.serial.impl.NamedBinaryDeserializer;
import com.github.nightfall.cbds.io.serial.impl.NamedBinarySerializer;
import com.github.nightfall.cbds.io.serial.api.INamedSerializer;
import com.github.nightfall.cbds.io.serial.impl.UnNamedBinarySerializer;
import com.github.nightfall.cbds.objects.TestObject;
import finalforeach.cosmicreach.savelib.IByteArray;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.utils.*;

import java.io.IOException;
import java.util.Random;

public class Main {

    static long startTime;
    static long endTime;
    static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        CBDSConstants.init();

        DynamicArrays.instantiator = new IDynamicArrayInstantiator() {
            public <E> IDynamicArray<E> create(Class<E> clazz) {
                return new DynamicArray<>(clazz);
            }

            public <E> IDynamicArray<E> create(Class<E> clazz, int initialCapacity) {
                return new DynamicArray<>(clazz, initialCapacity);
            }

            public IByteArray createByteArray() {
                return new DynamicByteArray();
            }
        };

        ObjectMaps.instantiator = new IObjectMapInstantiator() {
            public <K> IObjectLongMap<K> createObjectLongMap() {
                return new CRObjectLongMap<>();
            }

            public <K> IObjectIntMap<K> createObjectIntMap() {
                return new CRObjectIntMap<>();
            }

            public <K> IObjectFloatMap<K> createObjectFloatMap() {
                return new CRObjectFloatMap<>();
            }

            public <K, V> IObjectMap<K, V> create() {
                return new CRObjectMap<>();
            }

            public <K, V> IObjectMap<K, V> create(IObjectMap<K, V> srcMap) {
                if (srcMap instanceof CRObjectMap<K, V> m) {
                    return new CRObjectMap<>(m);
                } else {
                    CRObjectMap<K, V> m = new CRObjectMap<>();
                    m.putAll(srcMap);
                    return m;
                }
            }
        };

        INamedSerializer serializer = new NamedBinarySerializer();
        IUnNamedSerializer userializer = new UnNamedBinarySerializer();
        CRBinSerializer crSerializer = new CRBinSerializer();

        int objCount = 400;
        final TestObject[] objs = new TestObject[objCount];
        for (int i = 0; i < objs.length; i++) objs[i] = new TestObject();

        startTime = System.nanoTime();
        serializer.writeUnNamedObjectArray("testObjs", objs);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to write " + objCount + " objects to CBDS");

        startTime = System.nanoTime();
        userializer.writeUnNamedObjectArray(objs);

        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to write " + objCount + " objects to UCBDS");

        startTime = System.nanoTime();
        crSerializer.writeObjArray("testObjs", objs);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to write " + objCount + " objects to CRBIN");

        System.out.println();

        final byte[] CRBIN_REGULAR_BYTES = runCRBINSerializationBenchmark(crSerializer, SerializationStyle.REGULAR);

        final byte[] CBDS_REGULAR_BYTES = runCBDSSerializationBenchmark(serializer, SerializationStyle.REGULAR);
        final byte[] CBDS_COMPRESSED_BYTES = runCBDSSerializationBenchmark(serializer, SerializationStyle.COMPRESSED);
        calculateSizeDiff(CBDS_REGULAR_BYTES.length, CBDS_COMPRESSED_BYTES.length, "CBDS Regular with size %d bytes and CBDS Compressed with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_REGULAR_BYTES.length, CBDS_REGULAR_BYTES.length, "CRBIN Regular with size %d bytes and CBDS Regular with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_REGULAR_BYTES.length, CBDS_COMPRESSED_BYTES.length, "CRBIN Regular with size %d bytes and CBDS Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        final byte[] CRBIN_B64_REGULAR_BYTES = runCRBINSerializationBenchmark(crSerializer, SerializationStyle.REGULAR_B64);
        final byte[] CBDS_B64_REGULAR_BYTES = runCBDSSerializationBenchmark(serializer, SerializationStyle.REGULAR_B64);
        final byte[] CBDS_B64_COMPRESSED_BYTES = runCBDSSerializationBenchmark(serializer, SerializationStyle.COMPRESSED_B64);
        calculateSizeDiff(CBDS_B64_REGULAR_BYTES.length, CBDS_B64_COMPRESSED_BYTES.length, "CBDS B64_Regular with size %d bytes and CBDS B64_Compressed with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_B64_REGULAR_BYTES.length, CBDS_B64_REGULAR_BYTES.length, "CRBIN B64_Regular with size %d bytes and CBDS B64_Regular with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_B64_REGULAR_BYTES.length, CBDS_B64_COMPRESSED_BYTES.length, "CRBIN B64_Regular with size %d bytes and CBDS B64_Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        final byte[] UCBDS_REGULAR_BYTES = runCBDSSerializationBenchmark(userializer, SerializationStyle.REGULAR);
        final byte[] UCBDS_COMPRESSED_BYTES = runCBDSSerializationBenchmark(userializer, SerializationStyle.COMPRESSED);
        calculateSizeDiff(UCBDS_REGULAR_BYTES.length, UCBDS_COMPRESSED_BYTES.length, "UCBDS Regular with size %d bytes and UCBDS Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        final byte[] UCBDS_B64_REGULAR_BYTES = runCBDSSerializationBenchmark(userializer, SerializationStyle.REGULAR_B64);
        final byte[] UCBDS_B64_COMPRESSED_BYTES = runCBDSSerializationBenchmark(userializer, SerializationStyle.COMPRESSED_B64);
        calculateSizeDiff(UCBDS_B64_REGULAR_BYTES.length, UCBDS_B64_COMPRESSED_BYTES.length, "UCBDS B64_Regular with size %d bytes and UCBDS B64_Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        startTime = System.nanoTime();
        INamedDeserializer deserializer = INamedDeserializer.createDefault(CBDS_REGULAR_BYTES, false);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize CBDS");
        startTime = System.nanoTime();
        deserializer.readUnNamedObjectArray("testObjs", TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from CBDS");

        System.out.println();

        startTime = System.nanoTime();
        INamedDeserializer deserializerc = INamedDeserializer.createDefault(CBDS_COMPRESSED_BYTES, true);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize COMPRESSED CBDS");
        startTime = System.nanoTime();
        deserializerc.readUnNamedObjectArray("testObjs", TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from COMPRESSED CBDS");

        System.out.println();

        startTime = System.nanoTime();
        IUnNamedDeserializer deserializer0 = IUnNamedDeserializer.createDefault(UCBDS_REGULAR_BYTES, false);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize UCBDS");
        startTime = System.nanoTime();
        deserializer0.readUnNamedObjectArray(TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from UCBDS");

        System.out.println();

        startTime = System.nanoTime();
        IUnNamedDeserializer deserializer1 = IUnNamedDeserializer.createDefault(UCBDS_COMPRESSED_BYTES, true);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize COMPRESSED UCBDS");
        startTime = System.nanoTime();
        deserializer1.readUnNamedObjectArray(TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from COMPRESSED UCBDS");

        System.out.println();

        startTime = System.nanoTime();
        CRBinDeserializer crBinDeserializer = CRBinDeserializer.fromBase64(new String(CRBIN_B64_REGULAR_BYTES));
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize CRBIN");
        startTime = System.nanoTime();
        crBinDeserializer.readObjArray("testObjs", TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from CRBIN");
    }

    public static void calculateSizeDiff(int largest, int smallest, String message) {
        System.out.println((message).formatted(largest, smallest, (100f / largest) * smallest).replaceAll("\\$per", "%"));
    }

    public static byte[] runCBDSSerializationBenchmark(INamedSerializer serializer, SerializationStyle style) {
        ThrowableSupplier<byte[]> method = switch (style) {
            case REGULAR -> serializer::toBytes;
            case COMPRESSED -> serializer::toCompressedBytes;
            case REGULAR_B64 -> () -> serializer.toBase64().getBytes();
            case COMPRESSED_B64 -> () -> serializer.toCompressedBase64().getBytes();
        };

        try {
            startTime = System.nanoTime();
            byte[] bytes = method.get();
            endTime = System.nanoTime();
            System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize with styleSerializationStyle { "+style.name()+" } with size " + bytes.length + " bytes on Named CBDS");

            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] runCBDSSerializationBenchmark(IUnNamedSerializer serializer, SerializationStyle style) {
        ThrowableSupplier<byte[]> method = switch (style) {
            case REGULAR -> serializer::toBytes;
            case COMPRESSED -> serializer::toCompressedBytes;
            case REGULAR_B64 -> () -> serializer.toBase64().getBytes();
            case COMPRESSED_B64 -> () -> serializer.toCompressedBase64().getBytes();
        };

        try {
            startTime = System.nanoTime();
            byte[] bytes = method.get();
            endTime = System.nanoTime();
            System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize with styleSerializationStyle { "+style.name()+" } with size " + bytes.length + " bytes on UnNamed CBDS");

            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] runCRBINSerializationBenchmark(CRBinSerializer serializer, SerializationStyle style) {
        ThrowableSupplier<byte[]> method = switch (style) {
            case REGULAR -> serializer::toBytes;
            case REGULAR_B64 -> () -> serializer.toBase64().getBytes();
            default -> throw new IllegalStateException("Unexpected value: " + style);
        };

        try {
            startTime = System.nanoTime();
            byte[] bytes = method.get();
            endTime = System.nanoTime();
            System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize with styleSerializationStyle { "+style.name()+" } with size " + bytes.length + " bytes on CRBIN");

            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double nanoToMilli(long nano) {
        return (nano / 1e+6);
    }

    public enum SerializationStyle {
        REGULAR,
        COMPRESSED,
        REGULAR_B64,
        COMPRESSED_B64
    }

    private interface ThrowableSupplier<T> {
        T get() throws IOException;
    }

}
