package cn.javass.point.dao;

import java.util.List;

import cn.javass.commons.Constants;
import cn.javass.commons.dao.hibernate.BaseHibernateDao;
import cn.javass.point.model.GoodsModel;

public class GoodsDao extends BaseHibernateDao<GoodsModel, Integer> {
   
     
    @Override //覆盖掉父类的delete方法，不进行物理删除
    public void delete(Integer id) {
        GoodsModel goods = get(id);
        goods.setDeleted(true);
        update(goods);
    }
    
    @Override //覆盖掉父类的getCountAllHql方法，查询不包括逻辑删除的记录
    protected String getCountAllHql() {
        return super.getCountAllHql() + " where deleted=false";
    }
    
    @Override //覆盖掉父类的getListAllHql方法，查询不包括逻辑删除的记录
    protected String getListAllHql() {
        return super.getListAllHql() + " where deleted=false";
    }

   //统计没有被逻辑删除的且发布的商品数量
    public int countAllPublished() {
        String hql = getCountAllHql() + " and published=true";
        Number result = unique(hql);
        return result.intValue();
    }
    
    //查询没有被逻辑删除的且发布的商品
    public List<GoodsModel> listAllPublished(int pn) {
        String hql = getListAllHql() + " and published=true";
        return list(hql, pn, Constants.DEFAULT_PAGE_SIZE);
    }
}
