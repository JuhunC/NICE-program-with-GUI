package application;

public class ConfigBuilder {

	private String executableString;
	
	private ConfigBuilder(Builder builder) {
		this.executableString = builder.executableString;
	}

	public String toString() {
		return this.executableString;
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

		public Builder setRPath(String rPath) throws Exception {
			if (exePathType == pathType.unset) {
				this.executableString = rPath + " CMD BATCH --args";
				this.exePathType = pathType.R;
				return this;
			} else {
				throw new Exception("Excecutablepath already set ");
			}
		}

		public Builder setJavaPath(String javaPath) throws Exception {
			if (exePathType == pathType.unset) {
				this.executableString = javaPath;
				this.exePathType = pathType.Java;
				return this;

			} else {
				throw new Exception("Excecutablepath already set");
			}
		}

		public Builder setROutputFile(String outputPath) throws Exception {
			if (exePathType == pathType.R) {
				executableString += " -- " + outputPath;
				return this;

			} else {
				throw new Exception("ROutputfile cannot be defined");
			}

		}

		public Builder addParameter(String parameter, String value) throws Exception {

			switch (exePathType) {
			case Java:
				this.executableString = executableString + " -" + parameter + " " + value;
				return this;

			case R:
				this.executableString = executableString + " -" + parameter + "=" + value;
				return this;

			default:
				throw new Exception("Please set Executablepath first");
			}
		}
	}
}
