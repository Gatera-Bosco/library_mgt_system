/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lms.com.views;

import java.awt.Font;
import java.awt.Image;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import lms.com.dao.*;
import lms.com.enums.TransactionType;
import lms.com.models.Book;
import lms.com.models.BookTransaction;
import lms.com.models.Client;

/**
 *
 * @author Schadrack-Olivet
 */
public class OperationsForm extends javax.swing.JInternalFrame {

    Book book;
    BookDao bookDao;
    Client client;
    ClientDao clientDao;
    List<String>allBooks;
    List<String>allClients;
    BookTransaction bookTransaction;
    BookTransactionDao bookTransDao;
    long transIdPassed=-1;
    String[] TRANSACTIONCOLUMNS = {
        "Trans Id",
        "Transaction Date",
        "Return Date",
        "Book Title",
        "Book Author",
        "Book Category",
        "Client Names",
        "Trans Type",
        "Book Returned"
    };
    public OperationsForm(){
        initComponents();
        clientCombobox2.setEnabled(false);
        booksComboBox2.setEnabled(false);
        findClientField.setEnabled(false);
        findClientBtn.setEnabled(false);
        findBookField.setEnabled(false);
        findBookBtn.setEnabled(false);
        saveBtn1.setEnabled(false);
        deletebtn1.setEnabled(false);
        deletebtn.setEnabled(false);
        transIdField1.setEditable(true);
        searchTransBtn.setEnabled(true);
        
        JTableHeader borrowedTransHeader = borrowedTransactionsTable.getTableHeader();
        borrowedTransHeader.setFont(new Font(null, Font.BOLD, 12));
        
        JTableHeader returnedTransHeader = returnedTransactionsTable.getTableHeader();
        returnedTransHeader.setFont(new Font(null, Font.BOLD, 12));
        
        fillCombox(); 
        fillBorrowedTransactionsTable();
        fillReturnedTransactionsTable();
    }
    private void fillCombox(){
        allBooks = new ArrayList<>();
        allClients = new ArrayList<>();
        book = new Book();
        bookDao = new BookDao();
        client = new Client();
        clientDao = new ClientDao();
        List<Book> books = bookDao.retrieveBooks();
        List<Client> clients = clientDao.retrieveClients();
        
        Iterator<Client> clientsIter = clients.iterator();
        Iterator<Book> booksIter = books.iterator();
        
        while(clientsIter.hasNext()){
            client = clientsIter.next();
            clientsCombobox.addItem(client.getRegId() + "-" + client.getFirstName() + " " + client.getLastName());
            allClients.add(client.getRegId() + "-" + client.getFirstName() + " " + client.getLastName());
        }
        
        while(booksIter.hasNext()){
            book = booksIter.next();
            if(book.isAvailable()){
                if(book.getTitle().length()>50){
                    booksCombobox.addItem(book.getBookId() + "-" + book.getTitle().substring(0,50) + "...");
                    allBooks.add(book.getBookId() + "-" + book.getTitle().substring(0,50));
                }else{
                    booksCombobox.addItem(book.getBookId() + "-" + book.getTitle());
                    allBooks.add(book.getBookId() + "-" + book.getTitle());
                }
            }
        } 
        if(allClients.size()>clientsCombobox.getMaximumRowCount()){
            findClientField.setEnabled(true);
            findClientBtn.setEnabled(true);
        }else{
            findClientField.setEnabled(false);
            findClientBtn.setEnabled(false);
        }
        if(allBooks.size()>booksCombobox.getMaximumRowCount()){
            findBookField.setEnabled(true);
            findBookBtn.setEnabled(true);
        }else{
            findBookField.setEnabled(false);
            findBookBtn.setEnabled(false);
        }
    }
    
    private void fillClientCombo(){
        int clientSize=clientsCombobox.getItemCount();
        if(clientSize>0){
            clientsCombobox.removeAllItems();
        }
        clientsCombobox.addItem("choose a client");
        for(int j=0; j<allClients.size(); j++){
            if(allClients.get(j).toLowerCase().contains(findClientField.getText().toLowerCase().trim())){
                clientsCombobox.addItem(allClients.get(j));
            }
        }       
        if(clientsCombobox.getItemCount()==1){
            clientsCombobox.addItem("No clients found....");
        }
    }
    
    private void fillBookCombo(){
        
        int booksSize=booksCombobox.getItemCount();
        if(booksSize>0){
           booksCombobox.removeAllItems();
        }
        booksCombobox.addItem("available books found");
        for(int j=0; j<allBooks.size(); j++){
            if(allBooks.get(j).toLowerCase().contains(findBookField.getText().toLowerCase().trim())){
                booksCombobox.addItem(allBooks.get(j));
            }
        }
        
        if(booksCombobox.getItemCount()==1){
            booksCombobox.addItem("No available books found...");
        }
    }
    
