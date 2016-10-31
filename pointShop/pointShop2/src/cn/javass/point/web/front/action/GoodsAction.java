package cn.javass.point.web.front.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.javass.commons.web.action.BaseAction;
import cn.javass.point.exception.NotCodeException;
import cn.javass.point.model.GoodsCodeModel;
import cn.javass.point.service.GoodsCodeService;
import cn.javass.point.service.GoodsService;

@Controller("/front/goodsAction")
@Scope("prototype")
public class GoodsAction extends BaseAction {

	private static final long serialVersionUID = -3014331095548711061L;
	private static final String BUY_RESULT = "buyResult";
    
    public String list() {
        getValueStack().set(PAGE, goodsService.listAllPublished(getPn()));
        return LIST;
    }
    
    public String buy() {
        String username = "test";
        GoodsCodeModel goodsCode = null;
        try {
            goodsCode = goodsCodeService.buy(username, goodsId);
        } catch (NotCodeException e) {
            this.addActionError("没有足够的兑换码了");
            return BUY_RESULT;
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError("未知错误");
            return BUY_RESULT;
        }
        this.addActionMessage("购买成功，您的兑换码为 :"+ goodsCode.getCode());
        getValueStack().set(MODEL, goodsCode);
        return BUY_RESULT;
    }
    
    //-----------------------
    //字段驱动数据填充
    //-----------------------    
    private int goodsId;
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    
    //-----------------------
    //依赖注入
    //-----------------------
    GoodsService goodsService;
    GoodsCodeService goodsCodeService;
    @Autowired @Required
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
    @Autowired @Required
    public void setGoodsCodeService(GoodsCodeService goodsCodeService) {
        this.goodsCodeService = goodsCodeService;
    }
    
}
