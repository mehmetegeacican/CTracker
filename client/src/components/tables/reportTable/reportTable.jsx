import React from 'react';
import { Button, Table } from 'antd';

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
      {
        title: 'Created At',
        dataIndex: 'createdAt',
        key: 'createdAt',
        render: (text) => new Date(text).toLocaleDateString(), // Format the date
        defaultSortOrder: 'descend',
        sorter: (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
      },
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

const data = [
    {
      "id": "677f96937804da46383107f8",
      "report": "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.",
      "createdAt": "2025-01-09T12:27:47.26"
    },
    {
      "id": "677f96937804da46383107f7",
      "report": "19.04.2020 tarihinde izmir için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu. 3 kişin de vefat ettiği öğrenildi.",
      "createdAt": "2025-01-09T12:27:47.26"
    },
    {
      "id": "677f96ae7804da46383107fa",
      "report": "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.",
      "createdAt": "2025-01-09T12:28:14.974"
    },
    {
      "id": "677f97125fe5ec33bb20acc1",
      "report": "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.",
      "createdAt": "2025-01-09T12:29:54.742"
    },
    {
      "id": "677f972a5fe5ec33bb20acc3",
      "report": "Korona virüs salgınında yapılan testlerde 19.04.2020 tarihinde Istanbul da 30 yeni vaka tespit edil. İstanbul da taburcu sayısı 7 kişi . 3 kişi de vefat etti.",
      "createdAt": "2025-01-09T12:30:18.685"
    },
    {
      "id": "677f973d5fe5ec33bb20acc5",
      "report": "19.04.2020 tarihinde Istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu. 3 kişin de vefat ettiği öğrenildi.",
      "createdAt": "2025-01-09T12:30:37.41"
    },
    {
      "id": "677f9b163164f80609ab491c",
      "report": "19.04.2020 tarihinde Istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu. 3 kişin de vefat ettiği öğrenildi.",
      "createdAt": "2025-01-09T12:47:02.593"
    },
    {
      "id": "677f9b27a1b02334635bdca1",
      "report": "19.04.2020 tarihinde Istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu. 3 kişin de vefat ettiği öğrenildi.",
      "createdAt": "2025-01-09T12:47:19.775"
    },
    {
      "id": "677f9b41a1b02334635bdca3",
      "report": "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.",
      "createdAt": "2025-01-09T12:47:45.278"
    },
    {
      "id": "677f9ba92e1367282986ba3c",
      "report": "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.",
      "createdAt": "2025-01-09T12:49:29.952"
    },
    {
      "id": "677f9c3563d6db22622da9d0",
      "report": "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.",
      "createdAt": "2025-01-09T12:51:49.328"
    },
    {
      "id": "677f9c4363d6db22622da9d2",
      "report": "Korona virüs salgınında yapılan testlerde 19.04.2020 tarihinde Istanbul da 30 yeni vaka tespit edil. İstanbul da taburcu sayısı 7 kişi . 3 kişi de vefat etti.",
      "createdAt": "2025-01-09T12:52:03.809"
    },
    {
      "id": "677f9ce963d6db22622da9d4",
      "report": "19.04.2020 tarihinde Istanbul için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 20 yeni vaka tespit edildi. taburcu sayısı ise 7 oldu. 3 kişin de vefat ettiği öğrenildi.",
      "createdAt": "2025-01-09T12:54:49.227"
    }
  ];

export default function ReportTable() {
  return (
    <Table columns={COLUMNS} dataSource={data}/>
  )
}
