﻿<%@page import="utils.DefineUtil"%>
<%@page import="models.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Advanced Tables -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <div class="row">
                            	<%
                            		User objUser = null;
                            		if (session.getAttribute("login") != null) {
                            			objUser = (User) session.getAttribute("login");
                            		}
                            		if (objUser.getRole() != 0) {
                            	%>
                                <div class="col-sm-6">
                                    <a href="<%=request.getContextPath() %>/admin/cat/add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <%
                            		} else {
                                %>
                                <div class="col-sm-6"></div>
                                <%
                            		}
                                %>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath() %>/admin/cat/index">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="searchContent" class="form-control input-sm" value="${searchContent}" placeholder="Nhập tên danh mục" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>
							<%
                              	if (request.getAttribute("success") != null) {
                            %>
                            <div class="alert alert-success" role="alert">
                            	<span style="color: blue; font-style: italic">${success}</span>
                            </div>
                            <%
                            	}
								if (request.getAttribute("err") != null) {
                            %>
                            <div class="alert alert-danger" role="alert">
                              	<span style="color: red; font-style: italic">${err}</span>
                            </div>
                            <%
								}
								int check = 0;
								int numberPage = DefineUtil.NUMBER_PER_PAGE;
								int total = 0, pages = 1, totalPages = 1, first = 0, last = 0;
								if (request.getAttribute("pages") != null) {
							        pages = (int) request.getAttribute("pages");
							    }
								if (request.getAttribute("total") != null) {
							        total = (int) request.getAttribute("total");
							    }
								if (request.getAttribute("totalPages") != null) {
									totalPages = (int) request.getAttribute("totalPages");
							    }
								if (request.getAttribute("first") != null) {
									first = (int) request.getAttribute("first");
							    }
								if (request.getAttribute("last") != null) {
									last = (int) request.getAttribute("last");
							    }
								if (request.getAttribute("listcat") != null) {
                        			ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("listcat");
                        			if (listCat.size() > 0) {
                            %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên danh mục</th>
                                        <%
                                       		if (objUser.getRole() != 0) {
                                        %>
                                        <th width="160px">Chức năng</th>
                                        <%
                                       		}
                                        %>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                		for (Category objCat : listCat) {
                                	%>
                                    <tr>
                                        <td><%=objCat.getId() %></td>
                                        <td class="center"><%=objCat.getName() %></td>
                                        <%
                                        	if (objUser.getRole() != 0) {
                                        %>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin/cat/edit?id=<%=objCat.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin/cat/delete?id=<%=objCat.getId() %>" onclick="return confirm('Bạn có chắc muốn xoá <%=objCat.getName() %> không?')" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                        <%
                                        	}
                                        %>
                                    </tr>
                                    <%
                                		}
                                    %>
                                </tbody>
                            </table>
                            <%
                        			} else {
                        				check = 1;
                        	%>
                        		<div class="alert alert-success" role="alert">
      								<span style="color: blue; font-style: italic">Không có danh mục nào!</span>
      							</div>	
                        	<%
                        			}
								}
								if (check == 0) {
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                	<%
                                		if (total - first > numberPage) {
                                	%>
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=first + 1 %> đến <%=first + last %> của <%=total %> danh mục</div>
                                	<%
                                		} else {
                                	%>
                                	<div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=first + 1 %> đến <%=total %> của <%=total %> danh mục</div>
                                	<%
                                		}
                                	%>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<%
												// Button previous
												if (pages > 1) {
													int back = pages - 1;
													if (back == 1) {
		   									%>
                                            <li class="paginate_button previous" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath() %>/admin/cat/index">Trang trước</a></li>
                                            <%
													} else {
                                            %>
                                            <li class="paginate_button previous" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath() %>/admin/cat/index?pages=<%=back %>">Trang trước</a></li>
                                           	<%
													}
												}
	                                        	// Button Number page
	                            				int loop = 0, num = 0;
	                                            if ((total / 4) % 2 == 0) {
	                                                num = total / 4;
	                                            } else {
	                                                num = (total + 1) / 4;
	                                            }
	                                            //Nếu total lẻ thêm 1
	                                            if (total % 2 != 0) {
	                                                loop = (total / 4) + 1;
	                                            } else {
	                                                //Nếu total chẵn nhỏ hơn fullpage và không fullPage thì thêm 1
	                                                if (total < (num * 4) + 4 && total != num * 4) {
	                                                    loop = (total / 4) + 1;
	                                                } else {
	                                                    //Nếu bằng fullPage thì không thêm
	                                                    loop = (total / 4);
	                                                }
	                                            }
	                                            for (int j = 1; j <= loop; j++) {
	                                        		if (pages == j) {
	                                        			if (j == 1) {
                                           	%>
                                            <li class="paginate_button active" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/cat/index"><%=j %></a></li>
                                            <%
	                                        			} else {
                                            %>
                                            <li class="paginate_button active" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/cat/index?pages=<%=j %>"><%=j %></a></li>
                                            <%
	                                        			}
	                                        		} else {
	                                        			if (j == 1) {
                                            %>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/cat/index"><%=j %></a></li>
											<%
	                                        			} else {
											%>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/cat/index?pages=<%=j %>"><%=j %></a></li>
											<%
	                                        			}
	                                        		}
	                                            }
		                                        //Button Next
		                                        int next = 0;
		                                           //Nếu total lẻ
		                                           if (total % 2 != 0) {
		                                               if (pages != (total / 4) + 1) {
		                                                   next = pages + 1;
											%>
											<li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath() %>/admin/cat/index?pages=<%=next %>">Trang tiếp</a></li>
								             <%
		                                               }
		                                           } else {
									                   //Nếu total chẵn nhỏ hơn fullpage và không fullPage thì thêm 1
									                   if (total < (num * 4) + 4 && total != num * 4) {
									                       if (pages != (total / 4) + 1) {
									                           next = pages + 1;
								             %>
								             <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath() %>/admin/cat/index?pages=<%=next %>">Trang tiếp</a></li>
								             <%
									                       }
									                   } else {
									                       //Nếu fullPage đến trang cuối dừng chưa tới trang cuối thì được next
									                       if (pages != (total / 4)) {
									                           next = pages + 1;
								             %>
								             <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath() %>/admin/cat/index?pages=<%=next %>">Trang tiếp</a></li>
								             <%
									                       }
									                   }
		                                           }
										     %>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <%
								}
                            %>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>