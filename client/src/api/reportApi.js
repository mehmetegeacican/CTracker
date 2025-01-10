import axios from 'axios';

/**
 * Fetches All the Reports
 * @returns 
 */
export const fetchAllReports = async () => {
    try{
        const res = await axios.get("http://localhost:8080/api/v1/reports/all");
        return res.data;
    } catch(e){
        console.error(e);
        return [];
    }
};

/**
 * Create New Report
 * @param {*Object} requestObj 
 * @returns 
 */
export const createNewReport = async (requestObj) => {
    try{
        const res = await axios.post("http://localhost:8080/api/v1/reports",requestObj);
        return res.data;
    } catch(e){
        console.error(e);
        return {
            message:"Could not create the report " + e.message
        };
    }
};

/**
 * Delete Reports
 * @param {*} reportId 
 * @returns 
 */
export const deleteExistingReport = async (reportId) => {
    try{
        const res = await axios.delete(`http://localhost:8080/api/v1/reports/${reportId}`)
        return res.data;
    } catch(e){
        console.error(e);
        return {
            message:"Could not delete the report " + e.message
        };
    }
}