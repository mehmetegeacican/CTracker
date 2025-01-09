import React from 'react';
import { Table } from 'antd';

const COLUMNS = [
    {
        title: 'Report ID',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: 'Report',
        dataIndex: 'title',
        key: 'title',
      },
      {
        title: 'Created At',
        dataIndex: 'createdAt',
        key: 'createdAt',
        render: (text) => new Date(text).toLocaleDateString(), // Format the date
      },
      {
        title: 'Edit',
        dataIndex: 'edit',
        key: 'edit',
      },
      {
        title: 'Delete',
        dataIndex: 'delete',
        key: 'delete',
      },
]

export default function ReportTable() {
  return (
    <Table columns={COLUMNS} dataSource={[]}/>
  )
}
