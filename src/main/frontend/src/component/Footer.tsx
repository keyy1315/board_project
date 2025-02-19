import { useState } from 'react';

export default function Footer() {
  // 상태 관리: 관련 사이트 리스트 열고 닫기
  const [isOpen, setIsOpen] = useState(false);

  // 버튼 클릭 시, 상태 토글
  const handleButtonClick = () => {
    setIsOpen(!isOpen); // isOpen 상태를 반전시킴
  };

  return (
    <div id="footer">
      <div className="footer-wrap">
        <ul className="footer-link">
          <li>
            <a href="#">개인정보처리방침</a>
          </li>
          <li>
            <a href="#">이메일 무단수집거부</a>
          </li>
        </ul>
        <address>
          <b>(주)인터플러그</b> 서울시 서대문구 연세로 5다길 22-3 3층 (신촌역1번 출구)
          <br />
        </address>
        <p className="copy">Copyright(C) 2024 by interplug. All Rights Reserved.</p>
        <div className="famliy-link">
          {/* 버튼 클릭 시 handleButtonClick 호출 */}
          <button onClick={handleButtonClick}>관련 사이트 안내</button>
          {/* isOpen 상태에 따라 ul의 display 스타일 변경 */}
          <ul style={{ display: isOpen ? 'block' : 'none' }}>
            <li>
              <a href="#">네이버</a>
            </li>
            <li>
              <a href="#">다음</a>
            </li>
            <li>
              <a href="#">구글</a>
            </li>
            <li>
              <a href="#">유투브</a>
            </li>
            <li>
              <a href="#">구글</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
