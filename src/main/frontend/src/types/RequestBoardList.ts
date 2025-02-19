export interface RequestBoardList {
    category_cd : string,
    src_cd : string,
    search: string,
    sort_cd : string | "reg_dt",
    page_no : number | 1,
    page_size : number | 10  
}