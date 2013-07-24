package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HanZi4Validate {
	private static Random random = new Random(new java.util.Date().getTime() + 1);// 生成随机对象，并设置随机种子

	/**
	 * 
	 * 将0-2000汉字区位码转换为机内码 参数为区位码字符串 返回Object（汉字串）
	 */
	public static String gb2JiNei(String code) {
		// 存放转换后的机内码
		String coded = "";
		// 转换过程
		for (int i = 0; i < code.length(); i += 4) {
			int r1;// 存放区位高位字节
			int r2;// 存放区位低位字节
			try {
				r1 = Integer.parseInt(code.substring(i, i + 2));
				r2 = Integer.parseInt(code.substring(i + 2, i + 4));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("区位码存在错误！");
				return null;
			}
			r1 += 160;// 区位码转机内码（需加上A0H，即160）
			r2 += 160;

			// coded+=temp[0]+temp[1];
			coded += Integer.toHexString(r1) + Integer.toHexString(r2);
		}
		return coded.toUpperCase();
	}

	/**
	 * 随机生成机内码（第一和第二级的汉字）：从第B0区到D7区为简汉字，D7及以后汉字过于繁杂，不允考虑
	 * 
	 * @return String 机内码字符串
	 * 
	 */
	public static String createRandomJiNei() {
		String model[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		String temp = "";
		byte byte2[] = new byte[2];
		int r1, r2, r3, r4;// 机内码四位

		r1 = random.nextInt(3) + 11;
		// 高位字节首位为D，则次位要小于7
		if (r1 == 13) {
			r2 = random.nextInt(7);
		} else {
			r2 = random.nextInt(16);
		}
		r3 = random.nextInt(6) + 10;
		// 区首位首及区尾位尾汉字为空
		if (r3 == 10) {
			r4 = random.nextInt(15) + 1;
		} else if (r3 == 15) {
			r4 = random.nextInt(15);
		} else {
			r4 = random.nextInt(16);
		}

		// 生成机内码高位和低位字节码
		temp += model[r1] + model[r2];
		byte2[0] = (byte) Integer.parseInt(temp, 16);
		temp = "";
		temp += model[r3] + model[r4];
		byte2[1] = (byte) Integer.parseInt(temp, 16);

		// System.out.println(model[r1]+model[r2]+model[r3]+model[r4]);
		return new String(byte2);
	}

	/***
	 * 获得动态验证图片文字（由数字、英文字符、中文组成） 返回String 字符串
	 */
	public static String createRandomValidateString() {
		String validate = "";
		// 动态生成四个验证码
		for (int i = 0; i < 4; i++) {

			int choose = random.nextInt(3);
			switch (choose) {
			case 0:// 生成数字
				validate += (char) (random.nextInt(10) + 48);
				break;
			case 1:// 生成字母
				validate += (char) (random.nextInt(26) + 65);
				break;
			case 2:// 生成中文
				validate += createRandomJiNei();
			}
		}
		return validate;
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

	/***
	 * 生成验证图片 嵌入验证文字、斜线条、乱点
	 */
	public static String getValidateImage() {
		// 获得动态验证文字
//		String validateString = createRandomValidateString();
		String validateString = random.nextInt(9999)+""+random.nextInt(9999)+random.nextInt(9999)+random.nextInt(9999);
		System.out.println(validateString);

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

//		StringBufferInputStream
//		new BASE64Encoder().encode(arg0)
		//return com.sun.org.apache.xml.internal.security.utils.Base64.encode(bufferedImage.get);
		// 输出图片（保存）
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			
			// out为IO写文件对象
			ImageIO.write(bufferedImage, "jpg", outputStream);
			return new BASE64Encoder().encode(outputStream.toByteArray());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO操作异常!");
		}
		return validateString;
	}

	public static void main(String[] args) throws IOException {

//		// 测试是否可将区位码转为机内码
//		System.out.println(HanZi4Validate.gb2JiNei("2563"));
//
//		// 测试是否生成验证码
//		System.out.println(HanZi4Validate.createRandomValidateString());

		// 测试是否生成图片，并将图片存于相应位置，用于验证
		System.out.println("11"+HanZi4Validate.getValidateImage());
		IOUtils.write(new BASE64Decoder().decodeBuffer(HanZi4Validate.getValidateImage()), new FileOutputStream("/p/pp.jpg"));
	}
}
