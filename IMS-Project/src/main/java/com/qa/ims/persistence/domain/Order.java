package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Order
{

    private Long oid, cid;

    public Order(Long oid, Long cid)
    {
        this.oid = oid;
        this.cid = cid;
    }

    public Long getOid()
    {
        return oid;
    }

    public Long getCid()
    {
        return cid;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(oid, order.oid) && Objects.equals(cid, order.cid);
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "oid=" + oid +
                ", cid=" + cid +
                '}';
    }
}
