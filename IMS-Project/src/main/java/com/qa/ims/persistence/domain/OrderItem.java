package com.qa.ims.persistence.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrderItem
{

    private Long oid, cid;
    private List<Long> iid;

    public OrderItem(Long cid, Long oid)
    {
        this.cid = cid;
        this.oid = oid;
    }

    public OrderItem(Long cid, List<Long> longs)
    {
        this.cid = cid;
        this.iid = longs;
    }

    public OrderItem(Long oid, Long cid, List<Long> longs)
    {
        this.oid = oid;
        this.cid = cid;
        this.iid = longs;
    }

    public OrderItem(Long oid, Long cid, Long... iid)
    {
        this.oid = oid;
        this.cid = cid;
        this.iid = Arrays.asList(iid);
    }

    public Long getOid()
    {
        return oid;
    }

    public Long getCid()
    {
        return cid;
    }

    public List<Long> getIid()
    {
        return iid;
    }

    public void setIid(List<Long> iid)
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
