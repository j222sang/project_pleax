/**
 CallBack 함수 : 특정 Event에 대응
 */
 
/** 함수 정의 영역 */


var replyService = (function() {
	function getCountOfReply(replyId, successCallBack, errorCallBack) {
		$.getJSON(
			"/replies/count/" + replyId, 
			function(cnt) {
				if (successCallBack) {
					successCallBack(cnt);
				}
			}
		).fail(function(xhr, status, errMsg){
			if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}	
		);
	}
	
	//  https://api.jquery.com/jquery.getjson/
	function getList(orgIdAndPage, successCallBack, errorCallBack) { //댓글 목록을 불러오는 js
		var page = orgIdAndPage.page || 1; // page의 값이 있을수도, 없을수도 있으니 조건 달아두기
		$.getJSON( // List를 불러오는 함수의 방식이 @GetMapping이다.  여기에서도 getJSON 방식이니 서로 Matching이 잘 되는지 확인
			"/replies/pages/" + orgIdAndPage.orgId +  "/" + page , 
			function(listReply) {
				if (successCallBack) {
					successCallBack(listReply);
				}
			}
		).fail(function(xhr, status, errMsg){
			if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}	
		);
	}
	
	function getListOfReply(replyId, successCallBack, errorCallBack) {
			$.getJSON( 
			"/replies/pages/" + replyId, 
			function(listALllReplyOfReply) {
				if (successCallBack) {
					successCallBack(listALllReplyOfReply);
				}
			}
		).fail(function(xhr, status, errMsg){
			if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}	
		);
	}
	
	function get(replyId, successCallBack, errorCallBack){
		$.getJSON(
			"/replies/" + replyId + ".json",
			function(replyObj){
				if(successCallBack){
					successCallBack(replyObj);
				}
			}
		).fail(
			function(xhr, status, errMsg){
				if (errorCallBack){
					errorCallBack(errMsg);
				}
			}
		);
		
	}
	

	function add(originalId, reply, successCallBack, errorCallBack) { // 06.14 시작 - 인자들을 선언
	// https://api.jquery.com/jquery.ajax/ 에서 더 자세한 설명들을 볼 수있다.
		$.ajax({
			type: 'post',
			url: '/replies/new/' + originalId,
			data: JSON.stringify(reply), // stringify : 객체를 문자열로 출력
			contentType: "application/json; charset=UTF-8",
			success: function(resObj, status, xhr) {
				if (successCallBack) {
					successCallBack(resObj);
				}
			},
			
			error: function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}
	
	function update(reply, successCallBack, errorCallBack){
		$.ajax({
			type: 'put',
			url: '/replies/',
			data: JSON.stringify(reply),
			contentType : "application/json; charset=UTF-8",
			success: function(resultMsg, status, xhr) {
				if (successCallBack) {
					successCallBack(resultMsg);
				}
			},
			
			error: function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
		
	}
	
	function remove(replyId, successCallBack, errorCallBack ) {
		$.ajax({
			type: 'delete',
			url: '/replies/' + replyId,
			success: function(delResult, status, xhr) {
				if (successCallBack) {
					successCallBack(delResult);
				}
			},
			
			error: function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}
	
	//댓글 처리용 함수들
	return {
		getAllReplyCountOfReply : getCountOfReply, 
		getReplyList : getList,
		getReplyListOfReply : getListOfReply,
		getReply : get,
		addReply : add,
		updateReply : update,
		removeReply : remove
		};
})();