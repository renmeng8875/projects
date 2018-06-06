package com.babytree.commons.protopack.exception;
/**
 * 解包异常类
 * @author: renmeng
 * @date: 2018/5/31
 */
public class UnpackException extends RuntimeException
{
    public static final long serialVersionUID = 12L;

    public UnpackException()
    {
        this( "Unpack error" );
    }

    public UnpackException( String message )
    {
        super( message );
    }

    public UnpackException( Throwable cause )
    {
        super( cause );
    }

    public UnpackException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
