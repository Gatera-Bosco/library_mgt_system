package lms.com.dao;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lms.com.models.BookCategory;
import lms.com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BookCategoryDao {
    Session session = null;
    Transaction tx = null;
    
    
    public void saveBookCategory(BookCategory category){
        try{
           session = HibernateUtil.getSessionFactory().openSession();
           tx = session.beginTransaction();
           session.save(category);
           tx.commit();
           JOptionPane.showMessageDialog(null, "New book category has been saved successfully!", "Saving book category", JOptionPane.INFORMATION_MESSAGE);         
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
    
    public List<BookCategory> retrieveBookCategory(){
        List<BookCategory> bookCategories = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            bookCategories = session.createQuery("FROM BookCategory").list();
        }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }       
      return bookCategories;  
    }
    
    public void updateBookCategory(BookCategory category){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            BookCategory updateCategory = (BookCategory)session.get(BookCategory.class, category.getCode());           
            if(updateCategory !=null){
                updateCategory.setName(category.getName());
                session.merge(updateCategory);
                tx.commit();
                JOptionPane.showMessageDialog(null, "Book category has been updated successfully!", "Updating book category", JOptionPane.INFORMATION_MESSAGE);
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
    
    public void deleteBookCategory(String code){
        try{
          session = HibernateUtil.getSessionFactory().openSession();
          tx = session.beginTransaction();
          String sql  = "UPDATE Book SET category=" + null + " WHERE category='" + code + "'";
          BookCategory category = (BookCategory)session.get(BookCategory.class, code);
          if(category!=null){
              int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete book category?", "Deleting book category", JOptionPane.YES_NO_OPTION);
              if(response==0){
                  Query q = session.createQuery(sql);
                  q.executeUpdate();
                  session.delete(category);
                  tx.commit();
                  JOptionPane.showMessageDialog(null, "Book category has been deleted successfully!", "deleting book category", JOptionPane.INFORMATION_MESSAGE);
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
}
