package cn.javass.commons.pagination;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

import cn.javass.commons.Constants;
import cn.javass.commons.pagination.provider.NavigationProviderFactory;

/**
 * 分页导航Tag.
 * @author Zhang Kaitao
 */
public class NavigationTag extends TagSupport {
    static final long serialVersionUID = 2372405317744358833L;
    
    /**
     * ValueStack中的Page<?>翻页对象的变量名
     */
    private String pageName = Constants.DEFAULT_PAGE_NAME;
    
    /**
     * 分页跳转的url地址,此属性必须
     */
    private String url = null;
    
    /**
     * 分页提供者版本
     */
    private String version = "v1";
    
    
    @Override
    public int doStartTag() throws JspException {
        Page<?> page = (Page<?>) getValueStack().findValue(pageName);
        if (page == null) 
            return SKIP_BODY;
        try {
            JspWriter writer = pageContext.getOut();
            writer.print("<form action='"+ url + "' method='post'>");
            String link = NavigationProviderFactory.getNavigationProvider(version).build(page, url);
            writer.print(link);
            writer.print("&nbsp;<input name='pn' size='3'/>");
            writer.print("<input type='submit' value='跳转'/>");
//            writer.print("&nbsp;[共" + page.getContext().getPageCount() + "页]");
            writer.print("</form>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
    
    
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    private ValueStack getValueStack() {
        return ActionContext.getContext().getValueStack();
    }
    
}
