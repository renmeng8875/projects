package cn.javass.point.dao;

import java.util.List;

import org.springframework.util.CollectionUtils;

import cn.javass.commons.Constants;
import cn.javass.commons.dao.hibernate.BaseHibernateDao;
import cn.javass.point.model.GoodsCodeModel;

public class GoodsCodeDao extends BaseHibernateDao<GoodsCodeModel, Integer> {

   //根据商品ID查询该商品的兑换码
    public List<GoodsCodeModel> listAllByGoods(int pn, int goodsId) {
        final String hql = getListAllHql() + " where goods.id = ?";
        return list(hql, pn, Constants.DEFAULT_PAGE_SIZE , goodsId);
    }

   //根据商品ID统计该商品的兑换码数量
    public int countAllByGoods(int goodsId) {
        final String hql = getCountAllHql() + " where goods.id = ?";
        Number result = unique(hql, goodsId);
        return result.intValue();
    }

    //获取一个指定商品的没有被兑换的兑换码
    public GoodsCodeModel getOneNotExchanged(int goodsId) {
        String hql = getListAllHql() + " where goods.id = ? and exchanged=false";
        List<GoodsCodeModel> result = list(hql, goodsId);
        if(CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

}
