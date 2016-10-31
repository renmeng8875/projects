package cn.javass.point.service.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.javass.point.exception.NotCodeException;
import cn.javass.point.model.GoodsCodeModel;
import cn.javass.point.model.GoodsModel;
import cn.javass.point.service.GoodsCodeService;
import cn.javass.point.service.GoodsService;


@ContextConfiguration(
        locations={"classpath:applicationContext-resources-test.xml",
                   "classpath:cn/javass/point/dao/applicationContext-hibernate.xml",
                   "classpath:cn/javass/point/service/applicationContext-service.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback=false)
public class GoodsCodeServiceImplIntegrationTest extends AbstractJUnit4SpringContextTests {
    
    
    @Autowired
    private GoodsCodeService goodsCodeService;

    @Autowired
    private GoodsService goodsService;
    

    @Transactional
    @Rollback
    @ExpectedException(NotCodeException.class)
    @Test
    public void testBuyFail() {
        goodsCodeService.buy("test", 1);
    }
    
    @Transactional
    @Rollback
    @Test
    public void testBuySuccess() {
        //1.添加商品
        GoodsModel goods = new GoodsModel();
        goods.setDeleted(false);
        goods.setDescription("");
        goods.setName("测试商品");
        goods.setPublished(true);
        goodsService.save(goods);
        
        //2.添加商品Code码
        GoodsCodeModel goodsCode = new GoodsCodeModel();
        goodsCode.setGoods(goods);
        goodsCode.setCode("test");
        goodsCodeService.save(goodsCode);
        //3.测试购买商品Code码
        GoodsCodeModel resultGoodsCode = goodsCodeService.buy("test", 1);
        Assert.assertEquals(goodsCode.getId(), resultGoodsCode.getId());
    }
    
}
