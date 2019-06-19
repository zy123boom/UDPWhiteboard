package whiteboard;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import javax.swing.*;


//�ͻ���
public class ClientComponent extends JComponent {
	String flag = "0";
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;

	private static final int SIDELENGTHOFSQUARE = 20;// �߳�
	private static final int DIAMETEROFCIRCLE = 40; //ֱ��
	
	private ArrayList<Rectangle2D> squares; // �淽���list
	private ArrayList<Ellipse2D> circles;// ��Բ��list
	private Rectangle2D current;// �������˷���
	private Ellipse2D ccurrent;// ��������Բ

	
	public ClientComponent() {
		squares = new ArrayList<Rectangle2D>();
		circles = new ArrayList<Ellipse2D>();
		current = null;
		ccurrent = null;
		addMouseListener(new MouseHandler());
	}

	//��������û������ж��
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	// һ�����Ƶ����
	public void paintComponent(Graphics g) {
		// ����
		Graphics2D g2 = (Graphics2D) g;
		// �����еķ���
		for (Rectangle2D r : squares) {
			g2.draw(r);
		}
		//�����е�Բ
		for (Ellipse2D e : circles) {
			g2.draw(e);
		}
	}

	
	/**
	 * �ҵ�һ�����������ķ���
	 * @param p ��
	 * @return �����������ĵ�һ����
	 */
	
	public Rectangle2D findSquare(Point2D p) {
		for (Rectangle2D r : squares) {
			if (r.contains(p))
				return r;
		}
		return null;
	}

	/**
	 * �ҵ�һ������������Բ
	 * @param p ��
	 * @return �����������ĵ�һ��Բ
	 */
	public Ellipse2D findCircle(Point2D p) {
		for (Ellipse2D e : circles) {
			if (e.contains(p))
				return e;
		}
		return null;
	}

	/**
	 * �ѵ�ǰ�ķ�����뵽����list��
	 * @param p ��������ĵ�
	 */
	public void addSquare(Point2D p) {
		double x = p.getX();
		double y = p.getY();

		//(���Ͻ�x�����Ͻ�y������)
		current = new Rectangle2D.Double(x - SIDELENGTHOFSQUARE / 2, y - SIDELENGTHOFSQUARE / 2, SIDELENGTHOFSQUARE,
				SIDELENGTHOFSQUARE);
		squares.add(current);
		repaint();
	}

	/**
	 * �ѵ�ǰ��Բ���뵽Բlist��
	 * @param p Բ��
	 */
	public void addCircle(Point2D p) {
		double x = p.getX();
		double y = p.getY();

		ccurrent = new Ellipse2D.Double(x - DIAMETEROFCIRCLE / 2, y - DIAMETEROFCIRCLE / 2, DIAMETEROFCIRCLE,
				DIAMETEROFCIRCLE);
		circles.add(ccurrent);
		repaint();
	}
	
	public double getXOfSquare() {
		return current.getX();
	}

	public double getYOfSquare() {
		return current.getY();
	}

	public double getXOfCircle() {
		return ccurrent.getX();
	}

	public double getYOfCircle() {
		return ccurrent.getY();
	}

	public class MouseHandler extends MouseAdapter {

		public void mousePressed(MouseEvent event) {
			int c = event.getButton(); // �õ����µ�����
			if (c == MouseEvent.BUTTON1) {
				// ���Ʒ���
				current = findSquare(event.getPoint());
				if (current == null) {
					addSquare(event.getPoint());
				}
				
				//����
				DatagramSocket ds1 = null;
				try {
					ds1 = new DatagramSocket();
					flag = "0";
					String strX = String.valueOf(getXOfSquare());
					byte[] b1 = strX.getBytes();
					String strY = String.valueOf(getYOfSquare());
					byte[] b2 = strY.getBytes();
					byte[] b3 = flag.getBytes();
					InetAddress address = InetAddress.getByName("192.168.50.100");
					
					DatagramPacket packet1 = new DatagramPacket(b1, b1.length, address, 9090);
					DatagramPacket packet2 = new DatagramPacket(b2, b2.length, address, 9090);
					DatagramPacket packet3 = new DatagramPacket(b3, b3.length, address, 9090);
					
					ds1.send(packet1);
					ds1.send(packet2);
					ds1.send(packet3);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ds1.close();
				}
			} else if (c == MouseEvent.BUTTON3) {
				// ����ԲȦ
				ccurrent = findCircle(event.getPoint());
				if (ccurrent == null) {
					addCircle(event.getPoint());
				}
				flag = "1";
				DatagramSocket ds2 = null;
			
				try {
					ds2 = new DatagramSocket();
					
					String strX = String.valueOf(getXOfCircle());
					byte[] b1 = strX.getBytes();
					String strY = String.valueOf(getYOfCircle());
					byte[] b2 = strY.getBytes();
					byte[] b3 = flag.getBytes();
					
					InetAddress address = InetAddress.getByName("192.168.50.100");
					
					DatagramPacket packet1 = new DatagramPacket(b1, b1.length, address, 9090);
					DatagramPacket packet2 = new DatagramPacket(b2, b2.length, address, 9090);
					DatagramPacket packet3 = new DatagramPacket(b3, b3.length, address, 9090);
					
					ds2.send(packet1);
					ds2.send(packet2);
					ds2.send(packet3);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ds2.close();
				}
			}
		}

	}
}
