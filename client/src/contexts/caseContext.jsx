// ReportContext.jsx
import React, { createContext, useReducer, useContext, useEffect } from 'react';
import { fetchAllReports } from '../api/reportApi';
import { fetchAllCases } from '../api/casesApi';

const initialState = {
    cases: [],
    stats: [],
    triggerCase: 0
};

const SET_CASES = 'SET_CASES';
const TRIGGER_CASE = 'TRIGGER_CASE'

const casereducer = (state, action) => {
    switch (action.type) {
        case SET_CASES:
            return {
                ...state,
                cases: action.payload,
            };
        case TRIGGER_CASE:
            return {
                ...state,
                triggerCase: state.trigger + 1,
            };
        default:
            return state;
    }
};

const CaseContext = createContext();

export const CaseProvider = ({ children }) => {
    const [state, dispatch] = useReducer(casereducer, initialState);

    useEffect(() => {
        const fetchCases = async () => {
            try {
                let response = await fetchAllCases({});
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
        fetchCases();
    }, [state.triggerCase]);

    return (
        <CaseContext.Provider value={{ state, dispatch }}>
            {children}
        </CaseContext.Provider>
    );
};

export const useCasesContext = () => {
    const context = useContext(CaseContext);
    if (!context) {
        throw new Error('useCasesContext must be used within a CaseProvider');
    }
    return context;
};
