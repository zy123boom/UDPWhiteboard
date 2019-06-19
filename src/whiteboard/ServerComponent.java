package whiteboard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JComponent;


//�����
public class ServerComponent extends JComponent {
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int SIDELENGTHOFSQUARE = 20;// �߳�
	private static final int DIAMETEROFCIRCLE = 40;
	private ArrayList<Rectangle2D> squares; // �淽���list
	private ArrayList<Ellipse2D> circles;// ��ԲȦ��list
	private Rectangle2D current;// �������˷���
	private Ellipse2D ccurrent;// ��������Բ

	public double x;
	public double y;
	public int flag;

	public ServerComponent() {
		squares = new ArrayList<Rectangle2D>();
		circles = new ArrayList<Ellipse2D>();
		current = null;
		ccurrent = null;
	}
	
	public void addSquareOrCircle(){
		if (flag == 0) {
			current = new Rectangle2D.Double(x , y , SIDELENGTHOFSQUARE,
					SIDELENGTHOFSQUARE);
			squares.add(current);
			repaint();
		}
		
		if (flag == 1) {
			// �����е�Բ
			ccurrent = new Ellipse2D.Double(x , y , DIAMETEROFCIRCLE,
					DIAMETEROFCIRCLE);
			circles.add(ccurrent);
			repaint();
		}	
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	// һ�����Ƶ����
	public void paintComponent(Graphics g) {
		// ����
		Graphics2D g2 = (Graphics2D) g;
		
		for (Rectangle2D r : squares) {
			g2.draw(r);
		}

		for (Ellipse2D e : circles) {
			g2.draw(e);
		}
	}

	public void receive() {

		// ���տͻ��˷��������ݣ����ģ�
		DatagramSocket ds1 = null;
		try {
			ds1 = new DatagramSocket(9090);
			while (true) {
				byte[] b1 = new byte[1024];
				byte[] b2 = new byte[1024];
				byte[] b3 = new byte[1024];
				DatagramPacket packet1 = new DatagramPacket(b1, b1.length);
				DatagramPacket packet2 = new DatagramPacket(b2, b2.length);
				DatagramPacket packet3 = new DatagramPacket(b3, b3.length);
				ds1.receive(packet1);
				ds1.receive(packet2);
				ds1.receive(packet3);

				byte[] data1 = packet1.getData();
				byte[] data2 = packet2.getData();
				byte[] data3 = packet3.getData();

				String str1 = new String(data1, 0, packet1.getLength());
				String str2 = new String(data2, 0, packet2.getLength());
				String str3 = new String(data3, 0, packet3.getLength());

				x = Double.valueOf(str1);
				y = Double.valueOf(str2);
				flag = Integer.parseInt(str3);
				
				addSquareOrCircle();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ds1.close();
		}
	}
}
