import { Flex, Select, Tabs, DatePicker, Button, Divider } from 'antd'
import React, { useEffect, useState } from 'react'
import CaseTable from '../../components/tables/caseTable';
import { CITIES  as cities} from '../../assets/cities';
import { useReportsContext } from '../../contexts/store';
import CaseGraph from './components/caseGraph/caseGraph';

export default function StatisticsPage() {
  const tabItems = [
    {
      key: '1',
      label: 'Graph View',
      children: <CaseGraph/>,
    },
    {
      key: '2',
      label: 'Table View',
      children: <CaseTable />,
    },
  ];
  const { RangePicker } = DatePicker;

  const [selectedCity, setSelectedCity] = useState("all");
  const [dateRange, setDateRange] = useState([]);
  const {dispatch} = useReportsContext();

  const handleCityChange = (value) => {
    setSelectedCity(value);
  };

  const handleDateChange = (dates, dateStrings) => {
    setDateRange(dateStrings); // Array of formatted date strings [start, end]
  };

  const handleFilter = () => {
    dispatch({type:'SET_CITY', payload: selectedCity});
    dispatch({type:'SET_DATE_RANGE',payload:dateRange})
    dispatch({type:'TRIGGER'});
  };

  useEffect(() => {
    console.log(dateRange);
  },[dateRange]);

  return (
    <div>
      <h1>Statistics Section</h1>
      <Divider/>
      <Flex vertical gap={10}>
        
        <Flex justify='flex-end' gap={20}>
          <Select
            placeholder="Select a city"
            onChange={handleCityChange}
            style={{ width: '30%' }}
            allowClear
            defaultValue={'all'}
          >
            {cities.map((city,index) => {
              return(
                <Option key={index} value={city.toLowerCase()}>{city}</Option>
              )
            })}
            
          </Select>
          <RangePicker
                format="DD.MM.YYYY"
                onChange={handleDateChange}
                style={{ width: '30%' }}
            />
          <Button type="primary" onClick={handleFilter}> Go </Button>
        </Flex>
      </Flex>
      <Flex vertical gap={20}>
        <Tabs defaultActiveKey='1' items={tabItems}></Tabs>
      </Flex>
    </div>
  )
}
