package cn.javass.commons.web.action;

import cn.javass.commons.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

public class BaseAction extends ActionSupport {
    
    /** 通用Result */
    public static final String INDEX = "index";
    public static final String LIST = "list";
    public static final String REDIRECT = "redirect";
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    /** 模型对象属性名*/
    public static final String MODEL = "model";
    /** 列表模型对象属性名*/
    public static final String PAGE = Constants.DEFAULT_PAGE_NAME;
    
    public static final int DEFAULT_PAGE_SIZE = Constants.DEFAULT_PAGE_SIZE;
    
    
    /** 页码，默认为1 */
    private int pn = 1;
    
    public void setPn(int pn) {
        this.pn = pn;
    }
    
    public int getPn() {
        return pn;
    }
    
    public ActionContext getActionContext() {
        return ActionContext.getContext();
    }

    public ValueStack getValueStack() {
        return getActionContext().getValueStack();
    }

}
