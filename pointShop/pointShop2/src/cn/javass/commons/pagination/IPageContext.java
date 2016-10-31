package cn.javass.commons.pagination;

import cn.javass.commons.Constants;

/**
 * 分页上下文环境。用于计算Page。
 *
 * @author Zhang Kaitao
 */
public interface IPageContext<E> {
    
    /** 默认设定每页显示记录数为10 */
    public static final int DEFAULT_PAGE_SIZE = Constants.DEFAULT_PAGE_SIZE;
    
    /** 计算总页数. */
    public int getPageCount();
    
    /** 返回 Page 对象. */
    public Page<E> getPage(int index);
    
    /** 每页显示的记录数量 */
    public int getPageSize();
    
    /** 计算总记录数 */
    public int getTotal();
    
}
