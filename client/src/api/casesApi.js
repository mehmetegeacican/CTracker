import axios from "axios";
const caseUrl = import.meta.env.VITE_API_CASE_URL;
/**
 * This function returns cases
 * @param {*} query 
 * @returns 
 */
export const fetchAllCases = async (query) => {
    try{
        const res = await axios.get(`${caseUrl}/all`,{
            params : query
        });
        return res.data;
    } catch(e){
        console.error(e);
        return [];
    }
}