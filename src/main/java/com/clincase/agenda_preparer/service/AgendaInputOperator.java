package com.clincase.agenda_preparer.service;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.clincase.agenda_preparer.constans.ConstantValues;
import com.clincase.agenda_preparer.constans.Messages;
import com.clincase.agenda_preparer.management.AgendaBusiness;
import com.clincase.agenda_preparer.model.Conference;

public class AgendaInputOperator implements AgendaInputService {
	
	private final Logger logger = Logger.getLogger(AgendaInputOperator.class);

	@Override
	public void insertConfernce2BulkConference(Conference conf, int duration) {

		if (!AgendaBusiness.bulkConference.containsKey(duration)) {
			Vector<Conference> insertList = new Vector<>();
			insertList.add(conf);
			AgendaBusiness.bulkConference.put(duration, insertList);

		} else {
			Vector<Conference> conflist = AgendaBusiness.bulkConference.get(duration);
			conflist.add(conf);
		}
	}

	@Override
	public int getDuration(String lastWord) {

		try {

			if (lastWord.toUpperCase().contains(ConstantValues.LIGHTNING)) {
				return ConstantValues.FIVE_MIN;

			} else if (lastWord.toUpperCase().contains(ConstantValues.MIN)) {
				return Integer.parseInt(lastWord.replaceAll("\\D+", ConstantValues.EMPTY));
			}
			return ConstantValues.ERROR_CODE;

		} catch (Exception e) {
			logger.debug(Messages.UNRECOGNIZED_DURATION + lastWord);
			return ConstantValues.ERROR_CODE;
		}
	}

	@Override
	public boolean conferenceTitleContainNumber(String conferenceTitle) {
		conferenceTitle = conferenceTitle.substring(ConstantValues.ZERO,
				conferenceTitle.lastIndexOf(ConstantValues.SPACE_CHAR) + 1);
		return conferenceTitle.matches(".*\\d.*");
	}
}
