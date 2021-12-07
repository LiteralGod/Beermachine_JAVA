<%@ page import="Persistence.PersistenceHandler" %>
<%@ page language="java" contentType="text/html; ISO-8859-1" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="beer.css">
</head>

<body>
    <h1>Beermachine</h1>
    <% PersistenceHandler handler = new PersistenceHandler(); %>
        <p>
            <% out.println(handler.getBeerType(2)); %>
        </p>
</body>
</html>