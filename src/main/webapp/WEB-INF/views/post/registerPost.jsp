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
				<form id="frmPost" method="post" action="/post/registerPost">
				<%@ include file="./include/postCommon.jsp" %>
				
				<button id="btnRegisterPost" type="submit" class="btn btn-primary">등록</button>
				<button type="reset" class="btn btn-secondary">초기화</button>
				<input type="hidden" name="boardId" value="${boardId}">
			</form>

		</div>
	</div>
<!-- /.container-fluid -->
<%@include file="../common/attachFileManagement.jsp"%>

</div>
<%@include file="../includes/footer.jsp"%>

<!-- End of Main Content -->

<script type="text/javascript">
$(document).ready(function() {
	var csrfHN = "${_csrf.headerName}";
	var csrfTV = "${_csrf.token}";
	
	$(document).ajaxSend(
		function(e, xhr){
			xhr.setRequestHeader(csrfHN, csrfTV);
		}
	);
	
	controlInput('신규');
	adjustCRUDAtAttach('신규');
	
	var frmPost = $("#frmPost");
	
	$("#btnRegisterPost").on("click", function(e) {
		e.preventDefault();
		addAttachInfo(frmPost, "listAttachInStringFormat");
		frmPost.submit();
	});
});
</script>