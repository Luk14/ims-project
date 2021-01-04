package com.qa.ims;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CrudController;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMS
{

    public static final Logger LOGGER = LogManager.getLogger();

    private final CustomerController customers;
    private final ItemController item;
    private final Utils utils;

    public IMS()
    {
        this.utils = new Utils();
        final CustomerDAO custDAO = new CustomerDAO();
        final ItemDAO itemDAO = new ItemDAO();
        this.customers = new CustomerController(custDAO, utils);
        this.item = new ItemController(itemDAO, utils);
    }

    public void imsSystem()
    {
        for (int i = 1; i <= 3; i++)
        {
            LOGGER.info("What is your username");
            String username = utils.getString();
            LOGGER.info("What is your password");
            String password = utils.getString();
            DBUtils.connect(username, password);
            if (DBUtils.getInstance().testConnection())
            {
                LOGGER.info("Successfully logged to the database!");
                break;
            }
            if (i == 3)
            {
                LOGGER.info("Failed to login Several times! Program Terminating");
                System.exit(-1);
            }
            LOGGER.info("Failed to login to Database, please try again! " + (3 - i) + " tries remaining!");
        }
        Domain domain = null;
        do
        {
            LOGGER.info("Which entity would you like to use?");
            Domain.printDomains();

            domain = Domain.getDomain(utils);
            boolean changeDomain = false;
            do
            {

                CrudController<?> active = null;
                switch (domain)
                {
                    case CUSTOMER:
                        active = this.customers;
                        break;
                    case ITEM:
                        active = this.item;
                        break;
                    case ORDER:
                        active = null;
                        break;
                    case STOP:
                        domain = Domain.STOP;
                        return;
                    default:
                        break;
                }

                LOGGER.info("What would you like to do with " + domain.name().toLowerCase() + ":");

                Action.printActions();
                Action action = Action.getAction(utils);

                if (action == Action.RETURN)
                {
                    changeDomain = true;
                }
                else
                {
                    doAction(active, action);
                }
            } while (!changeDomain);
        } while (domain != Domain.STOP);
    }

    public void doAction(CrudController<?> crudController, Action action)
    {
        switch (action)
        {
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
