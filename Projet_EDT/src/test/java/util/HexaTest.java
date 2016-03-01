/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package util;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.BeforeClass;
import org.junit.Test;

public class HexaTest {
	public static String msgTxt;
	public static String msgHex;
	public static byte[] msgByte;
	
	@BeforeClass
	public static void init(){
		// abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789&"#'{([-|_\@)]=}
		msgTxt = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789&\"#'{([-|_\\@)]=}";
		msgHex = "6162636465666768696a6b6c6d6e6f707172737475767778797a4142434445464748494a4b4c4d4e4f505152535455565758595a30313233343536373839262223277b285b2d7c5f5c40295d3d7d";
		try {
			msgByte = msgTxt.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void bytesToHex() {
		assertEquals(msgHex, Hexa.bytesToHex(msgByte));
	}

	@Test
	public void bytesToHexUpperCase() {
		assertEquals(msgHex.toUpperCase(), Hexa.bytesToHex_UpperCase(msgByte));
	}
	
	@Test
	public void hexToString() {
		assertEquals(msgTxt, Hexa.hexToString(msgHex));
	}
	
	@Test
	public void stringToHex() {
		assertEquals(msgHex, Hexa.stringToHex(msgTxt));
	}
	
	@Test
	public void bytesToString() {
		assertEquals(msgTxt, Hexa.bytesToString(msgByte));
	}
	
	@Test
	public void hexStringToBytes() {
		assertArrayEquals(msgByte, Hexa.hexToBytes(msgHex));
	}
}
