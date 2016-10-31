package cn.javass.point.dao.hibernate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.javass.point.dao.GoodsDao;
import cn.javass.point.model.GoodsModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations={"classpath:applicationContext-resources-test.xml",
                   "classpath:cn/javass/point/dao/applicationContext-hibernate.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback=false)
public class GoodsHibernateDaoIntegrationTest {
    

    @Autowired
    private ApplicationContext ctx;
    
    @Autowired
    private GoodsDao goodsDao;
    
    @Transactional
    @Rollback
    @Test
    public void testListAllPublishedSuccess() {
        GoodsModel goods = new GoodsModel();
        goods.setDeleted(false);
        goods.setDescription("");
        goods.setName("测试商品");
        goods.setPublished(true);
        goodsDao.save(goods);
        Assert.assertTrue(goodsDao.listAllPublished(1).size() == 1);
        Assert.assertTrue(goodsDao.listAllPublished(2).size() == 0);
    }



}
