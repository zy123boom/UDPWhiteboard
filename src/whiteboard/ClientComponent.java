package whiteboard;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import javax.swing.*;


//客户端
public class ClientComponent extends JComponent {
	String flag = "0";
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;

	private static final int SIDELENGTHOFSQUARE = 20;// 边长
	private static final int DIAMETEROFCIRCLE = 40; //直径
	
	private ArrayList<Rectangle2D> squares; // 存方块的list
	private ArrayList<Ellipse2D> circles;// 存圆的list
	private Rectangle2D current;// 鼠标包含了方块
	private Ellipse2D ccurrent;// 鼠标包含了圆

	
	public ClientComponent() {
		squares = new ArrayList<Rectangle2D>();
		circles = new ArrayList<Ellipse2D>();
		current = null;
		ccurrent = null;
		addMouseListener(new MouseHandler());
	}

	//组件告诉用户界面有多大
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	// 一个绘制的组件
	public void paintComponent(Graphics g) {
		// 绘制
		Graphics2D g2 = (Graphics2D) g;
		// 画所有的方块
		for (Rectangle2D r : squares) {
			g2.draw(r);
		}
		//画所有的圆
		for (Ellipse2D e : circles) {
			g2.draw(e);
		}
	}

	
	/**
	 * 找第一个包含这个点的方块
	 * @param p 点
	 * @return 包含了这个点的第一个块
	 */
	
	public Rectangle2D findSquare(Point2D p) {
		for (Rectangle2D r : squares) {
			if (r.contains(p))
				return r;
		}
		return null;
	}

	/**
	 * 找第一个包含这个点的圆
	 * @param p 点
	 * @return 包含了这个点的第一个圆
	 */
	public Ellipse2D findCircle(Point2D p) {
		for (Ellipse2D e : circles) {
			if (e.contains(p))
				return e;
		}
		return null;
	}

	/**
	 * 把当前的方块加入到方块list中
	 * @param p 方块的中心点
	 */
	public void addSquare(Point2D p) {
		double x = p.getX();
		double y = p.getY();

		//(左上角x，左上角y，宽，高)
		current = new Rectangle2D.Double(x - SIDELENGTHOFSQUARE / 2, y - SIDELENGTHOFSQUARE / 2, SIDELENGTHOFSQUARE,
				SIDELENGTHOFSQUARE);
		squares.add(current);
		repaint();
	}

	/**
	 * 把当前的圆加入到圆list中
	 * @param p 圆心
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
			int c = event.getButton(); // 得到按下的鼠标键
			if (c == MouseEvent.BUTTON1) {
				// 绘制方块
				current = findSquare(event.getPoint());
				if (current == null) {
					addSquare(event.getPoint());
				}
				
				//传输
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
				// 绘制圆圈
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
