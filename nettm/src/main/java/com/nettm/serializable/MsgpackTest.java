package com.nettm.serializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.msgpack.MessagePack;
import org.msgpack.template.SetTemplate;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MsgpackTest {

    private long time;
    private MessagePack msgpack;

    @BeforeTest
    public void beforeTest() {
        time = System.currentTimeMillis();
        msgpack = new MessagePack();
        msgpack.register(CustomItemDto.class);
        msgpack.register(CustomCategoryDto.class);
    }

    @AfterTest
    public void afterTest() {
        msgpack.unregister();
        System.out.println(System.currentTimeMillis() - time);
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void testObject() {
        CustomItemDto val = new CustomItemDto();
        val.setId(10L);
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
            val.setId(10L);
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
            val.setId(10L);
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
            val.setId(10L);
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
        byte[] b = null;
        try {
            b = msgpack.write(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(new Base64().encode(b));
    }

    private <T extends Serializable> T deserializationObject(String obj, Class<T> clazz) {
        T t = null;
        byte[] bytes = new Base64().decode(obj.getBytes());
        try {
            t = msgpack.read(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    private <T extends Serializable> String serializationList(List<T> obj, Class<T> clazz) {
        byte[] b = null;
        try {
            b = msgpack.write(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(new Base64().encode(b));
    }

    private <T extends Serializable> List<T> deserializationList(String obj, Class<T> clazz) {
        Template<T> elementTemplate = msgpack.lookup(clazz);
        Template<List<T>> listTmpl = Templates.tList(elementTemplate);

        List<T> t = null;
        byte[] bytes = new Base64().decode(obj.getBytes());
        try {
            t = msgpack.read(bytes, listTmpl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    private <T extends Serializable> String serializationMap(Map<String, T> obj, Class<T> clazz) {
        byte[] b = null;
        try {
            b = msgpack.write(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(new Base64().encode(b));
    }

    private <T extends Serializable> Map<String, T> deserializationMap(String obj, Class<T> clazz) {
        Template<T> elementTemplate = msgpack.lookup(clazz);
        Template<Map<String, T>> listTmpl = Templates.tMap(Templates.TString, elementTemplate);

        Map<String, T> t = null;
        byte[] bytes = new Base64().decode(obj.getBytes());
        try {
            t = msgpack.read(bytes, listTmpl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    private <T extends Serializable> String serializationSet(Set<T> obj, Class<T> clazz) {
        Template<T> elementTemplate = msgpack.lookup(clazz);
        byte[] b = null;
        try {
            b = msgpack.write(obj, new SetTemplate<T>(elementTemplate));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(new Base64().encode(b));
    }

    private <T extends Serializable> Set<T> deserializationSet(String obj, Class<T> clazz) {
        Template<T> elementTemplate = msgpack.lookup(clazz);
        Set<T> t = null;
        byte[] bytes = new Base64().decode(obj.getBytes());
        try {
            t = msgpack.read(bytes, new SetTemplate<T>(elementTemplate));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
