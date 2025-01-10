import React, { useState, useEffect } from 'react';
import LineGraph from '../../../../components/graphs/lineGraph/lineGraph'; // Import the LineGraph component
import { useReportsContext } from '../../../../contexts/store';
import { groupBy } from '../../functions/statfunctions';

export default function CaseGraph() {
  const [chartData, setChartData] = useState(null);
  const { state: { cases } } = useReportsContext();

  useEffect(() => {
    const retrieveStats = async () => {

      const groupedData = groupBy(cases, 'reportDate', [
        'deathCaseNumber',
        'newCaseNumber',
        'dischargedCaseNumber',
      ]);

      // Convert grouped data back to an array if needed
      const groupedArray = Object.values(groupedData).sort((a,b) => new Date(a.reportDate) - new Date(b.reportDate));

      // Example: Preparing data for the chart
      const labels = groupedArray.map((item) => new Date(item.reportDate).toLocaleDateString());
      const newCases = groupedArray.map((item) => item.newCaseNumber);
      const deathCases = groupedArray.map((item) => item.deathCaseNumber);
      const dischargedCases = groupedArray.map((item) => item.dischargedCaseNumber);

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

    retrieveStats();
  }, [cases]);

  const options = {
    responsive: true,
    maintainAspectRatio: false,
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
        <div style={{ height: '30em', width: '100%' }}>
          <LineGraph data={chartData} options={options} />
        </div>

      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}
