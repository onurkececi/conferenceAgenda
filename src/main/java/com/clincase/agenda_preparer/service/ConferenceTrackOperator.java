package com.clincase.agenda_preparer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Vector;

import com.clincase.agenda_preparer.constans.ConstantValues;
import com.clincase.agenda_preparer.management.AgendaBusiness;
import com.clincase.agenda_preparer.model.Conference;
import com.clincase.agenda_preparer.model.Track;

public class ConferenceTrackOperator implements ConferenceTrackService {

	@Override
	public void fillConferencePerTrack(Track track) {

		List<Integer> bulkRemoveDurationList = new ArrayList<>();

		for (Entry<Integer, Vector<Conference>> conf : AgendaBusiness.bulkConference.entrySet()) {
			Integer confDuration = conf.getKey();
			Vector<Conference> confList = conf.getValue();

			boolean skipDuration = false;
			int removeConfListCounter = 0;

			while (!skipDuration && confList.size() > removeConfListCounter) {

				if (track.getAvailableDurationMorning() >= confDuration) {
					Conference localconf = confList.get(removeConfListCounter);
					// get the last start time which will be the new event's start time
					String confTime = track.getMorningLastEventTimeAsString();
					localconf.setStartTime(confTime);
					track.getConferencesMorning().add(localconf);
					++removeConfListCounter;
					track.setAndCalcualteAvailableDurationMorning(confDuration);
				} else {
					skipDuration = true;

				}
			}

			cleanConfList(confList, removeConfListCounter);
			removeConfListCounter = 0;

			skipDuration = false;

			while (!skipDuration && confList.size() > removeConfListCounter) {

				if (track.getAvailableDurationAfterNoon() >= confDuration) {
					Conference localconf = confList.get(removeConfListCounter);
					// get the last start time which will be the new event's start time
					localconf.setStartTime(track.getAfternoonLastEventTime());
					track.getConferencesAfternoon().add(localconf);
					++removeConfListCounter;
					track.setAndCalcualteAvailableDurationAfternoon(confDuration);
				} else {
					skipDuration = true;
				}
			}

			cleanConfList(confList, removeConfListCounter);

			if (confList.size() == ConstantValues.ZERO) {
				bulkRemoveDurationList.add(confDuration);
			}
		}

		track.setNetworkingIfRequired();
		cleanBulkConferencebyMinute(bulkRemoveDurationList);
	}

	@Override
	public void cleanConfList(Vector<Conference> confList, int counter) {

		while (counter > ConstantValues.ZERO) {
			confList.remove(ConstantValues.ZERO);
			--counter;
		}
	}

	@Override
	public void cleanBulkConferencebyMinute(List<Integer> keys) {

		for (Integer key : keys) {
			AgendaBusiness.bulkConference.remove(key);
		}
	}

}
