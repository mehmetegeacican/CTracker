import React, { useCallback, useEffect, useState } from 'react';
import { Button, Table } from 'antd';
import { fetchAllReports } from '../../../api/reportApi';

const COLUMNS = [
    /*
      {
        title: 'Report ID',
        dataIndex: 'id',
        key: 'id',
      },
    */
      {
        title: 'Report',
        dataIndex: 'report',
        key: 'title',
      },
      /*
      {
        title: 'Created At',
        dataIndex: 'createdAt',
        key: 'createdAt',
        render: (text) => new Date(text).toLocaleDateString(), // Format the date
        //defaultSortOrder: 'descend',
        //sorter: (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
      },
      */
      /*
      {
        title: 'Edit',
        dataIndex: 'edit',
        key: 'edit',
      },
      */
      {
        title: 'Delete',
        dataIndex: 'id',
        key: 'id',
        render: (id) => (
          <Button type="primary" danger onClick={() => console.log(id)}>
            Delete
          </Button>
        ),
      },
      
];

export default function ReportTable() {
  const [reports,setReports] = useState(false);
  const fetchReports = useCallback(async () => {
      const result = await fetchAllReports();
      let updatedRes = result.map((res) => {
        return {key:res.id,...res};
      });
      setReports(updatedRes);
  },[]);

  useEffect(() => {
    fetchReports();
  },[]);
  return (
    <Table columns={COLUMNS} dataSource={reports}/>
  )
}
