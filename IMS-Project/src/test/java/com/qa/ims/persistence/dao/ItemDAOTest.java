package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ItemDAOTest
{

    private final ItemDAO itemDAO = new ItemDAO();

    @BeforeClass
    public static void init()
    {
        DBUtils.connect("root", "");
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
        assertEquals(item, itemDAO.create(item));
    }

    @Test
    public void testReadAll()
    {
        List<Item> expected = new ArrayList<>();
        expected.add(new Item(1L, "Books", 1.0, 15.50, "Book is for Reading!"));
        assertEquals(expected, itemDAO.readAll());
    }

    @Test
    public void testReadLatest()
    {
        assertEquals(new Item(1L, "Books", 1.0, 15.50, "Book is for Reading!"), itemDAO.readLatest());
    }

    @Test
    public void testRead()
    {
        final long ID = 1L;
        assertEquals(new Item(ID, "Books", 1.0, 15.50, "Book is for Reading!"), itemDAO.readItem(ID));
    }

    @Test
    public void testUpdate()
    {
        final Item updatedItem = new Item(1L, "Computer", 1.0, 20, "Book is for Reading!");
        assertEquals(updatedItem, itemDAO.update(updatedItem));

    }

    @Test
    public void testDelete()
    {
        assertEquals(1, itemDAO.delete(1));
    }


}
