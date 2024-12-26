package com.github.nightfall.odsl;

import com.github.nightfall.odsl.cr.*;
import com.github.nightfall.odsl.io.serial.api.IKeylessDeserializer;
import com.github.nightfall.odsl.io.serial.api.IKeylessSerializer;
import com.github.nightfall.odsl.io.serial.api.INamedDeserializer;
import com.github.nightfall.odsl.io.serial.api.INamedSerializer;
import com.github.nightfall.odsl.io.serial.impl.NamedBinarySerializer;
import com.github.nightfall.odsl.io.serial.impl.KeylessBinarySerializer;
import com.github.nightfall.odsl.objects.TestObject;
import finalforeach.cosmicreach.savelib.IByteArray;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import finalforeach.cosmicreach.savelib.utils.*;

import java.io.IOException;
public class Main {

    static long startTime;
    static long endTime;

    public static void main(String[] args) throws IOException {
        ODSLConstants.init();

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
        IKeylessSerializer userializer = new KeylessBinarySerializer();
        CRBinSerializer crSerializer = new CRBinSerializer();

        int objCount = 400;
        final TestObject[] objs = new TestObject[objCount];
        for (int i = 0; i < objs.length; i++) objs[i] = new TestObject();

        startTime = System.nanoTime();
        serializer.writeKeylessObjectArray("testObjs", objs);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to write " + objCount + " objects to ODSL");

        startTime = System.nanoTime();
        userializer.writeKeylessObjectArray(objs);

        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to write " + objCount + " objects to UODSL");

        startTime = System.nanoTime();
        crSerializer.writeObjArray("testObjs", objs);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to write " + objCount + " objects to CRBIN");

        System.out.println();

        final byte[] CRBIN_REGULAR_BYTES = runCRBINSerializationBenchmark(crSerializer, SerializationStyle.REGULAR);
        final byte[] ODSL_REGULAR_BYTES = runODSLSerializationBenchmark(serializer, SerializationStyle.REGULAR);
        final byte[] ODSL_COMPRESSED_BYTES = runODSLSerializationBenchmark(serializer, SerializationStyle.COMPRESSED);
        calculateSizeDiff(ODSL_REGULAR_BYTES.length, ODSL_COMPRESSED_BYTES.length, "ODSL Regular with size %d bytes and ODSL Compressed with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_REGULAR_BYTES.length, ODSL_REGULAR_BYTES.length, "CRBIN Regular with size %d bytes and ODSL Regular with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_REGULAR_BYTES.length, ODSL_COMPRESSED_BYTES.length, "CRBIN Regular with size %d bytes and ODSL Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        final byte[] CRBIN_B64_REGULAR_BYTES = runCRBINSerializationBenchmark(crSerializer, SerializationStyle.REGULAR_B64);
        final byte[] ODSL_B64_REGULAR_BYTES = runODSLSerializationBenchmark(serializer, SerializationStyle.REGULAR_B64);
        final byte[] ODSL_B64_COMPRESSED_BYTES = runODSLSerializationBenchmark(serializer, SerializationStyle.COMPRESSED_B64);
        calculateSizeDiff(ODSL_B64_REGULAR_BYTES.length, ODSL_B64_COMPRESSED_BYTES.length, "ODSL B64_Regular with size %d bytes and ODSL B64_Compressed with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_B64_REGULAR_BYTES.length, ODSL_B64_REGULAR_BYTES.length, "CRBIN B64_Regular with size %d bytes and ODSL B64_Regular with size %d bytes changed by %.2f$per");
        calculateSizeDiff(CRBIN_B64_REGULAR_BYTES.length, ODSL_B64_COMPRESSED_BYTES.length, "CRBIN B64_Regular with size %d bytes and ODSL B64_Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        final byte[] UODSL_REGULAR_BYTES = runODSLSerializationBenchmark(userializer, SerializationStyle.REGULAR);
        final byte[] UODSL_COMPRESSED_BYTES = runODSLSerializationBenchmark(userializer, SerializationStyle.COMPRESSED);
        calculateSizeDiff(UODSL_REGULAR_BYTES.length, UODSL_COMPRESSED_BYTES.length, "UODSL Regular with size %d bytes and UODSL Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        final byte[] UODSL_B64_REGULAR_BYTES = runODSLSerializationBenchmark(userializer, SerializationStyle.REGULAR_B64);
        final byte[] UODSL_B64_COMPRESSED_BYTES = runODSLSerializationBenchmark(userializer, SerializationStyle.COMPRESSED_B64);
        calculateSizeDiff(UODSL_B64_REGULAR_BYTES.length, UODSL_B64_COMPRESSED_BYTES.length, "UODSL B64_Regular with size %d bytes and UODSL B64_Compressed with size %d bytes changed by %.2f$per");

        System.out.println();

        startTime = System.nanoTime();
        INamedDeserializer deserializer = INamedDeserializer.createDefault(ODSL_REGULAR_BYTES, false);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize ODSL");
        startTime = System.nanoTime();
        deserializer.readKeylessObjectArray("testObjs", TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from ODSL");

        System.out.println();

        startTime = System.nanoTime();
        INamedDeserializer deserializerc = INamedDeserializer.createDefault(ODSL_COMPRESSED_BYTES, true);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize COMPRESSED ODSL");
        startTime = System.nanoTime();
        deserializerc.readKeylessObjectArray("testObjs", TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from COMPRESSED ODSL");

        System.out.println();

        startTime = System.nanoTime();
        IKeylessDeserializer deserializer0 = IKeylessDeserializer.createDefault(UODSL_REGULAR_BYTES, false);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize UODSL");
        startTime = System.nanoTime();
        deserializer0.readKeylessObjectArray(TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from UODSL");

        System.out.println();

        startTime = System.nanoTime();
        IKeylessDeserializer deserializer1 = IKeylessDeserializer.createDefault(UODSL_COMPRESSED_BYTES, true);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to deserialize COMPRESSED UODSL");
        startTime = System.nanoTime();
        deserializer1.readKeylessObjectArray(TestObject.class);
        endTime = System.nanoTime();
        System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to read " + objCount + " item object array from COMPRESSED UODSL");

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

    public static byte[] runODSLSerializationBenchmark(INamedSerializer serializer, SerializationStyle style) {
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
            System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize with styleSerializationStyle { "+style.name()+" } with size " + bytes.length + " bytes on Named ODSL");

            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] runODSLSerializationBenchmark(IKeylessSerializer serializer, SerializationStyle style) {
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
            System.out.println("Took " + nanoToMilli(endTime - startTime) + "ms to serialize with styleSerializationStyle { "+style.name()+" } with size " + bytes.length + " bytes on UnNamed ODSL");

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
