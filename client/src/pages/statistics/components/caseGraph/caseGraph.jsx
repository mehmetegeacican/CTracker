import React, { useState, useEffect } from 'react';
import LineGraph from '../../../../components/graphs/lineGraph/lineGraph'; // Import the LineGraph component
import { useReportsContext } from '../../../../contexts/store';

export default function CaseGraph() {
  const [chartData, setChartData] = useState(null);
  const {state:{cases}} = useReportsContext();

  useEffect(() => {
    const fetchData = async () => {
      // Process data for the graph
      const labels = cases.map((item) => new Date(item.reportDate).toLocaleDateString());
      const newCases = cases.map((item) => item.newCaseNumber);
      const deathCases = cases.map((item) => item.deathCaseNumber);
      const dischargedCases = cases.map((item) => item.dischargedCaseNumber);

      // Set data for the graph
      setChartData({
        labels,
        datasets: [
          {
            label: 'New Cases',
            data: newCases,
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            tension: 0.4,
          },
          {
            label: 'Death Cases',
            data: deathCases,
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            tension: 0.4,
          },
          {
            label: 'Discharged Cases',
            data: dischargedCases,
            borderColor: 'rgb(59, 177, 112)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            tension: 0.4,
          },
        ],
      });
    };

    fetchData();
  }, [cases]);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Cases Over Time',
      },
    },
  };

  return (
    <div>
      {chartData ? (
        <LineGraph data={chartData} options={options} />
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}
