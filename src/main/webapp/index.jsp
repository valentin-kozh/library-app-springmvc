<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        /* Стили для кнопки */
        .styled-button {
            background-color: #ed92bf; /* Пастельный цвет (пример) */
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: block;
            margin: 0 auto; /* Центрирование горизонтально */
            font-size: 16px;
            border-radius: 5px; /* Закругленные края */
            cursor: pointer;
            margin-top: 20px; /* Отступ сверху */
            width: 200px
        }

        /* Стили для контейнера */
        .button-container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
    </style>
</head>
<body>
<div class="button-container">
    <form method="GET" action="people">
        <input class="styled-button" type="submit" value="Список читателей">
    </form>
    <form method="GET" action="books">
        <input class="styled-button" type="submit" value="Список книг">
    </form>
</div>
</body>
</html>
