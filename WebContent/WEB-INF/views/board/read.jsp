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
    $('#btnComment').on('click', function(e) {
        e.preventDefault(); // 폼 제출 방지
        var data = {
            boardSeq: $("input[name='boardSeq']").val(),
            memberId: $("input[name='memberId']").val(),
            memberNick: $("input[name='memberNick']").val(),
            commentContent: $("#commentContent").val()
        };

        $.ajax({
            url: "<c:url value='/comment/write.do'/>",
            type: 'POST',
            contentType: 'application/json', // JSON 데이터를 보내기 위한 Content-Type 설정
            data: JSON.stringify(data), // JavaScript 객체를 JSON 문자열로 변환
            dataType: 'json', // 서버로부터 JSON 응답을 기대함
            success: function(response) {
            	// 새로운 댓글을 댓글 목록에 추가
                var newComment = $('<div>').addClass('comment-item').text(data.commentContent);
                $('#comment-list').append(newComment);
                
                // 댓글 입력 필드 초기화
                $("#commentContent").val('');
                alert(response.msg);
            },
            error: function(xhr, status, error) {
                alert('작성 중 오류 발생');
            }
        });
    });

    $('#btnDelete').on('click', function() {
        var formData = new FormData(document.readForm);
        if (confirm("삭제하시겠습니까?")) {
            $.ajax({
                url: "<c:url value='/board/delete.do'/>", // 요청을 보낼 서버의 URL
                type: 'POST', // HTTP 요청 방식
                data: formData, // 서버로 보낼 데이터
                processData: false,
                contentType: false,
                success: function(response) {
                    if (response.success) {
                        alert(response.message); // 여기를 수정했습니다.
                        window.location.reload();
                    } else {
                        alert(response.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert('삭제 중 실패 오류');
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
				<form name="readForm" class="validate" method="post" enctype="multipart/form-data" data-success="Sent! Thank you!" data-toastr-position="top-right">
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
							<section id="comments">
							<div class="container">
								<div class="row">
									<div class="col-md-12">
										<h3>댓글</h3>
										<!-- 댓글 목록 -->
										<div id="comment-list">
											<c:forEach items="${commentList}" var="comment">
												<div class="comment-item">
													<p>
														<strong>${comment.member_nick}</strong> (${comment.create_dtm}):${comment.comment_content}
													</p>
													<!-- 현재 로그인한 사용자의 ID나 닉네임이 댓글 작성자의 ID나 닉네임과 같은 경우에만 수정 및 삭제 버튼을 보여줍니다. -->
											        <c:if test="${sessionScope.memberId == comment.member_id}">
											            <button type="button" class="btn btn-secondary" onclick="editComment('${comment.commentSeq}')">수정</button>
											            <button type="button" class="btn btn-danger" onclick="deleteComment('${comment.commentSeq}')">삭제</button>
											        </c:if>
												</div>
											</c:forEach>
										</div>

										<!-- 댓글 작성 -->
										<div id="comment-form">
											<h4>댓글 작성</h4>
											<form name="commentForm" action="<c:url value='/comment/write.do'/>" method="post">
												<input type="hidden" name="boardSeq" value="${read.board_seq}" />
												<input type="hidden" name="memberId" value="${sessionScope.memberId}" />
												<input type="hidden" name="memberNick" value="${sessionScope.memberNick}" />
												<div class="form-group">
													<label for="commentContent">댓글:</label>
													<textarea class="form-control" id="commentContent" name="commentContent" rows="3" required></textarea>
												</div>
												<button type="submit" class="btn btn-primary" id="btnComment">댓글 작성</button>
											</form>
										</div>
									</div>
								</div>
							</div>
							</section>
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