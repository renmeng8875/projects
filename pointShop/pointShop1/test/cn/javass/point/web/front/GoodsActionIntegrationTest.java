package cn.javass.point.web.front;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts2.StrutsSpringTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.javass.point.model.GoodsModel;
import cn.javass.point.web.front.action.GoodsAction;

import com.opensymphony.xwork2.ActionContext;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({})
public class GoodsActionIntegrationTest extends StrutsSpringTestCase {
    
    @Override
    protected String getContextLocations() {
        return "classpath:applicationContext-test.xml";
    }
    
    @Before
    public void setUp() throws Exception {
        //1 指定Struts2配置文件
        //该方式等价于通过web.xml中的<init-param>方式指定参数
        Map<String, String> dispatcherInitParams = new HashMap<String, String>();
        ReflectionTestUtils.setField(this, "dispatcherInitParams", dispatcherInitParams);
        //1.1 指定Struts配置文件位置
        dispatcherInitParams.put("config", "struts-default.xml,struts-plugin.xml,struts.xml");
        super.setUp();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testBuyFail() throws UnsupportedEncodingException, ServletException {
        //2 前台购买商品失败
        //2.1 首先重置hhtp相关对象，并准备准备请求参数
        initServletMockObjects();
        request.setParameter("goodsId", String.valueOf(Integer.MIN_VALUE));
        //2.2 调用前台GoodsAction的buy方法完成购买相应商品的Code码
        executeAction("/goods/buy.action");
        GoodsAction frontGoodsAction = (GoodsAction) ActionContext.getContext().getActionInvocation().getAction();
        //2.3 验证前台GoodsAction的buy方法有错误
        Assert.assertTrue(frontGoodsAction.getActionErrors().size() > 0);
    }
    

    @Test
    public void testBuySuccess() throws UnsupportedEncodingException, ServletException {
        //3 后台新增商品
        //3.1 准备请求参数
        request.setParameter("goods.name", "测试商品");
        request.setParameter("goods.description", "测试商品描述");
        request.setParameter("goods.originalPoint", "1");
        request.setParameter("goods.nowPoint", "2");
        request.setParameter("goods.published", "true");
        //3.2 调用后台GoodsAction的add方法完成新增
        executeAction("/admin/goods/add.action");
        //2.3 获取GoodsAction的goods属性
        GoodsModel goods = (GoodsModel) findValueAfterExecute("goods");
        //4 后台新增商品Code码
        //4.1 首先重置hhtp相关对象，并准备准备请求参数
        initServletMockObjects();
        request.setParameter("goodsId", String.valueOf(goods.getId()));
        request.setParameter("codes", "a\rb");
        //4.2 调用后台GoodsCodeAction的add方法完成新增商品Code码
        executeAction("/admin/goodsCode/add.action");
        //5 前台购买商品成功
        //5.1 首先重置hhtp相关对象，并准备准备请求参数
        initServletMockObjects();
        request.setParameter("goodsId", String.valueOf(goods.getId()));
        //5.2 调用前台GoodsAction的buy方法完成购买相应商品的Code码
        executeAction("/goods/buy.action");
        GoodsAction frontGoodsAction = (GoodsAction) ActionContext.getContext().getActionInvocation().getAction();
        //5.3 验证前台GoodsAction的buy方法没有错误
        Assert.assertTrue(frontGoodsAction.getActionErrors().size() == 0);
    }

}
