<%@ page import="Persistence.PersistenceHandler" %>
<%@ page language="java" contentType="text/html; ISO-8859-1" %>
<style><%@include file="beer.css"%></style>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Beer machine</title>
    <link rel="stylesheet" href="beer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

</head>

<body>
<div class="view">
    <div class="main-header">
        <label class="main-header-text">B&R Beer Machine</label>
    </div>

    <div class="login-header">
        <label class="login_header_text">Username</label>
    </div>

    <div class="container">

        <div class="flex-item column">
            <div class="category-header">
                <label class="category-label">Production</label>
            </div>

            <div>
                <a href="/production"><button class="production-button button">Production</button></a>
            </div>
        </div>

        <div class="flex-item column">
            <div class="ingredient-box">
                <p>Barley: <%  %></p>
                <p>Hops: <%  %></p>
                <p>Malt: <%  %></p>
                <p>Wheat: <%  %></p>
                <p>Yeast: <%  %></p>
                <button class="refill-button button">Refill</button>
            </div>
        </div>
    </div>

    <div class="sensors">
        <div class="sensor-sub">
            <div class="sensor-header">
                <label class="sensor-label">Sensors</label>
            </div>

            <div class="sensor-box">
                <div class="sensor-box-section">
                    <p>
                        <label>Temperature: </label>
                        <label>
                            <% out.println("temp sensor"); %>
                        </label>
                    </p>
                    <p>
                        <label>Humidity: </label>
                        <label>
                            <% out.println("humidity sensor"); %>
                        </label>
                    </p>
                    <p>
                        <label>Vibration: </label>
                        <label>
                            <% out.println("Vibration sensor"); %>
                        </label>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>