package com.tedu.ST;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Alarm extends Applet implements Runnable {
	/* 成员变量 */
	int x = 0, y = 0, r = 100; // （x,y）为(0,0)点，表示原点
	int h, m, s; // 时,分,秒
	double rad = Math.PI / 180; // 1°

	public void init() { // 初始化函数
		Calendar now = new GregorianCalendar();
		// GregorianCalendar（标准阳历）是Calendar（日历）【国际环境下都能运行的程序】的子类
		s = now.get(Calendar.SECOND) * 6; // 秒针转换成角度：1秒，秒针动一次，转动6°
		m = now.get(Calendar.MINUTE) * 6; // 分针转换为角度：1分，分针动一次，转动6°
		h = now.get(Calendar.HOUR) * 30 + now.get(Calendar.MINUTE) / 12 * 6; // 先把分化为小时，再乘以6°
		// Calendar.HOUR 显示范围：1-12（无论AM还是PM） Calendar.HOUR_OF_DAY 显示范围：1-24（包括PM
		Thread t = new Thread(this);
		t.start();
	}

	// 画图函数 注意：Applet的画图界面以左上角为(0,0)原点坐标，即所有坐标均大于等于0，该点的坐标越大（且>0）
	public void paint(Graphics g) {
		super.paint(g);
		// paint(g)函数会重绘图像，要加上super.paint(g)，表示在原来图像的基础上，再画图。
		// 如果不加super.paint(g)，重绘时，会将原有的绘制清空，再根据paing(g)函数绘制。
		g.setColor(Color.BLACK);
		g.drawOval(x, y, r * 2, r * 2);// 画表 drawOval(x,y,width,height)以矩形恰好框住椭圆，矩形左上角的顶点坐标为(x,y)
		// 秒针
		int x1 = (int) (90 * Math.sin(rad * s));
		int y1 = (int) (90 * Math.cos(rad * s));
		g.drawLine(r, r, r + x1, r - y1); // drawLine(a,b,c,d) (a,b)为起始坐标 (c,d)为终点坐标

		// 分针
		x1 = (int) (80 * Math.sin(rad * m));
		y1 = (int) (80 * Math.cos(rad * m));
		g.drawLine(r, r, r + x1, r - y1);

		// 时针
		x1 = (int) (60 * Math.sin(rad * h));
		y1 = (int) (60 * Math.cos(rad * h));
		g.drawLine(r, r, r + x1, r - y1);
		// 画数字
		int d = 30;
		for (int i = 1; i <= 12; i++) {
			x1 = (int) ((r - 10) * Math.sin(rad * d));
			y1 = (int) ((r - 10) * Math.cos(rad * d));
			g.drawString(i + "", r + x1, r - y1);
			d += 30;
		}
		// 画刻度
		d = 0;
		for (int i = 1; i <= 60; i++) {
			x1 = (int) ((r - 2) * Math.sin(rad * d));
			y1 = (int) ((r - 2) * Math.cos(rad * d));
			g.drawString(".", r + x1, r - y1);
			d += 6;
		}
		// 显示时间
		Calendar now1 = new GregorianCalendar();
		int a, b, c;
		a = now1.get(Calendar.HOUR_OF_DAY);
		b = now1.get(Calendar.MINUTE);
		c = now1.get(Calendar.SECOND);
		g.drawString(a + ":" + b + ":" + c, 0, 10);
	}

	public void run() { // 实现Runnable
		while (true) {
			try {
				Thread.sleep(1000);// 间隔一秒
			} catch (Exception ex) {
			}
			s += 6; // 秒针每次走6°
			if (s >= 360) // 秒针归零
			{
				s = 0;
				m += 6;
				if (m == 72 || m == 144 || m == 288) // 分针走72°，时针走6° 分针的12倍，时针走一次
				{
					h += 6;
				}
				if (m >= 360) // 分针归零
				{
					m = 0;
					h += 6;
				}
				if (h >= 360) // 时针归零
				{
					h = 0;
				}
			}
			// 重新绘制
			this.repaint();
		}
	}

}
