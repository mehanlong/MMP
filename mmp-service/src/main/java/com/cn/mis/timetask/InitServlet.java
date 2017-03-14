package com.cn.mis.timetask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
  
    public void init() throws ServletException {
        new Thread(new TokenThread()).start();
//        new Thread(new XCTicketThread()).start();
    }  
}
