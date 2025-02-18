import { useQuery } from "@tanstack/react-query";
import { useRecoilState } from "recoil";
import { useEffect } from "react";
import { Category } from "../types/Category";
import { getCategory } from "../api/CategoryApi";
import { CategoryState } from "../store/CategoryState"

export const useCategory = () => {
  const [category, setCategory] = useRecoilState(CategoryState);

  const { data, error, isLoading } = useQuery<Category[], Error>({
    queryKey: ["category"],
    queryFn: getCategory,
  });

  useEffect(() => {
    if (data) setCategory(data);
  }, [data, setCategory]);

  
  if(error) throw error;
  return {category, isLoading}
};
