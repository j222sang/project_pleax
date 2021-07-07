<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!-- id 값으로 content를 그냥 사용하는건 위험하다. 왜냐하면 content값이 예약을 처리하는 기능이 있기에  -->
<!-- 실 내용만 담을 postCommon.jsp -->
<div class="form-group">
	<label>아이디</label>
	<input name="id" value="${post.id}" class="form-control" readonly>
<!-- 여긴 중요한게, 객체를 만들어주는 부분이다. 제목을 넣는 부분 -->
</div>

	<!-- 수정 처리시(modify) title,content에는 readonly는 없어야 한다. -->
	<!-- 신규 처리시 title,content에는 value가 없고 readonly도 없다.  -->
	
	<!-- 신규화면에서 필요한 것들,  -->
<div class="form-group">
	<label>제목</label> <input id="title" name="title" value="${post.title}" 	class="form-control" readonly>
</div>

<div class="form-group">
	<label>내용</label>
	<textarea id="txaContent" name="content" class="form-control" rows="3" readonly>${post.content}</textarea>
	<!-- rows: 몇줄까지 화면에 보이게 할건지 -->
</div>

<div class="form-group">
	<label>작성자</label>
	 <c:choose>
	 	<c:when test="${empty post}">
	 		<input value= '<sec:authentication property="principal.curUser.name"/>'	class="form-control" readonly>
	 		
	 	</c:when>
	 	<c:otherwise>
	 		<input value="${post.writer.name}"	class="form-control" readonly>
	 	</c:otherwise>
	 </c:choose>
</div>

<!-- 05.27 새로운 속성들 추가 -->

<div class="form-group">
	<p>
		조회수 : <b>${post.readCnt}</b> 좋아요 : <i>${post.likeCnt}</i> 싫어요 : <strong>${post.dislikeCnt}</strong>
	</p>
</div>

<div class="form-group">
	<label>등록일 : </label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.registrationDate}" />
	<label>, 수정일 : </label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.updateDate}" />
</div>

<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>

<script type="text/javascript">
//$(document).ready(function() { 이게 있으면 함수가 정의?가 안된다. 
	<!-- 수정 처리시(modify) title,content에는 readonly는 없어야 한다. -->
	<!-- 신규 처리시 title,content에는 value가 없고 readonly도 없다.  -->
	function controlInput(includer) {
		if(includer === '수정' || includer === '신규')  { //includer가 갖고있는 요소가 수정이니 아님 신규이니 라고 묻는다면
			$('#title').attr("readonly" , false);
			//document.getElementById("txaContent").readonly =  false;
			 $('#txaContent').attr("readonly" , false); //title, content 부분을 readonly를 false로 바꿔주면
		}
	}
</script>

