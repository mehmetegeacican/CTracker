import { Modal } from 'antd'
import React, { useState } from 'react'
import ReportForm from '../../form/reportForm/reportForm'

export default function EditReportModal({ open, successFunction, cancelFunction, reportId, confirmLoading }) {

  const [report, setReport] = useState("");

  const handleOk = async () => {
    successFunction();
  }
  const handleCancel = async () => {
    cancelFunction();
  }

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
      <ReportForm setReport={setReport} />
    </Modal>
  )
}
