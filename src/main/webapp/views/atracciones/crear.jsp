<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/ini.jsp"></jsp:include>

<style type="text/css">

.form {
padding-top: 20px;
}

</style>
</head>
<body background="/TierraMedia/img/ecole.jpg">
	<jsp:include page="/barra/nav.jsp"></jsp:include>

	<main class="container">

		<c:if test="${attraction != null && !attraction.esValida()}">
			<div class="alert alert-danger">
				<p>Se encontraron errores al crear la atracción.</p>
			</div>
		</c:if>

		<form action="/TierraMedia/atracciones/crear.do" method="post" class="form">
			<input type="hidden" name="id" value="${attraction.id}">
			<jsp:include page="/views/atracciones/formulario.jsp"></jsp:include>
		</form>
	</main>
</body>
</html>
