package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ItemDAOFailTest
{

    private final ItemDAO DAO = new ItemDAO();

    @BeforeClass
    public static void init()
    {
        DBUtils.connect("root", "wrongPassword");
    }

    @Before
    public void setup()
    {
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate()
    {
        final Item item = new Item(2L, "PC", 1.0, 2499.99, "Gaming!");
        assertNull(DAO.create(item));
    }

    @Test
    public void testReadAll()
    {
        List<Item> expected = new ArrayList<>();
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void testReadLatest()
    {
        assertNull(DAO.readLatest());
    }

    @Test
    public void testRead()
    {
        final long ID = 1L;
        assertNull(DAO.readItem(ID));
    }

    @Test
    public void testUpdate()
    {
        final Item updatedItem = new Item(1L, "Computer", 1.0, 20, "Book is for Reading!");
        assertNull(DAO.update(updatedItem));

    }

    @Test
    public void testDelete()
    {
        assertEquals(0, DAO.delete(1));
    }


}
