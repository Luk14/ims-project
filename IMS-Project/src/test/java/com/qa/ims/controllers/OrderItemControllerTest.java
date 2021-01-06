package com.qa.ims.controllers;

import com.qa.ims.controller.OrderItemController;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemControllerTest
{

    @Mock
    private Utils utils;

    @Mock
    private OrderItemDAO dao;

    @InjectMocks
    private OrderItemController controller;

    @Test
    public void testCreate()
    {
        final String OIDs = "1,2";
        final Long CID = 1L;
        final OrderItem orderItem = new OrderItem(CID, Arrays.asList(1L, 2L));

        Mockito.when(utils.getLong()).thenReturn(CID);
        Mockito.when(utils.getString()).thenReturn(OIDs);
        Mockito.when(dao.create(any())).thenReturn(orderItem);

        assertEquals(orderItem, controller.create());

        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(utils, Mockito.times(1)).getLong();
        Mockito.verify(dao, Mockito.times(1)).create(orderItem);
    }

    @Test
    public void testReadAll()
    {
        List<OrderItem> items = new ArrayList<>();
        final Long CID = 1L;
        final OrderItem orderItem = new OrderItem(1L, CID, 1L, 2L);
        items.add(orderItem);

        Mockito.when(dao.readAll()).thenReturn(items);

        assertEquals(items, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

    @Test
    public void testUpdateFailInvalidID()
    {
        Mockito.when(utils.getLong()).thenReturn(352L);

        assertNull(controller.update());

        Mockito.verify(utils, Mockito.times(1)).getLong();
    }

    @Test
    public void testUpdateDecision()
    {
        OrderItem updated = new OrderItem(1L, 1L, 1L, 4L);

        Mockito.when(utils.getLong()).thenReturn(1L, 2L, 4L);
        Mockito.when(utils.getString()).thenReturn("UPDATE");
        Mockito.when(dao.updateItemFromOrder(1L, 2L, 4L)).thenReturn(updated);
        Mockito.when(dao.readOrder(1L)).thenReturn(updated);

        assertEquals(updated, controller.update());

        Mockito.verify(utils, Mockito.times(3)).getLong();
        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(dao, Mockito.times(1)).updateItemFromOrder(1L, 2L, 4L);
    }

    @Test
    public void testAddDecision()
    {
        OrderItem updated = new OrderItem(1L, 1L, 1L, 4L);

        Mockito.when(utils.getLong()).thenReturn(1L, 4L);
        Mockito.when(utils.getString()).thenReturn("ADD");
        Mockito.when(dao.addItemToOrder(1L, 4L)).thenReturn(updated);
        Mockito.when(dao.readOrder(1L)).thenReturn(updated);

        assertEquals(updated, controller.update());

        Mockito.verify(utils, Mockito.times(2)).getLong();
        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(dao, Mockito.times(1)).addItemToOrder(1L, 4L);
    }

    @Test
    public void testDeleteDecision()
    {
        OrderItem updated = new OrderItem(1L, 1L, Arrays.asList());

        Mockito.when(utils.getLong()).thenReturn(1L, 1L);
        Mockito.when(utils.getString()).thenReturn("DELETE");
        Mockito.when(dao.deleteItemFromOrder(1L, 1L)).thenReturn(updated);
        Mockito.when(dao.readOrder(1L)).thenReturn(updated);

        assertEquals(updated, controller.update());

        Mockito.verify(utils, Mockito.times(2)).getLong();
        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(dao, Mockito.times(1)).deleteItemFromOrder(1L, 1L);
    }

    @Test
    public void testDelete()
    {
        final long ID = 1L;

        Mockito.when(utils.getLong()).thenReturn(ID);
        Mockito.when(dao.delete(ID)).thenReturn(1);

        assertEquals(1L, controller.delete());

        Mockito.verify(utils, Mockito.times(1)).getLong();
        Mockito.verify(dao, Mockito.times(1)).delete(ID);
    }

}
