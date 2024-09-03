
package lms.com.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lms.com.models.Book;
import lms.com.models.BookCategory;
import lms.com.models.BookTransaction;
import lms.com.models.Client;
import lms.com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BookTransactionDao {
    private Session session=null;
    private Transaction tx = null;
    
    public void saveBookTransaction(BookTransaction bookTrans, String bookCode, String clientCode){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            
            Book book = (Book)session.get(Book.class, bookCode);
            Client client = (Client)session.get(Client.class, clientCode);
            if(book!=null && client!=null){
                book.setAvailable(false);
                book.addTransaction(bookTrans);
                client.addTransaction(bookTrans);
                session.save(bookTrans);
                tx.commit();
                JOptionPane.showMessageDialog(null, "New book transaction has been saved successfully!", "Saving book transaction", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(HibernateException ex){
            if(tx!=null){
                tx.rollback();
            }
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }
    }
    
    public void saveReturnedTransaction(BookTransaction trans, String bookCode, String clientCode, long transId){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            
            Book book = (Book)session.get(Book.class, bookCode);
            Client client = (Client)session.get(Client.class, clientCode);
            if(book!=null && client!=null){
                book.setAvailable(true);
                book.addTransaction(trans);
                client.addTransaction(trans);                
                BookTransaction bTrans = (BookTransaction)session.get(BookTransaction.class, transId);
                if(bTrans!=null){
                    bTrans.setBookReturned(true);
                    bTrans.setReturnDate(trans.getReturnDate());
                    session.merge(bTrans);
                }
                session.save(trans);
                tx.commit();
                JOptionPane.showMessageDialog(null, "New book transaction has been saved successfully!", "Saving book transaction", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(HibernateException ex){
            if(tx!=null){
                tx.rollback();
            }
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }
    }
    
    public List<BookTransaction> retrieveBorrowedTransactions(){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transactions = session.createQuery("FROM BookTransaction WHERE transactionType='BORROW'").list();           
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }       
        return transactions;
    }
    public List<BookTransaction> retrieveReturnedTransactions(){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transactions = session.createQuery("FROM BookTransaction WHERE transactionType='RETURN'").list();           
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }       
        return transactions;
    }
    
    public void deleteTransaction(long transId){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            BookTransaction trans = (BookTransaction)session.get(BookTransaction.class, transId);
            if(trans!=null){
                int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete book transaction?", "Deleting book transaction", JOptionPane.YES_NO_OPTION);
                if(response==0){
                  session.delete(trans);
                  tx.commit();
                  JOptionPane.showMessageDialog(null, "Book transaction has been deleted successfully!", "deleting book transaction", JOptionPane.INFORMATION_MESSAGE);
                } 
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }
    }
    
    public BookTransaction searchTransactionById(long transId){
        BookTransaction bookTrans = null;        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            bookTrans = (BookTransaction)session.get(BookTransaction.class, transId);
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }              
        return bookTrans;
    }
    
    public List<BookTransaction> searchByClientId(String clientId){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Client client = (Client)session.get(Client.class, clientId);
            if(client!=null){
                if(client.getTransactions().size()>0){
                    for(BookTransaction bt: client.getTransactions()){
                        if(bt.getTransactionType().equalsIgnoreCase("BORROW")){
                            transactions.add(bt);
                        }
                    }                   
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchByClientName(String clientName){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM Client c WHERE c.firstName like '%" + clientName + "%' OR  c.lastName like '%" + clientName + "%'";
            List<Client> clients = session.createQuery(sql).list();
            if(clients.size()>0){
                for(Client cl: clients){
                    if(cl.getTransactions().size()>0){
                        for(BookTransaction bt: cl.getTransactions()){
                            if(bt.getTransactionType().equalsIgnoreCase("BORROW")){
                                transactions.add(bt);
                            }
                        }                        
                    }
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchByBook(String book){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM Book b WHERE b.bookId='" + book + "' OR  b.title like '%" + book + "%'";
            List<Book> books = session.createQuery(sql).list();
            if(books.size()>0){
                for(Book b: books){
                    if(b.getTransactions().size()>0){
                        for(BookTransaction bt: b.getTransactions()){
                            if(bt.getTransactionType().equalsIgnoreCase("BORROW")){
                                transactions.add(bt);
                            }
                        }                        
                    }
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchByBookCategory(String bookCategory){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM BookCategory b WHERE b.code='" + bookCategory + "' OR  b.name like '%" + bookCategory + "%'";
            List<BookCategory> bookCategories = session.createQuery(sql).list();
            if(bookCategories.size()>0){
                for(BookCategory bc: bookCategories){
                    if(bc.getBooks().size()>0){
                        for(Book b: bc.getBooks()){
                            if(b.getTransactions().size()>0){
                                for(BookTransaction bt: b.getTransactions()){
                                    if(bt.getTransactionType().equalsIgnoreCase("BORROW")){
                                        transactions.add(bt);
                                    }
                                } 
                            }
                        } 
                    }
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchByTransDate(Date date){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM BookTransaction bt WHERE bt.transactionDate=" + date + " AND bt.transactionType=BORROW";
            transactions = session.createQuery(sql).list();           
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    //-------------------------------------------
    public List<BookTransaction> searchReturnByClientId(String clientId){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            Client client = (Client)session.get(Client.class, clientId);
            if(client!=null){
                if(client.getTransactions().size()>0){
                    for(BookTransaction bt: client.getTransactions()){
                        if(bt.getTransactionType().equalsIgnoreCase("RETURN")){
                            transactions.add(bt);
                        }
                    }                   
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchReturnByClientName(String clientName){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM Client c WHERE c.firstName like '%" + clientName + "%' OR  c.lastName like '%" + clientName + "%'";
            List<Client> clients = session.createQuery(sql).list();
            if(clients.size()>0){
                for(Client cl: clients){
                    if(cl.getTransactions().size()>0){
                        for(BookTransaction bt: cl.getTransactions()){
                            if(bt.getTransactionType().equalsIgnoreCase("RETURN")){
                                transactions.add(bt);
                            }
                        }                        
                    }
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchReturnByBook(String book){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM Book b WHERE b.bookId='" + book + "' OR  b.title like '%" + book + "%'";
            List<Book> books = session.createQuery(sql).list();
            if(books.size()>0){
                for(Book b: books){
                    if(b.getTransactions().size()>0){
                        for(BookTransaction bt: b.getTransactions()){
                            if(bt.getTransactionType().equalsIgnoreCase("RETURN")){
                                transactions.add(bt);
                            }
                        }                        
                    }
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchReturnByBookCategory(String bookCategory){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM BookCategory b WHERE b.code='" + bookCategory + "' OR  b.name like '%" + bookCategory + "%'";
            List<BookCategory> bookCategories = session.createQuery(sql).list();
            if(bookCategories.size()>0){
                for(BookCategory bc: bookCategories){
                    if(bc.getBooks().size()>0){
                        for(Book b: bc.getBooks()){
                            if(b.getTransactions().size()>0){
                                for(BookTransaction bt: b.getTransactions()){
                                    if(bt.getTransactionType().equalsIgnoreCase("RETURN")){
                                        transactions.add(bt);
                                    }
                                } 
                            }
                        } 
                    }
                }
            }
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
    public List<BookTransaction> searchReturnByTransDate(Date date){
        List<BookTransaction> transactions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            String sql = "FROM BookTransaction bt WHERE bt.transactionDate=" + date + " AND bt.transactionType=RETURN";
            transactions = session.createQuery(sql).list();           
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            session.close();
        }               
        return transactions;
    }
    
}
