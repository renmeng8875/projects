package cn.javass.point.service;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import cn.javass.commons.Constants;
import cn.javass.commons.pagination.Page;
import cn.javass.commons.pagination.PageUtil;
import cn.javass.commons.service.impl.BaseServiceImpl;
import cn.javass.point.dao.GoodsCodeDao;
import cn.javass.point.exception.NotCodeException;
import cn.javass.point.model.GoodsCodeModel;
import cn.javass.point.model.GoodsModel;

public class GoodsCodeService extends BaseServiceImpl<GoodsCodeModel, Integer> {
   
    private GoodsService goodsService;
    
    public Page<GoodsCodeModel> listAllByGoods(int pn, int goodsId) {
        Integer count = getGoodsCodeDao().countAllByGoods(goodsId);
        List<GoodsCodeModel> items = getGoodsCodeDao().listAllByGoods(pn, goodsId);
        return PageUtil.getPage(count, pn, items, Constants.DEFAULT_PAGE_SIZE);
    }
    
    private GoodsCodeDao getGoodsCodeDao() {
        return (GoodsCodeDao) getDao();
    }

    public void save(int goodsId, String[] codes) {
        GoodsModel goods = goodsService.get(goodsId);
        for(String code : codes) {
            if(StringUtils.hasText(code)) {
                GoodsCodeModel goodsCode = new GoodsCodeModel();
                goodsCode.setCode(code);
                goodsCode.setGoods(goods);
                save(goodsCode);
            }
        }
    }
    
    public GoodsCodeModel buy(String username, int goodsId) throws NotCodeException {
        //1、验证用户积分是否充足
        //2、其他逻辑判断
        //3、记录交易记录开始
        GoodsCodeModel goodsCode = getGoodsCodeDao().getOneNotExchanged(goodsId);
        if(goodsCode == null) {
            //3、记录交易记录失败
            throw new NotCodeException();
        }
        goodsCode.setExchanged(true);
        goodsCode.setExchangeTime(new Date());
        goodsCode.setUsername(username);
        save(goodsCode);
        //3、记录交易记录成功
        return goodsCode;
    }

    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
}
