package Data;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Client {
    private int clientId;
    private SimpleStringProperty name;
    private SimpleStringProperty companyCode;
    private SimpleStringProperty vatNumber;
    private SimpleStringProperty address;

    public Client() {
        this.name = new SimpleStringProperty();
        this.companyCode = new SimpleStringProperty();
        this.vatNumber = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCompanyCode() {
        return companyCode.get();
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode.set(companyCode);
    }

    public String getVatNumber() {
        return vatNumber.get();
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber.set(vatNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Client client = (Client) object;
        if (this.getName().equals(client.getName())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
