//I used a program to generate the majority of this file, I'm not good with jframes and I didn't want to bother with it

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class config extends JFrame 
{
static config theconfig;


class Panel0 extends JPanel implements ActionListener 
{
   JButton btBut1;
   JLabel lbLabel1;
   JLabel lbLabel2;
   JSpinner spnSpinner0;
   JSpinner spnSpinner1;
   SpinnerNumberModel spnModel0;
   SpinnerNumberModel spnModel1;


   public Panel0() 
   {
      super();
      setBorder( BorderFactory.createTitledBorder( "Config" ) );

      GridBagLayout gbPanel0 = new GridBagLayout();
      GridBagConstraints gbcPanel0 = new GridBagConstraints();
      setLayout( gbPanel0 );

      btBut1 = new JButton( "Start"  );
      btBut1.addActionListener( this );
      gbcPanel0.gridx = 1;
      gbcPanel0.gridy = 2;
      gbcPanel0.gridwidth = 2;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 1;
      gbcPanel0.weighty = 1;
      gbcPanel0.anchor = GridBagConstraints.NORTH;
      gbPanel0.setConstraints( btBut1, gbcPanel0 );
      add( btBut1 );

      lbLabel1 = new JLabel( "Mouse Sensitivity"  );
      gbcPanel0.gridx = 0;
      gbcPanel0.gridy = 0;
      gbcPanel0.gridwidth = 2;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 1;
      gbcPanel0.weighty = 1;
      gbcPanel0.anchor = GridBagConstraints.CENTER;
      gbPanel0.setConstraints( lbLabel1, gbcPanel0 );
      add( lbLabel1 );

      lbLabel2 = new JLabel( "Map Size"  );
      gbcPanel0.gridx = 0;
      gbcPanel0.gridy = 1;
      gbcPanel0.gridwidth = 2;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 1;
      gbcPanel0.weighty = 1;
      gbcPanel0.anchor = GridBagConstraints.NORTH;
      gbPanel0.setConstraints( lbLabel2, gbcPanel0 );
      add( lbLabel2 );

      spnModel0 = new SpinnerNumberModel (5, 1, 10, 1);
      spnSpinner0 = new JSpinner(spnModel0);
      gbcPanel0.gridx = 2;
      gbcPanel0.gridy = 0;
      gbcPanel0.gridwidth = 1;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 1;
      gbcPanel0.weighty = 0;
      gbcPanel0.anchor = GridBagConstraints.NORTH;
      gbPanel0.setConstraints( spnSpinner0, gbcPanel0 );
      add( spnSpinner0 );

      spnModel1 = new SpinnerNumberModel (10, 3, 50, 1);
      spnSpinner1 = new JSpinner(spnModel1);
      gbcPanel0.gridx = 2;
      gbcPanel0.gridy = 1;
      gbcPanel0.gridwidth = 1;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 1;
      gbcPanel0.weighty = 0;
      gbcPanel0.anchor = GridBagConstraints.NORTH;
      gbPanel0.setConstraints( spnSpinner1, gbcPanel0 );
      add( spnSpinner1 );
   } 

   public void actionPerformed( ActionEvent e ) 
   {
      if ( e.getSource() == btBut1 ) 
      {
         int sensitivity = (int) spnSpinner0.getValue();
         int mapSize = (int) spnSpinner1.getValue();

         Engine engine = null;
         engine.startup(sensitivity,mapSize);
         setVisible(false); //you can't see me!
         dispose(); //Destroy the JFrame object
      }
   } 
} 

Panel0 pnPanel0;

public static void main( String args[] ) 
{
   try 
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
   }
   catch ( ClassNotFoundException e ) 
   {
   }
   catch ( InstantiationException e ) 
   {
   }
   catch ( IllegalAccessException e ) 
   {
   }
   catch ( UnsupportedLookAndFeelException e ) 
   {
   }
   theconfig = new config();
} 

public config() 
{
   super();

   pnPanel0 = new Panel0();

   setDefaultCloseOperation( EXIT_ON_CLOSE );
   setLocationRelativeTo(null);
   setContentPane( pnPanel0 );
   pack();
   setVisible( true );
} 
} 
