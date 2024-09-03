/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lms.com.views;

import java.awt.Font;
import java.awt.HeadlessException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import lms.com.dao.BookCategoryDao;
import lms.com.dao.BookDao;
import lms.com.models.Book;
import lms.com.models.BookCategory;
import lms.com.models.BookTransaction;

/**
 *
 * @author Schadrack-Olivet
 */
public class BookAndBookCategory extends javax.swing.JInternalFrame {

    BookCategoryDao categoryDao;
    Book book;
    BookDao bookDao;
    final String[] CATEGORYCOLUMNS={
        "Book Category Code",
        "Book Category Name",
        "All Books"
    };
    final String[] BOOKCOLUMNS = {
        "Book Id",
        "Book Title",
        "Book Author",
        "Book Pages",
        "Book Available",
        "Book Category",
        "Publishing House",
        "Publicattion Date"
    };
    final String[] BOOKTRANSACTIONCOLUMNS = {
        "Trans Id",
        "Transaction Date",
        "Return Date",
        "Trans Type",
        "Client Names",
        "Book Returned"
    };
    public BookAndBookCategory() {
        initComponents();
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
        updateClientBtn.setEnabled(false);
        deleteBookBtn.setEnabled(false);
        categoryDao = new BookCategoryDao();
        List<BookCategory> bookCategories = categoryDao.retrieveBookCategory();
        Iterator<BookCategory> categoryIter = bookCategories.iterator();
        while(categoryIter.hasNext()){
            BookCategory bookCategory = categoryIter.next();
            bookCategoryField.addItem(bookCategory.getCode() + "-" + bookCategory.getName());
        }
        
        JTableHeader bookCategoryHeader = bookCategoriesTable.getTableHeader();
        bookCategoryHeader.setFont(new Font(null, Font.BOLD, 12));
        
        JTableHeader categoryBooksHeader = bookCategoryBooksTable.getTableHeader();
        categoryBooksHeader.setFont(new Font(null, Font.BOLD, 12));
        
        JTableHeader booksHeader = booksTable.getTableHeader();
        booksHeader.setFont(new Font(null, Font.BOLD, 12));
        
        JTableHeader bookTransHeader = bookTransactionsTable.getTableHeader();
        bookTransHeader.setFont(new Font(null, Font.BOLD, 12));
        
        bookCategoryCode.setEditable(true);
        
        fillCategoriesTable();
        fillCategoryBooks(null);
        fillBooks();
        fillBookTransactions(null);
    }
    
    public void resetFormBook(){
        bookIdField.setText("  Book Id ....");
        bookTitleField.setText("  Book title");
        bookPublishingHouseField.setText("  Publishibg house ....");
        bookAuthorField.setText("  Book author ....");
        bookPagesField.setText("  Book pages ....");
        publicationDateField.setDate(null);
        bookCategoryField.setSelectedIndex(0);
        updateClientBtn.setEnabled(false);
        deleteBookBtn.setEnabled(false);
        saveClientBtn.setEnabled(true);
        bookIdField.setEditable(true);
    }
    
    private void fillCategoriesTable(){
        DefaultTableModel categoriesModel = new DefaultTableModel(CATEGORYCOLUMNS, 0);
        categoryDao = new BookCategoryDao();
        List<BookCategory> bookCategories = categoryDao.retrieveBookCategory();
        Iterator<BookCategory> categoriesIter = bookCategories.iterator();
        String booksNumber;
        while(categoriesIter.hasNext()){
            booksNumber ="";
            BookCategory category = categoriesIter.next();
            if(category.getBooks().size()>1){
                booksNumber = category.getBooks().size() + " books";
            }else if(category.getBooks().size()== 1){
                booksNumber = category.getBooks().size() + " book";
            }
            String[] rowData = {category.getCode(), category.getName(), booksNumber};
            categoriesModel.addRow(rowData);
        }
        bookCategoriesTable.setModel(categoriesModel);
    }
    
