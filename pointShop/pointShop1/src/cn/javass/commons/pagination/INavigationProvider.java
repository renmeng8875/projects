package cn.javass.commons.pagination;

/**
 * 分页标签提供者
 * @author Zhang Kaitao
 *
 */
public interface INavigationProvider {
    
    public String build(Page<?> page, String url);
    
}
