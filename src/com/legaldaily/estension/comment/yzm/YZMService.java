package com.legaldaily.estension.comment.yzm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.terracotta.ehcachedx.org.mortbay.jetty.security.Credential.MD5;

import sun.misc.BASE64Encoder;

public class YZMService {
	private static Random random = new Random(new java.util.Date().getTime() + 1);// 生成随机对象，并设置随机种子
	private static Map<String, Yzm> yzmMap = new HashMap<String, Yzm>();

	public Yzm getYzm() {
		// 获得动态验证文字
		// String validateString = createRandomValidateString();
		String validateString = random.nextInt(9999) + "" + random.nextInt(9999) + random.nextInt(9999) + random.nextInt(9999);
		validateString = validateString.substring(0, 4);
		BufferedImage bufferedImage = getBufferedImage(validateString);

		// 输出图片（保存）
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			// out为IO写文件对象
			ImageIO.write(bufferedImage, "jpg", outputStream);
			String sid = MD5.digest(System.currentTimeMillis()+"").substring(4);
			String b64 = new BASE64Encoder().encode(outputStream.toByteArray());
			Yzm yzm = new Yzm(sid, b64);
			yzmMap.put(validateString, yzm);
			return yzm;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO操作异常!");
		}
		return null;
	}

	/***
	 * 动态生成色rgb色图 返回Color对象
	 */
	public static Color getRandomColor() {
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r, g, b);
	}

	public BufferedImage getBufferedImage(String validateString) {

		// 设置图片的高和宽
		int width = 130;
		int height = 30;

		// 在内存在创建图片,并设置大小及色素方案
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获得图片对像
		Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

		// 设置图片填充色
		g.setColor(Color.white);
		// g.setColor(getRandomColor());
		g.fillRect(0, 0, width, height);

		// 设置字体
		g.setFont(new Font("宋体", Font.BOLD, 20));
		g.setColor(getRandomColor());

		// 在图片中随机选择位置放置生成的验证文字
		for (int i = 0; i < 4; ++i) {
			int w;
			if ((i + 1) % 3 == random.nextInt(3))
				w = 19 - random.nextInt(7);
			else
				w = random.nextInt(7) + 19;
			// 设置验证码字体颜色
			g.setColor(getRandomColor());
			g.drawString(validateString.substring(i, i + 1), i * 25 + 20, w);// /等定
		}

		// 产生干扰线，并用不同颜色表示
		for (int i = 0; i < 7; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			// 设置画线的颜色
			g.setColor(getRandomColor());
			g.drawLine(x, y, x1, y1);
		}

		// 产生干扰点，不同颜色显示
		for (int i = 0; i < 100; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			// 设置干扰点的颜色
			g.setColor(getRandomColor());
			g.drawOval(x, y, 0, 0);
		}

		// 生成图片
		g.dispose();
		return bufferedImage;
	}
	public static void main(String[] args) {
		System.out.println(MD5.digest("134").substring(4));
	}
}
