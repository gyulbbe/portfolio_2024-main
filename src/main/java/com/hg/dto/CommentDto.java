package com.hg.dto;

public class CommentDto {

	private String commentContent; // 댓글 내용
	private int memberIdx; // 댓글 Idx
	private String memberId; // 댓글 Id
	private String memberNick; // 댓글 Id
	private String createDtm; // 댓글 작성 날짜
	private int commentSeq; // 댓글 번호
	private int boardSeq; // 게시물 번호
	private String boardTypeSeq; //공지글인가 자유게시글인가

	// 기본 생성자
	public CommentDto() {}
	

	public CommentDto(String commentContent, int memberIdx, String memberId, String memberNick, String createDtm,
			int commentSeq, int boardSeq, String boardTypeSeq) {
		super();
		this.commentContent = commentContent;
		this.memberIdx = memberIdx;
		this.memberId = memberId;
		this.memberNick = memberNick;
		this.createDtm = createDtm;
		this.commentSeq = commentSeq;
		this.boardSeq = boardSeq;
		this.boardTypeSeq = boardTypeSeq;
	}

	@Override
	public String toString() {
		return "CommentDto [commentContent=" + commentContent + ", memberIdx=" + memberIdx + ", memberId=" + memberId
				+ ", memberNick=" + memberNick + ", createDtm=" + createDtm + ", commentSeq=" + commentSeq
				+ ", boardSeq=" + boardSeq + ", boardTypeSeq=" + boardTypeSeq + "]";
	}



	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}


	public String getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(String createDtm) {
		this.createDtm = createDtm;
	}

	public int getCommentSeq() {
		return commentSeq;
	}
	
	public void setCommentSeq(int commentSeq) {
		this.commentSeq = commentSeq;
	}

	public int getBoardSeq() {
		return boardSeq;
	}


	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}


	public String getBoardTypeSeq() {
		return boardTypeSeq;
	}


	public void setBoardTypeSeq(String boardTypeSeq) {
		this.boardTypeSeq = boardTypeSeq;
	}


	public int getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

}
