package com.qa.ims.persistence.dao;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemDAOTest
{

    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

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
        final OrderItem orderItem = new OrderItem(2L, 1L, 1L);
        assertEquals(orderItem, orderItemDAO.create(orderItem));
    }

    @Test
    public void testReadAll()
    {
        List<OrderItem> expected = new ArrayList<>();
        final OrderItem orderItem = new OrderItem(1L, 1L);
        expected.add(orderItem);
        assertEquals(expected, orderItemDAO.readAll());
    }

    @Test
    public void testReadLatest()
    {
        assertEquals(new OrderItem(1L, 1L), orderItemDAO.readLatest());
    }

    @Test
    public void testRead()
    {
        final long ID = 1L;
        final OrderItem orderItem = new OrderItem(ID, 1L, 1L);
        assertEquals(orderItem, orderItemDAO.readOrder(ID));
    }

    @Test
    public void testOrderPrice()
    {
        assertEquals(15.5, orderItemDAO.getOrderPrice(1L), 0);
    }

    @Test
    public void testUpdate()
    {
        OrderItem orderItem = new OrderItem(1L, 1L, 1L);
        assertEquals(orderItem, orderItemDAO.update(orderItem));
    }

    @Test
    public void testAddItemToOrder()
    {
        OrderItem orderItem = new OrderItem(1L, 1L, 1L, 1L);
        assertEquals(orderItem, orderItemDAO.addItemToOrder(1L, 1L));
    }

    @Test
    public void testDeleteItemFromOrder()
    {
        List<Long> emptyList = new ArrayList<>();
        OrderItem orderItem = new OrderItem(1L, 1L, emptyList);
        assertEquals(orderItem, orderItemDAO.deleteItemFromOrder(1L, 1L));
    }

    @Test
    public void testUpdateItemFromOrder()
    {
        OrderItem orderItem = new OrderItem(1L, 1L, 2L);
        assertEquals(orderItem, orderItemDAO.updateItemFromOrder(1L, 1L, 2L));
    }

    @Test
    public void testDelete()
    {
        assertEquals(1, orderItemDAO.delete(1));
    }

}
