package Company;

import java.util.List;

public class Company {
    private String name;
    private String code;
    private String vatNumber;
    private String address;
    private String accountNumber;
    private String bankName;
    private String bankCode;
    private List<Employee> list;
    private boolean selected;

    public Company() {
    }

    public Company(String name, String code, String vatNumber, String address, String accountNumber, String bankName, String bankCode) {
        this.name = name;
        this.code = code;
        this.vatNumber = vatNumber;
        this.address = address;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public void addEmployee(Employee employee){
        list.add(employee);
    }

    public String getSelectedEmployee(){
        for (Employee employee: list){
            if (employee.isSelected()){
                return employee.getName() + ", " + employee.getPosition();
            }
        }
        return null;
    }

    public List<Employee> getList() {
        return list;
    }

    public void setList(List<Employee> list) {
        this.list = list;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
