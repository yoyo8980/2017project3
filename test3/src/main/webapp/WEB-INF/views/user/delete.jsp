<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../menu/header.jsp"></jsp:include>
			        	<li><a href="/major">HOME </a></li>
			           	<li><a href="/major/aboutus">ABOUT US</a></li>
						<li><a href="/major/notice">공지사항</a></li>
			            <li  class="active"><a href="/major/board">게시판<span class="sr-only">(current)</span></a></li>
			            <li><a href="/major/question">문의사항</a></li>
			      </ul>
<jsp:include page="../loginoutdel/login.jsp"></jsp:include>
			      </div>
	  </div>
	</nav>
	</div>
	<!-- 내용 -->	
	<div class="container">
		<div class="row content">
		  <div class="col-md-12">
		  	<div class="page-header">
			  <h1>삭제확인</h1>
			</div>
			  <h2>정말로 게시글을 삭제하시겠습니까?</h2>
			  <form  action="/major/delete/${bean.no}" method="post">
			  		<input name="_method" value="DELETE" type="hidden">
					<button class="btn btn-danger">삭제</button>
			 </form>
			
			

<jsp:include page="../menu/footer.jsp"></jsp:include>