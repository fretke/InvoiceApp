package Data;

import java.util.Objects;
import java.util.regex.Matcher;

public class RentItem {
    private int rentItemId;
    private String itemName;
    private String measuringUnit;
    private double quantity;
    private double price;
    private double sum;

    public RentItem() {
    }

    public RentItem(String itemName, String measuringUnit, double quantity, double price) {
        this.itemName = itemName;
        this.measuringUnit = measuringUnit;
        this.quantity = quantity;
        this.price = price;
        this.sum = quantity * price;
    }


    public int getRentItemId() {
        return rentItemId;
    }

    public void setRentItemId(int rentItemId) {
        this.rentItemId = rentItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalSum(){
        double totalSum = this.quantity * this.price;
        totalSum = Math.round(totalSum * 100.00) / 100.00;
       return totalSum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void calculateSum(){
        this.sum = Math.round((quantity * price) * 100.00) / 100.00;
    }

    @Override
    public String toString() {
        return  itemName;
    }

    @Override
    public boolean equals(Object first) {
        if (this == first) return true;
        if (first == null || getClass() != first.getClass()) return false;
        RentItem rentItem = (RentItem) first;
        return this.getRentItemId() == rentItem.getRentItemId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentItemId);
    }
}
