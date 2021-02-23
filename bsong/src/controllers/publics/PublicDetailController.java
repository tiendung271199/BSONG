package controllers.publics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CommentDao;
import daos.SongDao;
import models.Comment;
import models.Song;
import utils.CategoryUtil;
import utils.DefineUtil;

public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicDetailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryUtil.getCat(request, response);
		SongDao objSongDao = new SongDao();
		CommentDao objCmtDao = new CommentDao();
		if (request.getParameter("id") != null) {
			if (request.getParameter("err") != null) {
				int err = Integer.parseInt(request.getParameter("err"));
				if (err == 1) {
					request.setAttribute("err", "Không có nội dung bình luận");
				}
			}
			int id = 0;
			try {
				id = Integer.parseInt(request.getParameter("id"));
				if (objSongDao.getSong(id) == null) {
					response.sendRedirect(request.getContextPath() + "/error/loi-4");
					return;
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/error/loi-1");
				return;
			}
			Song objSong = objSongDao.getSong(id);
			objSong.setCounter(objSong.getCounter() + 1);
			if (objSongDao.editCounter(objSong.getCounter(), id) > 0) {
				request.setAttribute("song", objSong);
			}
			// Phân trang cho bình luận
			int numberPage = DefineUtil.NUMBER_PER_PAGE;
			int first = 0, last = 0, pages = 1, totalPages = 1;
			int total = objCmtDao.getCount(id);
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
			ArrayList<Comment> listComment = objCmtDao.getCmtShow(id, first, last);
			request.setAttribute("listComment", listComment);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("total", total);
			request.setAttribute("pages", pages);

			ArrayList<Song> listSong = objSongDao.getSongByCat(objSong.getCat().getId());
			int rand = 0;
			if (listSong.size() > 0) {
				for (int i = 0; i < listSong.size(); i++) {
					if (listSong.get(i).getId() == id) {
						listSong.remove(i);
						break;
					}
				}
			}
			while (true) {
				if (listSong.size() < 1) {
					break;
				}
				rand = new Random().nextInt(listSong.size());
				if (rand == id) {
					continue;
				}
				Song song = listSong.get(rand);
				request.setAttribute("songlienquan", song);
				break;
			}

		}

		RequestDispatcher rd = request.getRequestDispatcher("/views/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
