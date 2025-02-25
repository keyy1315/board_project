import { useRecoilState } from "recoil";
import { RequestBoardListState } from "../store/BoardListState";

interface PaginateProps {
  total: number;
}

export default function Paginate({ total }: PaginateProps) {
  const [request, setRequest] = useRecoilState(RequestBoardListState);
  const totalPages = Math.ceil(total / request.page_size);
  const currentPage = request.page_no;

  // Calculate start and end page numbers for current group of 10 pages
  const pageGroupSize = 10;
  const currentGroup = Math.floor((currentPage - 1) / pageGroupSize);
  const startPage = currentGroup * pageGroupSize + 1;
  const endPage = Math.min(startPage + pageGroupSize - 1, totalPages);
  
  // Create array of page numbers to display
  const pageNumbers = Array.from(
    { length: endPage - startPage + 1 },
    (_, i) => startPage + i
  );

  const handlePageClick = (pageNo: number) => {
    setRequest(prev => ({...prev, page_no: pageNo}));
  };
 
  return (
    <div className="paginate_complex">
      <a 
        href=""
        className="direction fir"
        onClick={(e) => {
          e.preventDefault();
          handlePageClick(1);
        }}>
        처음
      </a>
      <a 
        href=""
        className="direction prev"
        onClick={(e) => {
          e.preventDefault();
          const prevPage = Math.max(1, startPage - 1);
          handlePageClick(prevPage);
        }}
      >
        이전
      </a>
      {pageNumbers.map((num) => (
        currentPage === num ? (
          <strong 
            key={num}
            className="active"
          >
            {num}
          </strong>
        ) : (
          <a 
            key={num}
            href=""
            onClick={(e) => {
              e.preventDefault();
              handlePageClick(num);
            }}
          >
            {num}
          </a>
        )
      ))}
      <a 
        href=""
        className="direction next"
        onClick={(e) => {
          e.preventDefault();
          const nextPage = Math.min(totalPages, endPage + 1);
          handlePageClick(nextPage);
        }}
      >
        다음
      </a>
      <a 
        href=""
        className="direction last"
        onClick={(e) => {
          e.preventDefault();
          handlePageClick(totalPages);
        }}
      >
        끝
      </a>
      <div className="right">
        <select
          className="select"
          style={{ width: "120px" }}
          value={request.page_size}
          onChange={(e) =>
            setRequest((p) => ({ ...p, page_size: parseInt(e.target.value), page_no: 1 }))
          }
        >
          <option value="10">10개씩보기</option>
          <option value="20">20개씩보기</option>
          <option value="50">50개씩보기</option>
        </select>
      </div>
    </div>
  );
}
