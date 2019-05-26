package com.clincase.agenda_preparer.service;

import com.clincase.agenda_preparer.model.Conference;

public interface AgendaInputService {

	void insertConfernce2BulkConference(Conference conf, int duration);

	int getDuration(String lastWord);

	boolean conferenceTitleContainNumber(String conferenceTitle);
}
