
package lms.com.test;

import java.sql.Date;
import java.time.LocalDate;
import lms.com.dao.BookDao;
import lms.com.models.*;


public class TestBook {
    BookDao bookDao;        
    
    public void testsaveBook(){
        Date date = Date.valueOf(LocalDate.now());
        bookDao = new BookDao();
        Book book = new Book("BSN12345", "Intro to python programming", "Schadrack Niyibizi", "Califonia house of program",date, 250);
        bookDao.saveBook(book, "BC1234");
    }
    
}
