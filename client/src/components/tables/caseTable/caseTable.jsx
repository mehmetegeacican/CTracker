import { Table } from 'antd';
import React from 'react'
import { useReportsContext } from '../../../contexts/store';

export default function CaseTable() {
    const COLUMNS = [
        /*
        {
            title: 'Case ID',
            dataIndex: 'id',
            key: 'id',
        },
        */
        {
            title: 'Location',
            dataIndex: 'reportLocation',
            key: 'reportLocation',
        },
        {
            title: 'Report Date',
            dataIndex: 'reportDate',
            key: 'reportDate',
            render: (text) => {
                const date = new Date(text);
                const day = String(date.getDate()).padStart(2, '0');  // Ensure two digits
                const month = String(date.getMonth() + 1).padStart(2, '0');  // Month is 0-indexed
                const year = date.getFullYear();

                return `${day}.${month}.${year}`;
            },
            defaultSortOrder: 'descend',
            sorter: (a, b) => new Date(b.reportDate) - new Date(a.reportDate)
        },
        {
            title: 'New Case',
            dataIndex: 'newCaseNumber',
            key: 'newCaseNumber',
            sorter: (a, b) => b.newCaseNumber - a.newCaseNumber
        },
        {
            title: 'Death Case',
            dataIndex: 'deathCaseNumber',
            key: 'deathCaseNumber',
            sorter: (a, b) => b.deathCaseNumber - a.deathCaseNumber
        },
        {
            title: 'Discharged Case',
            dataIndex: 'dischargedCaseNumber',
            key: 'dischargedCaseNumber',
            sorter: (a, b) => b.dischargedCaseNumber - a.dischargedCaseNumber
        },
        {
            title: "Report",
            dataIndex: 'reportId',
            key: 'reportId',
        }
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
    ];

    const {state:{cases}} = useReportsContext();

    return (
        <Table
            columns={COLUMNS}
            dataSource={cases}
            pagination={{
                pageSize: 30,
            }}
            scroll={{
                y: 88 * 5,
            }}
        />
    )
}
