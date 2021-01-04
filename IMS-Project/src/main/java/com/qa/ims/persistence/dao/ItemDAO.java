package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements Dao<Item>
{

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Item modelFromResultSet(ResultSet resultSet) throws SQLException
    {
        Long id = resultSet.getLong("IID");
        String name = resultSet.getString("name");
        double ver = resultSet.getDouble("version");
        double price = resultSet.getDouble("price");
        String description = resultSet.getString("description");
        return new Item(id, name, ver, price, description);
    }

    /**
     * Reads all item from the database
     *
     * @return A list of item
     */
    @Override
    public List<Item> readAll()
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from item");)
        {
            List<Item> item = new ArrayList<>();
            while (resultSet.next())
            {
                item.add(modelFromResultSet(resultSet));
            }
            return item;
        } catch (SQLException e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Item readLatest()
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM item ORDER BY IID DESC LIMIT 1");)
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
     * Creates a item in the database
     *
     * @param item - takes in a item object. id will be ignored
     */
    @Override
    public Item create(Item item)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("INSERT INTO item(name, version, price, description) values('" + item.getName()
                    + "','" + item.getVersion() + "','" + item.getPrice() + "','" + item.getDescription() + "')");
            return readLatest();
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public Item readItem(Long id)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM item where IID = " + id);)
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
     * Updates a item in the database
     *
     * @param item - takes in a item object, the id field will be used to
     *             update that item in the database
     * @return
     */
    @Override
    public Item update(Item item)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.executeUpdate("update item set name ='" + item.getName() + "', version ='"
                    + item.getVersion() + "', price ='" + item.getPrice() + "', description ='" + item.getDescription() + "' where IID =" + item.getId());
            return readItem(item.getId());
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a item in the database
     *
     * @param id - id of the item
     */
    @Override
    public int delete(long id)
    {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();)
        {
            return statement.executeUpdate("delete from item where IID = " + id);
        } catch (Exception e)
        {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }
}
