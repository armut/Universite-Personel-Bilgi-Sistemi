/* List Staff Window */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ListPersonel extends JPanel implements ActionListener {
   
   JRadioButton jradName, jradSurname, jradDept;
   JButton jbList;
   JPanel jpnlList = new JPanel();
   JTextArea jtxa = new JTextArea();
   AcademicStaff[] academicStaff;

   public ListPersonel() {
      loadStaff();
      initPanel();
      listStaff();
   }

   private void initPanel() {
      this.setLayout( new BoxLayout(this,BoxLayout.Y_AXIS) );
      this.setOpaque( true );
      this.setBackground( new Color(0,120,130) );
      
      /* jpnlTitle */
      JPanel jpnlTitle = new JPanel();
      jpnlTitle.setBackground( new Color(0,120,130) );
      jpnlTitle.setBorder( BorderFactory.createMatteBorder(0,0,3,0, new Color(0,60,65)) );

      JLabel jlblTitle = new JLabel( "List Staff" );
      jlblTitle.setForeground( new Color(0,60,65) );
      jpnlTitle.add( jlblTitle );
      this.add( jpnlTitle );
      /* jpnlTitle */


      /* jpnlOpt */
      JPanel jpnlOpt = new JPanel( new GridBagLayout() );
      GridBagConstraints c = new GridBagConstraints();
      jpnlOpt.setBorder( BorderFactory.createMatteBorder(5,0,0,0, new Color(0,120,130)) );
      jpnlOpt.setBackground( AnaPencere.lightCyan );

      jradName = new JRadioButton( "Name" );
      jradName.setOpaque( false );
      jradName.setSelected( true );

      jradSurname = new JRadioButton( "Surname" );
      jradSurname.setOpaque( false );

      jradDept = new JRadioButton( "Dept." );
      jradDept.setOpaque( false );

      // Group the radio buttons.
      ButtonGroup group = new ButtonGroup();
      group.add( jradName );
      group.add( jradSurname );
      group.add( jradDept );

      // Register a listener for the radio buttons.
      jradName.addActionListener( this );
      jradSurname.addActionListener( this );
      jradDept.addActionListener( this );

      jbList = new JButton( "List" );
      jbList.addActionListener( this );

      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      jpnlOpt.add( jradName, c );

      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 1;
      jpnlOpt.add( jradSurname, c );

      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 2;
      jpnlOpt.add( jradDept, c );

      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridwidth = 3;
      c.gridx = 0;
      c.gridy = 1;
      //jpnlOpt.add( jbList, c );
      //this.add( jpnlOpt );
      /* jpnlOpt */

      jpnlList.setLayout( new BoxLayout(jpnlList,BoxLayout.Y_AXIS) );
      jtxa.setFont( new Font("Courier",Font.ITALIC,14) );
      jtxa.setTabSize( 14 );
      jtxa.setVisible( false );
      jpnlList.add( jtxa );
      this.add( jpnlList );

   }
   

   boolean sortByName = true, sortBySurname = false, sortByDept = false;
   public void actionPerformed( ActionEvent e ) {

      if( e.getSource() == jradName ) {
         sortByName = true;
         sortBySurname = false;
         sortByDept = false;
      }
      else if( e.getSource() == jradSurname ) {
         sortByName = false;
         sortBySurname = true;
         sortByDept = false;
      }
      else if( e.getSource() == jradDept ) {
         sortByName = false;
         sortBySurname = false;
         sortByDept = true;
      }
      else if( e.getSource() == jbList ) {
         listStaff();
      }
   }


   private void listStaff() {
      jtxa.setText( null );
      jtxa.append( "Name \t Surname \t Department \t \n" );
      jtxa.append( "---- \t --------\t ---------- \t \n" );

      if( sortByName ) {
         for( int i = 0; i < academicStaff.length; i++ ) {
            jtxa.append( academicStaff[i].getName() + "\t " + academicStaff[i].getSurname() + "\t " + academicStaff[i].getDept() + "\n" );
            jtxa.setVisible( true );
         }
      }
      else if( sortBySurname ) {
      }
      else if( sortByDept ) {
      }
   }


   private void loadStaff() {

      Connection c = null;
      Statement stmt = null;
      int count = 0;

      try {

         Class.forName( "org.sqlite.JDBC" );
         c = DriverManager.getConnection( "jdbc:sqlite:personnel.db" );
         c.setAutoCommit( false );
         

         stmt = c.createStatement();
         ResultSet rs4count = stmt.executeQuery( "SELECT *  FROM academic;" );
         // this loop is to count number of rows.
         while( rs4count.next() ) {
            count++;
         }

         academicStaff = new AcademicStaff[count];

         // this loop is to fetch data
         ResultSet rs = stmt.executeQuery( "SELECT *  FROM academic;" );
         for( int i = 0; i < count; i++ ) {
            rs.next();
            academicStaff[i] = new AcademicStaff();
            academicStaff[i].setName( rs.getString("name") );
            academicStaff[i].setSurname( rs.getString("surname") );
            academicStaff[i].setDept( rs.getString("department") );
            academicStaff[i].setPhoneNumber( rs.getString("phone") );
            academicStaff[i].setEmail( rs.getString("email") );
            academicStaff[i].setImage( rs.getString("image") );
         }
         rs.close();
         stmt.close();
         c.close();
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
