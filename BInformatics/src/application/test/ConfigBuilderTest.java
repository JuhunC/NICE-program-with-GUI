package application.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.ConfigBuilder;

class ConfigBuilderTest {

	@Test
	void testRBuildup() {
		//TESTDATA
		String rPath = "E:\\Programme\\R\\R-2.15.1\\bin\\R.exe";
		String snp = "012";
		String pheno = "345";
		String snpNum = "678";
		String phenoNum = "910";
		String rOutputPath = "./test/";
		
		
		ConfigBuilder configBuilder = null;
		try {
			configBuilder = ConfigBuilder.builder().setRPath(rPath).addParameter("snp", snp)
					.addParameter("pheno", pheno).addParameter("snpNum", snpNum).addParameter("phenoNum", phenoNum)
					.setROutputFile(rOutputPath).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String toTest = rPath + " CMD BATCH --args" + " -snp=" + snp + " -pheno=" + pheno + " -snpNum=" + snpNum + " -phenoNum=" + phenoNum + " -- " + rOutputPath;
		assertEquals(toTest, configBuilder.toString());
	}
}
