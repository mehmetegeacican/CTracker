import { Button, Modal, Spin } from "antd";
import { Suspense, useCallback, useEffect, useState } from "react";
import { fetchReportById } from "../../../api/reportApi";

const LoadingSpinner = () => <Spin tip="Loading..." size="large" />;

export default function ViewReportButton({ reportId }) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [report, setReport] = useState("");


    const fetchReport = useCallback(async () => {
        const result = await fetchReportById(reportId);
        setReport(result.report);
    }, [reportId]);

    const handleOpenModal = () => {
        setIsModalVisible(true);
    };

    const handleCloseModal = () => {
        setIsModalVisible(false);
    };

    useEffect(() => {
        if (isModalVisible) {
            fetchReport();
        }
    }, [isModalVisible]);

    const ReportDetail = ({report}) => {
        if(!report || report === ""){
            throw new Promise(() => {});
        }
        else {
            return (
                <p>{report}</p>
            )
        }
    }

    return (
        <>
            <Button type="primary" onClick={handleOpenModal}>
                View
            </Button>
            <Modal
                title="Report Details"
                open={isModalVisible}
                onOk={handleCloseModal}
                onCancel={handleCloseModal}
                footer={[
                    <Button key="close" type="primary" onClick={handleCloseModal}>
                        Close
                    </Button>,
                ]}
            >
                <Suspense fallback={<LoadingSpinner />}>
                    <ReportDetail report={report}/>
                </Suspense>

            </Modal>
        </>
    );
};
