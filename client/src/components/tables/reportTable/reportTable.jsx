import React, { useCallback, useEffect, useState } from 'react';
import { Button, message, Table } from 'antd';
import { deleteExistingReport, fetchAllReports } from '../../../api/reportApi';
import { useReportsContext } from '../../../contexts/store';
import EditReportModal from '../../modal/editReportModal/editReportModal';
import DeleteReportModal from '../../modal/deleteReportModal';



export default function ReportTable() {
  const [openEditModal, setOpenEditModal] = useState(false);
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const [selectedReportId, setSelectedReportId] = useState("");
  const [confirmLoading,setConfirmLoading] = useState(false);

  const handleSubmit = async () => {
    setConfirmLoading(true);
    setTimeout(() => {
      setOpenDeleteModal(false);
      setOpenEditModal(false);
      setConfirmLoading(false);
    }, 100);
  }

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
        <Button type="primary" onClick={() => {
          setSelectedReportId(id);
          setOpenEditModal(true)
        }}>
          Edit
        </Button>
      ),
    },
    {
      title: 'Delete',
      dataIndex: 'id',
      key: 'id',
      render: (id) => (
        <Button type="primary" danger onClick={() => {
          setSelectedReportId(id);
          setOpenDeleteModal(true)
        }}>
          Delete
        </Button>
      ),
    },

  ];
  const { state: { reports }, dispatch } = useReportsContext();

  return (
    <div>
      <Table columns={COLUMNS} dataSource={reports} />
      <EditReportModal
        open={openEditModal}
        successFunction={handleSubmit}
        cancelFunction={() => setOpenEditModal(false)}
        reportId={selectedReportId}
      />
      <DeleteReportModal
        open={openDeleteModal}
        successFunction={handleSubmit}
        cancelFunction={() => setOpenDeleteModal(false)}
        reportId={selectedReportId}
      />
    </div>
  )
}
