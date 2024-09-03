
import java.util.Iterator;
import java.util.List;
import lms.com.dao.*;
import lms.com.enums.ClientCategory;
import lms.com.models.Book;
import lms.com.models.Client;
import lms.com.test.TestBook;
import lms.com.test.TestBookCategory;
import lms.com.test.TestBookTransaction;
import lms.com.test.TestClient;
import lms.com.views.MainFrame;



public class MainClass {

  
    public static void main(String[] args) {
          MainFrame frame = new MainFrame();
          frame.setVisible(true);
//        Client cl = new Client("22456", "Schadrack", "Niyibizi", "+250780626422", "niyibizischadrack@gmail.com");
//        cl.setClientCategory(ClientCategory.STUDENT);
        
//          TestBook testBook = new TestBook();
//          TestClient testClient = new TestClient();
//          TestBookCategory testBookCategory = new TestBookCategory();
//          TestBookTransaction testBookTransaction = new TestBookTransaction();
//          BookDao bookDao = new BookDao();
//          ClientDao clientDao = new ClientDao();
//          BookCategoryDao category = new BookCategoryDao();
//          BookTransactionDao transDao = new BookTransactionDao();
          
//          testClient.testSaveClient();
//          testBookCategory.testSaveBookCategory();
//          testBook.testsaveBook();
//          testBookTransaction.testSaveBookTransaction();
          
          
          
//          List<Book> books = bookDao.retrieveBooks();
//          if(books!=null){
//              Iterator<Book> booksIter = books.iterator();
//              System.out.println("\n\nBooks list");
//              while(booksIter.hasNext()){
//                  Book book = booksIter.next();
//                  System.out.println("Book Id: " + book.getBookId());
//                  System.out.println("Book Title: " + book.getTitle());
//                  System.out.println("Book Author: " + book.getAuthor());
//                  System.out.println("Book publishing house: " + book.getPublishingHouse());
//                  System.out.println("Book publication date: " + book.getPublicationDate());
//                  System.out.println("Book pages: " + book.getPages());
//                  if(book.isAvailable()){
//                      System.out.println("Book Available: Yes\n\n----------------------------------------------\n\n");
//                  }else{
//                      System.out.println("Book Available: No\n\n---------------------------------------------\n\n");
//                  }
//                  bookDao.deleteBook(book.getBookId());
//                  
//              }
//          }
//          clientDao.retrieveClients();
//          category.retrieveBookCategory();
//          transDao.retrieveTransactions();
//        System.out.println(cl);
    }
    
}
