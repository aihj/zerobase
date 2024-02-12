<%@ page import="data.historyDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="data.historyDTO" %>
<%@ page import="data.wifiDAO" %>
<%@ page import="data.wifiDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>



<script src="https://code.jquery.com/jquery-3.5.1.js"></script>

<html>
<head>
    <title>와이파이 정보 구하기</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css?after">
</head>
<body>
<h1>와이파이 정보 구하기</h1>


<%@ include file="title.jsp"%>
<br>
<div>
    <%
        historyDAO service = new historyDAO();
        List<historyDTO> historyList = service.searchList();

        String strID = request.getParameter("id");
        if (strID != null) {
            service.delList(strID);
        }
    %>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>x좌표</th>
                <th>y좌표</th>
                <th>조회일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
            <% if (historyList.isEmpty()) {%>
                <tr>
                    <td colspan="5">위치를 검색한 이력이 없어요.</td>
                </tr>
            <% } else { %>
            <% for (data.historyDTO historyDTO : historyList) { %>
                <tr>
                    <td><%=historyDTO.getId()%></td>
                    <td><%=historyDTO.getLat()%></td>
                    <td><%=historyDTO.getLnt()%></td>
                    <td><%=historyDTO.getSearchDttm()%></td>
                    <td><button onclick="del(<%=historyDTO.getId()%>)">삭제</button></td>
                </tr>
            <% }} %>
    </tbody>
    </table>
</div>
<script>
    function del(ID) {

        $.ajax({
            url: "http://localhost:8080/history.jsp",
            data: {id : ID},
            success: function () {
                location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n"+ "message: " + request.responseText + "\n" + "error: " + error);
            }
        })
    }
</script>
</body>
</html>
