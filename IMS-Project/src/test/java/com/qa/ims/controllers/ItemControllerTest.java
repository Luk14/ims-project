package com.qa.ims.controllers;

import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest
{

    @Mock
    private Utils utils;

    @Mock
    private ItemDAO dao;

    @InjectMocks
    private ItemController controller;

    @Test
    public void testCreate()
    {
        final String NAME = "Books", DESC = "Reading! Reading is Good!";
        final double VER = 1.0, PRICE = 99.99;
        final Item item = new Item(NAME, VER, PRICE, DESC);

        Mockito.when(utils.getString()).thenReturn(NAME, DESC);
        Mockito.when(utils.getDouble()).thenReturn(VER, PRICE);
        Mockito.when(dao.create(item)).thenReturn(item);

        assertEquals(item, controller.create());

        Mockito.verify(utils, Mockito.times(2)).getString();
        Mockito.verify(utils, Mockito.times(2)).getDouble();
        Mockito.verify(dao, Mockito.times(1)).create(item);
    }

    @Test
    public void testReadAll()
    {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Books", 1.0, 99.99, "Reading! Reading is Good!"));

        Mockito.when(dao.readAll()).thenReturn(items);

        assertEquals(items, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

    @Test
    public void testUpdate()
    {
        Item updated = new Item(1L, "Books", 1.6, 10.10, "Reading! Reading is Good!");

        Mockito.when(utils.getLong()).thenReturn(1L);
        Mockito.when(utils.getString()).thenReturn(updated.getName(), updated.getDescription());
        Mockito.when(utils.getDouble()).thenReturn(updated.getVersion(), updated.getPrice());
        Mockito.when(dao.update(updated)).thenReturn(updated);

        assertEquals(updated, controller.update());

        Mockito.verify(utils, Mockito.times(1)).getLong();
        Mockito.verify(utils, Mockito.times(2)).getString();
        Mockito.verify(utils, Mockito.times(2)).getDouble();
        Mockito.verify(dao, Mockito.times(1)).update(updated);
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
