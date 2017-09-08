package hirondelle.stocks.quotes;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.logging.*;
import java.util.*;
import java.text.MessageFormat;

import hirondelle.stocks.util.Consts;
import hirondelle.stocks.util.Util;
import hirondelle.stocks.util.Args;
import hirondelle.stocks.util.DataAccessException;
import hirondelle.stocks.util.ui.UiConsts;
import hirondelle.stocks.util.ui.UiUtil;

import hirondelle.stocks.portfolio.CurrentPortfolio;
import hirondelle.stocks.table.QuoteTable;
import hirondelle.stocks.preferences.QuoteTablePreferencesEditor;

/**
* Fetch current quote data for the {@link CurrentPortfolio} from a data 
* source on the web.
*
* <P>This action is performed at many different times :
*<ul>
* <li>once upon startup
* <li>periodically, at an interval which is configured by the user
* <li>when the end user explicitly requests a refresh of quote info
* <li>when the user makes a change to the current portfolio
*</ul>
*
* <P>This class performs most of its work in a background daemon thread, and uses 
* <tt>EventQueue.invokeLater</tt> to update the user interface with the result. 
* The user interface remains responsive, regardless of the time taken for its 
* work to complete.
*
* <P>A daemon thread is used since daemon threads do not prevent an application 
* from terminating.
*/
public final class FetchQuotesAction extends AbstractAction implements Observer {

