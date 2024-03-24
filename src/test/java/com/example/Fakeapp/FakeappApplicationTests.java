//package com.example.Fakeapp;
//
//import com.example.Fakeapp.dao.bookManager.Book;
//import com.example.Fakeapp.dao.bookManager.BookManagerDao;
//import com.example.Fakeapp.dao.bookManager.BookManagerSqlDao;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class FakeappApplicationTests {
//
//	@Test
//	void contextLoads() {
//		System.out.println("start running");
//		BookManagerDao bookManagerDao = new BookManagerSqlDao();
//		Book bookOne = new Book("title", "Stephen King", 1234434421L, "dsa");
//
//		assert true == bookManagerDao.add(bookOne);
//	}
//
//}
