package cn.javass.point.web.admin.action;

import cn.javass.commons.web.action.BaseAction;
import cn.javass.point.model.GoodsModel;
import cn.javass.point.service.GoodsService;

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

//    public String doUpdate() {//到更新页面
//        if(id == -1) {
//            return ERROR;
//        }
//        GoodsModel model = goodsService.get(id);
//        getValueStack().set(MODEL, model);
//        return UPDATE;
//    }
//    public String update() {//更新修改过的模型对象，然后重定向到列表页面
//        goodsService.update(goods);
//        return REDIRECT;
//    }
//    
//    public String doDelete() {//到删除确认页面
//        if(id == -1) {
//            return ERROR;
//        }
//        GoodsModel model = goodsService.get(id);
//        getValueStack().set(MODEL, model);
//        return DELETE;
//    }
//    public String delete() {//删除指定的模型对象
//        goodsService.delete(id);
//        return REDIRECT;
//    }
    
    //-----------------------
    //字段驱动数据填充
    //-----------------------
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
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
    
}