    private void fillCategoryBooks(List<Book> books){
        DefaultTableModel booksModel = new DefaultTableModel(BOOKCOLUMNS, 0);
        if(books!=null){
            Iterator<Book> booksIter = books.iterator();
            while(booksIter.hasNext()){
                Book bookIn = booksIter.next();
                String available="not available";
                String name="";
                if(bookIn.isAvailable()){
                    available="available";
                }
                if(bookIn.getCategory()!=null){
                    name=bookIn.getCategory().getName();
                }
                String [] rowData = {
                    bookIn.getBookId(), bookIn.getTitle(), bookIn.getAuthor(), String.valueOf(bookIn.getPages()),
                    available, name, bookIn.getPublishingHouse(), String.valueOf(bookIn.getPublicationDate())                          
                };

                booksModel.addRow(rowData);
            }
        }
        bookCategoryBooksTable.setModel(booksModel);
    }
    private void fillBooks(){
        DefaultTableModel booksModel = new DefaultTableModel(BOOKCOLUMNS, 0);
        bookDao = new BookDao();
        List<Book> books = bookDao.retrieveBooks();
        Iterator<Book> booksIter = books.iterator();
        while(booksIter.hasNext()){
            Book bookIn = booksIter.next();
            String available="not available";
            String name="";
            if(bookIn.isAvailable()){
                available="available";
            }
            if(bookIn.getCategory()!=null){
                name= bookIn.getCategory().getName();
            }
            String [] rowData = {
                bookIn.getBookId(), bookIn.getTitle(), bookIn.getAuthor(), String.valueOf(bookIn.getPages()),
                available, name, bookIn.getPublishingHouse(), String.valueOf(bookIn.getPublicationDate())                          
            };

            booksModel.addRow(rowData);
        }

        booksTable.setModel(booksModel);
    }
    
