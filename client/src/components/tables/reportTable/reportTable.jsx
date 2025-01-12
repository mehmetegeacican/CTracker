import React, { useCallback, useEffect, useState } from 'react';
import { Button, message, Table } from 'antd';
import { deleteExistingReport, fetchAllReports } from '../../../api/reportApi';
import { useReportsContext } from '../../../contexts/store';



export default function ReportTable() {
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
        
        {
          title: 'Edit',
            dataIndex: 'id',
            key: 'id',
            render: (id) => (
                <Button type="primary" onClick={() => console.log(id)}>
                    Edit
                </Button>
            ),
        },
        
        {
            title: 'Delete',
            dataIndex: 'id',
            key: 'id',
            render: (id) => (
                <Button type="primary" danger onClick={() => handleDelete(id)}>
                    Delete
                </Button>
            ),
        },

    ];
    const { state: { reports }, dispatch } = useReportsContext();



    const handleDelete = async (id) => {
        await deleteExistingReport(id);
        dispatch({type:'TRIGGER'})
    }


    return (
        <Table columns={COLUMNS} dataSource={reports}/>
    )
}
