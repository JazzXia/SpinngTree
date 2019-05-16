package com.tedu.ST;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Alarm extends Applet implements Runnable {
	/* ��Ա���� */
	int x = 0, y = 0, r = 100; // ��x,y��Ϊ(0,0)�㣬��ʾԭ��
	int h, m, s; // ʱ,��,��
	double rad = Math.PI / 180; // 1��

	public void init() { // ��ʼ������
		Calendar now = new GregorianCalendar();
		// GregorianCalendar����׼��������Calendar�������������ʻ����¶������еĳ��򡿵�����
		s = now.get(Calendar.SECOND) * 6; // ����ת���ɽǶȣ�1�룬���붯һ�Σ�ת��6��
		m = now.get(Calendar.MINUTE) * 6; // ����ת��Ϊ�Ƕȣ�1�֣����붯һ�Σ�ת��6��
		h = now.get(Calendar.HOUR) * 30 + now.get(Calendar.MINUTE) / 12 * 6; // �Ȱѷֻ�ΪСʱ���ٳ���6��
		// Calendar.HOUR ��ʾ��Χ��1-12������AM����PM�� Calendar.HOUR_OF_DAY ��ʾ��Χ��1-24������PM
		Thread t = new Thread(this);
		t.start();
	}

	// ��ͼ���� ע�⣺Applet�Ļ�ͼ���������Ͻ�Ϊ(0,0)ԭ�����꣬��������������ڵ���0���õ������Խ����>0��
	public void paint(Graphics g) {
		super.paint(g);
		// paint(g)�������ػ�ͼ��Ҫ����super.paint(g)����ʾ��ԭ��ͼ��Ļ����ϣ��ٻ�ͼ��
		// �������super.paint(g)���ػ�ʱ���Ὣԭ�еĻ�����գ��ٸ���paing(g)�������ơ�
		g.setColor(Color.BLACK);
		g.drawOval(x, y, r * 2, r * 2);// ���� drawOval(x,y,width,height)�Ծ���ǡ�ÿ�ס��Բ���������ϽǵĶ�������Ϊ(x,y)
		// ����
		int x1 = (int) (90 * Math.sin(rad * s));
		int y1 = (int) (90 * Math.cos(rad * s));
		g.drawLine(r, r, r + x1, r - y1); // drawLine(a,b,c,d) (a,b)Ϊ��ʼ���� (c,d)Ϊ�յ�����

		// ����
		x1 = (int) (80 * Math.sin(rad * m));
		y1 = (int) (80 * Math.cos(rad * m));
		g.drawLine(r, r, r + x1, r - y1);

		// ʱ��
		x1 = (int) (60 * Math.sin(rad * h));
		y1 = (int) (60 * Math.cos(rad * h));
		g.drawLine(r, r, r + x1, r - y1);
		// ������
		int d = 30;
		for (int i = 1; i <= 12; i++) {
			x1 = (int) ((r - 10) * Math.sin(rad * d));
			y1 = (int) ((r - 10) * Math.cos(rad * d));
			g.drawString(i + "", r + x1, r - y1);
			d += 30;
		}
		// ���̶�
		d = 0;
		for (int i = 1; i <= 60; i++) {
			x1 = (int) ((r - 2) * Math.sin(rad * d));
			y1 = (int) ((r - 2) * Math.cos(rad * d));
			g.drawString(".", r + x1, r - y1);
			d += 6;
		}
		// ��ʾʱ��
		Calendar now1 = new GregorianCalendar();
		int a, b, c;
		a = now1.get(Calendar.HOUR_OF_DAY);
		b = now1.get(Calendar.MINUTE);
		c = now1.get(Calendar.SECOND);
		g.drawString(a + ":" + b + ":" + c, 0, 10);
	}

	public void run() { // ʵ��Runnable
		while (true) {
			try {
				Thread.sleep(1000);// ���һ��
			} catch (Exception ex) {
			}
			s += 6; // ����ÿ����6��
			if (s >= 360) // �������
			{
				s = 0;
				m += 6;
				if (m == 72 || m == 144 || m == 288) // ������72�㣬ʱ����6�� �����12����ʱ����һ��
				{
					h += 6;
				}
				if (m >= 360) // �������
				{
					m = 0;
					h += 6;
				}
				if (h >= 360) // ʱ�����
				{
					h = 0;
				}
			}
			// ���»���
			this.repaint();
		}
	}

}
