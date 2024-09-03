/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lms.com.views;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import lms.com.dao.ClientDao;
import lms.com.enums.ClientCategory;
import lms.com.models.BookTransaction;
import lms.com.models.Client;

/**
 *
 * @author Schadrack-Olivet
 */
public class ClientForm extends javax.swing.JInternalFrame {
    String defaultFilePath ="src\\icons3.jpg";
    String imagePath = defaultFilePath;
    final String[] CLIENTCOLUMNS = {
        "RegNo.",
        "Firstname",
        "Lastname",
        "E-mail",
        "Phone number",
        "Category"
    }; 
    final String[] BOOKTRANSACTIONCOLUMNS = {
        "Trans Id",
        "Transaction Date",
        "Return Date",
        "Trans Type",
        "Book Returned",
        "Book"
    };
    
    Client client;   
    ClientDao clientDao;
    public ClientForm() {
        initComponents();
        updateClientBtn.setEnabled(false);
        deleteClientBtn.setEnabled(false);
        clientCategoryField.addItem(ClientCategory.STAFF.toString());
        clientCategoryField.addItem(ClientCategory.STUDENT.toString());
        
        JTableHeader clientsTableHeader = clientsTable.getTableHeader();
        clientsTableHeader.setFont(new Font(null, Font.BOLD, 12));
        
        
        clientNewPhoto.setIcon(resizeImage(imagePath, null));
        
        fillClientsTable();
        fillClientTransactions(null);
    }
    
    public void resetForm(){
        clientRegNoField.setText("  Registration no ....");
        clientFirstNameField.setText("  First name ....");
        clientLastNameField.setText("  Last name ....");
        clientPhoneField.setText("  Phone number ....");
        clientEmailField.setText("  E-mail ....");
        clientCategoryField.setSelectedIndex(0);
        
        imagePath = defaultFilePath;
        clientNewPhoto.setIcon(resizeImage(imagePath, null));
        saveClientBtn.setEnabled(true);
        updateClientBtn.setEnabled(false);
        deleteClientBtn.setEnabled(false);
        clientRegNoField.setEditable(true);
    }
    
    private void fillClientsTable(){
        DefaultTableModel clientsModel = new DefaultTableModel(CLIENTCOLUMNS, 0);
        clientDao = new ClientDao();
        List<Client> clients = clientDao.retrieveClients();
        Iterator<Client> clientsIter = clients.iterator();
        while(clientsIter.hasNext()){
            client = clientsIter.next();
            
            String[] rowData = {
                client.getRegId(), client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getPhoneNumber(), client.getClientCategory()              
            };
            
            clientsModel.addRow(rowData);
        }
        clientsTable.setModel(clientsModel);     
    }
    
