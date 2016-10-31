package cn.javass.point.service.impl;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.javass.point.dao.GoodsCodeDao;
import cn.javass.point.exception.NotCodeException;
import cn.javass.point.model.GoodsCodeModel;
import cn.javass.point.service.GoodsCodeService;

public class GoodsCodeServiceImplUnitTest {
    

    //1、Mock对象上下文，用于创建Mock对象
    private final Mockery context = new Mockery() {{
        //1.1、表示可以支持Mock非接口类，默认只支持Mock接口
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    
    //2、Mock IGoodsCodeDao接口
    private GoodsCodeDao goodsCodeDao = context.mock(GoodsCodeDao.class);;
    
    private GoodsCodeService goodsCodeService;

    @Before
    public void setUp() {
        GoodsCodeService goodsCodeServiceTemp = new GoodsCodeService();
        //3、依赖注入
        goodsCodeServiceTemp.setDao(goodsCodeDao);
        goodsCodeService = goodsCodeServiceTemp;
        
    }
    
    @Test(expected = NotCodeException.class)
    public void testBuyFail() {
        final int goodsId = 1;
        //4、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //5、表示需要调用goodsCodeDao对象的getOneNotExchanged一次且仅一次
                //且返回值为null
                one(goodsCodeDao).getOneNotExchanged(goodsId);
                will(returnValue(null));
            }
        });
        goodsCodeService.buy("test", goodsId);
        context.assertIsSatisfied();
    }

    @Test()
    public void testBuySuccess() {
        final int goodsId = 1;
        final GoodsCodeModel goodsCode = new GoodsCodeModel();
        //6、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //7、表示需要调用goodsCodeDao对象的getOneNotExchanged一次且仅仅一次
                //且返回值为null
                one(goodsCodeDao).getOneNotExchanged(goodsId);
                will(returnValue(goodsCode));
                //8、表示需要调用goodsCodeDao对象的save方法一次且仅一次
                //且参数为goodsCode
                one(goodsCodeDao).save(goodsCode);
            }
        });
        goodsCodeService.buy("test", goodsId);
        context.assertIsSatisfied();
        Assert.assertEquals(goodsCode.isExchanged(), true);
    }

}
