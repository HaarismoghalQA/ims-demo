package com.qa.ims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;

import com.qa.ims.persistence.dao.CustomerDaoMysql;

import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.services.CustomerServices;

import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;

public class IMS {

	public static final Logger LOGGER = LogManager.getLogger();

	public void imsSystem() {
		LOGGER.info("What is your username");
		String username = Utils.getInstance().getInput();
		LOGGER.info("What is your password");
		String password = Utils.getInstance().getInput();

		DBUtils.getInstance(username, password);
		boolean stop = false;
		do {
			LOGGER.info("Which entity would you like to use?");
			Domain.printDomains();

			Domain domain = Domain.getDomain();

			LOGGER.info("What would you like to do with " + domain.name().toLowerCase() + ":");

			Action.printActions();
			Action action = Action.getAction();

			switch (domain) {
			case CUSTOMER:
				CustomerController customerController = new CustomerController(
						new CustomerServices(new CustomerDaoMysql()));
				doAction(customerController, action);
				break;
			case ITEM:
				
				
				break;
			case ORDER:
				break;
			case STOP:
				stop = true;
				break;
			default:
				break;
			}
		} while (!stop);
		LOGGER.info("SO LONG!");
	}

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}

}