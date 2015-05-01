package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import beans.Acteur;
import serveur.LanceurServer;
import Client.Client;

public class MaFrame extends JFrame implements ActionListener
{
	private JComboBox comboActeur;
	private JButton jbRafraichir, jbInfos, jbQuitter;
	private JTextField jtfanneeNaiss ;
	private JRadioButton jrbFeminin, jrbMasculin;
	private Client client;
	
	public MaFrame()
	{
		super();
		
		
		client = new Client();
		
		this.setTitle("Acteur");
		this.setSize(600, 300);
		setResizable(false);
		 
		//Création de la JPanel
		JPanel panelData = new JPanel();
		panelData.setLayout(new GridLayout(5, 2, 5, 5));
		
		//ComboBox
		panelData.add(new JLabel("Liste des acteurs :"));
		panelData.add(this.comboActeur = new JComboBox<>()); 
		
		//Radio boutton genre
		panelData.add(jrbFeminin=new JRadioButton("Féminin"));
		panelData.add(jrbMasculin=new JRadioButton("Masculin"));
		
		//On lie les radio bouton a un ButtonGroup
		ButtonGroup bg=new ButtonGroup();
		bg.add(jrbFeminin);
		bg.add(jrbMasculin);
		
		//Jfield année de naissance
		panelData.add(new JLabel("Année de naissance :"));
		panelData.add(this.jtfanneeNaiss = new JTextField());

		this.add(panelData);
		
		
		//Création du panel des boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridLayout(1, 5,5,5));
		
		//Bouton Rafraichir
		panelBoutons.add( this.jbRafraichir = new JButton("Rafraichir"));
		jbRafraichir.addActionListener(this);
		
		//Bouton Infos
		panelBoutons.add(this.jbInfos = new JButton("Infos"));
		jbInfos.addActionListener(this);
		
		//Boutons Quitter
		panelBoutons.add(this.jbQuitter = new JButton("Quitter"));
		jbQuitter.addActionListener(this);
		
		this.add(panelBoutons,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
		//Action sur le bouton Rafraichir
		if(e.getSource()==this.jbRafraichir)
		{
			for(Acteur actor : client.refresh())
			{
				comboActeur.addItem(actor);
			}
		}
		
		//Action sur le bouton Infos
		if(e.getSource()== this.jbInfos)
		{
			
		}
		
		//Action sur le bouton Quitter
		if(e.getSource()==this.jbQuitter)
		{
			client.quitter();
			this.dispose();
		}
	}
	
}
