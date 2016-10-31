package cn.javass.point.web.admin.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.javass.commons.web.action.BaseAction;
import cn.javass.point.model.GoodsModel;
import cn.javass.point.service.GoodsService;

@Controller("/admin/goodsAction")
@Scope("prototype")
public class GoodsAction extends BaseAction {

	private static final long serialVersionUID = 6881163125759496902L;
	public String list() {//列表、展示所有商品（包括未发布的）
        getValueStack().set(PAGE, goodsService.listAll(getPn()));
        return LIST;
    }
    
    public String doAdd() {//到新增页面
        goods = new GoodsModel();
        getValueStack().set(MODEL, goods);
        return ADD;
    }

    public String add() {//保存新增模型对象
        goodsService.save(goods);
        return REDIRECT;
    }


    private int id = -1;
    private GoodsModel goods;
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setGoods(GoodsModel goods) {
        this.goods = goods;
    }
    
    public GoodsModel getGoods() {
        return goods;
    }
    
    //-----------------------
    //依赖注入
    //-----------------------
    private GoodsService goodsService;
    @Autowired @Required
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
    
}
