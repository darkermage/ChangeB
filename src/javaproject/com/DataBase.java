package javaproject.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataBase {

    private Connection conn;
    private PreparedStatement pst;
	
    public DataBase() {
            try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
            } catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Cannot connect to database");
                    System.exit(1);
            }
	}
	
    public Object[] getCodes() {
       List<Object> codeName = new ArrayList<>();
       
        try {
            pst = conn.prepareCall("SELECT rateName FROM rates"); 
            ResultSet resultSet = pst.executeQuery();
       
            while (resultSet.next()) {
                codeName.add(resultSet.getString("RateName"));
            }
            
            resultSet.close();
            return codeName.toArray();
        } catch (SQLException ex) {
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException ex) {
                }
            }
        }
            
        return null;
    }
    
    public void updateCurrency() {
            try {
                pst = conn.prepareStatement("truncate table Rates");
                pst.execute();
                pst.close();

                URL xml = new URL("http://bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/index.htm?download=xml&search=&lang=BG");
                URLConnection yc = xml.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                String xml2;
                StringBuilder Sb = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    Sb.append(inputLine);
                    Sb.append("\n");
                }

                in.close();
                Sb.delete(0, 1);
                xml2 = Sb.toString();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml2));

                Document doc = builder.parse(is);
                    doc.getDocumentElement().normalize();

                    NodeList nList = doc.getElementsByTagName("ROW");
                    String code_str="";
                    String reverse_str="";
                    double reverse_d=0;

                    for (int temp = 1; temp < nList.getLength() - 1; temp++) {

                            Node nNode = nList.item(temp);

                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                    Element eElement = (Element) nNode;
                                    NodeList code = eElement.getElementsByTagName("CODE");
                                    NodeList reverse = eElement.getElementsByTagName("REVERSERATE");
                                    if( code.getLength() > 0 ){
                                            code_str = code.item(0).getTextContent();
                                            if( reverse.getLength() > 0 ){
                                                    reverse_str = reverse.item(0).getTextContent();
                                                    reverse_d = Double.parseDouble(reverse_str);
                                            }
                                    }
                                    //res.put(code_str, reverse_d);
                                    pst = conn.prepareStatement("INSERT INTO rates(RateName,Rate) VALUES (?, ?)");
                                    pst.setString(1, code_str);
                                    pst.setDouble(2, reverse_d);
                                    pst.execute();

                            }
                    }
            } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Cannot connect to internet!");	
            } catch (SQLException e) {
                    System.err.println(e.getMessage());
            } catch (ParserConfigurationException e) {
                    System.err.println(e.getMessage());
            } catch (SAXException e) {
                    System.err.println(e.getMessage());
            } finally {
                    if (pst != null) {
                            try {
                                    pst.close();
                            } catch (SQLException e) {
                                    System.err.println(e.getMessage());
                            }
                    }
            }
	}
	
    public void updateLog(Date date, String currencyFrom, double amountFrom, double rate, String currencyTo, double amountTo) {
        try {
            pst = conn.prepareStatement("INSERT INTO log(`date`, `currency_from`, `currency_from_amount`, `rate`, `currency_to`, `currency_to_amount`) VALUES(?,?,?,?,?,?)");
            pst.setDate(1, date);
            pst.setString(2, currencyFrom);
            pst.setDouble(3, amountFrom);
            pst.setDouble(4, rate);
            pst.setString(5, currencyTo);
            pst.setDouble(6, amountTo);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
    
    public String getLog(Date date) {
        try {
            pst = conn.prepareStatement("SELECT * FROM log WHERE date = ?");
            pst.setDate(1, date);
            ResultSet res = pst.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (res.next()) {
                Date d = res.getDate(2);
                String cF = res.getString(3);
                double aF = res.getDouble(4);
                double r = res.getDouble(5);
                String cT = res.getString(6);
                double aT = res.getDouble(7);
                sb.append("Date:"+d.toString()+"\tFrom currency:"+cF+"\tAmount from:"+aF+"\tRate:"+r+"\tTo currency:"+cT+"\tAmount to:"+aT+"\n");  
            }
            return sb.toString();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return "";
    }
    
    public void fillStockTable(){
        try{
            pst = conn.prepareStatement("truncate table stock");
            pst.execute();
                        
            pst = conn.prepareStatement("INSERT INTO stock (code) SELECT RateName FROM rates");
            pst.executeUpdate();
            
            pst = conn.prepareStatement("UPDATE stock SET stock = 0");
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
	
    public void updateStock(double amount,int stockId) {
        System.out.println(stockId);
        try{
            pst = conn.prepareStatement("UPDATE stock SET stock = ? WHERE stockid = ?");
            pst.setDouble(1, amount);
            pst.setInt(2, stockId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
    
//    public Object[][] getStock(){
//        Object[][] ret;
//        try {
//            pst = conn.prepareStatement("SELECT * FROM stock");
//            ResultSet res = pst.executeQuery();
//            res.last();
//            
//            int i = res.getRow();
//        
//            ret = new Object[i - 1][2];
//            res.beforeFirst();
//            int j = 0;
//            while (res.next()) {
//                 String code = res.getString(2);
//                 double amount = res.getDouble(3);
//                 ret[j][0]=code;
//                 ret[j][1]=amount;
//                 j++;
//            }
//            return ret;
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        } finally {
//            if (pst != null) {
//                try {
//                    pst.close();
//                } catch (SQLException e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//        }
//        return null;
//        
//    }
//            
//    public static void main(String argv[]){
//		DataBase db = new DataBase();
//                //db.updateCurrency();
//                //db.fillStockTable();
//                long date = new java.util.Date().getTime();
//                Date d = new Date(date);
//                //db.updateLog(d, "USD", 100, 1.56, "BGR", 156);
//                //System.out.println(db.getLog(d));
//                //System.out.println(new Date(date));
//                Object[][] a = db.getStock();
//                for (int i=0;i<a.length;i++){
//                    System.out.println(Arrays.toString(a[i]));
//                }
//                //db.fillStockTable();
//                //db.updateStock(123, "USD");
//                
//	}
}