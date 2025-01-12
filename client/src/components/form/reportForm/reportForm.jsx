import { Form, Input } from 'antd'
import React, { useEffect } from 'react'

export default function ReportForm({ report,setReport }) {

  return (
    <Form>
      <Form.Item
        rules={[{ required: true, message: 'Please input the report description!' }]}
      >
        <Input.TextArea 
          rows={5} 
          onChange={(e) => setReport(e.target.value)} 
          placeholder='Enter your report here'
          style={{marginTop:10}}
          value={report ?? ""}
        />
      </Form.Item>
    </Form>
  )
}
