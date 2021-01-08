package com.qa.ims;

import com.qa.ims.controller.Action;
import com.qa.ims.controller.CustomerController;
import com.qa.ims.controller.ItemController;
import com.qa.ims.controller.OrderItemController;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class IMSTest
{

    @Mock
    private CustomerController controller;

    @Mock
    private ItemController itemController;

    @Mock
    private OrderItemController orderItemController;

    @Mock
    private Utils utils;

    @InjectMocks
    private IMS ims;

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
    public void testIMSSystemDatabaseFail()
    {
        Mockito.when(utils.getString()).thenReturn("root", "a");
        assertNull(ims.imsSystem());
    }

    @Test
    public void testIMSSystemCustomer()
    {
        Mockito.when(utils.getString()).thenReturn("root", "", "CUSTOMER", "RETURN", "STOP");
        assertEquals(controller, ims.imsSystem());
    }

    @Test
    public void testIMSSystemItem()
    {
        Mockito.when(utils.getString()).thenReturn("root", "", "ITEM", "RETURN", "STOP");
        assertEquals(itemController, ims.imsSystem());
    }
    @Test
    public void testIMSSystemOrder()
    {
        Mockito.when(utils.getString()).thenReturn("root", "", "ORDER", "RETURN", "STOP");
        assertEquals(orderItemController, ims.imsSystem());
    }

    @Test
    public void testIMSSystemStop()
    {
        Mockito.when(utils.getString()).thenReturn("root", "", "STOP");
        assertNull(ims.imsSystem());
    }


    @Test
    public void testdoActionCreate()
    {
        Action action = Action.CREATE;
        assertEquals(action, ims.doAction(controller, Action.CREATE));
    }

    @Test
    public void testdoActionRead()
    {
        Action action = Action.READ;
        assertEquals(action, ims.doAction(controller, Action.READ));
    }

    @Test
    public void testdoActionUpdate()
    {
        Action action = Action.UPDATE;
        assertEquals(action, ims.doAction(controller, Action.UPDATE));
    }
    @Test
    public void testdoActionDelete()
    {
        Action action = Action.DELETE;
        assertEquals(action, ims.doAction(controller, Action.DELETE));
    }

    @Test
    public void testdoActionReturn()
    {
        Action action = Action.RETURN;
        assertEquals(action, ims.doAction(controller, Action.RETURN));
    }



}
