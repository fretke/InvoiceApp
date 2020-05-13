package Coversion;

import Data.Company;
import Data.Client;
import Data.Invoice;
import Data.RentItem;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;

public class DocFormer {

    public static final String DOC_FONT_STYLE = "Times New Roman";
    public static final int DOC_BIG_LETTERS = 16;
    public static final int DOC_SMALL_LETTERS = 11;
    public static final int DOC_TITLE_LETTERS = 12;

    private XWPFParagraph centeredParagraph;
    private XWPFParagraph leftAlignedParagraph;

    private static DocFormer instance = new DocFormer();

    private DocFormer(){

    }

    public static DocFormer getInstance(){
        return instance;
    }

    public void formWordDocument(Invoice invoice, Company company){

        File file = new File(new File ("").getAbsolutePath() + "\\" + invoice.getClient().getName());
        if (!file.exists()){
            file.mkdir();
        }

        String fileName = file.getPath() + "\\" + invoice.getInvoiceNumber() + ".docx";

        XWPFDocument doc = setTitle(invoice);

        doc = setRequisites(invoice, doc, company);

        doc = setTable(invoice, doc);

        XWPFParagraph first = doc.createParagraph();
        first.setAlignment(ParagraphAlignment.LEFT);
        writeParagraph(first.createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true, "Mokėtina suma žodžiais: ");

        XWPFParagraph second = doc.createParagraph();
        second.setAlignment(ParagraphAlignment.LEFT);
        writeParagraph(second.createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false, NumbersToWords.getAmountInWords(invoice.getTotalAmount() * 1.21));
        second.getRuns().get(0).addBreak();

        doc = setSignatureArea(doc, company);

        try(FileOutputStream out = new FileOutputStream(fileName)){
            doc.write(out);
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    private XWPFDocument setTitle (Invoice invoice){

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        writeParagraph(paragraph.createRun(), DOC_FONT_STYLE, DOC_BIG_LETTERS, true, "PVM - SĄSKAITA FAKTŪRA");

        XWPFParagraph paragraph2 = doc.createParagraph();
        paragraph2.setAlignment(ParagraphAlignment.CENTER);

        writeParagraph(paragraph2.createRun(), DOC_FONT_STYLE, DOC_TITLE_LETTERS, false, "Serija ");
        writeParagraph(paragraph2.createRun(), DOC_FONT_STYLE, DOC_TITLE_LETTERS, true, "BAK ");
        writeParagraph(paragraph2.createRun(), DOC_FONT_STYLE, DOC_TITLE_LETTERS, false, "Nr.: ");
        writeParagraph(paragraph2.createRun(), DOC_FONT_STYLE, DOC_TITLE_LETTERS, true, invoice.getInvoiceNumber().replaceAll("(^BAK)(\\W)*", ""));
        paragraph2.createRun().addBreak();
        writeParagraph(paragraph2.createRun(), DOC_FONT_STYLE, DOC_TITLE_LETTERS, false, convertDate(invoice.getDate()));
        return doc;
    }

    private String convertDate (LocalDate date){
        String rawDate = date.toString();
        String [] constituents = rawDate.split("-");
        return constituents[0] + " m. " + monthInWords(constituents[1]) + " mėn. " + constituents[2] + " d.";
    }

    private String monthInWords(String month){
        String [] months = {"","sausio", "vasario", "kovo", "balandžio", "gegužės", "birželio", "liepos", "rugpjūčio", "rugsėjo", "spalio", "lapkričio", "gruodžio"};
        month = month.replace("0", "");
        return months[Integer.parseInt(month)];
    }


    private void writeParagraph(XWPFRun run, String fontFamily, int fontSize, boolean bold, String text){
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setBold(bold);
        run.setText(text);
    }

    private XWPFDocument setRequisites(Invoice invoice, XWPFDocument doc, Company company){

        Client client = invoice.getClient();
        XWPFParagraph main = doc.createParagraph();
        main.setAlignment(ParagraphAlignment.LEFT);
        this.leftAlignedParagraph = main;
        XWPFTable table = doc.createTable(8, 4);
        table.setWidth(10000);

        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3000));
        table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
        table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3000));
        table.getCTTbl().getTblPr().unsetTblBorders();

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 4; j++){
                table.getRow(i).getCell(j).setParagraph(leftAlignedParagraph);
            }
        }

        writeParagraph(table.getRow(0).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "PARDAVĖJAS");
        writeParagraph(table.getRow(0).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "PIRKĖJAS");

        writeParagraph(table.getRow(1).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Pavadinimas");
        writeParagraph(table.getRow(1).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Pavadinimas");
        writeParagraph(table.getRow(1).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getName());
        writeParagraph(table.getRow(1).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                client.getName());

        writeParagraph(table.getRow(2).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Įmonės kodas");
        writeParagraph(table.getRow(2).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getCode());
        writeParagraph(table.getRow(2).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Įmonės kodas");
        writeParagraph(table.getRow(2).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                client.getCompanyCode());

        writeParagraph(table.getRow(3).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "PVM mok. kodas");
        writeParagraph(table.getRow(3).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getVatNumber());
        writeParagraph(table.getRow(3).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "PVM mok. kodas");
        writeParagraph(table.getRow(3).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                client.getVatNumber());

        writeParagraph(table.getRow(4).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Adresas");
        writeParagraph(table.getRow(4).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getAddress());
        writeParagraph(table.getRow(4).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Adresas");
        writeParagraph(table.getRow(4).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                client.getAddress());

        writeParagraph(table.getRow(5).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "A/s Nr. ");
        writeParagraph(table.getRow(5).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getAccountNumber());

        writeParagraph(table.getRow(6).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Bankas");
        writeParagraph(table.getRow(6).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getBankName());

        writeParagraph(table.getRow(7).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, true,
                "Banko kodas");
        writeParagraph(table.getRow(7).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS, false,
                company.getBankCode());

        return doc;
    }

    private  XWPFDocument setTable (Invoice invoice, XWPFDocument doc){
        XWPFParagraph main = doc.createParagraph();
        main.setAlignment(ParagraphAlignment.CENTER);

        XWPFTable itemsTable = doc.createTable(invoice.getList().size() + 1, 5);
        itemsTable.setWidth(10000);

        itemsTable.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(4000));
        itemsTable.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1500));
        itemsTable.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1400));
        itemsTable.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1600));
        itemsTable.getRow(0).getCell(4).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1500));

        for (int j = 0; j < invoice.getList().size()+1; j++) {
            for (int i = 0; i < 5; i++) {
                itemsTable.getRow(j).getCell(i).setParagraph(main);
            }
        }

        writeParagraph(itemsTable.getRow(0).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, true, "Prekės pavadinimas");
        writeParagraph(itemsTable.getRow(0).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, true, "Mato vnt.");
        writeParagraph(itemsTable.getRow(0).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, true, "Kiekis");
        writeParagraph(itemsTable.getRow(0).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, true, "Kaina be PVM");
        writeParagraph(itemsTable.getRow(0).getCell(4).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, true, "Suma be PVM");


        for (int i = 0; i < invoice.getList().size(); i++){
            RentItem item = invoice.getList().get(i);
            writeParagraph(itemsTable.getRow(i+1).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                    DOC_SMALL_LETTERS, false, item.getItemName());
            writeParagraph(itemsTable.getRow(i+1).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                    DOC_SMALL_LETTERS, false, item.getMeasuringUnit());
            writeParagraph(itemsTable.getRow(i+1).getCell(2).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                    DOC_SMALL_LETTERS, false, formatQuantity(item.getQuantity()));
            writeParagraph(itemsTable.getRow(i+1).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                    DOC_SMALL_LETTERS, false, formatNumber(item.getPrice()));
            writeParagraph(itemsTable.getRow(i+1).getCell(4).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                    DOC_SMALL_LETTERS, false, formatNumber(item.getTotalSum()));
        }

        XWPFTable sumUpTable = doc.createTable(3,5);
        sumUpTable.getCTTbl().getTblPr().unsetTblBorders();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                sumUpTable.getRow(i).getCell(j).setParagraph(main);
            }
        }

        writeParagraph(sumUpTable.getRow(0).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, false, "Viso");
        writeParagraph(sumUpTable.getRow(1).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, false, "PVM, 21%");
        writeParagraph(sumUpTable.getRow(2).getCell(3).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, false, "Viso suma");

        double amount = invoice.getTotalAmount();
        double vat = amount * 0.21;
        vat = Math.round(vat * 100.00) / 100.00;

        writeParagraph(sumUpTable.getRow(0).getCell(4).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, false, formatNumber(amount));
        writeParagraph(sumUpTable.getRow(1).getCell(4).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, false, formatNumber(vat));
        writeParagraph(sumUpTable.getRow(2).getCell(4).getParagraphs().get(0).createRun(), DOC_FONT_STYLE,
                DOC_SMALL_LETTERS, false, formatNumber(vat+amount));

        return doc;
    }

    private  XWPFDocument setSignatureArea(XWPFDocument doc, Company company){
        XWPFTable table = doc.createTable(2, 2);
        table.getCTTbl().getTblPr().unsetTblBorders();
        XWPFParagraph main = doc.createParagraph();
        main.setAlignment(ParagraphAlignment.CENTER);
        XWPFParagraph leftAligned = doc.createParagraph();
        leftAligned.setAlignment(ParagraphAlignment.LEFT);
        table.setWidth(10000);
        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3000));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(7000));

        table.getRow(0).getCell(0).setParagraph(leftAligned);
        table.getRow(0).getCell(1).setParagraph(main);
        table.getRow(1).getCell(0).setParagraph(leftAligned);
        table.getRow(1).getCell(1).setParagraph(main);

        writeParagraph(table.getRow(0).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS,
                true, "Sąskaitą išrašė:");
        writeParagraph(table.getRow(0).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS,
                false, company.getSelectedEmployee());
        table.getRow(0).getCell(1).getParagraphs().get(0).getRuns().get(0).addBreak();

        writeParagraph(table.getRow(0).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, 9,
                false, "(parašas, vardas, pavardė, pareigos)");
        writeParagraph(table.getRow(1).getCell(0).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS,
                true, "Sąskaitą priėmė: ");
        writeParagraph(table.getRow(1).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, DOC_SMALL_LETTERS,
                false, ".......................................................");
        table.getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).addBreak();

        writeParagraph(table.getRow(1).getCell(1).getParagraphs().get(0).createRun(), DOC_FONT_STYLE, 9,
                false, "(parašas, vardas, pavardė, pareigos)");

        return doc;
    }

    private String formatNumber(double number){
        String string = String.valueOf(number);

        if (string.matches("([0-9]+)\\.[0-9]")){
            string = string.concat("0");
        }
        return string.replaceAll("\\.", ",");
    }

    private String formatQuantity(double number){
        String string = String.valueOf(number);
        if (string.matches("([0-9]+)\\.[0]")){
            String[] qty = string.split("\\.");
            string = qty[0];
        }
        return string;
    }
}
