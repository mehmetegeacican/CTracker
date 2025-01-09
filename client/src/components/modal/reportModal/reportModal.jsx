import { Modal } from 'antd'
import React, { useState } from 'react'

export default function ReportModal({open,successFunction,cancelFunction,confirmLoading}) {
    

    const handleOk = async () => {
        successFunction();
    }

    const handleCancel = async () => {
        cancelFunction();
    }

    return (
        <Modal
            title="Create New Report"
            open={open}
            onOk={handleOk}
            confirmLoading={confirmLoading}
            onCancel={handleCancel}
        >
            
        </Modal>
    )
}
