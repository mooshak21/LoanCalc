<%-- 
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>

<div class="row justify-content-center">
	<div class="card col-10 col-md-8 cardBody">
		<div class="card-header">
			<br>${message}</br>
		</div>
		<div class="card-block">
			<br>The subscription will be enabled for FREE plan </br>
			<br>as you have cancelled payment option which apply to LITE/PREMIUM plans.</br>
		</div>
	</div>
</div>


<script>
	function resetForm() {
		$(document).ready(function() {
			$(".resetMe").val("");
		});

	};
</script>
