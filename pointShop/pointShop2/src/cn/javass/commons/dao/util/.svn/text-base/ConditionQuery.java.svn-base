package cn.javass.commons.dao.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

public class ConditionQuery {

    
    private List<Criterion> criterions = new ArrayList<Criterion>();
    
    public void add(Criterion criterion) {
        criterions.add(criterion);
    }
    
    public void build(Criteria criteria) {
        for(Criterion criterion : criterions) {
            //只对一级有效
            if(criterion.toString().contains(".")) {
                String path = criterion.toString().substring(0, criterion.toString().indexOf("."));
                criteria.createAlias(path, path);
            }
            criteria.add(criterion);
        }
    }
        
}
