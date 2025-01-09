import { Button, Divider, Flex } from 'antd'
import React from 'react'
import ReportTable from '../../components/tables/reportTable/reportTable'

export default function ReportsPage() {
  return (
    <div>
      <h1>Reports Section</h1>
      <Divider/>
      <Flex vertical gap={20}>
          <Flex justify='flex-end'>
              <Button>Add New</Button>
          </Flex>
          <ReportTable/>
      </Flex>
    </div>
  )
}
