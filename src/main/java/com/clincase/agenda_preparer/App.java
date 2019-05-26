package com.clincase.agenda_preparer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.clincase.agenda_preparer.constans.Messages;
import com.clincase.agenda_preparer.management.AgendaBusiness;
import com.clincase.agenda_preparer.management.AgendaService;

/**
 * The software is developed by Onur Kececi (onurkececi@gmail.com) 5/27/2019
 */
public class App {

	private final static Logger logger = Logger.getLogger(App.class);
	private static final int START_ARGUMENT = 0;

	public static void main(String[] args) {

		BasicConfigurator.configure();
		AgendaService aci = new AgendaBusiness();
		
		logger.debug(Messages.INFO_MESSAGE);

		// if condition is true file read is enabled
		if (args.length > START_ARGUMENT) {
			aci.prepareAgenda(args[0]);
		} else {
			// otherwise warn user
			logger.error(Messages.ERROR_FILE_INPUT);
		}
	}
}
