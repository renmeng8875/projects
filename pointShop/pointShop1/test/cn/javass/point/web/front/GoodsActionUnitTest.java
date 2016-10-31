package cn.javass.point.web.front;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import cn.javass.point.exception.NotCodeException;
import cn.javass.point.model.GoodsCodeModel;
import cn.javass.point.service.GoodsCodeService;
import cn.javass.point.web.front.action.GoodsAction;

public class GoodsActionUnitTest {
    
    

    //1、Mock对象上下文，用于创建Mock对象
    private final Mockery context = new Mockery() {{
        //1.1、表示可以支持Mock非接口类，默认只支持Mock接口
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    
    //2、Mock IGoodsCodeService接口
    private GoodsCodeService goodsCodeService =  context.mock(GoodsCodeService.class);
    
    private GoodsAction goodsAction;

    @Before
    public void setUp() {
        goodsAction = new GoodsAction();
        //3、依赖注入
        goodsAction.setGoodsCodeService(goodsCodeService);
    }
    
    @Test 
    public void testBuyFail() {
        final int goodsId = 1;
        
        //4、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //5、表示需要调用goodsCodeService对象的buy方法一次且仅一次
                //且抛出NotCodeException异常
                one(goodsCodeService).buy("test", goodsId);
                will(throwException(new NotCodeException()));
            }
        });
        //6、模拟Struts注入请求参数
        goodsAction.setGoodsId(goodsId);
        String actualResultCode = goodsAction.buy();
        context.assertIsSatisfied();
        Assert.assertEquals(ReflectionTestUtils.getField(goodsAction, "BUY_RESULT"), actualResultCode);
        Assert.assertTrue(goodsAction.getActionErrors().size() > 0);
    }

    @Test 
    public void testBuySuccess() {
        final int goodsId = 1;
        final GoodsCodeModel goodsCode = new GoodsCodeModel();
        
        
        //7、定义预期行为，并在后边来验证预期行为是否正确
        context.checking(new org.jmock.Expectations() {
            {
                //8、表示需要调用goodsCodeService对象的buy方法一次且仅一次
                //且返回goodsCode对象
                one(goodsCodeService).buy("test", goodsId);
                will(returnValue(goodsCode));
            }
        });
        //9、模拟Struts注入请求参数
        goodsAction.setGoodsId(goodsId);
        String actualResultCode = goodsAction.buy();
        context.assertIsSatisfied();
        Assert.assertEquals(ReflectionTestUtils.getField(goodsAction, "BUY_RESULT"), actualResultCode);
        Assert.assertTrue(goodsAction.getActionErrors().size() == 0);
    }
}
