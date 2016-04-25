/* Add Personnel Window */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;

public class AddPersonel extends JPanel implements ActionListener {

   JTextField jtfName, jtfSurname, jtfDepartment, jtfPhoneNumber, jtfEmail;
   String imgAddr;
   JLabel jlblStatus;

   public AddPersonel() {
      initPanel();
   }

   private void initPanel() {
      this.setLayout( new BorderLayout() );
      this.setOpaque( true );
      this.setBackground( new Color(0,120,130) );

      
      /* jpnlTitle */
      JPanel jpnlTitle = new JPanel();
      jpnlTitle.setBackground( new Color(0,120,130) );
      jpnlTitle.setBorder( BorderFactory.createMatteBorder(0,0,3,0, new Color(0,60,65)) );

      JLabel jlblTitle = new JLabel( "Add Personnel" );
      jlblTitle.setForeground( new Color(0,60,65) );
      jpnlTitle.add( jlblTitle );
      this.add( jpnlTitle, BorderLayout.PAGE_START );
      /* jpnlTitle */

      /* jpnlInput */
      JPanel jpnlInput = new JPanel( new GridLayout(5,2) );
      jpnlInput.setBackground( new Color(0,120,130) );

      JLabel jlblName = new JLabel( "Name: " );
      jlblName.setForeground( Color.white );
      jpnlInput.add( jlblName );

      jtfName = new JTextField();
      jpnlInput.add( jtfName );


      JLabel jlblSurname = new JLabel( "Surname: " );
      jlblSurname.setForeground( Color.white );
      jpnlInput.add( jlblSurname );

      jtfSurname = new JTextField();
      jpnlInput.add( jtfSurname );


      JLabel jlblDepartment = new JLabel( "Department: " );
      jlblDepartment.setForeground( Color.white );
      jpnlInput.add( jlblDepartment );

      jtfDepartment = new JTextField();
      jpnlInput.add( jtfDepartment );


      JLabel jlblPhoneNumber = new JLabel( "Phone Number: " );
      jlblPhoneNumber.setForeground( Color.white );
      jpnlInput.add( jlblPhoneNumber );

      jtfPhoneNumber = new JTextField();
      jpnlInput.add( jtfPhoneNumber );


      JLabel jlblEmail = new JLabel( "e-mail address: " );
      jlblEmail.setForeground( Color.white );
      jpnlInput.add( jlblEmail );

      jtfEmail = new JTextField();
      jpnlInput.add( jtfEmail );

      jpnlInput.setBorder( BorderFactory.createMatteBorder(5,0,0,5, new Color(0,120,130)) );
      this.add( jpnlInput, BorderLayout.LINE_START );
      /* jpnlInput */

      /* jpnlImg */
      JPanel jpnlImg = new JPanel();
      jpnlImg.setLayout( new BoxLayout(jpnlImg,BoxLayout.Y_AXIS) );
      jpnlImg.setBorder( BorderFactory.createMatteBorder(5,0,0,0, new Color(0,120,130)) );

      final JLabel jlblImg = new JLabel( "<html>Click<br>here to<br>browse<br>image", JLabel.CENTER );
      jlblImg.setPreferredSize( new Dimension(75,125) );
      jlblImg.addMouseListener( new MouseAdapter()
      {
         public void mouseClicked( MouseEvent e )
         {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog( AddPersonel.this );

            if( result == JFileChooser.APPROVE_OPTION ) {
               File f = fc.getSelectedFile();
               imgAddr = f.getAbsolutePath();
               ImageIcon icon = new ImageIcon( imgAddr );
               jlblImg.setIcon( Sistem.shrinkImage(icon,75,125) );

            }
         }
      } );
      jpnlImg.add( jlblImg );

      this.add( jpnlImg, BorderLayout.LINE_END );
      /* jpnlImg */

      /* jpnlBottom */
      JPanel jpnlBottom = new JPanel();
      jpnlBottom.setLayout( new BorderLayout() );
      jpnlBottom.setBackground( new Color(0,120,130) );

      JButton jbAdd = new JButton( "Add" );
      jbAdd.addActionListener( this );
      jpnlBottom.add( jbAdd, BorderLayout.PAGE_START );

      jlblStatus = new JLabel( "Status: " );
      jpnlBottom.add( jlblStatus, BorderLayout.PAGE_END );

      this.add( jpnlBottom, BorderLayout.PAGE_END );
      /* jpnlBottom */

   }
   
   public void actionPerformed( ActionEvent e ) {

      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName( "org.sqlite.JDBC" );
         c = DriverManager.getConnection( "jdbc:sqlite:personnel.db" );
         c.setAutoCommit( false );

         stmt = c.createStatement();
         String sql = "INSERT INTO academic (name,surname,phone,email,department,image) " +
                      "VALUES ('" + jtfName.getText() + "','" + jtfSurname.getText() + "','" + jtfPhoneNumber.getText() + "','" + jtfEmail.getText() + "','" + jtfDepartment.getText() + "','" + imgAddr + "');";
         stmt.executeUpdate( sql );
         stmt.close();
         c.commit();
         c.close();
         jlblStatus.setText( "Insertion has been done succesfully" );

      }
      catch( Exception ex ) {
         jlblStatus.setText( "Error in insertion operation!" );
         System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
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
