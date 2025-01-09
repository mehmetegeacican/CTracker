import { Flex, Tabs } from 'antd'
import React from 'react'
import CaseTable from '../../components/tables/caseTable'

export default function StatisticsPage() {
  const tabItems = [
    {
      key: '1',
      label: 'Graph View',
      children: <div>Graph View</div>,
    },
    {
      key: '2',
      label: 'Table View',
      children: <CaseTable/>,
    },
  ]
  return (
    <div>
      <h1>Statistics Section</h1>
      <Flex>
        <div>Filtering Section</div>
      </Flex>
      <Flex vertical gap={20}>
          <Tabs defaultActiveKey='1' items={tabItems}></Tabs>
      </Flex>
    </div>
  )
}
