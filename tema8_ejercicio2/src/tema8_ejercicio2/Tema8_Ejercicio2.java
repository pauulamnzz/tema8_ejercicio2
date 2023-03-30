package tema8_ejercicio2;
//Prueba que no se borre
//Prueba que no se borrev2
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Tema8_Ejercicio2 {
	
	class ConnectionSingleton {
		private static Connection con;

		public static Connection getConnection() throws SQLException {
			String url = "jdbc:mysql://127.0.0.1:3307/provincias";
			String user = "alumno";
			String password = "alumno";
			if (con == null || con.isClosed()) {
				con = DriverManager.getConnection(url, user, password);
			}
			return con;
		}
	}
	
	private JFrame frmTemaEjercicio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tema8_Ejercicio2 window = new Tema8_Ejercicio2();
					window.frmTemaEjercicio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tema8_Ejercicio2() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemaEjercicio = new JFrame();
		frmTemaEjercicio.setTitle("TEMA 8 EJERCICIO 2");
		frmTemaEjercicio.setBounds(100, 100, 450, 300);
		frmTemaEjercicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTemaEjercicio.getContentPane().setLayout(null);
		
		JComboBox comboBoxProv = new JComboBox();
		comboBoxProv.setBounds(262, 122, 161, 24);
		frmTemaEjercicio.getContentPane().add(comboBoxProv);
		
		JComboBox comboBoxCom = new JComboBox();
		comboBoxCom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				//cuando cambia de opcion se ejecuta lo siguiente

				String url = "jdbc:mysql://127.0.0.1:3307/provincias";
				String user = "alumno";
				String password = "alumno";
				 try {
					 
					 Connection con=ConnectionSingleton.getConnection();					 
					 
		                int idcSelected = comboBoxCom.getSelectedIndex() + 1;
		                
		                // Preparar consulta para seleccionar provincias de la comunidad seleccionada
		                PreparedStatement sel_pstmt = con.prepareStatement(
		                    "SELECT nomp FROM provincia WHERE codc = ?");
		                sel_pstmt.setInt(1, idcSelected);

		                // Obtener resultados y agregarlos al comboBoxProv
		                ResultSet rs_sel = sel_pstmt.executeQuery();
		                comboBoxProv.removeAllItems();
		                while (rs_sel.next()) {
		                    comboBoxProv.addItem(rs_sel.getString("nomp"));
		                }
		                rs_sel.close();
		                sel_pstmt.close();

		            } catch (SQLException ex) {
		                System.out.println(ex.getMessage());
		            }	
				
			}
		});
		comboBoxCom.setBounds(30, 122, 208, 24);
		frmTemaEjercicio.getContentPane().add(comboBoxCom);
		
		JLabel lblComunidad = new JLabel("Comunidad:");
		lblComunidad.setBounds(79, 61, 113, 15);
		frmTemaEjercicio.getContentPane().add(lblComunidad);
		
		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(296, 61, 103, 15);
		frmTemaEjercicio.getContentPane().add(lblProvincia);
		
	
		String url = "jdbc:mysql://127.0.0.1:3307/provincias";
		String user = "alumno";
		String password = "alumno";
		try {
			
			Connection con=ConnectionSingleton.getConnection();//conectarse a la base de datos con la URL de conexión, el nombre de usuario y la contraseña especificados anteriormente.
			Statement stmt = con.createStatement();// ejecutar consultas SQL
		
			ResultSet rs = stmt.executeQuery("SELECT nomc FROM comunidad");//consulta para recuperar los nombres de las comunidades de la tabla "comunidad" y se almacena en un objeto ResultSet.
			while (rs.next()) {
				comboBoxCom.addItem(rs.getString("nomc"));
			}
			
			
			
			int idpSelected=(comboBoxCom.getSelectedIndex()+1);
			/*
			 *  consulta parametrizada que busca las provincias que pertenecen a la comunidad seleccionada.
			 *  Se utiliza el método PreparedStatement.setInt() para asignar el valor de idpSelected al parámetro de la consulta. 
			 *  Luego, se ejecuta la consulta y se almacena el resultado en un objeto ResultSet.
			 */
			PreparedStatement sel_pstmt = con.prepareStatement("SELECT nomp FROM provincia WHERE codc=?");
			sel_pstmt.setInt(1, idpSelected);
			ResultSet rs_sel = sel_pstmt.executeQuery();
			
			comboBoxProv.removeAllItems();
			while (rs_sel.next()) {
				comboBoxProv.addItem(rs_sel.getString("nomp"));

			}
			
			rs_sel.close();
			stmt.close();
			rs.close();
			sel_pstmt.close();
			
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
