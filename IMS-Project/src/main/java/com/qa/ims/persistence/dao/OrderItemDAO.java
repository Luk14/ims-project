package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderItemDAO implements Dao<OrderItem>
{

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public OrderItem modelFromResultSet(ResultSet resultSet) throws SQLException
    {
        Long oid = resultSet.getLong("OID");
        Long cid = resultSet.getLong("CID");
        return new OrderItem(cid, oid);
    }

    /**
     * Reads all orderItems from the database
     *
     * @return A list of orderItems
     */
    @Override
    public List<OrderItem> readAll()
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from orders");)
        {
            List<OrderItem> orderItems = new ArrayList<>();
            while (resultSet.next())
            {
                orderItems.add(modelFromResultSet(resultSet));
            }
            return orderItems;
        } catch (SQLException e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public OrderItem readLatest()
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY OID DESC LIMIT 1");)
        {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Creates a order in the database
     *
     * @param orderItem - takes in a order object. id will be ignored
     */
    @Override
    public OrderItem create(OrderItem orderItem)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("INSERT INTO orders (CID) values('" + orderItem.getCid() + "')");
            System.out.println(readLatest().toString());
            Long id = readLatest().getOid();
            orderItem.getIid().forEach(aLong ->
            {
                try
                {
                    statement.executeUpdate("INSERT INTO item_orders (OID, IID) values ('" + (id) + "', '" + aLong + "')");
                } catch (SQLException e)
                {
                    LOGGER.debug(e);
                    LOGGER.error(e.getMessage());
                }
            });
            return readLatest();
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public OrderItem readOrder(Long id)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet1 = statement.executeQuery("SELECT * FROM item_orders WHERE OID = " + id))
        {
            List<Long> items = new ArrayList<>();
            while (resultSet1.next())
            {
                items.add(resultSet1.getLong("IID"));
            }
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders where OID = " + id);
            resultSet.next();
            OrderItem orderItem = modelFromResultSet(resultSet);
            orderItem.setIid(items);
            return orderItem;
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public double getOrderPrice(Long id)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet1 = statement.executeQuery("SELECT * FROM item_orders WHERE OID = " + id))
        {
            List<Long> items = new ArrayList<>();
            while (resultSet1.next())
            {
                items.add(resultSet1.getLong("IID"));
            }
            ItemDAO itemDAO = new ItemDAO();
            return items.stream().map(aLong -> itemDAO.readItem(aLong).getPrice()).reduce(Double::sum).get();
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * Updates a order in the database
     *
     * @param orderItem - takes in a order object, the id field will be used to
     *                  update that order in the database
     * @return
     */
    @Override
    public OrderItem update(OrderItem orderItem)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            orderItem.getIid().forEach(aLong ->
            {
                try
                {
                    statement.executeUpdate("UPDATE item_orders SET IID = '" + aLong + "' WHERE OID = '" + orderItem.getOid() + "'");
                } catch (SQLException e)
                {
                    LOGGER.debug(e);
                    LOGGER.error(e.getMessage());
                }
            });
            return readOrder(orderItem.getOid());
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public OrderItem addItemToOrder(Long oid, Long iid)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("INSERT INTO item_orders (OID, IID) values ('" +oid + "', '" + iid + "')");
            return readOrder(oid);
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public OrderItem deleteItemFromOrder(Long oid, Long iid)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("delete from item_orders where IID = '" + iid + "' AND OID = '" + oid + "'");
            return readOrder(oid);
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public OrderItem updateItemFromOrder(Long oid, Long iid, Long newiid)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("UPDATE item_orders SET IID = '" + newiid + "' WHERE OID = '" + oid + "' AND IID = '" + iid + "'");
            return readOrder(oid);
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a order in the database
     *
     * @param id - id of the order
     */
    @Override
    public int delete(long id)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("delete from orders where OID = " + id);
            statement.executeUpdate("delete from item_orders where OID = " + id);
            return 1;
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }
}
