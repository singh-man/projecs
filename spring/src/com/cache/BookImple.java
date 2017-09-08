package com.cache;

public class BookImple implements Book {

	private String book;
	
	@Override
	@Cacheable("Book.find")
	public String findBook(String book) {
		if(book.equals(this.book))
			System.out.println("Requested book found");
		else {
			System.out.println("no book found");
			return null;
		}
		return this.book;
	}

	@Override
	@Cacheable(value="Book.find", evict=true)
	public void addBook(String book) {
		this.book = book;
		System.out.println("Book Changed to : " + book);
	}

}
