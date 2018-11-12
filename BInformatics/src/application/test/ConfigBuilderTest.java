package application.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import application.ConfigBuilder;
import application.ProgramExecutor;

class ConfigBuilderTest {

	@Test
	void testRBuildup() {
		// TESTDATA
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
		String toTest = rPath + " CMD BATCH --args" + " -snp=" + snp + " -pheno=" + pheno + " -snpNum=" + snpNum
				+ " -phenoNum=" + phenoNum + " -- " + rOutputPath;
		assertEquals(toTest, configBuilder.toString());
	}

	@Test
	void testDefaultPath() {

		System.out.println(javaExe());
		try {
			Process process = Runtime.getRuntime().exec("where java");

			Scanner kb = new Scanner(process.getInputStream());
			System.out.println(kb.nextLine());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String javaExe() {
		final String JAVA_HOME = System.getProperty("java.home");
		final File BIN = new File(JAVA_HOME, "bin");
		File exe = new File(BIN, "java");

		if (!exe.exists()) {
			// We might be on Windows, which needs an exe extension
			exe = new File(BIN, "java.exe");
		}

		if (exe.exists()) {
			return exe.getAbsolutePath();
		}

		try {
			// Just try invoking java from the system path; this of course
			// assumes "java[.exe]" is /actually/ Java
			final String NAKED_JAVA = "java";
			new ProcessBuilder(NAKED_JAVA).start();

			return NAKED_JAVA;
		} catch (IOException e) {
			return null;
		}
	}

	@Test void testProgress() throws IOException {
		String path = "E:\\Developement\\Korea\\Bioinformatics\\NICE\\NICE\\inputMS.Rout";
		System.out.println("Workingdir: "+path);
		List<String> readStatusLines = null;
		readStatusLines = Files.readAllLines(Paths.get(path));
	
		if (!readStatusLines.isEmpty()) {
		String progressString = "0";
		int progress = 0;
		try {

			progressString = readStatusLines.get(readStatusLines.size() - 1).substring(4);

			progress = Integer.parseInt(progressString);
		} catch (NumberFormatException|IndexOutOfBoundsException e) {
			// do nothing
		}
		System.out.println(progress);
	}

	}
}
