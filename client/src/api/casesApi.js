import axios from "axios";

/**
 * This function returns cases
 * @param {*} query 
 * @returns 
 */
export const fetchAllCases = async (query) => {
    try{
        const res = await axios.get("http://localhost:8080/api/v1/cases/all",{
            params : query
        });
        return res.data;
    } catch(e){
        console.error(e);
        return [];
    }
}