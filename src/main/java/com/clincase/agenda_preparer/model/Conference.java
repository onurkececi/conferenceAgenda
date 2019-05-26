package com.clincase.agenda_preparer.model;

import java.io.Serializable;

public class Conference implements Serializable {

	private static final long serialVersionUID = -1508958401586989147L;

	private String name;
	private int duration;
	private String startTime;

	public Conference() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getStartTimeAsString() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
