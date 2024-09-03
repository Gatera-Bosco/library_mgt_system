package lms.com.dao;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lms.com.models.Book;
import lms.com.models.BookCategory;
import lms.com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BookDao {
   private Session session = null;
   private Transaction tx = null;
   
   public void saveBook(Book book, String categoryCode){
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           tx = session.beginTransaction();
           BookCategory category = (BookCategory)session.get(BookCategory.class, categoryCode);
           if(category!=null){
                category.addBook(book);
            }
           session.save(book);
           tx.commit();
           JOptionPane.showMessageDialog(null, "New book has been saved successfully!", "Saving a book", JOptionPane.INFORMATION_MESSAGE);
       }catch(HibernateException ex){
           if(tx!=null){
               tx.rollback();
           }
           JOptionPane.showMessageDialog(null, ex.getMessage());
       }finally{
           if(session!=null){
               session.close();
           }
       }
   }
   
   public List<Book> retrieveBooks(){
       List<Book> books = new ArrayList<>();
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           books = session.createQuery("FROM Book").list();
       }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex);
       }finally{
           if(session!=null){
               session.close();
           }
       }      
       return books;
   }
   
   public void updateBook(Book book, String categoryCode){
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           tx = session.beginTransaction();
           Book updateBook = (Book)session.get(Book.class, book.getBookId());
           BookCategory category = (BookCategory)session.get(BookCategory.class, categoryCode);
           if(updateBook!=null){
               updateBook.setAuthor(book.getAuthor());              
               updateBook.setPages(book.getPages());
               updateBook.setPublicationDate(book.getPublicationDate());
               updateBook.setPublishingHouse(book.getPublishingHouse());
               updateBook.setTitle(book.getTitle());
               
               if(category!=null){
                   category.addBook(updateBook);
               }
               session.merge(updateBook);
               tx.commit();
               JOptionPane.showMessageDialog(null, "Book has been updated successfully!", "Updating a book", JOptionPane.INFORMATION_MESSAGE);
           }
       }catch(HibernateException ex){
           if(tx!=null){
               tx.rollback();
           }
           JOptionPane.showMessageDialog(null, ex);
       }
   }
   
   public void deleteBook(String code){
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           tx = session.beginTransaction();
           Book book = (Book)session.get(Book.class, code);
           if(book!=null){
                int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete a book?", "deleting a book", JOptionPane.YES_NO_OPTION);
                if(response==0){
                    session.delete(book);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Book has been deleted successfully!", "deleting book", JOptionPane.INFORMATION_MESSAGE);
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
   
   public List<Book> booksForCategory(String categoryCode){
       List<Book> books = null;
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           books = session.createQuery("FROM Book WHERE category='" + categoryCode + "'").list();
       }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex.getMessage());
       }finally{
           session.close();
       }
       
       return books;
   }
   
   public Book searchBookById(String bookId){
       Book book = new Book();
       try{
           session = HibernateUtil.getSessionFactory().openSession();
           book = (Book)session.get(Book.class, bookId);
       }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex);
       }finally{
           session.close();
       }
       
       return book;
   }
}
