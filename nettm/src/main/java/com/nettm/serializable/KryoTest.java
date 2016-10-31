package com.nettm.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;

public class KryoTest {

    private long time;

    @BeforeTest
    public void beforeTest() {
        time = System.currentTimeMillis();
    }

    @AfterTest
    public void afterTest() {
        System.out.println(System.currentTimeMillis() - time);
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void testObject() {
        CustomItemDto val = new CustomItemDto();
        val.setId(Long.parseLong(String.valueOf(1)));
        val.setItemCode("");
        val.setItemDespositPrice(32.45);
        val.setItemMemo(null);
        val.setItemName("张金");
        val.setItemPrice(89.02);
        val.setSort(10);

        String a = serializationObject(val);
        CustomItemDto newValue = deserializationObject(a, CustomItemDto.class);
        Assert.assertEquals(val.getId(), newValue.getId());
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void testList() {
        List<CustomItemDto> lst = new ArrayList<CustomItemDto>();
        for (int i = 0; i < 10; i++) {
            CustomItemDto val = new CustomItemDto();
            val.setId(Long.parseLong(String.valueOf(i)));
            val.setItemCode("");
            val.setItemDespositPrice(32.45);
            val.setItemMemo(null);
            val.setItemName("张金");
            val.setItemPrice(89.02);
            val.setSort(10);
            lst.add(val);
        }

        String a = serializationList(lst, CustomItemDto.class);
        List<CustomItemDto> newValue = deserializationList(a, CustomItemDto.class);
        Assert.assertEquals(lst.size(), newValue.size());
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void testBean() {
        CustomCategoryDto dto = new CustomCategoryDto();
        dto.setCategoryCode("ABCD_001");
        dto.setCategoryName("呼吸系统");
        for (int i = 0; i < 10; i++) {
            CustomItemDto val = new CustomItemDto();
            val.setId(Long.parseLong(String.valueOf(i)));
            val.setItemCode("");
            val.setItemDespositPrice(32.45);
            val.setItemMemo(null);
            val.setItemName("张金");
            val.setItemPrice(89.02);
            val.setSort(10);
            dto.getCustomItemList().add(val);
        }
        for (int i = 0; i < 10; i++) {
            CustomItemDto val = new CustomItemDto();
            val.setId(Long.parseLong(String.valueOf(i)));
            val.setItemCode("");
            val.setItemDespositPrice(32.45);
            val.setItemMemo(null);
            val.setItemName("张金");
            val.setItemPrice(89.02);
            val.setSort(10);
            dto.getCustomItemSet().add(val);
        }
        for (int i = 0; i < 10; i++) {
            CustomItemDto val = new CustomItemDto();
            val.setId(Long.parseLong(String.valueOf(i)));
            val.setItemCode("");
            val.setItemDespositPrice(32.45);
            val.setItemMemo(null);
            val.setItemName("张金");
            val.setItemPrice(89.02);
            val.setSort(10);
            dto.getCustomItemMap().put(String.valueOf(i), val);
        }

        String a = serializationObject(dto);
        CustomCategoryDto newValue = deserializationObject(a, CustomCategoryDto.class);
        Assert.assertEquals(dto.getCategoryCode(), newValue.getCategoryCode());
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void testMap() {
        Map<String, CustomItemDto> map = new HashMap<String, CustomItemDto>();
        for (int i = 0; i < 10; i++) {
            CustomItemDto val = new CustomItemDto();
            val.setId(Long.parseLong(String.valueOf(i)));
            val.setItemCode("");
            val.setItemDespositPrice(32.45);
            val.setItemMemo(null);
            val.setItemName("张金");
            val.setItemPrice(89.02);
            val.setSort(10);
            map.put(new ObjectId().toString(), val);
        }

        String a = serializationMap(map, CustomItemDto.class);
        Map<String, CustomItemDto> newValue = deserializationMap(a, CustomItemDto.class);
        Assert.assertEquals(map.size(), newValue.size());
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void testSet() {
        Set<CustomItemDto> set = new HashSet<CustomItemDto>();
        for (int i = 0; i < 10; i++) {
            CustomItemDto val = new CustomItemDto();
            val.setId(Long.parseLong(String.valueOf(i)));
            val.setItemCode("");
            val.setItemDespositPrice(32.45);
            val.setItemMemo(null);
            val.setItemName("金星");
            val.setItemPrice(89.02);
            val.setSort(10);
            set.add(val);
        }

        String a = serializationSet(set, CustomItemDto.class);
        Set<CustomItemDto> newValue = deserializationSet(a, CustomItemDto.class);
        Assert.assertEquals(set.size(), newValue.size());
    }

    private <T extends Serializable> String serializationObject(T obj) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(obj.getClass(), new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(new Base64().encode(b));
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> T deserializationObject(String obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(clazz, new JavaSerializer());

        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (T) kryo.readClassAndObject(input);
    }

    private <T extends Serializable> String serializationList(List<T> obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(new Base64().encode(b));
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> List<T> deserializationList(String obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (List<T>) kryo.readObject(input, ArrayList.class, serializer);
    }

    private <T extends Serializable> String serializationMap(Map<String, T> obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(String.class, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(clazz, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashMap.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(new Base64().encode(b));
    }

    @SuppressWarnings("unchecked")
    private <T extends Serializable> Map<String, T> deserializationMap(String obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(String.class, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(clazz, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashMap.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (Map<String, T>) kryo.readObject(input, HashMap.class, serializer);
    }

    public static <T extends Serializable> String serializationSet(Set<T> obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(new Base64().encode(b));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> Set<T> deserializationSet(String obj, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (Set<T>) kryo.readObject(input, HashSet.class, serializer);
    }
}
