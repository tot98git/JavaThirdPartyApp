package thirdPartyToken;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.net.httpserver.HttpServer;

public class MainFrame extends JFrame {	
	DefaultTableModel table;
	static HttpServer httpserver=null;
	public MainFrame() {
		
		JFrame f = new JFrame();
		FlowLayout experimentLayout = new FlowLayout();
		f.setLayout(experimentLayout);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnStart = new JButton("Start server");
		btnStart.setPreferredSize(new Dimension(100,50));
		JButton btnEnd = new JButton("Stop server");
		btnEnd.setPreferredSize(new Dimension(100,50));
		btnStart.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent ae) {
			        stopStartServer("start");
			        btnStart.setEnabled(false);
					btnEnd.setEnabled(true);
			     }
		});
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopStartServer("stop");
				btnEnd.setEnabled(false);
		        btnStart.setEnabled(true);
			}
		});
		f.add(btnStart);
		f.add(btnEnd);
		JScrollPane scrollPane = new JScrollPane(tokenCollection.table);
	    f.add(scrollPane, BorderLayout.CENTER);
	    f.setSize(500,500);
		f.setVisible(true);
	}
	public static void stopStartServer(String cmd) {
		if(cmd=="start") {
			try {
				httpserver= HttpServer.create(new InetSocketAddress(7000), 0);
				} 
			  catch (IOException ee) {
				ee.printStackTrace();
			  }
			httpserver.createContext("/get",new getHandler());
			httpserver.setExecutor(null);
			httpserver.start();
		}
		else if(cmd=="stop") {
			httpserver.stop(0);
		}
	}

	
}
