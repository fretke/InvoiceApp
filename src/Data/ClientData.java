package Data;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientData {

    private DateTimeFormatter formatter;

    public static final String DB_NAME = "Clients.db";
//    public static final String DB_PATH = "jdbc:sqlite:E:\\" + DB_NAME;
    public static final String DB_PATH = "jdbc:sqlite:" + new File(".").getAbsolutePath().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\.", "") + DB_NAME;


    public static final String TABLE_CLIENT = "client";
    public static final String CLIENT_ID_COL = "_id";
    public static final String CLIENT_NAME_COL = "name";
    public static final String CLIENT_CODE_COL = "companyCode";
    public static final String CLIENT_VAT_COL = "vatNumber";
    public static final String CLIENT_ADDRESS_COL = "address";

    public static final String TABLE_INVOICES = "invoices";
    public static final String INVOICES_ID_COL = "_id";
    public static final String INVOICES_NUMBER_COL = "number";
    public static final String INVOICES_DATE_COL = "date";
    public static final String INVOICES_AMOUNT_COL = "amount";
    public static final String INVOICES_CLIENT_ID_COL = "client_id";

    public static final String TABLE_RENT_ITEM = "rentItem";
    public static final String RENT_ITEM_ID = "_id";
    public static final String RENT_ITEM_NAME_COL = "itemName";
    public static final String RENT_ITEM_UNIT_COL = "measuringUnit";
    public static final String RENT_ITEM_QTY_COL = "quantity";
    public static final String RENT_ITEM_PRICE_COL = "price";
    public static final String RENT_ITEM_INVOICE_ID_COL = "invoice_id";

    public static final String TABLE_PREP_RENT_ITEM = "prepRentItem";
    public static final String PREP_NAME_COL = "name";
    public static final String PREP_UNIT = "measuringUnit";
    public static final String PREP_PRICE = "price";
    public static final String PREP_ID_COL = "_id";


    public static final String QUERY_GET_ALL_CLIENTS = "SELECT * FROM " + TABLE_CLIENT;
    public static final String INSERT_CLIENT_INTO_DB = "INSERT INTO " + TABLE_CLIENT + " (" + CLIENT_NAME_COL + ", " +
            CLIENT_CODE_COL + ", " + CLIENT_VAT_COL + ", " + CLIENT_ADDRESS_COL +") VALUES (?,?,?,?)";
    public static final String INSERT_INVOICE_INTO_DB = "INSERT INTO " + TABLE_INVOICES + "(" + INVOICES_NUMBER_COL + ", " + INVOICES_DATE_COL +
        ", " + INVOICES_AMOUNT_COL + ", " + INVOICES_CLIENT_ID_COL + ") VALUES (?,?,?,?)";

    public static final String INSERT_RENT_ITEM_INTO_DB = "INSERT INTO " + TABLE_RENT_ITEM + " (" + RENT_ITEM_NAME_COL + ", " + RENT_ITEM_UNIT_COL +
        ", " + RENT_ITEM_QTY_COL + ", " + RENT_ITEM_PRICE_COL + ", " + RENT_ITEM_INVOICE_ID_COL + ") VALUES (?, ?, ?, ?, ?)";
    public static final String QUERY_ID_CLIENT = "SELECT " + CLIENT_ID_COL + " FROM " + TABLE_CLIENT + " WHERE " + CLIENT_NAME_COL + " =?";


    public static final String QUERY_INVOICE_DATA_FROM_CLIENT_ID = "SELECT " + INVOICES_ID_COL + ", " + INVOICES_NUMBER_COL + ", " + INVOICES_DATE_COL + " FROM " + TABLE_INVOICES +
            " WHERE " + INVOICES_CLIENT_ID_COL + " = ?";
    public static final String QUERY_RENT_ITEMS_FROM_INVOICE_ID = "SELECT " + RENT_ITEM_ID + ", " + RENT_ITEM_NAME_COL + ", " + RENT_ITEM_UNIT_COL + ", " + RENT_ITEM_QTY_COL + ", " +
        RENT_ITEM_PRICE_COL + " FROM " + TABLE_RENT_ITEM + " WHERE " + RENT_ITEM_INVOICE_ID_COL + " = ?";
    public static final String DELETE_RENT_ITEM = "DELETE FROM " + TABLE_RENT_ITEM + " WHERE " + RENT_ITEM_ID + "= ?";
    public static final String DELETE_RENT_ITEM_INVOICE_ID = "DELETE FROM " + TABLE_RENT_ITEM + " WHERE " + RENT_ITEM_INVOICE_ID_COL + "= ?";

    public static final String DELETE_INVOICE = "DELETE FROM " + TABLE_INVOICES + " WHERE " + INVOICES_ID_COL + " = ?";
    public static final String DELETE_CLIENT = "DELETE FROM " + TABLE_CLIENT + " WHERE " + CLIENT_ID_COL + " =?";

    public static final String GET_PREP_RENT_ITEM_LIST = "SELECT * FROM " + TABLE_PREP_RENT_ITEM;
    public static final String UPDATE_INVOICE_DATA = "UPDATE " + TABLE_INVOICES + " SET " + INVOICES_NUMBER_COL + " = ?, " +
        INVOICES_DATE_COL + " = ?, " + INVOICES_CLIENT_ID_COL + "= ? WHERE " + INVOICES_ID_COL + " = ?";

    public static final String QUERY_INVOICE_DATA_NEW = "SELECT * FROM " + TABLE_INVOICES;

    public static final String QUERY_CLIENT_FROM_ID = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + CLIENT_ID_COL + " = ?";

    public static final String UPDATE_PREP_ITEM = "UPDATE " + TABLE_PREP_RENT_ITEM + " SET " + PREP_NAME_COL + " = ?, " +
        PREP_UNIT + " = ?, " + PREP_PRICE + " = ? WHERE " + PREP_ID_COL + " = ?";

    public static final String INSERT_PREP_ITEM = "INSERT INTO " + TABLE_PREP_RENT_ITEM + "(" + PREP_NAME_COL + ", " +
        PREP_UNIT + ", " + PREP_PRICE + ") VALUES (?, ?, ?)";

    public static final String DELETE_PREP_ITEM = "DELETE FROM " + TABLE_PREP_RENT_ITEM + " WHERE " + PREP_ID_COL + " = ?";


    ///////////////////////////////////FROM COMPANY DATA//////////////////////////////////////////////////////////////////
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
//    UPDATE Client SET name = "iki parduotuve", companyCode = "51551", vatNumber = "LT11111", address = "zardininku 17-46"
//    WHERE _id = 8;
    public static final String UPDATE_CLIENT = "UPDATE " + TABLE_CLIENT + " SET " + CLIENT_NAME_COL + " = ?," + CLIENT_CODE_COL + "=?," + CLIENT_VAT_COL +
    "=?," + CLIENT_ADDRESS_COL + "=? WHERE " + CLIENT_ID_COL + " = ?";


    public static final String UPDATE_EMPLOYEE_SELECTED_FIRST = "UPDATE " + TABLE_EMPLOYEES + " SET " + EMPLOYEES_SELECTED_COL + " = 0 WHERE " +
            EMPLOYEES_COMPANY_ID_COL + " =?";
    //    UPDATE employees SET isSelected = 1 WHERE name = "Aistis Liutkevičius";
    public static final String UPDATE_EMPLOYEE_SELECTED_SECOND = "UPDATE " + TABLE_EMPLOYEES + " SET " + EMPLOYEES_SELECTED_COL + " = 1 WHERE " +
            EMPLOYEES_NAME_COL + " = ?";

    public static final String INSERT_EMPLOYEE = "INSERT INTO " + TABLE_EMPLOYEES + " (" + EMPLOYEES_NAME_COL + ", " + EMPLOYEES_POSITION_COL +
            ", " + EMPLOYEES_SELECTED_COL + ", " + EMPLOYEES_COMPANY_ID_COL + ") VALUES " + "(?, ?, 0, 1)";

    public static final String DELETE_EMPLOYEE = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " + EMPLOYEES_NAME_COL + " = ?";

/////////////////////////////////////////////////////////////////////////////////////////////////////

    private static ClientData instance = new ClientData();

    private ClientData() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    public static ClientData getInstance(){
        return instance;
    }

    private Connection con;
    private PreparedStatement loadClients;
    private PreparedStatement insertClientInDB;
    private PreparedStatement clientIdFromDB;
    private PreparedStatement invoicesFromDB;
    private PreparedStatement rentItemsDB;
    private PreparedStatement insertInvoiceInDB;
    private PreparedStatement insertRentItemInDB;

    private PreparedStatement deleteRentItemFromDB;
    private PreparedStatement deleteRentItemOnInvoiceID;
    private PreparedStatement deleteInvoiceFromDB;
    private PreparedStatement deleteClientFromDB;

    private PreparedStatement getPrepItemFromDB;
    private PreparedStatement updateInvoiceDB;

    private PreparedStatement updatePrepItem;
    private PreparedStatement insertPrepItem;
    private PreparedStatement deletePrepItem;

    ////////////////////////////////////FROM COMPANY DATA///////////////////////////////
    private PreparedStatement getEmployeesFromDB;
    private PreparedStatement getCompanyFromDB;
    private PreparedStatement updateCompanyDB;
    private PreparedStatement updateSelectedFirst;
    private PreparedStatement updateSelectedSecond;
    private PreparedStatement insertEmployeeInDB;
    private PreparedStatement deleteEmployeeFromDB;
    private PreparedStatement updateClientDB;

    // new stuff

    private PreparedStatement getClientFromID;

    public boolean open(){
        try{
            con = DriverManager.getConnection(DB_PATH);
            loadClients = con.prepareStatement(QUERY_GET_ALL_CLIENTS);
            insertClientInDB = con.prepareStatement(INSERT_CLIENT_INTO_DB);
            clientIdFromDB = con.prepareStatement(QUERY_ID_CLIENT);
            invoicesFromDB = con.prepareStatement(QUERY_INVOICE_DATA_FROM_CLIENT_ID);
            rentItemsDB = con.prepareStatement(QUERY_RENT_ITEMS_FROM_INVOICE_ID);
            insertInvoiceInDB = con.prepareStatement(INSERT_INVOICE_INTO_DB);
            insertRentItemInDB = con.prepareStatement(INSERT_RENT_ITEM_INTO_DB, Statement.RETURN_GENERATED_KEYS);
            deleteRentItemFromDB = con.prepareStatement(DELETE_RENT_ITEM);
            getPrepItemFromDB = con.prepareStatement(GET_PREP_RENT_ITEM_LIST);
            updateInvoiceDB = con.prepareStatement(UPDATE_INVOICE_DATA);
            deleteRentItemOnInvoiceID = con.prepareStatement(DELETE_RENT_ITEM_INVOICE_ID);
            deleteInvoiceFromDB = con.prepareStatement(DELETE_INVOICE);
            deleteClientFromDB = con.prepareStatement(DELETE_CLIENT);
            getClientFromID = con.prepareStatement(QUERY_CLIENT_FROM_ID);
            updatePrepItem = con.prepareStatement(UPDATE_PREP_ITEM);
            insertPrepItem = con.prepareStatement(INSERT_PREP_ITEM, Statement.RETURN_GENERATED_KEYS);
            deletePrepItem = con.prepareStatement(DELETE_PREP_ITEM);
            updateClientDB = con.prepareStatement(UPDATE_CLIENT);


            /////////////////////////////////////FROM COMPANY DATA//////////////////////////
            getEmployeesFromDB = con.prepareStatement(GET_EMPLOYEES_FROM_CLIENT_ID);
            getCompanyFromDB = con.prepareStatement(GET_COMPANY);
            updateCompanyDB = con.prepareStatement(UPDATE_COMPANY);
            updateSelectedFirst = con.prepareStatement(UPDATE_EMPLOYEE_SELECTED_FIRST);
            updateSelectedSecond = con.prepareStatement(UPDATE_EMPLOYEE_SELECTED_SECOND);
            insertEmployeeInDB = con.prepareStatement(INSERT_EMPLOYEE);
            deleteEmployeeFromDB = con.prepareStatement(DELETE_EMPLOYEE);
            ////////////////////////////////////////////////////////////////////////////////
            return true;
        } catch (SQLException e){
            System.out.println("Was not able to open connections " + e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if (loadClients != null){
                loadClients.close();
            }
            if (insertClientInDB != null){
                insertClientInDB.close();
            }
            if (clientIdFromDB != null){
                clientIdFromDB.close();
            }
            if (invoicesFromDB != null){
                invoicesFromDB.close();
            }
            if (rentItemsDB != null){
                rentItemsDB.close();
            }
            if (insertInvoiceInDB != null){
                insertInvoiceInDB.close();
            }
            if (insertRentItemInDB != null){
                insertRentItemInDB.close();
            }
            if (deleteRentItemFromDB != null){
                deleteRentItemFromDB.close();
            }
            if (getPrepItemFromDB != null){
                getPrepItemFromDB.close();
            }
            if (updateInvoiceDB != null){
                updateInvoiceDB.close();
            }
            if (deleteRentItemOnInvoiceID != null){
                deleteRentItemOnInvoiceID.close();
            }
            if (deleteInvoiceFromDB != null){
                deleteInvoiceFromDB.close();
            }
            if (deleteClientFromDB != null){
                deleteClientFromDB.close();
            }
            if (getClientFromID != null){
                getClientFromID.close();
            }
            if (updatePrepItem != null){
                updatePrepItem.close();
            }
            if (insertPrepItem != null){
                insertPrepItem.close();
            }
            if (deletePrepItem != null){
                deletePrepItem.close();
            }
            if (updateClientDB != null){
                updateClientDB.close();
            }
            //////////////////////////////////FROM COMPANY DATA////////////////////////////////////////////////////////
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
            System.out.println("Problem closing connections " + e.getMessage());
        }
    }

    private Client getClient(int clientId){
        Client client = new Client();
        try{
            getClientFromID.setInt(1, clientId);
            ResultSet result = getClientFromID.executeQuery();
            if (result.next()){
                client.setClientId(result.getInt(1));
                client.setName(result.getString(2));
                client.setCompanyCode(result.getString(3));
                client.setVatNumber(result.getString(4));
                client.setAddress(result.getString(5));
                return client;
            } else {
                throw new SQLException("Not able to access client in getClient method");
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void updateClient(String name, String code, String vatNumber, String address, int clientId){
        try{
            con.setAutoCommit(false);
            updateClientDB.setString(1, name);
            updateClientDB.setString(2, code);
            updateClientDB.setString(3, vatNumber);
            updateClientDB.setString(4, address);
            updateClientDB.setInt(5, clientId);
            int affectedRows = updateClientDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in updateClient method");
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

    public List<Invoice> getInvoiceList(){
        List<Invoice> invoiceList = new ArrayList<>();

        try{
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(QUERY_INVOICE_DATA_NEW);
            while (result.next()){
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(result.getInt(1));
                invoice.setInvoiceNumber(result.getString(2));
                invoice.setDate(LocalDate.parse(result.getString(3), formatter));
                invoice.setClient(getClient(result.getInt(5)));
                invoice.setList(getRentItem(invoice.getInvoiceId()));
                invoiceList.add(invoice);
            }
            return invoiceList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private int getClientId (String clientName){
        try{
            clientIdFromDB.setString(1, clientName);
            ResultSet result = clientIdFromDB.executeQuery();
            return result.getInt(1);
        } catch (SQLException e){
            System.out.println("Problem getting clientId " + e.getMessage());
            return -1;
        }
    }

    private List<RentItem> getRentItem(int invoiceId){
        List<RentItem> rentItemList = new ArrayList<>();
        try{
            rentItemsDB.setInt(1, invoiceId);
            ResultSet result = rentItemsDB.executeQuery();
            while (result.next()){
                RentItem item = new RentItem();
                item.setRentItemId(result.getInt(1));
                item.setItemName(result.getString(2));
                item.setMeasuringUnit(result.getString(3));
                item.setQuantity(result.getDouble(4));
                item.setPrice(result.getDouble(5));
                item.calculateSum();
                rentItemList.add(item);
            }
            return rentItemList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Invoice> getInvoice (Client client){
        List<Invoice> list = new ArrayList<>();
        int clientId = getClientId(client.getName());
        if (clientId <= 0) {
            return null;
        }
        try{
            invoicesFromDB.setInt(1, clientId);
            ResultSet result = invoicesFromDB.executeQuery();
            while(result.next()){
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(result.getInt(1));
                invoice.setInvoiceNumber(result.getString(2));
                invoice.setDate(LocalDate.parse(result.getString(3), formatter));
                invoice.setList(getRentItem(invoice.getInvoiceId()));
                list.add(invoice);
            }
            return list;
        } catch (SQLException e){
            System.out.println("Problem in getInvoice method " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Client> getClients(){
        List<Client> list = new ArrayList<>();
        try{
            ResultSet result = loadClients.executeQuery();

            while (result.next()){
                int clientId = result.getInt(1);
                String companyName = result.getString(2);
                String companyCode = result.getString(3);
                String vatNumber = result.getString(4);
                String address = result.getString(5);

                Client client = new Client();
                client.setClientId(clientId);
                client.setName(companyName);
                client.setCompanyCode(companyCode);
                client.setVatNumber(vatNumber);
                client.setAddress(address);
                list.add(client);
            }
            return list;
        } catch (SQLException e){
            System.out.println("Problem getting clients from DB " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<RentItem> getPrepList (){
        List<RentItem> items = new ArrayList<>();
        try{
            ResultSet result = getPrepItemFromDB.executeQuery();
            while (result.next()){
                RentItem item = new RentItem();
                item.setRentItemId(result.getInt(1));
                item.setItemName(result.getString(2));
                item.setMeasuringUnit(result.getString(3));
                item.setPrice(result.getDouble(4));
                items.add(item);
            }
            return items;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void updatePrepItem(String itemName, String measuringUnit, double priceAfter, int id){
        try{
            con.setAutoCommit(false);
            updatePrepItem.setString(1, itemName);
            updatePrepItem.setString(2, measuringUnit);
            updatePrepItem.setDouble(3, priceAfter);
            updatePrepItem.setInt(4, id);
            int affectedRows = updatePrepItem.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in updatePrepItem method");
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
            try {
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public int addPrepItem (String itemName, String measuringUnit, double price){

        try{
            insertPrepItem.setString(1, itemName);
            insertPrepItem.setString(2, measuringUnit);
            insertPrepItem.setDouble(3, price);
            insertPrepItem.executeUpdate();
            ResultSet result = insertPrepItem.getGeneratedKeys();
            if (result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();

        }
        return -1;
    }

    public void deletePrepItem (RentItem item){
        try{
            con.setAutoCommit(false);
            deletePrepItem.setInt(1, item.getRentItemId());
            int affectedRows = deletePrepItem.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in deletePrepItem method");
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

    public void insertClient(String name, String code, String vat, String address){
        try{
            con.setAutoCommit(false);
            insertClientInDB.setString(1, name);
            insertClientInDB.setString(2, code);
            insertClientInDB.setString(3, vat);
            insertClientInDB.setString(4, address);

            int affectedRows = insertClientInDB.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Problem in updating the DB");
            }
            con.commit();
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException a){
                System.out.println("Problem in rolling back data... UPS " + a.getMessage());
            }
            System.out.println("Problem inserting Client into DB " + e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                System.out.println("Problem in setting autocommit " + e.getMessage());
            }
        }
    }

    public void insertInvoice (String invoiceNumber, LocalDate date, Client client){
        try{
            con.setAutoCommit(false);
            insertInvoiceInDB.setString(1, invoiceNumber);
            insertInvoiceInDB.setString(2, date.toString());
            insertInvoiceInDB.setInt(3, 0);
            insertInvoiceInDB.setInt(4, client.getClientId());
            int affectedRows = insertInvoiceInDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("Too many rows affected");
            }
            con.commit();
        } catch (SQLException e){
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException a){
                a.printStackTrace();
            }
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void insertInvoice (Client client){

        try{
            con.setAutoCommit(false);
            insertInvoiceInDB.setString(1, getCurrentInvoiceNumber());
            insertInvoiceInDB.setString(2, LocalDate.now().toString());
            insertInvoiceInDB.setInt(3, 0);
            insertInvoiceInDB.setInt(4, client.getClientId());
            int affectedRows = insertInvoiceInDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("Too many rows affected");
            }
            con.commit();
        } catch (SQLException e){
            e.printStackTrace();
            try{
                con.rollback();
            } catch (SQLException a){
                a.printStackTrace();
            }
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public String getCurrentInvoiceNumber(){
        int maxNumber = 0;
        List<String> invoiceNumbers = new ArrayList<>();
        String sqlQuery = "SELECT number FROM invoices";
        try{
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sqlQuery);
            while (result.next()){
                invoiceNumbers.add(result.getString(1));
            }

            for (String string : invoiceNumbers){

                if (string.matches("(^BAK)(\\W)*([0-9]){4}")){
                    String number = string.replaceAll("(^BAK)(\\W)*", "");
                    if (Integer.parseInt(number) > maxNumber){
                        maxNumber = Integer.parseInt(number);
                    }
                }
            }
            int newNumber = maxNumber + 1;
            return numberOfZeroes(newNumber) + (newNumber);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private String numberOfZeroes (int number){
        StringBuilder sb = new StringBuilder();
        sb.append("BAK ");
        int digits = 0;
        while (number != 0){
            number /= 10;
            digits++;
        }
        for (int i = 0; i < (4- digits); i++){
            sb.append("0");
        }
        return sb.toString();
    }

    public int insertRentItem (String itemName, String measuringUnit, double qty, double price, int invoiceId){
        int generatedKey;
        try {
            con.setAutoCommit(false);
            insertRentItemInDB.setString(1, itemName);
            insertRentItemInDB.setString(2, measuringUnit);
            insertRentItemInDB.setDouble(3, qty);
            insertRentItemInDB.setDouble(4, price);
            insertRentItemInDB.setInt(5, invoiceId);

            int affectedRows = insertRentItemInDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in insertRentItem method");
            }
            con.commit();
            ResultSet result = insertRentItemInDB.getGeneratedKeys();
            if (result.next()){
              return generatedKey = result.getInt(1);
            }
        } catch (SQLException e){
            try {
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
        return -1;
    }

    public boolean deleteClient(Client client){
        List<Invoice> invoiceList = getInvoice(client);
        for (Invoice invoice: invoiceList){
            System.out.println(invoice.getInvoiceNumber());
            if (!deleteInvoice(invoice)){
                return false;
            }
        }
        try{
            con.setAutoCommit(false);
            deleteClientFromDB.setInt(1, client.getClientId());
            int affectedRows = deleteClientFromDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("affected too many rows in deleteClient method");
            }
            con.commit();
            return true;
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException a){
                a.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean deleteInvoice (Invoice invoice){
            try{
                con.setAutoCommit(false);
                if (!deleteRentItem(invoice)){
                    throw new SQLException("Not able to delete Rent items");
                }
                deleteInvoiceFromDB.setInt(1, invoice.getInvoiceId());
                int affectedRows = deleteInvoiceFromDB.executeUpdate();
                if (affectedRows != 1){
                    throw new SQLException("deleted too many invoices...");
                }
                con.commit();
                return true;
            } catch (SQLException e){
                try{
                    con.rollback();
                } catch (SQLException a){
                    a.printStackTrace();
                }
                e.printStackTrace();
                return false;
            } finally {
                try{
                    con.setAutoCommit(true);
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
    }

    private boolean deleteRentItem (Invoice invoice){
        try {
            deleteRentItemOnInvoiceID.setInt(1, invoice.getInvoiceId());
            deleteRentItemOnInvoiceID.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRentItem (RentItem item){
        System.out.println(item.getRentItemId());
        try{
            con.setAutoCommit(false);
            deleteRentItemFromDB.setInt(1, item.getRentItemId());
            int affectedRows = deleteRentItemFromDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("Deleted too many rows!!");
            }
            con.commit();
            return true;
        } catch (SQLException e){
            try{
                con.rollback();
            } catch (SQLException a){
                System.out.println("Not able to roll back changes (deleteRentItem) " + a.getMessage());
            }
            System.out.println("problem in deleteRentItem method " + e.getMessage());
        } finally {
            try{
                con.setAutoCommit(true);
            } catch (SQLException e){
                System.out.println("Not able to set autoCommit " + e.getMessage());
            }
        }
        return false;
    }
    public void updateInvoice (String number, String date, Client client, int id){
        try{
            con.setAutoCommit(false);
            updateInvoiceDB.setString(1, number);
            updateInvoiceDB.setString(2, date);
            updateInvoiceDB.setInt(3, client.getClientId());
            updateInvoiceDB.setInt(4, id);

            int affectedRows = updateInvoiceDB.executeUpdate();
            if (affectedRows != 1){
                throw new SQLException("Affected too many rows un updateInvoice method");
            }
            con.commit();
        } catch (SQLException e){
            try {
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
    /////////////////////////////////////FROM COMPANY DATA//////////////////////////////////////////
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
