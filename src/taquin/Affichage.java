package taquin;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import static taquin.Main.*;

public class Affichage extends JFrame {
    private static final int PIXEL_PER_SQUARE = 100;
    private static JLabel[][] tabC;
    private JLabel label_deplacement;
    private JPanel contentPane = new JPanel(new BorderLayout());



    public Affichage() {
        setTitle("TAQUIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Plateau.getSize() * PIXEL_PER_SQUARE, Plateau.getSize() * PIXEL_PER_SQUARE);
        tabC = new JLabel[Plateau.getSize()][Plateau.getSize()];

        label_deplacement = new JLabel();
        JPanel deplacementPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        deplacementPane.add(label_deplacement);

                
        JPanel jeuPane = new JPanel(new GridLayout(Plateau.getSize(), Plateau.getSize()));
        contentPane.add(deplacementPane,BorderLayout.NORTH);
        

        for (int i = 0; i < Plateau.getSize(); i++) {
            for (int j = 0; j < Plateau.getSize(); j++) {

                Border border = BorderFactory.createLineBorder(Color.darkGray, 5);
                if(plateau.getCase(i,j)=="  "){
                    tabC[i][j] = new JLabel();
                    tabC[i][j].setBorder(border);
                    tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    tabC[i][j] = new JLabel(plateau.getCase(i,j));
                    tabC[i][j].setBorder(border);
                    tabC[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                }
                jeuPane.add(tabC[i][j]);
                setVisible(true);
            }

        }
        contentPane.add(jeuPane,BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    public void AffichageGraphique() {
        SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            label_deplacement.setText("Deplacement n° : " + Plateau.nbrDeplacement +"");
            for (int i = 0; i < Plateau.getSize(); i++) {
                for (int j = 0; j < Plateau.getSize(); j++) {
                    
                    tabC[i][j].setForeground(Color.black);
                    if(plateau.getCase(i,j)=="  "){
                        tabC[i][j].setText("");
                    } else {
                        tabC[i][j].setText(plateau.getCase(i,j));

                    }
                }
            }
        }
    });
    }
}
