import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.ResultSetMetaData;

import javax.swing.JTextArea;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ProgettoBDFinaleFrame extends JFrame {
	public Connection con = null;
	public java.sql.Statement stmt = null;

	public ProgettoBDFinaleFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 440);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		JButton btnConnect = new JButton("Connect");
		panel.add(btnConnect);

		JButton btnExecstatement = new JButton("ExecStatement");
		btnExecstatement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnExecstatement);

		JButton btnExecquery = new JButton("ExecQuery");
		panel.add(btnExecquery);

		JButton btnClear = new JButton("Clear");
		panel.add(btnClear);

		JTextArea txtrOutput = new JTextArea();
		txtrOutput.setFont(new Font("monospaced", Font.PLAIN, 12));
		getContentPane().add(txtrOutput, BorderLayout.CENTER);

		class ButtonListener1 implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://localhost:3306/hairdresser_reservation";
					String username = "root";
					String pwd = "password";
					con = DriverManager.getConnection(url, username, pwd);
					txtrOutput.setText("Connected to DB");
				} catch (Exception e) {
					System.out.println("Failed connection");
				}

			}

		}
		ButtonListener1 bl1 = new ButtonListener1();
		btnConnect.addActionListener(bl1);

		class ButtonListener2 implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				txtrOutput.setText(null);

			}

		}

		ButtonListener2 bl2 = new ButtonListener2();
		btnClear.addActionListener(bl2);

		class ButtonListener3 implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {
				try {
					stmt = con.createStatement();
					stmt.executeUpdate(txtrOutput.getText());
					txtrOutput.setText("Statement executed");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		ButtonListener3 bl3 = new ButtonListener3();
		btnExecstatement.addActionListener(bl3);

		class ButtonListener4 implements ActionListener {

			public void actionPerformed(ActionEvent arg0) {

				try {
					stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(txtrOutput.getText());
					txtrOutput.setText(null);
					String columnValue = null;
					java.sql.ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();

					for (int i = 1; i <= columnsNumber; i++) {
						columnValue = String.format("%1$20s", rsmd.getColumnName(i));
						txtrOutput.append(columnValue + " ");
					}

					while (rs.next()) {
						txtrOutput.append("\n");

						for (int i = 1; i <= columnsNumber; i++) {
							if (i > 1)
								txtrOutput.append(" ");
							columnValue = String.format("%1$20s", rs.getString(i));
							txtrOutput.append(columnValue + " ");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		ButtonListener4 bl4 = new ButtonListener4();
		btnExecquery.addActionListener(bl4);

	}

}
