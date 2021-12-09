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

            <div class="beer-choice">
                <label for="beer_selection" class="choice">Choose beer type:</label><br>
                <select id="beer_selection" name="beer_selection" class="beer-type">
                    <option value="pilsner">Pilsner</option>
                    <option value="wheat">Wheat</option>
                    <option value="ipa">IPA</option>
                    <option value="stout">Stout</option>
                    <option value="ale">Ale</option>
                    <option value="alcohol_free">Alcohol Free</option>
                    <%  %>
                </select>
            </div>
            <div class="production-box">
                <div class="production-box">
                    <div class="production-started-box">
                        <div class="info-grid-left">
                            <p>
                                <label>Product amount: <% out.println(); %></label>
                            </p>
                            <p>
                                <label>Product type: <% out.println(); %></label>
                            </p>
                            <p>
                                <label>Production speed: <% out.println(); %></label>
                            </p>
                        </div>
                        <div class="info-grid-right">
                            <p>
                                <label>Total good: <% out.println(); %></label>
                            </p>
                            <p>
                                <label>Total bad: <% out.println(); %></label>
                            </p>
                            <p>
                                <label>Efficiency: <% out.println(); %></label>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="production-buttons">
                    <button class="start-button button" type="submit">Start</button>
                    <button class="stop_button button" type="submit">Stop</button>
                    <button class="pause_button button" type="submit">Pause</button>
                    <button class="reset_button button" type="submit">Reset</button>
                    <a href="/">
                        <button class="back-link button">Back</button>
                    </a>
                </div>
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