    private void fillBookTransactions(List<BookTransaction> bookTransactions){
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
                   bookTransaction.getClient().getFirstName() + " " + bookTransaction.getClient().getLastName(),
                   bookReturned
               };
               transModel.addRow(rowData);
           }
        }
        bookTransactionsTable.setModel(transModel);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        bookCategoryCode = new javax.swing.JTextField();
        bookCategoryName = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookCategoriesTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        bookCategoryBooksLbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        bookCategoryBooksTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        searchBookKey = new javax.swing.JComboBox<>();
        searchTextFieldBook = new javax.swing.JTextField();
        searchBookBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        clientsScrollPane = new javax.swing.JScrollPane();
        booksTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        saveClientBtn = new javax.swing.JButton();
        updateClientBtn = new javax.swing.JButton();
        resetFormBtn = new javax.swing.JButton();
        importBookBtn = new javax.swing.JButton();
        exportBooksBtn = new javax.swing.JButton();
        deleteBookBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        bookIdField = new javax.swing.JTextField();
        bookTitleField = new javax.swing.JTextField();
        bookPublishingHouseField = new javax.swing.JTextField();
        bookAuthorField = new javax.swing.JTextField();
        bookPagesField = new javax.swing.JTextField();
        bookCategoryField = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        publicationDateField = new com.toedter.calendar.JDateChooser();
        bookRecordUpdateLabel = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        bookIdLbl = new javax.swing.JLabel();
        bookTitleLbl = new javax.swing.JLabel();
        bookAuthorLbl = new javax.swing.JLabel();
        bookPagesLbl = new javax.swing.JLabel();
        bookPublishingHouseLbl = new javax.swing.JLabel();
        bookPublicationDateLbl = new javax.swing.JLabel();
        bookCategoryLbl = new javax.swing.JLabel();
        bookAvailableLbl = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        bookTransactionsListLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bookTransactionsTable = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        bookTransactionFrom = new com.toedter.calendar.JDateChooser();
        bookTransactionTo = new com.toedter.calendar.JDateChooser();
        bookTransactionSearchBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 51, 51));
        setResizable(true);
        setTitle("Book and book category");
        setFrameIcon(null);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 102, 102));
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jTabbedPane1.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jPanel9.setBackground(new java.awt.Color(0, 51, 51));

        jPanel11.setBackground(new java.awt.Color(0, 51, 51));
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        jLabel2.setBackground(new java.awt.Color(0, 51, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Record a new / update existing book category");

        bookCategoryCode.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookCategoryCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bookCategoryCode.setText("  ");
        bookCategoryCode.setBorder(null);

        bookCategoryName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookCategoryName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bookCategoryName.setBorder(null);

        updateButton.setBackground(new java.awt.Color(153, 102, 0));
        updateButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        updateButton.setForeground(new java.awt.Color(255, 255, 255));
        updateButton.setText("update");
        updateButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        updateButton.setFocusable(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        saveButton.setBackground(new java.awt.Color(0, 51, 102));
        saveButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("Save");
        saveButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        saveButton.setFocusable(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(102, 0, 0));
        deleteButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("delete");
        deleteButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 51, 51));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Book category code");

        jLabel4.setBackground(new java.awt.Color(0, 51, 51));
        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Book category name");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookCategoryName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookCategoryCode, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jPanel12.setBackground(new java.awt.Color(0, 51, 51));

        jScrollPane1.setBackground(new java.awt.Color(0, 51, 51));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        bookCategoriesTable.setModel(new javax.swing.table.DefaultTableModel(
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
        bookCategoriesTable.setFillsViewportHeight(true);
        bookCategoriesTable.setRowHeight(20);
        bookCategoriesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookCategoriesTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookCategoriesTable);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(0, 51, 51));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        bookCategoryBooksLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookCategoryBooksLbl.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        bookCategoryBooksLbl.setForeground(new java.awt.Color(255, 255, 255));
        bookCategoryBooksLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookCategoryBooksLbl.setText("List of books for this category");
        bookCategoryBooksLbl.setOpaque(true);

        jScrollPane3.setBackground(new java.awt.Color(0, 51, 51));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        bookCategoryBooksTable.setModel(new javax.swing.table.DefaultTableModel(
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
        bookCategoryBooksTable.setFillsViewportHeight(true);
        bookCategoryBooksTable.setRowHeight(20);
        jScrollPane3.setViewportView(bookCategoryBooksTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bookCategoryBooksLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookCategoryBooksLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 905, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(268, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Book Category", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));
        jPanel3.setPreferredSize(new java.awt.Dimension(597, 660));

        jPanel4.setBackground(new java.awt.Color(0, 51, 51));

        searchBookKey.setEditable(true);
        searchBookKey.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchBookKey.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "book Id", "book title", "author", "books available" }));
        searchBookKey.setBorder(null);
        searchBookKey.setFocusable(false);
        searchBookKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookKeyActionPerformed(evt);
            }
        });

        searchTextFieldBook.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchTextFieldBook.setText("  search book....");
        searchTextFieldBook.setBorder(null);

        searchBookBtn.setBackground(new java.awt.Color(0, 102, 102));
        searchBookBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        searchBookBtn.setForeground(new java.awt.Color(255, 255, 255));
        searchBookBtn.setText("Search");
        searchBookBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        searchBookBtn.setFocusable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(searchBookKey, 0, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextFieldBook, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBookBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchBookBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(searchTextFieldBook)
            .addComponent(searchBookKey, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        clientsScrollPane.setBackground(new java.awt.Color(0, 51, 51));
        clientsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        clientsScrollPane.setAutoscrolls(true);
        clientsScrollPane.setFocusable(false);

        booksTable.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        booksTable.setModel(new javax.swing.table.DefaultTableModel(
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
        booksTable.setFillsViewportHeight(true);
        booksTable.setFocusable(false);
        booksTable.setRowHeight(20);
        booksTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                booksTableMouseClicked(evt);
            }
        });
        clientsScrollPane.setViewportView(booksTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clientsScrollPane)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clientsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        jPanel6.setBackground(new java.awt.Color(0, 51, 51));

        jPanel7.setBackground(new java.awt.Color(0, 51, 51));
        jPanel7.setFocusable(false);

        saveClientBtn.setBackground(new java.awt.Color(0, 51, 102));
        saveClientBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        saveClientBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveClientBtn.setText("Save book");
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
        updateClientBtn.setText("Update book");
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

        importBookBtn.setBackground(new java.awt.Color(0, 0, 51));
        importBookBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        importBookBtn.setForeground(new java.awt.Color(255, 255, 255));
        importBookBtn.setText("Import books");
        importBookBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        importBookBtn.setFocusable(false);

        exportBooksBtn.setBackground(new java.awt.Color(102, 102, 0));
        exportBooksBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        exportBooksBtn.setForeground(new java.awt.Color(255, 255, 255));
        exportBooksBtn.setText("export books");
        exportBooksBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        exportBooksBtn.setFocusable(false);

        deleteBookBtn.setBackground(new java.awt.Color(102, 0, 0));
        deleteBookBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        deleteBookBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteBookBtn.setText("Delete book");
        deleteBookBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        deleteBookBtn.setFocusable(false);
        deleteBookBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(saveClientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(updateClientBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resetFormBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(importBookBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
            .addComponent(exportBooksBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(deleteBookBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateClientBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetFormBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(deleteBookBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(importBookBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportBooksBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(0, 51, 51));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        bookIdField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookIdField.setText("  Book Id ....");
        bookIdField.setBorder(null);

        bookTitleField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookTitleField.setText("  Book title ....");
        bookTitleField.setBorder(null);

        bookPublishingHouseField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookPublishingHouseField.setText("  Publishing house ....");
        bookPublishingHouseField.setBorder(null);
        bookPublishingHouseField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookPublishingHouseFieldActionPerformed(evt);
            }
        });

        bookAuthorField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookAuthorField.setText("  Book author ....");
        bookAuthorField.setBorder(null);
        bookAuthorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookAuthorFieldActionPerformed(evt);
            }
        });

        bookPagesField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookPagesField.setText("  Book pages ....");
        bookPagesField.setBorder(null);
        bookPagesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookPagesFieldActionPerformed(evt);
            }
        });

        bookCategoryField.setEditable(true);
        bookCategoryField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookCategoryField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Book category ...." }));
        bookCategoryField.setBorder(null);
        bookCategoryField.setFocusable(false);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("  Date of publication: ");
        jLabel1.setOpaque(true);

        publicationDateField.setBackground(new java.awt.Color(255, 255, 255));
        publicationDateField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookPublishingHouseField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bookIdField)
                    .addComponent(bookTitleField)
                    .addComponent(bookAuthorField)
                    .addComponent(bookPagesField)
                    .addComponent(bookCategoryField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(publicationDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookPublishingHouseField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookAuthorField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookPagesField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(publicationDateField, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookCategoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bookRecordUpdateLabel.setBackground(new java.awt.Color(0, 51, 51));
        bookRecordUpdateLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bookRecordUpdateLabel.setForeground(new java.awt.Color(255, 255, 255));
        bookRecordUpdateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookRecordUpdateLabel.setText("Record new /update existing book information");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bookRecordUpdateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookRecordUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        jPanel16.setBackground(new java.awt.Color(0, 51, 51));

        jPanel17.setBackground(new java.awt.Color(0, 51, 51));

        bookIdLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookIdLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookTitleLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookTitleLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookAuthorLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookAuthorLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookPagesLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookPagesLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookPublishingHouseLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookPublishingHouseLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookPublicationDateLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookPublicationDateLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookCategoryLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookCategoryLbl.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookCategoryLbl.setForeground(new java.awt.Color(255, 255, 255));

        bookAvailableLbl.setBackground(new java.awt.Color(0, 51, 51));
        bookAvailableLbl.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bookAvailableLbl.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookPublicationDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookPublishingHouseLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookPagesLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookAuthorLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookTitleLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookIdLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookCategoryLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(bookAvailableLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(bookIdLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookTitleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookAuthorLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookPagesLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookPublishingHouseLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookPublicationDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bookCategoryLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bookAvailableLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(0, 51, 51));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        bookTransactionsListLabel.setBackground(new java.awt.Color(0, 51, 51));
        bookTransactionsListLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        bookTransactionsListLabel.setForeground(new java.awt.Color(255, 255, 255));
        bookTransactionsListLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookTransactionsListLabel.setText("List of transactions made on Book  #");
        bookTransactionsListLabel.setOpaque(true);

        jScrollPane2.setBackground(new java.awt.Color(0, 51, 51));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(0, 51, 51));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        bookTransactionsTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        bookTransactionsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        bookTransactionsTable.setFillsViewportHeight(true);
        bookTransactionsTable.setRowHeight(20);
        jScrollPane2.setViewportView(bookTransactionsTable);

        jPanel19.setBackground(new java.awt.Color(0, 51, 51));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

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

        bookTransactionFrom.setBackground(new java.awt.Color(255, 255, 255));
        bookTransactionFrom.setFocusable(false);
        bookTransactionFrom.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        bookTransactionTo.setBackground(new java.awt.Color(255, 255, 255));
        bookTransactionTo.setFocusable(false);
        bookTransactionTo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        bookTransactionSearchBtn.setBackground(new java.awt.Color(51, 51, 51));
        bookTransactionSearchBtn.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bookTransactionSearchBtn.setForeground(new java.awt.Color(255, 255, 255));
        bookTransactionSearchBtn.setText("search");
        bookTransactionSearchBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        bookTransactionSearchBtn.setFocusable(false);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookTransactionFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookTransactionTo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bookTransactionSearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bookTransactionTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                    .addComponent(bookTransactionSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(9, 9, 9))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(bookTransactionFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(bookTransactionsListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(bookTransactionsListLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Book", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bookPagesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookPagesFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookPagesFieldActionPerformed

    private void bookAuthorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookAuthorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookAuthorFieldActionPerformed

    private void bookPublishingHouseFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookPublishingHouseFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookPublishingHouseFieldActionPerformed

    private void resetFormBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFormBtnActionPerformed
        // TODO add your handling code here:
        resetFormBook();
        bookIdField.setEditable(true);

    }//GEN-LAST:event_resetFormBtnActionPerformed

    private void searchBookKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookKeyActionPerformed
        // TODO add your handling code here:
        searchTextFieldBook.setText("  search book by " + searchBookKey.getSelectedItem().toString() + " ....");
    }//GEN-LAST:event_searchBookKeyActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        String categoryCode = bookCategoryCode.getText().trim();
        String categoryName = bookCategoryName.getText().trim();
        if(categoryCode.length()!=5 || categoryCode.contains("-")){
            JOptionPane.showMessageDialog(null, "Book category code must be 5 characters and cannnot contain hyphen(-)!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        }else if(categoryName.length()>100 || categoryName.length()<4){
            JOptionPane.showMessageDialog(null, "Book category name must be 4 characters minimum or 100 characters maximum!", "Fields validation", JOptionPane.INFORMATION_MESSAGE);
        }else{
            BookCategory category = new BookCategory();
            categoryDao = new BookCategoryDao();
            category.setCode(categoryCode);
            category.setName(categoryName);
            categoryDao.saveBookCategory(category);
            fillCategoriesTable();
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void saveClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveClientBtnActionPerformed
        String bookId = bookIdField.getText().trim();
        String bookTitle = bookTitleField.getText().trim();
        String publishingHouse = bookPublishingHouseField.getText().trim();
        String bookAuthor = bookAuthorField.getText().trim();
        String bookPages = bookPagesField.getText().trim();
        Date publicationDate = publicationDateField.getDate();
        String bookCategory = bookCategoryField.getSelectedItem().toString();
        
        book = new Book();
        bookDao = new BookDao();
        
        try{
            Integer.valueOf(bookPages);
            if("Book Id ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase()) || bookId.contains("-")){
                JOptionPane.showMessageDialog(null, "Book Id cannot be empty or contains hyphen(-)!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Book title ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book Title cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Publishing house ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book publishing house cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Book author ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book author cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Book pages ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book pages cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(publicationDateField.getDate()==null){
                JOptionPane.showMessageDialog(null, "Book publication date cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(bookCategoryField.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Book category cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else{
                if(bookId.length()>15 || bookId.length()<10){
                    JOptionPane.showMessageDialog(null, "Book Id must be 10 characters or 15 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else if(bookTitle.length()>100 || bookTitle.length()<4){
                    JOptionPane.showMessageDialog(null, "Book title must be 100 characters or 4 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else if(publishingHouse.length()>100 || publishingHouse.length()<4){
                    JOptionPane.showMessageDialog(null, "Book publishing house must be100 characters or 4 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else if(bookAuthor.length()>80 || bookAuthor.length()<4){
                    JOptionPane.showMessageDialog(null, "Book author names must be 80 characters or 4 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    book.setAuthor(bookAuthor);
                    book.setBookId(bookId);
                    book.setPages(Integer.valueOf(bookPages));
                    book.setPublicationDate(new java.sql.Date(publicationDate.getTime()));
                    book.setPublishingHouse(publishingHouse);
                    book.setTitle(bookTitle);
                    
                    bookCategory = bookCategory.split("-")[0].trim();
                    
                    bookDao.saveBook(book, bookCategory);
                    fillBooks();
                }
            }           
        }catch(HeadlessException | NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid book pages number!", "Fields validation", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveClientBtnActionPerformed

    private void bookCategoriesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookCategoriesTableMouseClicked
        // TODO add your handling code here:
        int currentRow = bookCategoriesTable.getSelectedRow();
        bookCategoryCode.setEditable(false);
        if(currentRow>-1){
            String code = bookCategoriesTable.getModel().getValueAt(currentRow, 0).toString();
            String name = bookCategoriesTable.getModel().getValueAt(currentRow, 1).toString();
            bookCategoryCode.setText(code);
            bookCategoryName.setText(name);
            saveButton.setEnabled(false);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
            
            bookDao = new BookDao();
            List<Book> books = bookDao.booksForCategory(code.trim());
            fillCategoryBooks(books);
            
        }
    }//GEN-LAST:event_bookCategoriesTableMouseClicked

    private void booksTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_booksTableMouseClicked
        // TODO add your handling code here:
        int currentRow = booksTable.getSelectedRow();
        bookDao = new BookDao();
        bookIdField.setEditable(false);
        
        if(currentRow>-1){
            String bookId = booksTable.getModel().getValueAt(currentRow, 0).toString().trim();
            bookIdField.setEditable(false);
            
            book = bookDao.searchBookById(bookId);
            if(book!=null){
                String bookTitle = book.getTitle();
                String bookAuthor = book.getAuthor();
                String bookPages = String.valueOf(book.getPages());
                String bookAvailable = booksTable.getModel().getValueAt(currentRow, 4).toString();               
                String bookPublishingHouse = book.getPublishingHouse();
                Date bookPublicationDate = book.getPublicationDate();
               
                 String bookCategory ="";
                 if(book.getCategory()!=null){
                     bookCategory = book.getCategory().getName();
                     bookCategoryField.setSelectedItem(book.getCategory().getCode()+ "-" + book.getCategory().getName());
                 }else{
                     bookCategoryField.setSelectedIndex(0);
                 }

                bookIdField.setText("  " + bookId);
                bookTitleField.setText("  "+ bookTitle);
                bookPublishingHouseField.setText("  " + bookPublishingHouse);
                bookAuthorField.setText("  " + bookAuthor);
                bookPagesField.setText("  " + bookPages);

                publicationDateField.setDate(bookPublicationDate);
                
                bookIdLbl.setText("Book Id:  " + bookId);
                bookTitleLbl.setText("Book Title:  " + bookTitle);
                bookAuthorLbl.setText("Book Author:  " + book.getAuthor());
                bookPagesLbl.setText("Book Pages:  " + book.getPages());
                bookPublishingHouseLbl.setText("Publishing House:  " + book.getPublishingHouse());
                bookPublicationDateLbl.setText("Publication Date:  " + book.getPublicationDate());
                if(book.getCategory()!=null){
                    bookCategoryLbl.setText("Book Category:  " + book.getCategory().getName());
                }else{
                    bookCategoryLbl.setText("Book Category:  has no category");
                }
                bookAvailableLbl.setText("Book Available:  " + bookAvailable);
                
                if(book.getTransactions().size()>0){
                    List<BookTransaction> bookTransactions = book.getTransactions();
                    fillBookTransactions(bookTransactions);
                }else{
                    fillBookTransactions(null);
                }
                bookTransactionsListLabel.setText("List of transactions made on Book  #" + bookId);
                
                updateClientBtn.setEnabled(true);
                deleteBookBtn.setEnabled(true);
                saveClientBtn.setEnabled(false);
            }
            
        }
    }//GEN-LAST:event_booksTableMouseClicked

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        BookCategory bookCategory = new BookCategory();
        bookCategory.setCode(bookCategoryCode.getText().trim());
        bookCategory.setName(bookCategoryName.getText().trim());
        categoryDao = new BookCategoryDao();
        categoryDao.updateBookCategory(bookCategory);
        fillCategoriesTable();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        categoryDao = new BookCategoryDao();
        categoryDao.deleteBookCategory(bookCategoryCode.getText().trim());
        fillCategoriesTable();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void updateClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateClientBtnActionPerformed
        // TODO add your handling code here:
        String bookId = bookIdField.getText().trim();
        String bookTitle = bookTitleField.getText().trim();
        String publishingHouse = bookPublishingHouseField.getText().trim();
        String bookAuthor = bookAuthorField.getText().trim();
        String bookPages = bookPagesField.getText().trim();
        Date publicationDate = publicationDateField.getDate();
        String bookCategory = bookCategoryField.getSelectedItem().toString();
        
        book = new Book();
        bookDao = new BookDao();
        
        try{
            Integer.valueOf(bookPages);
            if("Book Id ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase()) || bookId.contains("-")){
                JOptionPane.showMessageDialog(null, "Book Id cannot be empty or contains hyphen(-)!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Book title ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book Title cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Publishing house ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book publishing house cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Book author ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book author cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if("Book pages ....".toLowerCase().contains(bookIdField.getText().trim().toLowerCase())){
                JOptionPane.showMessageDialog(null, "Book pages cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(publicationDateField.getDate()==null){
                JOptionPane.showMessageDialog(null, "Book publication date cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else if(bookCategoryField.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Book category cannot be empty!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
            }else{
                if(bookId.length()>15 || bookId.length()<10){
                    JOptionPane.showMessageDialog(null, "Book Id must be 10 characters or 15 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else if(bookTitle.length()>100 || bookTitle.length()<4){
                    JOptionPane.showMessageDialog(null, "Book title must be 100 characters or 4 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else if(publishingHouse.length()>100 || publishingHouse.length()<4){
                    JOptionPane.showMessageDialog(null, "Book publishing house must be100 characters or 4 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else if(bookAuthor.length()>80 || bookAuthor.length()<4){
                    JOptionPane.showMessageDialog(null, "Book author names must be 80 characters or 4 characters range!", "Field validation", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    book.setAuthor(bookAuthor);
                    book.setBookId(bookId);
                    book.setPages(Integer.valueOf(bookPages));
                    book.setPublicationDate(new java.sql.Date(publicationDate.getTime()));
                    book.setPublishingHouse(publishingHouse);
                    book.setTitle(bookTitle);
                    
                    bookCategory = bookCategory.split("-")[0].trim();
                    
                    bookDao.updateBook(book, bookCategory);
                    fillBooks();
                }
            }           
        }catch(HeadlessException | NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid book pages number!", "Fields validation", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_updateClientBtnActionPerformed

    private void deleteBookBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookBtnActionPerformed
        // TODO add your handling code here:       
        bookDao = new BookDao();
        bookDao.deleteBook(bookIdField.getText().trim());
        fillBooks();
    }//GEN-LAST:event_deleteBookBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bookAuthorField;
    private javax.swing.JLabel bookAuthorLbl;
    private javax.swing.JLabel bookAvailableLbl;
    private javax.swing.JTable bookCategoriesTable;
    private javax.swing.JLabel bookCategoryBooksLbl;
    private javax.swing.JTable bookCategoryBooksTable;
    private javax.swing.JTextField bookCategoryCode;
    private javax.swing.JComboBox<String> bookCategoryField;
    private javax.swing.JLabel bookCategoryLbl;
    private javax.swing.JTextField bookCategoryName;
    private javax.swing.JTextField bookIdField;
    private javax.swing.JLabel bookIdLbl;
    private javax.swing.JTextField bookPagesField;
    private javax.swing.JLabel bookPagesLbl;
    private javax.swing.JLabel bookPublicationDateLbl;
    private javax.swing.JTextField bookPublishingHouseField;
    private javax.swing.JLabel bookPublishingHouseLbl;
    private javax.swing.JLabel bookRecordUpdateLabel;
    private javax.swing.JTextField bookTitleField;
    private javax.swing.JLabel bookTitleLbl;
    private com.toedter.calendar.JDateChooser bookTransactionFrom;
    private javax.swing.JButton bookTransactionSearchBtn;
    private com.toedter.calendar.JDateChooser bookTransactionTo;
    private javax.swing.JLabel bookTransactionsListLabel;
    private javax.swing.JTable bookTransactionsTable;
    private javax.swing.JTable booksTable;
    private javax.swing.JScrollPane clientsScrollPane;
    private javax.swing.JButton deleteBookBtn;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton exportBooksBtn;
    private javax.swing.JButton importBookBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser publicationDateField;
    private javax.swing.JButton resetFormBtn;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton saveClientBtn;
    private javax.swing.JButton searchBookBtn;
    private javax.swing.JComboBox<String> searchBookKey;
    private javax.swing.JTextField searchTextFieldBook;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton updateClientBtn;
    // End of variables declaration//GEN-END:variables
}
