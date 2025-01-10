import { message, Modal } from 'antd'
import React, { useState } from 'react'
import ReportForm from '../../form/reportForm/reportForm';
import { createNewReport } from '../../../api/reportApi';
import { useReportsContext } from '../../../contexts/reportContext';
import { useCasesContext } from '../../../contexts/caseContext';

export default function ReportModal({ open, successFunction, cancelFunction, confirmLoading }) {

    const [report, setReport] = useState("");
    const {dispatch} = useReportsContext();
    const {caseDispatch} = useCasesContext(); 

    const handleOk = async () => {
        
        const res = await createNewReport({
            report:report
        });
        if(res.message === "Could not create the report Request failed with status code 400"){
            message.error("Could not add the report")
        }
        else{
            dispatch({
                type:'TRIGGER'
            })
            caseDispatch({
                type:'TRIGGER_CASES'
            })
            successFunction();
        }
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
            width={800}
            style={{overflowY:'auto', height:'30em'}}
        >
            <ReportForm setReport={setReport}/>
        </Modal>
    )
}
