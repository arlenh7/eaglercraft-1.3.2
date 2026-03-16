package net.minecraft.src;

public class PanelCrashReport {

	private final String reportText;

	public PanelCrashReport(CrashReport par1CrashReport) {
		this.reportText = par1CrashReport != null ? par1CrashReport.getCompleteReport() : "";
	}

	public String getReportText() {
		return reportText;
	}
}
