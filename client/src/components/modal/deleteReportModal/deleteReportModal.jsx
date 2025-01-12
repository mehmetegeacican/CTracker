import { Modal } from 'antd'
import React from 'react'
import { useReportsContext } from '../../../contexts/store';
import { deleteExistingReport } from '../../../api/reportApi';

export default function DeleteReportModal({ open, successFunction, cancelFunction, confirmLoading , reportId}) {

    const {dispatch} = useReportsContext();

    const handleOk = async () => {
        await deleteExistingReport(reportId);
        dispatch({ type: 'TRIGGER' })
        successFunction();
    }

    const handleCancel = async () => {
        cancelFunction();
    }

    return (
        <Modal
            title="Delete Report"
            open={open}
            onOk={handleOk}
            confirmLoading={confirmLoading}
            okType='danger'
            okText="Delete"
            onCancel={handleCancel}
            width={500}
            style={{ overflowY: 'auto', height: '30em' }}
        >
            Warning! The following report is about to be deleted.
            Are you sure?
        </Modal>
    )
}
