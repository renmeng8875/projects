package com.babytree.commons.protopack.exception;

/**
 * 打包异常类
 * @author: renmeng
 * @date: 2018/5/31
 */
public class PackException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    

    public PackException()
    {
        this( "PackError" );
    }
    
    public PackException(String message)
    {
    	super(message);
    }
    
    public PackException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PackException(Throwable cause){
    	super(cause);
    }
}
