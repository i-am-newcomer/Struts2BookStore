package com.zy.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

public class ImageAction {
	
	public String checkcode() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置响应编码格式和响应类型
		response.setCharacterEncoding("utf-8");
		response.setContentType("image/jpeg");
		//设置不缓存图片
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expire", "0");
		
		//设置图片长度和宽度
		int width = 80;
		int height = 25;
		//得到BufferedImage对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//得到画笔g
		Graphics g = image.getGraphics();
		//用画笔填充矩形
		g.setColor(new Color(200,200,200));
		g.fillRect(0, 0, width, height);
		//生成随机数1000-9999
		Random rd = new Random();
		int rdnum = rd.nextInt(8999)+1000;
		String rdstr = String.valueOf(rdnum);
		//将该随机数保存在会话属性中
		HttpSession session = request.getSession();
		session.setAttribute("checkcode", rdstr);
		//画笔画随机数
		g.setColor(Color.BLUE);
		g.setFont(new Font("", Font.PLAIN, 22));
		g.drawString(rdstr, 15, 24);
		//画100个随机点
		g.setColor(Color.GREEN);
		for(int i=0; i<100; i++) {
			int x = rd.nextInt(width);
			int y = rd.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		
		//释放资源
		g.dispose();
		
		//输出图片
		ImageIO.write(image, "jpeg", response.getOutputStream());
		
		return Action.NONE;
		

		
		
		
	}

}
