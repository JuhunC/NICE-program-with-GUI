package application;

public class ConfigBuilder {

	private ConfigBuilder(Builder builder) {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private enum pathType {
			R, Java, unset
		};

		private pathType exePathType = pathType.unset;
		private String executableString = "";

		private Builder() {
		}

		public ConfigBuilder build() {
			return new ConfigBuilder(this);
		}

		public void setRPath(String rPath) throws Exception {
			if (exePathType == pathType.unset) {
				this.executableString = rPath + " CMD BATCH --args";
				this.exePathType = pathType.R;
			} else {
				throw new Exception("Excecutablepath already set");
			}
		}

		public void setJavaPath(String javaPath) throws Exception {
			if (exePathType == pathType.unset) {
				this.executableString = javaPath;
				this.exePathType = pathType.Java;
			} else {
				throw new Exception("Excecutablepath already set");
			}
		}

		public void setROutputFile(String outputPath) throws Exception {
			if (exePathType == pathType.R) {
				executableString += " -- " + outputPath;
			} else {
				throw new Exception("ROutputfile cannot be defined");
			}

		}

		public void addParameter(String parameter, String value) throws Exception {

			switch (exePathType) {
			case Java:
				this.executableString = executableString + " -" + parameter + " " + value;
				break;
			case R:
				this.executableString = executableString + " -" + parameter + "=" + value;
			default:
				throw new Exception("Please set Executablepath first");
			}
		}
	}
}
