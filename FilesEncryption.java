package projekti2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class FilesEncryption extends JFrame {

	private JPanel contentPane;
	private JTextField txtPathi;
	private File file;
	private boolean oldKeys;
	
	public static void main(String[] args) {
		FilesEncryption frame = new FilesEncryption();
		frame.setVisible(true);
	}
	
	public FilesEncryption() {
		GenerateKeys objGenerate = new GenerateKeys();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 360);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(154, 205, 50));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPathi = new JTextField();
		txtPathi.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtPathi.setBackground(new Color(255, 250, 250));
		txtPathi.setBounds(55, 67, 393, 32);
		contentPane.add(txtPathi);
		txtPathi.setColumns(10);
		
		JButton btnZgjedh = new JButton("Choose file");
		btnZgjedh.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnZgjedh.setBackground(new Color(255, 250, 250));
		btnZgjedh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
					txtPathi.setText(file.getAbsolutePath());
				}
				else {
					JOptionPane.showMessageDialog(null, "Ju nuk keni zgjedhur asnje fajll");
				}
			}
		});
		btnZgjedh.setBounds(55, 120, 183, 32);
		btnZgjedh.grabFocus();
		contentPane.add(btnZgjedh);
		
		JButton btnEnkripto = new JButton("Encrypt");
		btnEnkripto.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEnkripto.setBackground(new Color(255, 250, 250));
		btnEnkripto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(file != null) {
					try {
						EncryptFunctions objEncrypt = new EncryptFunctions();
						FileInputStream fis = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream("temp.dat");
						
						byte[] fileData = new byte[(int)file.length()];
						
						fis.read(fileData);
						fos.write(fileData);
						
						fos.close();
						fis.close();
						
						FileInputStream fis2 = new FileInputStream("temp.dat");
						FileOutputStream fos2 = new FileOutputStream(file);
						
						byte[] blockData = new byte[245];
						int i;
						while((i = fis2.read(blockData)) != -1) {
							byte [] encryptedData = objEncrypt.encrypt(blockData,0,i,oldKeys,objGenerate);
							fos2.write(encryptedData);
						}
						fis2.close();
						fos2.close();
					}
					
					catch(FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, "Gabimi "+e.getMessage());
					}
					catch(IOException e) {
						JOptionPane.showMessageDialog(null, "Gabimi "+e.getMessage());
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Nuk keni zgjedhur asnje fajll");
				}
				
			}
		});
		btnEnkripto.setBounds(55, 232, 183, 32);
		contentPane.add(btnEnkripto);
		
		JButton btnDekripto = new JButton("Decrypt");
		btnDekripto.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDekripto.setBackground(new Color(255, 250, 250));
		btnDekripto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(file != null) {
					try {
						EncryptFunctions objDecrypt = new EncryptFunctions();
						FileInputStream fis = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream("temp.dat");
						
						byte[] fileData = new byte[(int)file.length()];
						
						fis.read(fileData);
						fos.write(fileData);
						
						fos.close();
						fis.close();

						FileInputStream fis2 = new FileInputStream("temp.dat");
						FileOutputStream fos2 = new FileOutputStream(file);
						
						byte[] blockOfData = new byte[256];
						int i ;
						while((i = fis2.read(blockOfData)) != -1) {
							byte [] decryptedData = objDecrypt.decrypt(blockOfData,0,i,oldKeys,objGenerate);
							fos2.write(decryptedData);
						}
						
						fos2.close();
						fis2.close();
					}
					
					catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Gabimi " + e.getMessage());
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Nuk keni zgjedhur asnje fajll");
				}
			}
		});
		btnDekripto.setBounds(265, 232, 183, 32);
		contentPane.add(btnDekripto);
		
		JButton btnGenerate = new JButton("Generate keys");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				objGenerate.generate();
			}
		});
		btnGenerate.setBackground(new Color(255, 250, 250));
		btnGenerate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnGenerate.setBounds(265, 120, 183, 32);
		contentPane.add(btnGenerate);
		
		JButton btnExportKeys = new JButton("Export keys");
		btnExportKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oldKeys = true;
			}
		});
		btnExportKeys.setBackground(new Color(255, 250, 250));
		btnExportKeys.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExportKeys.setBounds(265, 174, 183, 32);
		contentPane.add(btnExportKeys);
		
		JButton btnSaveKeys = new JButton("Save keys");
		btnSaveKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					objGenerate.saveToFile("public.key",objGenerate.rsaPublicKey.getModulus(),objGenerate.rsaPublicKey.getPublicExponent());
					objGenerate.saveToFile("private.key",objGenerate.rsaPrivateKey.getModulus(),objGenerate.rsaPrivateKey.getPrivateExponent());
				}
				catch(IOException ex) {
					JOptionPane.showMessageDialog(null, "Gabimi " + ex.getMessage());
				}
			}
		});
		btnSaveKeys.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSaveKeys.setBackground(new Color(255, 250, 250));
		btnSaveKeys.setBounds(55, 174, 183, 32);
		contentPane.add(btnSaveKeys);
	}
}
