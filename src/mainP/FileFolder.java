package mainP;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.JTextPane;

public class FileFolder extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JScrollPane scroller;
	private JScrollPane scroller_1;
	private JTextPane textPane;
	private JTextPane textPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileFolder frame = new FileFolder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class actionListener implements ActionListener {

		private void addTextPane(JTextPane tp, String msg, Color c) {
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

			aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
			aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

			int len = tp.getDocument().getLength();
			tp.setCaretPosition(len);
			tp.setCharacterAttributes(aset, false);
			tp.replaceSelection(msg);
		}

		public void actionPerformed(ActionEvent e) {
			String path1 = textField.getText();
			String path2 = textField_1.getText();
			File directory = null;
			File directory2 = null;
			String[] files = null;
			String[] files2 = null;
			textPane.setText("");
			textPane_1.setText("");
			boolean check = false;

			try {

				directory = new File(path1);

			} catch (Exception ex) {
				System.out.println("There is not a such DIRECTORY");
				ex.printStackTrace();
			}

			try {

				directory2 = new File(path2);

			} catch (Exception ex) {
				System.out.println("There is not a such DIRECTORY");
				ex.printStackTrace();
			}

			files = directory.list();
			files2 = directory2.list();

			if (directory.isDirectory() && directory2.isDirectory()) {
				File[] file1 = directory.listFiles();
				File[] file2 = directory2.listFiles();
				File f1;
				File f2;

				if (files == null) {
					return;
				}
				if (files2 == null) {
					return;
				}

				for (int i = 0; i < files.length; i++) {
					for (int j = 0; j < files2.length; j++) {
						if (files[i].equals(files2[j])) {
							check = true;
							break;
						}
					}
					f1 = new File(file1[i].getAbsolutePath());
					if (check && f1.isFile()) {
						addTextPane(textPane, files[i] + "\n", Color.GREEN);
					} else if (f1.isFile()) {
						addTextPane(textPane, files[i] + "\n", Color.RED);
					} else {
						addTextPane(textPane, files[i] + "\n", Color.BLACK);
					}
					check = false;
				}

				for (int i = 0; i < files2.length; i++) {
					for (int j = 0; j < files.length; j++) {
						if (files2[i].equals(files[j])) {
							check = true;
							break;
						}
					}
					f2 = new File(file2[i].getAbsolutePath());
					if (check && f2.isFile()) {
						addTextPane(textPane_1, files2[i] + "\n", Color.GREEN);
					} else if (f2.isFile()) {
						addTextPane(textPane_1, files2[i] + "\n", Color.RED);
					} else {
						addTextPane(textPane_1, files2[i] + "\n", Color.BLACK);
					}
					check = false;
				}
			} else if (directory.isFile() && directory2.isFile()) {
				InputStream file1 = null;
				InputStream file2 = null;
				int data1 = 0;
				int data2 = 0;
				boolean checkData = false;

				try {
					file1 = new FileInputStream(path1);
				} catch (FileNotFoundException e1) {
					System.out.println("There is not a such File");
					e1.printStackTrace();
				}

				try {
					file2 = new FileInputStream(path2);
				} catch (FileNotFoundException e1) {
					System.out.println("There is not a such File");
					e1.printStackTrace();
				}

				try {
					while (data1 != -1 && data2 != -1) {
						data1 = file1.read();
						data2 = file2.read();

						if (data1 == data2) {
							checkData = true;
						} else {
							checkData = false;
							break;
						}
					}
				} catch (Exception ex) {
					System.out.println("Error occured while checking FILES");
				}

				if (checkData) {
					addTextPane(textPane, "Files are IDENTICAL", Color.BLUE);
					addTextPane(textPane_1, "Files are IDENTICAL", Color.BLUE);
				} else {
					addTextPane(textPane, "Files are NOT IDENTICAL", Color.RED);
					addTextPane(textPane_1, "Files are NOT IDENTICAL", Color.RED);
				}

				checkData = false;
			} else {
				addTextPane(textPane, "Comparison is IMPOSSIBLE", Color.RED);
				addTextPane(textPane_1, "Comparision is IMPOSSIBLE", Color.RED);

			}

		}

	}

	/**
	 * Create the frame.
	 */
	public FileFolder() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 395);

		setTitle("File and Folder Comparing Utility");
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		actionListener listener = new actionListener();
		JButton btnStart = new JButton("START");
		btnStart.setForeground(Color.BLACK);
		btnStart.setBackground(Color.YELLOW);
		btnStart.addActionListener(listener);

		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField.setText("Path 1");
		textField_1.setText("Path 2");
		scroller = new JScrollPane();
		scroller_1 = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(textField)
								.addComponent(scroller, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
						.addGap(32)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(textField_1)
								.addComponent(scroller_1, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
						.addGap(19)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnStart)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scroller, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
						.addComponent(scroller_1, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))));

		textPane_1 = new JTextPane();
		scroller_1.setViewportView(textPane_1);

		textPane = new JTextPane();
		scroller.setViewportView(textPane);

		contentPane.setLayout(gl_contentPane);
	}
}