    private ImageIcon resizeImage(String filePath, byte[] pic){
        ImageIcon myImage;
        if(filePath!=null){
            myImage = new ImageIcon(filePath);
        }else{
            myImage = new ImageIcon(pic);
        }
        
        Image img = myImage.getImage();
        Image newImage;
        newImage = img.getScaledInstance(190, 200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        
        return image;
    }
    private void fillClientTransactions(List<BookTransaction> bookTransactions){
        DefaultTableModel transModel = new DefaultTableModel(BOOKTRANSACTIONCOLUMNS, 0);
        if(bookTransactions!=null){
           Iterator<BookTransaction> bookTransIter = bookTransactions.iterator();
           String returnedDate;
           String bookReturned;
           while(bookTransIter.hasNext()){
               returnedDate="";
               bookReturned = "no";
               BookTransaction bookTransaction = bookTransIter.next();
               if(bookTransaction.getReturnDate()!=null){
                   returnedDate = String.valueOf(bookTransaction.getReturnDate());
               }
               if(bookTransaction.isBookReturned()){
                   bookReturned="yes";
               }
               
               String[] rowData = {
                   String.valueOf(bookTransaction.getTransId()),
                   String.valueOf(bookTransaction.getTransactionDate()),
                   returnedDate,
                   bookTransaction.getTransactionType(),                   
                   bookReturned,
                   bookTransaction.getBook().getBookId()+ " " + bookTransaction.getBook().getTitle(),
               };
               transModel.addRow(rowData);
           }
        }
        clientTransactionsTable.setModel(transModel);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        searchClientKey = new javax.swing.JComboBox<>();
        searchTextFieldClient = new javax.swing.JTextField();
        searchClientBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        clientsScrollPane = new javax.swing.JScrollPane();
        clientsTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        saveClientBtn = new javax.swing.JButton();
        updateClientBtn = new javax.swing.JButton();
        resetFormBtn = new javax.swing.JButton();
        deleteClientBtn = new javax.swing.JButton();
        importClientsBtn = new javax.swing.JButton();
        exportClientsBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        clientRegNoField = new javax.swing.JTextField();
        clientFirstNameField = new javax.swing.JTextField();
        clientLastNameField = new javax.swing.JTextField();
        clientPhoneField = new javax.swing.JTextField();
        clientEmailField = new javax.swing.JTextField();
        clientCategoryField = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        clientNewPhoto = new javax.swing.JLabel();
        changeClientNewPhotoBtn = new javax.swing.JButton();
        cleintRecordUpdateLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        clientProfilePictureLabel = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        clientRegNoLbl = new javax.swing.JLabel();
        clientFirstNameLbl = new javax.swing.JLabel();
        clientLastNameLbl = new javax.swing.JLabel();
        clientPhoneNumberLbl = new javax.swing.JLabel();
        clientEmailLbl = new javax.swing.JLabel();
        clientCategoryLbl = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        listClientNamesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        clientTransactionsTable = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        clientTransactionFrom = new com.toedter.calendar.JDateChooser();
        clientTransactionTo = new com.toedter.calendar.JDateChooser();
        cleintTransactionSearchBtn = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(true);
        setTitle("Clients");
        setFocusable(false);
        setFrameIcon(null);
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(1194, 660));

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(597, 660));

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));

        searchClientKey.setEditable(true);
        searchClientKey.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchClientKey.setBorder(null);
        searchClientKey.setFocusable(false);
        searchClientKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchClientKeyActionPerformed(evt);
            }
        });

        searchTextFieldClient.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchTextFieldClient.setText("  search client ....");
        searchTextFieldClient.setBorder(null);

        searchClientBtn.setBackground(new java.awt.Color(0, 102, 102));
        searchClientBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchClientBtn.setForeground(new java.awt.Color(255, 255, 255));
        searchClientBtn.setText("Search");
        searchClientBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        searchClientBtn.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(searchClientKey, 0, 168, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextFieldClient, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchClientBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(searchTextFieldClient)
            .addComponent(searchClientKey, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        clientsScrollPane.setBackground(new java.awt.Color(0, 51, 51));
        clientsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        clientsScrollPane.setAutoscrolls(true);
        clientsScrollPane.setFocusable(false);

        clientsTable.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        clientsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        clientsTable.setFillsViewportHeight(true);
        clientsTable.setFocusable(false);
        clientsTable.setRowHeight(20);
        clientsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientsTableMouseClicked(evt);
            }
        });
        clientsScrollPane.setViewportView(clientsTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clientsScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clientsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));

        jPanel6.setBackground(new java.awt.Color(0, 51, 51));
        jPanel6.setFocusable(false);

        saveClientBtn.setBackground(new java.awt.Color(0, 51, 102));
        saveClientBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        saveClientBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveClientBtn.setText("Save client");
        saveClientBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        saveClientBtn.setFocusable(false);
        saveClientBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveClientBtnActionPerformed(evt);
            }
        });

        updateClientBtn.setBackground(new java.awt.Color(153, 102, 0));
        updateClientBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updateClientBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateClientBtn.setText("Update client");
        updateClientBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        updateClientBtn.setFocusable(false);
        updateClientBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        updateClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateClientBtnActionPerformed(evt);
            }
        });

        resetFormBtn.setBackground(new java.awt.Color(51, 51, 51));
        resetFormBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        resetFormBtn.setForeground(new java.awt.Color(255, 255, 255));
        resetFormBtn.setText("Reset form");
        resetFormBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        resetFormBtn.setFocusable(false);
        resetFormBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        resetFormBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFormBtnActionPerformed(evt);
            }
        });

        deleteClientBtn.setBackground(new java.awt.Color(102, 0, 0));
        deleteClientBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        deleteClientBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteClientBtn.setText("Delete client");
        deleteClientBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        deleteClientBtn.setFocusable(false);
        deleteClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteClientBtnActionPerformed(evt);
            }
        });

        importClientsBtn.setBackground(new java.awt.Color(0, 51, 102));
        importClientsBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        importClientsBtn.setForeground(new java.awt.Color(255, 255, 255));
        importClientsBtn.setText("Import clients");
        importClientsBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        importClientsBtn.setFocusable(false);

        exportClientsBtn.setBackground(new java.awt.Color(51, 51, 0));
        exportClientsBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        exportClientsBtn.setForeground(new java.awt.Color(255, 255, 255));
        exportClientsBtn.setText("export clients");
        exportClientsBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        exportClientsBtn.setFocusable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(saveClientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(updateClientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resetFormBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
            .addComponent(deleteClientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(importClientsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exportClientsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(saveClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetFormBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(importClientsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportClientsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 51, 51));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        clientRegNoField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientRegNoField.setText("  Registration no ....");
        clientRegNoField.setBorder(null);

        clientFirstNameField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientFirstNameField.setText("  First name ....");
        clientFirstNameField.setBorder(null);

        clientLastNameField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientLastNameField.setText("  Last name ....");
        clientLastNameField.setBorder(null);
        clientLastNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientLastNameFieldActionPerformed(evt);
            }
        });

        clientPhoneField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientPhoneField.setText("  Phone number ....");
        clientPhoneField.setBorder(null);
        clientPhoneField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientPhoneFieldActionPerformed(evt);
            }
        });

        clientEmailField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientEmailField.setText("  E-mail ....");
        clientEmailField.setBorder(null);
        clientEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientEmailFieldActionPerformed(evt);
            }
        });

        clientCategoryField.setEditable(true);
        clientCategoryField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientCategoryField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Client category ...." }));
        clientCategoryField.setBorder(null);
        clientCategoryField.setFocusable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientLastNameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clientRegNoField)
                    .addComponent(clientFirstNameField)
                    .addComponent(clientPhoneField)
                    .addComponent(clientEmailField)
                    .addComponent(clientCategoryField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientRegNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientFirstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientLastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientPhoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientCategoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 51, 51));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        clientNewPhoto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clientNewPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientNewPhoto.setPreferredSize(new java.awt.Dimension(160, 200));

        changeClientNewPhotoBtn.setBackground(new java.awt.Color(51, 51, 0));
        changeClientNewPhotoBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        changeClientNewPhotoBtn.setForeground(new java.awt.Color(255, 255, 255));
        changeClientNewPhotoBtn.setText("change picture");
        changeClientNewPhotoBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        changeClientNewPhotoBtn.setFocusable(false);
        changeClientNewPhotoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeClientNewPhotoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeClientNewPhotoBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
            .addComponent(clientNewPhoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(clientNewPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changeClientNewPhotoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cleintRecordUpdateLabel.setBackground(new java.awt.Color(0, 51, 51));
        cleintRecordUpdateLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cleintRecordUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        cleintRecordUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cleintRecordUpdateLabel.setText("Record new /update existing client information");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(cleintRecordUpdateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cleintRecordUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(597, 660));

        jPanel9.setBackground(new java.awt.Color(0, 51, 51));

        jPanel11.setBackground(new java.awt.Color(0, 51, 51));

        clientProfilePictureLabel.setBackground(new java.awt.Color(0, 51, 51));
        clientProfilePictureLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientProfilePictureLabel.setForeground(new java.awt.Color(255, 255, 255));
        clientProfilePictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientProfilePictureLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        clientProfilePictureLabel.setOpaque(true);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(clientProfilePictureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientProfilePictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(0, 51, 51));

        clientRegNoLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientRegNoLbl.setForeground(new java.awt.Color(255, 255, 255));

        clientFirstNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientFirstNameLbl.setForeground(new java.awt.Color(255, 255, 255));

        clientLastNameLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientLastNameLbl.setForeground(new java.awt.Color(255, 255, 255));

        clientPhoneNumberLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientPhoneNumberLbl.setForeground(new java.awt.Color(255, 255, 255));

        clientEmailLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientEmailLbl.setForeground(new java.awt.Color(255, 255, 255));

        clientCategoryLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientCategoryLbl.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientCategoryLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientEmailLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientPhoneNumberLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientLastNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientFirstNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(clientRegNoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientRegNoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientFirstNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientLastNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(clientPhoneNumberLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(clientEmailLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientCategoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(0, 51, 51));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        listClientNamesLabel.setBackground(new java.awt.Color(0, 51, 51));
        listClientNamesLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        listClientNamesLabel.setForeground(new java.awt.Color(255, 255, 255));
        listClientNamesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        listClientNamesLabel.setText("List of transactions for client names");
        listClientNamesLabel.setOpaque(true);

        jScrollPane2.setBackground(new java.awt.Color(0, 51, 51));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(0, 51, 51));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        clientTransactionsTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        clientTransactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        clientTransactionsTable.setFillsViewportHeight(true);
        jScrollPane2.setViewportView(clientTransactionsTable);

        jPanel13.setBackground(new java.awt.Color(0, 51, 51));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel7.setBackground(new java.awt.Color(0, 102, 102));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("From:");

        jLabel8.setBackground(new java.awt.Color(0, 102, 102));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("To:");

        clientTransactionFrom.setBackground(new java.awt.Color(255, 255, 255));
        clientTransactionFrom.setFocusable(false);
        clientTransactionFrom.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        clientTransactionTo.setBackground(new java.awt.Color(255, 255, 255));
        clientTransactionTo.setFocusable(false);
        clientTransactionTo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        cleintTransactionSearchBtn.setBackground(new java.awt.Color(51, 51, 51));
        cleintTransactionSearchBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cleintTransactionSearchBtn.setForeground(new java.awt.Color(255, 255, 255));
        cleintTransactionSearchBtn.setText("search");
        cleintTransactionSearchBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        cleintTransactionSearchBtn.setFocusable(false);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientTransactionFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientTransactionTo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cleintTransactionSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clientTransactionTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                    .addComponent(cleintTransactionSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(9, 9, 9))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(clientTransactionFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(listClientNamesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(listClientNamesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clientEmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientEmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientEmailFieldActionPerformed

    private void clientLastNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientLastNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientLastNameFieldActionPerformed

    private void clientPhoneFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientPhoneFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientPhoneFieldActionPerformed

    private void updateClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateClientBtnActionPerformed
        // TODO add your handling code here:
        String regNo = clientRegNoField.getText().trim();
        String firstName = clientFirstNameField.getText().trim();
        String lastName = clientLastNameField.getText().trim();
        String phoneNumber = clientPhoneField.getText().trim();
        String email = clientEmailField.getText().trim();
        String clientCategory = clientCategoryField.getSelectedItem().toString().trim();
        
        try{
            Long.valueOf(regNo);
            if(regNo.length()!=6){
                JOptionPane.showMessageDialog(null, "Client registration number must be 6 characters and cannot contain hyphen(-)!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(firstName.length()>40 || firstName.length()<2){
                JOptionPane.showMessageDialog(null, "Client firstname must be 2 minimum characters or 40 maximum characters", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(lastName.length()>40 || lastName.length()<2){
                JOptionPane.showMessageDialog(null, "Client lastname must be 2 minimum characters or 40 maximum characters", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(phoneNumber.length()!=13){
                JOptionPane.showMessageDialog(null, "Client phone number must be 12 digits and + symbol in front!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(clientCategoryField.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Please choose client category", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else{
               clientDao = new ClientDao();
               client = new Client();
               client.setRegId(regNo);
               client.setFirstName(firstName);
               client.setLastName(lastName);
               client.setEmail(email);
               client.setClientCategory(clientCategory);
               client.setPhoneNumber(phoneNumber);
               
               File file = new File(imagePath);
               InputStream in = new FileInputStream(file);
               byte[] bytes = new byte[(int)file.length()];
               int i=0;
               while(i<(int)file.length()){
                   bytes[i]= (byte) in.read();
                   i++;
               }
               client.setPhoto(bytes);
               
               
               clientDao.updateClient(client);
               fillClientsTable();
               
            }
        }catch(HeadlessException | NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Client registration number must contain digits only", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_updateClientBtnActionPerformed

    private void resetFormBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFormBtnActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_resetFormBtnActionPerformed

    private void searchClientKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchClientKeyActionPerformed
        // TODO add your handling code here:
        searchTextFieldClient.setText("  search client by " + searchClientKey.getSelectedItem().toString() + " ....");
    }//GEN-LAST:event_searchClientKeyActionPerformed

    private void saveClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveClientBtnActionPerformed
        // TODO add your handling code here:
        String regNo = clientRegNoField.getText().trim();
        String firstName = clientFirstNameField.getText().trim();
        String lastName = clientLastNameField.getText().trim();
        String phoneNumber = clientPhoneField.getText().trim();
        String email = clientEmailField.getText().trim();
        String clientCategory = clientCategoryField.getSelectedItem().toString().trim();
        
        try{
            Long.valueOf(regNo);
            if(regNo.length()!=6){
                JOptionPane.showMessageDialog(null, "Client registration number must be 6 characters and cannot contain hyphen(-)!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(firstName.length()>40 || firstName.length()<2){
                JOptionPane.showMessageDialog(null, "Client firstname must be 2 minimum characters or 40 maximum characters", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(lastName.length()>40 || lastName.length()<2){
                JOptionPane.showMessageDialog(null, "Client lastname must be 2 minimum characters or 40 maximum characters", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(phoneNumber.length()!=13){
                JOptionPane.showMessageDialog(null, "Client phone number must be 12 digits and + symbol in front!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(clientCategoryField.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Please choose client category", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
            }else{
               clientDao = new ClientDao();
               client = new Client();
               client.setRegId(regNo);
               client.setFirstName(firstName);
               client.setLastName(lastName);
               client.setEmail(email);
               client.setClientCategory(clientCategory);
               client.setPhoneNumber(phoneNumber);
               
               File file = new File(imagePath);
               InputStream in = new FileInputStream(file);
               byte[] bytes = new byte[(int)file.length()];
               int i=0;
               while(i<(int)file.length()){
                   bytes[i]= (byte) in.read();
                   i++;
               }
               client.setPhoto(bytes);
               
               
               clientDao.saveClient(client);
               fillClientsTable();
               
            }
        }catch(HeadlessException | NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Client registration number must contain digits only", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveClientBtnActionPerformed

    private void changeClientNewPhotoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeClientNewPhotoBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choosing profile picture");
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            if(imagePath.endsWith(".jpg")|| imagePath.endsWith(".png") || imagePath.endsWith(".jpeg")){               
                clientNewPhoto.setIcon(resizeImage(imagePath, null));
            }else{
                imagePath = defaultFilePath;
                clientNewPhoto.setIcon(resizeImage(imagePath, null));
                JOptionPane.showMessageDialog(null, "Please choose valid image, accepted image format(.jpg, jpeg, png) !");
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "You didn't choose image for profile picture!");
        }
        
    }//GEN-LAST:event_changeClientNewPhotoBtnActionPerformed

    private void clientsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientsTableMouseClicked
        // TODO add your handling code here:
        saveClientBtn.setEnabled(false);
        updateClientBtn.setEnabled(true);
        deleteClientBtn.setEnabled(true);
        clientRegNoField.setEditable(false);
        int currentRow = clientsTable.getSelectedRow();
        clientDao = new ClientDao();
        if(currentRow>-1){
            String clientRegNo = clientsTable.getModel().getValueAt(currentRow, 0).toString().trim();
            
            client = clientDao.searchClientByRegNo(clientRegNo);
            if(client!=null){
                String firstName = client.getFirstName();
                String lastName = client.getLastName();
                String phone = client.getPhoneNumber();
                String email = client.getEmail();
                String clientCategory = client.getClientCategory();
                
                clientRegNoField.setText("  " + clientRegNo);
                clientFirstNameField.setText(client.getFirstName());
                clientLastNameField.setText(client.getLastName());
                clientPhoneField.setText(client.getPhoneNumber());
                clientEmailField.setText(client.getEmail());
                clientCategoryField.setSelectedItem(client.getClientCategory());
                
                if(client.getPhoto()!=null){
                    clientNewPhoto.setIcon(resizeImage(null, client.getPhoto()));
                }
                
                if(client.getPhoto()!=null){
                    clientProfilePictureLabel.setIcon(resizeImage(null, client.getPhoto()));
                }
                clientRegNoLbl.setText("Reg No:  " + client.getRegId());
                clientFirstNameLbl.setText("Firstname:  " + client.getFirstName());
                clientLastNameLbl.setText("Lastname:  " + client.getLastName());
                clientPhoneNumberLbl.setText("Phone number:  " + client.getPhoneNumber());
                clientEmailLbl.setText("E-mail:  " + client.getEmail());
                clientCategoryLbl.setText("Client Category:  " + client.getClientCategory());
                
                listClientNamesLabel.setText("List of transactions for client: #" + client.getRegId() + "-"  + client.getFirstName() + " " + client.getLastName());
                
                if(client.getTransactions().size()>0){
                    fillClientTransactions(client.getTransactions());
                }else{
                   fillClientTransactions(null); 
                }
            
            }
        }
    }//GEN-LAST:event_clientsTableMouseClicked

    private void deleteClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteClientBtnActionPerformed
        // TODO add your handling code here:
        String clientCode = clientRegNoField.getText().trim();
        clientDao = new ClientDao();
        clientDao.deleteClient(clientCode);
        fillClientsTable();
        resetForm();
    }//GEN-LAST:event_deleteClientBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeClientNewPhotoBtn;
    private javax.swing.JLabel cleintRecordUpdateLabel;
    private javax.swing.JButton cleintTransactionSearchBtn;
    private javax.swing.JComboBox<String> clientCategoryField;
    private javax.swing.JLabel clientCategoryLbl;
    private javax.swing.JTextField clientEmailField;
    private javax.swing.JLabel clientEmailLbl;
    private javax.swing.JTextField clientFirstNameField;
    private javax.swing.JLabel clientFirstNameLbl;
    private javax.swing.JTextField clientLastNameField;
    private javax.swing.JLabel clientLastNameLbl;
    private javax.swing.JLabel clientNewPhoto;
    private javax.swing.JTextField clientPhoneField;
    private javax.swing.JLabel clientPhoneNumberLbl;
    private javax.swing.JLabel clientProfilePictureLabel;
    private javax.swing.JTextField clientRegNoField;
    private javax.swing.JLabel clientRegNoLbl;
    private com.toedter.calendar.JDateChooser clientTransactionFrom;
    private com.toedter.calendar.JDateChooser clientTransactionTo;
    private javax.swing.JTable clientTransactionsTable;
    private javax.swing.JScrollPane clientsScrollPane;
    private javax.swing.JTable clientsTable;
    private javax.swing.JButton deleteClientBtn;
    private javax.swing.JButton exportClientsBtn;
    private javax.swing.JButton importClientsBtn;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel listClientNamesLabel;
    private javax.swing.JButton resetFormBtn;
    private javax.swing.JButton saveClientBtn;
    private javax.swing.JButton searchClientBtn;
    private javax.swing.JComboBox<String> searchClientKey;
    private javax.swing.JTextField searchTextFieldClient;
    private javax.swing.JButton updateClientBtn;
    // End of variables declaration//GEN-END:variables
}
