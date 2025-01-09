import { Button, Divider, Flex } from 'antd'
import React, { useState } from 'react'
import ReportTable from '../../components/tables/reportTable/reportTable';
import ReportModal from '../../components/modal/reportModal';

export default function ReportsPage() {
  const [openCreateForm, setOpenCreateForm] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);

  const handleSubmit = async () => {
    setConfirmLoading(true);
    setTimeout(() => {
      setOpenCreateForm(false);
      setConfirmLoading(false);
    }, 100);
  }
  return (
    <div>
      <h1>Reports Section</h1>
      <Divider />
      <Flex vertical gap={20}>
        <Flex justify='flex-end'>
          <Button onClick={() => setOpenCreateForm(true)}>Add New</Button>
        </Flex>
        <ReportTable />
      </Flex>
      {/*Report Modal */}
      <ReportModal
        open={openCreateForm}
        successFunction={() => handleSubmit()}
        cancelFunction={() => setOpenCreateForm(false)}
        confirmLoading={confirmLoading}
      />
    </div>
  )
}
