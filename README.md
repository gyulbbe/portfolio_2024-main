# finish:<br/>
회원가입(회원가입 시 id중복 검사 + 비밀번호 최소 6자리 + 회원가입 시 비밀번호 확인)<br/>
로그인(세션)<br/>
회원가입 시 환영 이메일<br/>
게시글 작성, 읽기, 삭제, 수정<br/>
페이징<br/>
게시물 검색<br/>
로그인이 안되어 있을 경우 글 작성, 삭제 버튼 클릭 시 로그인 페이지로 이동<br/>
자신의 게시물이 아니면 수정, 삭제 불가능(세션 중간에 끊어져도)<br/>
댓글 리스트 불러오기, 작성, 삭제<br/>
댓글 작성과 삭제(세션 중간에 끊어져도 못하게 막기)<br/>

# info:<br/>
게시글 삭제 버튼은 본인 게시글이 아니어도 보이지만 누르면 알림과 함께 막기<br/>
수정은 버튼은 본인 게시글이 아니면 보이지 않게 하고 혹여나 수정하는 페이지 안에 들어갔더라도 수정을 실행하는 컨트롤러에서 본인 게시글이 아니면 못하게끔 막아놨음<br/>
이유는 만약 수정페이지 안에서 세션이 끊긴다면 데이터를 받지 못하게 하려고<br/>
버튼을 없애는 것과 컨트롤러에서 막는 것 하나씩 다 해보고자 했음<br/>

# to-do:<br/>
게시글 파일 추가와 삭제, 관리자가 파일 옆에 체크표시로 자유게시물 쉽게 삭제, 관리자 페이지에는 관리자만 게시 가능하게
