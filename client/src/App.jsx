import { useState } from 'react'
import { Button } from 'antd';
import AppLayout from './layout';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import ReportsPage from './pages/reports';
import StatisticPage from './pages/statistics';
import { ReportProvider } from './contexts/reportContext';
import { CaseProvider } from './contexts/caseContext';

function App() {
  return (
    <BrowserRouter>
      <ReportProvider>
        <CaseProvider>
          <AppLayout>
            <Routes>
              <Route path="/" element={<Navigate to="/statistics" />} />
              <Route path="/statistics" element={<StatisticPage />} />
              <Route path="/reports" element={<ReportsPage />} />
            </Routes>
          </AppLayout>
        </CaseProvider>
      </ReportProvider>
    </BrowserRouter>
  )
}

export default App
