import axios from "axios";
import { Category } from "../types/Category";

export const getCategory = async () => {
    const response = await axios.get<Category[]>('/api/common/category');
    return response.data;
}
