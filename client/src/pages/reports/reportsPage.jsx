import { Button, Flex } from 'antd'
import React from 'react'
import ReportTable from '../../components/tables/reportTable/reportTable'

export default function ReportsPage() {
  return (
    <div>
      <h1>Reports Page</h1>
      <p>This is the reports page content.</p>
      <Flex vertical gap={20}>
          <Flex justify='flex-end'>
              <Button>Add New</Button>
          </Flex>
          <ReportTable/>
      </Flex>
    </div>
  )
}
