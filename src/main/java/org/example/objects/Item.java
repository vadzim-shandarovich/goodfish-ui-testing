package org.example.objects;

import java.math.BigDecimal;

/**
 * Describes GoodFish product item
 */
public class Item {
    private String name;
    private BigDecimal price;
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return name.equals(item.name) &&
                price.equals(item.price) &&
                amount.equals(item.amount);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Item name: " + name + "; price: " + price.toString() + "; amount: " + amount.toString();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
