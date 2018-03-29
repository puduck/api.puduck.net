<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home</title>
    <script src="/resources/hello.all.js"></script>
    <script type="text/javascript">
        hello.init({facebook: '652796481510780'}, {redirect_uri: location.origin + '/sns/callback'}
        );
        hello.on('auth.login', function(auth) {
            console.info(auth);
        });
    </script>
</head>
<body>
<h1>
    Hello world!
</h1>

<P> The time on the server is ${serverTime}. </P>

<button onclick="hello('facebook').login()">facebook</button>

</body>
</html>
