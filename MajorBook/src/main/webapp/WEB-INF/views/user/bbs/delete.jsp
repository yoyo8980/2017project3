<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../../menu/header.jsp"></jsp:include>
<jsp:include page="../../menu/menutest.jsp"></jsp:include>

	<!-- 내용 -->	
	<div class="container">
		<div class="row content">
		  <div class="col-md-12">
		  	<div class="page-header">
			  <h1>삭제확인</h1>
			</div>
			  <h2>정말로 게시글을 삭제하시겠습니까?</h2>
			  <form  action="delete/${bean.no}" method="post">
			  		<input name="_method" value="DELETE" type="hidden">
					<button class="btn btn-danger">삭제</button>
			 </form>
			
			

<jsp:include page="../../menu/footer.jsp"></jsp:include>