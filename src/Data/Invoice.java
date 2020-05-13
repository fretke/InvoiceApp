package Data;

import java.time.LocalDate;
import java.util.List;

public class Invoice {
    private int invoiceId;
    private String invoiceNumber;
    private LocalDate date;
    private double totalAmount;
    private List<RentItem> list;
    private Client client;


    public Invoice() {
        this.totalAmount = 0;
    }

    public Invoice(String invoiceNumber, LocalDate date, List<RentItem> list) {
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.list = list;
        this.totalAmount = 0;
        for (RentItem item : list){
            this.totalAmount+= item.getTotalSum();
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public List<RentItem> getList() {
        return list;
    }

    public void setList(List<RentItem> list) {
        if (list != null){
            this.list = list;
            for (RentItem item : list){
                this.totalAmount+= item.getTotalSum();
            }
        }
    }

    private void calculateTotalAmount(){
        for (RentItem item : list){
            this.totalAmount+= item.getTotalSum();
        }
    }

    public void addRentItem(RentItem item){
        list.add(item);
        totalAmount += item.getTotalSum();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return Math.round(totalAmount * 100.00) / 100.00;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void removeRentItem(RentItem item){
        if (list.removeIf(rentItem -> rentItem.getRentItemId() == item.getRentItemId())){
            totalAmount-=item.getTotalSum();
        }
    }

    @Override
    public String toString() {
        return  invoiceNumber + " " + date + " " + totalAmount;
    }
}
