<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Company Analysis</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Company Analysis</h1>
        <form id="dateForm">
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" name="startDate" required>
            <label for="endDate">End Date:</label>
            <input type="date" id="endDate" name="endDate" required>
            <button type="submit">Submit</button>
        </form>
        <div id="chartsContainer" class="charts-container">
            <!-- Charts will be appended here -->
        </div>
    </div>

    <script>
        document.getElementById('dateForm').addEventListener('submit', function(event) {
            event.preventDefault();

            const startDate = document.getElementById('startDate').value;
            const endDate = document.getElementById('endDate').value;

            // Simulate fetching data and rendering charts
            renderCharts(startDate, endDate);
        });

        function renderCharts(startDate, endDate) {
            const chartsContainer = document.getElementById('chartsContainer');
            chartsContainer.innerHTML = ''; // Clear existing charts

            // Simulate fetching data for company 1
            const company1Data = generateRandomData(startDate, endDate);
            renderChart('Company 1', company1Data, chartsContainer);

            // Simulate fetching data for company 2
            const company2Data = generateRandomData(startDate, endDate);
            renderChart('Company 2', company2Data, chartsContainer);

            // Repeat for other companies if needed
        }

        function renderChart(companyName, data, container) {
            // Here you can use your preferred charting library to create the chart
            // For simplicity, let's just generate a simple bar chart using HTML

            const chartDiv = document.createElement('div');
            chartDiv.classList.add('chart');

            chartDiv.innerHTML = `
                <h2>${companyName}</h2>
                <canvas id="${companyName}-chart"></canvas>
            `;

            container.appendChild(chartDiv);

            // Simulate generating the chart using Chart.js
            const ctx = document.getElementById(`${companyName}-chart`).getContext('2d');
            const chart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'],
                    datasets: [{
                        label: 'Revenue',
                        data: data,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }

        function generateRandomData(startDate, endDate) {
            // Simulate generating random data for a period
            const data = [];
            const start = new Date(startDate);
            const end = new Date(endDate);

            let currentDate = new Date(start);

            while (currentDate <= end) {
                data.push(Math.floor(Math.random() * 1000)); // Generate random revenue data
                currentDate.setDate(currentDate.getDate() + 7); // Increment by 1 week
            }

            return data;
        }
    </script>
</body>
</html>