// ReportContext.jsx
import React, { createContext, useReducer, useContext, useEffect } from 'react';
import { fetchAllReports } from '../api/reportApi';
import { fetchAllCases } from '../api/casesApi';

const initialState = {
    reports: [],
    cases: [],
    trigger: 0,
    selectedCity:null,
    dateRange: []
};

const SET_REPORTS = 'SET_REPORTS';
const TRIGGER = 'TRIGGER'
const SET_CASES = "SET_CASES";
const SET_CITY = "SET_CITY";
const SET_DATE_RANGE = "SET_DATE_RANGE";

const reportsReducer = (state, action) => {
    switch (action.type) {
        case SET_REPORTS:
            return {
                ...state,
                reports: action.payload,
            };
        case SET_CASES:
            return {
                ...state,
                cases: action.payload
            };
        case TRIGGER:
            return {
                ...state,
                trigger: state.trigger + 1,
            };
        case SET_CITY:
            return {
                ...state,
                selectedCity:action.payload
            }
        case SET_DATE_RANGE: 
            return {
                ...state,
                dateRange: action.payload
            }
        default:
            return state;
    }
};

const ReportContext = createContext();

export const ReportProvider = ({ children }) => {
    const [state, dispatch] = useReducer(reportsReducer, initialState);

    useEffect(() => {
        const fetchReports = async () => {
            try {
                let response = await fetchAllReports();
                response = response.map((item) => {
                    return {
                        key: item.id,
                        ...item
                    }
                }).sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                dispatch({
                    type: 'SET_REPORTS',
                    payload: response,
                });
            } catch (error) {
                console.error('Error fetching reports:', error);
            }
        };
        const fetchCases = async () => {
            try {
                let query = {};
                if(state.selectedCity && state.selectedCity !== "all"){
                    query.reportLocation = state.selectedCity
                }
                if(state.dateRange && state.dateRange.length === 2 && !state.dateRange.includes('')){
                    query.startDate = state.dateRange[0];
                    query.endDate = state.dateRange[1];
                }
                let response = await fetchAllCases(query);
                response = response.map((item) => {
                    return {
                        key: item.id,
                        ...item
                    }
                }).sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                dispatch({
                    type: 'SET_CASES',
                    payload: response,
                });
            } catch (error) {
                console.error('Error fetching reports:', error);
            }
        };
        fetchReports();
        fetchCases();
    }, [state.trigger]);

    return (
        <ReportContext.Provider value={{ state, dispatch }}>
            {children}
        </ReportContext.Provider>
    );
};

export const useReportsContext = () => {
    const context = useContext(ReportContext);
    if (!context) {
        throw new Error('useReportsContext must be used within a ReportProvider');
    }
    return context;
};
