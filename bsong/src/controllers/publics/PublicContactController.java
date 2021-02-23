package controllers.publics;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDao;
import models.Contact;
import utils.CategoryUtil;

public class PublicContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicContactController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryUtil.getCat(request, response);
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/contact.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryUtil.getCat(request, response);
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String content = request.getParameter("message");
		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("website", website);
		request.setAttribute("content", content);
		if (name.equals("")) {
			request.setAttribute("err", "Tên không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/public/contact.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (email.equals("")) {
			request.setAttribute("err", "Email không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/public/contact.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (content.equals("")) {
			request.setAttribute("err", "Nội dung không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/public/contact.jsp");
			rd.forward(request, response);
			return;
		}
		
		request.removeAttribute("name");
		request.removeAttribute("email");
		request.removeAttribute("website");
		request.removeAttribute("content");
		ContactDao objContactDao = new ContactDao();
		if (website.equals("")) {
			Contact objContact = new Contact(name, email, content);
			if (objContactDao.addContact(objContact) > 0) {
				request.setAttribute("success", "Gửi liên hệ thành công!");
			}
		} else {
			Contact objContact = new Contact(0, name, email, website, content);
			if (objContactDao.addContact(objContact) > 0) {
				request.setAttribute("success", "Gửi liên hệ thành công!");
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/contact.jsp");
		rd.forward(request, response);
	}

}
