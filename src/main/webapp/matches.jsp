<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="./">Home</a>
                <a class="nav-link" href="matches">Matches</a>
            </nav>
        </div>
    </section>
</header>

<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="container">
            <h1>Matches</h1>
            <div class="input-container">

                <form class="form-matches" method="get" action="${pageContext.request.contextPath}/matches">
<%--                    <input type="hidden" name="page" value="1">--%>
                    <input class="input-filter" placeholder="Filter by name" type="text" name="name" />
                </form>

                <div>
                    <a href="${pageContext.request.contextPath}/matches?page=1">
                        <button class="btn-filter">Reset Filter</button>
                    </a>
                </div>
            </div>
        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${matches}">
                <tr>
                    <td>${match.playerOne}</td>
                    <td>${match.playerTwo}</td>
                    <td><span class="winner-name-td">${match.winner}</span></td>
                </tr>
            </c:forEach>
        </table>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/matches?page=${currentPage - 1}">Prev</a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalMatches / pageSize + (totalMatches % pageSize > 0 ? 1 : 0)}">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <span>${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/matches?page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage < totalMatches / pageSize + (totalMatches % pageSize > 0 ? 1 : 0)}">
                    <a href="${pageContext.request.contextPath}/matches?page=${currentPage + 1}">Next</a>
                </c:if>
            </div>

    </div>


<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>

        <style>
            .pagination a,
            .pagination span {
                margin: 0 5px; /* Отступ между кнопками */
                text-decoration: none; /* Убрать подчеркивание */
                padding: 5px 10px; /* Внутренние отступы для увеличения размера */
                border: 1px solid #ccc; /* Граница для кнопок */
                border-radius: 4px; /* Скругление углов */
                background-color: #f9f9f9; /* Светлый фон для кнопок */
                color: #333; /* Цвет текста */
            }

            .pagination a:hover {
                background-color: #ddd; /* Фон при наведении */
            }

            .pagination span {
                font-weight: bold; /* Выделение активной страницы */
                background-color: #ccc; /* Фон активной кнопки */
            }
        </style>


</body>
</html>