    private void fillBorrowedTransactionsTable(){
        DefaultTableModel transModel = new DefaultTableModel(TRANSACTIONCOLUMNS, 0);
        bookTransDao = new BookTransactionDao();
        List<BookTransaction> bookTransactions ;
        switch (searchTransactionKey.getSelectedIndex()) {
            case 1:
                bookTransactions= bookTransDao.searchByClientId(searchTransactionField.getText().trim());
                break;
            case 2:
                bookTransactions= bookTransDao.searchByClientName(searchTransactionField.getText().trim());
                break;
            case 3:
                bookTransactions= bookTransDao.searchReturnByBook(searchTransactionField.getText().trim());
                break;
            case 4:
                bookTransactions= bookTransDao.searchByBookCategory(searchTransactionField.getText().trim());
                break;
            case 5:
                bookTransactions= bookTransDao.searchReturnByTransDate(new java.sql.Date(jDateChooser1.getDate().getTime()));
                break;
            default:
                bookTransactions= bookTransDao.retrieveBorrowedTransactions();
                break;
        }
        Iterator<BookTransaction> bookTransIter = bookTransactions.iterator();
        
        while(bookTransIter.hasNext()){
            String category = "";
            String dateReturned ="";
            String bookReturned = "no";
            bookTransaction = bookTransIter.next();
            if(bookTransaction.getBook().getCategory()!=null){
                category = bookTransaction.getBook().getCategory().getName();
            }
            if(bookTransaction.getReturnDate()!=null){
                dateReturned = String.valueOf(bookTransaction.getReturnDate());
            }
            if(bookTransaction.isBookReturned()){
                bookReturned="yes";
            }

            String[] rowData = {
                String.valueOf(bookTransaction.getTransId()),
                String.valueOf(bookTransaction.getTransactionDate()),
                dateReturned,
                bookTransaction.getBook().getTitle(),
                bookTransaction.getBook().getAuthor(),
                category,
                bookTransaction.getClient().getFirstName() + " " +  bookTransaction.getClient().getLastName(),
                bookTransaction.getTransactionType(),
                bookReturned                
            };
            transModel.addRow(rowData);  
        }
        borrowedTransactionsTable.setModel(transModel);
    }
    
