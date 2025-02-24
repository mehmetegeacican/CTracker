import { message, Modal } from 'antd'
import React, { useCallback, useEffect, useState } from 'react'
import ReportForm from '../../form/reportForm/reportForm'
import { fetchReportById, updateExistingReport } from '../../../api/reportApi';
import { useReportsContext } from '../../../contexts/store';

export default function EditReportModal({ open, successFunction, cancelFunction, reportId, confirmLoading }) {

  const [report, setReport] = useState("");
  const {dispatch} = useReportsContext();

  const handleOk = async () => {
    const res = await updateExistingReport(reportId,{
      report: report
    });
    if (res.status === 400 && res.status) {
      message.error(res.message + "\n  Please make sure the report follows turkish language format.")
    }
    else {
      dispatch({
        type: 'TRIGGER'
      })
      successFunction();
    }
  };
  const handleCancel = async () => {
    cancelFunction();
  };

  const fetchSpecificReport = useCallback(async () => {
    const res = await fetchReportById(reportId);
    if (res) {
      setReport(res.report);
    }
  }, [reportId]);


  useEffect(() => {
    if(open){
      fetchSpecificReport();
    }
  }, [open]);

  return (
    <Modal
      title="Edit Report"
      open={open}
      onOk={handleOk}
      confirmLoading={confirmLoading}
      onCancel={handleCancel}
      width={800}
      style={{ overflowY: 'auto', height: '30em' }}
    >
      <ReportForm setReport={setReport} report={report} />
    </Modal>
  )
}
