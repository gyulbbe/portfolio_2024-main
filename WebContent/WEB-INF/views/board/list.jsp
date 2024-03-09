<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<section>
	<div class="container">
		<h4>자유게시판</h4>
		<div class="table-responsive">
			<table class="table table-sm">
				<colgroup>
					<col width="10%" />
					<col width="35%" />
					<col width="10%" />
					<col width="8%" />
					<col width="8%" />
					<col width="15%" />
				</colgroup>

				<thead>
					<tr>
						<th class="fw-30" align="center">&emsp;&emsp;&emsp;#</th>
						<th align="center">제목</th>
						<th align="center">글쓴이</th>
						<th align="center">조회수</th>
						<th align="center">첨부파일</th>
						<th align="center">작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="board" items="${boardList}">
						<tr>
							<td align="center">${board.board_seq}</td>
							<td><span class="bold"> <a
									href="javascript:movePage('/board/read.do?boardSeq=${board.board_seq}&currentPage=currentPage')">
										${board.title} </a>
							</span></td>
							<td>${board.member_nick}</td>
							<td>${board.hits}</td>
							<td>${board.has_file}</td>
							<td>${board.create_date}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row text-center">
			<div class="col-md-12">
				<ul class="pagination pagination-simple pagination-sm">
					<!-- 1 페이지 -->
					<li class="page-item"><a class="page-link"
						href="javascript:movePage('/board/list.do?page=1')">&laquo;</a></li>
					<!-- 이전 페이지 -->
					<c:if test="${beginPage != 1}">
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=${beginPage-1}')">&lt;</a></li>
					</c:if>
					<!-- 페이징 -->
					<c:forEach begin="${beginPage}" end="${endPage}" var="page">
						<li class="${page == currentPage ? 'page-item active' : ''}"><a
							class="page-link"
							href="javascript:movePage('/board/list.do?page=${page}')">${page}</a></li>
					</c:forEach>
					<!-- 다음 페이지 -->
					<c:if test="${endPage != totalPage}">
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=${endPage+1}')">&gt;</a></li>
					</c:if>
					<!-- 맨 마지막 페이지 -->
					<li class="page-item"><a class="page-link"
						href="javascript:movePage('/board/list.do?page=${totalPage}')">&raquo;</a></li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 text-right">
				<a href="javascript:movePage('/board/goToWrite.do')">
					<button type="button" class="btn btn-primary">
						<i class="fa fa-pencil"></i> 글쓰기
					</button>
				</a>
			</div>
		</div>
	</div>
	</section>
	<!-- / -->
</body>
</html>