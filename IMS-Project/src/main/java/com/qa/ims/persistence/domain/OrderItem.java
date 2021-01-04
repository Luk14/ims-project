package com.qa.ims.persistence.domain;

import java.util.Objects;

public class OrderItem
{

    private Long oid, cid;
    private Long[] iid;

    public OrderItem(Long oid, Long cid, Long... iid)
    {
        this.oid = oid;
        this.cid = cid;
        this.iid = iid;
    }

    public Long getOid()
    {
        return oid;
    }

    public Long getCid()
    {
        return cid;
    }

    public Long[] getIid()
    {
        return iid;
    }

    public void setIid(Long[] iid)
    {
        this.iid = iid;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(oid, orderItem.oid) && Objects.equals(cid, orderItem.cid);
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
