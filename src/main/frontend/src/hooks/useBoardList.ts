import { useQuery } from "@tanstack/react-query";
import { BoardList } from "../types/BoardList";
import { getBoardList } from "../api/BoardApi";
import { RequestBoardList } from "../types/request/RequestBoardList";

export const useBoardList = (requestBoardList: RequestBoardList) => {
  const {
    data: boardList,
    error,
    isLoading,
  } = useQuery<BoardList, Error>({
    queryKey: ["boardList", requestBoardList],
    queryFn: () => getBoardList(requestBoardList),
    initialData: { boards: [], total: 0 },
    enabled: !!requestBoardList, 
    gcTime: Infinity,  
  });

  if (error) throw error;
  if (isLoading) console.log("loading");

  return { boardList: boardList };
};
