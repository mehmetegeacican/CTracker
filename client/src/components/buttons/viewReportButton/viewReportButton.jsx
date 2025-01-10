import { Button, Modal } from "antd";
import { useState } from "react";

export default function ViewReportButton({ report }){
    const [isModalVisible, setIsModalVisible] = useState(false);

    const handleOpenModal = () => {
        setIsModalVisible(true);
    };

    const handleCloseModal = () => {
        setIsModalVisible(false);
    };

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
                <p>{report}</p>
            </Modal>
        </>
    );
};
