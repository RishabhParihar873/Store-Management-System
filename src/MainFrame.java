import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame
{
    private JPanel mainPanel;
    private JTextField txt_product_name;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField txt_product_input_id;
    private JTextField txt_product_qty;
    private JTextField txt_product_price;
    private JButton searchButton;

    //MAIN BEGINS
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(new MainFrame().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    //Code to Connect with the database.
    Connection con;
    PreparedStatement pst;
    public void Connect()
    {
        try{
            String url = "jdbc:mysql://localhost:3306/store";
            String username = "root";
            String password = "password";
            con = DriverManager.getConnection(url,username,password);
            //System.out.println("Successful connection to Store db");
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    public MainFrame() //This is the main frame, all the methods will be called by this constructor
    {
        Connect();
        //save Button Code Starts Here/
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                String nameOfProduct,quantityOfProduct,priceOfProduct;

                nameOfProduct = txt_product_name.getText();
                quantityOfProduct = txt_product_qty.getText();
                priceOfProduct = txt_product_price.getText();

                String sql = "INSERT INTO products(product_name,product_qty,product_price) VALUES(?,?,?)";
                try {
                    pst = con.prepareStatement(sql);
                    pst.setString(1,nameOfProduct);
                    pst.setString(2,quantityOfProduct);
                    pst.setString(3,priceOfProduct);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record successfully Added.");

                    txt_product_name.setText("");
                    txt_product_name.requestFocus();
                    txt_product_price.setText("");
                    txt_product_qty.setText("");


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Search Button Code Starts Here
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = txt_product_input_id.getText();

                String sql = "SELECT product_name,product_qty,product_price FROM products where product_id = ?";
                try {
                    pst = con.prepareStatement(sql);
                    pst.setString(1,id);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next() == true)
                    {
                        String name = rs.getString(1);
                        String qty = rs.getString(2);
                        String price = rs.getString(3);

                        txt_product_name.setText(name);
                        txt_product_qty.setText(qty);
                        txt_product_price.setText(price);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Invalid id inserted.");
                        txt_product_name.setText(" ");
                        txt_product_qty.setText(" ");
                        txt_product_price.setText(" ");
                    }



                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        //update button starts here
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //String sql = "UPDATE products SET product_id=?,product_name = ?,product_qty = ?,product_price = ?";
                String sql = "UPDATE products SET product_name = ?,product_qty = ?,product_price = ? where product_id=?";
                try {
                    pst = con.prepareStatement(sql);

                    String idOfProduct,nameOfProduct,quantityOfProduct,priceOfProduct;
                    //String nameOfProduct,quantityOfProduct,priceOfProduct;

                    idOfProduct = txt_product_input_id.getText();
                    nameOfProduct = txt_product_name.getText();
                    quantityOfProduct = txt_product_qty.getText();
                    priceOfProduct = txt_product_price.getText();

                    pst = con.prepareStatement(sql);

                    pst.setString(1,nameOfProduct);
                    pst.setString(2,quantityOfProduct);
                    pst.setString(3,priceOfProduct);
                    pst.setString(4,idOfProduct);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated!!");
                    txt_product_name.setText(" ");
                    txt_product_name.requestFocus();
                    txt_product_qty.setText(" ");
                    txt_product_price.setText(" ");
                    txt_product_input_id.setText(" ");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Delete code starts here:
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txt_product_input_id.getText();
                String sql = "DELETE FROM products WHERE product_id=?";
                try {
                    pst = con.prepareStatement(sql);
                    pst.setString(1,id);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Deleted Successfully!!");

                    txt_product_name.setText(" ");
                    txt_product_name.requestFocus();
                    txt_product_qty.setText(" ");
                    txt_product_price.setText(" ");
                    txt_product_input_id.setText(" ");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}
