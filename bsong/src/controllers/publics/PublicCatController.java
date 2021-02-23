package controllers.publics;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDao;
import daos.SongDao;
import models.Category;
import models.Song;
import utils.CategoryUtil;
import utils.DefineUtil;

public class PublicCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicCatController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryUtil.getCat(request, response);
		SongDao objSongDao = new SongDao();
		CategoryDao objCatDao = new CategoryDao();
		int numberPage = DefineUtil.NUMBER_PER_PAGE;
		int first = 0, last = 0, pages = 1, catId = 0, totalPages = 1;
		try {
			catId = Integer.parseInt(request.getParameter("catid"));
			Category objCat = new CategoryDao().getCat(catId);
			request.setAttribute("category", objCat);
			if (objCatDao.getCount(catId) == 0) {
				response.sendRedirect(request.getContextPath() + "/error/loi-2");
				return;
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/error/loi-1");
			return;
		}
		int total = objSongDao.getCount(catId);
		if (total % numberPage == 0) {
			totalPages = total / numberPage;
		} else {
			totalPages = total / numberPage + 1;
		}
		if (request.getParameter("pages") != null) {
			try {
				pages = Integer.parseInt(request.getParameter("pages"));
				if (pages < 1 || pages > totalPages) {
					response.sendRedirect(request.getContextPath() + "/error/loi-3");
					return;
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/error/loi-1");
				return;
			}
		}
		if (total <= numberPage) {
			first = 0;
			last = total;
		} else {
			first = (pages - 1) * numberPage;
			last = numberPage;
		}
		ArrayList<Song> listSong = objSongDao.getSong(first, last, catId);
		request.setAttribute("listsong", listSong);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("total", total);
		request.setAttribute("pages", pages);
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/cat.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
