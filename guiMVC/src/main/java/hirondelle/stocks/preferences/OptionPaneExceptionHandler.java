package hirondelle.stocks.preferences;

import java.util.logging.*;
import javax.swing.JOptionPane;
import java.awt.EventQueue;
import hirondelle.stocks.util.Consts;

/**
* An implementation of a Java Logging API {@link Handler}
* which displays a short message to the user using a <tt>JOptionPane</tt>, and 
* is intended only for <tt>Level.SEVERE</tt> messages.
*
* <P>The issue arises of how to inform the user in the case of a 
* <tt>Level.SEVERE</tt> message : if all 
* <tt>Handler</tt>s are purely textual, then in a graphical application it is 
* not uncommon for the user to be unaware of the problem, since the textual output  
* is not necessarily visible. This <tt>Handler</tt> addresses the issue by defining 
* a graphical logging <tt>Handler</tt>, which will always get the user's attention 
* by using a visual pop-up message.
* 
* <P>This <tt>Handler</tt> is unusual for two reasons :
* <ul>
* <li> it is intended only for <tt>SEVERE</tt> messages, so 
* that the user is never overloaded with too many pop-up messages.
* <li>it is intended only for programmatic use, and should not appear in the 
* <tt>logging.properties</tt> config file. 
*</ul>
*
* <P>The content of the message is taken from 
* {@link LogRecord#getMessage}, which is usually a short piece 
* of text. The intent is that this <tt>Handler</tt> present only the barest
* facts, and that the user refer to other text-based <tt>Handler</tt> tools 
* for further information.
*
* <P>This <tt>Handler</tt> can be used both from the event dispatch thread, 
* and from any other thread. Thus, it may be used by any worker thread which 
* is experiencing difficulties.
*
* <P>Typically, this <tt>Handler</tt> is attached to the root <tt>Logger</tt>,
* and is thus inherited by all all other <tt>Logger</tt>s.
*/
public final class OptionPaneExceptionHandler extends Handler {

  /*
  * Implementation Note :
  * A simple way to test the behavior of this class is 
  * to attempt a File->Import with corrupted data.
  */
  
  /**
  * Construct this <tt>Handler</tt> with default settings.
  *
  * These defaults should not be changed :
  *<ul>
  * <li><tt>Level</tt> is set to <tt>SEVERE</tt>
  * <li>{@link Formatter} is set to a {@link SimpleFormatter}
  *</ul>
  */
  public OptionPaneExceptionHandler(){
    setFormatter( new SimpleFormatter() );
    setLevel(Level.SEVERE);
  }

  /** No-operation. */
  public void close() { }
  
  /** No-operation.  */
  public void flush() { }

  /**
  * If <tt>aLogRecord</tt> satisfies {@link Handler#isLoggable},
  * then a short message is displayed to the user using a <tt>JOptionPane</tt>.
  *
  * <P>The message is presented in a modal dialog, and will grab the focus. 
  *
  * <P> Warning : if a <tt>SEVERE</tt> message is generated during startup, before 
  * the main window is displayed, then any pop-up message generated by this class will 
  * eventually be hidden behind the main window.
  */
  public void publish(LogRecord aLogRecord) {
    if ( ! isLoggable(aLogRecord) ) return;
    
    if ( EventQueue.isDispatchThread() ) {
      showGui(aLogRecord);
    }
    else {
      EventQueue.invokeLater( new Worker(aLogRecord) );
    }
  }

  /**
  * Add a <tt>OptionPaneExceptionHandler</tt> to the root <tt>Logger</tt>.
  *
  *<P>Typically, the <tt>Handler</tt> attached to the root <tt>Logger</tt> is 
  * inherited by all other <tt>Logger</tt>s.
  *
  * <P>Called both upon startup and when refreshing the 
  * <tt>logging.properties</tt> file. In the case of startup, it is best to 
  * call this method only after the main window is shown ; otherwise, it is likely that
  * any startup error messages generated by this class will be hidden
  * by the main window.
  */
  public static void attachToRootLogger(){
    Handler handler =  new OptionPaneExceptionHandler();
    Logger rootLogger = Logger.getLogger(Consts.EMPTY_STRING);
    rootLogger.addHandler(handler);
  }
  
  // PRIVATE //
  
  private static final String TITLE = "Error Occured";
  
  private void showGui(LogRecord aLogRecord){
    String message = getFormatter().formatMessage(aLogRecord);
    JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.ERROR_MESSAGE);
  }
  
  private class Worker implements Runnable {
    Worker(LogRecord aLogRecord){
      fLogRecord = aLogRecord;
    }
    public void run(){
      showGui(fLogRecord);
    }
    private LogRecord fLogRecord;
  }
}