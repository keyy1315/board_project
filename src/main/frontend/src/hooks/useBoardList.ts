import { useQuery } from "@tanstack/react-query";
import { BoardList } from "../types/BoardList";
import { getBoardList } from "../api/BoardApi";
import { RequestBoardList } from "../types/RequestBoardList";

export const useBoardList = (requestBoardList: RequestBoardList) => {
  const {
    data: boardList,
    error,
    isLoading,
  } = useQuery<BoardList, Error>({
    queryKey: ["boardList", requestBoardList],
    queryFn: () => getBoardList(requestBoardList),
    initialData: { boards: [], total: 0 },
    enabled: !!requestBoardList, // RequestBoardList가 있을 때만 쿼리 실행
    gcTime: Infinity,  // Changed from cacheTime to gcTime
  });

  if (error) throw error;
  if (isLoading) console.log("loading");

  return { boardList: boardList };
};
