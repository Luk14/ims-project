package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OrderItemDAOFailTest
{

    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

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
        final OrderItem orderItem = new OrderItem(2L, 1L, 1L);
        assertNull(orderItemDAO.create(orderItem));
    }

    @Test
    public void testReadAll()
    {
        List<OrderItem> expected = new ArrayList<>();
        assertEquals(expected, orderItemDAO.readAll());
    }

    @Test
    public void testReadLatest()
    {
        assertNull(orderItemDAO.readLatest());
    }

    @Test
    public void testRead()
    {
        final long ID = 1L;
        assertNull(orderItemDAO.readOrder(ID));
    }
}
