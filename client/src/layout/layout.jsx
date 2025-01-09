import React from 'react';
import { Flex, Layout, Menu } from 'antd';

const { Header, Content, Footer } = Layout;

export default function AppLayout({ children }) {
    return (
        <Layout style={{ minHeight: '100vh' }}>
            {/* Header */}
            <Header style={{ background: '#001529', padding: 0, display:'flex',justifyContent:'space-between' }}>
                <div style={{ color: '#fff', fontSize: '1.4em', marginLeft:'2em'}}>
                    CTracker
                </div>
                <Flex align='center' justify='space-evenly' gap={30} style={{
                    marginRight:'2em'
                }}>
                    <a>Statistics</a>
                    <a>Reports</a>
                </Flex>
            </Header>
            {/* Content */}
            <Content style={{ margin: '2em', padding: '1.5em', background: '#fff' }}>
                {children}
            </Content>
            {/* Footer */}
            <Footer style={{ textAlign: 'center' }}>
                © CTracker
            </Footer>
        </Layout>
    )
}
