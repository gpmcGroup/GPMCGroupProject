package com.gpmc.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.junit.Test;

import com.sun.xml.internal.ws.util.xml.XmlUtil;


public class xmlUtilTest {

	@Test
	public void getTopicFilepathtest() {
		assertEquals("/F:/GPMCGroupProject/build/classes/..\\Is_Abortion_is_wrong\\Topic.xml",xmlUtil.getTopicFilePath("Is_Abortion_is_wrong","Topic"));
		assertEquals("/F:/GPMCGroupProject/build/classes/..\\Is_Abortion_is_wrong\\report.pdf",xmlUtil.getTopicFilePath("Is_Abortion_is_wrong","report.pdf" ));
		assertEquals("/F:/GPMCGroupProject/build/classes/..\\Is_Abortion_is_wrong\\material",xmlUtil.getTopicFilePath("Is_Abortion_is_wrong","material" ));
	}
	
	@Test
	public void Userlogintest() throws DocumentException {
		assertEquals("12345",xmlUtil.Userlogin("frank"));
	}
	
	@Test
	public void getUserDetailtest() throws DocumentException {
		assertEquals(9,xmlUtil.getUserDetail("frank").elements().size());
	}
	
	@Test
	public void modifyUserValuetest() throws DocumentException {
		assertEquals("12345",xmlUtil.Userlogin("Test3"));
		xmlUtil.modifyUserValue("Test3", "password", "54321");
		assertEquals("54321",xmlUtil.Userlogin("Test3"));
		xmlUtil.modifyUserValue("Test3", "password", "12345");
	}
	
}
