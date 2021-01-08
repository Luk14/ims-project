package com.qa.ims;

import com.qa.ims.controller.*;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMS
{

    public static final Logger LOGGER = LogManager.getLogger();

    private CustomerController customers;
    private ItemController item;
    private OrderItemController orderItemController;
    private Utils utils;

    public IMS()
    {
        this.utils = new Utils();
        final CustomerDAO custDAO = new CustomerDAO();
        final ItemDAO itemDAO = new ItemDAO();
        final OrderItemDAO orderItemDAO = new OrderItemDAO();
        this.customers = new CustomerController(custDAO, utils);
        this.item = new ItemController(itemDAO, utils);
        this.orderItemController = new OrderItemController(orderItemDAO, utils);
    }

    public CrudController imsSystem()
    {
        for (int i = 1; true; i++)
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
                LOGGER.info("Failed to Login!");
                return null;
            }
            LOGGER.info("Failed to login to Database, please try again! " + (3 - i) + " tries remaining!");
        }
        Domain domain = null;
        CrudController<?> active = null;
        do
        {
            LOGGER.info("Which entity would you like to use?");
            Domain.printDomains();

            domain = Domain.getDomain(utils);
            boolean changeDomain = false;
            do
            {

                switch (domain)
                {
                    case CUSTOMER:
                        active = this.customers;
                        break;
                    case ITEM:
                        active = this.item;
                        break;
                    case ORDER:
                        active = this.orderItemController;
                        break;
                    case STOP:
                        domain = Domain.STOP;
                        return active;
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
        } while (true);
    }

    public Action doAction(CrudController<?> crudController, Action action)
    {
        switch (action)
        {
            case CREATE:
                crudController.create();
                return Action.CREATE;
            case READ:
                crudController.readAll();
                return Action.READ;
            case UPDATE:
                crudController.update();
                return Action.UPDATE;
            case DELETE:
                crudController.delete();
                return Action.DELETE;
            case RETURN:
            default:
                return Action.RETURN;
        }
    }
}
