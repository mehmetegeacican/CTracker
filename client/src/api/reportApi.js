import axios from 'axios';
const reportUrl = import.meta.env.VITE_API_REPORT_URL;
/**
 * Fetches All the Reports
 * @returns 
 */
export const fetchAllReports = async () => {
    try{
        const res = await axios.get(`${reportUrl}/all`);
        return res.data;
    } catch(e){
        console.error(e);
        return [];
    }
};

/**
 * Fetch By Id
 * @param {*string} reportId 
 * @returns 
 */
export const fetchReportById = async (reportId) => {
    try{
        const res = await axios.get(`${reportUrl}/${reportId}`);
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
        const res = await axios.post(`${reportUrl}`,requestObj);
        return res.data;
    } catch(e){
        if (e.response) {
            if (e.response.status === 400) {
                return {
                    status : 400,
                    message: e.response.data.message || "Could not add new report",
                };
            }
        } else {
            return {
                message: "Could not create the report: " + e.message,
            };
        }
    }
};

/**
 * Updates an Existing Report
 * @param {*string} id 
 * @param {*Object} requestObj 
 */
export const updateExistingReport = async (id,requestObj) => {
    try{
        const res = await axios.put(`${reportUrl}/${id}`,requestObj);
        return res.data;
    } catch(e){
        if (e.response) {
            if (e.response.status === 400) {
                return {
                    status : 400,
                    message: e.response.data.message || "Could not add new report",
                };
            }
        } else {
            return {
                message: "Could not create the report: " + e.message,
            };
        }
    }
};

/**
 * Delete Reports
 * @param {*} reportId 
 * @returns 
 */
export const deleteExistingReport = async (reportId) => {
    try{
        const res = await axios.delete(`${reportUrl}/${reportId}`)
        return res.data;
    } catch(e){
        console.error(e);
        return {
            message:"Could not delete the report " + e.message
        };
    }
}