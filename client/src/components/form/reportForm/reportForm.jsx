import { Form, Input } from 'antd'
import React from 'react'

export default function ReportForm({ setReport }) {
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
        />
      </Form.Item>
    </Form>
  )
}
