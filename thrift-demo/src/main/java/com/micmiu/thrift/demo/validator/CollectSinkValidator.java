/*
 * Copyright (c) 2014 yy.com. 
 *
 * All Rights Reserved.
 *
 * This program is the confidential and proprietary information of 
 * YY.INC. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with yy.com.
 */
package com.micmiu.thrift.demo.validator;

import com.yy.ent.mobile.thrift.client.failover.ConnectionValidator;
import org.apache.thrift.transport.TTransport;

/**
 * @author Tingkun Zhang
 */
public class CollectSinkValidator implements ConnectionValidator {

	@Override
	public boolean isValid(TTransport object) {
		return object.isOpen();
	}

}
