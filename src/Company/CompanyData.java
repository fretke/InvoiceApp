package Company;

import javafx.animation.ScaleTransition;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyData {

    public static final String DB_NAME = "Clients.db";
//    public static final String DB_PATH = "jdbc:sqlite:E:\\" + DB_NAME;
    public static final String DB_PATH = "jdbc:sqlite:" + new File(".").getAbsolutePath().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\.", "") + DB_NAME;

    public static final String TABLE_COMPANY = "company";
    public static final String COMPANY_ID_COL = "_id";
    public static final String COMPANY_NAME_COL = "name";
    public static final String COMPANY_CODE_COL = "code";
    public static final String COMPANY_VAT_COL = "vatNumber";
    public static final String COMPANY_ADDRESS_COL = "address";
    public static final String COMPANY_ACCOUNT_NUMBER_COL = "accountNumber";
    public static final String COMPANY_BANK_NAME_COL = "bankName";
    public static final String COMPANY_BANK_CODE_COL = "bankCode";
    public static final String COMPANY_SELECTED_COL = "isSelected";

    public static final String TABLE_EMPLOYEES = "employees";
    public static final String EMPLOYEES_NAME_COL = "name";
    public static final String EMPLOYEES_POSITION_COL = "position";
    public static final String EMPLOYEES_SELECTED_COL = "isSelected";
    public static final String EMPLOYEES_COMPANY_ID_COL = "company_id";

    public static final String GET_EMPLOYEES_FROM_CLIENT_ID = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE " +
            EMPLOYEES_COMPANY_ID_COL + "= ?";
    public static final String GET_COMPANY = "SELECT * FROM " + TABLE_COMPANY + " WHERE " + COMPANY_SELECTED_COL
            + " = 1";

    public static final String UPDATE_COMPANY = "UPDATE " + TABLE_COMPANY + " SET " + COMPANY_NAME_COL + " =?, " +
    COMPANY_CODE_COL + " =?, " + COMPANY_VAT_COL + " =?, " + COMPANY_ADDRESS_COL + " =?, " + COMPANY_ACCOUNT_NUMBER_COL + " =?, " +
            COMPANY_BANK_NAME_COL + " =?, " + COMPANY_BANK_CODE_COL + " =? WHERE " + COMPANY_ID_COL + "= ?";
//    UPDATE employees SET isSelected = 0 WHERE company_id = 1;
    public static final String UPDATE_EMPLOYEE_SELECTED_FIRST = "UPDATE " + TABLE_EMPLOYEES + " SET " + EMPLOYEES_SELECTED_COL + " = 0 WHERE " +
        EMPLOYEES_COMPANY_ID_COL + " =?";
//    UPDATE employees SET isSelected = 1 WHERE name = "Aistis Liutkevičius";
    public static final String UPDATE_EMPLOYEE_SELECTED_SECOND = "UPDATE " + TABLE_EMPLOYEES + " SET " + EMPLOYEES_SELECTED_COL + " = 1 WHERE " +
    EMPLOYEES_NAME_COL + " = ?";
//    INSERT INTO employees (name, position, isSelected, company_id)
//    VALUES ("Alfredas Kidus", "sales", 0, 1);

    public static final String INSERT_EMPLOYEE = "INSERT INTO " + TABLE_EMPLOYEES + " (" + EMPLOYEES_NAME_COL + ", " + EMPLOYEES_POSITION_COL +
            ", " + EMPLOYEES_SELECTED_COL + ", " + EMPLOYEES_COMPANY_ID_COL + ") VALUES " + "(?, ?, 0, 1)";

    public static final String DELETE_EMPLOYEE = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " + EMPLOYEES_NAME_COL + " = ?";

    private Connection con;
    private PreparedStatement getEmployeesFromDB;
    private PreparedStatement getCompanyFromDB;
    private PreparedStatement updateCompanyDB;
    private PreparedStatement updateSelectedFirst;
    private PreparedStatement updateSelectedSecond;
    private PreparedStatement insertEmployeeInDB;
    private PreparedStatement deleteEmployeeFromDB;

    public static CompanyData instance = new CompanyData();

    private CompanyData(){

    }

    public static CompanyData getInstance(){
        return instance;
    }

    public boolean open(){
        try{
            con = DriverManager.getConnection(DB_PATH);
            getEmployeesFromDB = con.prepareStatement(GET_EMPLOYEES_FROM_CLIENT_ID);
            getCompanyFromDB = con.prepareStatement(GET_COMPANY);
            updateCompanyDB = con.prepareStatement(UPDATE_COMPANY);
            updateSelectedFirst = con.prepareStatement(UPDATE_EMPLOYEE_SELECTED_FIRST);
            updateSelectedSecond = con.prepareStatement(UPDATE_EMPLOYEE_SELECTED_SECOND);
            insertEmployeeInDB = con.prepareStatement(INSERT_EMPLOYEE);
            deleteEmployeeFromDB = con.prepareStatement(DELETE_EMPLOYEE);

            return true;
        } catch (SQLException e){
            System.out.println("Problem opening CompanyData " + e.getMessage());
        }
        return false;
    }

    public void close(){
        try{
            if (getEmployeesFromDB != null){
                getEmployeesFromDB.close();
            }
            if (getCompanyFromDB != null){
                getCompanyFromDB.close();
            }
            if (updateCompanyDB != null){
                updateCompanyDB.close();
            }
            if (updateSelectedFirst != null){
                updateSelectedFirst.close();
            }
            if (updateSelectedSecond != null){
                updateSelectedSecond.close();
            }
            if (insertEmployeeInDB != null){
                insertEmployeeInDB.close();
            }
            if (deleteEmployeeFromDB != null){
                deleteEmployeeFromDB.close();
            }
            if (con != null){
                con.close();
            }
        } catch (SQLException e){
            System.out.println("Not able to close properly companyData" + e.getMessage());
        }
    }

    public void deleteEmployee(Employee employee){
        try{
            con.setAutoCommit(false);
            deleteEmployeeFromDB.setString(1, employee.getName());
            int affectedRows = deleteEmployeeFromDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in deleteEmployee method");
            }
            con.commit();
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException a){
                a.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void insertEmployee(Employee employee){
        try{
            insertEmployeeInDB.setString(1, employee.getName());
            insertEmployeeInDB.setString(2, employee.getPosition());
            insertEmployeeInDB.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSelected(Employee employee){

        try{
            con.setAutoCommit(false);
            updateSelectedFirst.setInt(1, 1);
            updateSelectedSecond.setString(1, employee.getName());
            updateSelectedFirst.executeUpdate();
            int affectedRows = updateSelectedSecond.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in updateSelected employee method");
            }
            con.commit();
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException a){
                a.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void updateCompany(Company company){
        try{
            con.setAutoCommit(false);
            updateCompanyDB.setString(1, company.getName());
            updateCompanyDB.setString(2, company.getCode());
            updateCompanyDB.setString(3, company.getVatNumber());
            updateCompanyDB.setString(4, company.getAddress());
            updateCompanyDB.setString(5, company.getAccountNumber());
            updateCompanyDB.setString(6, company.getBankName());
            updateCompanyDB.setString(7, company.getBankCode());
            updateCompanyDB.setInt(8, 1);
            int affectedRows = updateCompanyDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("Affected too many rows in updateCompany method");
            }
            con.commit();
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException a){
                a.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private List<Employee> loadEmployees(int clientId){
        List<Employee> listEmployee = new ArrayList<Employee>();
        try{
            getEmployeesFromDB.setInt(1, clientId);
            ResultSet result = getEmployeesFromDB.executeQuery();
            while (result.next()){
                Employee employee = new Employee();
                employee.setName(result.getString(1));
                employee.setPosition(result.getString(2));
                if (result.getInt(3) == 1){
                    employee.setSelected(true);
                } else {
                    employee.setSelected(false);
                }
                listEmployee.add(employee);
            }
            return listEmployee;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Company> getCompanyList(){
        List <Company> companyList = new ArrayList<Company>();
        try{
            ResultSet result = getCompanyFromDB.executeQuery();
            while(result.next()){
                Company company = new Company();
                company.setName(result.getString(2));
                company.setCode(result.getString(3));
                company.setVatNumber(result.getString(4));
                company.setAddress(result.getString(5));
                company.setAccountNumber(result.getString(6));
                company.setBankName(result.getString(7));
                company.setBankCode(result.getString(8));
                if (result.getInt(9) == 1){
                    company.setSelected(true);
                } else {
                    company.setSelected(false);
                }
                company.setList(loadEmployees(result.getInt(1)));
                companyList.add(company);
            }
            return companyList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
