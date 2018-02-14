package hirondelle.stocks.quotes;

import junit.framework.*;
import java.util.*;
import java.math.BigDecimal;
import hirondelle.stocks.util.DataAccessException;

/**
* JUnit test cases for {@link QuotesDAO}.
*
* <P>These tests require an open web connection, and that the hard-coding 
* of <tt>QuotesDAO.fOFF_LINE</tt> is set to <tt>false</tt>.
*
* <P>When run from inside the Sun ONE IDE on Windows, the following error occurs: 
 <pre>
 Caused by: java.net.SocketException: Unrecognized Windows Sockets error: 10106: create
        at java.net.Socket.createImpl(Socket.java:312)
        at java.net.Socket.connect(Socket.java:423)
        at java.net.Socket.connect(Socket.java:375)
        at sun.net.NetworkClient.doConnect(NetworkClient.java:139)
        at sun.net.www.http.HttpClient.openServer(HttpClient.java:366)
</pre>
* This error is not seen if the test is run outside the IDE.
*/
public final class TESTQuotesDAO extends TestCase {
  
  /** Run the test cases.  */
  public static void main (String... aArgs) {
    String[] testCaseName = { TESTQuotesDAO.class.getName ()};
    junit.textui.TestRunner.main(testCaseName);
  }
  
  public TESTQuotesDAO ( String aName) {
    super ( aName );
  }
  
  // TEST CASES //
  
  public void testSuccessfulConstruction(){
    try {
      Collection<Stock> empty = Collections.emptyList();
      QuotesDAO dao = new QuotesDAO(QuotesDAO.UseMonitor.TRUE, empty);
      dao = new QuotesDAO(QuotesDAO.UseMonitor.FALSE, fStocks);
      dao = new QuotesDAO(QuotesDAO.UseMonitor.TRUE, fStocks);
    }
    catch (Throwable ex){
      fail("Ctor failed unexpectedly.");
    }
  }
  
  public void testFailedConstruction(){
    testFailedCtor(null, Collections.EMPTY_LIST);
    testFailedCtor(QuotesDAO.UseMonitor.TRUE, null);
  }
  
  
  public void testEmptyStocks () throws DataAccessException {
    Set<Stock> stocks = new LinkedHashSet<Stock>();
    assertTrue ( stocks.isEmpty() );
    QuotesDAO quotes = new QuotesDAO (QuotesDAO.UseMonitor.TRUE, stocks);
    List result = quotes.getQuotes();
    assertTrue ( result.isEmpty() ); 
  }
  
  public void testQuotesSizeAndOrder() throws DataAccessException {
    List quotes = fDao.getQuotes();
    assertTrue( quotes.size() == fStocks.size() );
    Iterator quotesIter = quotes.iterator();
    Iterator stocksIter = fStocks.iterator();
    while ( quotesIter.hasNext() ){
      Quote quote = (Quote)quotesIter.next();
      Stock stock = (Stock)stocksIter.next();
      assertTrue( quote.getStock() == stock );
    }
  }
  
  public void testQuotesSizeAndContentForInvalidTicker() throws DataAccessException {
    Stock blah = new Stock(
      "Blah", "BLAH", fTSE, 100, new BigDecimal("10.00") 
    );
    fStocks.add(blah);
    QuotesDAO dao = new QuotesDAO(QuotesDAO.UseMonitor.FALSE, fStocks);
    List quotes = dao.getQuotes();
    assertTrue( quotes.size() == fStocks.size() );
    Iterator quotesIter = quotes.iterator();
    while ( quotesIter.hasNext() ) {
      Quote quote = (Quote) quotesIter.next();
      if ( quote.getStock() == blah ) {
        assertTrue( quote.getPercentChange().equals(new BigDecimal("0.00")));
        assertTrue( quote.getPercentProfit().equals(new BigDecimal("0.00")) );
      }
    }
  }
  
  // PRIVATE  //
  private static final Collection<Stock> fStocks;
  private static final Exchange fNasdaq = Exchange.valueFrom("Nasdaq Stock Exchange");
  private static final Exchange fTSE = Exchange.valueFrom("Toronto Stock Exchange");
  private static final Exchange fNYSE = Exchange.valueFrom("NYSE Stock Exchanges");
  
  static {
    fStocks = new ArrayList<Stock>();
    Stock sunw = new Stock(
      "Sun", "SUNW", fNasdaq, 100, new BigDecimal("10.00") 
    );
    Stock ctr = new Stock(
      "Cdn Tire", "CTR", fTSE, 100, new BigDecimal("10.00") 
    );
    Stock ibm = new Stock(
      "Ibm", "IBM", fNYSE, 100, new BigDecimal("10.00") 
    );
    Stock sp500 = new Stock(
      "S&P 500", "^GSPC", fNYSE, 0, new BigDecimal("0.00") 
    );
    Stock nasdaq = new Stock(
      "Nasdaq", "^IXIC", fNasdaq, 0, new BigDecimal("0.00") 
    );
    fStocks.add( sunw );
    fStocks.add( ctr );
    fStocks.add( ibm );
    fStocks.add( sp500 );
    fStocks.add( nasdaq );
  }
  
  private QuotesDAO fDao = new QuotesDAO(QuotesDAO.UseMonitor.FALSE, fStocks);
  
  private void testFailedCtor(
    QuotesDAO.UseMonitor aUseMonitor, Collection<Stock> aCollection
  ){
    boolean hasSucceeded = true;
    try {
      QuotesDAO dao = new QuotesDAO(aUseMonitor, aCollection);
    }
    catch (Throwable ex){
      hasSucceeded = false;
    }
    if (  hasSucceeded ) fail();
  }
}
