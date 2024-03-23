<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- chatgpt를 통해 가져온 코드 -->
<script type="text/javascript">
	function search() {
		// 폼에서 검색 유형과 키워드를 가져옵니다.
		var searchType = document.getElementById('searchType').value;
		var keyword = document.querySelector('input[name="keyword"]').value;

		// movePage 함수를 사용하여 서버에 검색 조건과 함께 AJAX 요청
		movePage('/board/list.do', {
			searchType : searchType,
			keyword : keyword
		});

		// 자동 제출 방지
		return false;
	}
</script>
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
						<th class="fw-30" align="center">&emsp;&emsp;&emsp;글 번호</th>
						<th align="center">제목</th>
						<th align="center">닉네임</th>
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
									href="javascript:movePage('/board/read.do?boardSeq=${board.board_seq}&currentPage=${currentPage}')">
										${board.title} </a>
							</span></td>
							<td>${board.member_nick}</td>
							<td>${board.hits}</td>
							<td>${board.has_file}</td>
							<td>${board.create_dtm}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row text-center">
			<div class="col-md-12">
				<ul class="pagination pagination-simple pagination-sm">
				
					<!-- 1 페이지 -->
					<c:choose>
					<c:when test="${not empty searchType and not empty keyword}">
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=1&searchType=${searchType}&keyword=${keyword}')">&laquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=1')">&laquo;</a></li>
					</c:otherwise>
					</c:choose>
						
					<!-- 이전 페이지 -->
					<c:choose>
					<c:when test="${beginPage != 1 and not empty searchType and not empty keyword}">
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=${beginPage-1}&searchType=${searchType}&keyword=${keyword}')">&lt;</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=${beginPage-1}')">&lt;</a></li>
					</c:otherwise>
					</c:choose>
					
					<!-- 페이징 -->
					<c:choose>
					<c:when test="${not empty searchType and not empty keyword}">
					<c:forEach begin="${beginPage}" end="${endPage}" var="page">
						<li class="${page == currentPage ? 'page-item active' : ''}"><a
							class="page-link"
							href="javascript:movePage('/board/list.do?page=${page}&searchType=${searchType}&keyword=${keyword}')">${page}</a></li>
					</c:forEach>
					</c:when>
					
					<c:otherwise>
					<c:forEach begin="${beginPage}" end="${endPage}" var="page">
						<li class="${page == currentPage ? 'page-item active' : ''}"><a
							class="page-link"
							href="javascript:movePage('/board/list.do?page=${page}')">${page}</a></li>
					</c:forEach>
					</c:otherwise>
					</c:choose>
					
					<!-- 다음 페이지 -->
					<c:choose>
					<c:when test="${endPage != totalPage and not empty searchType and not empty keyword}">
						<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=${endPage+1}&searchType=${searchType}&keyword=${keyword}')">&gt;</a></li>
					</c:when>
					
					<c:otherwise>
					<li class="page-item"><a class="page-link"
							href="javascript:movePage('/board/list.do?page=${endPage+1}')">&gt;</a></li>
					</c:otherwise>
					</c:choose>
					
					<!-- 맨 마지막 페이지 -->
					<c:choose>
					<c:when test="${endPage != totalPage and not empty searchType and not empty keyword}">
					<li class="page-item"><a class="page-link"
						href="javascript:movePage('/board/list.do?page=${totalPage}&searchType=${searchType}&keyword=${keyword}')">&raquo;</a></li>
					</c:when>
					
					<c:otherwise>
					<li class="page-item"><a class="page-link"
						href="javascript:movePage('/board/list.do?page=${totalPage}')">&raquo;</a></li>
					</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 d-flex justify-content-end">
				<form action="<c:url value='/board/list.do'/>" method="get" onsubmit="return search();">
					<div class="input-group input-group-sm mb-3">
						<!-- 드롭다운 메뉴 -->
						<select class="custom-select custom-select-sm" id="searchType" name="searchType">
							<option value="memberNick">글쓴이</option>
							<option value="title">제목</option>
						</select>
						<!-- 검색창 -->
						<input type="text" class="form-control form-control-sm"
							placeholder="검색어를 입력하세요" name="keyword" style="flex: auto;" required>
						<!-- 검색 버튼 -->
						<div class="input-group-append">
							<button class="btn btn-primary" type="submit" onclick="search()">검색</button>
						</div>
					</div>
				</form>
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