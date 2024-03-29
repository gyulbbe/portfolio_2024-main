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
		var memberIdx = "${sessionScope.memberIdx}";
		var memberId = "${sessionScope.memberId}";
        var memberNick = "${sessionScope.memberNick}";
        
		//서버 사이드에서 생성된 값을 JavaScript 변수에 할당
		//댓글 작성
				$('#btnComment').on('click',function(e) {
					
					var boardSeq = "${read.boardSeq}";
			        var commentContent = $("#commentContent").val().trim();
					
							e.preventDefault(); // 폼 제출 방지
							var data = {
								boardSeq : boardSeq,
								memberIdx : memberIdx,
								memberId : memberId,
								memberNick : memberNick,
								commentContent : commentContent
							};

						    if (commentContent.length === 0) {
						        alert("댓글을 입력하세요.");
						        $('#commentContent').focus();
						        return;
						    } else if (commentContent.length > 20) {
						        alert("댓글은 최대 20자까지 허용합니다.");
						        $('#commentContent').focus();
						        return;
						    }
							
							$.ajax({
								url : "<c:url value='/comment/write.do'/>",
								type : 'POST',
								contentType : 'application/json', // JSON 데이터를 보내기 위한 Content-Type 설정
								data : JSON.stringify(data), // JavaScript 객체를 JSON 문자열로 변환
								dataType : 'json', // 서버로부터 JSON 응답을 기대함
								success : function(response) {
									
									//success와 아닌 경우 나눈 이유: 세션id끊긴 채로 댓글 입력 방지하려고
									if(response.success){
										
									// 새로운 댓글을 댓글 목록에 추가
									var newCommentHtml = '<div class="comment-item">' +
    '<p><strong>' + response.memberNick + '</strong> (' + response.createDtm + '): ' + response.commentContent + '</p>' +
    '<button type="button" class="delete-btn btn btn-danger" data-comment-seq="' + response.commentSeq + '">삭제</button>' +
    '</div>';
							       // 댓글 목록에 새 댓글 추가
							          $('#comment-list').prepend(newCommentHtml);

									// 댓글 입력 필드 초기화
									$("#commentContent").val('');
									alert(response.msg);
									} else{
									alert(response.msg);
									}
								},
								error : function(xhr, status, error) {
									alert('작성 중 오류 발생');
								}
							});
						});
				
				// 댓글 목록 내의 삭제 버튼에 대한 이벤트 위임
			    $('#comment-list').on('click', '.delete-btn', function() {
        // 클릭된 버튼의 부모 요소인 댓글 항목을 찾음
        var commentItem = $(this).closest('.comment-item');
        var commentSeq = $(this).data('comment-seq'); // data-comment-seq 속성에서 commentSeq 값을 가져옴
        var data = {
				commentSeq : commentSeq
			};
        // 사용자에게 삭제 확인 받음
        if (confirm("댓글을 삭제하시겠습니까?")) {
            $.ajax({
            	url: "<c:url value='/comment/" + commentSeq + "/delete.do'/>", // 서버의 댓글 삭제 처리 URL
                type: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(response) {
                    if(response.result == 1) {
                        // 댓글 삭제 성공 시 DOM에서 해당 댓글 항목 제거
                        commentItem.remove();
                        alert(response.msg);
                    } else {
                        alert(response.msg);
                    }
                },
                error: function(xhr, status, error) {
                    alert('댓글 삭제 중 오류 발생');
                }
            });
        }
    });
				$('#btnDelete').on('click', function() {
					var formData = new FormData(document.readForm);
					if (confirm("삭제하시겠습니까?")) {
						$.ajax({
							url: `<c:url value='/board/${read.boardSeq}/delete.do'/>`, // 요청을 보낼 서버의 URL
							type : 'DELETE', // HTTP 요청 방식
							data : formData, // 서버로 보낼 데이터
							processData : false,
							contentType : false,
							success : function(response) {
								if (response.success) {
									alert(response.message);
									movePage('/board/list.do');
								} else {
									alert(response.message);
								}
							},
							error : function(xhr, status, error) {
								alert('삭제 중 실패 오류');
								//window.location.reload();
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
				<form name="readForm" class="validate" enctype="multipart/form-data" data-success="Sent! Thank you!" data-toastr-position="top-right" method="post">
					
					<input type="hidden" name="typeSeq" value="${read.typeSeq}" />
					<input type="hidden" name="memberId" value="${read.memberId}" />
					<input type="hidden" name="memberNick" value="${read.memberNick}" />
				</form>
				<!-- post -->
				<div class="clearfix mb-80">
					<div class="border-bottom-1 border-top-1 p-12">
						<span class="float-right fs-10 mt-10 text-muted">${read.createDtm}</span>
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
								<small class="block">${read.memberNick}</small>
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
														<strong>${comment.member_nick}</strong>(${comment.create_dtm}):${comment.comment_content}
													</p>
													<!-- 현재 로그인한 사용자의 ID나 닉네임이 댓글 작성자의 ID나 닉네임과 같은 경우에만 수정 및 삭제 버튼을 보여줍니다. -->
													<c:if test="${sessionScope.memberId == comment.member_id}">
														<button type="button" class="delete-btn btn btn-danger"
															data-comment-seq="${comment.comment_seq}">삭제</button>

													</c:if>
												</div>
											</c:forEach>
										</div>
										<c:if test="${not empty sessionScope.memberId}">
											<!-- 댓글 작성 -->
											<div id="comment-form">
												<h4>댓글 작성</h4>
												<div class="form-group">
													<label for="commentContent">댓글:</label>
													<textarea class="form-control" id="commentContent"
														name="commentContent" rows="3" required></textarea>
												</div>
												<button type="submit" class="btn btn-primary"
													id="btnComment">댓글 작성</button>
											</div>
										</c:if>

									</div>
								</div>
							</div>
							</section>
						</div>
						<div class="row">
							<div class="col-md-12 text-right">
								<c:if test="${sessionScope.memberId == read.memberId}">
									<a href="javascript:movePage('/board/goToUpdate.do?boardSeq=${read.boardSeq}&memberId=${read.memberId}')">
										<button type="button" class="btn btn-primary">
											<i class="fa fa-pencil"></i> 수정
										</button>
									</a>
								</c:if>
								<button type="button" class="btn btn-danger" id="btnDelete">삭제</button>
								<c:choose>
									
									<c:when test="${not empty currentPage and not empty searchType and not empty keyword}">
										<a href="javascript:movePage('/board/list.do?page=${currentPage}&searchType=${searchType}&keyword=${keyword}')">
											<button type="button" class="btn btn-primary">목록</button>
										</a>
									</c:when>
									
									<c:when test="${not empty currentPage}">
										<a href="javascript:movePage('/board/list.do?page=${currentPage}')">
											<button type="button" class="btn btn-primary">목록</button>
										</a>
									</c:when>
									
									<c:otherwise>
										<a
											href="javascript:movePage('/board/list.do')">
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