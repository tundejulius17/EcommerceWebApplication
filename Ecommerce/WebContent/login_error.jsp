<jsp:include page="/includes/adminHeader.jsp" />

<div id="cusLeftColumn">
	<ul id="verticalNav">
		<li id="navHead">ADMIN MENU</li>
	</ul>
</div>

<div id="indexRightColumn">
	<div id="adminLoginColumn">
		<h1 class="hclass">Admin Login Form</h1>
		<p class="pclass">Incorrect user name or password.</p>
		<p class="pclass">Try again to login.</p>

		<form action="j_security_check" method="post">
			<table class="formTable" id="checkoutTable">

				<tr>
					<td><label>User name</label></td>


					<td><input type="text" name="j_username" required><br></td>

				</tr>

				<tr>
					<td><label>Password</label></td>


					<td><input type="password" name="j_password" required><br></td>
				</tr>


				<tr>

					<td></td>
					<td><input type="submit" value="Login" id="submit"></td>
				</tr>


			</table>

		</form>

	</div>
</div>

<jsp:include page="/includes/footer.jsp" />