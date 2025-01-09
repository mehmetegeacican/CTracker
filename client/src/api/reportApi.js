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
}