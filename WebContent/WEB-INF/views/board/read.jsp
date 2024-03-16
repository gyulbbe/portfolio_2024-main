<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="<c:url value='/resources/js/scripts.js'/>"></script>

<script type="text/javascript">
	$(document).ready(function() {
				
		$('#btnDelete').on('click', function() {
			var formData = new FormData(document.readForm);
			if (confirm("삭제하시겠습니까?")) {

				$.ajax({
					url : "<c:url value='/board/delete.do'/>", // 요청을 보낼 서버의 URL

					type : 'POST', // HTTP 요청 방식

					data : formData, // 서버로 보낼 데이터

					processData : false,
					contentType : false,

					success : function(map) {
						if (map.success) {
							alert(map.message);
							window.location.reload();
						} else {
							alert(map.message);
						}
					},
					error : function(xhr, status, error) {
						// 실패 시
						alert('삭제 중 실패 오류');
						// 새로고침
						window.location.reload();
					}
				});
			}
		});
	});
</script>

</head>
<body>
	<section>
	<div class="container">
		<div class="row">
			<!-- LEFT -->
			<div class="col-md-12 order-md-1">
				<form name="readForm" class="validate" method="post"
					enctype="multipart/form-data" data-success="Sent! Thank you!"
					data-toastr-position="top-right">
					<input type="hidden" name="boardSeq" value="${read.board_seq}" />
					<input type="hidden" name="typeSeq" value="${read.type_seq}" /> <input
						type="hidden" name="memberId" value="${read.member_id}" /> <input
						type="hidden" name="memberNick" value="${read.member_nick}" />
				</form>
				<!-- post -->
				<div class="clearfix mb-80">
					<div class="border-bottom-1 border-top-1 p-12">
						<span class="float-right fs-10 mt-10 text-muted">${read.create_dtm}</span>
						<center>
							<strong>${read.title}</strong>
						</center>
					</div>
					<div class="block-review-content">
						<div class="block-review-body">
							<div class="block-review-avatar text-center">
								<div class="push-bit">
									<img src="resources/images/_smarty/avatar2.jpg" width="100"
										alt="avatar">
									<!--  <i class="fa fa-user" style="font-size:30px"></i>-->
								</div>
								<small class="block">${read.member_nick}</small>
								<hr />
							</div>
							<p>${read.content}</p>
							<!-- 컬렉션 형태에서는 (list) items  -->

							<!-- 첨부파일 없으면  -->
							<c:if test="${empty attFiles}">
								<tr>
									<th class="tright">#첨부파일 다운로드 횟수</th>
									<td colspan="6" class="tright"></td>
									<!-- 걍빈칸  -->
								</tr>
							</c:if>

							<!-- 파일있으면  -->
							<c:forEach items="${attFiles}" var="file" varStatus="f">
								</tr>
								<tr>
									<th class="tright">첨부파일 ${ f.count }</th>
									<td colspan="6" class="tleft"><c:choose>
											<c:when test="${file.linked == 0}">
												${file.file_name} (서버에 파일을 찾을 수 없습니다.)
											</c:when>

											<c:otherwise>
												<a
													href="<c:url value='/board/downloadFile.do?fileIdx=${file.file_idx}'/>">
													${file.file_name} ( ${file.file_size } bytes) </a>
												<br />
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</div>
						<div class="row">
							<div class="col-md-12 text-right">
								<c:if test="${ true }">
									<a
										href="javascript:movePage('/board/goToUpdate.do?boardSeq=${read.board_seq}&memberId=${read.member_id}')">
										<button type="button" class="btn btn-primary" id="btnUpdate">
											<i class="fa fa-pencil"></i> 수정
										</button>
									</a>
									<button type="button" class="btn btn-primary" id="btnDelete">삭제</button>
								</c:if>

								<c:choose>
									<c:when test="${empty currentPage}">
										<a href="javascript:movePage('/board/list.do')">
											<button type="button" class="btn btn-primary">목록</button>
										</a>
									</c:when>
									<c:otherwise>
										<a
											href="javascript:movePage('/board/list.do?page=currentPage')">
											<button type="button" class="btn btn-primary">목록</button>
										</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
</body>
</html>