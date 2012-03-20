package com.listenMyApp.util;

import junit.framework.Assert;

import org.junit.Test;

import com.listenMyApp.core.util.ManageKeyUtil;


public class ManageKeyUtilTest {
	
	@Test
	public void generateApiKey() {
		String internalKey = "MY_APP_SAMPLE";
		
		String digest = ManageKeyUtil.createKey("god@heaven.sky", internalKey);
		Assert.assertEquals("dc8dcfcb70d61f67389d8ce6f83d212f4c75", digest);
		
	}
	
	
}
