package com.qa.ims.controller;

import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.plaf.InsetsUIResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class OrderItemController implements CrudController<OrderItem>
{

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderItemDAO orderItemDAO;
    private Utils utils;

    public OrderItemController(OrderItemDAO orderItemDAO, Utils utils)
    {
        super();
        this.orderItemDAO = orderItemDAO;
        this.utils = utils;
    }

    /**
     * Reads all orderItems to the logger
     */
    @Override
    public List<OrderItem> readAll()
    {
        List<OrderItem> orderItems = orderItemDAO.readAll();
        for (OrderItem orderItem : orderItems)
        {
            LOGGER.info(orderItem.toString() + " Total Price: " + orderItemDAO.getOrderPrice(orderItem.getOid()));
        }
        return orderItems;
    }

    /**
     * Creates a orderItem by taking in user input
     */
    @Override
    public OrderItem create()
    {
        LOGGER.info("Please enter a customer ID");
        Long cid = utils.getLong();
        LOGGER.info("Please enter an item ID(s) [Eg 12,1,2,35]");
        String idd = utils.getString();
        List<Long> idds = new ArrayList<>();
        try
        {
            Arrays.stream(idd.split(",")).forEach(s ->
            {
                idds.add(Long.parseLong(s));
            });
        }
        catch (Exception e)
        {
            LOGGER.info("Error processing your Order! Returning...");
            return null;
        }
        LOGGER.info("OrderItem created");
        return orderItemDAO.create(new OrderItem(cid, idds));
    }

    /**
     * Updates an existing orderItem by taking in user input
     */
    @Override
    public OrderItem update()
    {
        LOGGER.info("Please enter the id of the Order you would like to update");
        Long id = utils.getLong();
        if(orderItemDAO.readOrder(id)==null)
        {
            LOGGER.info("The order you have entered, does not exist in the System!");
            return null;
        }
        UPDATE_DECISION update_decision = null;
        do
        {
            LOGGER.info("DELETE: Delete an Item from Order");
            LOGGER.info("UPDATE: Update the Product ID");
            LOGGER.info("ADD: Add a Product to Order");
            LOGGER.info("EXIT: Exit this Order");
            String choice = utils.getString();
            try
            {
                switch (UPDATE_DECISION.valueOf(choice.toUpperCase()))
                {
                    case UPDATE:
                        LOGGER.info("Item you would like to replace");
                        Long oldid = utils.getLong();
                        LOGGER.info("Item to replace " + oldid);
                        Long newid = utils.getLong();
                        return orderItemDAO.updateItemFromOrder(id,oldid,newid);
                    case ADD:
                        LOGGER.info("Item you would like to add");
                        Long item = utils.getLong();
                        return orderItemDAO.addItemToOrder(id, item);
                    case DELETE:
                        LOGGER.info("Item you would like to remove");
                        Long removeitem = utils.getLong();
                        return orderItemDAO.deleteItemFromOrder(id, removeitem);
                    case EXIT:
                        update_decision = UPDATE_DECISION.EXIT;
                        break;
                }
            }
            catch (Exception e)
            {
                LOGGER.info("Invalid Input! Returning...");
                return null;
            }
        }while (update_decision!=UPDATE_DECISION.EXIT);
        LOGGER.info("Order Updated");
        return null;
    }

    /**
     * Deletes an existing orderItem by the id of the orderItem
     *
     * @return
     */
    @Override
    public int delete()
    {
        LOGGER.info("Please enter the id of the orderItem you would like to delete");
        Long id = utils.getLong();
        return orderItemDAO.delete(id);
    }

    private enum UPDATE_DECISION
    {
        DELETE,
        UPDATE,
        ADD,
        EXIT;
    }
}
