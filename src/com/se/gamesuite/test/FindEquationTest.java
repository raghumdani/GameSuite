/**
 * 
 */
package com.se.gamesuite.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.se.gamesuite.FindEquation;

import junit.framework.TestCase;

/**
 * @author raghumdani
 *
 */
public class FindEquationTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() {
		FindEquation fe = new FindEquation();
		
		//assert statements for validateAnswer() method
		
		//Case with division
		assertEquals("10 * 20 / 10 must be 20", 20, fe.calc("10 * 20 / 10"));
		
	}
	
	@Test
	public void test2() {
		FindEquation fe = new FindEquation();
		//Case with little more numbers
		assertEquals("20 * 5 + 30 * 10 must be 1300", 1300, fe.calc("20 * 5 + 30 * 10"));
	}
	
	@Test
	public void test3() {
		FindEquation fe = new FindEquation();

		//Case with too many numbers -- padded extra spaces.
		assertEquals("900 must be 900", 900, fe.calc("		  900  			"));
	}
	
	@Test
	public void test4() {
		FindEquation fe = new FindEquation();
		
		//Case with too many numbers -- padded extra spaces.
		assertEquals("900 must be 900", 900, fe.calc("900     "));
	}
	
	@Test
	public void test5() {
		FindEquation fe = new FindEquation();
		
		//Now on there are random cases
		assertEquals("1 * 1 must be 1", 1, fe.calc("1 *        1"));
		assertEquals("1 * 2 * 2 * 3 * 4 * 5 * 6 / 10 must be 144", 144, 
				fe.calc("1 * 2 * 2 * 3 * 4 * 5 * 6 / 10"));
	}
	
	@Test
	public void test6() {
		FindEquation fe = new FindEquation();

		//Jumbled cases with specifies un - certainities in input
		assertEquals("1 *  * 2 * 4 must be -1", -1, fe.calc("1 *  * 2 * 4"));
		assertEquals("1 * 2 # 3 ^ 4 must be -1", -1, fe.calc("1 * 2 # 3 ^ 4"));
	}

}
