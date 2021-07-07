<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid"></div>
<!-- DataTales Example -->
<div class="card shadow mb-4">
	<div class="card-body">
		<div class="form-group">
			<%@ include file="./include/postCommon.jsp" %>
			<!-- 공통적 속성인 실 내용들은 postCommon.jsp를 만들어서 보내버렸음 -->

			<sec:authentication property="principal" var ="customUser"/>
			<sec:authorize access="isAuthenticated()">
				<c:if test="${customUser.curUser.userId eq post.writer.userId}">
					<button data-oper='modify' class="btn btn-primary">수정</button>
				</c:if>
			</sec:authorize>
			
			<button data-oper='list' class="btn btn-secondary">목록</button>

			<form id='frmOper' action="/post/modifyPost" method="get">
				<input type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" id="postId" name="postId" value="${post.id}">
				<input type="hidden" name="pageNumber" value="${pagination.pageNumber}">
				<input type="hidden" name="amount" value="${pagination.amount}">
				<input type="hidden" name="searching" value='${pagination.searching}'>
			</form>
			
			<%@include file="../common/attachFileManagement.jsp" %>
			
		</div>

		<%@include file="./include/replyManagement.jsp" %>
	</div>
</div>
<!-- /.container-fluid -->

<!-- End of Main Content -->
<%@include file="../includes/footer.jsp"%>

<script type="text/javascript"> // El에 JSP가 만들어져야 돌아감 ↓
	$(document).ready(function() {
		adjustCRUDAtAttach('조회');
		
		<c:forEach var="attachVoInStr" items="${post.attachListInGson}" >
			appendUploadUl('<c:out value="${attachVoInStr}" />');
		</c:forEach>
		
		//EL이 표현한 LIST 출력 양식, 그래서 첨부파일이 안보임, El은 Server에서 돌아감
		//postCommon에 있는 함수를 부를 것
		
		$("button[data-oper='modify']").on("click", function() {
			$("#frmOper").submit();
		});
		
		$("button[data-oper='list']").on("click", function() {
			$("#frmOper").find("#postId").remove();
			$("#frmOper").attr("action", "/post/listBySearch").submit();
	});
});
</script>

