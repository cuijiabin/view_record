package com.jiabin.record.test;

import org.junit.Test;

import com.jiabin.record.run.CombinationExtract;

public class ViewRecordTest {

	@Test
	public void testRunKeys() throws Exception {
		CombinationExtract.extractKeys("10");
	}
	
	@Test
	public void testRunSql() throws Exception {
		CombinationExtract.extractRun("10");
	}
}
