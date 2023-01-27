package com.project.eum.prodtool.model.add;

public class TimeSummary {

	private Long totalWork;
	private Long totalProductive;
	private Long totalNeutral;
	private Long totalNonProductive;
	
	private String totalWorkString;
	private String totalProductiveString;
	private String totalNeutralString;
	private String totalNonProductiveString;
	
	public TimeSummary() {
		
	}

	public Long getTotalWork() {
		return totalWork;
	}

	public void setTotalWork(Long totalWork) {
		this.totalWork = totalWork;
	}

	public Long getTotalProductive() {
		return totalProductive;
	}

	public void setTotalProductive(Long totalProductive) {
		this.totalProductive = totalProductive;
	}

	public Long getTotalNeutral() {
		return totalNeutral;
	}

	public void setTotalNeutral(Long totalNeutral) {
		this.totalNeutral = totalNeutral;
	}

	public Long getTotalNonProductive() {
		return totalNonProductive;
	}

	public void setTotalNonProductive(Long totalNonProductive) {
		this.totalNonProductive = totalNonProductive;
	}

	public String getTotalWorkString() {
		totalWorkString = getHours(totalWork) + "h " + getMinutes(totalWork) + "m";
		return totalWorkString;
	}

	public void setTotalWorkString(String totalWorkString) {
		this.totalWorkString = totalWorkString;
	}

	public String getTotalProductiveString() {
		totalProductiveString = getHours(totalProductive) + "h " + getMinutes(totalProductive) + "m";
		return totalProductiveString;
	}

	public void setTotalProductiveString(String totalProductiveString) {
		this.totalProductiveString = totalProductiveString;
	}

	public String getTotalNeutralString() {
		totalNeutralString = getHours(totalNeutral) + "h " + getMinutes(totalNeutral) + "m";
		return totalNeutralString;
	}

	public void setTotalNeutralString(String totalNeutralString) {
		this.totalNeutralString = totalNeutralString;
	}

	public String getTotalNonProductiveString() {
		totalNonProductiveString = getHours(totalNonProductive) + "h " + getMinutes(totalNonProductive) + "m";
		return totalNonProductiveString;
	}

	public void setTotalNonProductiveString(String totalNonProductiveString) {
		this.totalNonProductiveString = totalNonProductiveString;
	}
	
	private Long getMinutes(Long minutes) {
		return minutes % 60;
	}
	
	private Long getHours(Long minutes) {
		return minutes / 60;
	}
	
	@Override
	public String toString() {
		return "T: " + getTotalWorkString() + "\n"
				+ "P: " + getTotalProductiveString() + "\n"
				+ "OP: " + getTotalNeutralString() + "\n"
				+ "NP: " + getTotalNonProductiveString();
	}
	
}