    private void fillReturnedTransactionsTable(){
        DefaultTableModel transModel = new DefaultTableModel(TRANSACTIONCOLUMNS, 0);
        bookTransDao = new BookTransactionDao();
        List<BookTransaction> bookTransactions = bookTransDao.retrieveReturnedTransactions();
        Iterator<BookTransaction> bookTransIter = bookTransactions.iterator();
        
        while(bookTransIter.hasNext()){
            String category = "";
            String dateReturned ="";
            String bookReturned = "no";
            bookTransaction = bookTransIter.next();
            if(bookTransaction.getBook().getCategory()!=null){
                category = bookTransaction.getBook().getCategory().getName();
            }
            if(bookTransaction.getReturnDate()!=null){
                dateReturned = String.valueOf(bookTransaction.getReturnDate());
            }
            if(bookTransaction.isBookReturned()){
                bookReturned="yes";
            }

            String[] rowData = {
                String.valueOf(bookTransaction.getTransId()),
                String.valueOf(bookTransaction.getTransactionDate()),
                dateReturned,
                bookTransaction.getBook().getTitle(),
                bookTransaction.getBook().getAuthor(),
                category,
                bookTransaction.getClient().getFirstName() + " " +  bookTransaction.getClient().getLastName(),
                bookTransaction.getTransactionType(),
                bookReturned                
            };
            transModel.addRow(rowData);  
        }
        returnedTransactionsTable.setModel(transModel);
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        clientsCombobox = new javax.swing.JComboBox<>();
        booksCombobox = new javax.swing.JComboBox<>();
        saveBtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        transIdField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        findClientField = new javax.swing.JTextField();
        findClientBtn = new javax.swing.JButton();
        findBookField = new javax.swing.JTextField();
        findBookBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        borrowedTransactionsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        searchTransactionKey = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        searchTransactionField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        transactionDateLbl = new javax.swing.JLabel();
        transactionDateValueLbl = new javax.swing.JLabel();
        returnedDateLbl = new javax.swing.JLabel();
        returnedDateLblValue = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        clientProfilePictureLbl = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        clientIdLbl = new javax.swing.JLabel();
        clientNamesLbl = new javax.swing.JLabel();
        clientPhoneLbl = new javax.swing.JLabel();
        clientEmailLbl = new javax.swing.JLabel();
        clientCategoryLbl = new javax.swing.JLabel();
        clientInfoLbl = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        bookAuthorNamesLbl = new javax.swing.JLabel();
        publishingHouseLbl = new javax.swing.JLabel();
        publicationDateLbl = new javax.swing.JLabel();
        bookCategoryLbl = new javax.swing.JLabel();
        bookTitleLbl = new javax.swing.JLabel();
        bookIdLbl = new javax.swing.JLabel();
        bookPagesLbl = new javax.swing.JLabel();
        availableLbl = new javax.swing.JLabel();
        bookInfoLbl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        clientCombobox2 = new javax.swing.JComboBox<>();
        booksComboBox2 = new javax.swing.JComboBox<>();
        saveBtn1 = new javax.swing.JButton();
        deletebtn1 = new javax.swing.JButton();
        transIdField1 = new javax.swing.JTextField();
        searchTransBtn = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        returnedTransactionsTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        searchTransactionKey2 = new javax.swing.JComboBox<>();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton4 = new javax.swing.JButton();
        seachTransactionField2 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        transactionDateLbl2 = new javax.swing.JLabel();
        transactionDateValueLbl2 = new javax.swing.JLabel();
        returnedDateLbl1 = new javax.swing.JLabel();
        returnedDateLblValue1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        clientProfilePictureLbl1 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        clientIdLbl1 = new javax.swing.JLabel();
        clientNamesLbl1 = new javax.swing.JLabel();
        clientPhoneLbl1 = new javax.swing.JLabel();
        clientEmailLbl1 = new javax.swing.JLabel();
        clientCategoryLbl1 = new javax.swing.JLabel();
        clientInfoLbl1 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        bookAuthorNamesLbl1 = new javax.swing.JLabel();
        publishingHouseLbl1 = new javax.swing.JLabel();
        publicationDateLbl1 = new javax.swing.JLabel();
        bookCategoryLbl1 = new javax.swing.JLabel();
        bookTitleLbl1 = new javax.swing.JLabel();
        bookIdLbl1 = new javax.swing.JLabel();
        bookPagesLbl1 = new javax.swing.JLabel();
        availableLbl1 = new javax.swing.JLabel();
        bookInfoLbl1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(true);
        setTitle("Book Transactions");
        setFrameIcon(null);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));

        jPanel5.setBackground(new java.awt.Color(0, 51, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel2.setBackground(new java.awt.Color(0, 51, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Record a new borrowed book transaction");

        clientsCombobox.setEditable(true);
        clientsCombobox.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientsCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "choose a client" }));
        clientsCombobox.setBorder(null);
        clientsCombobox.setFocusable(false);
        clientsCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientsComboboxActionPerformed(evt);
            }
        });

        booksCombobox.setEditable(true);
        booksCombobox.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        booksCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "available books" }));
        booksCombobox.setBorder(null);
        booksCombobox.setFocusable(false);
        booksCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksComboboxActionPerformed(evt);
            }
        });
        booksCombobox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                booksComboboxKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                booksComboboxKeyTyped(evt);
            }
        });

        saveBtn.setBackground(new java.awt.Color(102, 153, 255));
        saveBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveBtn.setText("save");
        saveBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        saveBtn.setFocusable(false);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        deletebtn.setBackground(new java.awt.Color(102, 0, 0));
        deletebtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        deletebtn.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn.setText("delete");
        deletebtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        deletebtn.setFocusable(false);
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        transIdField.setEditable(false);
        transIdField.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        transIdField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        transIdField.setBorder(null);

        jButton1.setBackground(new java.awt.Color(51, 0, 51));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Export borrowers");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton1.setFocusable(false);

        findClientField.setText(" client regNo or names ....");
        findClientField.setBorder(null);

        findClientBtn.setBackground(new java.awt.Color(51, 51, 0));
        findClientBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        findClientBtn.setForeground(new java.awt.Color(255, 255, 255));
        findClientBtn.setText("Find");
        findClientBtn.setBorder(null);
        findClientBtn.setFocusable(false);
        findClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findClientBtnActionPerformed(evt);
            }
        });

        findBookField.setText(" book Id or title ....");
        findBookField.setBorder(null);

        findBookBtn.setBackground(new java.awt.Color(51, 51, 0));
        findBookBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        findBookBtn.setForeground(new java.awt.Color(255, 255, 255));
        findBookBtn.setText("Find");
        findBookBtn.setBorder(null);
        findBookBtn.setFocusable(false);
        findBookBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBookBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(booksCombobox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(transIdField)
                    .addComponent(clientsCombobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(findClientField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(findBookField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findBookBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(findClientField)
                    .addComponent(findClientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientsCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 26, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(findBookField)
                    .addComponent(findBookBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(booksCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 51, 51));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        borrowedTransactionsTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        borrowedTransactionsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        borrowedTransactionsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        borrowedTransactionsTable.setFillsViewportHeight(true);
        borrowedTransactionsTable.setRowHeight(20);
        borrowedTransactionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrowedTransactionsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(borrowedTransactionsTable);

        jLabel1.setBackground(new java.awt.Color(0, 102, 102));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("List of borrowed book transactions");

        jPanel7.setBackground(new java.awt.Color(0, 51, 51));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        searchTransactionKey.setEditable(true);
        searchTransactionKey.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        searchTransactionKey.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "......", "Client Id", "Client Name", "BookId or Title", "Book category Code or Name", "Transaction date or Return date" }));
        searchTransactionKey.setBorder(null);
        searchTransactionKey.setFocusable(false);
        searchTransactionKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTransactionKeyActionPerformed(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setFocusable(false);
        jDateChooser1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton3.setBackground(new java.awt.Color(153, 153, 153));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("search");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        searchTransactionField.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        searchTransactionField.setBorder(null);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(searchTransactionKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchTransactionField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchTransactionKey, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(searchTransactionField))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 51, 51));

        jPanel8.setBackground(new java.awt.Color(0, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jPanel9.setBackground(new java.awt.Color(0, 51, 51));
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));

        transactionDateLbl.setBackground(new java.awt.Color(0, 51, 51));
        transactionDateLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        transactionDateLbl.setForeground(new java.awt.Color(255, 255, 255));
        transactionDateLbl.setOpaque(true);

        transactionDateValueLbl.setBackground(new java.awt.Color(0, 51, 51));
        transactionDateValueLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        transactionDateValueLbl.setForeground(new java.awt.Color(255, 255, 255));
        transactionDateValueLbl.setOpaque(true);

        returnedDateLbl.setBackground(new java.awt.Color(0, 51, 51));
        returnedDateLbl.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        returnedDateLbl.setForeground(new java.awt.Color(255, 255, 255));
        returnedDateLbl.setOpaque(true);

        returnedDateLblValue.setBackground(new java.awt.Color(0, 51, 51));
        returnedDateLblValue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        returnedDateLblValue.setForeground(new java.awt.Color(255, 255, 255));
        returnedDateLblValue.setOpaque(true);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(returnedDateLblValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(returnedDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transactionDateLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(transactionDateValueLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(transactionDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionDateValueLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(returnedDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnedDateLblValue, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(0, 51, 51));

        clientProfilePictureLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientProfilePictureLbl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        clientProfilePictureLbl.setOpaque(true);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clientProfilePictureLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientProfilePictureLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(0, 51, 51));
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));

        clientIdLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientIdLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientIdLbl.setForeground(new java.awt.Color(255, 255, 255));
        clientIdLbl.setOpaque(true);

        clientNamesLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientNamesLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientNamesLbl.setForeground(new java.awt.Color(255, 255, 255));
        clientNamesLbl.setOpaque(true);

        clientPhoneLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientPhoneLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientPhoneLbl.setForeground(new java.awt.Color(255, 255, 255));
        clientPhoneLbl.setOpaque(true);

        clientEmailLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientEmailLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientEmailLbl.setForeground(new java.awt.Color(255, 255, 255));
        clientEmailLbl.setOpaque(true);

        clientCategoryLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientCategoryLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientCategoryLbl.setForeground(new java.awt.Color(255, 255, 255));
        clientCategoryLbl.setOpaque(true);

        clientInfoLbl.setBackground(new java.awt.Color(0, 51, 51));
        clientInfoLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientInfoLbl.setForeground(new java.awt.Color(255, 255, 255));
        clientInfoLbl.setOpaque(true);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientNamesLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientPhoneLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(clientCategoryLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientEmailLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientIdLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientInfoLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(clientInfoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientIdLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientNamesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientPhoneLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientEmailLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientCategoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(0, 51, 51));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel12.setForeground(new java.awt.Color(255, 255, 255));

        bookAuthorNamesLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookAuthorNamesLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookAuthorNamesLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookAuthorNamesLbl.setOpaque(true);

        publishingHouseLbl.setBackground(new java.awt.Color(0, 51, 51));
        publishingHouseLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        publishingHouseLbl.setForeground(new java.awt.Color(255, 255, 255));
        publishingHouseLbl.setOpaque(true);

        publicationDateLbl.setBackground(new java.awt.Color(0, 51, 51));
        publicationDateLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        publicationDateLbl.setForeground(new java.awt.Color(255, 255, 255));
        publicationDateLbl.setOpaque(true);

        bookCategoryLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookCategoryLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookCategoryLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookCategoryLbl.setOpaque(true);

        bookTitleLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookTitleLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookTitleLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookTitleLbl.setOpaque(true);

        bookIdLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookIdLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookIdLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookIdLbl.setOpaque(true);

        bookPagesLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookPagesLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookPagesLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookPagesLbl.setOpaque(true);

        availableLbl.setBackground(new java.awt.Color(0, 51, 51));
        availableLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        availableLbl.setForeground(new java.awt.Color(255, 255, 255));
        availableLbl.setOpaque(true);

        bookInfoLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookInfoLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookInfoLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookInfoLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookInfoLbl.setOpaque(true);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookTitleLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bookCategoryLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(publicationDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(publishingHouseLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                            .addComponent(bookAuthorNamesLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookIdLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookPagesLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(availableLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bookInfoLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(bookInfoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookTitleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bookAuthorNamesLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookIdLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(publishingHouseLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookPagesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(availableLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(publicationDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(bookCategoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Borrowed list", jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jPanel13.setBackground(new java.awt.Color(0, 51, 51));

        jPanel14.setBackground(new java.awt.Color(0, 51, 51));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel6.setBackground(new java.awt.Color(0, 51, 51));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Record a new returned book transaction");

        clientCombobox2.setEditable(true);
        clientCombobox2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientCombobox2.setBorder(null);
        clientCombobox2.setFocusable(false);
        clientCombobox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientCombobox2ActionPerformed(evt);
            }
        });

        booksComboBox2.setEditable(true);
        booksComboBox2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        booksComboBox2.setBorder(null);
        booksComboBox2.setFocusable(false);

        saveBtn1.setBackground(new java.awt.Color(102, 153, 255));
        saveBtn1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        saveBtn1.setForeground(new java.awt.Color(255, 255, 255));
        saveBtn1.setText("save");
        saveBtn1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        saveBtn1.setFocusable(false);
        saveBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtn1ActionPerformed(evt);
            }
        });

        deletebtn1.setBackground(new java.awt.Color(102, 0, 0));
        deletebtn1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        deletebtn1.setForeground(new java.awt.Color(255, 255, 255));
        deletebtn1.setText("delete");
        deletebtn1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        deletebtn1.setFocusable(false);
        deletebtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtn1ActionPerformed(evt);
            }
        });

        transIdField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        transIdField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        transIdField1.setText("borrowed transaction Id ...");
        transIdField1.setBorder(null);

        searchTransBtn.setBackground(new java.awt.Color(102, 102, 102));
        searchTransBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searchTransBtn.setText("search");
        searchTransBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        searchTransBtn.setFocusable(false);
        searchTransBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTransBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientCombobox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(transIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTransBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(booksComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(saveBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(deletebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transIdField1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(searchTransBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(clientCombobox2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(booksComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jPanel15.setBackground(new java.awt.Color(0, 51, 51));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        returnedTransactionsTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        returnedTransactionsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        returnedTransactionsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        returnedTransactionsTable.setFillsViewportHeight(true);
        returnedTransactionsTable.setRowHeight(20);
        returnedTransactionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnedTransactionsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(returnedTransactionsTable);

        jLabel7.setBackground(new java.awt.Color(0, 102, 102));
        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("List of returned book transactions");

        jPanel16.setBackground(new java.awt.Color(0, 51, 51));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        searchTransactionKey2.setEditable(true);
        searchTransactionKey2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        searchTransactionKey2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Client Id", "Client Name", "BookId or Title", "Book category Code or Name", "Transaction date" }));
        searchTransactionKey2.setBorder(null);
        searchTransactionKey2.setFocusable(false);
        searchTransactionKey2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTransactionKey2ActionPerformed(evt);
            }
        });

        jDateChooser2.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser2.setFocusable(false);
        jDateChooser2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton4.setBackground(new java.awt.Color(153, 153, 153));
        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("search");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton4.setFocusable(false);

        seachTransactionField2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        seachTransactionField2.setBorder(null);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTransactionKey2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seachTransactionField2, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchTransactionKey2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(seachTransactionField2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(0, 51, 51));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jPanel19.setBackground(new java.awt.Color(0, 51, 51));
        jPanel19.setForeground(new java.awt.Color(255, 255, 255));

        transactionDateLbl2.setBackground(new java.awt.Color(0, 51, 51));
        transactionDateLbl2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        transactionDateLbl2.setForeground(new java.awt.Color(255, 255, 255));
        transactionDateLbl2.setOpaque(true);

        transactionDateValueLbl2.setBackground(new java.awt.Color(0, 51, 51));
        transactionDateValueLbl2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        transactionDateValueLbl2.setForeground(new java.awt.Color(255, 255, 255));
        transactionDateValueLbl2.setOpaque(true);

        returnedDateLbl1.setBackground(new java.awt.Color(0, 51, 51));
        returnedDateLbl1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        returnedDateLbl1.setForeground(new java.awt.Color(255, 255, 255));
        returnedDateLbl1.setOpaque(true);

        returnedDateLblValue1.setBackground(new java.awt.Color(0, 51, 51));
        returnedDateLblValue1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        returnedDateLblValue1.setForeground(new java.awt.Color(255, 255, 255));
        returnedDateLblValue1.setOpaque(true);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transactionDateLbl2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(transactionDateValueLbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(returnedDateLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(returnedDateLblValue1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(transactionDateLbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionDateValueLbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(returnedDateLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnedDateLblValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(0, 51, 51));

        clientProfilePictureLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientProfilePictureLbl1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        clientProfilePictureLbl1.setOpaque(true);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clientProfilePictureLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientProfilePictureLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(0, 51, 51));
        jPanel21.setForeground(new java.awt.Color(255, 255, 255));

        clientIdLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientIdLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientIdLbl1.setForeground(new java.awt.Color(255, 255, 255));
        clientIdLbl1.setOpaque(true);

        clientNamesLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientNamesLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientNamesLbl1.setForeground(new java.awt.Color(255, 255, 255));
        clientNamesLbl1.setOpaque(true);

        clientPhoneLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientPhoneLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientPhoneLbl1.setForeground(new java.awt.Color(255, 255, 255));
        clientPhoneLbl1.setOpaque(true);

        clientEmailLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientEmailLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientEmailLbl1.setForeground(new java.awt.Color(255, 255, 255));
        clientEmailLbl1.setOpaque(true);

        clientCategoryLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientCategoryLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientCategoryLbl1.setForeground(new java.awt.Color(255, 255, 255));
        clientCategoryLbl1.setOpaque(true);

        clientInfoLbl1.setBackground(new java.awt.Color(0, 51, 51));
        clientInfoLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        clientInfoLbl1.setForeground(new java.awt.Color(255, 255, 255));
        clientInfoLbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientInfoLbl1.setOpaque(true);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientNamesLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientPhoneLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(clientCategoryLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientEmailLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientIdLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientInfoLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(clientInfoLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientIdLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientNamesLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientPhoneLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientEmailLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientCategoryLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel22.setBackground(new java.awt.Color(0, 51, 51));
        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel22.setForeground(new java.awt.Color(255, 255, 255));

        bookAuthorNamesLbl1.setBackground(new java.awt.Color(0, 51, 51));
        bookAuthorNamesLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookAuthorNamesLbl1.setForeground(new java.awt.Color(255, 255, 255));
        bookAuthorNamesLbl1.setOpaque(true);

        publishingHouseLbl1.setBackground(new java.awt.Color(0, 51, 51));
        publishingHouseLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        publishingHouseLbl1.setForeground(new java.awt.Color(255, 255, 255));
        publishingHouseLbl1.setOpaque(true);

        publicationDateLbl1.setBackground(new java.awt.Color(0, 51, 51));
        publicationDateLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        publicationDateLbl1.setForeground(new java.awt.Color(255, 255, 255));
        publicationDateLbl1.setOpaque(true);

        bookCategoryLbl1.setBackground(new java.awt.Color(0, 51, 51));
        bookCategoryLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookCategoryLbl1.setForeground(new java.awt.Color(255, 255, 255));
        bookCategoryLbl1.setOpaque(true);

        bookTitleLbl1.setBackground(new java.awt.Color(0, 51, 51));
        bookTitleLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookTitleLbl1.setForeground(new java.awt.Color(255, 255, 255));
        bookTitleLbl1.setOpaque(true);

        bookIdLbl1.setBackground(new java.awt.Color(0, 51, 51));
        bookIdLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookIdLbl1.setForeground(new java.awt.Color(255, 255, 255));
        bookIdLbl1.setOpaque(true);

        bookPagesLbl1.setBackground(new java.awt.Color(0, 51, 51));
        bookPagesLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookPagesLbl1.setForeground(new java.awt.Color(255, 255, 255));
        bookPagesLbl1.setOpaque(true);

        availableLbl1.setBackground(new java.awt.Color(0, 51, 51));
        availableLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        availableLbl1.setForeground(new java.awt.Color(255, 255, 255));
        availableLbl1.setOpaque(true);

        bookInfoLbl1.setBackground(new java.awt.Color(0, 51, 51));
        bookInfoLbl1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookInfoLbl1.setForeground(new java.awt.Color(255, 255, 255));
        bookInfoLbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookInfoLbl1.setOpaque(true);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookTitleLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bookCategoryLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(publicationDateLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(publishingHouseLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookAuthorNamesLbl1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookIdLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookPagesLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(availableLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bookInfoLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(bookInfoLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookTitleLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bookAuthorNamesLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookIdLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(publishingHouseLbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bookPagesLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(availableLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(publicationDateLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(bookCategoryLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        jTabbedPane1.addTab("Returned list", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clientsComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsComboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientsComboboxActionPerformed

    private void clientCombobox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientCombobox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientCombobox2ActionPerformed

    private void searchTransactionKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTransactionKeyActionPerformed
        // TODO add your handling code here:
        searchTransactionField.setText(" seach transaction by " + searchTransactionKey.getSelectedItem().toString() + " ....");
    }//GEN-LAST:event_searchTransactionKeyActionPerformed

    private void searchTransactionKey2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTransactionKey2ActionPerformed
        // TODO add your handling code here:
        seachTransactionField2.setText("  search transaction by " + searchTransactionKey2.getSelectedItem().toString() + " ....");
    }//GEN-LAST:event_searchTransactionKey2ActionPerformed

    private void booksComboboxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_booksComboboxKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_booksComboboxKeyTyped

    private void booksComboboxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_booksComboboxKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_booksComboboxKeyReleased

    private void booksComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksComboboxActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_booksComboboxActionPerformed

    private void findBookBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBookBtnActionPerformed
        // TODO add your handling code here:
        fillBookCombo();
    }//GEN-LAST:event_findBookBtnActionPerformed

    private void findClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findClientBtnActionPerformed
        // TODO add your handling code here:
        fillClientCombo();
    }//GEN-LAST:event_findClientBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        bookTransaction = new BookTransaction();
        bookTransDao = new BookTransactionDao();
        
        if(clientsCombobox.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null, "You didn't choose a client!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        }else if(booksCombobox.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null, "You didn't choose a book!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        }else{
          String clientRegNo=clientsCombobox.getSelectedItem().toString().split("-")[0].trim();
          String bookId= booksCombobox.getSelectedItem().toString().split("-")[0].trim(); 
          
          Date transDate = Date.valueOf(LocalDate.now());        
          bookTransaction.setTransactionDate(transDate);
          bookTransaction.setTransactionType(TransactionType.BORROW.toString().trim());
          
          bookTransDao.saveBookTransaction(bookTransaction, bookId, clientRegNo);
          
          clientsCombobox.removeAllItems();
          booksCombobox.removeAllItems();
          clientsCombobox.addItem("choose a client");
          booksCombobox.addItem("available books");
          fillCombox();
          fillBorrowedTransactionsTable();
        }
        
    }//GEN-LAST:event_saveBtnActionPerformed

    private void searchTransBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTransBtnActionPerformed
        // TODO add your handling code here:
        deletebtn1.setEnabled(false);
        try{
            long transId = Long.valueOf(transIdField1.getText().trim());
            if(booksComboBox2.getItemCount()>0){
                booksComboBox2.removeAllItems();
            }
            if(clientCombobox2.getItemCount()>0){
                clientCombobox2.removeAllItems();
            }
            bookTransaction = new BookTransaction();
            bookTransDao = new BookTransactionDao();
            bookTransaction = bookTransDao.searchTransactionById(transId);
            if(bookTransaction!=null){
                if(bookTransaction.getTransactionType().equalsIgnoreCase(TransactionType.RETURN.toString())){
                    JOptionPane.showMessageDialog(null, "Transaction found for transaction Id: " + transId + " has has transaction type of " + " RETURN and required transaction type is BORROW");
                }else{
                    if(bookTransaction.isBookReturned()){
                        JOptionPane.showMessageDialog(null, "Transaction found for transaction Id: " + transId + " has no book to return because it has already returned back!");
                    }else{
                        clientCombobox2.addItem(bookTransaction.getClient().getRegId() + "-" + bookTransaction.getClient().getFirstName() + " " + bookTransaction.getClient().getLastName());
                        clientCombobox2.setSelectedIndex(0);
                        if(bookTransaction.getBook().getTitle().length()>50){
                            booksComboBox2.addItem(bookTransaction.getBook().getBookId() + "-" + bookTransaction.getBook().getTitle().substring(0, 50) +"...");
                        }else{
                            booksComboBox2.addItem(bookTransaction.getBook().getBookId() + "-" + bookTransaction.getBook().getTitle());
                        }
                        booksComboBox2.setSelectedIndex(0);
                        transIdField1.setEditable(false);
                        searchTransBtn.setEnabled(false);
                    }
                    saveBtn1.setEnabled(true);
                }
            }else{
                saveBtn1.setEnabled(false);
                transIdField1.setEditable(true);
                searchTransBtn.setEnabled(true);
                JOptionPane.showMessageDialog(null, "No transaction found for transaction Id: " + transId);
            }
            
        }catch(NumberFormatException ex){
           JOptionPane.showMessageDialog(null, "Enter a valid transaction Id", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_searchTransBtnActionPerformed

    private void saveBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtn1ActionPerformed
        // TODO add your handling code here:
       try{ 
            long transId = Long.valueOf(transIdField1.getText().trim());
            String clientCode = clientCombobox2.getSelectedItem().toString().split("-")[0].trim();
            String bookCode = booksComboBox2.getSelectedItem().toString().split("-")[0].trim();
            Date transactionDate = Date.valueOf(LocalDate.now());
            bookTransaction = new BookTransaction();
            bookTransDao = new BookTransactionDao();
            bookTransaction.setBookReturned(true);
            bookTransaction.setReturnDate(transactionDate);
            bookTransaction.setTransactionDate(transactionDate);
            bookTransaction.setTransactionType(TransactionType.RETURN.toString());
                       
            bookTransDao.saveReturnedTransaction(bookTransaction, bookCode, clientCode, transId);
            fillReturnedTransactionsTable();
            
            if(booksComboBox2.getItemCount()>0){
                booksComboBox2.removeAllItems();
            }
            if(clientCombobox2.getItemCount()>0){
                clientCombobox2.removeAllItems();
            }
            saveBtn1.setEnabled(false);
       }catch(NumberFormatException ex){
           JOptionPane.showMessageDialog(null, "Enter a valid transaction Id", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
       }
    }//GEN-LAST:event_saveBtn1ActionPerformed

    private void borrowedTransactionsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowedTransactionsTableMouseClicked
        // TODO add your handling code here:
        bookTransDao = new BookTransactionDao();
        bookTransaction = new BookTransaction();
        int currentRow = borrowedTransactionsTable.getSelectedRow();
        if(currentRow >-1){            
            clientsCombobox.setEnabled(false);
            booksCombobox.setEnabled(false);
            saveBtn.setEnabled(false);
            deletebtn.setEnabled(true);
            String transId = borrowedTransactionsTable.getModel().getValueAt(currentRow, 0).toString().trim();
            transIdField.setText(transId);
            bookTransaction  = bookTransDao.searchTransactionById(Long.valueOf(transId));
            if(bookTransaction!=null){
                clientsCombobox.setSelectedItem(bookTransaction.getClient().getRegId() + "-" + bookTransaction.getClient().getFirstName() + " " + bookTransaction.getClient().getLastName());
                booksCombobox.setSelectedItem(bookTransaction.getBook().getBookId() + "-" + bookTransaction.getBook().getTitle());
                transactionDateLbl.setText("Transaction Date:");
                transactionDateValueLbl.setText(String.valueOf(bookTransaction.getTransactionDate()));
                clientInfoLbl.setText("Client information");
                clientIdLbl.setText("Client RegNo:  " + bookTransaction.getClient().getRegId());
                clientNamesLbl.setText("Names:  " + bookTransaction.getClient().getFirstName() + " " + bookTransaction.getClient().getLastName());
                clientPhoneLbl.setText("Phone number:  " + bookTransaction.getClient().getPhoneNumber());
                clientEmailLbl.setText("E-mail:  " + bookTransaction.getClient().getEmail());
                clientCategoryLbl.setText("Category:  " + bookTransaction.getClient().getClientCategory());
                
                bookInfoLbl.setText("Book Information on transaction");
                bookTitleLbl.setText("Title:  " + bookTransaction.getBook().getTitle());
                bookAuthorNamesLbl.setText("Author:  " + bookTransaction.getBook().getAuthor());
                publishingHouseLbl.setText("Publishing house:  " + bookTransaction.getBook().getPublishingHouse());
                publicationDateLbl.setText("Publication date:  " + String.valueOf(bookTransaction.getBook().getPublicationDate()));
                
                if(bookTransaction.getBook().getCategory()!=null){
                    bookCategoryLbl.setText("Category:  " + bookTransaction.getBook().getCategory().getName());
                }else{
                    bookCategoryLbl.setText("Category:  no category");
                }
                bookIdLbl.setText("Book Id:  " + bookTransaction.getBook().getBookId());
                bookPagesLbl.setText("Pages:  " + String.valueOf(bookTransaction.getBook().getPages()));
                
                if(bookTransaction.getBook().isAvailable()){
                    availableLbl.setText("Book available:  Yes");
                }else{
                    availableLbl.setText("Book available:  No");
                }
                returnedDateLbl.setText("Returned Date:");
                if(bookTransaction.getReturnDate()!=null){
                    returnedDateLblValue.setText(String.valueOf(bookTransaction.getReturnDate()));
                }else{
                    returnedDateLblValue.setText("");
                }
                
                clientProfilePictureLbl.setIcon(resizeImage(null, bookTransaction.getClient().getPhoto()));
                
            }           
        }       
    }//GEN-LAST:event_borrowedTransactionsTableMouseClicked

    private void returnedTransactionsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnedTransactionsTableMouseClicked
        // TODO add your handling code here:        
        bookTransDao = new BookTransactionDao();
        bookTransaction = new BookTransaction();
        int currentRow = returnedTransactionsTable.getSelectedRow();
        if(currentRow >-1){
            transIdField1.setText("borrowed transaction Id ...");
            deletebtn1.setEnabled(true);
            String transId = returnedTransactionsTable.getModel().getValueAt(currentRow, 0).toString().trim();
            transIdField.setText(transId);
            bookTransaction  = bookTransDao.searchTransactionById(Long.valueOf(transId));
            if(bookTransaction!=null){
                clientCombobox2.setSelectedItem(bookTransaction.getClient().getRegId() + "-" + bookTransaction.getClient().getFirstName() + " " + bookTransaction.getClient().getLastName());
                booksComboBox2.setSelectedItem(bookTransaction.getBook().getBookId() + "-" + bookTransaction.getBook().getTitle());
                transactionDateLbl2.setText("Transaction Date:");
                transactionDateValueLbl2.setText(String.valueOf(bookTransaction.getTransactionDate()));
                clientInfoLbl1.setText("Client information");
                clientIdLbl1.setText("Client RegNo:  " + bookTransaction.getClient().getRegId());
                clientNamesLbl1.setText("Names:  " + bookTransaction.getClient().getFirstName() + " " + bookTransaction.getClient().getLastName());
                clientPhoneLbl1.setText("Phone number:  " + bookTransaction.getClient().getPhoneNumber());
                clientEmailLbl1.setText("E-mail:  " + bookTransaction.getClient().getEmail());
                clientCategoryLbl1.setText("Category:  " + bookTransaction.getClient().getClientCategory());
                
                bookInfoLbl1.setText("Book Information on transaction");
                bookTitleLbl1.setText("Title:  " + bookTransaction.getBook().getTitle());
                bookAuthorNamesLbl1.setText("Author:  " + bookTransaction.getBook().getAuthor());
                publishingHouseLbl1.setText("Publishing house:  " + bookTransaction.getBook().getPublishingHouse());
                publicationDateLbl1.setText("Publication date:  " + String.valueOf(bookTransaction.getBook().getPublicationDate()));
                
                if(bookTransaction.getBook().getCategory()!=null){
                    bookCategoryLbl1.setText("Category:  " + bookTransaction.getBook().getCategory().getName());
                }else{
                    bookCategoryLbl1.setText("Category:  no category");
                }
                bookIdLbl1.setText("Book Id:  " + bookTransaction.getBook().getBookId());
                bookPagesLbl1.setText("Pages:  " + String.valueOf(bookTransaction.getBook().getPages()));
                
                if(bookTransaction.getBook().isAvailable()){
                    availableLbl1.setText("Book available:  Yes");
                }else{
                    availableLbl1.setText("Book available:  No");
                }
                returnedDateLbl1.setText("Returned Date:");
                if(bookTransaction.getReturnDate()!=null){
                    returnedDateLblValue1.setText(String.valueOf(bookTransaction.getReturnDate()));
                }else{
                    returnedDateLblValue1.setText("");
                }
                
                clientProfilePictureLbl1.setIcon(resizeImage(null, bookTransaction.getClient().getPhoto()));
                transIdPassed= Long.valueOf(transId.trim());
                
            }           
        }       
    }//GEN-LAST:event_returnedTransactionsTableMouseClicked

    private void deletebtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtn1ActionPerformed
        // TODO add your handling code here:
        bookTransDao = new BookTransactionDao();
        bookTransDao.deleteTransaction(transIdPassed);
        fillReturnedTransactionsTable();
        transIdPassed=-1;
    }//GEN-LAST:event_deletebtn1ActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:       
        bookTransDao = new BookTransactionDao();
        bookTransDao.deleteTransaction(Long.valueOf(transIdField.getText().trim()));
        fillBorrowedTransactionsTable();
    }//GEN-LAST:event_deletebtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        fillBorrowedTransactionsTable();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel availableLbl;
    private javax.swing.JLabel availableLbl1;
    private javax.swing.JLabel bookAuthorNamesLbl;
    private javax.swing.JLabel bookAuthorNamesLbl1;
    private javax.swing.JLabel bookCategoryLbl;
    private javax.swing.JLabel bookCategoryLbl1;
    private javax.swing.JLabel bookIdLbl;
    private javax.swing.JLabel bookIdLbl1;
    private javax.swing.JLabel bookInfoLbl;
    private javax.swing.JLabel bookInfoLbl1;
    private javax.swing.JLabel bookPagesLbl;
    private javax.swing.JLabel bookPagesLbl1;
    private javax.swing.JLabel bookTitleLbl;
    private javax.swing.JLabel bookTitleLbl1;
    private javax.swing.JComboBox<String> booksComboBox2;
    private javax.swing.JComboBox<String> booksCombobox;
    private javax.swing.JTable borrowedTransactionsTable;
    private javax.swing.JLabel clientCategoryLbl;
    private javax.swing.JLabel clientCategoryLbl1;
    private javax.swing.JComboBox<String> clientCombobox2;
    private javax.swing.JLabel clientEmailLbl;
    private javax.swing.JLabel clientEmailLbl1;
    private javax.swing.JLabel clientIdLbl;
    private javax.swing.JLabel clientIdLbl1;
    private javax.swing.JLabel clientInfoLbl;
    private javax.swing.JLabel clientInfoLbl1;
    private javax.swing.JLabel clientNamesLbl;
    private javax.swing.JLabel clientNamesLbl1;
    private javax.swing.JLabel clientPhoneLbl;
    private javax.swing.JLabel clientPhoneLbl1;
    private javax.swing.JLabel clientProfilePictureLbl;
    private javax.swing.JLabel clientProfilePictureLbl1;
    private javax.swing.JComboBox<String> clientsCombobox;
    private javax.swing.JButton deletebtn;
    private javax.swing.JButton deletebtn1;
    private javax.swing.JButton findBookBtn;
    private javax.swing.JTextField findBookField;
    private javax.swing.JButton findClientBtn;
    private javax.swing.JTextField findClientField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel publicationDateLbl;
    private javax.swing.JLabel publicationDateLbl1;
    private javax.swing.JLabel publishingHouseLbl;
    private javax.swing.JLabel publishingHouseLbl1;
    private javax.swing.JLabel returnedDateLbl;
    private javax.swing.JLabel returnedDateLbl1;
    private javax.swing.JLabel returnedDateLblValue;
    private javax.swing.JLabel returnedDateLblValue1;
    private javax.swing.JTable returnedTransactionsTable;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton saveBtn1;
    private javax.swing.JTextField seachTransactionField2;
    private javax.swing.JButton searchTransBtn;
    private javax.swing.JTextField searchTransactionField;
    private javax.swing.JComboBox<String> searchTransactionKey;
    private javax.swing.JComboBox<String> searchTransactionKey2;
    private javax.swing.JTextField transIdField;
    private javax.swing.JTextField transIdField1;
    private javax.swing.JLabel transactionDateLbl;
    private javax.swing.JLabel transactionDateLbl2;
    private javax.swing.JLabel transactionDateValueLbl;
    private javax.swing.JLabel transactionDateValueLbl2;
    // End of variables declaration//GEN-END:variables
}
