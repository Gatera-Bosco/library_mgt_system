
package lms.com.test;

import lms.com.dao.ClientDao;
import lms.com.enums.ClientCategory;
import lms.com.models.Client;


public class TestClient {
    ClientDao clientDao;
    
    public void testSaveClient(){
        Client client = new Client("C1234","Schadrack", "Niyibizi", "+250780626422", "niyibizischadrack@gmail.com");
        client.setClientCategory(ClientCategory.STUDENT.toString());
        clientDao = new ClientDao();
        clientDao.saveClient(client);
    }
}
