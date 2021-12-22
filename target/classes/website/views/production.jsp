<%@ page import="Persistence.PersistenceHandler" %>
<%@ page import="Domain.BeerType" %>
<%@ page import="java.util.List" %>
<%@ page import="Domain.DomainHandler" %>
<%@ page import="Domain.Subscription" %>
<%@ page language="java" contentType="text/html; ISO-8859-1" %>
<style><%@include file="../css/beer.css"%></style>
<script><%@include file="../js/beer.js"%></script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Beer machine</title>
    <link rel="stylesheet" href="../css/beer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <% String productType = request.getParameter("beer_selection"); %>

    <%! PersistenceHandler persistenceHandler = new PersistenceHandler(); %>
    <%! DomainHandler domainHandler = new DomainHandler(); %>
    <%! Subscription subscription = new Subscription(); %>
    <% List<BeerType> beerTypes = persistenceHandler.queryAllBeerTypes(); %>
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
                    <% if (beerTypes != null) { for (BeerType bt: beerTypes) { %>
                        <option><% out.println(bt.getType()); %></option>
                    <% }} %>
                </select>
            </div>
            <div class="production-box">
                <div class="production-box">
                    <div class="production-started-box">
                        <div class="info-grid-left">
                            <p id="amountPara">
                                <label>Product amount: <% out.println(); %></label>
                            </p>
                            <p id="typePara">
                                <label id="product_label">Product type: </label>
                                <label id="product"></label>
                            </p>
                            <p id="speedPara">
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
                    <button id="startButton" class="start-button button" type="button" onclick="appendSelectedProduct(); <% // domainHandler.StartMachine(); %>">Start</button>
                    <button class="stop_button button" type="button"
                        onclick="resetProductionPage() <% domainHandler.StopMachine(); %>">Stop</button>
                    <button class="pause_button button" type="button"
                        onclick="<%  %>">Pause</button>
                    <button class="reset_button button" type="button"
                        onclick="resetProductionPage(); <% domainHandler.ResetMachine(); %>">Reset</button>
                    <a href="/">
                        <button class="back-link button">Back</button>
                    </a>
                </div>
            </div>
        </div>

        <div class="flex-item column">
            <div class="ingredient-box">
                <p>Barley: <% subscription.barley(); %></p>
                <p>Hops: <% subscription.hops(); %></p>
                <p>Malt: <% subscription.malt(); %></p>
                <p>Wheat: <% subscription.wheat(); %></p>
                <p>Yeast: <% subscription.yeast(); %></p>
                <button class="refill-button button" onclick="executeRefill()">Refill</button>
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
                            <% subscription.temperature(); %>
                        </label>
                    </p>
                    <p>
                        <label>Humidity: </label>
                        <label>
                            <% subscription.humidity(); %>
                        </label>
                    </p>
                    <p>
                        <label>Vibration: </label>
                        <label>
                            <% subscription.vibration(); %>
                        </label>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
