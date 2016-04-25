/* Delete Personnel Window */
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class DeletePersonel extends JPanel {
   
   public DeletePersonel() {
      initPanel();
      initStaff();
   }

   private void initPanel() {
      this.setLayout( new BorderLayout() );
      this.setOpaque( true );
      this.setBackground( new Color(0,60,65) );
      
      JPanel jpnlTitle = new JPanel();
      jpnlTitle.setBackground( new Color(0,120,130) );
      jpnlTitle.setBorder( BorderFactory.createMatteBorder(0,0,3,0, new Color(0,60,65)) );

      JLabel jlblTitle = new JLabel( "Delete Personnel" );
      jlblTitle.setForeground( new Color(0,60,65) );
      jpnlTitle.add( jlblTitle );
      this.add( jpnlTitle, BorderLayout.PAGE_START );

   }
   
   
   private void initStaff() {
      JPanel jpnlVessel = new JPanel();
      jpnlVessel.setBackground( new Color(0,120,130) );
      jpnlVessel.setLayout( new GridLayout(0,2) );
      jpnlVessel.setBorder( BorderFactory.createMatteBorder(5,0,0,0, new Color(0,120,130)) );
      
      JPanel jpnlTag;
      JLabel jlblTag;
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName( "org.sqlite.JDBC" );
         c = DriverManager.getConnection( "jdbc:sqlite:personnel.db" );
         c.setAutoCommit( false );

         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery( "SELECT * FROM academic;" );
         while( rs.next() ) {
            jlblTag = new JLabel( "<html>" +  rs.getString("name") + " " + rs.getString("surname") + "<br><i>" + rs.getString("email") + "</i>" );
            jpnlTag = new JPanel();
            jpnlTag.setBorder( BorderFactory.createLineBorder(Color.black) );
            jpnlTag.setBackground( new Color(255,160,122) );
            jpnlTag.add( jlblTag );
            jpnlVessel.add( jpnlTag );
         }
         rs.close();
         stmt.close();
         c.close();
         this.add( jpnlVessel, BorderLayout.PAGE_END );
      }
      catch( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit( 0 );
      }
   }
   

   public void addTo( JPanel jpnlContainer ) {

      //clear the container JPanel first.
      jpnlContainer.removeAll();

      //add object(s) to the container.
      jpnlContainer.add( this );

      //update the container JPanel
      jpnlContainer.revalidate();
      jpnlContainer.repaint();
   }


   
   // Sets icon for a jLabel with given address
   public void setImg( String address, JLabel jlbl ) {
      jlbl.setIcon( new ImageIcon(address) );
      jlbl.setHorizontalAlignment( JLabel.CENTER );
   }




}
