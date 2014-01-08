package javaproject.com;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

public class Invoice {
    
    private static int invoiceNumber = 1;
    private String codeFrom, codeTo;
    private double amountFrom, rate, amountTo;
    
    public Invoice(String codeFrom, double amountFrom, double rate, String codeTo, double amountTo) {
        this.codeFrom = codeFrom;
        this.amountFrom = amountFrom;
        this.rate = rate;
        this.codeTo = codeTo;
        this.amountTo = amountTo;
    }
    
    public void setInvoice(String filePath, String city, String name, String egn, String bs) {
        PrintWriter writeInvoice = null;
        
        try {
            writeInvoice = new PrintWriter(filePath);
            
            writeInvoice.println("From: Change (Sofia)");
            writeInvoice.println("Date and time: " + new Date());
            writeInvoice.println("__________________________");
            writeInvoice.println("PURCHASE OF FOREIGN CURRENCY");
            writeInvoice.println("Invoice number: " + (invoiceNumber++));
            writeInvoice.println("__________________________");
            writeInvoice.println();
            writeInvoice.println("Name: " + name);
            writeInvoice.println("EGN: " + egn);
            writeInvoice.println("City: " + city);
            writeInvoice.println();
            writeInvoice.println("Customer'a amount: " + amountFrom + " " + codeFrom);
            writeInvoice.println("Exchange rate: " + rate);
            writeInvoice.println("Customer recive: " + amountTo + " " + codeTo);
            writeInvoice.println("Reason: " + bs);
            writeInvoice.println();
            writeInvoice.println("Beneficiary: .................                                 Cashier: ................");
            writeInvoice.println();
            writeInvoice.println("First name and last name: .................");
            
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (writeInvoice != null) {
                writeInvoice.close();
            }
        }
    }
}