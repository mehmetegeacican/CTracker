import { useState } from 'react'
import { Button } from 'antd';
import AppLayout from './layout';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import ReportsPage from './pages/reports';
import StatisticPage from './pages/statistics';
import { ReportProvider } from './contexts/reportContext';

function App() {
  return (
    <BrowserRouter>
      <ReportProvider>
        <AppLayout>
          <Routes>
            <Route path="/" element={<Navigate to="/statistics" />} />
            <Route path="/statistics" element={<StatisticPage />} />
            <Route path="/reports" element={<ReportsPage />} />
          </Routes>
        </AppLayout>
      </ReportProvider>
    </BrowserRouter>
  )
}

export default App
