<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/link.jsp" %>
 
<link rel="stylesheet" href="/css/auth/signin.css">
</head>
<body>
	<main>
		<div class="login_box">
        	<a href="/"><img src="/img/bamin2.png" alt="이미지" class="bm_img"></a>
				<form action="/auth/signup" method="post" >
					<div class="input_aera">
		            	<input type="text" name="username"  class="username" maxlength="20"  placeholder="아이디를 입력해 주세요"  value="${signupDto.username}" >
						<span class="msg_box">${valid_username}</span>
	            	</div>          
		               
					<div class="input_aera">
						<input type="password" class="password1" name="password" maxlength="20"  placeholder="비밀번호를 입력해 주세요"  value="${signupDto.password}">
					</div>
	               
					<div class="input_aera">
						<input type="password" class="password2" maxlength="20"  placeholder="비밀번호를 한번더 입력해 주세요" value="${signupDto.password}">
			            <span class="msg_box">${valid_password}</span>
	               	</div>
		               
	               	<div class="input_aera">
	               		<input type="text" name="email" class="email"  placeholder="이메일을 입력해 주세요"  value="${signupDto.email}" >
		                <span class="msg_box">${valid_email}</span>
               		</div>
		               
					<div class="input_aera">
						<input type="text" class="nickname" name="nickname" maxlength="20"  placeholder="사용하실 닉네임을 입력해 주세요"  value="${signupDto.nickname}">
	               		<span class="msg_box">${valid_nickname}</span>
					</div>
		               
					<div class="input_aera">
						<input type=number name="phone" class="phone" placeholder="'-' 없이 입력해 주세요" maxlength="11"  value="${signupDto.phone}" >
	                    <span class="msg_box">${valid_phone}</span>
	                </div>
	                
	               <input type="submit" value="회원가입" class="login_btn" >
			</form>
        </div>
 
    </main>
</body>
</html>
