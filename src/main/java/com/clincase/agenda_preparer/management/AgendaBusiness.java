package com.clincase.agenda_preparer.management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.clincase.agenda_preparer.constans.ConstantValues;
import com.clincase.agenda_preparer.constans.Messages;
import com.clincase.agenda_preparer.model.Conference;
import com.clincase.agenda_preparer.model.Track;
import com.clincase.agenda_preparer.service.AgendaInputOperator;
import com.clincase.agenda_preparer.service.AgendaInputService;
import com.clincase.agenda_preparer.service.ConferenceTrackOperator;
import com.clincase.agenda_preparer.service.ConferenceTrackService;

public class AgendaBusiness implements AgendaService {

	private final Logger logger = Logger.getLogger(AgendaBusiness.class);

	AgendaInputService agendaInput;
	ConferenceTrackService conferenceTrack;

	public static SortedMap<Integer, Vector<Conference>> bulkConference;
	public static List<Track> trackList;
	public static int totalConfDuration;

	public AgendaBusiness() {
		bulkConference = new TreeMap<Integer, Vector<Conference>>(Collections.reverseOrder());
		trackList = new ArrayList<Track>();
		totalConfDuration = 0;

		agendaInput = new AgendaInputOperator();
		conferenceTrack = new ConferenceTrackOperator();
	}

	@Override
	public void prepareAgenda(String fileName) {

		try (BufferedReader conferenceDetails = Files.newBufferedReader(Paths.get(fileName))) {

			String conferenceDetail;
			boolean isFirstLine = true;
			int totalLines = 1;

			while ((conferenceDetail = conferenceDetails.readLine()) != null && totalLines > 0) {

				if (isFirstLine) {
					totalLines = Integer.parseInt(conferenceDetail.trim());
					isFirstLine = false;
				} else {
					handleConferenceDetails(conferenceDetail.trim());
					--totalLines;
				}
			}

			if (totalLines > ConstantValues.ZERO) {
				logger.debug(Messages.CONF_SIZE_AND_CONF_VALUES_NOT_MATCH);
			}

			processTrack();
			exportAndDisplaySchedule();

		} catch (IOException e) {
			logger.error(Messages.UNRECOGNIZED_INPUT_FILE);
		}
	}

	public void handleConferenceDetails(String cmd) {

		try {
			if (cmd == null) {
				logger.debug(Messages.EMPTY_LINE);

			} else if (!agendaInput.conferenceTitleContainNumber(cmd)) {

				int conferenceDuration = agendaInput
						.getDuration(cmd.substring(cmd.lastIndexOf(ConstantValues.SPACE_CHAR) + 1));

				if (conferenceDuration == ConstantValues.ERROR_CODE) {
					return;
				}

				Conference conference = new Conference();
				conference.setDuration(conferenceDuration);
				totalConfDuration += conferenceDuration;
				conference.setName(cmd);
				agendaInput.insertConfernce2BulkConference(conference, conferenceDuration);

			} else {
				logger.debug(Messages.TITLE_CONTAIN_NUMBERS.concat(cmd));
			}

		} catch (Exception e) {
			logger.error(Messages.ERROR_GENERAL, e);
		}
	}

	public void processTrack() {
		while (bulkConference.size() != ConstantValues.ZERO) {
			Track track = new Track();
			conferenceTrack.fillConferencePerTrack(track);
			trackList.add(track);
		}
	}

	private void exportAndDisplaySchedule() {

		int trackCounter = 1;
		StringBuilder result = new StringBuilder();

		for (Track track : trackList) {

			result.append("Track ").append(trackCounter).append(ConstantValues.NEW_LINE)
					// for each track display the schedule
					.append(appendConference(track.getConferencesMorning()))
					.append(appendConference(track.getConferencesAfternoon())).append(ConstantValues.NEW_LINE);

			++trackCounter;
		}

		String agenda = result.toString();
		logger.debug(agenda);
		writeToFile(agenda);
	}

	private String appendConference(List<Conference> conferences) {

		StringBuilder confResults = new StringBuilder();
		for (Conference conference : conferences) {
			confResults.append(conference.getStartTimeAsString()).append(ConstantValues.SPACE_CHAR)
					.append(conference.getName()).append(ConstantValues.NEW_LINE);

		}
		return confResults.toString();
	}

	private void writeToFile(String context) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ConstantValues.OUTPUT_FILE_NAME))) {
			writer.write(context);
		} catch (IOException e) {
			logger.error(Messages.FILE_WRITE_ERROR);
		}
	}
}