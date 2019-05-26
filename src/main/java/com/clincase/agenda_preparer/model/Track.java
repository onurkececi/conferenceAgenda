package com.clincase.agenda_preparer.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.clincase.agenda_preparer.constans.ConstantValues;

public class Track implements Serializable {

	private static final long serialVersionUID = 6299196045700852231L;

	private List<Conference> conferencesMorning;
	private List<Conference> conferencesAfternoon;
	private int availableDurationMorning;
	private int availableDurationAfterNoon;
	private Calendar morningLastEventTime;
	private Calendar afternoonLastEventTime;

	public Track() {
		try {
			SimpleDateFormat dfMorning = new SimpleDateFormat("HH:mm");
			Date date;
			date = dfMorning.parse(ConstantValues.MORNING_START_TIME);
			morningLastEventTime = Calendar.getInstance();
			morningLastEventTime.setTime(date);

			SimpleDateFormat dfNoon = new SimpleDateFormat("HH:mm");

			date = dfNoon.parse(ConstantValues.AFTERNOON_START_TIME);
			afternoonLastEventTime = Calendar.getInstance();
			afternoonLastEventTime.setTime(date);

			availableDurationMorning = ConstantValues.BEFORE_NOON;
			availableDurationAfterNoon = ConstantValues.AFTER_NOON;

			conferencesMorning = new ArrayList<>();
			conferencesAfternoon = new ArrayList<>();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public String getMorningLastEventTimeAsString() {
		return getTimeAsString(morningLastEventTime).concat("AM");
	}

	public String getAfternoonLastEventTime() {
		return getTimeAsString(afternoonLastEventTime).concat("PM");
	}

	public List<Conference> getConferencesMorning() {
		return conferencesMorning;
	}

	public void setConferencesMorning(List<Conference> conferencesMorning) {
		this.conferencesMorning = conferencesMorning;
	}

	public List<Conference> getConferencesAfternoon() {
		return conferencesAfternoon;
	}

	public void setConferencesAfternoon(List<Conference> conferencesAfternoon) {
		this.conferencesAfternoon = conferencesAfternoon;
	}

	public int getAvailableDurationMorning() {
		return availableDurationMorning;
	}

	public void setAvailableDurationMorning(int availableDurationMorning) {
		this.availableDurationMorning = availableDurationMorning;
	}

	public int getAvailableDurationAfterNoon() {
		return availableDurationAfterNoon;
	}

	public void setAvailableDurationAfterNoon(int availableDurationAfterNoon) {
		this.availableDurationAfterNoon = availableDurationAfterNoon;
	}

	public void setAndCalcualteAvailableDurationMorning(int confTime) {
		morningLastEventTime.add(Calendar.MINUTE, confTime);
		this.availableDurationMorning -= confTime;
	}

	public void setAndCalcualteAvailableDurationAfternoon(int confTime) {
		afternoonLastEventTime.add(Calendar.MINUTE, confTime);
		this.availableDurationAfterNoon -= confTime;
	}

	private String getTimeAsString(Calendar cal) {

		String returnVal = "";

		int hour = cal.get(Calendar.HOUR);
		int min = cal.get(Calendar.MINUTE);

		if (hour < ConstantValues.DOUBLE_DIGIT) {
			returnVal = returnVal.concat(ConstantValues.ZERO_STRING);
		}
		returnVal = returnVal.concat(Integer.toString(hour)).concat(":");

		if (min < ConstantValues.DOUBLE_DIGIT) {
			returnVal = returnVal.concat(ConstantValues.ZERO_STRING);
		}
		returnVal = returnVal.concat(Integer.toString(min));

		return returnVal;
	}

	public void setNetworkingIfRequired() {

		if (availableDurationMorning > 0) {
			Conference conf = new Conference();
			conf.setName("Networking Event");
			conf.setStartTime(getTimeAsString(morningLastEventTime).concat("AM"));
			conferencesMorning.add(conf);
		}

		Conference lunchconf = new Conference();
		lunchconf.setName("Lunch");
		lunchconf.setStartTime("12:00PM");
		conferencesMorning.add(lunchconf);

		if (availableDurationAfterNoon > 0)

		{
			Conference conf = new Conference();
			conf.setName("Networking Event");
			conf.setStartTime(getTimeAsString(afternoonLastEventTime).concat("PM"));
			conferencesAfternoon.add(conf);
		}
	}
}
