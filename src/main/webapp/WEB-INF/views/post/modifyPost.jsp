<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<!-- Begin Page Content -->
<div class="container-fluid">
 
	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-body">
			<form id='frmPost' action="/post/modifyPost" method="post">
				<%@ include file="./include/postCommon.jsp" %>
				<%@ include file="../common/attachFileManagement.jsp"%>
				
				<sec:authentication property="principal" var ="customUser"/>
				<sec:authorize access="isAuthenticated()">
					<c:if test="${customUser.curUser.userId eq post.writer.userId}">
						<button type="submit" data-oper='modify' class="btn btn-primary" >수정</button>
						<!-- 이 data-oper는 modify라는 변수를 추가해서, html에서 제공하는 element의 변수명의 data를 추가할 수 있는 장치.  -->
						<button type="submit" data-oper='remove' class="btn btn-danger" >삭제</button>
					</c:if>
				</sec:authorize>	
				<button type="submit" data-oper='list' class="btn btn-secondary">목록으로</button>
				
				<input id="boardId" type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" name="postId" value="${post.id}">
				<input type="hidden" name="writerId" value="${customUser.curUser.userId}">
				<input type="hidden" name="pageNumber" value="${pagination.pageNumber}">
				<input type="hidden" name="amount" value="${pagination.amount}">
				<input type="hidden" name="searching" value='${pagination.searching}'>
				<!-- Criteria fromUser를 추가한 Data를 받아오기위해 두 개의 코드 추가 -->
				<!-- HashTag에서는 앞선 Searching에서 사용한 요소인 keyword, type을 searching 하나로 대신함 06.07 -->
				<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>
			</form>
			
		</div>
	</div>
</div>
<!-- /.container-fluid -->

<!-- End of Main Content -->
<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	var csrfHN = "${_csrf.headerName}";
	var csrfTV = "${_csrf.token}";
	
	$(document).ajaxSend(
		function(e, xhr) {
			xhr.setRequestHeader(csrfHN, csrfTV);
		}
	);
	controlInput('수정');
	adjustCRUDAtAttach('수정');
	
	<c:forEach var="attachVoInStr" items="${post.attachListInGson}" >
		appendUploadUl('<c:out value="${attachVoInStr}" />');
	</c:forEach>
	
	//empty라고 하는 기능은 form에 담겨있는 모든 하위 요소를 없애버려라.
	var frmPost = $("#frmPost"); // 값을 찾아놓고 객체로 선언
	//처리 우선 순위가 있고 script가 html에 등록되어 있는것보다 처리하는 우선 순위가 빠르다.
	$("button").on("click", function(eventInfo) { // eventInfo를 받아서
		// preventDefault(): 이벤트 처리의 전파(퍼져 나가는뜻)를 막아서 미리 개발되어있는 event 처리를 막는 함수입니다.
		eventInfo.preventDefault(); // window의 기본행위를 막는다.
		
		var oper = $(this).data('oper'); // "oper"의 data를 꺼낼것이다.
		if (oper === 'remove') {
			frmPost.attr('action', "/post/removePost");
		} else if(oper === 'list') {
			/* 속성 선택자 : 특정한 속성을 가지는 요소를 선택한다.
			*/
			var boardIdInput = frmPost.find("#boardId");
			var pageNumber = $('input[name="pageNumber"]');
			var amount = $('input[name="amount"]');
			
			var searching = $('input[name="searching"]');
			
			frmPost.attr("method", "get");
			frmPost.attr('action', "/post/listBySearch");
			// ↑ web에서 목록으로 돌아가기 하는 부분
			
			frmPost.empty(); // $("#frmPost")를 비워내 버릴것
			frmPost.append(boardIdInput);
			// 수정에서 목록 버튼을 누르면, 다시 보고있던 목록 페이지로 돌아올 수 있게끔 값을 추가
			frmPost.append(pageNumber);
			frmPost.append(amount);
			frmPost.append(searching);
			
		} else if (oper === 'modify') {
			addAttachInfo(frmPost, "listAttachInStringFormat");
		}
		frmPost.submit();  // 이런식으로 해놔야 성능에서 문제가 크게 안생인다.
	});
});
</script>