  /**
  * Constructor.
  *  
  * @param aCurrentPortfolio an <tt>Observable</tt> which notifies this 
  * object when the {@link CurrentPortfolio} is changed
  * @param aQuoteTablePrefEditor allows this class to read the user preference
  * for the frequency of periodic fetches
  * @param aQuoteTable a GUI element which is updated when a fetch is performed
  * @param aSummaryView a GUI element which is updated when a fetch is performed
  */
  public FetchQuotesAction (
    CurrentPortfolio aCurrentPortfolio, 
    QuoteTablePreferencesEditor aQuoteTablePrefEditor, 
    QuoteTable aQuoteTable, 
    SummaryView aSummaryView
  ) {
    super("Update", UiUtil.getImageIcon("/toolbarButtonGraphics/general/Refresh")); 
    Args.checkForNull(aQuoteTable);
    Args.checkForNull(aSummaryView);
    fCurrentPortfolio = aCurrentPortfolio;
    fCurrentPortfolio.addObserver(this);
    
    fQuoteTablePrefEditor = aQuoteTablePrefEditor;
    fQuoteTablePrefEditor.addObserver(this);
    
    fQuoteTable = aQuoteTable;
    fSummaryView = aSummaryView;
    
    putValue(SHORT_DESCRIPTION, "Fetch updated stock quotes from web");
    putValue(
      ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, UiConsts.NO_KEYSTROKE_MASK)
    );      
    putValue(
      LONG_DESCRIPTION, 
      "Retrieves fresh stock quotes and displays it to the user in a table."
    );
    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_U) );    

    fUpdateFreq = fQuoteTablePrefEditor.getUpdateFrequency();
    fTimer = new javax.swing.Timer(fUpdateFreq * CONVERSION_FACTOR, this);
    fTimer.start();
  }

  /** Fetch quotes from the web for the <tt>CurrentPortfolio</tt>.  */
  public void actionPerformed(ActionEvent e) {
    fLogger.info("Fetching quotes from web.");
    fSummaryView.showStatusMessage("Fetching quotes...");
    Thread worker = new Thread(new HardWorker());
    worker.setDaemon(true);
    worker.start();
  }
  
  /**
  * Listens for changes to the <tt>CurrentPortfolio</tt> or the user
  * preference for update frequency, and calls {@link #actionPerformed}.
  *
  * <P>In the case of changes to the update frequency, <tt>actionPerformed</tt> is 
  * called only if the udate frequency has been assigned a new value.
  */
  public void update(Observable aPublisher, Object aData) {
    fLogger.fine("Notified ...");
    if ( aPublisher == fQuoteTablePrefEditor ) {
      fLogger.fine("By StocksTablePrefEditor...");
      boolean hasChangedFreq = fQuoteTablePrefEditor.getUpdateFrequency()!= fUpdateFreq;
      if ( hasChangedFreq ) restartTimer();
    }
    else {
      fLogger.fine("By Current Portfolio...");
      actionPerformed(null);
    }
  } 

  // PRIVATE //

  private static final Logger fLogger = Util.getLogger(FetchQuotesAction.class);
  
  /**
  * The set of {@link Stock} objects in which the user 
  * is currently interested.
  */
  private CurrentPortfolio fCurrentPortfolio;
  
  /**
  * Edits user preferences attached to a table which presents quote data, and 
  * allows read-only programmatic access to such preferences.
  */
  private QuoteTablePreferencesEditor fQuoteTablePrefEditor;
  
  /**
  * GUI element which is updated whenever a new set of quotes is obtained.
  */
  private QuoteTable fQuoteTable;

  /**
  * GUI element which is updated whenever a new set of quotes is obtained.
  */
  private SummaryView fSummaryView;
  
  /**
  * Periodically fetches quote data.
  *
  * <P>Use of a Swing Timer is acceptable here, in spite of the fact that the task
  * takes a long time to complete, since the task does <em>not</em> in fact get 
  * executed on the event-dispatch thread (See below.)
  */
  private javax.swing.Timer fTimer;
  
  /**
  * The number of minutes to wait between fetches of quote information.
  */
  private int fUpdateFreq;
  
  private static final int CONVERSION_FACTOR = 
    Consts.MILLISECONDS_PER_SECOND * Consts.SECONDS_PER_MINUTE
  ;

  /**
  * Perform the fetch, and update the GUI elements using the event dispatch 
  * thread.
  *
  * <P>Should be run as a daemon thread, such that it never prevents the application
  * from exiting.
  */
  private final class HardWorker implements Runnable {
    public void run() {
      //simulateLongDelay();
      try {
        java.util.List<Quote> quotes = fCurrentPortfolio.getPortfolio().getQuotes();
        EventQueue.invokeLater( new GuiUpdater(quotes) );
      }
      catch(DataAccessException ex) {
        EventQueue.invokeLater( new GuiUpdater(ex) );
      }
    }
  }
  
  /**
  * Update the user interface after the fetch completes.
  *
  * <P>Must be run on the event dispatch thread. If the fetch fails for any reason, 
  * then any current quote data is left as is, and an error message is displayed in 
  * a status message.
  */
  private final class GuiUpdater implements Runnable {
    GuiUpdater( java.util.List<Quote> aQuotes ){
      fQuotes = aQuotes;
    }
    GuiUpdater(DataAccessException ex){
      //the exception is not used in this implementation
    }
    public void run(){
      if (fQuotes != null){
        fQuoteTable.setQuoteTable( fQuotes );
        fSummaryView.setQuotes( fQuotes );
        StringBuilder warning = new StringBuilder();
        if ( hasNoZeroPrices(warning) ){
          fSummaryView.showStatusMessage("Done.");
        }
        else {
          fSummaryView.showStatusMessage(warning.toString());
        }
      }
      else {
        fSummaryView.showStatusMessage("Failed - Please connect to the web.");
      }
    }
    private java.util.List<Quote> fQuotes;
    private MessageFormat fTickerWarningFormat = 
      new MessageFormat("Warning - no price for ticker {0} ({1})")
    ;
    private boolean hasNoZeroPrices(StringBuilder aMessage){
      for(Quote quote: fQuotes){
        if ( Util.isZeroMoney(quote.getPrice()) ) {
          Object[] params = {
            quote.getStock().getTicker(), 
            quote.getStock().getExchange()
          };
          aMessage.append(fTickerWarningFormat.format(params));
          return false;
        }
      }
      return true;
    }
  }
  
  /** Use for testing purposes only.  */
  private void simulateLongDelay(){
    try {
      Thread.sleep(20000);
    }
    catch (InterruptedException ex) {
      System.out.println(ex);
    }
  }
  
  private void restartTimer(){
    fLogger.fine("Resetting initial delay and delay to: " + fUpdateFreq + " minutes.");
    fUpdateFreq = fQuoteTablePrefEditor.getUpdateFrequency();
    fTimer.setInitialDelay(fUpdateFreq * CONVERSION_FACTOR);
    fTimer.setDelay(fUpdateFreq * CONVERSION_FACTOR);
    fLogger.fine("Cancelling pending tasks, and restarting timer...");
    fTimer.restart();
  }
}
