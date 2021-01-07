package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item
{

    private Long id;
    private String name;
    private double version;
    private double price;
    private String description;

    public Item(String name, double version, double price, String description)
    {
        this.name = name;
        this.version = version;
        this.price = price;
        this.description = description;
    }

    public Item(Long id, String name, double version, double price, String description)
    {
        this.id = id;
        this.name = name;
        this.version = version;
        this.price = price;
        this.description = description;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getVersion()
    {
        return version;
    }

    public void setVersion(double version)
    {
        this.version = version;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Double.compare(item.version, version) == 0 && Double.compare(item.price, price) == 0 && Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, version, price, description);
    }

    @Override
    public String toString()
    {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
