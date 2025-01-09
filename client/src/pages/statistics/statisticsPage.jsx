import { Flex } from 'antd'
import React from 'react'
import CaseTable from '../../components/tables/caseTable'

export default function StatisticsPage() {
  return (
    <div>
      <h1>Statistics Section</h1>
      <Flex vertical gap={20}>
          <CaseTable/>
      </Flex>
    </div>
  )
}
