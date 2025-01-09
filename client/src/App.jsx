import { useState } from 'react'
import { Button } from 'antd';
import AppLayout from './layout';

function App() {
  return (
    <AppLayout>
      <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>The Main Page</h1>
      <Button type="primary">Click Me</Button>
    </div>
    </AppLayout>
  )
}

export default App
