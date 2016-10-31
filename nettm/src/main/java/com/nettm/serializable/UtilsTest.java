package com.nettm.serializable;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UtilsTest {

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
        val.setId(10L);
        val.setItemCode("");
        val.setItemDespositPrice(32.45);
        val.setItemMemo(null);
        val.setItemName("张金");
        val.setItemPrice(89.02);
        val.setSort(10);

        String a = Utils.object2String(val);
        CustomItemDto newValue = Utils.string2Object(a, CustomItemDto.class);
        Assert.assertEquals(val.getId(), newValue.getId());
    }

}
