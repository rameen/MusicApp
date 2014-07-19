// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.basicmusicapp.network;


public class RestException extends Exception
{

    protected int httpStatusCode;

    public RestException(int i, String s)
    {
        super(s);
        httpStatusCode = i;
    }

    public RestException(String message) {
    	super(message);
	}

	public int getHttpStatusCode()
    {
        return httpStatusCode;
    }
}
