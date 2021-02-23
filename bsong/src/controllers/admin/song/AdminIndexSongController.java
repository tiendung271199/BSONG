package controllers.admin.song;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.SongDao;
import models.Song;
import utils.AuthUtil;
import utils.CategoryUtil;
import utils.DefineUtil;

public class AdminIndexSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminIndexSongController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if (!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		if (request.getParameter("err") != null) {
			int err = Integer.parseInt(request.getParameter("err"));
			if (err == 1) {
				request.setAttribute("err", "Xoá thất bại!");
			} else if (err == 2) {
				request.setAttribute("err", "ID không tồn tại!");
			} else {
				request.setAttribute("err", "Không có quyền thực hiện chức năng này!");
			}
		}
		if (request.getParameter("msg") != null) {
			int msg = Integer.parseInt(request.getParameter("msg"));
			if (msg == 1) {
				request.setAttribute("success", "Thêm thành công!");
			} else if (msg == 2) {
				request.setAttribute("success", "Sửa thành công!");
			} else {
				request.setAttribute("success", "Xoá thành công!");
			}
		}
		CategoryUtil.getCat(request, response);
		SongDao objSongDao = new SongDao();
		int numberPage = DefineUtil.NUMBER_PER_PAGE;
		int first = 0, last = 0, pages = 1, totalPages = 1;
		int total = objSongDao.getCount();
		if (total % numberPage == 0) {
			totalPages = total / numberPage;
		} else {
			totalPages = total / numberPage + 1;
		}
		if (request.getParameter("pages") != null) {
			try {
				pages = Integer.parseInt(request.getParameter("pages"));
				if (pages < 1 || pages > totalPages) {
					response.sendRedirect(request.getContextPath() + "/error/admin/loi-2");
					return;
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/error/admin/loi-1");
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
		ArrayList<Song> listSong = objSongDao.getSong(first, last);
		// Tìm kiếm
		if (request.getParameter("scat") != null || request.getParameter("searchContent") != null) {
			int catId = Integer.parseInt(request.getParameter("scat"));
			String searchContent = request.getParameter("searchContent");
			request.setAttribute("searchContent", searchContent);
			request.setAttribute("scat", catId);
			listSong = objSongDao.getSearchSong(searchContent, catId);
		}
		request.setAttribute("listsong", listSong);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("total", total);
		request.setAttribute("pages", pages);
		request.setAttribute("first", first);
		request.setAttribute("last", last);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
