export default function paginate() {
    return (
        <div className="paginate_complex">
        <a href="#" className="direction fir">
          처음
        </a>
        <a href="#" className="direction prev">
          이전
        </a>
        <a href="#">1</a>
        <a href="#">2</a>
        <a href="#">3</a>
        <a href="#">4</a>
        <strong>5</strong>
        <a href="#">6</a>
        <a href="#">7</a>
        <a href="#">8</a>
        <a href="#">9</a>
        <a href="#">10</a>
        <a href="#" className="direction next">
          다음
        </a>
        <a href="#" className="direction last">
          끝
        </a>
        <div className="right">
          <select className="select" style={{ width: "120px" }}>
            <option>10개씩보기</option>
            <option>20개씩보기</option>
            <option>50개씩보기</option>
          </select>
        </div>
      </div>
    )
}