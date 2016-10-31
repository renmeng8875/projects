package cn.javass.point.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import cn.javass.commons.Constants;
import cn.javass.commons.pagination.Page;
import cn.javass.commons.pagination.PageUtil;
import cn.javass.commons.service.impl.BaseServiceImpl;
import cn.javass.point.dao.GoodsDao;
import cn.javass.point.model.GoodsModel;
@Service
public class GoodsService extends BaseServiceImpl<GoodsModel, Integer> {

    public Page<GoodsModel> listAllPublished(int pn) {
        int count = getGoodsDao().countAllPublished();
        List<GoodsModel> items = getGoodsDao().listAllPublished(pn);
        return PageUtil.getPage(count, pn, items, Constants.DEFAULT_PAGE_SIZE);
    }
    
    public GoodsDao getGoodsDao() {
        return (GoodsDao) getDao();
    }

    @Autowired @Required
    public void setGoodsDao(GoodsDao dao) {
        setDao(dao);
    }
    
}
