<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	//사진 변경 코드
	$(document).ready(function() {
		$(".nav-tabs li").click(function() {
			var tabId = $(this).find("a").attr("href");
			var imageSrc = "";

			if (tabId === "#tab1") {
				imageSrc = "resources/demo_files/images/2.jpg";
			} else if (tabId === "#tab2") {
				imageSrc = "resources/demo_files/images/3.jpg";
			} else if (tabId === "#tab3") {
				imageSrc = "resources/demo_files/images/4.jpg";
			} else if (tabId === "#tab4") {

	imageSrc = "resources/demo_files/images/5.jpg";
			}

			$(".img-responsive").attr("src", imageSrc);

			// 모든 탭 내용 숨기기
			$(".tab-pane").removeClass("active in");

			// 선택된 탭 내용 보여주기
			$(tabId).addClass("active in");
		});
	});
</script>
</head>
<body class="smoothscroll enable-animation">
	<%-- 내용 나올 div 시작!!!! --%>
	<!-- -->
	<section>
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6 col-sm-6">
				<div class="col-lg-3 col-md-3 col-sm-3">
					<img class="img-responsive" src="resources/demo_files/images/1.jpg"
						width="500" height="300" alt="">
				</div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6">
				<div class="heading-title heading-border-bottom">
				<!-- 아래에 제목 작성 -->
					<h3></h3>
				</div>
				<p>정석 코딩에서 좋은 팀원들을 만나 프로젝트 진행 초반에 팀의 프로젝트 완성보단 각자 자신의 개인 역량을 키우자는 뜻이 통했다.</br>
				그리하여 우리는 정석 코딩에서 나와 함께 각자의 개인 프로젝트를 하기로 했으며,</br>
				같이 수업도 들으며 아직도 부족함을 느꼈던 우리는 추가로 공유오피스를 잡아</br>
				약 한달간 서로 도움을 주고 받아가며 각자 깃헙, 노션, 포트폴리오에 함께 자신의 이야기를 써내려갔다.</br>
				왼쪽의 사진은 팀원들과 함께 지냈던 공간이다.</p>
				<blockquote>
					<p>열심히 좀 하려고 하지 말고 그냥 해라</p>
					<cite>LJY</cite>
				</blockquote>
			</div>
		</div>
	</div>
	</section>

	<section>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="heading-title heading-border-bottom">
					<h3>The History Of Making This Portfolio(아래 탭을 눌렀을 때 바로 뜨지 않으면 새로고침 한번)</h3>
				</div>
				<ul class="nav nav-tabs nav-clean">
					<li class="active"><a href="#tab1" data-toggle="tab">ERROR
							SUMMARY</a></li>
					<li><a href="#tab2" data-toggle="tab">JAVA STUDY</a></li>
					<li><a href="#tab3" data-toggle="tab">SPRING STUDY</a></li>
					<li><a href="#tab4" data-toggle="tab">STUDY GROUP</a></li>
				</ul>
				<div class="tab-content">
					<div id="tab1" class="tab-pane fade">
						<p>이 포트폴리오를 만들며 겪었던 에러와 해결해나간 과정을 정리</p>
						<p>
							<a
								href="https://shore-houseboat-141.notion.site/81eca21e7f9b4e5da81894190ad75e1d?pvs=4"
								target="_blank">자세히 보기</a>
						</p>
					</div>
					<div id="tab2" class="tab-pane fade">
						<p>스프링 프레임워크를 배우며 자바 공부의 필요성을 느끼고 설 연휴 2주가량 자바의 정석 컬렉션 프레임워크까지 복습을 하며
						잘 이해가 안됐거나 새롭게 알게 된 부분들을 위주로 정리</p>
						<p>
							<a
								href="https://shore-houseboat-141.notion.site/5358801e68524216a931e0d8fd4cb6c4?pvs=4"
								target="_blank">자세히 보기</a>
						</p>
					</div>
					<div id="tab3" class="tab-pane fade">
						<p>포트폴리오를 제작하며 헷갈리거나 고생해서 시간을 많이 잡아먹었던 부분만 따로 정리</p>
						<p>
							<a
								href="https://shore-houseboat-141.notion.site/284b8197af174a138332d49ea97e865a?pvs=4">자세히
								보기</a>
						</p>
					</div>
					<div id="tab4" class="tab-pane fade">
						<p>팀원들끼리 부족한 부분을 느끼고 스터디 했던 기록을 정리</p>
						<p>
							<a
								href="https://shore-houseboat-141.notion.site/3bfac5abb4184380a9fec0ba83ac8ff5"
								target="_blank">자세히 보기</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	</section>
	<!-- / -->
</body>
</html>