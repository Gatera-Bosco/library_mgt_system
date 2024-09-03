
package lms.com.views;


public class MainFrame extends javax.swing.JFrame {

    private ClientForm clientForm=null;
    private OperationsForm operationsForm=null;
    private BookAndBookCategory bookForm=null;
    public MainFrame() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        desktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        operationsMenu = new javax.swing.JMenu();
        bookTransactionsMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        clientMenu = new javax.swing.JMenuItem();
        bookMenu = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System");

        desktopPane.setBackground(new java.awt.Color(0, 51, 51));
        desktopPane.setFocusable(false);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1194, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jMenuBar1.setFocusable(false);
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        operationsMenu.setText("Operations");
        operationsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationsMenuActionPerformed(evt);
            }
        });

        bookTransactionsMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        bookTransactionsMenu.setBackground(new java.awt.Color(255, 255, 255));
        bookTransactionsMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bookTransactionsMenu.setText("Book transactions");
        bookTransactionsMenu.setOpaque(true);
        bookTransactionsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookTransactionsMenuActionPerformed(evt);
            }
        });
        operationsMenu.add(bookTransactionsMenu);

        jMenuBar1.add(operationsMenu);

        jMenu2.setText("Settings");

        clientMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        clientMenu.setBackground(new java.awt.Color(255, 255, 255));
        clientMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clientMenu.setText("Client");
        clientMenu.setOpaque(true);
        clientMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientMenuActionPerformed(evt);
            }
        });
        jMenu2.add(clientMenu);

        bookMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        bookMenu.setBackground(new java.awt.Color(255, 255, 255));
        bookMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bookMenu.setText("Book");
        bookMenu.setOpaque(true);
        bookMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookMenuActionPerformed(evt);
            }
        });
        jMenu2.add(bookMenu);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void operationsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationsMenuActionPerformed
        // TODO add your handling code here:
        if(clientForm!=null){
            clientForm.dispose();
        }
        if(operationsForm!=null){
            operationsForm.dispose();
        }
        if(bookForm!=null){
            bookForm.dispose();
        }
        
        operationsForm = new OperationsForm();
        desktopPane.add(operationsForm);
        operationsForm.show();
    }//GEN-LAST:event_operationsMenuActionPerformed

    private void clientMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientMenuActionPerformed
        // TODO add your handling code here:
        if(clientForm!=null){
            clientForm.dispose();
        }
        if(operationsForm!=null){
            operationsForm.dispose();
        }
        if(bookForm!=null){
            bookForm.dispose();
        }
        
        
        clientForm = new ClientForm();
        desktopPane.add(clientForm);        
        clientForm.show();
    }//GEN-LAST:event_clientMenuActionPerformed

    private void bookMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookMenuActionPerformed
        // TODO add your handling code here:
        if(clientForm!=null){
            clientForm.dispose();
        }
        if(operationsForm!=null){
            operationsForm.dispose();
        }
        if(bookForm!=null){
            bookForm.dispose();
        }
        
        bookForm = new BookAndBookCategory();
        desktopPane.add(bookForm);
        bookForm.show();
        
    }//GEN-LAST:event_bookMenuActionPerformed

    private void bookTransactionsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookTransactionsMenuActionPerformed
        // TODO add your handling code here:
        if(clientForm!=null){
            clientForm.dispose();
        }
        if(operationsForm!=null){
            operationsForm.dispose();
        }
        if(bookForm!=null){
            bookForm.dispose();
        }
        
        operationsForm = new OperationsForm();
        desktopPane.add(operationsForm);
        operationsForm.show();
    }//GEN-LAST:event_bookTransactionsMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bookMenu;
    private javax.swing.JMenuItem bookTransactionsMenu;
    private javax.swing.JMenuItem clientMenu;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenu operationsMenu;
    // End of variables declaration//GEN-END:variables
}
