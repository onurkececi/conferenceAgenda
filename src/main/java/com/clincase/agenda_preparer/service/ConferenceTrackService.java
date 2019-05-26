package com.clincase.agenda_preparer.service;

import java.util.List;
import java.util.Vector;

import com.clincase.agenda_preparer.model.Conference;
import com.clincase.agenda_preparer.model.Track;

public interface ConferenceTrackService {

	void fillConferencePerTrack(Track track);

	void cleanConfList(Vector<Conference> confList, int counter);

	void cleanBulkConferencebyMinute(List<Integer> keys);
}
