<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/ini.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/barra/navi.jsp"></jsp:include>

	<main class="container">

		<c:if test="${attraction != null && !attraction.esValida()}">
			<div class="alert alert-danger">
				<p>Se encontraron errores al actualizar la atracción.</p>
			</div>
		</c:if>

		<form action="/TierraMedia/atracciones/editar.do" method="post">
			<input type="hidden" name="id" value="${attraction.id}">
			<jsp:include page="formulario.jsp"></jsp:include>
		</form>
	</main>
</body>
</html>
