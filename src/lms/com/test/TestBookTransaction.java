
package lms.com.test;

import java.sql.Date;
import java.time.LocalDate;
import lms.com.dao.BookTransactionDao;
import lms.com.enums.TransactionType;
import lms.com.models.BookTransaction;


public class TestBookTransaction {
    BookTransactionDao transDao = new BookTransactionDao();
    
    public void testSaveBookTransaction(){
        BookTransaction trans = new BookTransaction(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2021, 3, 5)), TransactionType.BORROW.toString());
        transDao.saveBookTransaction(trans, "BSN12345", "C1234");
    }
}
