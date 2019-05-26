package com.clincase.agenda_preparer;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.SortedMap;
import java.util.Vector;

import org.junit.Test;

import com.clincase.agenda_preparer.management.AgendaBusiness;
import com.clincase.agenda_preparer.model.Conference;
import com.clincase.agenda_preparer.model.Track;

public class AppTest {

	public final static int ZERO = 0;
	public final static int ONE = 1;
	public final static int THREE = 3;
	public final static int FIVE = 5;
	public final static int SIXTY = 60;
	public final static String NOON_TIME_STRING = "12:00PM";
	public final static String NETWORKING_EVENT_STRING = "Networking Event";
	public final static String LUNCH_EVENT_STRING = "Lunch";

	public String exampleInput;

	public AgendaBusiness agendaInputOperator;

	public void setInitialEnvironment(int operationType) {

		switch (operationType) {
		case 1:
			exampleInput = "Writing Fast Tests Using Selenium 60min";
			break;
		case 2:
			exampleInput = "Writing Fast Tests Using Selenium lightning";
			break;
		case 3:
			exampleInput = "What&#39;s New With Java 11 30min";
			break;

		default:
			exampleInput = "";
			break;
		}
	}

	@Test
	public void InitialTestWithOneLine() {
		agendaInputOperator = new AgendaBusiness();

		setInitialEnvironment(1);
		agendaInputOperator.handleConferenceDetails(exampleInput);
		assertEquals(ONE, AgendaBusiness.bulkConference.size());
		assertEquals(SIXTY, AgendaBusiness.totalConfDuration);

		agendaInputOperator = null;
	}

	@Test
	public void testLightningIsFiveMin() {
		agendaInputOperator = new AgendaBusiness();

		setInitialEnvironment(2);
		agendaInputOperator.handleConferenceDetails(exampleInput);

		SortedMap<Integer, Vector<Conference>> tempMap = AgendaBusiness.bulkConference;

		Vector<Conference> confVector = tempMap.get(5);

		assertEquals(FIVE, confVector.get(0).getDuration());
		assertEquals(exampleInput, confVector.get(0).getName());

		agendaInputOperator = null;
	}

	@Test
	public void testNetworkingEvent() {
		agendaInputOperator = new AgendaBusiness();

		setInitialEnvironment(2);
		agendaInputOperator.handleConferenceDetails(exampleInput);
		agendaInputOperator.processTrack();

		List<Track> trackList = agendaInputOperator.trackList;

		assertEquals(ONE, trackList.size());
		Track track = trackList.get(0);
		List<Conference> confList = track.getConferencesMorning();
		assertEquals(THREE, confList.size());
		assertEquals(NETWORKING_EVENT_STRING, confList.get(1).getName());

		agendaInputOperator = null;
	}

	@Test
	public void testLunchEventOccurs() {
		agendaInputOperator = new AgendaBusiness();

		setInitialEnvironment(2);
		agendaInputOperator.handleConferenceDetails(exampleInput);
		agendaInputOperator.processTrack();

		List<Track> trackList = agendaInputOperator.trackList;

		assertEquals(ONE, trackList.size());
		Track track = trackList.get(0);
		List<Conference> confList = track.getConferencesMorning();
		assertEquals(THREE, confList.size());
		assertEquals(LUNCH_EVENT_STRING, confList.get(2).getName());
		assertEquals(NOON_TIME_STRING, confList.get(2).getStartTimeAsString());

		agendaInputOperator = null;
	}

	@Test
	public void testConferenceNameContainsNumbers() {
		agendaInputOperator = new AgendaBusiness();

		setInitialEnvironment(2);
		agendaInputOperator.handleConferenceDetails(exampleInput);

		assertEquals(ONE, agendaInputOperator.bulkConference.size());

		agendaInputOperator = null;
	}

}
