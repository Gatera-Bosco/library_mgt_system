
package lms.com.dao;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lms.com.models.Client;
import lms.com.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ClientDao {
    private Session session=null;
    Transaction tx = null;
    public void saveClient(Client client){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(client);
            tx.commit();
            JOptionPane.showMessageDialog(null, "New client has been saved successfully!", "Saving a client", JOptionPane.INFORMATION_MESSAGE);       
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
    
    public List<Client> retrieveClients(){
        List<Client> clients = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            clients = session.createQuery("FROM Client").list();
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex);
        }finally{
            if(session!=null){
                session.close();
            }
        }        
        return clients;
    }
    
    public void updateClient(Client client){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Client updateClient = (Client)session.get(Client.class, client.getRegId());
            if(updateClient!=null){
                updateClient.setFirstName(client.getFirstName());
                updateClient.setLastName(client.getLastName());
                updateClient.setEmail(client.getEmail());
                updateClient.setClientCategory(client.getClientCategory());
                updateClient.setPhoneNumber(client.getPhoneNumber());
                updateClient.setPhoto(client.getPhoto());
                session.merge(updateClient);
                tx.commit();
                JOptionPane.showMessageDialog(null, "Client has been updated successfully!", "updating a client", JOptionPane.INFORMATION_MESSAGE);
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
    
    public void deleteClient(String code){
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Client client = (Client)session.get(Client.class, code);
            if(client!=null){
                int response = JOptionPane.showConfirmDialog(null, "Do you really want to delete client?", "Deleting a client", JOptionPane.YES_NO_OPTION);
                if(response==0){
                    session.delete(client);
                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Client has been deleted successfully!", "deletinga client", JOptionPane.INFORMATION_MESSAGE);
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
    
    public Client searchClientByRegNo(String regNo){
        Client client = new Client();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            client = (Client)session.get(Client.class, regNo);
        }catch(HibernateException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }finally{
            session.close();
        }       
        return client;
    }
}
